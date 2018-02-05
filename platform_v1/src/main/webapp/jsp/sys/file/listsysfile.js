//初始化
function init(){
	$('#listsysfiledg').datagrid('options').url=SKY.urlCSRF(basepath+'sys/SysFile/getSysFileByPage');
	$('#listsysfiledg').datagrid('load', {
		filter : function(){
			var ft = new HashMap();
			return ft.getJSON();
		}
	});
}
 /**
 *添加附件表
 **/
function addSysFile(){
	var opts={
				id:'addSysFile',
				title:'添加附件表',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysFile/initAddSysFilePage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initAddSysFilePage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	searchButton();
		                };
		            	this.content.initAddSysFilePage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
 /**
 *删除附件表
 **/
function delSysFile(){
	var checkeds=$('#listsysfiledg').datagrid('getChecked');
	if(null==checkeds||checkeds.length<1){
		$.messager.alert('提示','请选择要删除的记录','info');
		return;
	}else{
		var msg="确定要删除"+checkeds.length+"条数据?";
		$.messager.confirm("删除确认",msg,
		function (r){
			if(r){
				SKY_EASYUI.mask('正在进行删除，请稍等...');
				var url = SKY.urlCSRF(basepath+'sys/SysFile/delSysFile');
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
		    				$('#listsysfiledg').datagrid('reload');
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
*修改附件表
**/
function editSysFile(){
	var checkeds=$('#listsysfiledg').datagrid('getChecked');
	if(null==checkeds||checkeds.length!=1){
		$.messager.alert('提示','请选择一条记录','info');
		return;
	}
	var opts={
				id:'editSysFile',
				title:'修改附件表',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysFile/initEditSysFilePage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initEditSysFilePage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=checkeds[0];
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	searchButton();
		                };
		            	this.content.initEditSysFilePage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
/**
*查看明细
**/
function detailSysFile(){
	var checkeds=$('#listsysfiledg').datagrid('getChecked');
	if(null==checkeds||checkeds.length!=1){
		$.messager.alert('提示','请选择一条记录','info');
		return;
	}
	var opts={
				id:'detailSysFile',
				title:'附件表明细',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysFile/initDetailSysFilePage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initDetailSysFilePage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=checkeds[0];
		                paramOpts.callBack=function(){
		                	dialog.close();
		                };
		            	this.content.initDetailSysFilePage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
/**
 * 查询按钮
 */
function searchButton(){
	$('#listsysfiledg').datagrid('options').url=SKY.urlCSRF(basepath+'sys/SysFile/getSysFileByPage');
	$('#listsysfiledg').datagrid('load', {
		filter : function(){
			var ft = new HashMap();
			var type =$('#q_type').textbox("getValue");
			if(type){
				ft.put("type@=", type);
			}
			var name =$('#q_name').textbox("getValue");
			if(name){
				ft.put("name@=", name);
			}
			var bizCode =$('#q_bizCode').textbox("getValue");
			if(bizCode){
				ft.put("bizCode@=", bizCode);
			}
			var bizType =$('#q_bizType').textbox("getValue");
			if(bizType){
				ft.put("bizType@=", bizType);
			}
			
			return ft.getJSON();
		}
	});
}
/**
 * 附件上传
 */
function uploadSysFile(){
	var opts={
			bizCode:'6666',
			bizType:'Test',
			callback:function(){
				searchButton();
			}
	};
	SKY_EASYUI.upLoad(opts);
}
/**
 * 附件下载
 */
function downloadSysFile(){
	var checkeds=$('#listsysfiledg').datagrid('getChecked');
	if(null==checkeds||checkeds.length!=1){
		$.messager.alert('提示','请选择一条记录','info');
		return;
	}
	SKY_EASYUI.downLoad(checkeds[0].path);
}