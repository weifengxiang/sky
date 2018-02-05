package org.sky.utils;

public class BizTools {

	/**
	 * 解析物料条码
	 * @param itemBarCode
	 * @return
	 */
	public static String anaItemBarCode(String itemBarCode,String name){
		//5位厂商+*+13位零部件图号+2位颜色号+7位批次号:00000*1111111111111223333333
		//5位厂商+13位零部件图号+2位颜色号+8位批次号:  1111122222222222223344444444
		String supplierCode="";//供应商编号
		String itemCode="";
		String colorCode="";
		String batchCode="";
		if("*".equals(itemBarCode.substring(5, 6))){
			//第五位是*
			supplierCode=itemBarCode.substring(0,5);
			itemCode=itemBarCode.substring(6,19);
			colorCode=itemBarCode.substring(19,21);
			batchCode=itemBarCode.substring(21,28);	
		}else{
			supplierCode=itemBarCode.substring(0,5);
			itemCode=itemBarCode.substring(5,18);
			colorCode=itemBarCode.substring(18,20);
			batchCode=itemBarCode.substring(20,28);
		}
		if("supplierCode".equals(name)){
			return supplierCode;
		}
		if("itemCode".equals(name)){
			return itemCode;
		}
		if("colorCode".equals(name)){
			return colorCode;
		}
		if("batchCode".equals(name)){
			return batchCode;
		}
		return "";
	}
	public static void main(String[] args){
		System.out.println(anaItemBarCode("1111122222222222223344444444","supplierCode"));
		System.out.println(anaItemBarCode("1111122222222222223344444444","itemCode"));
		System.out.println(anaItemBarCode("1111122222222222223344444444","colorCode"));
		System.out.println(anaItemBarCode("1111122222222222223344444444","batchCode"));
	}
}
