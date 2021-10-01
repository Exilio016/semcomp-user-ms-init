package com.amdocs.usp.userms.view.converter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.amdocs.usp.userms.model.entity.UserEntity;
import com.amdocs.usp.userms.view.object.PageVO;
import com.amdocs.usp.userms.view.object.UserVO;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConverter implements IConverter<UserEntity, UserVO> {

    private static UserConverter INSTANCE;

    public static UserConverter getInstance() {
        if (Objects.isNull(INSTANCE)) {
            INSTANCE = new UserConverter();
        }
        return INSTANCE;
    }

    @Override
    public List<UserVO> toVO(List<UserEntity> all) {
        return Optional.ofNullable(all).map(p -> p.stream().map(this::fromEntityToVO).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public PageVO<UserVO> toVO(Page<UserEntity> page) {
        return PageConverter.toVO(page, this::fromEntityToVO);
    }

    @Override
    public UserVO fromEntityToVO(UserEntity entity) {
        Objects.requireNonNull(entity, "user entity can't be null");

        UserVO vo = new UserVO();
        vo.setId(entity.getId());
        vo.setUsername(entity.getUsername());
        vo.setFirstName(entity.getFirstName());
        vo.setLastName(entity.getLastName());
        vo.setEmail(entity.getEmail());

        return vo;
    }

    @Override
    public UserEntity toEntity(UserVO vo) {
        Objects.requireNonNull(vo, "user vo can't be null");

        UserEntity entity = new UserEntity();
        entity.setId(vo.getId());
        entity.setUsername(vo.getUsername());
        entity.setPassword(vo.getPassword());
        entity.setFirstName(vo.getFirstName());
        entity.setLastName(vo.getLastName());
        entity.setEmail(vo.getEmail());

        return entity;
    }
}
