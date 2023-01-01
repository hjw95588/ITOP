package com.ebchinatech.itop.web.punch.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ebchinatech.itop.web.punch.mapper.PunchCalendarsMapper;
import com.ebchinatech.itop.web.punch.domain.PunchCalendars;
import com.ebchinatech.itop.web.punch.service.IPunchCalendarsService;

/**
 * 打卡日历Service业务层处理
 * 
 * @author hjw
 * @date 2022-06-19
 */
@Service
public class PunchCalendarsServiceImpl implements IPunchCalendarsService 
{
    @Autowired
    private PunchCalendarsMapper punchCalendarsMapper;

    /**
     * 查询打卡日历
     * 
     * @param workDate 打卡日历ID
     * @return 打卡日历
     */
    @Override
    public PunchCalendars selectPunchCalendarsById(String workDate)
    {
        return punchCalendarsMapper.selectPunchCalendarsById(workDate);
    }

    /**
     * 查询打卡日历列表
     * 
     * @param punchCalendars 打卡日历
     * @return 打卡日历
     */
    @Override
    public List<PunchCalendars> selectPunchCalendarsList(PunchCalendars punchCalendars)
    {
        return punchCalendarsMapper.selectPunchCalendarsList(punchCalendars);
    }

    /**
     * 新增打卡日历
     * 
     * @param punchCalendars 打卡日历
     * @return 结果
     */
    @Override
    public int insertPunchCalendars(PunchCalendars punchCalendars)
    {
        return punchCalendarsMapper.insertPunchCalendars(punchCalendars);
    }

    /**
     * 修改打卡日历
     * 
     * @param punchCalendars 打卡日历
     * @return 结果
     */
    @Override
    public int updatePunchCalendars(PunchCalendars punchCalendars)
    {
        return punchCalendarsMapper.updatePunchCalendars(punchCalendars);
    }

    /**
     * 批量删除打卡日历
     * 
     * @param workDates 需要删除的打卡日历ID
     * @return 结果
     */
    @Override
    public int deletePunchCalendarsByIds(String[] workDates)
    {
        return punchCalendarsMapper.deletePunchCalendarsByIds(workDates);
    }

    /**
     * 删除打卡日历信息
     * 
     * @param workDate 打卡日历ID
     * @return 结果
     */
    @Override
    public int deletePunchCalendarsById(String workDate)
    {
        return punchCalendarsMapper.deletePunchCalendarsById(workDate);
    }
}
