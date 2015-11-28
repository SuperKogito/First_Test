package com.trustcase.client.util;

import com.trustcase.client.TrustCaseClient;
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
import java.util.Objects;

/**
 * Implementation of {@link TrustCaseClient}, that delegates all calls to a delegate client.
 * 
 * @author Gunther Klein
 */
public class DelegateTrustCaseClient implements TrustCaseClient {

    private TrustCaseClient delegate;

    public DelegateTrustCaseClient(TrustCaseClient delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public void setProperty(String key, Object value) {
        delegate.setProperty(key, value);
    }

    @Override
    public Object getProperty(String key) {
        return delegate.getProperty(key);
    }

    @Override
    public Object clearProperty(String key) {
        return delegate.clearProperty(key);
    }

    @Override
    public String getApiEndpoint() {
        return delegate.getApiEndpoint();
    }

    @Override
    public String getJid() {
        return delegate.getJid();
    }

    @Override
    public KeyPair getKeyPair() {
        return delegate.getKeyPair();
    }

    @Override
    public String generateSymmetricKey() {
        return delegate.generateSymmetricKey();
    }

    @Override
    public <T> String encrypt(T data, EncryptionType encryptionType, String roomId, Collection<String> recipients) throws EncryptionException {
        return delegate.encrypt(data, encryptionType, roomId, recipients);
    }

    @Override
    public <T> T decrypt(String data, String roomID, Class<T> clazz) throws EncryptionException, JSONException {
        return delegate.decrypt(data, roomID, clazz);
    }

    @Override
    public <T> String encryptSymmetrically(T data, String symmetricKey) throws EncryptionException {
        return delegate.encryptSymmetrically(data, symmetricKey);
    }

    @Override
    public <T> T decryptSymmetrically(String data, String symmetricKey, Class<T> clazz) throws EncryptionException, JSONException {
        return delegate.decryptSymmetrically(data, symmetricKey, clazz);
    }

    @Override
    public void encryptStream(InputStream in, OutputStream out, String symmetricKey) throws EncryptionException {
        delegate.encryptStream(in, out, symmetricKey);
    }

    @Override
    public void decryptStream(InputStream in, OutputStream out, String symmetricKey) throws EncryptionException {
        delegate.decryptStream(in, out, symmetricKey);
    }

    @Override
    public TrustCaseCredentials getCredentials() {
        return delegate.getCredentials();
    }

    @Override
    public void init(TrustCaseCredentials credentials, KeyAccessor keyAccessor) {
        delegate.init(credentials, keyAccessor);
    }

    @Override
    public void register(RegistrationRequest registrationRequest) throws TrustCaseClientException {
        delegate.register(registrationRequest);
    }

    @Override
    public TrustCaseCredentials createAccount(String pushId, String smsCode, DeviceType deviceType)
            throws TrustCaseClientException {
        return delegate.createAccount(pushId, smsCode, deviceType);
    }

    @Override
    public TrustCaseCredentials createAccount(boolean createNewKeyPair, String pushId, String smsCode,
                                              DeviceType deviceType) throws IllegalArgumentException,
            TrustCaseClientException {
        return delegate.createAccount(createNewKeyPair, pushId, smsCode, deviceType);
    }

    @Override
    public void updateRegistrationInfo(String pushId) throws TrustCaseClientException {
        delegate.updateRegistrationInfo(pushId);
    }

    @Override
    public Identity getAccountDetails(String jid) throws TrustCaseClientException {
        return delegate.getAccountDetails(jid);
    }

    @Override
    public WebLoginResponse createWebLogin(String loginId, String token) throws TrustCaseClientException {
        return delegate.createWebLogin(loginId, token);
    }

    @Override
    public TrustCaseCredentials performWebLogin(String loginId, String token) throws TrustCaseClientException {
        return delegate.performWebLogin(loginId, token);
    }

    @Override
    public void updateProfile(ProfileWrapper profileWrapper) throws TrustCaseClientException {
        delegate.updateProfile(profileWrapper);
    }

    @Override
    public List<ProfileWrapper> retrieveProfiles(List<String> jids) throws TrustCaseClientException {
        return delegate.retrieveProfiles(jids);
    }

    @Override
    public List<Contact> syncAddressBook(List<String> phones) throws TrustCaseClientException {
        return delegate.syncAddressBook(phones);
    }

    @Override
    public List<RoomListResponseItem> listRooms() throws TrustCaseClientException {
        return delegate.listRooms();
    }

    @Override
    public RoomMetadataEnvelope getRoomMetadata(String roomID) throws EncryptionException, TrustCaseClientException {
        return delegate.getRoomMetadata(roomID);
    }

    @Override
    public List<Identity> getRoomParticipants(String roomID) throws TrustCaseClientException {
        return delegate.getRoomParticipants(roomID);
    }

    @Override
    public OpenRoomResponse openRoom(RoomMetadataMessage metadata, List<Identity> participants)
            throws EncryptionException, TrustCaseClientException {
        return delegate.openRoom(metadata, participants);
    }

    @Override
    public OpenRoomResponse openRoom(String name, RoomIcon icon, String roomKey, List<Identity> participants,
                                     List<String> administratorJIDs) throws EncryptionException,
            TrustCaseClientException {
        return delegate.openRoom(name, icon, roomKey, participants, administratorJIDs);
    }

    @Override
    public void closeRoom(String roomID, String password) throws TrustCaseClientException {
        delegate.closeRoom(roomID, password);
    }

    @Override
    public void invitePersons(String roomID, String password, List<String> inviteJIDs) throws TrustCaseClientException {
        delegate.invitePersons(roomID, password, inviteJIDs);
    }

    @Override
    public void kickPersons(String roomID, String password, List<String> kickJIDs) throws TrustCaseClientException {
        delegate.kickPersons(roomID, password, kickJIDs);
    }

    @Override
    public void leaveRoom(String roomID) throws TrustCaseClientException {
        delegate.leaveRoom(roomID);
    }

    @Override
    public void updateRoomMetadata(String roomID, String password, RoomMetadataMessage metadataMessage)
            throws EncryptionException, TrustCaseClientException {
        delegate.updateRoomMetadata(roomID, password, metadataMessage);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void deleteRoom(String roomID) throws TrustCaseClientException {
        delegate.deleteRoom(roomID);
    }

    @Override
    public void changeRoomPassword(String roomID, String password, String newPassword) throws TrustCaseClientException {
        delegate.changeRoomPassword(roomID, password, newPassword);
    }

    @Override
    public List<MessageEnvelope> fetchMessages(String roomID) throws TrustCaseClientException, EncryptionException {
        return delegate.fetchMessages(roomID);
    }

    @Override
    public List<MessageEnvelope> syncMessages(Date lastSyncDate) throws TrustCaseClientException, EncryptionException {
        return delegate.syncMessages(lastSyncDate);
    }

    @Override
    public MessageId postRoomMessage(String roomID, Message message) throws TrustCaseClientException,
            EncryptionException {
        return delegate.postRoomMessage(roomID, message);
    }

    @Override
    public MessageId postRoomMessage(String roomID, Message message, EncryptionType encryptionType)
            throws TrustCaseClientException, EncryptionException {
        return delegate.postRoomMessage(roomID, message, encryptionType);
    }

    @Override
    public MessageId postRoomMessage(String roomID, Message message, List<String> recipientJIDs,
                                     EncryptionType encryptionType) throws TrustCaseClientException,
            EncryptionException {
        return delegate.postRoomMessage(roomID, message, recipientJIDs, encryptionType);
    }

    @Override
    public PrivateMessageId postPrivateMessage(Identity recipient, String roomName, Message message)
            throws TrustCaseClientException, EncryptionException {
        return delegate.postPrivateMessage(recipient, roomName, message);
    }

    @Override
    public PrivateMessageId postPrivateMessage(String recipientPhoneNumber, String roomName, Message message)
            throws PhoneNumberNotRegisteredException, TrustCaseClientException, EncryptionException {
        return delegate.postPrivateMessage(recipientPhoneNumber, roomName, message);
    }

    @Override
    public UploadFileResponse uploadFile(String roomId, File file) throws TrustCaseClientException, IOException {
        return delegate.uploadFile(roomId, file);
    }

    @Override
    public UploadFileResponse uploadFile(String roomId, File file, ProgressCallback progressCallback)
            throws TrustCaseClientException, IOException {
        return delegate.uploadFile(roomId, file, progressCallback);
    }

    @Override
    public UploadFileResponse uploadFile(File file, ProgressCallback progressCallback) throws TrustCaseClientException,
            IOException {
        return delegate.uploadFile(file, progressCallback);
    }

    @Override
    public UploadFileResponse uploadFile(File file) throws TrustCaseClientException, IOException {
        return delegate.uploadFile(file);
    }

    @Override
    public void downloadFile(String roomId, String fileId, OutputStream file) throws TrustCaseClientException,
            IOException {
        delegate.downloadFile(roomId, fileId, file);
    }

    @Override
    public void downloadFile(String roomId, String fileId, OutputStream file, ProgressCallback progressCallback)
            throws TrustCaseClientException, IOException {
        delegate.downloadFile(roomId, fileId, file, progressCallback);
    }

    @Override
    public void downloadFile(String fileId, OutputStream file, ProgressCallback progressCallback)
            throws TrustCaseClientException, IOException {
        delegate.downloadFile(fileId, file, progressCallback);
    }

    @Override
    public void downloadFile(String fileId, OutputStream file) throws TrustCaseClientException, IOException {
        delegate.downloadFile(fileId, file);
    }

    @Override
    public void downloadExternalFile(String downloadLink, OutputStream file) throws TrustCaseClientException,
            IOException {
        delegate.downloadExternalFile(downloadLink, file);
    }

    @Override
    public void downloadExternalFile(String downloadLink, OutputStream file, ProgressCallback progressCallback)
            throws TrustCaseClientException, IOException {
        delegate.downloadExternalFile(downloadLink, file, progressCallback);
    }

    @Override
    public void downloadExternalFile(String downloadLink, OutputStream file, ProgressCallback progressCallback,
                                     CancellationToken cancellationToken, boolean authorize)
            throws TrustCaseClientException, IOException {
        delegate.downloadExternalFile(downloadLink, file, progressCallback, cancellationToken, authorize);
    }

    @Override
    public void deleteFile(String fileId) throws TrustCaseClientException {
        delegate.deleteFile(fileId);
    }

    @Override
    public void deleteFile(String roomId, String fileId) throws TrustCaseClientException {
        delegate.deleteFile(roomId, fileId);
    }

    @Override
    public LocationRequestId sendLocationRequest(List<String> jids, int timeout) throws TrustCaseClientException {
        return delegate.sendLocationRequest(jids, timeout);
    }

    @Override
    public TrustCircleResponse addTrustCircleItems(List<TrustCircleWrapper> items) throws TrustCaseClientException {
        return delegate.addTrustCircleItems(items);
    }

    @Override
    public TrustCircleResponse removeTrustCircleItems(List<String> jids) throws TrustCaseClientException {
        return delegate.removeTrustCircleItems(jids);
    }

    @Override
    public List<TrustCircleWrapper> queryTrustCircle(String trustBrokerJid, List<String> syncJids) throws TrustCaseClientException {
        return delegate.queryTrustCircle(trustBrokerJid, syncJids);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DelegateTrustCaseClient that = (DelegateTrustCaseClient) o;

        return delegate.equals(that.delegate);

    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public String toString() {
        return "DelegateTrustCaseClient [" + delegate.toString() + "]";
    }
}
