package com.ebchinatech.itop.common.enums;

/**
 * Created with IntelliJ IDEA.
 * Package: com.ebchinatech.itop.common.enums
 * User: Tuzki
 * Date: 2021/8/9
 * Time: 17:47
 * Description:部门枚举类
 */
public enum DeptEnum {
    EB_TECH_DEPT_001("1800000000", "光大科技有限公司"),
    EB_TECH_DEPT_002("1801000000", "银行事业部"),
    EB_TECH_DEPT_003("1802000000", "产品研发部"),
    EB_TECH_DEPT_004("1803000000", "智能云计算部"),
    EB_TECH_DEPT_005("1804000000", "大数据部"),
    EB_TECH_DEPT_006("1805000000", "战略发展部"),
    EB_TECH_DEPT_007("1806000000", "人力资源部"),
    EB_TECH_DEPT_008("1807000000", "综合管理部"),
    EB_TECH_DEPT_009("1808000000", "财务管理部"),
    EB_TECH_DEPT_010("1809000000", "党群工作部"),
    EB_TECH_DEPT_011("1810000000", "风险管理部"),
    EB_TECH_DEPT_012("1811000000", "纪检工作部"),
    EB_TECH_DEPT_013("1812000000", "集团科创委办公室");
    private String deptId;
    private String deptName;

    DeptEnum(String deptId, String deptName) {
        this.deptId = deptId;
        this.deptName = deptName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
