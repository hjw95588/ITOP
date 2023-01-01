package com.ebchinatech.itop.web.flow.domain;

import com.ebchinatech.kylinflow.domain.dto.Approval;
import com.ebchinatech.kylinflow.domain.dto.CompleteTaskDTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created with IntelliJ IDEA.
 * Package: com.ebchinatech.itop.web.flow.domain
 * User: Tuzki
 * Date: 2021/6/11
 * Time: 16:11
 * Description:任务处理对象
 */
@Accessors(chain = true)
@Data
public class TaskProcessDTO<T> {
    /**
     * 业务对象
     */
    private T biz;

    /**
     * 任务
     */
    private CompleteTaskDTO task;

    /**
     * 审批信息
     */
    private Approval approval;
}
