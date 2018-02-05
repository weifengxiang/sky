package org.sky.sys.client;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.sky.sys.model.SysWidget;
import org.sky.sys.model.SysWidgetExample;

public interface SysWidgetMapper {
    long countByExample(SysWidgetExample example);

    int deleteByExample(SysWidgetExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysWidget record);

    int insertSelective(SysWidget record);

    List<SysWidget> selectByExample(SysWidgetExample example);

    SysWidget selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysWidget record, @Param("example") SysWidgetExample example);

    int updateByExample(@Param("record") SysWidget record, @Param("example") SysWidgetExample example);

    int updateByPrimaryKeySelective(SysWidget record);

    int updateByPrimaryKey(SysWidget record);
    
    List<SysWidget> getSysWidgetListByUserCode(String userCode);
}