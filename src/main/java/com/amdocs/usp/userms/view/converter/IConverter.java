package com.amdocs.usp.userms.view.converter;

import com.amdocs.usp.userms.view.object.PageVO;

import java.util.List;

public interface IConverter<ENTITY, VO> {
    List<VO> toVO (List<ENTITY> all);
    PageVO<VO> toVO (PageVO<ENTITY> page);

    VO fromEntityToVO(ENTITY entity);
    ENTITY toEntity(VO data);
}
