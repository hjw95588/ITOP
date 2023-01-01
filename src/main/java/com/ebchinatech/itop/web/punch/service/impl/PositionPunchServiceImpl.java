package com.ebchinatech.itop.web.punch.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ebchinatech.itop.web.punch.mapper.PositionPunchMapper;
import com.ebchinatech.itop.web.punch.domain.PositionPunch;
import com.ebchinatech.itop.web.punch.service.IPositionPunchService;

/**
 * 打卡需要的常用位置Service业务层处理
 *
 * @author hjw
 * @date 2022-06-16
 */
@Service
public class PositionPunchServiceImpl implements IPositionPunchService
{
    @Autowired
    private PositionPunchMapper positionPunchMapper;

    /**
     * 查询打卡需要的常用位置
     *
     * @param id 打卡需要的常用位置ID
     * @return 打卡需要的常用位置
     */
    @Override
    public PositionPunch selectPositionPunchById(Long id)
    {
        return positionPunchMapper.selectPositionPunchById(id);
    }

    /**
     * 查询打卡需要的常用位置列表
     *
     * @param positionPunch 打卡需要的常用位置
     * @return 打卡需要的常用位置
     */
    @Override
    public List<PositionPunch> selectPositionPunchList(PositionPunch positionPunch)
    {
        return positionPunchMapper.selectPositionPunchList(positionPunch);
    }

    /**
     * 新增打卡需要的常用位置
     *
     * @param positionPunch 打卡需要的常用位置
     * @return 结果
     */
    @Override
    public int insertPositionPunch(PositionPunch positionPunch)
    {

        Map<String,String> hashMap = new HashMap();
        hashMap.put("11",positionPunch.getSiteName());
        hashMap.put("22",positionPunch.getSiteName());
        hashMap.put("33",positionPunch.getSiteName());
        try{
           // positionPunchMapper.insertPositionPunchBatch(hashMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return positionPunchMapper.insertPositionPunch(positionPunch);
    }

    /**
     * 修改打卡需要的常用位置
     *
     * @param positionPunch 打卡需要的常用位置
     * @return 结果
     */
    @Override
    public int updatePositionPunch(PositionPunch positionPunch)
    {
        return positionPunchMapper.updatePositionPunch(positionPunch);
    }

    /**
     * 批量删除打卡需要的常用位置
     *
     * @param ids 需要删除的打卡需要的常用位置ID
     * @return 结果
     */
    @Override
    public int deletePositionPunchByIds(Long[] ids)
    {
        return positionPunchMapper.deletePositionPunchByIds(ids);
    }

    /**
     * 删除打卡需要的常用位置信息
     *
     * @param id 打卡需要的常用位置ID
     * @return 结果
     */
    @Override
    public int deletePositionPunchById(Long id)
    {
        return positionPunchMapper.deletePositionPunchById(id);
    }
}
