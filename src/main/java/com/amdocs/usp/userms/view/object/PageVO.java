package com.amdocs.usp.userms.view.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class PageVO<E> extends BasicVO{
    private List<E> contents;
    private Long totalElements;
    private int totalPages;
}
