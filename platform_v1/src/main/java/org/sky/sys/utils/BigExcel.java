package org.sky.sys.utils;


import org.apache.poi.ss.usermodel.DateUtil;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;

public class BigExcel {

	public static void main(String[] args) {
		try {
			
			// 第一步.创建一个临时的 excel 文件，配置单元格属性，数值格式。
			FileOutputStream os;
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("Big Grid");
			os = new FileOutputStream("template.xlsx");
			wb.write(os);
	        os.close();
	        
			Map<String, XSSFCellStyle> styles = createStyles(wb);
			String sheetRef = sheet.getPackagePart().getPartName().getName();
			
			// 第二步，生成xml数据临时文件	        
	        File tmp = File.createTempFile("sheet", ".xml");
	        Writer fw = new OutputStreamWriter(new FileOutputStream(tmp), "UTF-8");
	        generate(fw, styles);
	        fw.close();
	        
	        // 第三步，创建ZIP输出流，将xml数据临时文件数据写入到ZIP文件中。
	        FileOutputStream out = new FileOutputStream("D:/Documents/Downloads/big-grid.xls");
	        substitute(new File("template.xlsx"), tmp, sheetRef.substring(1), out);
	        out.close();
	        
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	/**
	 * 
	* @Title: createExcel 
	* @Description: TODO(创建Excel文件) 
	* @param @param filepath
	* @param @param sheetName
	* @param @param titles Excel表头
	* @param @param fields 对象Data中Map的key
	* @param @param data  参数
	* @return void    返回类型 
	* @throws
	 */
	public static void createExcel(String filepath,String sheetName,String[] titles,String[] fields, List<Map> data) {
			try {
				// 第一步.创建一个临时的 excel 文件，配置单元格属性，数值格式。
				FileOutputStream os;
				XSSFWorkbook wb = new XSSFWorkbook();
				XSSFSheet sheet = wb.createSheet(sheetName);
				os = new FileOutputStream("template.xlsx");
				wb.write(os);
		        os.close();
		        
				String sheetRef = sheet.getPackagePart().getPartName().getName();
				
				// 第二步，生成xml数据临时文件	        
		        File tmp = File.createTempFile("sheet", ".xml");
		        Writer fw = new OutputStreamWriter(new FileOutputStream(tmp), "UTF-8");
		        SpreadsheetWriter sw = new SpreadsheetWriter(fw);
		        sw.beginSheet();
		        //插入表头
		        sw.insertRow(0);
		        for(int i=0;i<titles.length;i++){
		        	sw.createCell(i,titles[i]);
		        }
		        sw.endRow();
		        //插入数据
		        for(int rownum = 1; rownum <= data.size(); rownum++) {
		            sw.insertRow(rownum);
		            Map rowMap = data.get(rownum-1);
		            int column=0;
		            for(String key:fields) {
		            	Object value=rowMap.get(key);
		            	 if(null!=value){
		            		 sw.createCell(column,value.toString());
			             }else{
			            	 sw.createCell(column,"");
			             }
		            	 column++;
		            }
		            sw.endRow();
		        }
		        sw.endSheet();
		        fw.close();
		        
		        // 第三步，创建ZIP输出流，将xml数据临时文件数据写入到ZIP文件中。
		        File file = new File(filepath);
		        if(!file.getParentFile().exists()) {   
		            file.getParentFile().mkdirs();
		        }  
		        file.createNewFile();
		        FileOutputStream out = new FileOutputStream(file);
		        substitute(new File("template.xlsx"), tmp, sheetRef.substring(1), out);
		        out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	public  static <T> void insertExcelFirst(){
		try {
			// 第一步.创建一个临时的 excel 文件，配置单元格属性，数值格式。
			FileOutputStream os;
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("Big Grid");
			os = new FileOutputStream("template.xlsx");
			wb.write(os);
	        os.close();
	        
			Map<String, XSSFCellStyle> styles = createStyles(wb);
			String sheetRef = sheet.getPackagePart().getPartName().getName();
			
			// 第二步，生成xml数据临时文件	        
	        File tmp = File.createTempFile("sheet", ".xml");
	        Writer fw = new OutputStreamWriter(new FileOutputStream(tmp), "UTF-8");
	        SpreadsheetWriter write = getWrite(fw, styles);
	        
	        
	        generate(fw, styles);
	        fw.close();
	        
	        // 第三步，创建ZIP输出流，将xml数据临时文件数据写入到ZIP文件中。
	        FileOutputStream out = new FileOutputStream("D:/Documents/Downloads/big-grid.xls");
	        substitute(new File("template.xlsx"), tmp, sheetRef.substring(1), out);
	        out.close();
	        
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public  static <T> void insertExcelSecond(String[] headers, List<T> dataList,
			List showNameList,int page,int rows){
		try {
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
    /**
     * 创建样式表
     */
    private static Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb){
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
    
    private static SpreadsheetWriter getWrite(Writer out, Map<String, XSSFCellStyle> styles) throws Exception {

        SpreadsheetWriter sw = new SpreadsheetWriter(out);
        sw.beginSheet();

        //insert header row
        sw.insertRow(0);
        int styleIndex = styles.get("header").getIndex();
        sw.createCell(0, "Title", styleIndex);
        sw.createCell(1, "% Change", styleIndex);
        sw.createCell(2, "Ratio", styleIndex);
        sw.createCell(3, "Expenses", styleIndex);
        sw.createCell(4, "Date", styleIndex);

        sw.endRow();
        return sw;
    }
    
    private static void generate(Writer out, Map<String, XSSFCellStyle> styles) throws Exception {

        Random rnd = new Random();
        Calendar calendar = Calendar.getInstance();

        SpreadsheetWriter sw = new SpreadsheetWriter(out);
        sw.beginSheet();

        //insert header row
        sw.insertRow(0);
        int styleIndex = styles.get("header").getIndex();
        sw.createCell(0, "Title", styleIndex);
        sw.createCell(1, "% Change", styleIndex);
        sw.createCell(2, "Ratio", styleIndex);
        sw.createCell(3, "Expenses", styleIndex);
        sw.createCell(4, "Date", styleIndex);

        sw.endRow();

        //write data rows
        for (int rownum = 1; rownum < 200000; rownum++) {
            sw.insertRow(rownum);

            sw.createCell(0, "Hello, " + rownum + "!");
            sw.createCell(1, (double)rnd.nextInt(100)/100, styles.get("percent").getIndex());
            sw.createCell(2, (double)rnd.nextInt(10)/10, styles.get("coeff").getIndex());
            sw.createCell(3, rnd.nextInt(10000), styles.get("currency").getIndex());
            sw.createCell(4, calendar, styles.get("date").getIndex());

            sw.endRow();

            calendar.roll(Calendar.DAY_OF_YEAR, 1);
        }
        sw.endSheet();
    }

    /**
     *
     * @param zipfile the template file
     * @param tmpfile the XML file with the sheet data
     * @param entry the name of the sheet entry to substitute, e.g. xl/worksheets/sheet1.xml
     * @param out the stream to write the result to
     */
	public static void substitute(File zipfile, File tmpfile, String entry, OutputStream out) throws IOException {
        ZipFile zip = new ZipFile(zipfile);

        ZipOutputStream zos = new ZipOutputStream(out);

        @SuppressWarnings("unchecked")
        Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zip.entries();
        while (en.hasMoreElements()) {
            ZipEntry ze = en.nextElement();
            if(!ze.getName().equals(entry)){
                zos.putNextEntry(new ZipEntry(ze.getName()));
                InputStream is = zip.getInputStream(ze);
                copyStream(is, zos);
                is.close();
            }
        }
        zos.putNextEntry(new ZipEntry(entry));
        InputStream is = new FileInputStream(tmpfile);
        copyStream(is, zos);
        is.close();

        zos.close();
    }

    private static void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] chunk = new byte[1024];
        int count;
        while ((count = in.read(chunk)) >=0 ) {
          out.write(chunk,0,count);
        }
    }

    /**
     * Writes spreadsheet data in a Writer.
     * (YK: in future it may evolve in a full-featured API for streaming data in Excel)
     */
    public static class SpreadsheetWriter {
        private final Writer _out;
        private int _rownum;

        public SpreadsheetWriter(Writer out){
            _out = out;
        }

        public void beginSheet() throws IOException {
            _out.write("<?xml version=\"1.0\" encoding=\""+"UTF-8"+"\"?>" +
                    "<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">" );
            _out.write("<sheetData>\n");
        }

        public void endSheet() throws IOException {
            _out.write("</sheetData>");
            _out.write("</worksheet>");
        }

        /**
         * Insert a new row
         *
         * @param rownum 0-based row number
         */
        public void insertRow(int rownum) throws IOException {
            _out.write("<row r=\""+(rownum+1)+"\">\n");
            this._rownum = rownum;
        }

        /**
         * Insert row end marker
         */
        public void endRow() throws IOException {
            _out.write("</row>\n");
        }

        public void createCell(int columnIndex, String value, int styleIndex) throws IOException {
            String ref = new CellReference(_rownum, columnIndex).formatAsString();
            _out.write("<c r=\""+ref+"\" t=\"inlineStr\"");
            if(styleIndex != -1) _out.write(" s=\""+styleIndex+"\"");
            _out.write(">");
            _out.write("<is><t>"+value+"</t></is>");
            _out.write("</c>");
        }

        public void createCell(int columnIndex, String value) throws IOException {
            createCell(columnIndex, value, -1);
        }

        public void createCell(int columnIndex, double value, int styleIndex) throws IOException {
            String ref = new CellReference(_rownum, columnIndex).formatAsString();
            _out.write("<c r=\""+ref+"\" t=\"n\"");
            if(styleIndex != -1) _out.write(" s=\""+styleIndex+"\"");
            _out.write(">");
            _out.write("<v>"+value+"</v>");
            _out.write("</c>");
        }

        public void createCell(int columnIndex, double value) throws IOException {
            createCell(columnIndex, value, -1);
        }

        public void createCell(int columnIndex, Calendar value, int styleIndex) throws IOException {
            createCell(columnIndex, DateUtil.getExcelDate(value, false), styleIndex);
        }
    }
}
