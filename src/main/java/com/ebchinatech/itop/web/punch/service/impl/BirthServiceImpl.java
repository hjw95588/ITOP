package com.ebchinatech.itop.web.punch.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ebchinatech.itop.web.punch.mapper.BirthMapper;
import com.ebchinatech.itop.web.punch.domain.Birth;
import com.ebchinatech.itop.web.punch.service.IBirthService;

/**
 * 点赞的所有人Service业务层处理
 * 
 * @author hjw
 * @date 2022-10-13
 */
@Service
public class BirthServiceImpl implements IBirthService 
{
    @Autowired
    private BirthMapper birthMapper;

    /**
     * 查询点赞的所有人
     * 
     * @param birUserId 点赞的所有人ID
     * @return 点赞的所有人
     */
    @Override
    public Birth selectBirthById(String birUserId)
    {
        return birthMapper.selectBirthById(birUserId);
    }

    /**
     * 查询点赞的所有人列表
     * 
     * @param birth 点赞的所有人
     * @return 点赞的所有人
     */
    @Override
    public List<Birth> selectBirthList(Birth birth)
    {
        return birthMapper.selectBirthList(birth);
    }

    /**
     * 新增点赞的所有人
     * 
     * @param birth 点赞的所有人
     * @return 结果
     */
    @Override
    public int insertBirth(Birth birth)
    {
        return birthMapper.insertBirth(birth);
    }

    /**
     * 修改点赞的所有人
     * 
     * @param birth 点赞的所有人
     * @return 结果
     */
    @Override
    public int updateBirth(Birth birth)
    {
        return birthMapper.updateBirth(birth);
    }

    /**
     * 批量删除点赞的所有人
     * 
     * @param birUserIds 需要删除的点赞的所有人ID
     * @return 结果
     */
    @Override
    public int deleteBirthByIds(String[] birUserIds)
    {
        return birthMapper.deleteBirthByIds(birUserIds);
    }

    /**
     * 删除点赞的所有人信息
     * 
     * @param birUserId 点赞的所有人ID
     * @return 结果
     */
    @Override
    public int deleteBirthById(String birUserId)
    {
        return birthMapper.deleteBirthById(birUserId);
    }
}
