package com.amdocs.usp.userms.view.object;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DefaultErrorVO extends BasicVO {

    private static final long serialVersionUID = -2431578485512376961L;
    private Integer status;
    private String error;
    private String message;
    private String path;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
