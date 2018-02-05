//初始化
function init(){
	$('#listsyswidgetdg').datagrid('options').url=SKY.urlCSRF(basepath+'sys/SysWidget/getSysWidgetByPage');
	$('#listsyswidgetdg').datagrid('load', {
		filter : function(){
			var ft = new HashMap();
			return ft.getJSON();
		}
	});
}
 /**
 *添加微件
 **/
function addSysWidget(){
	var opts={
				id:'addSysWidget',
				title:'添加微件',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysWidget/initAddSysWidgetPage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initAddSysWidgetPage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	searchButton();
		                };
		            	this.content.initAddSysWidgetPage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
 /**
 *删除微件
 **/
function delSysWidget(){
	var checkeds=$('#listsyswidgetdg').datagrid('getChecked');
	if(null==checkeds||checkeds.length<1){
		$.messager.alert('提示','请选择要删除的记录','info');
		return;
	}else{
		var msg="确定要删除"+checkeds.length+"条数据?";
		$.messager.confirm("删除确认",msg,
		function (r){
			if(r){
				SKY_EASYUI.mask('正在进行删除，请稍等...');
				var url = SKY.urlCSRF(basepath+'sys/SysWidget/delSysWidget');
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
		    				$('#listsyswidgetdg').datagrid('reload');
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
*修改微件
**/
function editSysWidget(){
	var checkeds=$('#listsyswidgetdg').datagrid('getChecked');
	if(null==checkeds||checkeds.length!=1){
		$.messager.alert('提示','请选择一条记录','info');
		return;
	}
	var opts={
				id:'editSysWidget',
				title:'修改微件',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysWidget/initEditSysWidgetPage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initEditSysWidgetPage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=checkeds[0];
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	searchButton();
		                };
		            	this.content.initEditSysWidgetPage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
/**
*查看明细
**/
function detailSysWidget(){
	var checkeds=$('#listsyswidgetdg').datagrid('getChecked');
	if(null==checkeds||checkeds.length!=1){
		$.messager.alert('提示','请选择一条记录','info');
		return;
	}
	var opts={
				id:'detailSysWidget',
				title:'微件明细',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysWidget/initDetailSysWidgetPage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initDetailSysWidgetPage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=checkeds[0];
		                paramOpts.callBack=function(){
		                	dialog.close();
		                };
		            	this.content.initDetailSysWidgetPage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
/**
 * 查询按钮
 */
function searchButton(){
	$('#listsyswidgetdg').datagrid('options').url=SKY.urlCSRF(basepath+'sys/SysWidget/getSysWidgetByPage');
	$('#listsyswidgetdg').datagrid('load', {
		filter : function(){
			var ft = new HashMap();
			var name =$('#q_name').textbox("getValue");
			if(name){
				ft.put("name@=", name);
			}
			return ft.getJSON();
		}
	});
}