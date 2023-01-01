package com.ebchinatech.itop.web.punch.service.impl;

import java.util.List;
import com.ebchinatech.kylin.common.utils.DateUtils;
import com.ebchinatech.kylin.framework.utils.SecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ebchinatech.itop.web.punch.mapper.PersonInfoGivelikeMapper;
import com.ebchinatech.itop.web.punch.domain.PersonInfoGivelike;
import com.ebchinatech.itop.web.punch.service.IPersonInfoGivelikeService;

/**
 * 点赞人信息Service业务层处理
 *
 * @author hjw
 * @date 2022-07-09
 */
@Service
public class PersonInfoGivelikeServiceImpl implements IPersonInfoGivelikeService
{
    @Autowired
    private PersonInfoGivelikeMapper personInfoGivelikeMapper;

    /**
     * 查询点赞人信息
     *
     * @param userId 点赞人信息ID
     * @return 点赞人信息
     */
    @Override
    public PersonInfoGivelike selectPersonInfoGivelikeById(String userId)
    {
        return personInfoGivelikeMapper.selectPersonInfoGivelikeById(userId);
    }

    /**
     * 查询点赞人信息列表
     *
     * @param personInfoGivelike 点赞人信息
     * @return 点赞人信息
     */
    @Override
    public List<PersonInfoGivelike> selectPersonInfoGivelikeList(PersonInfoGivelike personInfoGivelike)
    {
        return personInfoGivelikeMapper.selectPersonInfoGivelikeList(personInfoGivelike);
    }

    /**
     * 新增点赞人信息
     *
     * @param personInfoGivelike 点赞人信息
     * @return 结果
     */
    @Override
    public int insertPersonInfoGivelike(PersonInfoGivelike info)
    {
        int m=0;
        PersonInfoGivelike personInfoGivelike = personInfoGivelikeMapper.selectPersonInfoGivelikeById(info.getUserId());
        if(personInfoGivelike!=null){
            info.setUpdateBy(SecurityUtils.getUsername());
            info.setUpdateTime(DateUtils.getNowDate());
           m=personInfoGivelikeMapper.updatePersonInfoGivelike(info);
        }else{
            info.setCreateBy(SecurityUtils.getUsername());
            info.setCreateTime(DateUtils.getNowDate());
            m=personInfoGivelikeMapper.insertPersonInfoGivelike(info);
        }
        return m;
    }

    /**
     * 修改点赞人信息
     *
     * @param personInfoGivelike 点赞人信息
     * @return 结果
     */
    @Override
    public int updatePersonInfoGivelike(PersonInfoGivelike personInfoGivelike)
    {
        personInfoGivelike.setUpdateTime(DateUtils.getNowDate());
        return personInfoGivelikeMapper.updatePersonInfoGivelike(personInfoGivelike);
    }

    /**
     * 批量删除点赞人信息
     *
     * @param userIds 需要删除的点赞人信息ID
     * @return 结果
     */
    @Override
    public int deletePersonInfoGivelikeByIds(String[] userIds)
    {
        return personInfoGivelikeMapper.deletePersonInfoGivelikeByIds(userIds);
    }

    /**
     * 删除点赞人信息信息
     *
     * @param userId 点赞人信息ID
     * @return 结果
     */
    @Override
    public int deletePersonInfoGivelikeById(String userId)
    {
        return personInfoGivelikeMapper.deletePersonInfoGivelikeById(userId);
    }
}
