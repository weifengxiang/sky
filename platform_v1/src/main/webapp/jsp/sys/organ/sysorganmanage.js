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
						loadOrganDetail(data);
					}
				},
				onContextMenu : function(e, node) {
					e.preventDefault();
					$(this).tree('select', node.target);
					$('#mmorgan').menu('show', {
						left : e.pageX,
						top : e.pageY
					});
				}
			});
}
 /**
 *添加组织机构表
 **/
function addSysOrgan(){
	var selectNode = $('#organtree').tree("getData",
	  		 $('#organtree').tree("getSelected").target
	  		);
	if (null == selectNode.data) {
		$.messager.alert('提示', '请选择组织机构', 'info');
		return;
	}
	var opts={
				title:'添加组织机构表',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysOrgan/initAddSysOrganPage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initAddSysOrganPage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=selectNode.data;
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	SKY_EASYUI.refreshSelectTreeNode('organtree');
		                };
		            	this.content.initAddSysOrganPage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
 /**
 *删除组织机构表
 **/
function delSysOrgan(){
	$.messager.confirm('确认对话框', '您确定要删除该组织机构吗？', function(r) {
		if (r) {
			var selectNode = $('#organtree').tree("getData",
			  		 $('#organtree').tree("getSelected").target
			  		);
			if (null == selectNode.data) {
				$.messager.alert('提示', '请选择要删除的组织机构', 'info');
				return;
			}
			var url = SKY.urlCSRF(basepath+'sys/SysOrgan/delSysOrgan');
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
						SKY_EASYUI.refreshSelectTreeParentNode('organtree');
					}
				}
			});
		}
	});
}
/**
*修改组织机构表
**/
function editSysOrgan(){
	var selectNode = $('#organtree').tree("getData",
	  		 $('#organtree').tree("getSelected").target
	  		);
	if (null == selectNode.data) {
		$.messager.alert('提示', '请选择要修改的组织机构', 'info');
		return;
	}
	var opts={
				title:'修改组织机构',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysOrgan/initEditSysOrganPage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initEditSysOrganPage){//判断弹出窗体iframe中的driveInit方法是否存在 
		                var paramOpts=new Object();
		                paramOpts.dialog=dialog;
		                paramOpts.data=selectNode.data;
		                paramOpts.callBack=function(){
		                	dialog.close();
		                	SKY_EASYUI.refreshSelectTreeParentNode('organtree');
		                };
		            	this.content.initEditSysOrganPage(paramOpts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(opts);
}
/**
 * 加载组织机构明细
 */
function loadOrganDetail(data){
	$('#detail').empty();
	$('#detail').append($('#organDetailTemplate').html());
	$.parser.parse('#detail');
	
	$('#type').combobox(
		{url : SKY.urlCSRF(basepath+'sys/common/queryComboBoxData'),
		 queryParams: {
			"tablename" : "sys_organ_type",                                
			"fields" : "code,name"
		 }
		});
	var url=basepath+"sys/SysOrgan/getSysOrganById?id="+data.id;
	$('#detailsysorganform').form('load',SKY.urlCSRF(url));
}