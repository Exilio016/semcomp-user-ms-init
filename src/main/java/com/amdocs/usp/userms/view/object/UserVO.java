package com.amdocs.usp.userms.view.object;

import javax.validation.constraints.NotNull;

import com.amdocs.usp.userms.utils.ValidationConstants;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserVO extends BasicVO {

    private static final long serialVersionUID = 2740160883523115639L;

    private int id;

    @NotNull(message = ValidationConstants.FIELD_REQUIRED)
    private String username;

    private String password;

    @NotNull(message = ValidationConstants.FIELD_REQUIRED)
    private String firstName;

    private String lastName;

    private String email;


}
