package com.trustcase.client;

import com.trustcase.client.api.KeyPair;
import com.trustcase.client.api.TrustCaseCredentials;
import com.trustcase.client.api.enums.DeviceType;
import com.trustcase.client.api.enums.EncryptionType;
import com.trustcase.client.api.enums.RoomIcon;
import com.trustcase.client.api.exceptions.EncryptionException;
import com.trustcase.client.api.exceptions.JSONException;
import com.trustcase.client.api.exceptions.PhoneNumberNotRegisteredException;
import com.trustcase.client.api.exceptions.TrustCaseClientException;
import com.trustcase.client.api.messages.Message;
import com.trustcase.client.api.messages.MessageEnvelope;
import com.trustcase.client.api.messages.ProfileWrapper;
import com.trustcase.client.api.messages.RoomMetadataEnvelope;
import com.trustcase.client.api.messages.RoomMetadataMessage;
import com.trustcase.client.api.messages.TrustCircleWrapper;
import com.trustcase.client.api.requests.RegistrationRequest;
import com.trustcase.client.api.responses.*;
import com.trustcase.client.api.upload.ProgressCallback;
import com.trustcase.client.impl.CancellationToken;
import com.trustcase.client.impl.crypto.KeyAccessor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Interface, representing all relevant business methods a trustcase client is able to perform. A concrete client
 * instance is resolved using one of the various static <code>newClient()</code> methods of class {@link TrustCase}.
 *
 * @author Gunther Klein
 * @see TrustCase
 * @see TrustCase#newClient(java.util.Properties)
 * @see TrustCase#newClient(String)
 * @see TrustCase#newClient(String, TrustCaseCredentials)
 */
public interface TrustCaseClient {

    /**
     * Default node for public channels/rooms
     */
    String PUBLIC_NODE = "posts";
    /**
     * Default MIME for file uploads
     */
    String MIME_TYPE_FORM_DATA = "multipart/form-data";
    /**
     * Well known property name for a client's phone number.
     * 
     * @see #setProperty(String, Object)
     */
    String PROPERTY_PHONE_NUMBER = "phoneNumber";
    /**
     * Property name for default profile key which is used for tests
     * 
     * @see #setProperty(String, Object)
     */
    String PROPERTY_PROFILE_KEY = "profileKey";
    /**
     * Property name for default trust circle key which is used for tests
     *
     * @see #setProperty(String, Object)
     */
    String PROPERTY_TRUST_CIRCLE_KEY = "trustCircleKey";
    /**
     * Well known property name for a client's device type.
     *
     * @see #setProperty(String, Object)
     */
    String PROPERTY_DEVICE_TYPE = "deviceType";

    /**
     * Sets the given property on this client. This does not do anything except storing the given property in an
     * internal hash map. This is useful e.g. for testing purposes where you want to associated custom properties with
     * various clients that were created, e.g. phone numbers
     * 
     * @param key
     *            property key
     * @param value
     *            property value
     */
    void setProperty(String key, Object value);

    /**
     * Gets the specified property value
     * 
     * @param key
     *            property key
     * @return property value
     */
    Object getProperty(String key);

    /**
     * Removes the mapping for the specified property key from this client's property map if present.
     * 
     * @param key
     *            property key
     * @return property value
     */
    Object clearProperty(String key);

    /**
     * Returns the api endpoint that this client is pointing to.
     */
    String getApiEndpoint();

    /**
     * Returns the JID, that this client is configured with - if any. Returns null if this client has no JID assigned
     *         yet.
     */
    String getJid();

    /**
     * Returns the key pair (private/public key) that this client is configured/initialized with. May be null if not
     * initialized/configured with credentials yet.
     */
    KeyPair getKeyPair();

    /**
     * Returns the credentials that this client is configured with. May be null if not initialized/configured with
     * credentials yet.
     */
    TrustCaseCredentials getCredentials();

