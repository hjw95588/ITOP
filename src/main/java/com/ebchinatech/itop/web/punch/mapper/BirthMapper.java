package com.ebchinatech.itop.web.punch.mapper;

import java.util.List;
import com.ebchinatech.itop.web.punch.domain.Birth;

/**
 * 点赞的所有人Mapper接口
 *
 * @author hjw
 * @date 2022-10-13
 */
public interface BirthMapper
{
    /**
     * 查询点赞的所有人
     *
     * @param birUserId 点赞的所有人ID
     * @return 点赞的所有人
     */
    public Birth selectBirthById(String birUserId);

    /**
     * 查询点赞的所有人列表
     *
     * @param birth 点赞的所有人
     * @return 点赞的所有人集合
     */
    public List<Birth> selectBirthList(Birth birth);

    /**
     * 新增点赞的所有人
     *
     * @param birth 点赞的所有人
     * @return 结果
     */
    public int insertBirth(Birth birth);

    /**
     * 修改点赞的所有人
     *
     * @param birth 点赞的所有人
     * @return 结果
     */
    public int updateBirth(Birth birth);

    /**
     * 删除点赞的所有人
     *
     * @param birUserId 点赞的所有人ID
     * @return 结果
     */
    public int deleteBirthById(String birUserId);

    /**
     * 批量删除点赞的所有人
     *
     * @param birUserIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBirthByIds(String[] birUserIds);


    public int insertBirthBatch(List<Birth> list);

}
