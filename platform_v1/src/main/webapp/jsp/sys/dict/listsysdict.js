//初始化
function init(){
	$('#listsysdictdg').datagrid('options').url=SKY.urlCSRF(basepath+'sys/SysDict/getSysDictByPage');
	$('#listsysdictdg').datagrid('load', {
		filter : function(){
			var ft = new HashMap();
			return ft.getJSON();
		}
	});
}
 /**
 *添加字典表
 **/
function addSysDict(){
	var opts={
				id:'addSysDict',
				title:'添加字典表',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysDict/initAddSysDictPage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initAddSysDictPage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	loadSysDict();
		                };
		            	this.content.initAddSysDictPage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
 /**
 *删除字典表
 **/
function delSysDict(){
	var checkeds=$('#listsysdictdg').datagrid('getChecked');
	if(null==checkeds||checkeds.length<1){
		$.messager.alert('提示','请选择要删除的记录','info');
		return;
	}else{
		var msg="确定要删除"+checkeds.length+"条数据?";
		$.messager.confirm("删除确认",msg,
		function (r){
			if(r){
				SKY_EASYUI.mask('正在进行删除，请稍等...');
				var url = SKY.urlCSRF(basepath+'sys/SysDict/delSysDict');
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
		    				$('#listsysdictdg').datagrid('reload');
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
*修改字典表
**/
function editSysDict(){
	var checkeds=$('#listsysdictdg').datagrid('getChecked');
	if(null==checkeds||checkeds.length!=1){
		$.messager.alert('提示','请选择一条记录','info');
		return;
	}
	var opts={
				id:'editSysDict',
				title:'修改字典表',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysDict/initEditSysDictPage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initEditSysDictPage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=checkeds[0];
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	loadSysDict();
		                };
		            	this.content.initEditSysDictPage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
/**
 * 查询字典
 */
function loadSysDict(){
	$('#listsysdictdg').datagrid('options').url=SKY.urlCSRF(basepath+'sys/SysDict/getSysDictByPage');
	$('#listsysdictdg').datagrid('load', {
		filter : function(){
			var ft = new HashMap();
			var code =$('#q_code').textbox("getValue");
			if(code){
				ft.put("code@=", code);
			}
			var name =$('#q_name').textbox("getValue");
			if(name){
				ft.put("name@=", name);
			}
			return ft.getJSON();
		}
	});
}
/**
 *添加字典项表
 **/
function addSysDictItem(){
	var selected = $('#listsysdictdg').datagrid('getSelected');
	if(null==selected){
		$.messager.alert('提示','请选择要一条记录','info');
		return;
	}
	var opts={
				id:'addSysDictItem',
				title:'添加字典项表',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysDict/initAddSysDictItemPage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initAddSysDictItemPage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=selected;
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	loadDictItem();
		                };
		            	this.content.initAddSysDictItemPage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
 /**
 *删除字典项表
 **/
function delSysDictItem(){
	var checkeds=$('#listsysdictitemdg').datagrid('getChecked');
	if(null==checkeds||checkeds.length<1){
		$.messager.alert('提示','请选择要删除的记录','info');
		return;
	}else{
		var msg="确定要删除"+checkeds.length+"条数据?";
		$.messager.confirm("删除确认",msg,
		function (r){
			if(r){
				SKY_EASYUI.mask('正在进行删除，请稍等...');
				var url = SKY.urlCSRF(basepath+'sys/SysDict/delSysDictItem');
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
		    				$('#listsysdictitemdg').datagrid('reload');
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
*修改字典项表
**/
function editSysDictItem(){
	var checkeds=$('#listsysdictitemdg').datagrid('getChecked');
	if(null==checkeds||checkeds.length!=1){
		$.messager.alert('提示','请选择一条记录','info');
		return;
	}
	var opts={
				id:'editSysDictItem',
				title:'修改字典项表',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysDict/initEditSysDictItemPage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initEditSysDictItemPage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=checkeds[0];
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	loadDictItem();
		                };
		            	this.content.initEditSysDictItemPage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
/**
 * 查询字典项
 */
function loadDictItem(row){
	var row = $('#listsysdictdg').datagrid('getSelected');
	$('#listsysdictitemdg').datagrid('options').url=SKY.urlCSRF(basepath+'sys/SysDict/getSysDictItemByPage');
	$('#listsysdictitemdg').datagrid('load', {
		filter : function(){
			var ft = new HashMap();
			ft.put("dictCode@=", row.code);
			return ft.getJSON();
		},
		sortfield:"seq"
	});
}