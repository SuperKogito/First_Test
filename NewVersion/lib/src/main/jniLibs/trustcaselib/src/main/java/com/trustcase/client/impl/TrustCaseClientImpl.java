package com.trustcase.client.impl;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.trustcase.client.TrustCaseClient;
import com.trustcase.client.api.KeyPair;
import com.trustcase.client.api.LogLevel;
import com.trustcase.client.api.Logger;
import com.trustcase.client.api.TrustCaseCredentials;
import com.trustcase.client.api.enums.DeviceType;
import com.trustcase.client.api.enums.EncryptionType;
import com.trustcase.client.api.enums.EnvelopeType;
import com.trustcase.client.api.enums.EventType;
import com.trustcase.client.api.enums.MessageType;
import com.trustcase.client.api.enums.RoomIcon;
import com.trustcase.client.api.enums.TrustCaseRole;
import com.trustcase.client.api.exceptions.EncryptionException;
import com.trustcase.client.api.exceptions.JSONException;
import com.trustcase.client.api.exceptions.NetworkException;
import com.trustcase.client.api.exceptions.PhoneNumberNotRegisteredException;
import com.trustcase.client.api.exceptions.TrustCaseClientException;
import com.trustcase.client.api.messages.EventMessage;
import com.trustcase.client.api.messages.FileMessage;
import com.trustcase.client.api.messages.LocationMessage;
import com.trustcase.client.api.messages.Message;
import com.trustcase.client.api.messages.MessageEnvelope;
import com.trustcase.client.api.messages.MessageWrapper;
import com.trustcase.client.api.messages.Profile;
import com.trustcase.client.api.messages.ProfileKeyMessage;
import com.trustcase.client.api.messages.ProfileWrapper;
import com.trustcase.client.api.messages.RoomMetadataEnvelope;
import com.trustcase.client.api.messages.RoomMetadataMessage;
import com.trustcase.client.api.messages.RoomPasswordMessage;
import com.trustcase.client.api.messages.TaskMessage;
import com.trustcase.client.api.messages.TaskResponseMessage;
import com.trustcase.client.api.messages.TextMessage;
import com.trustcase.client.api.messages.TrustCircleWrapper;
import com.trustcase.client.api.requests.CreateAccountRequest;
import com.trustcase.client.api.requests.OpenRoomRequest;
import com.trustcase.client.api.requests.RegistrationRequest;
import com.trustcase.client.api.responses.*;
import com.trustcase.client.api.upload.ProgressCallback;
import com.trustcase.client.impl.crypto.CryptoEngine;
import com.trustcase.client.impl.crypto.CryptoKeyManager;
import com.trustcase.client.impl.crypto.KeyAccessor;
import com.trustcase.client.impl.request.ChangeRoomPasswordRequest;
import com.trustcase.client.impl.request.CloseRoomRequest;
import com.trustcase.client.impl.request.InvitePersonsRequest;
import com.trustcase.client.impl.request.KickPersonsRequest;
import com.trustcase.client.impl.request.LocationRequest;
import com.trustcase.client.impl.request.MetadataRequest;
import com.trustcase.client.impl.request.UpdateAccountRequest;
import com.trustcase.client.impl.request.UpdateProfileRequest;
import com.trustcase.client.impl.upload.TypedFileWithProgress;
import com.trustcase.client.util.TrustCaseClientUtils;

import com.trustcase.client.util.TrustCaseCryptoUtil;
import org.abstractj.kalium.keys.PrivateKey;
import org.abstractj.kalium.keys.PublicKey;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.Log;
import retrofit.client.Response;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedByteArray;

/**
 * Implementation of {@link TrustCaseClient}.<br>
 * Note, that this class is not thread safe.
 *
 * @author Audrius Stonkus
 * @author Gunther Klein
 */
public class TrustCaseClientImpl implements TrustCaseClient {
    private static final int BUFFER_SIZE = 4096;
    public final static String SERVICE_REQUEST_INTERCEPTOR = "client.service.requestInterceptor";
    public final static String SERVICE_CONVERTER = "client.service.converter";

    private final RoomsRestAdapter restAdapter;
    private final Gson gson;
    private CryptoKeyManager keyManager;
    private CryptoEngine cryptoEngine;
    private KeyAccessor keyAccessor;
    private Logger logger;
    private HashMap<String, Object> properties;

    private final String apiEndpoint;
    private String password;

    //------------------------------------------------------------------------------------------------------------------
    // region initialization and set up functions
    public TrustCaseClientImpl(String apiEndpoint) {
        this(apiEndpoint, null, null);
    }

    public TrustCaseClientImpl(String apiEndpoint, TrustCaseCredentials credentials, KeyAccessor keyAccessor) {
        this(apiEndpoint, credentials, keyAccessor, null, null, null);
    }

