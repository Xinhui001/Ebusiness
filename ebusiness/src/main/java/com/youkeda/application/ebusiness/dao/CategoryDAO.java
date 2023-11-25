package com.youkeda.application.ebusiness.dao;

import com.youkeda.application.ebusiness.dataobject.CategoryDO;
import com.youkeda.application.ebusiness.model.domain.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Super
 */
@Mapper
public interface CategoryDAO {
    /**新增
     *
     * @param categoryDO
     */
    int insert(CategoryDO categoryDO);

    /**查询所有类目
     *
     * @return
     */
    List<Category> selectAll();

    /**根据一批父类目id查询类目及其所有子类目
     *
     * @param ids
     * @return
     */
    List<CategoryDO> selectByParentIds(@Param("ids") List<String> ids);

    /**根据一个父类目id查询类目及其所有子类目
     *
     * @param id
     * @return
     */
    List<Category> selectByParentId(@Param("id") String id);

    /**根据类目id查询类目及其所有子类目
     *
     * @param id
     * @return
     */
    CategoryDO get(String id);

    /**修改类目
     *
     * @param categoryDO
     * @return
     */
    int update(CategoryDO categoryDO);

    /**删除类目
     *
     * @param id
     * @return
     */
    int delete(@Param("id") String id);

}
