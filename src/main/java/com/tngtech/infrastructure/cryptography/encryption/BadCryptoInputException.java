package com.tngtech.infrastructure.cryptography.encryption;

import com.tngtech.infrastructure.exception.DomainException;

/**
 * This exception is thrown when the input to a crypto operation was invalid,
 * e.g. the decrypted buffer specified incorrect padding.
 */
public class BadCryptoInputException extends DomainException {

    public BadCryptoInputException(Throwable cause) {
        super(cause);
    }
}
