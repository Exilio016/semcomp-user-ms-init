package com.amdocs.usp.userms.service;

import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;
import java.util.stream.Stream;

import com.amdocs.usp.userms.business.service.UserService;
import com.amdocs.usp.userms.model.entity.UserEntity;
import com.amdocs.usp.userms.model.repository.UserRepository;
import com.amdocs.usp.userms.view.object.UserVO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

public class UserServiceTest {

    private UserRepository repository;
    private UserService service;

    @BeforeEach
    public void before() {
        repository = Mockito.mock(UserRepository.class);
        Mockito.when(repository.findByUsername(any())).thenReturn(defaultUser(1));
        Mockito.when(repository.findById(any())).thenReturn(defaultUser(2));
        service = new UserService(repository);
    }

    @ParameterizedTest()
    @MethodSource("findTestData")
    @DisplayName("Testing number cast")
    public void findTest(String value, String expected) {
        UserVO returnVO = service.findUserByIdOrUsername(value);
        Assertions.assertEquals(expected, returnVO.getUsername());
    }

    private static Stream<Arguments> findTestData() {
        return Stream.of(of("test", "test1"), of("2", "test2"));
    }

    private Optional<UserEntity> defaultUser(int id) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        String value = "test" + id;
        userEntity.setUsername(value);
        userEntity.setPassword(value);
        userEntity.setFirstName(value);
        userEntity.setLastName(value);
        userEntity.setEmail(value);
        return Optional.of(userEntity);
    }

}
