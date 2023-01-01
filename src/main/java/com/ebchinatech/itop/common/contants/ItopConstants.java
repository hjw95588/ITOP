package com.ebchinatech.itop.common.contants;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * Package: com.ebchinatech.itop.common.contants
 * User: Tuzki
 * Date: 2021/6/21
 * Time: 14:41
 * Description:
 */
public class ItopConstants {

    public static class Vnd {
        /**
         * 按钮类型 0：保存 1：提交
         */
        public static final Integer SAVE_TYPE = 0;

        /**
         * 全部数据权限
         */
        public static final Integer DATA_SCOPE_ALL = 1;

        public enum VndStateEnum {
            INIT_STATUS("INIT_STATUS", "已创建"),
            EXAMINE("EXAMINE", "审核中"),
            REJECT_STATUS("REJECT_STATUS", "已驳回"),
            ENTRANCE_FINISHED("ENTRANCE_FINISHED", "已入场"),
            LEAVE_FINISHED("LEAVE_FINISHED", "已离场"),
            CHG_FINISHED("CHG_FINISHED", "已变更");
            private String code;
            private String name;

            VndStateEnum(String code, String name) {
                this.code = code;
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public enum PersonStateEnum {
            INIT_STATUS("INIT_STATUS", "已创建"),
            APPROVAL_STATUS("APPROVAL_STATUS", "审批中"),
            FINISH_STATUS("FINISH_STATUS", "已完成"),
            REJECT_STATUS("REJECT_STATUS", "已驳回"),
            HAND_INIT_STATUS("HAND_INIT_STATUS", "挂起已创建"),
            HANDING_STATUS("HANDING_STATUS", "挂起中"),
            HAND_STATUS("HAND_STATUS", "已挂起"),
            CANCEL_HAND_INIT_STATUS("CANCEL_HAND_INIT_STATUS", "取消挂起已创建"),
            CANCEL_HANDING_STATUS("CANCEL_HANDING_STATUS", "取消挂起中"),
            CANCEL_HAND_STATUS("CANCEL_HAND_STATUS", "已取消挂起"),
            ENTRANCE_INIT_STATUS("ENTRANCE_INIT_STATUS", "入场已创建"),
            LEAVE_INIT_STATUS("LEAVE_INIT_STATUS", "离场已创建"),
            TRANSITION_INIT_STATUS("TRANSITION_INIT_STATUS", "转场已创建"),
            UN_ENTRANCE("UN_ENTRANCE", "入场中"),
            ENTRANCED("ENTRANCED", "已入场"),
            UN_LEAVE("UN_LEAVE", "离场中"),
            LEAVED("LEAVED", "已离场"),
            UN_TRANSITIONING("UN_TRANSITION", "转场中"),
            TRANSITIONED("TRANSITIONED", "已转场");
            private String code;
            private String name;

            PersonStateEnum(String code, String name) {
                this.code = code;
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
        //AdmissionEstablish	入场保存
        //AdmissionSubmit	入场提交
        //ChangeEstablish	变更保存
        //ChangeSubmit	变更提交
        //ContractFinal	审批完成
        public enum  ContractState{
            ADMISSION_ESTABLISH("AdmissionEstablish"),
            ADMISSION_SUBMIT("AdmissionSubmit"),
            CHANGE_ESTABLISH("ChangeEstablish"),
            CHANGE_SUBMIT("ChangeSubmit"),
            CONTRACT_FINAL("ContractFinal");
            private String code;
            ContractState(String code) {
                this.code = code;
            }
            public String getCode() {
                return code;
            }
            public void setCode(String code) {
                this.code = code;
            }
        }

        public enum  FileNumberEnum{
            SIXTY_FOUR((long)64),
            SIXTY_FIVE((long)65),
            SIXTY_SIX((long)66),
            SIXTY_EIGHT((long)68),
            SIXTY_NINE((long)69),
            SEVENTY((long)70);
            private Long code;
            FileNumberEnum(Long code) {
                this.code = code;
            }
            public Long getCode() {
                return code;
            }
            public ArrayList<Long> getList() {
                ArrayList<Long> list = new ArrayList<>();
                list.add(SIXTY_FOUR.getCode());
                list.add(SIXTY_FIVE.getCode());
                list.add(SIXTY_SIX.getCode());
                list.add(SIXTY_EIGHT.getCode());
                list.add(SIXTY_NINE.getCode());
                list.add(SEVENTY.getCode());
                return list;
            }
            public void setCode(Long code) {
                this.code = code;
            }

        }

        /**
         * 是否挂起 0-是；1-否
         */
        public static final String PENDING = "1";
        public static final String PENDING_CANCEL = "0";

        /**
         * 人员是否转场 0-是；1-否
         */
        public static final String TRANSITION = "0";
        public static final String UN_TRANSITION = "1";

        /**
         * 人员是否离场
         * 3：是
         * 0：否
         * 旧版字段含义：入场是0，离场时该入场的数据改为2并产生一条新的离场数据字段为0，初始化导入的一批数据是9
         * 新版策略：入场时是0 ，离场时改为3
         * 兼容旧版策略：有四种数字：0,2,3,9，其中9是入场，3是离场，0在新版是入场，旧版都有可能，2是旧版创建离场后的入场数据
         */
        public static final String LEAVE = "3";
        public static final String UN_LEAVING = "0";

        /**
         * 字段前缀
         */
        public static final String CODE_PREFIX = "GDKJ-";

        /**
         * 公司编号
         */
        public static final String COMPANY_DEPT_ID = "0000000000";

        /**
         * 供应商是否入场 0-是；1-否
         */
        public static final String ADMISSION = "0";
        public static final String UN_ADMISSION = "1";

        /**
         * 供应商操作类型
         * 0:入场，变更
         * 1:离场
         * 2:离场后将入场和变更的数据改成2（删除时回滚）
         */
        public static final String VND_OPERATION_TYPE1 = "-1";
        public static final String VND_OPERATION_TYPE2 = "0";
        public static final String VND_OPERATION_TYPE3 = "1";
        public static final String VND_OPERATION_TYPE4 = "2";

        /**
         * 供应商人员操作类型
         * 1：入场
         * 2：离场
         * 3：转场
         * 4：挂起
         * 5：取消挂起
         */
        public static final int VND_PERSON_OPERATION_TYPE1 = 1;
        public static final int VND_PERSON_OPERATION_TYPE2 = 2;
        public static final int VND_PERSON_OPERATION_TYPE3 = 3;
        public static final int VND_PERSON_OPERATION_TYPE4 = 4;
        public static final int VND_PERSON_OPERATION_TYPE5 = 5;

        /**
         * 供应商变更状态
         * 0：变更中
         * 1：已变更
         * 2：未变更
         */
        public static final String VND_CHANGE_STATE1 = "0";
        public static final String VND_CHANGE_STATE2 = "1";
        public static final String VND_CHANGE_STATE3 = "2";


        /**
         * 外包合同是否接收人员转场通知 1-是；0-否
         */
        public static final String RECEIVE = "1";
        public static final String UN_RECEIVE = "0";
    }

    public static final int FAIL_CODE = -1;
    public static final int SUCCESS_CODE = 200;

}
