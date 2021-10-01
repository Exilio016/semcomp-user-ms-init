package com.amdocs.usp.userms.view.converter;

import java.util.List;
import java.util.function.Function;

import com.amdocs.usp.userms.view.object.PageVO;

import org.springframework.data.domain.Page;

public interface IConverter<ENTITY, VO> {

    List<VO> toVO(List<ENTITY> all);

    PageVO<VO> toVO(Page<ENTITY> page);

    VO fromEntityToVO(ENTITY entity);

    ENTITY toEntity(VO data);
}
