package com.ebchinatech.itop.common.enums;

/**
 * Created with IntelliJ IDEA.
 * Package: com.ebchinatech.itop.common.enums
 * User: Tuzki
 * Date: 2021/6/30
 * Time: 14:09
 * Description:文件类型枚举
 */
public enum FileTypeEnum {
    VND_ENTRANCE_FILE01("64", "外包供应商信息登记"),
    VND_ENTRANCE_FILE02("65", "加盖公章营业执照复印件"),
    VND_ENTRANCE_FILE03("66", "最新年度财务审计报告"),
    VND_ENTRANCE_FILE04("68", "尽职调查报送表"),
    VND_ENTRANCE_FILE05("69", "尽职调查报告"),
    VND_ENTRANCE_FILE06("70", "风险评估报告"),
    VND_ENTRANCE_FILE07("74", "外包合同文件"),
    VND_ENTRANCE_FILE08("71", "外包可行性分析"),
    VND_ENTRANCE_FILE09("72", "外包活动识别记录表"),
    VND_ENTRANCE_FILE10("67", "项目审批签报材料(复印件)"),
    VND_LEAVE_FILE01("89", "离场材料"),
    VND_PERSON_ENTRANCE_FILE01("91", "身份证或护照等有效证件复印件"),
    VND_PERSON_ENTRANCE_FILE02("92", "背景调查表(加盖外包商公章)"),
    VND_PERSON_ENTRANCE_FILE03("93", "与外包商签订的劳动合同复印件"),
    VND_PERSON_ENTRANCE_FILE04("94", "学历/学位证书复印件"),
    VND_PERSON_ENTRANCE_FILE05("95", "光大科技有限公司外包人员保密承诺书"),
    VND_PERSON_ENTRANCE_FILE06("96", "光大科技有限公司外包人员安全保障公约"),
    VND_PERSON_ENTRANCE_FILE07("97", "备注"),
    VND_PERSON_LEAVE_FILE01("84", "科技服务项目变更表");


    private String fileRepositoryId;
    private String fileTypeName;


    FileTypeEnum(String fileRepositoryId, String fileTypeName) {
        this.fileRepositoryId = fileRepositoryId;
        this.fileTypeName = fileTypeName;
    }

    public String getFileTypeCode() {
        return fileRepositoryId;
    }

    public void setFileTypeCode(String fileRepositoryId) {
        this.fileRepositoryId = fileRepositoryId;
    }

    public String getFileTypeName() {
        return fileTypeName;
    }

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }
}
