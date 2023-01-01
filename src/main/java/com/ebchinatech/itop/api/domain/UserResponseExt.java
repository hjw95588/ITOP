package com.ebchinatech.itop.api.domain;//


import com.ebchinatech.kylin.web.domain.UserExternal;
import com.ebchinatech.kylin.web.domain.UserResponse;
import lombok.Data;

import java.util.List;
@Data
public class UserResponseExt extends UserResponse {
    private List<UserExternal> result;

    private String msg;

}
