package com.ebchinatech.itop.web.punch.domain;

import com.ebchinatech.kylin.framework.aspectj.lang.annotation.Excel;
import com.ebchinatech.kylin.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 打卡日历对象 punch_calendars
 *
 * @author hjw
 * @date 2022-06-19
 */
@TableName("punch_calendars")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class PunchCalendars extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日期 */
    private String workDate;

    /** 工作日/休息日/法定假日 */
    @Excel(name = "工作日/休息日/法定假日")
    private String shiftDefName;

    /** 1工作日 2休息日 3法定假日 */
    @Excel(name = "1工作日 2休息日 3法定假日")
    private String dateType;

    public String year;

}
