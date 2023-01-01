package com.ebchinatech.itop.web.punch.controller;

import java.util.List;

import com.ebchinatech.itop.web.punch.domain.PersonInfoGivelike;
import com.ebchinatech.kylin.web.controller.common.BaseController;
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
import com.ebchinatech.itop.web.punch.domain.PersonInfoPunch;
import com.ebchinatech.itop.web.punch.service.IPersonInfoPunchService;
import com.ebchinatech.kylin.web.domain.AjaxResult;
import com.ebchinatech.kylin.framework.utils.poi.ExcelUtil;
import com.ebchinatech.kylin.web.domain.page.TableDataInfo;

/**
 * 打卡人信息Controller
 *
 * @author hjw
 * @date 2022-06-16
 */
@RestController
@RequestMapping("/punchInfo/punch")
public class PersonInfoPunchController extends BaseController
{
    @Autowired
    private IPersonInfoPunchService personInfoPunchService;

    /**
     * 查询打卡人信息列表
     */
    @PreAuthorize("@ss.hasPermi('punchInfo:punch:list')")
    @GetMapping("/list")
    public TableDataInfo list(PersonInfoPunch personInfoPunch)
    {
        startPage();
        List<PersonInfoPunch> list = personInfoPunchService.selectPersonInfoPunchList(personInfoPunch);
        return getDataTable(list);
    }


    /**
     * 导出打卡人信息列表
     */
    @PreAuthorize("@ss.hasPermi('punchInfo:punch:export')")
    @Log(title = "打卡人信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PersonInfoPunch personInfoPunch)
    {
        List<PersonInfoPunch> list = personInfoPunchService.selectPersonInfoPunchList(personInfoPunch);
        ExcelUtil<PersonInfoPunch> util = new ExcelUtil<PersonInfoPunch>(PersonInfoPunch.class);
        return util.exportExcel(list, "punch");
    }

    /**
     * 获取打卡人信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('punchInfo:punch:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(personInfoPunchService.selectPersonInfoPunchById(id));
    }

    /**
     * 新增打卡人信息
     */
    @PreAuthorize("@ss.hasPermi('punchInfo:punch:add')")
    @Log(title = "打卡人信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PersonInfoPunch personInfoPunch)
    {
        return toAjax(personInfoPunchService.insertPersonInfoPunch(personInfoPunch));
    }

    /**
     * 修改打卡人信息
     */
    @PreAuthorize("@ss.hasPermi('punchInfo:punch:edit')")
    @Log(title = "打卡人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PersonInfoPunch personInfoPunch)
    {
        return toAjax(personInfoPunchService.updatePersonInfoPunch(personInfoPunch));
    }

    /**
     * 删除打卡人信息
     */
    @PreAuthorize("@ss.hasPermi('punchInfo:punch:remove')")
    @Log(title = "打卡人信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(personInfoPunchService.deletePersonInfoPunchByIds(ids));
    }

    //反转打卡状态
    @GetMapping(value = "/reversePunch/{ids}")
    public AjaxResult reversePunch(@PathVariable Long[] ids)
    {
        return toAjax(personInfoPunchService.reversePunch(ids));
    }

    //改变打卡状态 type:0 需要变成不打卡  type:1 需要变成打卡
    @GetMapping(value = "/changePunchStatue/{ids}/{type}")
    public AjaxResult changePunchStatue(@PathVariable Long[] ids,@PathVariable String type)
    {
        return toAjax(personInfoPunchService.changePunchStatue(ids,type));
    }
}
