package com.amdocs.usp.userms.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EncryptUtils {

    public static String encryptString(String value, String salt) {
        try {
            KeySpec keySpec = new PBEKeySpec(value.toCharArray(), hexToByte(salt), 310000, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] encoded = factory.generateSecret(keySpec).getEncoded();
            return bytesToHex(encoded);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("M=encryptString, message=Failed to encrypt, returning as is.");
            return value;
        }
    }

    protected static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    protected static byte[] hexToByte(String hexString){
        byte[] bytes = new byte[hexString.length() / 2];
        for(int i = 0 ; i < hexString.length() / 2; i++) {
            int firstDigit = (byte) Character.digit(hexString.charAt(i*2), 16);
            int secondDigit = (byte) Character.digit(hexString.charAt(i*2+1), 16);
            bytes[i] = (byte) ((firstDigit << 4) | (secondDigit));
        }
        return bytes;
    }

    public static String getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return bytesToHex(salt);
    }

}
