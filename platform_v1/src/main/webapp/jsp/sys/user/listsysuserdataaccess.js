//初始化
function initSysUserDataAccessPage(paramOpts){
	_user=paramOpts.data;
	initSysOrganTree();
	loadSysUserDataAccessList();
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
				checkbox:true,
				cascadeCheck:false,
				lines:true,
				method : 'POST',
				onBeforeExpand : function(node, param) {
					$('#organtree').tree('options').url = url + "&data="
							+ JSON.stringify(node.data);
				},
				onClick : function(node) {
					var data=node.data;
					if(data.id){
						loadOrganDetail(data);
					}
				},
				onCheck:function(node, checked){
					if(checked){
						var data=node.data;
						if(data.id){
							addSysUserDataAccess(node.data);
						}
					}
				}
			});
}
/**
 * 加载组织机构明细
 */
function loadOrganDetail(data){
	$('#detail').empty();
	$('#detail').append($('#organDetailTemplate').html());
	$.parser.parse('#detail');
	
	$('#type').combobox(
		{url : SKY.urlCSRF(basepath+'sys/common/queryComboBoxData'),
		 queryParams: {
			"tablename" : "sys_organ_type",                                
			"fields" : "code,name"
		 }
		});
	var url=basepath+"sys/SysOrgan/getSysOrganById?id="+data.id;
	$('#detailsysorganform').form('load',SKY.urlCSRF(url));
}
 /**
 *添加用户数据权限表
 **/
function addSysUserDataAccess(data){
	var rows = $('#listsysuserdataaccessdg').datagrid('getData').rows;
	for(var i=0;i<rows.length;i++){
		if(rows[i].organCode==data.code){
			$.messager.alert('提示','用户已分配该组织不能重复添加！','error');
			return;
		}
	}
	$('#listsysuserdataaccessdg').datagrid('appendRow',{
		userCode:_user.code,
		organCode:data.code,
		organName:data.name
	});
}
 /**
 *删除用户数据权限表
 **/
function delSysUserDataAccess(){
	var checkeds=$('#listsysuserdataaccessdg').datagrid('getChecked');
	if(null==checkeds||checkeds.length<1){
		$.messager.alert('提示','请选择要删除的记录','info');
		return;
	}else{
		var msg="确定要删除"+checkeds.length+"条数据?";
		$.messager.confirm("删除确认",msg,
		function (r){
			if(r){
				SKY_EASYUI.mask('正在进行删除，请稍等...');
				var url = SKY.urlCSRF(basepath+'sys/SysUser/delSysUserDataAccess');
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
		    				$('#listsysuserdataaccessdg').datagrid('reload');
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
 * 查询用户数据权限
 */
function loadSysUserDataAccessList(){
	$('#listsysuserdataaccessdg').datagrid('options').url=SKY.urlCSRF(basepath+'sys/SysUser/getSysUserDataAccessByPage');
	$('#listsysuserdataaccessdg').datagrid('load', {
		filter : function(){
			var ft = new HashMap();
			ft.put("userCode@=", _user.code);
			return ft.getJSON();
		}
	});
}
/**
 * 保存用户数据权限
 */
function saveSysUserDataAccess(){
	var gridId="listsysuserdataaccessdg";
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
	var url = basepath+'sys/SysUser/saveAddEditSysUserDataAccessList';
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