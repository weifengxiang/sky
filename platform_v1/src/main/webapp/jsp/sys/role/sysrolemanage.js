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
						loadRoleList();
					}
				}
			});
}
 /**
 *添加角色表
 **/
function addSysRole(){
	var selOrg=$('#organtree').tree('getSelected');
	if(!selOrg||!selOrg.data||!selOrg.data.id){
		$.messager.alert('提示','请选择组织机构','info');
		return;
	}
	var opts={
				id:'addSysRole',
				title:'添加角色',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysRole/initAddSysRolePage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initAddSysRolePage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.organ=selOrg.data;
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	loadRoleList();
		                };
		            	this.content.initAddSysRolePage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
 /**
 *删除角色表
 **/
function delSysRole(){
	var checkeds=$('#listsysroledg').datagrid('getChecked');
	if(null==checkeds||checkeds.length<1){
		$.messager.alert('提示','请选择要删除的记录','info');
		return;
	}else{
		var msg="确定要删除"+checkeds.length+"条数据?";
		$.messager.confirm("删除确认",msg,
		function (r){
			if(r){
				SKY_EASYUI.mask('正在进行删除，请稍等...');
				var url = SKY.urlCSRF(basepath+'sys/SysRole/delSysRole');
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
		    				$('#listsysroledg').datagrid('reload');
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
*修改角色表
**/
function editSysRole(){
	var checkeds=$('#listsysroledg').datagrid('getChecked');
	if(null==checkeds||checkeds.length!=1){
		$.messager.alert('提示','请选择一条记录','info');
		return;
	}
	var opts={
				id:'editSysRole',
				title:'修改角色',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysRole/initEditSysRolePage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initEditSysRolePage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=checkeds[0];
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	searchButton();
		                };
		            	this.content.initEditSysRolePage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
/**
 * 查询
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
 * 角色功能管理
 */
function roleOperation(){
	var checkeds=$('#listsysroledg').datagrid('getChecked');
	if(null==checkeds||checkeds.length!=1){
		$.messager.alert('提示','请选择要一条分配功能的角色','info');
		return;
	}
	var opts={
				id:'editSysRoleOperation',
				title:'角色功能管理',
				width:800,
				height:600,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysRole/initRoleOpeTreePage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initRoleOpeTreeHelpPage){//判断弹出窗体iframe中的driveInit方法是否存在 
		            	this.content.initRoleOpeTreeHelpPage(checkeds[0]);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        },
		        buttons:[{  
		                   text: '保存',  
		                   iconCls: 'icon-save',  
		                   handler: "saveRoleOperation"
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
 * 角色微件管理
 */
function roleWidget(){
	var checkeds=$('#listsysroledg').datagrid('getChecked');
	if(null==checkeds||checkeds.length!=1){
		$.messager.alert('提示','请选择要一条分配微件的角色','info');
		return;
	}
	var opts={
				id:'editSysRoleWidget',
				title:'角色微件管理',
				width:800,
				height:600,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysRole/initSysRoleWidgetPage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initSysRoleWidget){//判断弹出窗体iframe中的driveInit方法是否存在 
		            	var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=checkeds[0];
		                paramOpts.callBack=function(){
		                	dialog.close();
		                };
		            	this.content.initSysRoleWidget(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        },
		        buttons:[{  
		                   text: '保存',  
		                   iconCls: 'icon-save',  
		                   handler: "saveRoleWidget"
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