<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/jsp/inc/include.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<security:csrfMetaTags/>
<script type="text/javascript" src='${r"${basepath}"}${targetDir}list${className?lower_case}.js'></script>
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
	<tr style="height: 34px">
	<#list columns as column>
	<#if column.propertyName!= "id"
		 && column.propertyName!= "updater"
		 && column.propertyName!= "updateTime"
		 && column.propertyName!= "creater"
		 && column.propertyName!= "createTime"
	>
		<th><label>${column.columnComment}:</label></th>
		<td><input  class="easyui-textbox" data-options="prompt:'输入${column.columnComment}'" name="q_${column.propertyName}"  id="q_${column.propertyName}" ></input></td>				
	</#if>
	</#list>
		<td><a href="javascript:searchButton()"class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a></td>
	</tr>
</table>
</div>
<div data-options=" region:'center',iconCls: 'icon-table'" title="${comment}管理">
<table  id="list${className?lower_case}dg" class="easyui-datagrid" style="width: 100%; height: 100%"
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
								//SKY_EASYUI.beginEdit('list${className?lower_case}dg',rowIndex);
						  },
			onLoadSuccess : function () {
        		$(this).datagrid('fixRownumber');
        		$(this).datagrid('doCellTip',{'max-width':'200px','delay':500});
    		}
		">
	<thead>
		<tr>
			<th data-options="field: 'checked', checkbox:true"></th>
			<#list columns as column>
			<#if column.propertyName!= "id"
				 && column.propertyName!= "updater"
				 && column.propertyName!= "updateTime"
				 && column.propertyName!= "creater"
				 && column.propertyName!= "createTime"
			>
				<th data-options="field:'${column.propertyName}',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">${column.columnComment}</th>
			</#if>
			</#list>
		</tr>
	</thead>
</table>
</div>
<div id="tb" style="height: auto">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-add',plain:true" onclick="add${className}()">增加</a>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-edit',plain:true" onclick="edit${className}()">修改</a>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-20130408025545236_easyicon_net_30',plain:true" onclick="del${className}()">删除</a>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-06',plain:true" onclick="detail${className}()">查看明细</a>
</div>
</body>
</html>