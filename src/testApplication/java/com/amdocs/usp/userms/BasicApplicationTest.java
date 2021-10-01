package com.amdocs.usp.userms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("testApplication")
@SpringBootTest(classes = { UserMsApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicApplicationTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Test
    public void selfTest() {
        Assertions.assertTrue(true);
    }
}
