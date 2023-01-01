package com.ebchinatech.itop.web.flow.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ebchinatech.itop.web.flow.service.ITaskService;
import com.ebchinatech.kylinflow.domain.KylinTaskTodoInfo;
import com.ebchinatech.kylinflow.domain.dto.CompleteTaskDTO;
import com.ebchinatech.kylinflow.domain.dto.TaskDTO;
import com.ebchinatech.kylinflow.domain.vo.Assignee;
import com.ebchinatech.kylinflow.domain.vo.NodeAssignee;
import com.ebchinatech.kylinflow.service.IKylinTaskTodoInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author guona
 * @date 2021-08-16
 **/
@Service
@Slf4j
public class TaskServiceImpl implements ITaskService {

    @Resource
    private IKylinTaskTodoInfoService kylinTaskTodoInfoService;

    @Override
    public void completeTask(String processInstanceId, String userId, Map<String, Object> variables) {
        // 查询当前登录人查询待办列表
        String taskId = null;
        TaskDTO taskDTO = new TaskDTO().setStatus("0");
        if (StringUtils.isNotBlank(userId)) {
            taskDTO.setUserId(userId);
        }
        List<KylinTaskTodoInfo> kylinTaskTodoInfos = kylinTaskTodoInfoService.selectKylinTaskTodoInfoList(taskDTO);
        for (KylinTaskTodoInfo taskTodoInfo : kylinTaskTodoInfos) {
            if (processInstanceId.equals(taskTodoInfo.getProcessInstanceId())) {
                taskId = taskTodoInfo.getId();
                if (StringUtils.isEmpty(userId) || StringUtils.isBlank(userId)) {
                    JSONObject jsonObject = JSONObject.parseObject(taskTodoInfo.getBizJson());
                    userId = jsonObject.getString("assignee");
                    log.info("待办人：{}", userId);
                }
                break;
            }
        }
        log.info("流程实例ID为:{}, 任务ID为:{}, 任务处理人:{}", processInstanceId, taskId, userId);
        CompleteTaskDTO completeTaskDTO = new CompleteTaskDTO()
                .setTaskId(taskId)
                .setUserId(userId);
        if (variables != null) {
            completeTaskDTO.setVariables(variables);
        }
        kylinTaskTodoInfoService.completeTask(completeTaskDTO);
    }

    @Override
    public void completeTask(String processInstanceId, String nodeId, String groupKey, Map<String, Object> variables) {
        String userId = null;
        List<NodeAssignee> nodeAssigneeList = kylinTaskTodoInfoService
                .getNodeAssignee(processInstanceId, nodeId, groupKey);
        if (nodeAssigneeList.size() > 0) {
            NodeAssignee nodeAssignee = nodeAssigneeList.get(0);
            if (nodeAssignee != null) {
                ArrayList<Assignee> assignees = new ArrayList<>(nodeAssignee.getAssigneeSet());
                userId = assignees.get(0).getAssigneeId();
            }
        }
        log.info("流程实例ID为:{},待办人为:{}", processInstanceId, userId);
        if (StringUtils.isNotBlank(userId)) {
            this.completeTask(processInstanceId, userId, variables);
        }

    }
}
