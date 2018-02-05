/**
 * 初始化用户角色
 */
function initListSysUserRolePage(paramOpts){
	_user=paramOpts.data;
	initSysOrganTree();
	loadUserRoleList();
}
 /**
 *删除用户角色表
 **/
function delSysUserRole(){
	var checkeds=$('#listsysuserroledg').datagrid('getChecked');
	if(null==checkeds||checkeds.length<1){
		$.messager.alert('提示','请选择要删除的记录','info');
		return;
	}else{
		var msg="确定要删除"+checkeds.length+"条数据?";
		$.messager.confirm("删除确认",msg,
		function (r){
			if(r){
				SKY_EASYUI.mask('正在进行删除，请稍等...');
				var url = SKY.urlCSRF(basepath+'sys/SysUser/delSysUserRole');
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
		    				$('#listsysuserroledg').datagrid('reload');
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
 * 查询用户角色
 */
function loadUserRoleList(){
	$('#listsysuserroledg').datagrid('options').url=SKY.urlCSRF(basepath+'sys/SysUser/getSysUserRoleByPage');
	$('#listsysuserroledg').datagrid('load', {
		filter : function(){
			var ft = new HashMap();
			ft.put("userCode@=", _user.code);
			return ft.getJSON();
		}
	});
}
/**
 * 初始化组织机构树
 */
function initSysOrganTree() {
	var rootData=[
					{    
					    "id":"root",    
					    "text":"组织机构树[root]",    
					    "iconCls":"icon-2012080111634",
					    "state":"closed",
					    "data":{
					    	"code":"root",
					    	"name":"组织机构树",
					    }
					}
	              ];
	var url = basepath + 'sys/SysOrgan/getSysOrganTree';
	url=SKY.urlCSRF(url);
	$('#organtree').tree(
			{
				data : rootData,
				lines:true,
				method : 'POST',
				onBeforeExpand : function(node, param) {
					$('#organtree').tree('options').url = url + "&data="
							+ JSON.stringify(node.data);
				},
				onClick : function(node) {
					var data=node.data;
					if(data.id){
						loadRoleList();
					}
				}
			});
}
/**
 * 查询角色列表
 */
function loadRoleList(){
	var selOrg=$('#organtree').tree('getSelected');
	var organCode = selOrg.data.code;
	$('#listsysroledg').datagrid('options').url=SKY.urlCSRF(basepath+'sys/SysRole/getSysRoleByPage');
	$('#listsysroledg').datagrid('load', {
		filter : function(){
			var ft = new HashMap();
			ft.put("organCode@=", organCode);
			return ft.getJSON();
		}
	});
}
/**
 * 添加角色
 * @param index
 * @param row
 */
function listsysroledgOnCheck(index, row){
	var rows = $('#listsysuserroledg').datagrid('getData').rows;
	for(var i=0;i<rows.length;i++){
		if(rows[i].roleCode==row.code){
			$.messager.alert('提示','用户已分配该角色不能重复添加！','error');
			return;
		}
	};
	$('#listsysuserroledg').datagrid('appendRow',{
		userCode:_user.code,
		roleCode:row.code,
		roleName:row.name
	});
}
/**
 * 保存用户角色
 */
function saveSysUserRole(){
	var gridId="listsysuserroledg";
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
	var url = basepath+'sys/SysUser/saveAddEditSysUserRoleList';
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