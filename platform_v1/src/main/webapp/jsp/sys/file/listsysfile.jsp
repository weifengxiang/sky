<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/jsp/inc/include.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<security:csrfMetaTags/>
<script type="text/javascript" src='${basepath}jsp/sys/file/listsysfile.js'></script>
<script type="text/javascript">
$(function() {
	init();
});
</script> 
</head>
<body class="easyui-layout" style="width: 100%; height: 100%; padding: 0; border: 0"
	  data-options='border:false,fit:true'>
<div data-options="region:'north',split:false,collapsible:false,iconCls:'icon-search'" class="easyui-panel" title="查询条件"
	   style="width:100%; height:63px; padding:0px;" cellpadding="0">
<table class='noborder'>
	<tr  style="height: 34px">
		<th><label>附件类型:</label></th>
		<td><input  class="easyui-textbox" data-options="prompt:'输入附件类型'"name="q_type"  id="q_type" ></input></td>				
		<th><label>附件名称:</label></th>
		<td><input  class="easyui-textbox" data-options="prompt:'输入附件名称'"name="q_name"  id="q_name" ></input></td>				
		<th><label>业务编号:</label></th>
		<td><input  class="easyui-textbox" data-options="prompt:'输入业务编号'"name="q_bizCode"  id="q_bizCode" ></input></td>				
		<th><label>业务类型:</label></th>
		<td><input  class="easyui-textbox" data-options="prompt:'输入业务类型'"name="q_bizType"  id="q_bizType" ></input></td>				
		<td><a href="javascript:searchButton()"class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a></td>
	</tr>
</table>
</div>
<div data-options=" region:'center',iconCls: 'icon-table'" title="附件表管理">
<table  id="listsysfiledg" class="easyui-datagrid" style="width: 100%; height: 100%"
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
								//SKY_EASYUI.beginEdit('listsysfiledg',rowIndex);
						  },
			onLoadSuccess : function () {
        		$(this).datagrid('fixRownumber');
        		$(this).datagrid('doCellTip',{'max-width':'200px','delay':500});
    		}
		">
	<thead>
		<tr>
			<th data-options="field: 'checked', checkbox:true"></th>
				<th data-options="field:'type',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">附件类型</th>
				<th data-options="field:'name',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">附件名称</th>
				<th data-options="field:'size',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">附件大小</th>
				<th data-options="field:'path',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">附件保存路径</th>
				<th data-options="field:'suffix',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">附件后缀</th>
				<th data-options="field:'bizCode',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">业务编号</th>
				<th data-options="field:'bizType',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">业务类型</th>
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
<div id="tb" style="height: auto">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-add',plain:true" onclick="addSysFile()">增加</a>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-edit',plain:true" onclick="editSysFile()">修改</a>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-20130408025545236_easyicon_net_30',plain:true" onclick="delSysFile()">删除</a>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-06',plain:true" onclick="detailSysFile()">查看明细</a>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-folder_add',plain:true" onclick="uploadSysFile()">附件上传</a>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-folder_explore',plain:true" onclick="downloadSysFile()">附件下载</a>
</div>
</body>
</html>