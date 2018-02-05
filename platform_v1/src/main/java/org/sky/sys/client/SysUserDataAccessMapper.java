package org.sky.sys.client;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.sky.sys.model.SysUserDataAccess;
import org.sky.sys.model.SysUserDataAccessExample;

public interface SysUserDataAccessMapper {
    long countByExample(SysUserDataAccessExample example);

    int deleteByExample(SysUserDataAccessExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysUserDataAccess record);

    int insertSelective(SysUserDataAccess record);

    List<SysUserDataAccess> selectByExample(SysUserDataAccessExample example);

    SysUserDataAccess selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysUserDataAccess record, @Param("example") SysUserDataAccessExample example);

    int updateByExample(@Param("record") SysUserDataAccess record, @Param("example") SysUserDataAccessExample example);

    int updateByPrimaryKeySelective(SysUserDataAccess record);

    int updateByPrimaryKey(SysUserDataAccess record);
}