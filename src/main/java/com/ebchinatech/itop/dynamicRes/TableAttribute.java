package com.ebchinatech.itop.dynamicRes;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.ebchinatech.itop.dynamicRes
 * User: Tuzki
 * Date: 2021/4/16
 * Time: 10:40
 * Description:表格属性
 */
@Data
@Accessors(chain = true)
public class TableAttribute {
    /**
     * 列名
     */
    private List<String> columnName;

    /**
     * 列数量
     */
    private Integer columnCount;

    /**
     * 表格数据
     */
    private List tableData;

    /**
     * 表单页面属性
     */
    private DynamicEntityRes form;
}
