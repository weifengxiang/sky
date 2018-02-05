<%@	page import="org.sky.sys.utils.EnumUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/jsp/inc/include.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<security:csrfMetaTags/>
<script type="text/javascript" src='${basepath}jsp/sys/user/sysusermanage.js'></script>
<script type="text/javascript">
var SYS_SEX = <%=EnumUtils.getEnums("SYS.SEX") %>;
var SYS_IS = <%=EnumUtils.getEnums("SYS.IS") %>;
var SYS_USER_STATUS = <%=EnumUtils.getEnums("SYS.USER_STATUS") %>;
$(function() {
	init();
});
</script> 
</head>
<body class="easyui-layout" style="width: 100%; height: 100%; padding: 0; border: 0"
	  data-options='border:false,fit:true'>
<div data-options="region:'west',split:true,title:'组织机构'" style="width:300px;padding:0px;">
	<ul class="easyui-tree" id="organtree" data-options='animate:true'></ul>
</div>
<div data-options=" region:'center',iconCls: 'icon-table'" title="用户管理">
<table id="listsysuserdg" class="easyui-datagrid" style="width: 100%; height: 100%"
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
								//beginEdit('listsysuserdg',rowIndex);
						  },
			onLoadSuccess : function () {
        		$(this).datagrid('fixRownumber');
        		$(this).datagrid('doCellTip',{'max-width':'200px','delay':500});
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
						}}">用户编号</th>
				<th data-options="field:'name',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">用户名称</th>
				<th data-options="field:'status',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}},
				formatter:function(value,row){
						  	 return SKY.formatterEnum(value,row,SYS_USER_STATUS);
						 }">账户状态</th>
				<th data-options="field:'isSys',width:100,
				editor:{
						type:'textbox',
						options:{
							required:true
						}},
				formatter:function(value,row){
						  	 return SKY.formatterEnum(value,row,SYS_IS);
						 }
				">是否系统用户</th>
				<th data-options="field:'sex',width:50,
				editor:{
						type:'textbox',
						options:{
							required:true
						}},
				formatter:function(value,row){
						  	 return SKY.formatterEnum(value,row,SYS_SEX);
						 }
				">性别</th>
				<th data-options="field:'lockTime',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">锁定时间</th>
				<th data-options="field:'expiredTime',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">逾期时间</th>
				<th data-options="field:'mobeltel',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">移动电话</th>
				<th data-options="field:'worktel',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">办公电话</th>
				<th data-options="field:'email',width:180,
				editor:{
						type:'textbox',
						options:{
							required:true
						}}">邮箱</th>
		</tr>
	</thead>
</table>
</div>
<div id="tb" style="height: auto">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-add',plain:true" onclick="addSysUser()">增加</a>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-edit',plain:true" onclick="editSysUser()">修改</a>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-20130408025545236_easyicon_net_30',plain:true" onclick="delSysUser()">删除</a>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-06',plain:true" onclick="detailSysUser()">查看明细</a>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-20130406014211289_easyicon_net_16',plain:true" onclick="roleManage()">角色管理</a>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-database_key',plain:true" onclick="dataPermitManage()">数据权限管理</a>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-key_start',plain:true" onclick="resetPwd()">密码重置</a>
</div>
</body>
</html>