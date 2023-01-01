package com.ebchinatech.itop.web.punch.service;

import java.util.List;
import com.ebchinatech.itop.web.punch.domain.PunchCalendars;

/**
 * 打卡日历Service接口
 * 
 * @author hjw
 * @date 2022-06-19
 */
public interface IPunchCalendarsService 
{
    /**
     * 查询打卡日历
     * 
     * @param workDate 打卡日历ID
     * @return 打卡日历
     */
    public PunchCalendars selectPunchCalendarsById(String workDate);

    /**
     * 查询打卡日历列表
     * 
     * @param punchCalendars 打卡日历
     * @return 打卡日历集合
     */
    public List<PunchCalendars> selectPunchCalendarsList(PunchCalendars punchCalendars);

    /**
     * 新增打卡日历
     * 
     * @param punchCalendars 打卡日历
     * @return 结果
     */
    public int insertPunchCalendars(PunchCalendars punchCalendars);

    /**
     * 修改打卡日历
     * 
     * @param punchCalendars 打卡日历
     * @return 结果
     */
    public int updatePunchCalendars(PunchCalendars punchCalendars);

    /**
     * 批量删除打卡日历
     * 
     * @param workDates 需要删除的打卡日历ID
     * @return 结果
     */
    public int deletePunchCalendarsByIds(String[] workDates);

    /**
     * 删除打卡日历信息
     * 
     * @param workDate 打卡日历ID
     * @return 结果
     */
    public int deletePunchCalendarsById(String workDate);
}
