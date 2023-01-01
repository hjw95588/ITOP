package com.ebchinatech.itop.dynamicRes;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Package: com.ebchinatech.kylin.web.domain.vo
 * User: Tuzki
 * Date: 2021/4/16
 * Time: 9:27
 * Description:
 */
@Data
@Accessors(chain = true)
public class AttributeValue {

    /**
     * 文本/文本域值
     */
    private String textValue;

    /**
     * 日期框值
     */
    private Date dateValue;

    /**
     * 下拉框值
     */
    private List<Object> ComboBoxValue;

    /**
     * 单选/多选框选项&值
     */
    private Map<String, Boolean> boxValue;

    /**
     * 选择框值
     * 包括人员选择、部门选择
     */
    private String selectValue;

    /**
     * 文件值
     * key -> 文件名
     * value -> 文件路径
     */
    private List<Map<String, String>> fileValue;

    /**
     * 表格类型值
     */
    private TableAttribute tableValue;
}
