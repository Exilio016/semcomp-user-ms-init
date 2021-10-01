package com.amdocs.usp.userms.view.object;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DefaultErrorVO extends BasicVO {

    private Integer status;
    private String error;
    private String message;
    private String path;
}
