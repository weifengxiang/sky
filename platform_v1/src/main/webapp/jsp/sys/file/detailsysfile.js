/**
 * 初始化附件表详细页面
 */
function initDetailSysFilePage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
	var url=basepath+"sys/SysFile/getSysFileById?id="+paramOpts.data.id;
	$('#detailsysfileform').form('load',SKY.urlCSRF(url));
}
