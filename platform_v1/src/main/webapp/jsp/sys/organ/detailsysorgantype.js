/**
 * 初始化组织机构类型详细页面
 */
function initDetailSysOrganTypePage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
	var url=basepath+"sys/SysOrganType/getSysOrganTypeById?id="+paramOpts.data.id;
	$('#detailsysorgantypeform').form('load',SKY.urlCSRF(url));
}
