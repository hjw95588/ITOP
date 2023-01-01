package com.ebchinatech.itop.web.punch.controller;

import java.util.List;

import com.ebchinatech.itop.web.punch.mapper.BirthMapper;
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
import com.ebchinatech.itop.web.punch.domain.Birth;
import com.ebchinatech.itop.web.punch.service.IBirthService;
import com.ebchinatech.kylin.web.domain.AjaxResult;
import com.ebchinatech.kylin.framework.utils.poi.ExcelUtil;
import com.ebchinatech.kylin.web.domain.page.TableDataInfo;

/**
 * 点赞的所有人Controller
 *
 * @author hjw
 * @date 2022-10-13
 */
@RestController
@RequestMapping("/birthday/birth")
public class BirthController extends BaseController
{
    @Autowired
    private IBirthService birthService;

    /**
     * 查询点赞的所有人列表
     */
    @PreAuthorize("@ss.hasPermi('birthday:birth:list')")
    @GetMapping("/list")
    public TableDataInfo list(Birth birth)
    {
        startPage();
        List<Birth> list = birthService.selectBirthList(birth);
        return getDataTable(list);
    }

    /**
     * 导出点赞的所有人列表
     */
    @PreAuthorize("@ss.hasPermi('birthday:birth:export')")
    @Log(title = "点赞的所有人", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Birth birth)
    {
        List<Birth> list = birthService.selectBirthList(birth);
        ExcelUtil<Birth> util = new ExcelUtil<Birth>(Birth.class);
        return util.exportExcel(list, "birth");
    }

    /**
     * 获取点赞的所有人详细信息
     */
    @PreAuthorize("@ss.hasPermi('birthday:birth:query')")
    @GetMapping(value = "/{birUserId}")
    public AjaxResult getInfo(@PathVariable("birUserId") String birUserId)
    {
        return AjaxResult.success(birthService.selectBirthById(birUserId));
    }

    /**
     * 新增点赞的所有人
     */
    @PreAuthorize("@ss.hasPermi('birthday:birth:add')")
    @Log(title = "点赞的所有人", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Birth birth)
    {
        return toAjax(birthService.insertBirth(birth));
    }

    /**
     * 修改点赞的所有人
     */
    @PreAuthorize("@ss.hasPermi('birthday:birth:edit')")
    @Log(title = "点赞的所有人", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Birth birth)
    {
        return toAjax(birthService.updateBirth(birth));
    }

    /**
     * 删除点赞的所有人
     */
    @PreAuthorize("@ss.hasPermi('birthday:birth:remove')")
    @Log(title = "点赞的所有人", businessType = BusinessType.DELETE)
	@DeleteMapping("/{birUserIds}")
    public AjaxResult remove(@PathVariable String[] birUserIds)
    {
        return toAjax(birthService.deleteBirthByIds(birUserIds));
    }

}
