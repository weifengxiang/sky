//初始化
function init(){
	$('#onlineuserdg').datagrid('options').url=SKY.urlCSRF(basepath+'sys/onlineuser/getOnlineUser');
	$('#onlineuserdg').datagrid('load', {
		filter : function(){
			var ft = new HashMap();
			return ft.getJSON();
		}
	});
}
//踢下线
function kickOut(){
	var checked=$('#onlineuserdg').datagrid('getChecked');
	if(null==checked){
		return;
	}else{
		var msg="确认将该用户踢下线吗?";
		$.messager.confirm("踢下线确认",msg,
		function (r){
			if(r){
				SKY_EASYUI.mask('正在进行踢下线，请稍等...');
				var url = SKY.urlCSRF(basepath+'sys/onlineuser/kickOut');
				var params = {
							"kickOutRows":JSON.stringify(checked)
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
		    				$('#onlineuserdg').datagrid('reload');
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