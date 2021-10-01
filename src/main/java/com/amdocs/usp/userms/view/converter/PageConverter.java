package com.amdocs.usp.userms.view.converter;

import java.util.Objects;
import java.util.function.Function;

import com.amdocs.usp.userms.view.object.PageVO;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageConverter {

    public static <VO> PageVO<VO> toVO(Page<VO> page) {
        Objects.requireNonNull(page, "page can't be null");

        return new PageVO<VO>(page.getContent(), page.getTotalElements(), page.getTotalPages());
    }

    public static <ENTITY, VO> PageVO<VO> toVO(Page<ENTITY> page, Function<ENTITY, VO> mapping) {
        Objects.requireNonNull(page, "all data can't be null");
        Objects.requireNonNull(mapping, "mapping can't be null");

        return new PageVO<VO>(page.map(mapping::apply).getContent(), page.getTotalElements(), page.getTotalPages());

    }
}
