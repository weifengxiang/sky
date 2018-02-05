package org.sky.sys.client;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.sky.sys.model.SysMenu;
import org.sky.sys.model.SysMenuExample;

public interface SysMenuMapper {
    long countByExample(SysMenuExample example);

    int deleteByExample(SysMenuExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    List<SysMenu> selectByExample(SysMenuExample example);

    SysMenu selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysMenu record, @Param("example") SysMenuExample example);

    int updateByExample(@Param("record") SysMenu record, @Param("example") SysMenuExample example);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);
    /**
     * 查询用户具有的权限菜单
     */
    List<SysMenu> selectLeafMenuByUserCode(String userCode);
}