    /**
     * Initialize client with user credentials and key accessor.
     *
     * @param credentials
     *            - credentials with username, password and keys
     * @param keyAccessor
     *            - key accessor for loading participant keys for encryption/decryption If key accessor is null, the
     *            default one will be used, which loads keys from server every time.
     */
    void init(TrustCaseCredentials credentials, KeyAccessor keyAccessor);

    /**
     * Helper function to generate encryption key for symmetric encryption.
     */
    String generateSymmetricKey();

    /**
     * Serializes given data object to JSON and encrypts it asymmetrically for all room members.
     * @param data - data to be encrypted
     * @param encryptionType - what encryption type should be used
     * @param roomId - ID of the room the data is going to be posted in. Room members and public keys will be loaded
     *               using KeyAccessor which has been passed when creating the instance of the client.
     * @param recipients - if not null, data will be encrypted for these recipients only
     * @return - encrypted data string
     * @throws EncryptionException - when anything goes wrong during encryption process
     */
    <T> String encrypt(T data, EncryptionType encryptionType, String roomId, Collection<String> recipients)
            throws EncryptionException;

    /**
     * Decrypts asymmetrically encrypted data from the room.
     * @param data - encrypted data string
     * @param roomID - ID of the room the data was posted in. Room members and public keys will be loaded
     *               using KeyAccessor which has been passed when creating the instance of the client.
     * @param clazz - type of the data
     * @return - decrypted and deserialized data object, if possible
     * @throws EncryptionException - if something goes wrong during decryption process
     * @throws JSONException - if decryption succeeded, but object could not be deserialized into given type
     */
    <T> T decrypt(String data, String roomID, Class<T> clazz) throws EncryptionException, JSONException;

    /**
     * Serializes given data object and encrypts it with given symmetric key.
     * @param data - object to be encrypted
     * @param symmetricKey - encryption key. The same key should be used to decrypt the data later
     * @return - encrypted data string
     * @throws EncryptionException - when anything goes wrong during encryption process
     */
    <T> String encryptSymmetrically(T data, String symmetricKey) throws EncryptionException;

    /**
     * Decrypts symmetrically encrypted data.
     * @param data - encrypted data string
     * @param symmetricKey - symmetric key used for encryption
     * @param clazz - type of the data
     * @return - decrypted and deserialized data object, if possible
     * @throws EncryptionException - if something goes wrong during decryption process
     * @throws JSONException - if decryption succeeded, but object could not be deserialized into given type
     */
    <T> T decryptSymmetrically(String data, String symmetricKey, Class<T> clazz) throws EncryptionException,
            JSONException;

    /**
     * Encrypts data stream with symmetric key
     * @param in - data to encrypt
     * @param out - where to write encrypted data
     * @param symmetricKey - encryption key to use
     * @throws EncryptionException - when anything goes wrong during encryption process
     */
    void encryptStream(InputStream in, OutputStream out, String symmetricKey) throws EncryptionException;

    /**
     * Decrypts data stream with symmetric key
     * @param in - encrypted data
     * @param out - where to write decrypted data
     * @param symmetricKey - encryption key to use
     * @throws EncryptionException - if something goes wrong during decryption process
     */
    void decryptStream(InputStream in, OutputStream out, String symmetricKey) throws EncryptionException;

    /**
     * 101 - Register
     *
     * @param registrationRequest - registration data
     * @throws TrustCaseClientException
     */
    void register(RegistrationRequest registrationRequest) throws TrustCaseClientException;

    /**
     * 102 - Create Account. Shortcut method for {@link #createAccount(boolean, String, String, DeviceType)}. Calls
     * @see #init(TrustCaseCredentials, KeyAccessor)
     */
    TrustCaseCredentials createAccount(String pushId, String smsCode, DeviceType deviceType)
            throws TrustCaseClientException;

