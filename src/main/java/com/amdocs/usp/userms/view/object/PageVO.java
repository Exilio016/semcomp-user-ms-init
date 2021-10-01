package com.amdocs.usp.userms.view.object;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PageVO<E> extends BasicVO {

    private static final long serialVersionUID = 372287022056382011L;

    private List<E> contents;
    private Long totalElements;
    private int totalPages;

}
