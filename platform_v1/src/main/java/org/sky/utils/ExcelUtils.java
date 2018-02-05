package org.sky.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sky.sys.utils.ExcelMax.SpreadsheetWriter;

public class ExcelUtils {
	public static Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb) {
		Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
		XSSFDataFormat fmt = wb.createDataFormat();

		XSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		style1.setDataFormat(fmt.getFormat("0.0%"));
		styles.put("percent", style1);

		XSSFCellStyle style2 = wb.createCellStyle();
		style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style2.setDataFormat(fmt.getFormat("0.0X"));
		styles.put("coeff", style2);

		XSSFCellStyle style3 = wb.createCellStyle();
		style3.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		style3.setDataFormat(fmt.getFormat("$#,##0.00"));
		styles.put("currency", style3);

		XSSFCellStyle style4 = wb.createCellStyle();
		style4.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		style4.setDataFormat(fmt.getFormat("mmm dd"));
		styles.put("date", style4);

		XSSFCellStyle style5 = wb.createCellStyle();
		XSSFFont headerFont = wb.createFont();
		headerFont.setBold(true);
		style5.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style5.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style5.setFont(headerFont);
		styles.put("header", style5);

		return styles;
	}

	public static void dataInsert(SpreadsheetWriter sw, String[] headersArray,
			List dataList, List showNameList, int i, int rows) {
		// write data rows
		try {

			for (int excelKey = 0; excelKey < dataList.size(); excelKey++) {
				Map map = (Map) dataList.get(excelKey);
				sw.insertRow((i - 1) * rows + excelKey + 1);
				for (int k = 0; k < showNameList.size(); k++) {
					String fieldName = (String) showNameList.get(k);
					Object value = map.get(fieldName);
					String textValue = value == null
							|| "null".equals(value.toString()) ? "" : value
							.toString();
					sw.createCell(k, StringEscapeUtils.escapeXml(textValue));
				}
				sw.endRow();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static  SpreadsheetWriter getWrite(Writer out,
			Map<String, XSSFCellStyle> styles, Object[] headers)
			throws Exception {

		SpreadsheetWriter sw = new SpreadsheetWriter(out);
		sw.beginSheet();

		// insert header row
		sw.insertRow(0);
		int styleIndex = styles.get("header").getIndex();
		for (int i = 0; i < headers.length; i++) {
			sw.createCell(i, StringEscapeUtils.escapeXml(headers[i] + ""));
		}

		sw.endRow();
		return sw;
	}

}
