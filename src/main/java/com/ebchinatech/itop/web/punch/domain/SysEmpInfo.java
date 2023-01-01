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

import java.util.List;
import java.util.Map;

/**
 * 人力信息对象 sys_emp_info
 *
 * @author hjw
 * @date 2022-09-12
 */
@TableName("sys_emp_info")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class SysEmpInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long empid;

    /** 工号 */
    @Excel(name = "工号")
    private String workno;

    /** 姓名 */
    @Excel(name = "姓名")
    private String empName;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    /** 部门名称 */
    @Excel(name = "部门名称")
    private String firtorgname;

    /** 所属团队 */
    @Excel(name = "所属团队")
    private String teamAffiliated;

    /** 用工类型 */
    @Excel(name = "用工类型")
    private String employType;

    /** 首次参工时间 */
    @Excel(name = "首次参工时间")
    private String firstWorkDate;

    /** 入职日期 */
    @Excel(name = "入职日期")
    private String hireDate;
    @Excel(name = "离职日期")
    private String terminationDate;

    /** 手机 */
    @Excel(name = "手机")
    private String mobile;

    /** 工资卡账户名 */
    @Excel(name = "工资卡账户名")
    private String EXT_COL_994;

    /** 工资卡卡号 */
    @Excel(name = "工资卡卡号")
    private String EXT_COL_951;

    /** 应届生 */
    @Excel(name = "应届生")
    private String EXT_COL_999;

    /** 在职状态 */
    @Excel(name = "在职状态")
    private String EXT_COL_974;

    /** 职级（公司） */
    @Excel(name = "职级")
    private String cascadingJobGrade;

    /** 基本信息1 */
    private String other1;

    /** 基本信息2 */
    private String other2;

    /** 出生日期 */
    @Excel(name = "出生日期")
    private String birthDate;

    /** 民族 */
    @Excel(name = "民族")
    private String nation;

    /** 证件号码 */
    @Excel(name = "证件号码")
    private String idCardNo;

    /** 政治面貌 */
    @Excel(name = "政治面貌")
    private String politics;

    /** 入党时间 */
    @Excel(name = "入党时间")
    private String EXT_COL_998;

    /** 国籍 */
    @Excel(name = "国籍")
    private String nationality;

    /** 户口所在地 */
    @Excel(name = "户口所在地")
    private String hujiAddress;

    /** 出生地 */
    @Excel(name = "出生地")
    private String EXT_COL_934;

    /** 籍贯 */
    @Excel(name = "籍贯")
    private String hujiCity;

    /** 现居住地 */
    @Excel(name = "现居住地")
    private String nowAddr;

    /** 最高学历 */
    @Excel(name = "最高学历")
    private String lastEduDegree;

    /** 最高学位 */
    @Excel(name = "最高学位")
    private String highDegree;

    /** 特长 */
    @Excel(name = "特长")
    private String EXT_COL_935;

    /** 婚姻 */
    @Excel(name = "婚姻")
    private String marriage;

    /** 生育状况 */
    @Excel(name = "生育状况")
    private String fertilityStatus;

    /** 公司邮箱 */
    @Excel(name = "公司邮箱")
    private String corpEmail;

    private String photoUrl;

   private List<Map> updateGrade;//升级记录

    private String isUpgrade;

}
