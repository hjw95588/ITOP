package com.ebchinatech.itop.api.domain;

import lombok.Data;

/**
 * 工作日对象 working_calendar
 * 
 * @author yxb
 * @date 2021-06-21
 */
@Data
public class WorkingCalendarExternal
{
    private String shiftName;
    private String dateType;

    /** 日，具体的日期,Unix时间戳 */
    private Long shiftDate;
    /** 班次代码，D001代表工作日，D002代表休息日 */
    private String shiftCode;

    private Long startTime;
    private Long endTime;


}
