/**
 * 初始化微件详细页面
 */
function initDetailSysWidgetPage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
	var url=basepath+"sys/SysWidget/getSysWidgetById?id="+paramOpts.data.id;
	$('#detailsyswidgetform').form('load',SKY.urlCSRF(url));
}
