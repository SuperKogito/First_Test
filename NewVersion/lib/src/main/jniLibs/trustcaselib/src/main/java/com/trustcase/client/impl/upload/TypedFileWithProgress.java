package com.trustcase.client.impl.upload;

import com.trustcase.client.api.upload.ProgressCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import retrofit.mime.TypedFile;

/**
 * Counts bytes transferred to OutputStream and updates Transfer with that value.
 */
public class TypedFileWithProgress extends TypedFile {
    private static final int BUFFER_SIZE = 4096;
    private ProgressCallback progressCallback;

    /**
     * Constructs a new typed file.
     * @throws NullPointerException if file or mimeType is null
     */
    public TypedFileWithProgress(String mimeType, File file, ProgressCallback progressCallback) {
        super(mimeType, file);
        this.progressCallback = progressCallback;
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        long transferredBytes = 0;
        try (FileInputStream in = new FileInputStream(file())) {
            int read;
            while ((read = in.read(buffer)) != -1) {
                transferredBytes += read;
                out.write(buffer, 0, read);
                //transfer.setTransferredBytes(transferredBytes);
                if (progressCallback != null) {
                    progressCallback.onProgress(transferredBytes);
                }
            }
        }
    }
}