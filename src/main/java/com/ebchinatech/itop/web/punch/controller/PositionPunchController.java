package com.ebchinatech.itop.web.punch.controller;

import java.util.List;

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
import com.ebchinatech.itop.web.punch.domain.PositionPunch;
import com.ebchinatech.itop.web.punch.service.IPositionPunchService;
import com.ebchinatech.kylin.web.domain.AjaxResult;
import com.ebchinatech.kylin.framework.utils.poi.ExcelUtil;
import com.ebchinatech.kylin.web.domain.page.TableDataInfo;

/**
 * 打卡需要的常用位置Controller
 *
 * @author hjw
 * @date 2022-06-16
 */
@RestController
@RequestMapping("/position/punch")
public class PositionPunchController extends BaseController
{
    @Autowired
    private IPositionPunchService positionPunchService;

    /**
     * 查询打卡需要的常用位置列表
     */
    @PreAuthorize("@ss.hasPermi('position:punch:list')")
    @GetMapping("/list")
    public TableDataInfo list(PositionPunch positionPunch)
    {
        startPage();
        List<PositionPunch> list = positionPunchService.selectPositionPunchList(positionPunch);
        return getDataTable(list);
    }

    /**
     * 导出打卡需要的常用位置列表
     */
    @PreAuthorize("@ss.hasPermi('position:punch:export')")
    @Log(title = "打卡需要的常用位置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PositionPunch positionPunch)
    {
        List<PositionPunch> list = positionPunchService.selectPositionPunchList(positionPunch);
        ExcelUtil<PositionPunch> util = new ExcelUtil<PositionPunch>(PositionPunch.class);
        return util.exportExcel(list, "punch");
    }

    /**
     * 获取打卡需要的常用位置详细信息
     */
    @PreAuthorize("@ss.hasPermi('position:punch:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(positionPunchService.selectPositionPunchById(id));
    }

    /**
     * 新增打卡需要的常用位置
     */
    @PreAuthorize("@ss.hasPermi('position:punch:add')")
    @Log(title = "打卡需要的常用位置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PositionPunch positionPunch)
    {
        return toAjax(positionPunchService.insertPositionPunch(positionPunch));
    }

    /**
     * 修改打卡需要的常用位置
     */
    @PreAuthorize("@ss.hasPermi('position:punch:edit')")
    @Log(title = "打卡需要的常用位置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PositionPunch positionPunch)
    {
        return toAjax(positionPunchService.updatePositionPunch(positionPunch));
    }

    /**
     * 删除打卡需要的常用位置
     */
    @PreAuthorize("@ss.hasPermi('position:punch:remove')")
    @Log(title = "打卡需要的常用位置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(positionPunchService.deletePositionPunchByIds(ids));
    }
}
