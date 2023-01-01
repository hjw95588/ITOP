package com.ebchinatech.itop.web.punch.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ebchinatech.itop.web.punch.domain.PunchCalendars;

/**
 * 打卡日历Mapper接口
 * 
 * @author hjw
 * @date 2022-06-19
 */
public interface PunchCalendarsMapper 
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
     * 删除打卡日历
     * 
     * @param workDate 打卡日历ID
     * @return 结果
     */
    public int deletePunchCalendarsById(String workDate);

    /**
     * 批量删除打卡日历
     * 
     * @param workDates 需要删除的数据ID
     * @return 结果
     */
    public int deletePunchCalendarsByIds(String[] workDates);
    
    
    public List<PunchCalendars> getBetweenList(@Param("startDate") String startDate,@Param("endDate") String endDate);
}
