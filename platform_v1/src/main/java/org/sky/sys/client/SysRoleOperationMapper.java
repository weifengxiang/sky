package org.sky.sys.client;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.sky.sys.model.SysRoleOperation;
import org.sky.sys.model.SysRoleOperationExample;

public interface SysRoleOperationMapper {
    long countByExample(SysRoleOperationExample example);

    int deleteByExample(SysRoleOperationExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysRoleOperation record);

    int insertSelective(SysRoleOperation record);

    List<SysRoleOperation> selectByExample(SysRoleOperationExample example);

    SysRoleOperation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysRoleOperation record, @Param("example") SysRoleOperationExample example);

    int updateByExample(@Param("record") SysRoleOperation record, @Param("example") SysRoleOperationExample example);

    int updateByPrimaryKeySelective(SysRoleOperation record);

    int updateByPrimaryKey(SysRoleOperation record);
}