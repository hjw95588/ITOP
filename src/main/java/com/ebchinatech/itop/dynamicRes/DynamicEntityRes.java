package com.ebchinatech.itop.dynamicRes;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.ebchinatech.kylin.web.domain
 * User: Tuzki
 * Date: 2021/4/16
 * Time: 8:52
 * Description:动态返回数据实体
 */
@Data
@Accessors(chain = true)
public class DynamicEntityRes {
    /**
     * 表单子标题
     */
    private String subtitle;

    /**
     * 表单属性字段
     */
    private List<Attribute> attributeList;


}
