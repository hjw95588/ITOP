package com.ebchinatech.itop.common.override;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.util.ObjectUtils;

public class CustomAbstractWrapper<T> extends QueryWrapper<T> {
    @Override
    public QueryWrapper<T> like(String column, Object val) {
        boolean condition=true;
        if(ObjectUtils.isEmpty(val)){
            condition=false;
        }
        return super.like(condition,column,val);
    }
    @Override
    public QueryWrapper<T> eq(String column, Object val) {
        boolean condition=true;
        if(ObjectUtils.isEmpty(val)){
            condition=false;
        }
        return super.eq(condition,column,val);
    }
}