    public TrustCaseClientImpl(String apiEndpoint, TrustCaseCredentials credentials, KeyAccessor keyAccessor,
            LogLevel logLevel, Logger logger, Map<String, Object> services) {
        this.apiEndpoint = apiEndpoint;
        keyManager = new CryptoKeyManager();
        this.logger = logger;
        gson = GsonProvider.getInstance().getStandardGson();
        Builder builder = buildRestAdapter(apiEndpoint, logLevel, logger, gson, services);
        restAdapter = builder.build().create(RoomsRestAdapter.class);
        init(credentials, keyAccessor);
    }

    protected Builder buildRestAdapter(String endpoint, LogLevel logLevel, Logger logger, Gson gson,
                                       Map<String, Object> services) {
        Builder builder = new Builder()
                .setEndpoint(endpoint)
                .setLogLevel(toRetrofitLogLevel(logLevel))
                .setErrorHandler(new TrustCaseErrorHandler(logger))
                .setClient(new OkHttpProvider());
        addServices(builder, services);
        if (logger != null) {
            builder.setLog(new LoggerLog(logger));
        }
        return builder;
    }

    private void addServices(Builder builder, Map<String, Object> services) {
        // converter
        Converter converter = null;
        if (services != null && services.containsKey(SERVICE_CONVERTER)) {
            converter = (Converter) services.get(SERVICE_CONVERTER);
        }
        builder.setConverter(converter == null ? new GsonConverter(gson) : converter);
        // request interceptor
        RequestInterceptor interceptor = null;
        if (services != null && services.containsKey(SERVICE_REQUEST_INTERCEPTOR)) {
            interceptor = (RequestInterceptor) services.get(SERVICE_REQUEST_INTERCEPTOR);
        }
        builder.setRequestInterceptor(interceptor == null ? new TrustCaseRequestInterceptor() : interceptor);
    }

    @Override
    public void init(TrustCaseCredentials credentials, KeyAccessor keyAccessor) {
        this.keyAccessor = keyAccessor != null ? keyAccessor : new DefaultKeyAccessor();
        password = credentials == null ? null : credentials.getPassword();
        if (credentials != null) {
            keyManager.init(this.keyAccessor, credentials.getJid(), credentials.getPrivateKey(),
                    credentials.getPublicKey());
        }
        cryptoEngine = new CryptoEngine();
        cryptoEngine.init(keyManager);
    }

    private static RestAdapter.LogLevel toRetrofitLogLevel(LogLevel ll) {
        if (ll == null) {
            return RestAdapter.LogLevel.NONE;
        }
        switch (ll) {
            case BASIC:
                return RestAdapter.LogLevel.BASIC;
            case EXTENDED:
                return RestAdapter.LogLevel.HEADERS_AND_ARGS;
            case FULL:
                return RestAdapter.LogLevel.FULL;
            case OFF:
            default:
                return RestAdapter.LogLevel.NONE;
        }
    }

    /**
     * Interceptor to set authentication header on all REST calls
     */
    private class TrustCaseRequestInterceptor implements RequestInterceptor {
        @Override
        public void intercept(RequestFacade requestFacade) {
            requestFacade.addHeader("Accept", "application/json");
            String authorizationValue = generateAuth();
            if (authorizationValue != null) {
                requestFacade.addHeader("Authorization", authorizationValue);
            }
        }
    }

    private class LoggerLog implements Log {
        private final Logger logger;

        public LoggerLog(Logger logger) {
            this.logger = logger;
        }

        @Override
        public void log(String message) {
            logger.log(message);
        }
    }

    /**
     * Default key accessor which loads participant keys fom server every time it is asked
     */
    class DefaultKeyAccessor implements KeyAccessor {
        @Override
        public List<Identity> getPublicKeys(String roomId) {
            try {
                return getRoomParticipants(roomId);
            } catch (TrustCaseClientException ex) {
                return Collections.emptyList(); // TODO: exception handling!
            }
        }

        @Override
        public String getProfileKey(String jid) {
            return (String) getProperty(PROPERTY_PROFILE_KEY);
        }

        @Override
        public String getRoomKey(String roomId) {
            return null; // Not available for now with default key accessor. Change in the future?
        }

        @Override
        public String getTrustCircleKey(String jid) {
            return (String) getProperty(PROPERTY_TRUST_CIRCLE_KEY);
        }
    }

    @Override
    public void setProperty(String key, Object value) {
        if (properties == null) {
            properties = new HashMap<>();
        }
        properties.put(key, value);
    }

    @Override
    public Object getProperty(String key) {
        if (properties == null) {
            return null;
        }
        return properties.get(key);
    }

    @Override
    public Object clearProperty(String key) {
        if (properties != null) {
            return properties.remove(key);
        }
        return null;
    }

