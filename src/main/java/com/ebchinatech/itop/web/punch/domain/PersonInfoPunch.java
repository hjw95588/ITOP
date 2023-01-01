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
 * 打卡人信息对象 person_info_punch
 *
 * @author hjw
 * @date 2022-06-16
 */
@TableName("person_info_punch")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class PersonInfoPunch extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 登录密码 */
    @Excel(name = "登录密码")
    private String password;

    /** 定位id */
    private Long positionId;

    @Excel(name = "定位名称")
    private String positionName;

    /** 0不需要打卡，1需要打卡 */
    @Excel(name = "0=不需要打卡，1=需要打卡")
    private String isPunch;

    private Integer sleepTime;//休息秒数

}
