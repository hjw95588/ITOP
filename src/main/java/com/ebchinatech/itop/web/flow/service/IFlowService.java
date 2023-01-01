package com.ebchinatech.itop.web.flow.service;

import com.ebchinatech.kylinflow.domain.KylinTaskTodoInfo;
import com.ebchinatech.kylinflow.domain.dto.TaskDTO;
import com.ebchinatech.kylinflow.domain.vo.ActFlowChart;
import com.ebchinatech.kylinflow.domain.vo.ActHistory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.ebchinatech.itop.web.flow.service
 * User: Tuzki
 * Date: 2021/5/21
 * Time: 9:08
 * Description:封装kylin工作台service
 */
public interface IFlowService {
    /**
     * 查询待办任务列表
     *
     * @param task
     * @return 待办任务集合
     */
    List<KylinTaskTodoInfo> selectKylinTaskTodoInfoList(TaskDTO task);


    /**
     * 根据流程实例ID查询历史操作进展
     *
     * @param processInstantId
     * @return
     */
    List<ActHistory> getHistory(String processInstantId);

    /**
     * 根据业务主键查询操作进展
     *
     * @param bizKey
     * @return
     */
    List<ActHistory> getHistoryListByBizKey(String bizKey);

    ActFlowChart getChartByBizKey(String bizKey) throws Exception;

    void deleteFlowDataByBizKey(String bizKey);
}