    /**
     * @return the jid
     */
    @Override
    public String getJid() {
        return keyManager == null ? null : keyManager.getJid();
    }

    /**
     * @return the publicKey
     */
    private String getPublicKey() {
        return keyManager == null ? null : keyManager.getPublicKey().toString();
    }

    /**
     * @return the apiEndpoint
     */
    @Override
    public String getApiEndpoint() {
        return apiEndpoint;
    }

    @Override
    public KeyPair getKeyPair() {
        if (keyManager == null) {
            return null;
        }
        PrivateKey privateKey = keyManager.getPrivateKey();
        PublicKey publicKey = keyManager.getPublicKey();
        if (privateKey == null || publicKey == null) {
            return null;
        }
        return new KeyPair(privateKey.toString(), publicKey.toString());
    }

    @Override
    public TrustCaseCredentials getCredentials() {
        if (keyManager == null || keyManager.getPrivateKey() == null || keyManager.getPublicKey() == null) {
            return null;
        }
        return new TrustCaseCredentials(getJid(), password, getKeyPair());
    }

    private String generateAuth() {
        String jid = getJid();
        if (password == null || jid == null) {
            return null;
        }
        String userAndPassword = jid + ":" + password;
        return "Basic " + new String(Base64.encodeBase64(userAndPassword.getBytes()));
    }
    //endregion

    //------------------------------------------------------------------------------------------------------------------
    // region cryptography functions
    @Override
    public <T> String encrypt(T data, EncryptionType encryptionType, String roomId, Collection<String> recipients)
            throws EncryptionException {
        String json = gson.toJson(data);
        byte[] cipher = cryptoEngine.encryptMessage(json, encryptionType, roomId, recipients);
        return new String(Base64.encodeBase64(cipher));
    }

    @Override
    public <T> T decrypt(String string, String roomID, Class<T> clazz) throws EncryptionException, JSONException {
        byte[] cipher = Base64.decodeBase64(string.getBytes());
        String json = cryptoEngine.decryptMessage(cipher, roomID);
        try {
            return gson.fromJson(json, clazz);
        } catch (JsonParseException ex) {
            throw new JSONException(ex);
        }
    }

    @Override
    public <T> String encryptSymmetrically(T data, String symmetricKey) throws EncryptionException {
        String json = gson.toJson(data);
        byte[] cipher = cryptoEngine.encryptSymmetrically(json, symmetricKey);
        return new String(Base64.encodeBase64(cipher));
    }

    @Override
    public <T> T decryptSymmetrically(String data, String symmetricKey, Class<T> clazz) throws EncryptionException,
            JSONException {
        byte[] cipher = Base64.decodeBase64(data.getBytes());
        String json = cryptoEngine.decryptSymmetrically(cipher, symmetricKey);
        try {
            return gson.fromJson(json, clazz);
        } catch (JsonParseException ex) {
            throw new JSONException(ex);
        }
    }

    @Override
    public void encryptStream(InputStream in, OutputStream out, String symmetricKey) throws EncryptionException {
        try {
            cryptoEngine.encryptStream(in, out, symmetricKey);
        } catch (Exception ex) {
            throw new EncryptionException(ex.getMessage(), ex);
        }
    }

    @Override
    public void decryptStream(InputStream in, OutputStream out, String symmetricKey) throws EncryptionException {
        try {
            cryptoEngine.decryptStream(in, out, symmetricKey);
        } catch (Exception ex) {
            throw new EncryptionException(ex.getMessage(), ex);
        }
    }

    @Override
    public String generateSymmetricKey() {
        return cryptoEngine.generateSymmetricKey();
    }

    /**
     * Creates a new random key pair (private/public key) that the client can use.
     */
    private KeyPair createNewKeyPair() {
        String[] keyPair = cryptoEngine.generateKeyPair();
        return new KeyPair(keyPair[0], keyPair[1]);
    }
    // endregion

    //------------------------------------------------------------------------------------------------------------------
    // region 1 - account management functions
    @Override
    public void register(RegistrationRequest registrationRequest) throws TrustCaseClientException {
        restAdapter.register(registrationRequest);
    }

    @Override
    public TrustCaseCredentials createAccount(String pushId, String smsCode, DeviceType deviceType)
            throws TrustCaseClientException {
        return createAccount(getKeyPair() == null, pushId, smsCode, deviceType);
    }

    @Override
    public TrustCaseCredentials createAccount(boolean createNewKeyPair, String pushId, String smsCode,
                                              DeviceType deviceType) throws TrustCaseClientException {
        KeyPair keyPair;
        if (createNewKeyPair) {
            keyPair = createNewKeyPair();
        } else {
            keyPair = getKeyPair();
            if (keyPair == null) {
                throw new IllegalArgumentException(
                        "createNewKeyPair=false, but client was not initialized with a KeyPair");
            }
        }
        CreateAccountRequest req = new CreateAccountRequest(smsCode, keyPair.getPublicKey(), pushId, deviceType);
        CreateAccountResponse response = restAdapter.createAccount(req);
        TrustCaseCredentials credentials = new TrustCaseCredentials(response.getJid(), response.getPassword(),
                keyPair.getPrivateKey(), keyPair.getPublicKey());
        // update/create key manager
        init(credentials, keyAccessor);
        return credentials;
    }

