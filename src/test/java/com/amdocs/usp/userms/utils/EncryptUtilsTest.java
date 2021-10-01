package com.amdocs.usp.userms.utils;

import static org.junit.jupiter.params.provider.Arguments.of;

import java.util.Locale;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class EncryptUtilsTest {

    @ParameterizedTest()
    @MethodSource("hexString")
    @DisplayName("Testing byte array / hex string  conversions")
    public void hexTest(byte[] bytes, String expectedHexString){
        String hexString = EncryptUtils.bytesToHex(bytes);
        Assertions.assertArrayEquals(bytes, EncryptUtils.hexToByte(hexString));
        Assertions.assertEquals(expectedHexString, hexString);
    }

    private static Stream<Arguments> hexString(){
        return Stream.of(of(new byte[] {0, 30, 15, 12, 2, 1}, "001e0f0c0201"),
                of(new byte[] {1, 2, 3, 4, 5, 6}, "010203040506"),
                of(new byte[] {10, 11, 12, 13, 14, 15}, "0a0b0c0d0e0f"),
                of(new byte[] {127, 126, 125, 124, 123, 122}, "7f7e7d7c7b7a"));
    }

    @ParameterizedTest()
    @MethodSource("encryptedData")
    @DisplayName("Testing encrypt value")
    public void encryptTest(String value, String salt, String expected) {
        String valueEncrypted = EncryptUtils.encryptString(value, salt);
        Assertions.assertEquals(expected.toLowerCase(Locale.ROOT), valueEncrypted);
    }

    private static Stream<Arguments> encryptedData() {
        return Stream.of(of("123456", "15956459ccfcea3fe0cf35d4b92358a2", "700b711f19a98eadf0c18896cf62dcb345d08cfe5467aaeef9818e9ac4d64bf6"),
                of("test", "15956459ccfcea3fe0cf35d4b92358a2", "e9a9e5a3117a4af5d3c0b7d0df2ebe993bae53916ddd54a3a4c38288722a0009"),
                of("1", "15956459ccfcea3fe0cf35d4b92358a2", "47280908c2a4c27615dda10c924de264123e81effbeb82b97b1922f96c94a924"));
    }


}
