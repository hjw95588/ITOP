package com.ebchinatech.itop.web.punch.service;

import java.util.List;
import com.ebchinatech.itop.web.punch.domain.PersonInfoPunch;

/**
 * 打卡人信息Service接口
 *
 * @author hjw
 * @date 2022-06-16
 */
public interface IPersonInfoPunchService
{
    /**
     * 查询打卡人信息
     *
     * @param id 打卡人信息ID
     * @return 打卡人信息
     */
    public PersonInfoPunch selectPersonInfoPunchById(Long id);

    /**
     * 查询打卡人信息列表
     *
     * @param personInfoPunch 打卡人信息
     * @return 打卡人信息集合
     */
    public List<PersonInfoPunch> selectPersonInfoPunchList(PersonInfoPunch personInfoPunch);

    /**
     * 新增打卡人信息
     *
     * @param personInfoPunch 打卡人信息
     * @return 结果
     */
    public int insertPersonInfoPunch(PersonInfoPunch personInfoPunch);

    /**
     * 修改打卡人信息
     *
     * @param personInfoPunch 打卡人信息
     * @return 结果
     */
    public int updatePersonInfoPunch(PersonInfoPunch personInfoPunch);

    /**
     * 批量删除打卡人信息
     *
     * @param ids 需要删除的打卡人信息ID
     * @return 结果
     */
    public int deletePersonInfoPunchByIds(Long[] ids);

    public int reversePunch(Long[] ids);

    public int changePunchStatue(Long[] ids,String type);

    /**
     * 删除打卡人信息信息
     *
     * @param id 打卡人信息ID
     * @return 结果
     */
    public int deletePersonInfoPunchById(Long id);
}