    @Override
    public void updateRegistrationInfo(String pushId) throws TrustCaseClientException {
        UpdateAccountRequest request = new UpdateAccountRequest(pushId);
        restAdapter.updateRegistration(request);
    }

    @Override
    public Identity getAccountDetails(String jid) throws TrustCaseClientException {
        return restAdapter.getAccount(jid);
    }

    @Override
    public WebLoginResponse createWebLogin(String loginId, String token) throws TrustCaseClientException {
        String symmetricKey = TrustCaseCryptoUtil.symmetricKeyFromPassword(token, loginId);
        String encryptedLoginData = encryptSymmetrically(getCredentials(), symmetricKey);
        WebLogin webLogin = new WebLogin(loginId, encryptedLoginData);
        return restAdapter.createWebLogin(webLogin);
    }

    @Override
    public TrustCaseCredentials performWebLogin(String loginId, String token) throws TrustCaseClientException {
        String symmetricKey = TrustCaseCryptoUtil.symmetricKeyFromPassword(token, loginId);
        WebLogin request = new WebLogin(loginId, null);
        WebLogin response = restAdapter.performWebLogin(request);
        TrustCaseCredentials credentials = decryptSymmetrically(response.initial_data, symmetricKey,
                TrustCaseCredentials.class);
        return credentials;
    }
    // endregion

    //------------------------------------------------------------------------------------------------------------------
    // region 2 - profile and address book management functions
    @Override
    public void updateProfile(ProfileWrapper profileWrapper) throws TrustCaseClientException {
        UpdateProfileRequest request = new UpdateProfileRequest();
        String key = keyAccessor.getProfileKey(getJid());
        if (profileWrapper != null && profileWrapper.getProfile() != null && key != null) {
            String profile = encryptSymmetrically(profileWrapper.getProfile(), key);
            request.setProfile(profile);
        }
        restAdapter.updateProfile(request);
    }

    @Override
    public List<ProfileWrapper> retrieveProfiles(List<String> jids) throws TrustCaseClientException {
        List<ProfileResponseItem> profiles = restAdapter.retrieveProfiles(jids);
        List<ProfileWrapper> decryptedProfiles = new ArrayList<>();
        for (ProfileResponseItem profileItem : profiles) {
            String key = keyAccessor.getProfileKey(profileItem.jid);
            // If there is no key available we are not allowed to "see" the profile.
            if (key != null && profileItem.profile != null) {
                try {
                    Profile profile = decryptSymmetrically(profileItem.profile, key, Profile.class);
                    ProfileWrapper profileWrapper = new ProfileWrapper();
                    profileWrapper.setProfile(profile);
                    profileWrapper.setJid(profileItem.jid);
                    profileWrapper.setPublicKey(profileItem.public_key);
                    decryptedProfiles.add(profileWrapper);
                } catch (EncryptionException | JSONException ex) {
                    if (logger != null) {
                        logger.log("Failed to process profile: " + ex.getMessage());
                    }
                }
            }
        }
        return decryptedProfiles;
    }

    @Override
    public List<Contact> syncAddressBook(List<String> phones) throws TrustCaseClientException {
        Map<String, String> hashesToPhoneNumbers = new HashMap<>();

        for (String phoneNumber : phones) {
            hashesToPhoneNumbers.put(cryptoEngine.hash(phoneNumber), phoneNumber);
        }
        List<Contact> contacts = restAdapter.syncAddressBook(new ArrayList<>(hashesToPhoneNumbers.keySet()));
        if (contacts == null || contacts.size() == 0) {
            return contacts;
        }
        List<Contact> result = new ArrayList<>(contacts.size());
        for (Contact contact : contacts) {
            String phoneNumber = hashesToPhoneNumbers.get(contact.getHash());
            if (phoneNumber != null) {
                Contact c = new Contact(contact.getJid(), contact.getPublicKey(), phoneNumber);
                c.hash = contact.getHash();
                result.add(c);
            }
        }
        return result;
    }
    // endregion

    //------------------------------------------------------------------------------------------------------------------
    // region 3 - room management functions
    @Override
    public List<RoomListResponseItem> listRooms() throws TrustCaseClientException {
        return restAdapter.listRooms();
    }

