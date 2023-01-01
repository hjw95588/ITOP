package com.ebchinatech.itop.dynamicRes;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Package: com.ebchinatech.itop.dynamicRes
 * User: Tuzki
 * Date: 2021/4/16
 * Time: 15:11
 * Description:通用审批信息实体
 */
@Data
@Accessors(chain = true)
public class CommonApprovalRes {

    static private DynamicEntityRes data;

    static {
        data = new DynamicEntityRes();
        List<Attribute> attributeList = new ArrayList<>();

        Map<String, Boolean> radioMap = new HashMap<String, Boolean>() {
            {
                put("同意", true);
                put("不同意", false);
            }

        };
        Attribute approvalRadioAttr = new Attribute()
                .setAttrName("审批结果")
                .setAttrType(4)
                .setExistAttrNameFlag(true)
                .setReadonlyFlag(false)
                .setRequiredFlag(true)
                .setAttrValue(new AttributeValue().setBoxValue(radioMap));

        Attribute approvalSuggestionAttr = new Attribute()
                .setAttrName("审批意见")
                .setAttrType(6)
                .setExistAttrNameFlag(true)
                .setReadonlyFlag(false)
                .setRequiredFlag(true)
                .setAttrValue(new AttributeValue().setTextValue(null));

        Attribute approvalPersonAttr = new Attribute()
                .setAttrName("审批人员")
                .setAttrType(1)
                .setExistAttrNameFlag(true)
                .setReadonlyFlag(true)
                .setRequiredFlag(true)
                .setAttrValue(new AttributeValue().setTextValue(null));

        Attribute approvalDeptAttr = new Attribute()
                .setAttrName("审批部门")
                .setAttrType(1)
                .setExistAttrNameFlag(true)
                .setReadonlyFlag(true)
                .setRequiredFlag(true)
                .setAttrValue(new AttributeValue().setTextValue(null));

        Attribute approvalTimeAttr = new Attribute()
                .setAttrName("审批时间")
                .setAttrType(2)
                .setExistAttrNameFlag(true)
                .setReadonlyFlag(true)
                .setRequiredFlag(true)
                .setAttrValue(new AttributeValue().setDateValue(null));

        attributeList.add(approvalRadioAttr);
        attributeList.add(approvalSuggestionAttr);
        attributeList.add(approvalPersonAttr);
        attributeList.add(approvalDeptAttr);
        attributeList.add(approvalTimeAttr);
        data.setSubtitle("审批信息")
                .setAttributeList(attributeList);

    }

    public static DynamicEntityRes getCommonApprovalData() {
        return data;
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(CommonApprovalRes.getCommonApprovalData()));
    }
}
