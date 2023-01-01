package com.ebchinatech.itop.web.punch.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ebchinatech.itop.web.punch.mapper.PersonInfoPunchMapper;
import com.ebchinatech.itop.web.punch.domain.PersonInfoPunch;
import com.ebchinatech.itop.web.punch.service.IPersonInfoPunchService;

/**
 * 打卡人信息Service业务层处理
 *
 * @author hjw
 * @date 2022-06-16
 */
@Service
public class PersonInfoPunchServiceImpl implements IPersonInfoPunchService
{
    @Autowired
    private PersonInfoPunchMapper personInfoPunchMapper;

    /**
     * 查询打卡人信息
     *
     * @param id 打卡人信息ID
     * @return 打卡人信息
     */
    @Override
    public PersonInfoPunch selectPersonInfoPunchById(Long id)
    {
        return personInfoPunchMapper.selectPersonInfoPunchById(id);
    }

    /**
     * 查询打卡人信息列表
     *
     * @param personInfoPunch 打卡人信息
     * @return 打卡人信息
     */
    @Override
    public List<PersonInfoPunch> selectPersonInfoPunchList(PersonInfoPunch personInfoPunch)
    {
        return personInfoPunchMapper.selectPersonInfoPunchList(personInfoPunch);
    }

    /**
     * 新增打卡人信息
     *
     * @param personInfoPunch 打卡人信息
     * @return 结果
     */
    @Override
    public int insertPersonInfoPunch(PersonInfoPunch personInfoPunch)
    {
        return personInfoPunchMapper.insertPersonInfoPunch(personInfoPunch);
    }

    /**
     * 修改打卡人信息
     *
     * @param personInfoPunch 打卡人信息
     * @return 结果
     */
    @Override
    public int updatePersonInfoPunch(PersonInfoPunch personInfoPunch)
    {
        return personInfoPunchMapper.updatePersonInfoPunch(personInfoPunch);
    }

    /**
     * 批量删除打卡人信息
     *
     * @param ids 需要删除的打卡人信息ID
     * @return 结果
     */
    @Override
    public int deletePersonInfoPunchByIds(Long[] ids)
    {
        return personInfoPunchMapper.deletePersonInfoPunchByIds(ids);
    }

    @Override
    public int reversePunch(Long[] ids) {
        return personInfoPunchMapper.reversePunch(ids);
    }

    @Override
    public int changePunchStatue(Long[] ids,String type) {
        return personInfoPunchMapper.changePunchStatue(ids,type);
    }

    /**
     * 删除打卡人信息信息
     *
     * @param id 打卡人信息ID
     * @return 结果
     */
    @Override
    public int deletePersonInfoPunchById(Long id)
    {
        return personInfoPunchMapper.deletePersonInfoPunchById(id);
    }
}
