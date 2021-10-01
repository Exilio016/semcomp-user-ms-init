package com.amdocs.usp.userms.business.service;

import static com.amdocs.usp.userms.business.service.UserService.QUALIFIER;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.amdocs.usp.userms.exceptions.DataNotFoundException;
import com.amdocs.usp.userms.model.entity.UserEntity;
import com.amdocs.usp.userms.model.repository.UserRepository;
import com.amdocs.usp.userms.utils.EncryptUtils;
import com.amdocs.usp.userms.utils.NumberUtils;
import com.amdocs.usp.userms.view.converter.IConverter;
import com.amdocs.usp.userms.view.converter.UserConverter;
import com.amdocs.usp.userms.view.object.PageVO;
import com.amdocs.usp.userms.view.object.UserVO;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service(QUALIFIER)
public class UserService {

    public static final String QUALIFIER = "UserService";

    private final UserRepository repository;
    private final IConverter<UserEntity, UserVO> converter;

    @Autowired
    public UserService(UserRepository userRepository) {
        super();
        this.repository = userRepository;
        this.converter = UserConverter.getInstance();
    }

    @Transactional
    public List<UserVO> getAll() {
        log.info("M=getAll, message=Getting a list of {}", getEntitySimpleName());
        List<UserEntity> all = repository.findAll();
        return converter.toVO(all);
    }

    @Transactional
    public PageVO<UserVO> getAll(Pageable pageRequest) {
        log.info("M=getAll, message=Getting a list of {}", getEntitySimpleName());
        Page<UserEntity> all = repository.findAll(pageRequest);
        return converter.toVO(all);
    }

    @Transactional
    public UserVO get(int id) {
        log.info("M=get, id={}, message=Get a specific {}", id, getEntitySimpleName());
        Optional<UserEntity> data = repository.findById(id);
        return data.map(converter::fromEntityToVO).orElse(null);
    }

    public UserVO save(UserVO data) {
        log.info("M=save, data={}, message=Saving a {}", data, getEntitySimpleName());
        UserEntity saved = repository.save(encryptPassword(converter.toEntity(data)));
        return converter.fromEntityToVO(saved);
    }

    public UserVO update(UserVO data) {
        log.info("M=update, data={}, message=Updating a {}", data, getEntitySimpleName());
        Optional<UserEntity> entity = repository.findById(data.getId());
        UserEntity update = entity.orElseThrow(() -> new DataNotFoundException(getEntitySimpleName() + " not found"));
        updateData(data, update);
        return converter.fromEntityToVO(repository.save(update));
    }

    public void delete(int id) {
        log.info("M=delete, id={}, message=Delete a specific {}", id, getEntitySimpleName());
        Optional<UserEntity> data = repository.findById(id);
        repository.delete(data.orElseThrow(() -> new DataNotFoundException(getEntitySimpleName() + " not found")));
    }

    public UserVO getByName(String name) {
        log.info("M=getByName, username={}, message=Get a specific User", name);
        Optional<UserEntity> data = repository.findByUsername(name);
        return data.map(converter::fromEntityToVO).orElse(null);
    }

    public UserVO login(String username, String password){
        Optional<UserEntity> data = repository.findByUsername(username);
        if(data.isPresent()){
            UserEntity entity = data.get();
            String passwordHash = EncryptUtils.encryptString(password, entity.getSalt());
            if(passwordHash.equals(entity.getPassword())){
                return converter.fromEntityToVO(entity);
            }
        }
        return null;
    }

    public UserVO findUserByIdOrUsername(String value) {
        log.info("M=findUserByIdOrUsername, value={}, message=Searching for a specific user", value);
        UserVO userFounded = null;
        int id = NumberUtils.parseIntOrZero(value);
        if (id > 0) {
            userFounded = get(id);
        }
        if (Objects.isNull(userFounded) && StringUtils.isNotEmpty(value)) {
            userFounded = getByName(value);
        }
        if (!Objects.isNull(userFounded)) {
            return userFounded;
        }
        throw new DataNotFoundException("Not found user with value = " + value);
    }

    protected String getEntitySimpleName() {
        return UserEntity.class.getSimpleName();
    }

    protected void updateData(UserVO vo, UserEntity entity) {
        entity.setUsername(vo.getUsername());
        entity.setPassword(StringUtils.isNotEmpty(vo.getPassword()) ? vo.getPassword() : entity.getPassword());
        entity.setFirstName(vo.getFirstName());
        entity.setLastName(vo.getLastName());
        entity.setEmail(vo.getEmail());
        encryptPassword(entity);
    }

    private UserEntity encryptPassword(UserEntity entity) {
        String salt = EncryptUtils.getSalt();
        String encryptedPass = EncryptUtils.encryptString(entity.getPassword(), salt);
        entity.setPassword(encryptedPass);
        entity.setSalt(salt);
        return entity;
    }

}
