package com.trustcase.client.api.exceptions;


/**
 * Exception indicates error in encryption/decryption process.
 *
 * @author audrius
 *
 */
public class EncryptionException extends TrustCaseClientException {

    private static final long serialVersionUID = 1L;

    public EncryptionException() {

    }

    public EncryptionException( final String message ) {

        super( message );
    }

    public EncryptionException( final Throwable exception ) {

        super( exception.getMessage(), exception );
    }

    public EncryptionException( final String message, final Throwable exception ) {

        super( message, exception );
    }
}
