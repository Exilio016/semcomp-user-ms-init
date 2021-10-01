package com.amdocs.usp.userms.controllers;

import static org.junit.jupiter.params.provider.Arguments.of;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.stream.Stream;

import com.amdocs.usp.userms.BasicApplicationTest;
import com.amdocs.usp.userms.model.entity.UserEntity;
import com.amdocs.usp.userms.model.repository.UserRepository;
import com.amdocs.usp.userms.view.object.DefaultErrorVO;
import com.amdocs.usp.userms.view.object.PageVO;
import com.amdocs.usp.userms.view.object.UserVO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

@Sql(value = { "/sqls/clear.sql", "/sqls/users.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserControllerTest extends BasicApplicationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetUserList() {
        ResponseEntity<PageVO<UserVO>> response = restTemplate.exchange("/user", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageVO<UserVO>>() {

                });

        Assertions.assertNotNull(response);
        System.out.println(response);
        PageVO<UserVO> data = response.getBody();

        assert data != null;
        Assertions.assertEquals(4, data.getContents().size());

        Assertions.assertTrue(data.getContents().stream().anyMatch(p -> "test2".equalsIgnoreCase(p.getUsername())));
        Assertions.assertTrue(data.getContents().stream().anyMatch(p -> "test3".equalsIgnoreCase(p.getUsername())));
    }

    @ParameterizedTest()
    @MethodSource("findUserParameter")
    @DisplayName("Testing find user")
    public void testFindUser(String search, String email, boolean found) {
        ResponseEntity<Object> response = restTemplate.exchange("/user/find/" + search, HttpMethod.GET, null,
                Object.class);

        Assertions.assertNotNull(response);

        LinkedHashMap<String, String> data = (LinkedHashMap<String, String>) response.getBody();

        Assertions.assertNotNull(data);
        if (found) {
            Assertions.assertEquals(email, data.get("email"));
        } else {
            Assertions.assertEquals("Dados não encontrados", data.get("error"));
        }

    }

    @Test
    public void testGetUserNotFound() {
        ResponseEntity<HttpStatus> response = restTemplate.exchange("/user/99", HttpMethod.GET, null, HttpStatus.class);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void testSaveUser() {
        UserVO vo = createUser("Test");
        HttpEntity<?> request = new HttpEntity<>(vo);

        ResponseEntity<UserVO> response = restTemplate.exchange("/user", HttpMethod.POST, request, UserVO.class);

        Assertions.assertNotNull(response);

        UserVO responseData = response.getBody();

        Assertions.assertNotNull(responseData);

        Optional<UserEntity> entity = userRepository.findById(responseData.getId());

        Assertions.assertNotNull(entity);
        Assertions.assertNotNull(entity.get());

    }

    @Test
    public void testUpdateUser() {
        UserVO vo = createUser("Test");
        HttpEntity<?> request = new HttpEntity<>(vo);

        ResponseEntity<UserVO> response = restTemplate.exchange("/user", HttpMethod.POST, request, UserVO.class);

        Assertions.assertNotNull(response);

        UserVO responseData = response.getBody();

        Assertions.assertNotNull(responseData);

        responseData.setUsername("TestUpdate");
        HttpEntity<?> requestUpdate = new HttpEntity<>(responseData);

        ResponseEntity<UserVO> responseUpdate = restTemplate.exchange("/user", HttpMethod.PUT, requestUpdate,
                UserVO.class);

        UserVO responseDataUpdate = responseUpdate.getBody();

        Optional<UserEntity> entity = userRepository.findById(responseDataUpdate.getId());

        Assertions.assertNotNull(entity);
        Assertions.assertNotNull(entity.get());
        Assertions.assertEquals("TestUpdate", entity.get().getUsername());

    }

    @Test
    public void testDeleteUser() {
        int id = saveUser();
        ResponseEntity<Integer> response = restTemplate.exchange("/user/" + id, HttpMethod.DELETE, null, Integer.class);

        Assertions.assertNotNull(response);

        Integer data = response.getBody();

        Assertions.assertNotNull(data);

        Assertions.assertEquals(id, data.intValue());

    }

    @Test
    public void testDeleteUserNotFound() {
        ResponseEntity<DefaultErrorVO> response = restTemplate.exchange("/user/99", HttpMethod.DELETE, null,
                DefaultErrorVO.class);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        DefaultErrorVO error = response.getBody();

        Assertions.assertNotNull(error);

        Assertions.assertEquals("Dados não encontrados", error.getError());

    }

    private int saveUser() {
        UserVO vo = createUser("Test10");
        HttpEntity<?> request = new HttpEntity<>(vo);

        ResponseEntity<UserVO> response = restTemplate.exchange("/user", HttpMethod.POST, request, UserVO.class);

        return response.getBody().getId();
    }

    private UserVO createUser(String name) {
        UserVO vo = new UserVO();
        vo.setUsername(name);
        vo.setPassword(name + "pass");
        vo.setFirstName(name + "first");
        vo.setLastName(name + "last");
        vo.setEmail(name + "@test.com");
        return vo;
    }

    private static Stream<Arguments> findUserParameter() {
        return Stream.of(of("test1", "email1@test.com", true), of("test3", "email3@test.com", true),
                of("notFound", "", false));
    }
}
