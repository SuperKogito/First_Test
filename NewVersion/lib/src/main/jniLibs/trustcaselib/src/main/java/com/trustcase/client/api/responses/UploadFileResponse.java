package com.trustcase.client.api.responses;

/**
 * Response from server indicating a successful file upload (501 - upload file).
 */
public class UploadFileResponse {

    public String file_id;

    public UploadFileResponse(String global_file_id) {
        this.file_id = global_file_id;
    }

    public String getGlobalFileId() {
        return file_id;
    }

    @Override
    public String toString() {
        return String.format("UploadFileResponse{file_id='%s'}", file_id);
    }

}