    /**
     * 102 - Create Account. Tries to create an account for this client using the specified pushId, smsCode, deviceType
     * and KeyPair (private&public key). The key pair is resolved the following way:
     * <ul>
     * <li>if createNewPair=true: a new key pair is created independent of the current credentials.
     * <li>if createNewPair=false: the KeyPair is used that this client was initialized with. If the client was not
     * initialized with TrustcaseCredentials an IllegalArgumentException is thrown.
     * </ul>
     * <p/>
     * If successful the credentials for the account creation process are returned. These are also stored internally and
     * can be resolved later via {@link #getCredentials()}.
     *
     * @param createNewKeyPair
     *            if true registration is performed with a new random KeyPair (private/public key). If false, the
     *            KeyPair is used that this client was initialized with. If false and the client was not initialized
     *            with TrustCaseCredentials an IllegalArgumentException is thrown.
     * @param pushId - push ID, might be null
     * @param smsCode - code sent via SMS as a response to registration request
     * @param deviceType - type of the client device
     * @return the credentials for this account creation process
     * @throws TrustCaseClientException
     * @throws IllegalArgumentException
     *             thrown if createNewKeyPair=false and the client was not initialized with TrustcaseCredentials
     * @see #init(TrustCaseCredentials, KeyAccessor)
     */
    TrustCaseCredentials createAccount(boolean createNewKeyPair, String pushId, String smsCode, DeviceType deviceType)
            throws IllegalArgumentException, TrustCaseClientException;

    /**
     * 103 - Update registration info
     * 
     * @throws TrustCaseClientException
     */
    void updateRegistrationInfo(String pushId) throws TrustCaseClientException;

    /**
     * 104 - Get account details
     *
     * @param jid
     *            - JID of account
     * @throws TrustCaseClientException
     */
    Identity getAccountDetails(String jid) throws TrustCaseClientException;

    /**
     * 105 - Create web login
     * @param loginId - unique login ID
     * @param token - secret token which needs to be typed to login in the web (not sent to server, for encryption only!)
     * @throws TrustCaseClientException
     */
    WebLoginResponse createWebLogin(String loginId, String token) throws TrustCaseClientException;

    /**
     * 106 - perform web login
     * @param loginId - unique login ID
     * @param token - secret token for decryption of login data
     * @return - decrypted credentials, if all went well
     * @throws TrustCaseClientException
     */
    TrustCaseCredentials performWebLogin(String loginId, String token) throws TrustCaseClientException;

    /**
     * 201 - Update own profile
     * 
     * @param profileWrapper
     *            - unencrypted profile message (containing name and picture) can be null (No profile set, or user wants
     *            to delete profile)
     * @throws TrustCaseClientException
     */
    void updateProfile(ProfileWrapper profileWrapper) throws TrustCaseClientException;

    /**
     * 202 - Retrieve profiles of other users
     * 
     * @param jids
     *            - uses whose profiles I would like to see
     * @return - list of jids, keys and profiles of valid users
     * @throws TrustCaseClientException
     */
    List<ProfileWrapper> retrieveProfiles(List<String> jids) throws TrustCaseClientException;

    /**
     * 206 - Sync Address Book
     *
     * @param phoneHashes - list of phone hashes to check
     * @return - contact data of those phone numbers which are registered TrustCase clients
     * @throws TrustCaseClientException
     */
    List<Contact> syncAddressBook(List<String> phoneHashes) throws TrustCaseClientException;

    /**
     * 301 - Get Room List
     *
     * @return - list of active rooms for calling client
     * @throws TrustCaseClientException
     */
    List<RoomListResponseItem> listRooms() throws TrustCaseClientException;

    /**
     * 302 - Get Room Metadata
     *
     * @param roomID - ID of the room
     * @return - decrypted metadata of the room
     * @throws EncryptionException
     * @throws TrustCaseClientException
     */
    RoomMetadataEnvelope getRoomMetadata(String roomID) throws EncryptionException, TrustCaseClientException;

    /**
     * 303 - Get Room Participants
     *
     * @param roomID - ID of the room
     * @return - list of room's participants
     * @throws TrustCaseClientException
     */
    List<Identity> getRoomParticipants(String roomID) throws TrustCaseClientException;

