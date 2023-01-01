package com.ebchinatech.itop.dynamicRes;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created with IntelliJ IDEA.
 * Package: com.ebchinatech.kylin.web.domain
 * User: Tuzki
 * Date: 2021/4/16
 * Time: 8:58
 * Description:属性实体
 */
@Data
@Accessors(chain = true)
public class Attribute {
    /**
     * 属性名称
     */
    private String attrName;

    /**
     * 属性值
     */
    private AttributeValue attrValue;

    /**
     * 属性类型
     * 1：文本框
     * 2：日期框
     * 3：下拉框
     * 4：单选框
     * 5：多选框
     * 6：文本域
     * 7：选择框
     * 8：文件框
     * 9：表格
     */
    private Integer attrType;

    /**
     * 是否必填
     */
    private Boolean requiredFlag;

    /**
     * 是否只读
     */
    private Boolean readonlyFlag;

    /**
     * 是否有属性名称
     */
    private Boolean existAttrNameFlag;


}
