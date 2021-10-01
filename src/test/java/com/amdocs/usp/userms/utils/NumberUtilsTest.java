package com.amdocs.usp.userms.utils;

import static org.junit.jupiter.params.provider.Arguments.of;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class NumberUtilsTest {

    @ParameterizedTest()
    @MethodSource("numberData")
    @DisplayName("Testing number cast")
    public void numberCastTest(String value, int expected) {
        int number = NumberUtils.parseIntOrZero(value);
        Assertions.assertEquals(expected, number);
    }

    private static Stream<Arguments> numberData() {
        return Stream.of(of("123456", 123456), of("test", 0), of("test1", 0), of("a", 0), of("0", 0), of("1", 1));
    }

}
