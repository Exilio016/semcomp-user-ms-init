package com.amdocs.usp.userms.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptUtilsTest {

    @Test
    public void hexTest() {
        byte[] bytes = new byte[] {1, 2, 3, 4, 5, 6};
        String hexString = "010203040506";
        String generatedHex = EncryptUtils.bytesToHex(bytes);
        byte[] generatedBytes = EncryptUtils.hexToByte(hexString);

        Assertions.assertEquals(hexString, generatedHex);
        Assertions.assertArrayEquals(bytes, generatedBytes);
    }

    @Test
    public void encryptTest(){
        String password = "123456";
        String salt = "15956459ccfcea3fe0cf35d4b92358a2";
        String hash = "700b711f19a98eadf0c18896cf62dcb345d08cfe5467aaeef9818e9ac4d64bf6";
        Assertions.assertEquals(hash, EncryptUtils.encryptString(password, salt));
    }

}