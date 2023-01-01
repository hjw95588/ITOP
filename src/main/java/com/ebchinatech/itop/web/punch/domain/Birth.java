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
 * 点赞的所有人对象 birth
 *
 * @author hjw
 * @date 2022-10-13
 */
@TableName("birth")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class Birth extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 人员id */
    private String birUserId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String birName;

    /** 机构类型 */
    @Excel(name = "机构类型")
    private String deptType;

    /** 机构id */
    @Excel(name = "机构id")
    private String deptId;

    /** 机构名称 */
    @Excel(name = "机构名称")
    private String deptName;

    /** 月 */
    @Excel(name = "月")
    private String birMonth;

    /** 月天 */
    @Excel(name = "月天")
    private String birDay;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String avatar;

    /** $column.columnComment */
    private String result;

    /** $column.columnComment */
    private String monthResult;

    /** $column.columnComment */
    private String starFlag;

    private String startDate;
    private String endDate;

    private String flag;

}