    @Override
    public RoomMetadataEnvelope getRoomMetadata(String roomID) throws EncryptionException, TrustCaseClientException {
        MetadataResponse response = restAdapter.getMetadata(roomID);
        RoomMetadataMessage message = decrypt(response.data, roomID, RoomMetadataMessage.class);
        return new RoomMetadataEnvelope(message, response);
    }

    @Override
    public List<Identity> getRoomParticipants(String roomID) throws TrustCaseClientException {
        return restAdapter.getParticipants(roomID);
    }

    private <T> List<T> makeNotNull(List<T> list, boolean clone) {
        if (list == null) {
            return new ArrayList<>();
        }
        if (clone) {
            return new ArrayList<>(list);
        }
        return list;
    }

    @Override
    public OpenRoomResponse openRoom(RoomMetadataMessage metadata, List<Identity> participants)
            throws EncryptionException, TrustCaseClientException {
        participants = makeNotNull(participants, true);
        List<String> administratorJIDs = makeNotNull(metadata.getAdministrators(), true);
        List<String> participantJIDs = new ArrayList<>();
        for (Identity identity : participants) {
            participantJIDs.add(identity.jid);
        }

        String myJID = getJid();
        String myKey = getPublicKey();
        if (!participantJIDs.contains(myJID)) { // Opener must be participant
            participantJIDs.add(myJID);
            participants.add(new Identity(myJID, myKey));
        }
        if (!administratorJIDs.contains(myJID)) { // Opener must be admin, as well
            administratorJIDs.add(myJID);
        }
        metadata.setAdministrators(administratorJIDs);

        String tmpRoomID = UUID.randomUUID().toString();
        keyManager.cachePublicKeys(tmpRoomID, participants);
        String encryptedData = encrypt(metadata, EncryptionType.PUBLIC_KEY, tmpRoomID, null);

        OpenRoomRequest openRoomRequest = new OpenRoomRequest();
        openRoomRequest.participants = participantJIDs;
        openRoomRequest.data = encryptedData;
        OpenRoomResponse openRoomResponse = restAdapter.openRoom(openRoomRequest);

        keyManager.cachePublicKeys(openRoomResponse.room_id, openRoomResponse.participants);
        if (metadata.getRoomKey() != null) {
            keyManager.cacheRoomKey(openRoomResponse.room_id, metadata.getRoomKey());
        }
        if (!participants.equals(openRoomResponse.participants)) {
            updateRoomMetadata(openRoomResponse.room_id, openRoomResponse.password, metadata);
        }
        return openRoomResponse;
    }

    @Override
    public OpenRoomResponse openRoom(String name, RoomIcon icon, String roomKey, List<Identity> participants,
                                     List<String> administratorJIDs) throws EncryptionException,
            TrustCaseClientException {
        RoomMetadataMessage metadata = new RoomMetadataMessage(name, icon, roomKey, administratorJIDs);
        return openRoom(metadata, participants);
    }

    @Override
    public void closeRoom(String roomID, String password) throws TrustCaseClientException {
        CloseRoomRequest closeRoomRequest = new CloseRoomRequest(password);
        restAdapter.closeRoom(roomID, closeRoomRequest);
    }

    @Override
    public void invitePersons(String roomID, String password, List<String> inviteJIDs) throws TrustCaseClientException {
        InvitePersonsRequest invitePersonsRequest = new InvitePersonsRequest(password, inviteJIDs);
        restAdapter.invitePersons(roomID, invitePersonsRequest);
        keyManager.invalidateRoom(roomID);
    }

    @Override
    public void kickPersons(String roomID, String password, List<String> kickJIDs) throws TrustCaseClientException {
        KickPersonsRequest kickPersonsRequest = new KickPersonsRequest(password, kickJIDs);
        restAdapter.kickPersons(roomID, kickPersonsRequest);
        keyManager.invalidateRoom(roomID);
    }

    @Override
    public void leaveRoom(String roomID) throws TrustCaseClientException {
        restAdapter.leaveRoom(roomID);
    }

    @Override
    public void updateRoomMetadata(String roomID, String password, RoomMetadataMessage metadataMessage)
            throws EncryptionException, TrustCaseClientException {
        if (metadataMessage.getRoomKey() != null) { // If key is being changed, load it into cache
            keyManager.cacheRoomKey(roomID, metadataMessage.getRoomKey());
        }
        String encryptedData = encrypt(metadataMessage, EncryptionType.PUBLIC_KEY, roomID, null);
        MetadataRequest metadataRequest = new MetadataRequest(password, encryptedData);
        restAdapter.updateMetadata(roomID, metadataRequest);
    }

    @Override
    public void deleteRoom(String roomID) throws TrustCaseClientException {
        throw new UnsupportedOperationException(); // TODO: implement it!
    }

    @Override
    public void changeRoomPassword(String roomID, String password, String newPassword) throws TrustCaseClientException {
        ChangeRoomPasswordRequest changeRoomPasswordRequest = new ChangeRoomPasswordRequest(password, newPassword);
        restAdapter.changeRoomPassword(roomID, changeRoomPasswordRequest);
    }
    // endregion

