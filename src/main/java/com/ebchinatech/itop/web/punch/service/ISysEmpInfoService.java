package com.ebchinatech.itop.web.punch.service;

import java.util.List;
import com.ebchinatech.itop.web.punch.domain.SysEmpInfo;

/**
 * 人员信息Service接口
 * 
 * @author hjw
 * @date 2022-09-11
 */
public interface ISysEmpInfoService 
{
    /**
     * 查询人员信息
     * 
     * @param empid 人员信息ID
     * @return 人员信息
     */
    public SysEmpInfo selectSysEmpInfoById(Long empid);

    /**
     * 查询人员信息列表
     * 
     * @param sysEmpInfo 人员信息
     * @return 人员信息集合
     */
    public List<SysEmpInfo> selectSysEmpInfoList(SysEmpInfo sysEmpInfo);

    /**
     * 新增人员信息
     * 
     * @param sysEmpInfo 人员信息
     * @return 结果
     */
    public int insertSysEmpInfo(SysEmpInfo sysEmpInfo);

    /**
     * 修改人员信息
     * 
     * @param sysEmpInfo 人员信息
     * @return 结果
     */
    public int updateSysEmpInfo(SysEmpInfo sysEmpInfo);

    /**
     * 批量删除人员信息
     * 
     * @param empids 需要删除的人员信息ID
     * @return 结果
     */
    public int deleteSysEmpInfoByIds(Long[] empids);

    /**
     * 删除人员信息信息
     * 
     * @param empid 人员信息ID
     * @return 结果
     */
    public int deleteSysEmpInfoById(Long empid);
}
