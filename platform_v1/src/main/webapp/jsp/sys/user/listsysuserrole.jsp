<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/jsp/inc/include.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<security:csrfMetaTags/>
<script type="text/javascript" src='${basepath}jsp/sys/user/listsysuserrole.js'></script>
<script type="text/javascript">
var _user;
var _callbacks = $.Callbacks();
$(function() {
});
</script> 
</head>
<body class="easyui-layout" style="width: 100%; height: 100%; padding: 0; border: 0"
	  data-options='border:false,fit:true'>
<div data-options="region:'north',split:false,collapsible:false,iconCls:'icon-search'" class="easyui-panel" title=""
	   style="width:100%; height:50%; padding:0px;" cellpadding="0">
	<div class="easyui-layout" style="width: 100%; height: 100%; padding: 0; border: 0"
		  data-options='border:false,fit:true'>
	<div data-options="region:'west',split:true,title:'组织机构'" style="width:200px;padding:0px;">
			<ul class="easyui-tree" id="organtree" data-options='animate:true'></ul>
	</div>
	<div data-options=" region:'center',iconCls: 'icon-table'" title="角色列表">
	<table  id="listsysroledg" class="easyui-datagrid" style="width: 100%; height: 100%"
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
									//beginEdit('listsysroledg',rowIndex);
							  },
				onLoadSuccess : function () {
	        		$(this).datagrid('fixRownumber');
	        		$(this).datagrid('doCellTip',{'max-width':'200px','delay':500});
	    		},
	    		onCheck:function(index, row){
	    			listsysroledgOnCheck(index, row);
	    		},
	    		onUncheck:function(index, row){
	    			
	    		}
			">
		<thead>
			<tr>
				<th data-options="field: 'checked', checkbox:true"></th>
					<th data-options="field:'code',width:180,
					editor:{
							type:'textbox',
							options:{
								required:true
							}}">角色编号</th>
					<th data-options="field:'name',width:180,
					editor:{
							type:'textbox',
							options:{
								required:true
							}}">角色名称</th>
			</tr>
		</thead>
	</table>
	</div>
	</div>
</div>
<div data-options=" region:'center',iconCls: 'icon-table'" title="用户角色列表">
<table  id="listsysuserroledg" class="easyui-datagrid" style="width: 100%; height: 100%"
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
			pageSize:100,
			pageList:[50,100],
			checkOnSelect:false,
			onDblClickRow:function(rowIndex, rowData){
								SKY_EASYUI.beginEdit('listsysuserroledg',rowIndex);
						  },
			onLoadSuccess : function () {
        		$(this).datagrid('fixRownumber');
        		$(this).datagrid('doCellTip',{'max-width':'200px','delay':500});
    		}
		">
	<thead>
		<tr>
			<th data-options="field: 'checked', checkbox:true"></th>
				<th data-options="field:'userCode',width:180,hidden:true,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">用户编号</th>
				<th data-options="field:'roleCode',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">角色编号</th>
				<th data-options="field:'roleName',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">角色名称</th>
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
		data-options="iconCls:'icon-20130408025545236_easyicon_net_30',plain:true" onclick="delSysUserRole()">删除</a>
</div>
</body>
</html>