    /**
     * 304 - Open Room.
     *
     * @param metadata
     *            - room metadata consisting of room name, icon, key and list of administrators
     * @param participants
     *            List of participant identities to invite into the room. Note, that the caller is always participating
     *            in the room and needs not to be included in the participant list. The provided list stays untouched by
     *            the implementation (internally cloned).
     * @return - response with room ID and password
     * @throws EncryptionException
     * @throws TrustCaseClientException
     */
    OpenRoomResponse openRoom(RoomMetadataMessage metadata, List<Identity> participants) throws EncryptionException,
            TrustCaseClientException;

    /**
     * Convenience variant of "openRoom" which opens room without having to construct metadata message first.
     *
     * @param name
     *            - room name
     * @param icon
     *            - room icon
     * @param roomKey
     *            - symmetric key used for encryption of messages
     * @param participants
     *            List of participant identities to invite into the room. Note, that the caller is always participating
     *            in the room and needs not to be included in the participant list. The provided list stays untouched by
     *            the implementation (internally cloned).
     * @param administratorJIDs
     *            - JIDs of participants with administrator role
     * @return - response with room ID and password
     * @throws EncryptionException
     * @throws TrustCaseClientException
     */
    OpenRoomResponse openRoom(String name, RoomIcon icon, String roomKey, List<Identity> participants,
                              List<String> administratorJIDs) throws EncryptionException, TrustCaseClientException;

    /**
     * 305 - Close Room
     *
     * @param roomID - ID of the room
     * @param password - password of the room as returned by "open room"
     * @throws TrustCaseClientException
     */
    void closeRoom(String roomID, String password) throws TrustCaseClientException;

    /**
     * 306 - Invite Persons
     */
    void invitePersons(String roomID, String password, List<String> inviteJIDs) throws TrustCaseClientException;

    /**
     * 307 - Kick Persons
     *
     * @param roomID - ID of the room
     * @param password - password of the room as returned by "open room"
     * @param kickJIDs - list of participants to remove from the room
     * @throws TrustCaseClientException
     */
    void kickPersons(String roomID, String password, List<String> kickJIDs) throws TrustCaseClientException;

    /**
     * 308 - Leave Room
     *
     * @param roomID - ID of the room
     * @throws TrustCaseClientException
     */
    void leaveRoom(String roomID) throws TrustCaseClientException;

    /**
     * 309 - Update Room Metadata
     *
     * @param roomID - ID of the room
     * @param password - password of the room as returned by "open room"
     * @param metadataMessage - new metadata
     * @throws EncryptionException
     * @throws TrustCaseClientException
     */
    void updateRoomMetadata(String roomID, String password, RoomMetadataMessage metadataMessage)
            throws EncryptionException, TrustCaseClientException;

    /**
     * Note: Do not use: Deprecated and will be removed in the future<br>
     * 310 - Delete Room
     */
    @Deprecated
    void deleteRoom(String roomID) throws TrustCaseClientException;

    /**
     * 311 - Change room password
     *
     * @param roomID - ID of the room
     * @param password - password of the room as returned by "open room"
     * @param newPassword - new password of the room
     * @throws TrustCaseClientException
     */
    void changeRoomPassword(String roomID, String password, String newPassword) throws TrustCaseClientException;

    /**
     * 401 - Fetch messages
     *
     * @param roomID - ID of the room
     * @return - list of all messages in given room, if possible. Otherwise exception is thrown.
     * @throws TrustCaseClientException
     * @throws EncryptionException
     */
    List<MessageEnvelope> fetchMessages(String roomID) throws TrustCaseClientException, EncryptionException;

    /**
     * 402 - Sync Messages
     *
     * @param lastSyncDate - will return all messages later then this date. If null, ALL messages will be returned.
     *                     It is recommended that the client would pass timestamp of the last received message here.
     * @return - list of messages in all active rooms of the client later than given date.
     * @throws TrustCaseClientException
     * @throws EncryptionException
     */
    List<MessageEnvelope> syncMessages(Date lastSyncDate) throws TrustCaseClientException, EncryptionException;

