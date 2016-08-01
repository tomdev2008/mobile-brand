
package com.xiu.mobile.simple.common.util;

import java.io.UnsupportedEncodingException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * A static utility class that can encrypt and decrypt text.
 *
 * <p>This class is useful if you have simple needs and wish to use the DESede
 * encryption cipher. More sophisticated requirements will need to use the
 * Java crypto libraries directly.
 *
 * @author Alan Stewart
 * @author Ben Alex
 */
public final class EncryptionUtils {

    /**
     * This is a static class that should not be instantiated.
     */
    private EncryptionUtils() {}

    /**
     * Converts a String into a byte array using UTF-8, falling back to the
     * platform's default character set if UTF-8 fails.
     *
     * @param input the input (required)
     * @return a byte array representation of the input string
     */
    public static byte[] stringToByteArray(String input) {
        try {
            return input.getBytes("UTF-8");
        } catch (UnsupportedEncodingException fallbackToDefault) {
            return input.getBytes();
        }
    }

    /**
     * Converts a byte array into a String using UTF-8, falling back to the
     * platform's default character set if UTF-8 fails.
     *
     * @param byteArray the byte array to convert (required)
     * @return a string representation of the byte array
     */
    public static String byteArrayToString(byte[] byteArray) {
        try {
            return new String(byteArray, "UTF8");
        } catch (final UnsupportedEncodingException e) {
            return new String(byteArray);
        }
    }

    private static byte[] cipher(String key, byte[] passedBytes, int cipherMode) throws Exception {
        try {
            final KeySpec keySpec = new DESedeKeySpec(stringToByteArray(key));
            final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            final SecretKey secretKey = keyFactory.generateSecret(keySpec);
            cipher.init(cipherMode, secretKey);
            return cipher.doFinal(passedBytes);
        } catch (final Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Encrypts the inputString using the key.
     *
     * @param key at least 24 character long key (required)
     * @param inputString the string to encrypt (required)
     * @return the encrypted version of the inputString
     * @throws Exception 
     */
    public static String encrypt(String key, String inputString) throws Exception {
        final byte[] cipherText = cipher(key, stringToByteArray(inputString), Cipher.ENCRYPT_MODE);
        return byteArrayToString(Base64.encodeBase64(cipherText));
    }

    /**
     * Encrypts the inputBytes using the key.
     *
     * @param key at least 24 character long key (required)
     * @param inputBytes the bytes to encrypt (required)
     * @return the encrypted version of the inputBytes
     * @throws Exception 
     */
    public static byte[] encrypt(String key, byte[] inputBytes) throws Exception {
        return Base64.encodeBase64(cipher(key, inputBytes, Cipher.ENCRYPT_MODE));
    }

    /**
     * Decrypts the inputString using the key.
     *
     * @param key the key used to originally encrypt the string (required)
     * @param inputString the encrypted string (required)
     * @return the decrypted version of inputString
     * @throws EncryptionException in the event of an encryption failure
     */
    public static String decrypt(String key, String inputString) throws Exception {
        final byte[] cipherText = cipher(key, Base64.decodeBase64(stringToByteArray(inputString)), Cipher.DECRYPT_MODE);
        return byteArrayToString(cipherText);
    }

    /**
     * Decrypts the inputBytes using the key.
     *
     * @param key the key used to originally encrypt the string (required)
     * @param inputBytes the encrypted bytes (required)
     * @return the decrypted version of inputBytes
     * @throws EncryptionException in the event of an encryption failure
     */
    public static byte[] decrypt(String key, byte[] inputBytes) throws Exception {
        return cipher(key, Base64.decodeBase64(inputBytes), Cipher.DECRYPT_MODE);
    }

    
    public static class EncryptionException extends Exception {
        private static final long serialVersionUID = 1L;

        public EncryptionException(String message, Throwable t) {
            super(message, t);
        }

        public EncryptionException(String message) {
            super(message);
        }
    }
}
