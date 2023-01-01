package com.ebchinatech.itop.web.flow.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ebchinatech.itop.web.flow.mapper.FlowMapper;
import com.ebchinatech.itop.web.flow.service.IFlowService;
import com.ebchinatech.kylin.web.domain.SysDept;
import com.ebchinatech.kylin.web.domain.SysUser;
import com.ebchinatech.kylin.web.service.ISysUserService;
import com.ebchinatech.kylinflow.domain.KylinTaskTodoInfo;
import com.ebchinatech.kylinflow.domain.dto.TaskDTO;
import com.ebchinatech.kylinflow.domain.vo.ActFlowChart;
import com.ebchinatech.kylinflow.domain.vo.ActHistory;
import com.ebchinatech.kylinflow.service.IKylinTaskTodoInfoService;
import com.ebchinatech.kylinflow.service.KylinTaskService;
import com.ebchinatech.kylinflow.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.ebchinatech.itop.web.flow.service.dto
 * User: Tuzki
 * Date: 2021/5/21
 * Time: 9:10
 * Description:
 */
@Service
@Transactional
public class FlowServiceImpl implements IFlowService {

    @Autowired
    private KylinTaskService kylinTaskService;

    @Autowired
    private IKylinTaskTodoInfoService kylinTaskTodoInfoService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private FlowMapper flowMapper;
    /**
     * 查询待办任务列表
     *
     * @param task
     * @return 待办任务集合
     */
    @Override
    public List<KylinTaskTodoInfo> selectKylinTaskTodoInfoList(TaskDTO task) {
        List<KylinTaskTodoInfo> kylinTaskTodoInfoList = kylinTaskTodoInfoService.selectKylinTaskTodoInfoList(task);
        if (!CollectionUtils.isEmpty(kylinTaskTodoInfoList)) {
            SysUser user = userService.selectUserById(task.getUserId());
            kylinTaskTodoInfoList.forEach(taskTodoInfo -> {
                String bizJson = taskTodoInfo.getBizJson();
                if (!StringUtils.isEmpty(bizJson)) {
                    JSONObject jsonObject = JSONObject.parseObject(bizJson);
                    taskTodoInfo.setItemName(jsonObject.getString("abstract"));
                    taskTodoInfo.setHandler(user.getUserName());
                }
            });
        }
        return kylinTaskTodoInfoList;
    }

    /**
     * 根据流程实例ID查询历史操作进展
     *
     * @param processInstantId
     * @return
     */
    @Override
    public List<ActHistory> getHistory(String processInstantId) {
        List<ActHistory> actHistoryList = kylinTaskService.getHistory(processInstantId);
        if (!CollectionUtils.isEmpty(actHistoryList)) {
            for (ActHistory actHistory : actHistoryList) {
                String resultJson = actHistory.getResult();
                if (!StringUtils.isEmpty(resultJson)) {
                    if (resultJson.contains("{") && resultJson.contains("}")) {
                        JSONObject jsonObject = JSONObject.parseObject(resultJson);
                        actHistory.setResult(jsonObject.getString("abstract"));
                    }
                }
                //查询处理人部门
                String userId = actHistory.getHandler();
                if (!StringUtils.isEmpty(userId)) {
                    SysUser user = userService.selectUserById(userId);
                    List<SysDept> deptList = user.getDept();
                    String deptStr = "";
                    if (!CollectionUtils.isEmpty(deptList)) {
                        for (SysDept dept : deptList) {
                            deptStr += (dept.getDeptName() + ",");
                        }
                        deptStr = deptStr.substring(0, deptStr.lastIndexOf(","));
                        actHistory.setHandlerDept(deptStr);
                        actHistory.setHandler(user.getUserName());
                    }
                }
            }
        }
        return actHistoryList;
    }

    @Override
    public List<ActHistory> getHistoryListByBizKey(String bizKey) {
        //一条业务id可能由于审批不同意发起多次流程，取最后发起的一次流程（一条流程的不同节点流程实例id相同）
        String instanceId = flowMapper.getInstanceId(bizKey);
        return getHistory(instanceId);
    }

    @Override
    public ActFlowChart getChartByBizKey (String bizKey) throws Exception {
        //查询最新节点的taskid，这样无论是已完成还是待办都可正确显示
        String taskId = flowMapper.getTaskId(bizKey);
        return   kylinTaskTodoInfoService.selectFlowChart(taskId);
    }


    public void deleteFlowDataByBizKey(String bizKey) {
        List<String> instanceIds = flowMapper.getAllInstanceId(bizKey);
        if(!CollectionUtils.isEmpty(instanceIds)){
            instanceIds.forEach(instanceId->kylinTaskService.cleanProcessInstance(instanceId));
        }
    }
}