    /**
     * 403 - Post Message. Shortcut method for {@link #postRoomMessage(String, Message, List, EncryptionType)}, with an
     * empty list of recipientJIDs, meaning message will be encrypted for all room recipients. By default,
     * public/private key encryption will be used.
     *
     * @throws TrustCaseClientException
     * @throws EncryptionException
     */
    MessageId postRoomMessage(String roomID, Message message) throws TrustCaseClientException, EncryptionException;

    /**
     * 403 - Post Message. Shortcut method for {@link #postRoomMessage(String, Message, List, EncryptionType)}, with an
     * empty list of recipientJIDs, meaning message will be encrypted for all room recipients.
     *
     * @param roomID - ID of the room
     * @param message - message to post
     * @param encryptionType - message encryption type to use
     *
     * @return - response with message ID
     * @throws TrustCaseClientException
     * @throws EncryptionException
     */
    MessageId postRoomMessage(String roomID, Message message, EncryptionType encryptionType)
            throws TrustCaseClientException, EncryptionException;

    /**
     * 403 - Post Message
     *
     * @param roomID - ID of the room
     * @param message - message to post
     * @param recipientJIDs
     *            - list of intended recipients. If not null, message will be encrypted for these recipients only.
     * @param encryptionType
     *            - message encryption type to use
     * @return - ID of created message
     * @throws TrustCaseClientException
     * @throws EncryptionException
     */
    MessageId postRoomMessage(String roomID, Message message, List<String> recipientJIDs, EncryptionType encryptionType)
            throws TrustCaseClientException, EncryptionException;

    /**
     * Posts private message to given recipient. Opens new private room for that client if one doesn't exist yet.
     *
     * @param recipient - recipient JID
     * @param message - message to post
     * @param roomName - metadata of newly opened room. If room already exists, metadata will be updated.
     * @return - response with file and room ID
     * @throws TrustCaseClientException
     * @throws PhoneNumberNotRegisteredException - if the specified recipient's phone number is not a
     *         registered phone number in the system.
     * @throws EncryptionException
     */
    PrivateMessageId postPrivateMessage(Identity recipient, String roomName, Message message)
            throws TrustCaseClientException, EncryptionException;

    /**
     * Shorthand version of above method for sending private message to phone number directly
     */
    PrivateMessageId postPrivateMessage(String recipientPhoneNumber, String roomName, Message message)
            throws PhoneNumberNotRegisteredException, TrustCaseClientException, EncryptionException;

    /**
     * 501 - Upload file. Just a shortcut for {@link #uploadFile(String, File, ProgressCallback)} using no
     * {@link ProgressCallback}
     *
     * @param roomId - room ID
     * @param file - file tp upload
     * @return - response with file ID
     * @throws TrustCaseClientException
     */
    UploadFileResponse uploadFile(String roomId, File file) throws TrustCaseClientException, IOException;

    /**
     * 501 - Upload file
     *
     * @param roomId - room ID
     * @param file - file tp upload
     * @param progressCallback - callback which will be called to notify about upload progress
     * @return - response with file ID
     * @throws TrustCaseClientException
     */
    UploadFileResponse uploadFile(String roomId, File file, ProgressCallback progressCallback)
            throws TrustCaseClientException, IOException;

    /**
     * 501 - Upload file without room.
     */
    UploadFileResponse uploadFile(File file, ProgressCallback progressCallback) throws TrustCaseClientException,
            IOException;

    /**
     * 501 - Upload file - variant without room and progress callback.
     */
    UploadFileResponse uploadFile(File file) throws TrustCaseClientException, IOException;

    /**
     * 502 - Download file. Just a shortcut for {@link #downloadFile(String, String, OutputStream, ProgressCallback)}
     * using no {@link ProgressCallback}
     *
     * @param roomId - room ID
     * @param fileId - file to download
     * @param file - stream to write downloaded contents to
     */
    void downloadFile(String roomId, String fileId, OutputStream file) throws TrustCaseClientException, IOException;

