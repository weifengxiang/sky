function init() {
	
}
/**
 * 初始化树
 * @param paramOpts
 */
function initRoleOpeTreeHelpPage(role) {
	_ROLE=role;
	var url = basepath + 'sys/SysRole/getRoleOpeTree/'+_ROLE.code;
	url=SKY.urlCSRF(url);
	$.ajax({
		url:url,
		type: "POST",
		dataType:'json',
		success:function(data){
			var defOpts={
					data : data,
					lines:true,
					checkbox:true,
					method : 'POST',
					onClick : function(node) {
						var data=node.data;
						if(!data.url){
							loadMenuDetail(data);
						}else{
							loadOperationDetail(data);
						}
					}
				};
			$('#functiontree').tree(defOpts);
		}
	});
}
/**
 * 加载菜单明细
 */
function loadMenuDetail(data){
	$('#detail').panel('setTitle','菜单详情');
	$('#detail').empty();
	$('#detail').append($('#menuDetailTemplate').html());
	$.parser.parse('#detail');
	var url=basepath+"sys/function/getSysMenuById?id="+data.id;
	$('#detailsysmenuform').form('load',SKY.urlCSRF(url));
}
/**
 * 加载操作明细
 */
function loadOperationDetail(data){
	$('#detail').panel('setTitle','操作详情');
	$('#detail').empty();
	$('#detail').append($('#operationDetailTemplate').html());
	$.parser.parse('#detail');
	var url=basepath+"sys/function/getSysOperationById?id="+data.id;
	$('#detailsysoperationform').form('load',SKY.urlCSRF(url));
}
function saveRoleOperation(){
	var url = basepath + 'sys/SysRole/saveRoleOperation';
	url=SKY.urlCSRF(url);
	
	var nodes = $('#functiontree').tree('getChecked');	
	var operation= new Array();
	for(var i=0;i<nodes.length;i++){
		if(nodes[i].data.url){
			operation.push(nodes[i].data);
		}
	}
	var data={
			'role':JSON.stringify(_ROLE),
			'operation':JSON.stringify(operation)
	};
	$.ajax({
		url:url,
		type: "POST",
		dataType:'json',
		data:data,
		success:function(data){
			$.messager.alert("提示",data.name,"info");
			if(data.code != '0'){
				initRoleOpeTreeHelpPage(_ROLE);
			}else{
				$.messager.alert("提示",data.name,"error");
			}
		}
	});
}