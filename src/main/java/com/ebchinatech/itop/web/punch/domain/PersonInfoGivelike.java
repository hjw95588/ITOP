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
 * 点赞人信息对象 person_info_givelike
 *
 * @author hjw
 * @date 2022-07-09
 */
@TableName("person_info_givelike")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class PersonInfoGivelike extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @Excel(name = "工号")
    private String userId;

    @Excel(name = "名称")
    private String userName;

    /** 用户名 */
    @Excel(name = "sessionId")
    private String sessionId;

    private String isNeedGive;//默认需要参与点赞1

}
