<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/jsp/inc/include.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<security:csrfMetaTags />
<script type="text/javascript" src='${basepath}jsp/sys/onlineuser/onlineuser.js'></script>
<script type="text/javascript">
$(function() {
	init();
});
</script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%; padding: 0; border: 0">
<div data-options=" region:'center'," title="在线用户管理">
<table  id="onlineuserdg" class="easyui-datagrid" style="width: 100%; height: 100%"
	data-options="
			region:'center',
			iconCls: 'icon-edit',
			remoteSort : false,
			toolbar: '#tb',
			pagination:false,
			rownumbers: true,
			checkbox:true,
			singleSelect:true,
			selectOnCheck:false,
			checkOnSelect:false,
			onDblClickRow:function(rowIndex, rowData){
								//beginEdit('publogdg',rowIndex);
						  },
			onLoadSuccess : function () {
        		$(this).datagrid('fixRownumber');
        		$(this).datagrid('doCellTip',{'max-width':'200px','delay':500});
    		}
		">
	<thead>
		<tr>
			<th data-options="field: 'checked', checkbox:true"></th>
				<th data-options="field:'loginid',width:120">登录账号</th>
				<th data-options="field:'username',width:150">用户名称</th>
				<th data-options="field:'loginip',width:120">登录IP</th>
				<th data-options="field:'logindate',width:150">登录时间</th>
				<th data-options="field:'organname',width:180">所属单位</th>
				<th data-options="field:'depname',width:180">所属部门</th>
				<th data-options="field:'lastRequest',width:150">最后操作时间</th>
				<th data-options="field:'sessionId',width:180">回话ID</th>
		</tr>
	</thead>
</table>
<div id="tb" style="height: auto">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-status_offline',plain:true" onclick="kickOut()">踢下线</a>
</div>
</body>
</html>