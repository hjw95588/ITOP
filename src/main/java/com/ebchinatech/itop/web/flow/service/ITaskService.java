package com.ebchinatech.itop.web.flow.service;

import java.util.Map;

/**
 * @author guona
 * @date 2021-08-16
 **/
public interface ITaskService {

    /**
     * 根据流程实例ID完成当前登录人任务
     * @param processInstanceId 流程实例ID
     * @param userId 任务处理人（如果是当前登录人，则传SecurityUtils.getUsername()
     * 如果参数为空，则从待办任务列表遍历）
     * @param variables 参数
     */
    void completeTask(String processInstanceId, String userId, Map<String, Object> variables);

    /**
     * 根据流程实例ID完成当前登录人任务
     * @param processInstanceId 流程实例ID
     * @param nodeId 节点ID
     * @param groupKey 审批组key
     * @param variables 参数
     */
    void completeTask(String processInstanceId, String nodeId, String groupKey, Map<String, Object> variables);
}
