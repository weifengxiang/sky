package org.sky.sys.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.examples.BigGridDemo.SpreadsheetWriter;
import org.sky.sys.client.SysCommonMapper;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

@Service
public class SysCommonService {
	@Autowired
	private SysCommonMapper commonmapper;

	public SysCommonService() {
	}

	/**
	 * 获取下拉数据
	 * 
	 * @param map
	 * @return
	 */
	public List<Map> queryComboData(Map map) {
		List<Map> list = commonmapper.queryComboData(map);
		return list;
	}

	public Date queryDbDate() {
		return commonmapper.querySysDate();
	}

	public Timestamp queryDbTimestamp() {
		return commonmapper.queryTimestamp();
	}

}
