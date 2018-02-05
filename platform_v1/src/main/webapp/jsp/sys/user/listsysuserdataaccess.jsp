<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/jsp/inc/include.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<security:csrfMetaTags/>
<script type="text/javascript" src='${basepath}jsp/sys/user/listsysuserdataaccess.js'></script>
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
	<div class="easyui-layout" data-options='border:false'  style="width: 100%;height: 100%">
		<div data-options="region:'west',split:true,title:'组织机构管理'" style="width:200px;padding:0px;">
			<ul class="easyui-tree" id="organtree" data-options='animate:true'></ul>
		</div>
		<div id='detail' data-options="region:'center',title:'详细信息'">
		</div>
	</div>
</div>
<div data-options=" region:'center',iconCls: 'icon-table'" title="用户数据权限列表">
<table  id="listsysuserdataaccessdg" class="easyui-datagrid" style="width: 100%; height: 100%"
	data-options="
			region:'center',
			fit:true,
			border:false,
			remoteSort : false,
			toolbar: '#tb',
			pagination:true,
			rownumbers: true,
			checkbox:true,
			pageSize:100,
			pageList:[50,100],
			singleSelect:true,
			selectOnCheck:false,
			checkOnSelect:false,
			onDblClickRow:function(rowIndex, rowData){
							SKY_EASYUI.beginEdit('listsysuserdataaccessdg',rowIndex);
						  },
			onLoadSuccess : function () {
        		$(this).datagrid('fixRownumber');
        		$(this).datagrid('doCellTip',{'max-width':'200px','delay':500});
    		}
		">
	<thead>
		<tr>
			<th data-options="field: 'checked', checkbox:true"></th>
				<th data-options="field:'userCode',width:180,hiden:true,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">用户编号</th>
				<th data-options="field:'organCode',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">组织机构编号</th>
				<th data-options="field:'organName',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">组织机构名称</th>
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
		data-options="iconCls:'icon-20130408025545236_easyicon_net_30',plain:true" onclick="delSysUserDataAccess()">删除</a>
</div>
</body>
<script id="organDetailTemplate" type="text/template">
<!-- 组织机构模板开始 -->
<div class="easyui-panel"  style="width:100%;height:100%;text-align: center;"
	 data-options="border:false">
	<form id="detailsysorganform" class="easyui-form" method="post" 
			data-options="novalidate:true">
			<input type='hidden' name='id' id='id'/>
			<input type='hidden' name='parCode' id='parCode'/>
			<table style="width:100%">
				  <tr>
					<th><label>机构编号:</label></th>
					<td><input class="easyui-textbox" name="code"
						data-options="required:true"></input></td>

					<th><label>机构名称:</label></th>
					<td><input class="easyui-textbox" name="name"
						data-options="required:true"></input></td>
				  </tr>
				  <tr>
					<th><label>机构类型:</label></th>
					<td><input class="easyui-combobox" id='type' name="type"
						data-options="  required:true,  
										editable:false,
								        valueField: 'code',    
								        textField: 'name'   
									"></input></td>

					<th><label>序号:</label></th>
					<td><input class="easyui-numberbox" name="seq"
						data-options="required:true"></input></td>
				  </tr>
				  <tr>
					<th><label>备注:</label></th>
					<td colspan='3'><input class="easyui-textbox" name="note"  style="width:400px;height: 100px"
						data-options="multiline:true"></input></td>
				  </tr>
			</table>
	</form>
</div>
<!-- 组织机构模板结束 -->     
</script> 
</html>