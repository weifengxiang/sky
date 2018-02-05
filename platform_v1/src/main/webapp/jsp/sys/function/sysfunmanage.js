function init() {
	initFunctionTree();
}
function initFunctionTree() {
	var rootData=[
					{    
					    "id":"root",    
					    "text":"功能树[root]",    
					    "iconCls":"icon-2012080111634",
					    "state":"closed",
					    "data":{
					    	"code":"root",
					    	"name":"功能树",
					    	"isLeaf":"0",
					    	"type":'root'
					    }
					}
	              ];
	var url = basepath + 'sys/function/getSysFunctionTree';
	url=SKY.urlCSRF(url);
	$('#functiontree').tree(
			{
				data : rootData,
				lines:true,
				method : 'POST',
				onBeforeExpand : function(node, param) {
					$('#functiontree').tree('options').url = url + "&data="
							+ JSON.stringify(node.data);
				},
				onClick : function(node) {
					var data=node.data;
					if('menu'==data.type){
						loadMenuDetail(data);
					}else if('operation'==data.type){
						loadOperationDetail(data);
					}
				},
				onContextMenu : function(e, node) {
					e.preventDefault();
					$(this).tree('select', node.target);
					var type = node.data.type;
					var isLeaf = node.data.isLeaf;
					if ("root" == type) {
						$('#mmroot').menu('show', {
							left : e.pageX,
							top : e.pageY
						});
					}else if("menu" == type){
						if('0'==isLeaf){
							$('#mmmenu').menu('show', {
								left : e.pageX,
								top : e.pageY
							});
						}else if('1'==isLeaf){
							$('#mmmenu_leaf').menu('show', {
								left : e.pageX,
								top : e.pageY
							});
						}
					}else if("operation" == type){
						$('#mmoperation').menu('show', {
							left : e.pageX,
							top : e.pageY
						});
					}
				}
			});
}
/**
 * 添加菜单
 */
function addMenu() {
	if(null==$('#functiontree').tree("getSelected")){
		$.messager.alert('提示',"请先在功能树中选中一个节点",'info');
		return;
	}
	var selectNode = $('#functiontree').tree("getData",
									  		 $('#functiontree').tree("getSelected").target
									  		);
	var opts={
				title:'添加菜单',
				width:600,
				height:450,
				modal:true,
				content:'url:'+basepath+'sys/function/initAddSysMenuPage',
				onLoad: function(dialog){ 
		            if(this.content && this.content.initAddMenuPage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=selectNode.data;
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	SKY_EASYUI.refreshSelectTreeNode('functiontree');
		                };
		            	this.content.initAddMenuPage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
/**
 * 修改菜单
 */
function editMenu(){
	if(null==$('#functiontree').tree("getSelected")){
		$.messager.alert('提示',"请先在功能树中选中一个节点",'info');
		return;
	}
	var selectNode = $('#functiontree').tree("getData",
									  		 $('#functiontree').tree("getSelected").target
									  		);
	var opts={
				title:'修改菜单',
				width:600,
				height:450,
				modal:true,
				content:'url:'+basepath+'sys/function/initEditSysMenuPage',
				onLoad: function(dialog){ 
		            if(this.content && this.content.initAddMenuPage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=selectNode.data;
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	SKY_EASYUI.refreshSelectTreeParentNode('functiontree');
		                };
		            	this.content.initEditMenuPage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
/**
 * 删除菜单
 */
function delMenu(){
	$.messager.confirm('确认对话框', '您确定要删除该菜单吗？', function(r) {
		if (r) {
			var selectNode = $('#functiontree').tree("getData",
			  		 $('#functiontree').tree("getSelected").target
			  		);
			if (null == selectNode.data) {
				$.messager.alert('提示', '请选择要删除的菜单', 'info');
				return;
			}
			var url=basepath + "sys/function/delSysMenu";
			url=SKY.urlCSRF(url);
			$.ajax({
				type : "POST",
				url : url,
				data : {
					data : JSON.stringify(selectNode.data)
				},
				dataType : "json",
				success : function(data) {
					$.messager.alert('提示', data.name, 'info');
					if (data.code == '1') {
						SKY_EASYUI.refreshSelectTreeParentNode('functiontree');
					}
				}
			});
		}
	});
}
/**
 * 添加操作
 */
function addOperation(){
	if(null==$('#functiontree').tree("getSelected")){
		$.messager.alert('提示',"请先在功能树中选中一个节点",'info');
		return;
	}
	var selectNode = $('#functiontree').tree("getData",
									  		 $('#functiontree').tree("getSelected").target
									  		);
	var opts={
				title:'添加操作',
				width:600,
				height:450,
				modal:true,
				content:'url:'+basepath+'sys/function/initAddSysOperationPage',
				onLoad: function(dialog){ 
		            if(this.content && this.content.initAddSysOperationPage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=selectNode.data;
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	SKY_EASYUI.refreshSelectTreeNode('functiontree');
		                };
		            	this.content.initAddSysOperationPage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
/**
 * 修改操作
 */
function editOperation(){
	if(null==$('#functiontree').tree("getSelected")){
		$.messager.alert('提示',"请先在功能树中选中一个节点",'info');
		return;
	}
	var selectNode = $('#functiontree').tree("getData",
									  		 $('#functiontree').tree("getSelected").target
									  		);
	var opts={
				title:'修改操作',
				width:600,
				height:450,
				modal:true,
				content:'url:'+basepath+'sys/function/initEditSysOperationPage',
				onLoad: function(dialog){ 
		            if(this.content && this.content.initEditSysOperationPage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=selectNode.data;
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	SKY_EASYUI.refreshSelectTreeParentNode('functiontree');
		                };
		            	this.content.initEditSysOperationPage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
/**
 * 删除操作
 */
function delOperation(){
	$.messager.confirm('确认对话框', '您确定要删除该操作吗？', function(r) {
		if (r) {
			var selectNode = $('#functiontree').tree("getData",
			  		 $('#functiontree').tree("getSelected").target
			  		);
			if (null == selectNode.data) {
				$.messager.alert('提示', '请选择要删除的操作', 'info');
				return;
			}
			var url=basepath + "sys/function/delSysOperation";
			url=SKY.urlCSRF(url);
			$.ajax({
				type : "POST",
				url : url,
				data : {
					data : JSON.stringify(selectNode.data)
				},
				dataType : "json",
				success : function(data) {
					$.messager.alert('提示', data.name, 'info');
					if (data.code == '1') {
						SKY_EASYUI.refreshSelectTreeParentNode('functiontree');
					}
				}
			});
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