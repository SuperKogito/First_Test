package com.trustcase.client.api.enums;

/**
 * Supported encryption types
 */
public enum EncryptionType {
    /**
     * Message type 0: unencrypted message
     */
    UNENCRYPTED,

    /**
     * Message type 1: message encrypted with symmetric key which is known to all parties in advance
     */
    SYMMETRIC_KEY,

    /**
     * Message type 2: message encrypted with public/private keys, public key of sender is included in the message
     */
    PUBLIC_KEY,

    /**
     * Message type 3: message encrypted with public/private keys, public key of sender is not known
     */
    NO_KEY
}
