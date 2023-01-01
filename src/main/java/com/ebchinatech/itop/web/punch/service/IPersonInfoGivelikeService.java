package com.ebchinatech.itop.web.punch.service;

import java.util.List;
import com.ebchinatech.itop.web.punch.domain.PersonInfoGivelike;

/**
 * 点赞人信息Service接口
 * 
 * @author hjw
 * @date 2022-07-09
 */
public interface IPersonInfoGivelikeService 
{
    /**
     * 查询点赞人信息
     * 
     * @param userId 点赞人信息ID
     * @return 点赞人信息
     */
    public PersonInfoGivelike selectPersonInfoGivelikeById(String userId);

    /**
     * 查询点赞人信息列表
     * 
     * @param personInfoGivelike 点赞人信息
     * @return 点赞人信息集合
     */
    public List<PersonInfoGivelike> selectPersonInfoGivelikeList(PersonInfoGivelike personInfoGivelike);

    /**
     * 新增点赞人信息
     * 
     * @param personInfoGivelike 点赞人信息
     * @return 结果
     */
    public int insertPersonInfoGivelike(PersonInfoGivelike personInfoGivelike);

    /**
     * 修改点赞人信息
     * 
     * @param personInfoGivelike 点赞人信息
     * @return 结果
     */
    public int updatePersonInfoGivelike(PersonInfoGivelike personInfoGivelike);

    /**
     * 批量删除点赞人信息
     * 
     * @param userIds 需要删除的点赞人信息ID
     * @return 结果
     */
    public int deletePersonInfoGivelikeByIds(String[] userIds);

    /**
     * 删除点赞人信息信息
     * 
     * @param userId 点赞人信息ID
     * @return 结果
     */
    public int deletePersonInfoGivelikeById(String userId);
}
