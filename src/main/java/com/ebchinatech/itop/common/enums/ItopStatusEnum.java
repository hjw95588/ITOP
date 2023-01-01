package com.ebchinatech.itop.common.enums;

/**
 * Created with IntelliJ IDEA.
 * Package: com.ebchinatech.itop.common.enums
 * User: Tuzki
 * Date: 2021/6/21
 * Time: 14:18
 * Description:系统务状态枚举类
 */
public enum ItopStatusEnum {
    ;
    /**
     * 模块名
     */
    private String moduleName;
    /**
     * 状态key
     */
    private String statusKey;
    /**
     * 状态值
     */
    private String statusValue;


    ItopStatusEnum(String moduleName, String statusKey, String statusValue) {
        this.moduleName = moduleName;
        this.statusKey = statusKey;
        this.statusValue = statusValue;
    }
}
