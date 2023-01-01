package com.ebchinatech.itop.web.punch.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebchinatech.itop.api.utils.CommonService;
import com.ebchinatech.itop.web.punch.mapper.PersonInfoGivelikeMapper;
import com.ebchinatech.kylin.common.exception.CustomException;
import com.ebchinatech.kylin.web.controller.common.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ebchinatech.kylin.framework.aspectj.lang.annotation.Log;
import com.ebchinatech.kylin.framework.aspectj.lang.enums.BusinessType;
import com.ebchinatech.itop.web.punch.domain.PersonInfoGivelike;
import com.ebchinatech.itop.web.punch.service.IPersonInfoGivelikeService;
import com.ebchinatech.kylin.web.domain.AjaxResult;
import com.ebchinatech.kylin.framework.utils.poi.ExcelUtil;
import com.ebchinatech.kylin.web.domain.page.TableDataInfo;

/**
 * 点赞人信息Controller
 *
 * @author hjw
 * @date 2022-07-09
 */
@RestController
@RequestMapping("/person/givelike")
public class PersonInfoGivelikeController extends BaseController
{

    @Autowired
    private CommonService commonService;

    @Value("${giveLike.clock}")
    private String clockUrl;

    @Value("${giveLike.prev}")
    private String prevUrl;

    @Autowired
    private IPersonInfoGivelikeService personInfoGivelikeService;

    @Autowired
    private PersonInfoGivelikeMapper personInfoGivelikeMapper;

    /**
     * 查询点赞人信息列表
     */
    @PreAuthorize("@ss.hasPermi('person:givelike:list')")
    @GetMapping("/list")
    public TableDataInfo list(PersonInfoGivelike personInfoGivelike)
    {
        startPage();
        List<PersonInfoGivelike> list = personInfoGivelikeService.selectPersonInfoGivelikeList(personInfoGivelike);
        return getDataTable(list);
    }


    /**
     * 导出点赞人信息列表
     */
    @PreAuthorize("@ss.hasPermi('person:givelike:export')")
    @Log(title = "点赞人信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PersonInfoGivelike personInfoGivelike)
    {
        List<PersonInfoGivelike> list = personInfoGivelikeService.selectPersonInfoGivelikeList(personInfoGivelike);
        ExcelUtil<PersonInfoGivelike> util = new ExcelUtil<PersonInfoGivelike>(PersonInfoGivelike.class);
        return util.exportExcel(list, "givelike");
    }

    /**
     * 获取点赞人信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('person:givelike:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") String userId)
    {
        return AjaxResult.success(personInfoGivelikeService.selectPersonInfoGivelikeById(userId));
    }

    /**
     * 新增点赞人信息
     */
    @PreAuthorize("@ss.hasPermi('person:givelike:add')")
    @Log(title = "点赞人信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody PersonInfoGivelike personInfoGivelike)
    {
        personInfoGivelike.setIsNeedGive("1");
        return toAjax(personInfoGivelikeService.insertPersonInfoGivelike(personInfoGivelike));
    }


    /**
     * 删除点赞人信息
     */
    @PreAuthorize("@ss.hasPermi('person:givelike:remove')")
    @Log(title = "点赞人信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable String[] userIds)
    {
        return toAjax(personInfoGivelikeService.deletePersonInfoGivelikeByIds(userIds));
    }

    //反转点赞状态
    @GetMapping(value = "/reverseIsNeedGive/{ids}")
    public AjaxResult reverseIsNeedGive(@PathVariable Long[] ids)
    {
        return toAjax(personInfoGivelikeMapper.reverseIsNeedGive(ids));
    }


    @GetMapping(value = "/nowGiveLike")
    public AjaxResult nowGiveLike(String userName,String sessionId,String userId){
        String clockUrl=this.prevUrl+this.clockUrl;//点赞的url
        Map param=new HashMap();
        param.put("qryType","bir");
        param.put("id",userId);
        int n=0;
        try{
            String response =commonService.postForObject(sessionId, param, clockUrl);
            JSONObject object= JSON.parseObject(response);
            if(object.getString("code").equals("200")){
                n=object.getInteger("data");
            }
        }catch(Exception e){
            throw new CustomException(userName+"点赞失败");
        }
        return AjaxResult.success(n);
    }

}
