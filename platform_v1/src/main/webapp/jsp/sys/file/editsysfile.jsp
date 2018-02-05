<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/jsp/inc/include.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<security:csrfMetaTags/>
<script type="text/javascript" src='${basepath}jsp/sys/file/editsysfile.js'></script>
<script type="text/javascript">
var _callbacks = $.Callbacks();
$(function() {
	
});
</script> 
</head>
<body>
<div class="easyui-panel"  style="width:100%;height:100%;text-align: center;"
	 data-options="footer:'#editPageButtonsFt'">
		<form id="addeditsysfileform" class="easyui-form" method="post" 
			data-options="novalidate:true">
			<input type='hidden' name='id' id='id'/>
			<table style="width:100%">
				  <tr>
					<th><label>附件类型:</label></th>
					<td><input class="easyui-textbox" name="type"
						data-options="required:true"></input></td>
				  </tr>
				  <tr>
					<th><label>附件名称:</label></th>
					<td><input class="easyui-textbox" name="name"
						data-options="required:true"></input></td>
				  </tr>
				  <tr>
					<th><label>附件大小:</label></th>
					<td><input class="easyui-textbox" name="size"
						data-options="required:true"></input></td>
				  </tr>
				  <tr>
					<th><label>附件保存路径:</label></th>
					<td><input class="easyui-textbox" name="path"
						data-options="required:true"></input></td>
				  </tr>
				  <tr>
					<th><label>附件后缀:</label></th>
					<td><input class="easyui-textbox" name="suffix"
						data-options="required:true"></input></td>
				  </tr>
				  <tr>
					<th><label>业务编号:</label></th>
					<td><input class="easyui-textbox" name="bizCode"
						data-options="required:true"></input></td>
				  </tr>
				  <tr>
					<th><label>业务类型:</label></th>
					<td><input class="easyui-textbox" name="bizType"
						data-options="required:true"></input></td>
				  </tr>
				  <tr>
					<th><label>备注:</label></th>
					<td><input class="easyui-textbox" name="note"
						data-options="required:true"></input></td>
				  </tr>
			</table>
		</form>
</div>
<div id='editPageButtonsFt' style="text-align:center; padding:0px; top:0px">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls='icon-save'
		onclick="submitAddEditSysFileForm()">保存</a> 
	<a href="javascript:void(0)" id='cloBtn' class="easyui-linkbutton" iconCls='icon-cancel'>关闭</a>
</div>
</body>
</html>