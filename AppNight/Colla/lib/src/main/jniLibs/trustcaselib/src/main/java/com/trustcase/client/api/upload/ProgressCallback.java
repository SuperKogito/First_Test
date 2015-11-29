package com.trustcase.client.api.upload;

/**
 * Callback to monitor upload/download progress
 */
public interface ProgressCallback {
    void onProgress(long bytesProcessed);
}