    //------------------------------------------------------------------------------------------------------------------
    // region 4 - messaging functions
    private void decryptMessageEnvelope(MessageEnvelope envelope) {
        try {
            if (envelope.type == EnvelopeType.MESSAGE && envelope.messageWrapper != null
                    && envelope.messageWrapper.type != null) {
                Message message = null;
                MessageWrapper messageWrapper = envelope.messageWrapper;
                switch (messageWrapper.type) {
                    case TEXT:
                        message = decrypt(messageWrapper.data, envelope.room_id, TextMessage.class);
                        break;
                    case TASK:
                        message = decrypt(messageWrapper.data, envelope.room_id, TaskMessage.class);
                        break;
                    case TASK_RESPONSE:
                        message = decrypt(messageWrapper.data, envelope.room_id, TaskResponseMessage.class);
                        break;
                    case FILE:
                        message = decrypt(messageWrapper.data, envelope.room_id, FileMessage.class);
                        break;
                    case ROOM_PASSWORD:
                        message = decrypt(messageWrapper.data, envelope.room_id, RoomPasswordMessage.class);
                        break;
                    case PROFILE_KEY:
                        message = decrypt(messageWrapper.data, envelope.room_id, ProfileKeyMessage.class);
                        break;
                    case LOCATION:
                        message = decrypt(messageWrapper.data, envelope.room_id, LocationMessage.class);
                        break;
                    default:
                        break;
                }
                envelope.message = message;
            } else if (envelope.type == EnvelopeType.EVENT && envelope.eventMessage != null
                    && envelope.eventMessage.type != null) {
                EventMessage eventMessage = envelope.eventMessage;
                if (eventMessage.type == EventType.OPEN_ROOM || eventMessage.type == EventType.INVITE
                        || eventMessage.type == EventType.KICK || eventMessage.type == EventType.LEAVE
                        || eventMessage.type == EventType.PARTICIPANTS_UPDATE) {
                    keyManager.cachePublicKeys(envelope.room_id, eventMessage.participants);
                }
                if (eventMessage.type == EventType.OPEN_ROOM || eventMessage.type == EventType.METADATA_UPDATE) {
                    RoomMetadataMessage metadata = decrypt(eventMessage.metadata, envelope.room_id,
                            RoomMetadataMessage.class);
                    if (metadata.getRoomKey() != null) { // If key has been changed, load it into cache
                        keyManager.cacheRoomKey(envelope.room_id, metadata.getRoomKey());
                    }
                    envelope.message = metadata;
                }
            }
        } catch (TrustCaseClientException ex) {
            envelope.error = ex;
        }
    }

    @Override
    public List<MessageEnvelope> fetchMessages(String roomID) throws TrustCaseClientException, EncryptionException {
        List<MessageEnvelope> envelopes = restAdapter.fetchMessages(roomID, TrustCaseClient.PUBLIC_NODE);
        for (MessageEnvelope envelope : envelopes) {
            decryptMessageEnvelope(envelope);
        }
        return envelopes;
    }

    @Override
    public List<MessageEnvelope> syncMessages(Date lastSyncDate) throws TrustCaseClientException, EncryptionException {
        String strSince = null;
        if (lastSyncDate != null) {
            DateFormat dateFormat = new SimpleDateFormat(GsonProvider.API_DATE_PATTERN);
            strSince = dateFormat.format(lastSyncDate);
        }
        List<MessageEnvelope> envelopes = restAdapter.syncMessages(strSince);
        for (MessageEnvelope envelope : envelopes) {
                decryptMessageEnvelope(envelope);
        }
        return envelopes;
    }

    @Override
    public MessageId postRoomMessage(String roomID, Message message) throws TrustCaseClientException,
            EncryptionException {
        return postRoomMessage(roomID, message, null, EncryptionType.PUBLIC_KEY);
    }

    @Override
    public MessageId postRoomMessage(String roomID, Message message, EncryptionType encryptionType)
            throws TrustCaseClientException, EncryptionException {
        return postRoomMessage(roomID, message, null, encryptionType);
    }

    @Override
    public MessageId postRoomMessage(String roomID, Message message, List<String> recipientJIDs,
                                     EncryptionType encryptionType) throws TrustCaseClientException,
            EncryptionException {
        return postMessage(roomID, message, recipientJIDs, encryptionType);
    }

