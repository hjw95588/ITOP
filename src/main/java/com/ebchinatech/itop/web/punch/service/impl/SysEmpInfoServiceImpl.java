package com.ebchinatech.itop.web.punch.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ebchinatech.itop.web.punch.mapper.SysEmpInfoMapper;
import com.ebchinatech.itop.web.punch.domain.SysEmpInfo;
import com.ebchinatech.itop.web.punch.service.ISysEmpInfoService;

/**
 * 人员信息Service业务层处理
 *
 * @author hjw
 * @date 2022-09-11
 */
@Service
public class SysEmpInfoServiceImpl implements ISysEmpInfoService
{
    @Autowired
    private SysEmpInfoMapper sysEmpInfoMapper;

    /**
     * 查询人员信息
     *
     * @param empid 人员信息ID
     * @return 人员信息
     */
    @Override
    public SysEmpInfo selectSysEmpInfoById(Long empid)
    {

        SysEmpInfo sysEmpInfo = sysEmpInfoMapper.selectSysEmpInfoById(empid);

        List<Map> upgradeRow = sysEmpInfoMapper.getUpgradeRow(empid);
        if(upgradeRow!=null && upgradeRow.size()>0){
            sysEmpInfo.setUpdateGrade(upgradeRow);
        }
        return sysEmpInfo;
    }

    /**
     * 查询人员信息列表
     *
     * @param sysEmpInfo 人员信息
     * @return 人员信息
     */
    @Override
    public List<SysEmpInfo> selectSysEmpInfoList(SysEmpInfo sysEmpInfo)
    {
        return sysEmpInfoMapper.selectSysEmpInfoList(sysEmpInfo);
    }

    /**
     * 新增人员信息
     *
     * @param sysEmpInfo 人员信息
     * @return 结果
     */
    @Override
    public int insertSysEmpInfo(SysEmpInfo sysEmpInfo)
    {
        return sysEmpInfoMapper.insertSysEmpInfo(sysEmpInfo);
    }

    /**
     * 修改人员信息
     *
     * @param sysEmpInfo 人员信息
     * @return 结果
     */
    @Override
    public int updateSysEmpInfo(SysEmpInfo sysEmpInfo)
    {
        return sysEmpInfoMapper.updateSysEmpInfo(sysEmpInfo);
    }

    /**
     * 批量删除人员信息
     *
     * @param empids 需要删除的人员信息ID
     * @return 结果
     */
    @Override
    public int deleteSysEmpInfoByIds(Long[] empids)
    {
        return sysEmpInfoMapper.deleteSysEmpInfoByIds(empids);
    }

    /**
     * 删除人员信息信息
     *
     * @param empid 人员信息ID
     * @return 结果
     */
    @Override
    public int deleteSysEmpInfoById(Long empid)
    {
        return sysEmpInfoMapper.deleteSysEmpInfoById(empid);
    }
}
