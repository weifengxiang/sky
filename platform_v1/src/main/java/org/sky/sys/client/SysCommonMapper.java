package org.sky.sys.client;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SysCommonMapper {
	/**
	 * 
	 * @param map<tablename,''>;map<fields,'f1,f2'>;map<filter,'f1,f2'>
	 * map<sort,'f1 desc,f2 asc'>;map<begin,0>;map<end,20>
	 * @return
	 */
	public List<Map> queryComboData(Map map);
	
	/**
	 * 查询数据库当前时间
	 * @return
	 */
	public Date querySysDate();
	/**
	 * 查询数据库时间戳
	 * @return
	 */
	public Timestamp queryTimestamp();

}
