package com.amdocs.usp.userms.view.converter;

import com.amdocs.usp.userms.model.entity.UserEntity;
import com.amdocs.usp.userms.view.object.PageVO;
import com.amdocs.usp.userms.view.object.UserVO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserConverter implements IConverter<UserEntity, UserVO> {
    @Override
    public List<UserVO> toVO(List<UserEntity> all) {
        return all.stream().map(this::fromEntityToVO).collect(Collectors.toList());
    }

    @Override
    public PageVO<UserVO> toVO(PageVO<UserEntity> page) {

        return new PageVO<UserVO>(page.getContents().stream().map(this::fromEntityToVO).collect(Collectors.toList()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public UserVO fromEntityToVO(UserEntity userEntity) {
        Objects.requireNonNull(userEntity, "user entity can't be null");
        UserVO vo = new UserVO();
        vo.setId(userEntity.getId());
        vo.setUsername(userEntity.getUsername());
        vo.setFirstName(userEntity.getFirstName());
        vo.setLastName(userEntity.getLastName());
        vo.setEmail(userEntity.getEmail());

        return vo;
    }

    @Override
    public UserEntity toEntity(UserVO data) {
        Objects.requireNonNull(data, "user vo can't be null");

        UserEntity entity = new UserEntity();
        entity.setUsername(data.getUsername());
        entity.setId(data.getId());
        entity.setFirstName(data.getFirstName());
        entity.setLastName(data.getFirstName());
        entity.setEmail(data.getEmail());

        return entity;
    }
}