    @Override
    public PrivateMessageId postPrivateMessage(Identity recipient, String roomName, Message message)
            throws TrustCaseClientException, EncryptionException {
        List<Identity> identities = new ArrayList<>();
        identities.add(new Identity(getJid(), getPublicKey()));
        identities.add(new Identity(recipient.getJid(), recipient.getPublicKey()));

        // Hack: if sending task as private message, address it to recipient
        if (message.getType() == MessageType.TASK) {
            ((TaskMessage) message).recipient = recipient.getJid();
        }

        // Create room metadata
        String tmpRoomID = UUID.randomUUID().toString();
        RoomMetadataMessage metadata = new RoomMetadataMessage();
        metadata.setName(roomName);
        metadata.setIcon(RoomIcon.UNKNOWN);
        keyManager.cachePublicKeys(tmpRoomID, identities);
        String encryptedData = encrypt(metadata, EncryptionType.PUBLIC_KEY, tmpRoomID, null);

        OpenRoomResponse response = restAdapter.openPrivateRoom(recipient.getJid(), new MetadataRequest(null,
                encryptedData));
        // Open room
        keyManager.cachePublicKeys(response.room_id, response.participants);

        // Update metadata if current differs from it
        RoomMetadataEnvelope metadataEnvelope = getRoomMetadata(response.room_id);
        if (!metadata.equals(metadataEnvelope.getMetadataMessage())) {
            updateRoomMetadata(response.room_id, response.password, metadata);
        }

        // Post message
        MessageId messageID = postMessage(response.room_id, message, null, EncryptionType.PUBLIC_KEY);

        return new PrivateMessageId(response.room_id, messageID.getMessageId());
    }

    @Override
    public PrivateMessageId postPrivateMessage(String recipientPhoneNumber, String roomName, Message message)
            throws TrustCaseClientException, EncryptionException {
        List<Contact> contacts = syncAddressBook(Arrays.asList(recipientPhoneNumber));
        if (contacts.isEmpty()) {
            throw new PhoneNumberNotRegisteredException();
        }
        return postPrivateMessage(TrustCaseClientUtils.toIdentity(contacts.get(0), TrustCaseRole.MOBILE_USER), roomName,
                message);
    }

    private MessageId postMessage(String roomID, Message message, List<String> recipients, EncryptionType encryptionType)
            throws TrustCaseClientException, EncryptionException {
        MessageWrapper messageWrapper = new MessageWrapper();
        messageWrapper.type = message.getType();
        messageWrapper.data = encrypt(message, encryptionType, roomID, recipients);
        return restAdapter.sendMessage(roomID, TrustCaseClient.PUBLIC_NODE, messageWrapper);
    }
    // endregion

    //------------------------------------------------------------------------------------------------------------------
    // region 5 - file upload/download functions
    @Override
    public void deleteFile(String fileId) throws TrustCaseClientException {
        restAdapter.deleteFile(fileId);
    }

    @Override
    public void deleteFile(String roomId, String fileId) throws TrustCaseClientException {
        restAdapter.deleteFile(roomId, fileId);
    }

    @Override
    public UploadFileResponse uploadFile(String roomId, File file) throws TrustCaseClientException, IOException {
        return uploadFile(roomId, file, null);
    }

    @Override
    public UploadFileResponse uploadFile(String roomId, File file, ProgressCallback progressCallback)
            throws TrustCaseClientException, IOException {
        TypedFileWithProgress typedFile = new TypedFileWithProgress(MIME_TYPE_FORM_DATA, file, progressCallback);
        if (roomId == null || roomId.isEmpty()) {
            return restAdapter.uploadFile(typedFile);
        }
        return restAdapter.uploadFile(roomId, typedFile);
    }

    @Override
    public UploadFileResponse uploadFile(File file) throws TrustCaseClientException, IOException {
        return uploadFile(file, null);
    }

    @Override
    public UploadFileResponse uploadFile(File file, ProgressCallback progressCallback) throws TrustCaseClientException,
            IOException {
        TypedFileWithProgress typedFile = new TypedFileWithProgress(MIME_TYPE_FORM_DATA, file, progressCallback);
        return restAdapter.uploadFile(typedFile);
    }

    private void processDownloadResponse(Response response, OutputStream output) throws IOException,
            TrustCaseClientException {
        if (response == null) {
            throw new NetworkException("No response when downloading file");
        }
        TypedByteArray body = (TypedByteArray) response.getBody();
        output.write(body.getBytes());
    }

    @Override
    public void downloadFile(String roomId, String fileId, OutputStream file) throws TrustCaseClientException,
            IOException {
        downloadFile(roomId, fileId, file, null);
    }

    @Override
    public void downloadFile(String roomId, String fileId, OutputStream file, ProgressCallback progressCallback)
            throws TrustCaseClientException, IOException {
        Response response = restAdapter.downloadFile(roomId, fileId);
        processDownloadResponse(response, file);
    }

    @Override
    public void downloadFile(String fileId, OutputStream file) throws TrustCaseClientException, IOException {
        downloadFile(fileId, file, null);
    }

