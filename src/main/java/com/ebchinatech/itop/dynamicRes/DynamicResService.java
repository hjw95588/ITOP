package com.ebchinatech.itop.dynamicRes;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.ebchinatech.itop.dynamicRes
 * User: Tuzki
 * Date: 2021/4/16
 * Time: 10:11
 * Description:动态返回实体service
 */
public interface DynamicResService<T> {

    /**
     * 封装动态实体
     * @param t 业务表单实体
     * @return
     */
    List<DynamicEntityRes> getDynamicData(T t);
}