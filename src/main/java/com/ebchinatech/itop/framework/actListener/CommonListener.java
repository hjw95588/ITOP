package com.ebchinatech.itop.framework.actListener;

import com.ebchinatech.kylinflow.service.impl.KylinTaskServiceImpl;
import com.ebchinatech.kylinflow.service.impl.KylinTaskTodoInfoServiceImpl;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.history.HistoricActivityInstance;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Package: com.ebchinatech.itop.framework.actListener
 * User: Tuzki
 * Date: 2021/5/14
 * Time: 10:16
 * Description:通用任务监听器
 */
@Component
public class CommonListener implements ExecutionListener, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        KylinTaskTodoInfoServiceImpl kylinTaskTodoInfoService = (KylinTaskTodoInfoServiceImpl) applicationContext.getBean("kylinTaskTodoInfoServiceImpl");
        boolean pass = kylinTaskTodoInfoService.getApprovalResult(delegateExecution);
        System.out.println("测试delegateExecution id = " + delegateExecution.getId());
//        Integer completedInstances = (Integer) delegateExecution.getVariable("nrOfCompletedInstances");
//        Boolean signPass = (Boolean) delegateExecution.getVariable("signPass");
//
//        Map<String, Boolean> variablesMap = new HashMap<>();
//        variablesMap.put("pass", true);
//        RuntimeService runtimeService = (RuntimeService) applicationContext.getBean("runtimeServiceBean");
//        runtimeService.setVariables(delegateExecution.getId(), variablesMap);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (CommonListener.applicationContext == null) {
            CommonListener.applicationContext = applicationContext;
        }
    }
}
