package com.amdocs.usp.userms.model.repository;

import com.amdocs.usp.userms.model.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Collections;
import java.util.Optional;

@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    UserRepository repository;

    @Test
    public void afterSaveShouldFindUser(){
        String salt = String.join("", Collections.nCopies(32, "0"));
        String password = String.join("", Collections.nCopies(64, "0"));
        final UserEntity entity = new UserEntity();
        entity.setUsername("test1");
        entity.setPassword(password);
        entity.setSalt(salt);
        entity.setFirstName("Test");
        entity.setLastName("Entity");
        entity.setEmail("test@test.com");

        entityManager.persistAndFlush(entity);
        Optional<UserEntity> userEntityOptional = repository.findByUsername("test1");

        Assertions.assertTrue(userEntityOptional.isPresent());
        Assertions.assertEquals("test1" ,userEntityOptional.get().getUsername());
        Assertions.assertEquals(password ,userEntityOptional.get().getPassword());
        Assertions.assertEquals(salt ,userEntityOptional.get().getSalt());
        Assertions.assertEquals("Test", userEntityOptional.get().getFirstName());
        Assertions.assertEquals("Entity", userEntityOptional.get().getLastName());
        Assertions.assertEquals("test@test.com", userEntityOptional.get().getEmail());
    }
}
