/**
 * 初始化用户表详细页面
 */
function initDetailSysUserPage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
	var url=basepath+"sys/SysUser/getSysUserById?id="+paramOpts.data.id;
	$('#detailsysuserform').form('load',SKY.urlCSRF(url));
}
