<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/jsp/inc/include.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<security:csrfMetaTags/>
<script type="text/javascript" src='${basepath}jsp/sys/organ/sysorganmanage.js'></script>
<script type="text/javascript">
$(function() {
	init();
});
</script> 
</head>
<body class="easyui-layout" data-options='border:false'  style="width: 100%;height: 100%">
	<div data-options="region:'west',split:true,title:'组织机构管理'" style="width:300px;padding:0px;">
		<ul class="easyui-tree" id="organtree" data-options='animate:true'></ul>
	</div>
	<div id='detail' data-options="region:'center',title:'详细信息'">
	</div>
</body>
<div id="mmorgan" class="easyui-menu"">
	<div onclick="addSysOrgan()" data-options="iconCls:'icon-add'">添加下级组织机构</div>
	<div class="menu-sep"></div>
	<div onclick="editSysOrgan()" data-options="iconCls:'icon-edit'">修改组织机构</div>
	<div class="menu-sep"></div>
	<div onclick="delSysOrgan()" data-options="iconCls:'icon-20130408025545236_easyicon_net_30'">删除组织机构</div>
</div>
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