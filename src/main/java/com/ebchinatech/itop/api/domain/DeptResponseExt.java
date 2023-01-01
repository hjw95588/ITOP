package com.ebchinatech.itop.api.domain;

import com.ebchinatech.kylin.web.domain.DeptExternal;
import com.ebchinatech.kylin.web.domain.DeptResponse;
import lombok.Data;

import java.util.List;
@Data
public class DeptResponseExt extends DeptResponse {

    private List<DeptExternal> result;

    private String msg;
}
