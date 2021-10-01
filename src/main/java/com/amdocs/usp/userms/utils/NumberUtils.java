package com.amdocs.usp.userms.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberUtils {

    public static int parseIntOrZero(String value) {

        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            log.error("value={}, message=Failed to parse integer.", value);
            return 0;
        }
    }

}
