package com.amdocs.usp.userms.business.service;

import com.amdocs.usp.userms.model.entity.UserEntity;
import com.amdocs.usp.userms.model.repository.UserRepository;
import com.amdocs.usp.userms.utils.EncryptUtils;
import com.amdocs.usp.userms.view.converter.IConverter;
import com.amdocs.usp.userms.view.converter.UserConverter;
import com.amdocs.usp.userms.view.object.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    private final UserRepository repository;
    private final IConverter<UserEntity, UserVO> converter;

    @Autowired
    public UserService(UserRepository userRepository) {
        super();
        this.repository = userRepository;
        this.converter = UserConverter.getInstance();
    }

    public List<UserVO> getAll() {
        log.info("M=getAll, message=Getting a list of {}", getEntitySimpleName());
        List<UserEntity> all = repository.findAll();
        return converter.toVO(all);
    }

    public UserVO get(int id) {
        log.info("M=get, id={}, message=Get a specifc {}", id, getEntitySimpleName());
        Optional<UserEntity> data = repository.findById(id);
        return data.map(converter::fromEntityToVO).orElse(null);
    }

    public UserVO save(UserVO data) {
        log.info("M=save, data={}, message=Saving a {}", data, getEntitySimpleName());
        UserEntity saved = repository.save(encryptPassword(converter.toEntity(data)));
        return converter.fromEntityToVO(saved);
    }

    public UserVO login(String username, String password) {
        Optional<UserEntity> data = repository.findByUsername(username);
        if (data.isPresent()) {
            UserEntity entity = data.get();
            String passwordHash = EncryptUtils.encryptString(password, entity.getSalt());
            if (passwordHash.equals(entity.getPassword())) {
                return converter.fromEntityToVO(entity);
            }
        }
        return null;
    }

    private UserEntity encryptPassword(UserEntity entity) {
        String salt = EncryptUtils.getSalt();
        String encryptedPass = EncryptUtils.encryptString(entity.getPassword(), salt);
        entity.setPassword(encryptedPass);
        entity.setSalt(salt);
        return entity;
    }

    private String getEntitySimpleName() {
        return UserEntity.class.getSimpleName();
    }
}
