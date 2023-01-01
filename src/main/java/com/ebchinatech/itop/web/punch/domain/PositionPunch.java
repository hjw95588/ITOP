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
 * 打卡需要的常用位置对象 position_punch
 *
 * @author hjw
 * @date 2022-06-16
 */
@TableName("position_punch")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class PositionPunch extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 定位简称 */
    @Excel(name = "定位简称")
    private String siteName;

    /** 经度 */
    @Excel(name = "经度")
    private String longitude;

    /** 纬度 */
    @Excel(name = "纬度")
    private String latitude;

    /** 地址 */
    @Excel(name = "地址")
    private String addr;

}
