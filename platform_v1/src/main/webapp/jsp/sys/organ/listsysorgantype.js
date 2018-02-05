//初始化
function init(){
	$('#listsysorgantypedg').datagrid('options').url=SKY.urlCSRF(basepath+'sys/SysOrganType/getSysOrganTypeByPage');
	$('#listsysorgantypedg').datagrid('load', {
		filter : function(){
			var ft = new HashMap();
			return ft.getJSON();
		}
	});
}
 /**
 *添加组织机构类型
 **/
function addSysOrganType(){
	var opts={
				title:'添加组织机构类型',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysOrganType/initAddSysOrganTypePage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initAddSysOrganTypePage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	searchButton();
		                };
		            	this.content.initAddSysOrganTypePage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
 /**
 *删除组织机构类型
 **/
function delSysOrganType(){
	var checkeds=$('#listsysorgantypedg').datagrid('getChecked');
	if(null==checkeds){
		return;
	}else{
		var msg="确定要删除"+checkeds.length+"条数据?";
		$.messager.confirm("删除确认",msg,
		function (r){
			if(r){
				SKY_EASYUI.mask('正在进行删除，请稍等...');
				var url = SKY.urlCSRF(basepath+'sys/SysOrganType/delSysOrganType');
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
		    				$('#listsysorgantypedg').datagrid('reload');
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
*修改组织机构类型
**/
function editSysOrganType(){
	var checkeds=$('#listsysorgantypedg').datagrid('getChecked');
	if(null==checkeds||checkeds.length!=1){
		$.messager.alert('提示','请选择一条记录','info');
		return;
	}
	var opts={
				title:'修改组织机构类型',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysOrganType/initEditSysOrganTypePage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initEditSysOrganTypePage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=checkeds[0];
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	searchButton();
		                };
		            	this.content.initEditSysOrganTypePage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
/**
*查看明细
**/
function detailSysOrganType(){
	var checkeds=$('#listsysorgantypedg').datagrid('getChecked');
	if(null==checkeds||checkeds.length!=1){
		$.messager.alert('提示','请选择一条记录','info');
		return;
	}
	var opts={
				title:'组织机构类型明细',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysOrganType/initDetailSysOrganTypePage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initDetailSysOrganTypePage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=checkeds[0];
		                paramOpts.callBack=function(){
		                	dialog.close();
		                };
		            	this.content.initDetailSysOrganTypePage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
/**
 * 查询按钮
 */
function searchButton(){
	$('#listsysorgantypedg').datagrid('options').url=SKY.urlCSRF(basepath+'sys/SysOrganType/getSysOrganTypeByPage');
	$('#listsysorgantypedg').datagrid('load', {
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
			var seq =$('#q_seq').textbox("getValue");
			if(seq){
				ft.put("seq@=", seq);
			}
			var icon =$('#q_icon').textbox("getValue");
			if(icon){
				ft.put("icon@=", icon);
			}
			var note =$('#q_note').textbox("getValue");
			if(note){
				ft.put("note@=", note);
			}
			return ft.getJSON();
		}
	});
}