    /**
     * 502 - Download file
     *
     * @param roomId - room ID
     * @param fileId - file to download
     * @param file - stream to write downloaded contents to
     * @param progressCallback - callback which will be called to notify about download progress
     */
    void downloadFile(String roomId, String fileId, OutputStream file, ProgressCallback progressCallback)
            throws TrustCaseClientException, IOException;

    /**
     * 502 - Download file, variant without room
     */
    void downloadFile(String fileId, OutputStream file, ProgressCallback progressCallback)
            throws TrustCaseClientException, IOException;

    /**
     * 502 - Download file, variant without room and progress callback
     */
    void downloadFile(String fileId, OutputStream file) throws TrustCaseClientException, IOException;

    /**
     * Download file from external source. Just a shortcut for
     * {@link #downloadExternalFile(String, OutputStream, ProgressCallback)} using no {@link ProgressCallback}
     *
     * @param downloadLink - full http/https link to download file from
     * @param file - stream to write downloaded contents to
     */
    void downloadExternalFile(String downloadLink, OutputStream file) throws TrustCaseClientException, IOException;

    /**
     * Download file from external source
     */
    void downloadExternalFile(String downloadLink, OutputStream file, ProgressCallback progressCallback)
            throws TrustCaseClientException, IOException;

    /**
     * Download file from external source
     *
     * @param cancellationToken
     *            token that allows calling code to cancel the download in progress. it is caller's responsibility to
     *            "clean up" after the download (eg. delete and close the leftover, partial file).
     * @param authorize
     *            include authorization data? required when downloading files from our backend
     */
    void downloadExternalFile(String downloadLink, OutputStream file, ProgressCallback progressCallback,
                              CancellationToken cancellationToken, boolean authorize)
            throws TrustCaseClientException, IOException;

    /**
     * 503 - Delete File
     * 
     * @param fileId - file to be deleted
     */
    void deleteFile(String fileId) throws TrustCaseClientException;

    /**
     * 503 - Delete File
     * 
     * @param roomId - ID of room if file has been posted to specific room
     * @param fileId - file to be deleted
     */
    void deleteFile(String roomId, String fileId) throws TrustCaseClientException;

    /**
     * 601 - Add contacts to trust circle.
     * Only user with "trustbroker" role may call this method
     *
     * @param items
     *            - Contacts to add to trust circle
     * @return - response with counters how many items were added or updated.
     */
    TrustCircleResponse addTrustCircleItems(List<TrustCircleWrapper> items) throws TrustCaseClientException;

    /**
     * 602 - Remove contacts from trust circle.
     * Only user with "trustbroker" role may call this method
     *
     * @param jids - jids of contacts to remove from trust circle
     * @return - response with counters how many items were actually removed
     */
    TrustCircleResponse removeTrustCircleItems(List<String> jids) throws TrustCaseClientException;

    /**
     * 603 - Query trust circle.
     * Only users which are in trust circle themselves may call this method.
     *
     * @param trustBrokerJid - JID of trustbroker whose trust circle is queried
     * @param syncJids - "sync" contacts with these JIDs will be returned in addition to all "push" contacts
     * @return - all "push" contacts in this trust circle plus "sync" contacts which the caller explicitly requests via
     * "syncJids" parameter
     */
    List<TrustCircleWrapper> queryTrustCircle(String trustBrokerJid, List<String> syncJids)
            throws TrustCaseClientException;

    /**
     * 701 - Issue location request to given JIDs
     *
     * @param jids
     *            - JIDs of users to send the location request to
     * @param timeout
     *            - number of seconds the sender will wait for responses. The recipients will do their best to send a
     *            response in this time, even if that means using imprecise/older location data.
     * @return - server generated ID of request
     */
    LocationRequestId sendLocationRequest(List<String> jids, int timeout) throws TrustCaseClientException;
}
