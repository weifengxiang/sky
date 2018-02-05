//初始化
function init(){
	initSysOrganTree();
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
						loadUserList();
					}
				}
			});
}
 /**
 *添加用户表
 **/
function addSysUser(){
	var selOrg=$('#organtree').tree('getSelected');
	if(!selOrg||!selOrg.data||!selOrg.data.id){
		$.messager.alert('提示','请选择组织机构','info');
		return;
	}
	var opts={
				id:'addSysUser',
				title:'添加用户',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysUser/initAddSysUserPage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initAddSysUserPage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.organ=selOrg.data;
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	loadUserList();
		                };
		            	this.content.initAddSysUserPage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
 /**
 *删除用户表
 **/
function delSysUser(){
	var checkeds=$('#listsysuserdg').datagrid('getChecked');
	if(null==checkeds||checkeds.length<1){
		$.messager.alert('提示','请选择要删除的记录','info');
		return;
	}else{
		var msg="确定要删除"+checkeds.length+"条数据?";
		$.messager.confirm("删除确认",msg,
		function (r){
			if(r){
				SKY_EASYUI.mask('正在进行删除，请稍等...');
				var url = SKY.urlCSRF(basepath+'sys/SysUser/delSysUser');
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
		    				$('#listsysuserdg').datagrid('reload');
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
*修改用户表
**/
function editSysUser(){
	var checkeds=$('#listsysuserdg').datagrid('getChecked');
	if(null==checkeds||checkeds.length!=1){
		$.messager.alert('提示','请选择一条记录','info');
		return;
	}
	var opts={
				id:'editSysUser',
				title:'修改用户',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysUser/initEditSysUserPage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initEditSysUserPage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=checkeds[0];
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	loadUserList();
		                };
		            	this.content.initEditSysUserPage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
/**
*查看明细
**/
function detailSysUser(){
	var checkeds=$('#listsysuserdg').datagrid('getChecked');
	if(null==checkeds||checkeds.length!=1){
		$.messager.alert('提示','请选择一条记录','info');
		return;
	}
	var opts={
				id:'detailSysUser',
				title:'用户明细',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysUser/initDetailSysUserPage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initDetailSysUserPage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=checkeds[0];
		                paramOpts.callBack=function(){
		                	dialog.close();
		                };
		            	this.content.initDetailSysUserPage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
/**
 * 查询
 */
function loadUserList(){
	var selOrg=$('#organtree').tree('getSelected');
	var organCode = selOrg.data.code;
	$('#listsysuserdg').datagrid('options').url=SKY.urlCSRF(basepath+'sys/SysUser/getSysUserByPage');
	$('#listsysuserdg').datagrid('load', {
		filter : function(){
			var ft = new HashMap();
			ft.put("organCode@=", organCode);
			return ft.getJSON();
		}
	});
}
/**
 * 功能权限管理
 */
function roleManage(){
	var checkeds=$('#listsysuserdg').datagrid('getChecked');
	if(null==checkeds||checkeds.length!=1){
		$.messager.alert('提示','请选择一条记录','info');
		return;
	}
	var opts={
				id:'SysUserRoleManage',
				title:'用户角色管理',
				width:750,
				height:600,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysUser/initSysUserRoleManagePage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initListSysUserRolePage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=checkeds[0];
		                paramOpts.callBack=function(){
		                	dialog.close();
		                };
		            	this.content.initListSysUserRolePage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        },
		        buttons:[{  
	                   text: '保存',  
	                   iconCls: 'icon-save',  
	                   handler: "saveSysUserRole"
	               },{  
	                   text: '关闭',  
	                   iconCls: 'icon-cancel',  
	                   handler: function(dialog){  
	                       dialog.close();  
	                   }  
	               }]
			  };
	SKY_EASYUI.open(opts);
}
/**
 * 数据权限管理
 */
function dataPermitManage(){
	var checkeds=$('#listsysuserdg').datagrid('getChecked');
	if(null==checkeds||checkeds.length!=1){
		$.messager.alert('提示','请选择一条记录','info');
		return;
	}
	var opts={
				id:'SysUserDataAccessManage',
				title:'用户数据权限管理',
				width:750,
				height:600,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysUser/initSysUserDataAccessManagePage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initSysUserDataAccessPage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=checkeds[0];
		                paramOpts.callBack=function(){
		                	dialog.close();
		                };
		            	this.content.initSysUserDataAccessPage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        },
		        buttons:[{  
	                   text: '保存',  
	                   iconCls: 'icon-save',  
	                   handler: "saveSysUserDataAccess"
	               },{  
	                   text: '关闭',  
	                   iconCls: 'icon-cancel',  
	                   handler: function(dialog){  
	                       dialog.close();  
	                   }  
	               }]
			  };
	SKY_EASYUI.open(opts);
}
/**
 * 密码重置
 */
function resetPwd(){
	var checkeds=$('#listsysuserdg').datagrid('getChecked');
	if(null==checkeds||checkeds.length<1){
		$.messager.alert('提示','请选择要重置的记录','info');
		return;
	}else{
		var msg="确定要重置"+checkeds.length+"条数据?";
		$.messager.confirm("重置确认",msg,
		function (r){
			if(r){
				SKY_EASYUI.mask('正在进行重置，请稍等...');
				var url = SKY.urlCSRF(basepath+'sys/SysUser/resetPwd');
				var params = {
							"resetRows":JSON.stringify(checkeds)
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
		    				$('#listsysuserdg').datagrid('reload');
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