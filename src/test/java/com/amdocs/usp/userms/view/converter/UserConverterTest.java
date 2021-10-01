package com.amdocs.usp.userms.view.converter;

import com.amdocs.usp.userms.model.entity.UserEntity;
import com.amdocs.usp.userms.view.object.UserVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserConverterTest {

    UserConverter converter = UserConverter.getInstance();

    @Test
    public void convertEntityToVO(){
        UserEntity entity = new UserEntity();
        entity.setUsername("test1");
        entity.setPassword("password");
        entity.setSalt("salt");
        entity.setFirstName("Test");
        entity.setLastName("Entity");
        entity.setEmail("test@entity.com");

        UserVO vo = converter.fromEntityToVO(entity);
        Assertions.assertEquals(entity.getId(), vo.getId());
        Assertions.assertEquals(entity.getUsername(), vo.getUsername());
        Assertions.assertNull(vo.getPassword());
        Assertions.assertEquals(entity.getFirstName(), vo.getFirstName());
        Assertions.assertEquals(entity.getLastName(), vo.getLastName());
        Assertions.assertEquals(entity.getEmail(), vo.getEmail());
    }

    @Test
    public void convertVOToEntity(){
        UserVO user = new UserVO();
        user.setId(2);
        user.setUsername("test2");
        user.setEmail("test@vo.com");
        user.setPassword("123456");
        user.setFirstName("Test");
        user.setLastName("VO");

        UserEntity entity = converter.toEntity(user);
        Assertions.assertEquals(user.getId(), entity.getId());
        Assertions.assertEquals(user.getUsername(), entity.getUsername());
        Assertions.assertEquals(user.getPassword(), entity.getPassword());
        Assertions.assertNull(entity.getSalt());
        Assertions.assertEquals(user.getFirstName(), entity.getFirstName());
        Assertions.assertEquals(user.getLastName(), entity.getLastName());
        Assertions.assertEquals(user.getEmail(), entity.getEmail());
    }
}
