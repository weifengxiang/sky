//初始化
function initSysRoleWidget(paramOpts){
	_sysRole=paramOpts.data;
	_callbacks.add(paramOpts.callBack);
	loadRoleWidget();
	loadSysWidget();
}
 /**
 *删除角色微件
 **/
function delSysRoleWidget(){
	var checkeds=$('#listsysrolewidgetdg').datagrid('getChecked');
	if(null==checkeds||checkeds.length<1){
		$.messager.alert('提示','请选择要删除的记录','info');
		return;
	}else{
		var msg="确定要删除"+checkeds.length+"条数据?";
		$.messager.confirm("删除确认",msg,
		function (r){
			if(r){
				SKY_EASYUI.mask('正在进行删除，请稍等...');
				var url = SKY.urlCSRF(basepath+'sys/SysRole/delSysRoleWidget');
				var params = {
							"delRows":JSON.stringify(checkeds)
						};
				$.ajax({
		    		url:url,
		    		type: "POST",
		    		data:params,
		    		dataType:'json',
		    		success:function(data){
		    			SKY_EASYUI.unmask();
		    			$.messager.alert("提示",data.name,"info");
		    			if(data.code != '0'){
		    				$('#listsysrolewidgetdg').datagrid('reload');
		    			}
		    		}
				});
			}else{
				return;
			}
		}
		);
	}
}
/**
 * 加载角色微件
 */
function loadRoleWidget(){
	$('#listsysrolewidgetdg').datagrid('options').url=SKY.urlCSRF(basepath+'sys/SysRole/getSysRoleWidgetByPage');
	$('#listsysrolewidgetdg').datagrid('load', {
		filter : function(){
			var ft = new HashMap();
			ft.put("roleCode@=", _sysRole.roleCode);
			return ft.getJSON();
		}
	});
}
/**
 * 加载系统微件
 */
function loadSysWidget(){
	$('#listsyswidgetdg').datagrid('options').url=SKY.urlCSRF(basepath+'sys/SysWidget/getSysWidgetByPage');
	$('#listsyswidgetdg').datagrid('load', {
		filter : function(){
			var ft = new HashMap();
			ft.put("isSys@=", '0');
			return ft.getJSON();
		}
	});
}
/**
 * 保存角色微件
 */
function saveRoleWidget(){
	var gridId="listsysrolewidgetdg";
	if(!SKY_EASYUI.endEditing(gridId)){
		$.messager.alert("错误","请正确输入必填信息","error");    
		return;
	}
	// 获取新增的数据
	var insertRows = $('#'+gridId).datagrid('getChanges','inserted');
	// 获取修改的数据
	var updateRows = $('#'+gridId).datagrid('getChanges','updated');
	
	if(insertRows.length == 0 
			&& updateRows.length == 0){							
		$.messager.alert("提示","无数据更新！","info");
		return;
	}
	var msg = "";
	if(null!=insertRows && insertRows.length > 0){
		msg = "新增："+insertRows.length+"行<br>";
	}
	if(null!=updateRows && updateRows.length > 0){
		msg =msg+ "更新："+updateRows.length+"行<br>";
	}
	msg = msg+"确认提交?";	
	var url = basepath+'sys/SysRole/saveAddEditSysRoleWidgetList';
	url=SKY.urlCSRF(url);
	// 提示保存信息
	$.messager.confirm("保存确认",msg,
	     function (r){
			if(r){
				SKY_EASYUI.mask('正在进行保存，请稍等...');
				var params = {
							"insertRows":JSON.stringify(insertRows),
							"updateRows":JSON.stringify(updateRows)
						};
				$.ajax({
		    		url:url,
		    		type: "POST",
		    		data:params,
		    		dataType:'json',
		    		success:function(data){
		    			SKY_EASYUI.unmask();
		    			$.messager.alert("提示",data.name,"info");
		    			if(data.code != '0'){
		    				$('#'+gridId).datagrid('reload');
		    			}else{
		    				$('#'+gridId).datagrid('rejectChanges');
		    			}
		    		}
				});
			}else{// 点击取消
				return;
			}
		}
	);	
}
function addSysRoleWidget(row){
	var rows = $('#listsysrolewidgetdg').datagrid('getData').rows;
	for(var i=0;i<rows.length;i++){
		if(rows[i].widgetId==row.id){
			$.messager.alert('提示','角色已分配微件不能重复添加！','error');
			return;
		}
	};
	$('#listsysrolewidgetdg').datagrid('appendRow',{
		roleCode:_sysRole.code,
		widgetId:row.id,
		widgetName:row.name,
		seq:row.seq
	});
}