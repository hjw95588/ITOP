package com.ebchinatech.itop.api.domain;

import lombok.Data;

import java.util.List;
@Data
public class WorkingCalendarResponse extends WorkingCalendarExternal {
    private int status;
    private String message;
    private List<WorkingCalendarExternal> data;
}
