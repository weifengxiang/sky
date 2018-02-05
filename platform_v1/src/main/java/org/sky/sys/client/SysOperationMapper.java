package org.sky.sys.client;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.sky.sys.model.SysOperation;
import org.sky.sys.model.SysOperationExample;

public interface SysOperationMapper {
    long countByExample(SysOperationExample example);

    int deleteByExample(SysOperationExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysOperation record);

    int insertSelective(SysOperation record);

    List<SysOperation> selectByExample(SysOperationExample example);

    SysOperation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysOperation record, @Param("example") SysOperationExample example);

    int updateByExample(@Param("record") SysOperation record, @Param("example") SysOperationExample example);

    int updateByPrimaryKeySelective(SysOperation record);

    int updateByPrimaryKey(SysOperation record);
    
    List<SysOperation> selectByUserCode(String userCode);
}