package com.trustcase.client.api.messages;

import com.trustcase.client.api.responses.MetadataResponse;

/**
 * Wrapper which encapsulates server answer to "get room metadata" call.
 * If answer could be decrypted, "metadataMessage" will contain decrypted metadata
 */
public class RoomMetadataEnvelope {

    private RoomMetadataMessage metadataMessage;
    private MetadataResponse response;

    public RoomMetadataEnvelope(RoomMetadataMessage metadataMessage, MetadataResponse response){
        this.metadataMessage = metadataMessage;
        this.response = response;
    }

    public RoomMetadataMessage getMetadataMessage() {
        return metadataMessage;
    }

    public MetadataResponse getResponse() {
        return response;
    }
}
