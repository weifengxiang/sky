//初始化
function init(){
	${r"$('#"}list${className?lower_case}dg${r"'"}).datagrid('options').url=SKY.urlCSRF(basepath+'${urlPrefix}/${className}/get${className}ByPage');
	${r"$('#"}list${className?lower_case}dg${r"'"}).datagrid('load', {
		filter : function(){
			var ft = new HashMap();
			return ft.getJSON();
		}
	});
}
 /**
 *添加${comment}
 **/
function add${className}(){
	var opts={
				id:'add${className}',
				title:'添加${comment}',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'${urlPrefix}/${className}/initAdd${className}Page'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initAdd${className}Page){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	searchButton();
		                };
		            	this.content.initAdd${className}Page(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
 /**
 *删除${comment}
 **/
function del${className}(){
	var checkeds=$('#list${className?lower_case}dg').datagrid('getChecked');
	if(null==checkeds||checkeds.length<1){
		$.messager.alert('提示','请选择要删除的记录','info');
		return;
	}else{
		var msg="确定要删除"+checkeds.length+"条数据?";
		$.messager.confirm("删除确认",msg,
		function (r){
			if(r){
				SKY_EASYUI.mask('正在进行删除，请稍等...');
				var url = SKY.urlCSRF(basepath+'${urlPrefix}/${className}/del${className}');
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
		    				$('#list${className?lower_case}dg').datagrid('reload');
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
*修改${comment}
**/
function edit${className}(){
	var checkeds=$('#list${className?lower_case}dg').datagrid('getChecked');
	if(null==checkeds||checkeds.length!=1){
		$.messager.alert('提示','请选择一条记录','info');
		return;
	}
	var opts={
				id:'edit${className}',
				title:'修改${comment}',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'${urlPrefix}/${className}/initEdit${className}Page'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initEdit${className}Page){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=checkeds[0];
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	searchButton();
		                };
		            	this.content.initEdit${className}Page(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
/**
*查看明细
**/
function detail${className}(){
	var checkeds=$('#list${className?lower_case}dg').datagrid('getChecked');
	if(null==checkeds||checkeds.length!=1){
		$.messager.alert('提示','请选择一条记录','info');
		return;
	}
	var opts={
				id:'detail${className}',
				title:'${comment}明细',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'${urlPrefix}/${className}/initDetail${className}Page'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initDetail${className}Page){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=checkeds[0];
		                paramOpts.callBack=function(){
		                	dialog.close();
		                };
		            	this.content.initDetail${className}Page(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
/**
 * 查询按钮
 */
function searchButton(){
	${r"$('#"}list${className?lower_case}dg${r"'"}).datagrid('options').url=SKY.urlCSRF(basepath+'${urlPrefix}/${className}/get${className}ByPage');
	${r"$('#"}list${className?lower_case}dg${r"'"}).datagrid('load', {
		filter : function(){
			var ft = new HashMap();
			<#list columns as column>
			<#if column.propertyName!= "id"
				 && column.propertyName!= "updater"
				 && column.propertyName!= "updateTime"
				 && column.propertyName!= "creater"
				 && column.propertyName!= "createTime"
			>
			var ${column.propertyName} =$('#q_${column.propertyName}').textbox("getValue");
			if(${column.propertyName}){
				ft.put("${column.propertyName}@=", ${column.propertyName});
			}
			</#if>
			</#list>
			return ft.getJSON();
		}
	});
}