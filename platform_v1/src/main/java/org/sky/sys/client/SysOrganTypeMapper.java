package org.sky.sys.client;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.sky.sys.model.SysOrganType;
import org.sky.sys.model.SysOrganTypeExample;

public interface SysOrganTypeMapper {
    long countByExample(SysOrganTypeExample example);

    int deleteByExample(SysOrganTypeExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysOrganType record);

    int insertSelective(SysOrganType record);

    List<SysOrganType> selectByExample(SysOrganTypeExample example);

    SysOrganType selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysOrganType record, @Param("example") SysOrganTypeExample example);

    int updateByExample(@Param("record") SysOrganType record, @Param("example") SysOrganTypeExample example);

    int updateByPrimaryKeySelective(SysOrganType record);

    int updateByPrimaryKey(SysOrganType record);
}