package com.amdocs.usp.userms.view.object;

import com.amdocs.usp.userms.utils.ValidationConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserVO extends BasicVO {
    private int id;

    @NotNull(message = ValidationConstants.FIELD_REQUIRED)
    private String username;
    private String password;

    @NotNull(message = ValidationConstants.FIELD_REQUIRED)
    private String firstName;
    private String lastName;
    private String email;
}
