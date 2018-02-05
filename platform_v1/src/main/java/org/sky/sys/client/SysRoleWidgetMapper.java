package org.sky.sys.client;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.sky.sys.model.SysRoleWidget;
import org.sky.sys.model.SysRoleWidgetExample;

public interface SysRoleWidgetMapper {
    long countByExample(SysRoleWidgetExample example);

    int deleteByExample(SysRoleWidgetExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysRoleWidget record);

    int insertSelective(SysRoleWidget record);

    List<SysRoleWidget> selectByExample(SysRoleWidgetExample example);

    SysRoleWidget selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysRoleWidget record, @Param("example") SysRoleWidgetExample example);

    int updateByExample(@Param("record") SysRoleWidget record, @Param("example") SysRoleWidgetExample example);

    int updateByPrimaryKeySelective(SysRoleWidget record);

    int updateByPrimaryKey(SysRoleWidget record);
}