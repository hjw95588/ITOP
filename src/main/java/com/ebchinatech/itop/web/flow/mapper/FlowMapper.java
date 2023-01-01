package com.ebchinatech.itop.web.flow.mapper;

import java.util.List;

public interface FlowMapper {
    String getInstanceId(String bizKey);

    List<String> getAllInstanceId(String bizKey);

    String getTaskId(String bizKey);

    String getInstanceIdByBusinessKey(String flowKey,String businessKey);

    String getTaskIdByProcInstId(String procInstId);

}
