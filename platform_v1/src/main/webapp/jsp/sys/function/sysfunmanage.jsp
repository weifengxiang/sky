<%@page import="org.sky.sys.utils.EnumUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/jsp/inc/include.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<security:csrfMetaTags />
<script type="text/javascript" src='${basepath}jsp/sys/function/sysfunmanage.js'></script>
<script type="text/javascript">
var SYS_IS=<%=EnumUtils.getEnums("SYS.IS") %>;
var SYS_OPTYPE=<%=EnumUtils.getEnums("SYS.OPTYPE") %>;
$(function() {
	init();
});
</script>
</head>
<body class="easyui-layout" data-options='border:false'  style="width: 100%;height: 100%">
	<div data-options="region:'west',split:true,title:'功能管理'" style="width:300px;padding:0px;">
		<ul class="easyui-tree" id="functiontree" data-options='animate:true'></ul>
	</div>
	<div id='detail' data-options="region:'center',title:'详细信息'">
	</div>
</body>
<div id="mmroot" class="easyui-menu" style="width:120px;">
	<div onclick="addMenu()" data-options="iconCls:'icon-add'">添加菜单</div>
</div>
<div id="mmmenu" class="easyui-menu" style="width:120px;">
	<div onclick="addMenu()" data-options="iconCls:'icon-add'">添加下级菜单</div>
	<div class="menu-sep"></div>
	<div onclick="editMenu()" data-options="iconCls:'icon-edit'">修改菜单</div>
	<div class="menu-sep"></div>
	<div onclick="delMenu()" data-options="iconCls:'icon-20130408025545236_easyicon_net_30'">删除菜单</div>
</div>
<div id="mmmenu_leaf" class="easyui-menu" style="width:120px;">	
	<div onclick="editMenu()" data-options="iconCls:'icon-edit'">修改菜单</div>
	<div class="menu-sep"></div>
	<div onclick="delMenu()" data-options="iconCls:'icon-20130408025545236_easyicon_net_30'">删除菜单</div>
	<div class="menu-sep"></div>
	<div onclick="addOperation()" data-options="iconCls:'icon-add'">添加下级操作</div>
</div>
<div id="mmoperation" class="easyui-menu" style="width:120px;">
	<div onclick="editOperation()" data-options="iconCls:'icon-edit'">修改操作</div>
	<div class="menu-sep"></div>
	<div onclick="delOperation()" data-options="iconCls:'icon-20130408025545236_easyicon_net_30'">删除操作</div>
</div>
<script id="menuDetailTemplate" type="text/template">
<!-- 菜单模板开始 -->
<div class="easyui-panel"  style="width:100%;height:100%;text-align: center;"
	 data-options="border:false">
		<form id="detailsysmenuform" class="easyui-form" method="post" 
			data-options="novalidate:true">
			<input type='hidden' name='id' id='id'/>
			<input type='hidden' name='parentCode' id='parentCode'/>
			<table style="width:100%">
				<tr>
					<th><label>菜单编号:</label></th>
					<td><input class="easyui-textbox" name="code"
						data-options="required:true"></input></td>
				
					<th><label>菜单名称:</label></th>
					<td><input class="easyui-textbox" name="name"
						data-options="required:true"></input></td>
				</tr>
				<tr>
					<th><label>序号:</label></th>
					<td><input class="easyui-numberbox" name="seq"
						data-options="required:true"></input></td>

					<th><label>是否末级:</label></th>
					<td><input type='checkbox' name="isLeaf" value='1'></input>
					</td>
				</tr>
				<tr>
					<th><label>图标:</label></th>
					<td colspan='3'><input class="easyui-textbox" name="icon" style="width:400px"
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
<!-- 菜单模板结束 -->     
</script>
<script id="operationDetailTemplate" type="text/template">
<!-- 操作模板开始 -->
<div class="easyui-panel"  style="width:100%;height:100%;text-align: center;"
	 data-options="border:false">
		<form id="detailsysoperationform" class="easyui-form" method="post" 
			data-options="novalidate:true">
			<input type='hidden' name='id' id='id'>
			<input type='hidden' name='menuCode' id='menuCode'>
			<table style="width:100%">
				<tr>
					<th><label>操作编号:</label></th>
					<td><input class="easyui-textbox" name="code"
						data-options="required:true"></input></td>
			
					<th><label>操作名称:</label></th>
					<td><input class="easyui-textbox" name="name"
						data-options="required:true"></input></td>
				</tr>

				<tr>
					<th><label>序号:</label></th>
					<td><input class="easyui-numberbox" name="seq"
						data-options="required:true"></input></td>
					
					<th><label>是否默认:</label></th>
					<td>
						<input type='checkbox' name="isDefault" value='1'></input>
					</td>
				</tr>
		
				<tr>
					<th><label>操作地址:</label></th>
					<td colspan='3'>
						<input class="easyui-textbox" name="url"  style="width:400px"
						data-options="required:true"></input></td>
				
				</tr>
				<tr>
					<th><label>备注:</label></th>
					<td colspan='3'><input class="easyui-textbox" name="note" style="width:400px;height: 100px"
						data-options="multiline:true"></input></td>
				</tr>
			</table>
		</form>
</div>
<!-- 操作模板结束 -->     
</script>
</html>