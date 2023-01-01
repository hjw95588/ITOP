package com.ebchinatech.itop.web.punch.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ebchinatech.kylin.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 人力信息对象 sys_emp_info
 *
 * @author hjw
 * @date 2022-09-12
 */
@ToString(callSuper = true)
@Accessors(chain = true)
@Data
public class SysEmpInfoExport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

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

    /** 手机 */
    @Excel(name = "手机")
    private String mobile;

}
