package org.sky.sys.client;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.sky.sys.model.SysOrgan;
import org.sky.sys.model.SysOrganExample;

public interface SysOrganMapper {
    long countByExample(SysOrganExample example);

    int deleteByExample(SysOrganExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysOrgan record);

    int insertSelective(SysOrgan record);

    List<SysOrgan> selectByExample(SysOrganExample example);

    SysOrgan selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysOrgan record, @Param("example") SysOrganExample example);

    int updateByExample(@Param("record") SysOrgan record, @Param("example") SysOrganExample example);

    int updateByPrimaryKeySelective(SysOrgan record);

    int updateByPrimaryKey(SysOrgan record);
}