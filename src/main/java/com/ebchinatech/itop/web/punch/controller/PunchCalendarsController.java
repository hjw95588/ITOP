package com.ebchinatech.itop.web.punch.controller;

import java.util.List;

import com.ebchinatech.itop.api.utils.CommonService;
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
import com.ebchinatech.itop.web.punch.domain.PunchCalendars;
import com.ebchinatech.itop.web.punch.service.IPunchCalendarsService;
import com.ebchinatech.kylin.web.domain.AjaxResult;
import com.ebchinatech.kylin.framework.utils.poi.ExcelUtil;
import com.ebchinatech.kylin.web.domain.page.TableDataInfo;

/**
 * 打卡日历Controller
 *
 * @author hjw
 * @date 2022-06-19
 */
@RestController
@RequestMapping("/punchInfo/calendars")
public class PunchCalendarsController extends BaseController
{
    @Autowired
    private IPunchCalendarsService punchCalendarsService;



    /**
     * 查询打卡日历列表
     */
    @PreAuthorize("@ss.hasPermi('punchInfo:calendars:list')")
    @GetMapping("/list")
    public TableDataInfo list(PunchCalendars punchCalendars)
    {

        startPage();
        List<PunchCalendars> list = punchCalendarsService.selectPunchCalendarsList(punchCalendars);
        return getDataTable(list);
    }

    /**
     * 导出打卡日历列表
     */
    @PreAuthorize("@ss.hasPermi('punchInfo:calendars:export')")
    @Log(title = "打卡日历", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PunchCalendars punchCalendars)
    {
        List<PunchCalendars> list = punchCalendarsService.selectPunchCalendarsList(punchCalendars);
        ExcelUtil<PunchCalendars> util = new ExcelUtil<PunchCalendars>(PunchCalendars.class);
        return util.exportExcel(list, "calendars");
    }

    /**
     * 获取打卡日历详细信息
     */
    @PreAuthorize("@ss.hasPermi('punchInfo:calendars:query')")
    @GetMapping(value = "/{workDate}")
    public AjaxResult getInfo(@PathVariable("workDate") String workDate)
    {
        return AjaxResult.success(punchCalendarsService.selectPunchCalendarsById(workDate));
    }

    /**
     * 新增打卡日历
     */
    @PreAuthorize("@ss.hasPermi('punchInfo:calendars:add')")
    @Log(title = "打卡日历", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PunchCalendars punchCalendars)
    {
        return toAjax(punchCalendarsService.insertPunchCalendars(punchCalendars));
    }

    /**
     * 修改打卡日历
     */
    @PreAuthorize("@ss.hasPermi('punchInfo:calendars:edit')")
    @Log(title = "打卡日历", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PunchCalendars punchCalendars)
    {
        return toAjax(punchCalendarsService.updatePunchCalendars(punchCalendars));
    }

    /**
     * 删除打卡日历
     */
    @PreAuthorize("@ss.hasPermi('punchInfo:calendars:remove')")
    @Log(title = "打卡日历", businessType = BusinessType.DELETE)
	@DeleteMapping("/{workDates}")
    public AjaxResult remove(@PathVariable String[] workDates)
    {
        return toAjax(punchCalendarsService.deletePunchCalendarsByIds(workDates));
    }
}
