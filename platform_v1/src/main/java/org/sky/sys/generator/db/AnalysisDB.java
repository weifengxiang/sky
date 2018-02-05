package org.sky.sys.generator.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sky.sys.generator.consts.GeneratorConsts;
import org.sky.sys.generator.model.ColumnMeta;
import org.sky.sys.generator.model.TableMeta;

/**
 * 解析表结构
 * 
 * @author
 * @date
 */
public class AnalysisDB {

	/**
	 * 读取表列表
	 * 
	 * @return
	 */
	public static final List<TableMeta> readDB() {
		List<TableMeta> list = new ArrayList<TableMeta>();
		String sql = "select * from information_schema.TABLES t " +
					 " where t.table_schema='"+GeneratorConsts.DB_NAME+"' " +
					 " and t.TABLE_NAME = '"+GeneratorConsts.TABLE_NAME+"'";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = DBHandler.createConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				TableMeta tm = new TableMeta();
				tm.setSchemaName(GeneratorConsts.DB_NAME);
				tm.setTableName(rs.getString("TABLE_NAME"));
				tm.setComment(rs.getString("TABLE_COMMENT"));
				list.add(tm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 填充字段信息
	 * 
	 * @param tables
	 */
	public static final void readTables(List<TableMeta> tables) {
		if (tables != null) {
			for (TableMeta tm : tables) {
				readTable(tm);
			}
		}
		DBHandler.close();
	}

	/**
	 * 读取表结构
	 * 
	 * @param table
	 */
	private static final void readTable(TableMeta table) {
		List<ColumnMeta> list = new ArrayList<ColumnMeta>();
		String sql = "select * from information_schema.COLUMNS t " +
				 " where t.table_schema='"+GeneratorConsts.DB_NAME+"' " +
				 " and t.TABLE_NAME = '"+table.getTableName()+"'";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = DBHandler.createConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ColumnMeta fm = new ColumnMeta();

				fm.setColumnName(rs.getString("COLUMN_NAME"));
				fm.setIsNullable(rs.getString("IS_NULLABLE"));
				fm.setColumnDefault(rs.getString("COLUMN_DEFAULT"));
				fm.setColumnType(rs.getString("DATA_TYPE"));
				fm.setColumnComment(rs.getString("COLUMN_COMMENT"));

				list.add(fm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		table.setColumns(list);
	}

}