    @Override
    public void downloadFile(String fileId, OutputStream file, ProgressCallback progressCallback)
            throws TrustCaseClientException, IOException {
        Response response = restAdapter.downloadFile(fileId);
        processDownloadResponse(response, file);
    }

    @Override
    public void downloadExternalFile(String downloadLink, OutputStream file) throws TrustCaseClientException,
            IOException {
        downloadExternalFile(downloadLink, file, null);
    }

    @Override
    public void downloadExternalFile(String downloadLink, OutputStream file, ProgressCallback progressCallback)
            throws TrustCaseClientException, IOException {
        downloadExternalFile(downloadLink, file, progressCallback, new CancellationToken(), false);
    }

    @Override
    public void downloadExternalFile(String downloadLink, OutputStream file, ProgressCallback progressCallback,
                                     CancellationToken cancellationToken, boolean authorize)
            throws TrustCaseClientException, IOException {
        if (cancellationToken.isCancelled()) {
            return;
        }
        URL url = new URL(downloadLink);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            connection.setRequestMethod("GET");

            if (authorize) {
                String authorizationValue = generateAuth();
                if (authorizationValue != null) {
                    connection.setRequestProperty("Authorization", authorizationValue);
                }
            }

            if (cancellationToken.isCancelled()) {
                return;
            }

            connection.connect();
            if (cancellationToken.isCancelled()) {
                return;
            }

            InputStream inputStream = connection.getInputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            long written = 0;
            int n;
            while ((n = inputStream.read(buffer)) != -1) {
                if (cancellationToken.isCancelled()) {
                    return;
                }
                if (n > 0) {
                    file.write(buffer, 0, n);
                }
                if (progressCallback != null) {
                    written += n;
                    progressCallback.onProgress(written);
                }
            }
        } finally {
            connection.disconnect();
        }
    }
    // endregion

    //------------------------------------------------------------------------------------------------------------------
    // region 6 - trust broker functions
    @Override
    public LocationRequestId sendLocationRequest(List<String> jids, int timeout) throws TrustCaseClientException {
        return restAdapter.sendLocationRequest(new LocationRequest(jids, timeout));
    }

    @Override
    public TrustCircleResponse addTrustCircleItems(List<TrustCircleWrapper> trustCircleWrappers)
            throws TrustCaseClientException {
        List<TrustCircleItem> items = new ArrayList<>();
        String key = keyAccessor.getTrustCircleKey(getJid());
        if (key == null) {
            throw new EncryptionException("No trust circle key found - is client trust broker?");
        }
        for (TrustCircleWrapper wrapper : trustCircleWrappers) {
            try {
                String profile = encryptSymmetrically(wrapper, key);
                items.add(new TrustCircleItem(wrapper.getJid(), wrapper.getTrustMode(), profile));
            } catch (EncryptionException ex) {
                if (logger != null) {
                    logger.log("Failed to process trust circle item: " + ex.getMessage());
                }
            }
        }
        return restAdapter.addTrustCircleItems(items);
    }

    @Override
    public TrustCircleResponse removeTrustCircleItems(List<String> jids) throws TrustCaseClientException {
        return restAdapter.removeTrustCircleItems(jids);
    }

    @Override
    public List<TrustCircleWrapper> queryTrustCircle(String trustBrokerJid, List<String> syncJids)
            throws TrustCaseClientException {
        List<TrustCircleItem> items = restAdapter.queryTrustCircle(trustBrokerJid, syncJids);
        List<TrustCircleWrapper> decryptedItems = new ArrayList<>();
        String key = keyAccessor.getTrustCircleKey(trustBrokerJid);
        if (key == null) {
            throw new EncryptionException("No trust circle key found - is client trust broker?");
        }
        for (TrustCircleItem item : items) {
            try {
                TrustCircleWrapper wrapper = decryptSymmetrically(item.profile, key, TrustCircleWrapper.class);
                wrapper.setJid(item.jid);
                wrapper.setPublicKey(item.public_key);
                wrapper.setTrustMode(item.trustMode);
                decryptedItems.add(wrapper);
            } catch (EncryptionException|JSONException ex) {
                if (logger != null) {
                    logger.log("Failed to process trust circle item: " + ex.getMessage());
                }
            }
        }
        return decryptedItems;
    }
    // endregion

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("TrustCaseClientImpl [");
        sb.append("apiEndpoint=").append(getApiEndpoint());
        sb.append(",jid=").append(getJid());
        sb.append(",publicKey=").append(getJid());
        Object phone = getProperty(PROPERTY_PHONE_NUMBER);
        sb.append(",phoneNumber=").append(phone == null ? "" : phone);
        Object deviceType = getProperty(PROPERTY_DEVICE_TYPE);
        sb.append(",deviceType=").append(deviceType == null ? "" : deviceType);
        sb.append("]");
        return sb.toString();
    }
}
