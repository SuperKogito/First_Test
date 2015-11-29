package com.trustcase.client.impl;

import com.trustcase.client.api.exceptions.TrustCaseClientException;
import com.trustcase.client.api.messages.MessageEnvelope;
import com.trustcase.client.api.messages.MessageWrapper;
import com.trustcase.client.api.requests.CreateAccountRequest;
import com.trustcase.client.api.requests.OpenRoomRequest;
import com.trustcase.client.api.requests.RegistrationRequest;
import com.trustcase.client.api.responses.*;
import com.trustcase.client.impl.request.*;

import java.util.List;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Methods to communicate with TrustCase REST API using retrofit.
 *
 * @author Audrius Stonkus
 * @author Gunther Klein
 */
public interface RoomsRestAdapter {
    @POST("/accounts/register")
    Response register(@Body RegistrationRequest registration) throws TrustCaseClientException;

    @POST("/accounts")
    CreateAccountResponse createAccount(@Body CreateAccountRequest request) throws TrustCaseClientException;

    @PUT("/accounts")
    Response updateRegistration(@Body UpdateAccountRequest request) throws TrustCaseClientException;

    @GET("/accounts/{jid}")
    Identity getAccount(@Path("jid") String jid) throws TrustCaseClientException;

    @PUT("/accounts/weblogin")
    WebLoginResponse createWebLogin(@Body WebLogin webLogin) throws TrustCaseClientException;

    @POST("/accounts/weblogin")
    WebLogin performWebLogin(@Body WebLogin request) throws TrustCaseClientException;

    @GET("/rooms")
    List<RoomListResponseItem> listRooms() throws TrustCaseClientException;

    @POST("/addressbook/sync")
    List<Contact> syncAddressBook(@Body List<String> phoneHashes) throws TrustCaseClientException;

    @POST("/rooms") // 302
    OpenRoomResponse openRoom(@Body OpenRoomRequest request) throws TrustCaseClientException;

    @POST("/rooms/{jid}/private") // 312
    OpenRoomResponse openPrivateRoom(@Path("jid") String recipientJID, @Body MetadataRequest metadata) throws TrustCaseClientException;

    @PUT("/rooms/{room_id}/metadata") // 308
    Response updateMetadata(@Path("room_id") String roomId, @Body MetadataRequest metadata) throws TrustCaseClientException;

    @GET("/rooms/{room_id}/metadata") // 309
    MetadataResponse getMetadata(@Path("room_id") String roomId) throws TrustCaseClientException;

    @GET("/rooms/{room_id}/participants")
    List<Identity> getParticipants(@Path("room_id") String roomId) throws TrustCaseClientException;

    @POST("/messages/{room_id}/{node}")
    MessageId sendMessage(@Path("room_id") String roomId, @Path("node") String node, @Body MessageWrapper message) throws TrustCaseClientException;

    @GET("/messages")
    List<MessageEnvelope> syncMessages(@Query("since") String since) throws TrustCaseClientException;

    @GET("/messages/{room_id}/{node}")
    List<MessageEnvelope> fetchMessages(@Path("room_id") String roomId, @Path("node") String node) throws TrustCaseClientException;

    @POST("/rooms/{room_id}/leave")
    Response leaveRoom(@Path("room_id") String roomId) throws TrustCaseClientException;

    @POST("/rooms/{room_id}/close")
    Response closeRoom(@Path("room_id") String roomId, @Body CloseRoomRequest request) throws TrustCaseClientException;

    @POST("/rooms/{room_id}/kick")
    Response kickPersons(@Path("room_id") String roomId, @Body KickPersonsRequest request) throws TrustCaseClientException;

    @POST("/rooms/{room_id}/invite")
    Response invitePersons(@Path("room_id") String roomId, @Body InvitePersonsRequest request) throws TrustCaseClientException;

    @POST("/rooms/{room_id}/password")
    Response changeRoomPassword(@Path("room_id") String roomId, @Body ChangeRoomPasswordRequest request) throws TrustCaseClientException;

    @Multipart
    @POST("/files/{room_id}")
    UploadFileResponse uploadFile(@Path("room_id") String roomId, @Part("file") TypedFile file) throws TrustCaseClientException;

    @GET("/files/{room_id}/{file_id}")
    Response downloadFile(@Path ("room_id") String roomId, @Path ("file_id") String file_id) throws TrustCaseClientException;

    @Multipart
    @POST("/files")
    UploadFileResponse uploadFile(@Part("file") TypedFile file) throws TrustCaseClientException;

    @GET("/files/{file_id}")
    Response downloadFile(@Path ("file_id") String file_id) throws TrustCaseClientException;

    @DELETE("/files/{file_id}")
    Response deleteFile(@Path ("file_id") String file_id) throws TrustCaseClientException;
    
    @DELETE("/files/{room_id}/{file_id}")
    Response deleteFile(@Path ("room_id") String roomId, @Path ("file_id") String file_id) throws TrustCaseClientException;

    @PUT("/addressbook/profile")
    Response updateProfile(@Body UpdateProfileRequest request) throws TrustCaseClientException;

    @POST("/addressbook/profile")
    List<ProfileResponseItem> retrieveProfiles(@Body List<String> jids) throws TrustCaseClientException;

    @POST("/location")
    LocationRequestId sendLocationRequest(@Body LocationRequest locationRequest) throws TrustCaseClientException;

    @POST("/trustcircle/add")
    TrustCircleResponse addTrustCircleItems(@Body List<TrustCircleItem> items) throws TrustCaseClientException;

    @POST("/trustcircle/remove")
    TrustCircleResponse removeTrustCircleItems(@Body List<String> jids) throws TrustCaseClientException;

	@POST("/trustcircle/{jid}/query")
	List<TrustCircleItem> queryTrustCircle(@Path("jid") String trustBrokerJid, @Body List<String> syncJids)
			throws TrustCaseClientException;
}