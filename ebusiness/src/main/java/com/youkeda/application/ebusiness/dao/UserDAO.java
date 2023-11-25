package com.youkeda.application.ebusiness.dao;

import com.youkeda.application.ebusiness.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Super
 */
@Mapper
public interface UserDAO {

    /**批量新增用户
     *
     * @param userDOS
     * @return
     */
    int batchAdd(@Param("list") List<UserDO> userDOS);

    /**根据一组id批量查找
     *
     * @param ids
     * @return
     */
    List<UserDO> findByIds(@Param("ids") List<Long> ids);

    /**新增用户
     *
     * @param userDO
     * @return
     */
    int add(UserDO userDO);

    /**修改用户
     *
     * @param userDO
     * @return
     */
    int update(UserDO userDO);

    /**删除用户
     *
     * @param id
     * @return
     */
    int delete(@Param("id") long id);

    /**根据用户名查找用户
     *
     * @param name
     * @return
     */
    UserDO findByUserName(@Param("userName") String name);

}
