package com.ebchinatech.itop.web.flow.controller;

import com.ebchinatech.itop.web.flow.service.IFlowService;
import com.ebchinatech.kylin.common.enums.ResultEnum;
import com.ebchinatech.kylinflow.controller.BaseController;
import com.ebchinatech.kylinflow.domain.KylinTaskTodoInfo;
import com.ebchinatech.kylinflow.domain.dto.TaskDTO;
import com.ebchinatech.kylinflow.domain.page.TableData;
import com.ebchinatech.kylinflow.domain.vo.ActFlowChart;
import com.ebchinatech.kylinflow.domain.vo.ActHistory;
import com.ebchinatech.kylinflow.service.IKylinTaskTodoInfoService;
import com.ebchinatech.kylinflow.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.ebchinatech.itop.web.flow.controller
 * User: Tuzki
 * Date: 2021/5/21
 * Time: 10:01
 * Description:工作台API
 */

@RequestMapping("center")
@RestController
public class FlowController extends BaseController {

    @Autowired
    private IFlowService flowService;

    @Autowired
    private IKylinTaskTodoInfoService kylinTaskTodoInfoService;

    @GetMapping("/taskTodo/list")
    public TableData getTaskTodoList(TaskDTO taskDTO) {
        //手动分页
        if (null != taskDTO.getPageNum() || null != taskDTO.getPageSize()) {
            if (taskDTO.getPageNum() <= 0) {
                taskDTO.setPageNum(1);
            }
            taskDTO.setPageNum(taskDTO.getPageNum() - 1);
            Integer limit = taskDTO.getPageSize();
            Integer offset = taskDTO.getPageSize() * taskDTO.getPageNum();
            taskDTO.setPageNum(limit);
            taskDTO.setPageSize(offset);
        }
        List<KylinTaskTodoInfo> list = flowService.selectKylinTaskTodoInfoList(taskDTO);
        return new TableData()
                .setMsg(ResultEnum.SUCCESS.getMsg())
                .setCode(ResultEnum.SUCCESS.getCode())
                .setTotal(kylinTaskTodoInfoService.selectTaskCount(taskDTO))
                .setRows(list);
    }

    /**
     * 查询操作进展
     *
     * @param processInstanceId
     * @return
     */
    @GetMapping("/getHistory")
    public AjaxResult getHistoryList(@RequestParam String processInstanceId) {
        List<ActHistory> actHistoryList = flowService.getHistory(processInstanceId);
        return AjaxResult.success(actHistoryList);
    }

    /**
     * 根据业务主键查询操作进展
     *
     * @param bizKey
     * @return
     */
    @GetMapping("/getHistoryByBizKey")
    public AjaxResult getHistoryListByBizKey(@RequestParam String bizKey) {
        List<ActHistory> actHistoryList = flowService.getHistoryListByBizKey(bizKey);
        return AjaxResult.success(actHistoryList);
    }

    /**
     * 根据业务主键查询流程图
     *
     * @param bizKey
     * @return
     */
    @GetMapping("/getChartByBizKey")
    public AjaxResult getChartByBizKey(@RequestParam String bizKey) throws Exception {

        ActFlowChart actFlowChart = flowService.getChartByBizKey(bizKey);
        return AjaxResult.success(actFlowChart);
    }
}
