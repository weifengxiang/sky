<%@page import="org.sky.sys.utils.EnumUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/jsp/inc/include.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<security:csrfMetaTags/>
<script type="text/javascript" src='${basepath}jsp/sys/role/sysrolewidget.js'></script>
<script type="text/javascript">
var SYS_IS = <%=EnumUtils.getEnums("SYS.IS") %>;
var _sysRole;
var _callbacks = $.Callbacks();
$(function() {
});
</script> 
</head>
<body class="easyui-layout" style="width: 100%; height: 100%; padding: 0; border: 0"
	  data-options='border:false,fit:true'>
<div data-options="region:'north',split:false,collapsible:false,iconCls:'icon-detail'" class="easyui-panel" title="系统微件"
	   style="width:100%; height:50%; padding:0px;" cellpadding="0">
<table  id="listsyswidgetdg" class="easyui-datagrid" style="width: 100%; height: 100%"
	data-options="
			region:'center',
			fit:true,
			border:false,
			remoteSort : false,
			pagination:true,
			rownumbers: true,
			checkbox:true,
			singleSelect:true,
			selectOnCheck:false,
			checkOnSelect:false,
			onDblClickRow:function(rowIndex, rowData){
								//SKY_EASYUI.beginEdit('listsyswidgetdg',rowIndex);
						  },
			onLoadSuccess : function () {
        		$(this).datagrid('fixRownumber');
        		$(this).datagrid('doCellTip',{'max-width':'200px','delay':500});
    		},
    		onCheck:function(index, row){
    			addSysRoleWidget(row);
    		}
		">
	<thead>
		<tr>
			<th data-options="field: 'checked', checkbox:true"></th>
				<th data-options="field:'name',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">名称</th>
				<th data-options="field:'src',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">地址</th>
				<th data-options="field:'isSys',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}},
				formatter:function(value,row){
						  	 return SKY.formatterEnum(value,row,SYS_IS);
						 }">是否系统级</th>
				
				<th data-options="field:'seq',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">序号</th>
				<th data-options="field:'note',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">备注</th>
		</tr>
	</thead>
</table>
</div>
<div data-options="region:'center',iconCls: 'icon-table'" 
	 style="width:100%; height:50%; padding:0px;" cellpadding="0"
	 title="角色微件管理">
<table  id="listsysrolewidgetdg" class="easyui-datagrid" style="width: 100%; height: 100%"
	data-options="
			region:'center',
			fit:true,
			border:false,
			remoteSort : false,
			toolbar: '#tb',
			pagination:true,
			rownumbers: true,
			checkbox:true,
			singleSelect:true,
			selectOnCheck:false,
			checkOnSelect:false,
			onDblClickRow:function(rowIndex, rowData){
								SKY_EASYUI.beginEdit('listsysrolewidgetdg',rowIndex);
						  },
			onLoadSuccess : function () {
        		$(this).datagrid('fixRownumber');
        		$(this).datagrid('doCellTip',{'max-width':'200px','delay':500});
    		}
		">
	<thead>
		<tr>
			<th data-options="field: 'checked', checkbox:true"></th>
				<th data-options="field:'roleCode',width:180,hidden:true,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">角色编号</th>
				<th data-options="field:'widgetId',width:180,hidden:true,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">微件ID</th>
				<th data-options="field:'widgetName',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">微件名称</th>
				
				<th data-options="field:'seq',width:180,
				editor:{
						type:'textbox',
						options:{
						}}">序号</th>
				<th data-options="field:'note',width:180,
				editor:{
						type:'textbox',
						options:{
						}}">备注</th>
		</tr>
	</thead>
</table>
</div>
<div id="tb" style="height: auto">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-20130408025545236_easyicon_net_30',plain:true" onclick="delSysRoleWidget()">删除</a>
</div>
</body>
</html>