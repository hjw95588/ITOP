package com.ebchinatech.itop.common.enums;

/**
 * 文件业务对象枚举类
 *
 * @author Jiyue
 * @date 2022-02-09
 **/
public enum FileBizTypeEnum {

    CONTACT_APPLY("system.itop.cnt.model.bo.ItopCntInfoBO","合同附件"),
    CONTACT_ACCEPT("system.itop.cnt.model.bo.ItopCntPayInfoBO","验收报告");

    private String type;
    private String desc;

    FileBizTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
