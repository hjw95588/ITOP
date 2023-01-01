package com.ebchinatech.itop.web.punch.mapper;

import java.util.List;
import java.util.Map;

import com.ebchinatech.itop.web.punch.domain.PositionPunch;
import org.apache.ibatis.annotations.Param;

/**
 * 打卡需要的常用位置Mapper接口
 *
 * @author hjw
 * @date 2022-06-16
 */
public interface PositionPunchMapper
{
    /**
     * 查询打卡需要的常用位置
     *
     * @param id 打卡需要的常用位置ID
     * @return 打卡需要的常用位置
     */
    public PositionPunch selectPositionPunchById(Long id);

    /**
     * 查询打卡需要的常用位置列表
     *
     * @param positionPunch 打卡需要的常用位置
     * @return 打卡需要的常用位置集合
     */
    public List<PositionPunch> selectPositionPunchList(PositionPunch positionPunch);

    /**
     * 新增打卡需要的常用位置
     *
     * @param positionPunch 打卡需要的常用位置
     * @return 结果
     */
    public int insertPositionPunch(PositionPunch positionPunch);

    public int insertPositionPunchBatch(@Param("others") Map ma);

    /**
     * 修改打卡需要的常用位置
     *
     * @param positionPunch 打卡需要的常用位置
     * @return 结果
     */
    public int updatePositionPunch(PositionPunch positionPunch);

    /**
     * 删除打卡需要的常用位置
     *
     * @param id 打卡需要的常用位置ID
     * @return 结果
     */
    public int deletePositionPunchById(Long id);

    /**
     * 批量删除打卡需要的常用位置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePositionPunchByIds(Long[] ids);
}
