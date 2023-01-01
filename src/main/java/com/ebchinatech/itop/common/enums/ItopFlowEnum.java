package com.ebchinatech.itop.common.enums;

/**
 * Created with IntelliJ IDEA.
 * Package: com.ebchinatech.itop.common.enums
 * User: Tuzki
 * Date: 2021/5/14
 * Time: 10:42
 * Description:
 */
public enum ItopFlowEnum {
    PROJ_001("项目立项","flow4d81047a6e9e48a3aace08e7c78e39ca"),
    PROJ_002("项目变更","flowdd2978167d6a4752b17b1f66a94ec223"),
    PROJ_003("项目验收","flow5376fec6cca54b649d8fe7ade945be7d"),
    PROJ_004("项目月报","flow498497b2f80b43c482eb25e769d1916c"),
    PROJ_005("项目作废","flow423b989ec6d64dd5b6444b59081d9528"),

    VND_001("供应商入场","flowc627d18222a84e94bfee8491f816a70d"),
    VND_002("供应商离场","flowc42a9216dc454732abc1ef74e0f70280"),
    VND_003("供应商变更","flow2bd1bf2427164988bb18ee080c5940ab"),
    VND_004("供应商人员入场","flow28959727ea69405e8607b3296a28b0f7"),
    VND_005("供应商人员离场","flowa3f901bffa8f446199f88f4b9b22f5ab"),
    VND_006("供应商人员转场","flow9028a3dcd1b94ba3b0d6a8765e59fbbf"),
    VND_007("供应商人员挂起/取消挂起","flow108b6182d3994e0a82853e010e045763"),

    WKH_001("工时信息审批","flowc88870badca14cc5a51ff04f767a15fb");


    private String flowName;
    private String flowKey;


    ItopFlowEnum(String flowName, String flowKey){
        this.flowName = flowName;
        this.flowKey = flowKey;
    }
    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getFlowKey() {
        return flowKey;
    }

    public void setFlowKey(String flowKey) {
        this.flowKey = flowKey;
    }
}
