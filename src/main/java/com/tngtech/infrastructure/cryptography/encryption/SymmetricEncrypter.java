package com.tngtech.infrastructure.cryptography.encryption;

/**
 * Interface for symmetric cryptographic operations.
 */
public interface SymmetricEncrypter {
    String encrypt(String toEncrypt);
    String decrypt(String toDecrypt);

    byte[] encrypt(byte[] toEncrypt);
    byte[] decrypt(byte[] toDecrypt);
}
