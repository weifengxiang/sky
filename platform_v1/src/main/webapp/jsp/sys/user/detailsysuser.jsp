<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/jsp/inc/include.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<security:csrfMetaTags/>
<script type="text/javascript" src='${basepath}jsp/sys/user/detailsysuser.js'></script>
<script type="text/javascript">
var _callbacks = $.Callbacks();
$(function() {
	
});
</script> 
</head>
<body>
<div class="easyui-panel"  style="width:100%;height:100%;text-align: center;"
	 data-options="footer:'#detailPageButtonsFt'">
		<form id="detailsysuserform" class="easyui-form" method="post" 
			data-options="novalidate:true">
			<input type='hidden' name='id' id='id'/>
			<input type='hidden' name='organCode' id='organCode'/>
			<table style="width:100%">
				  <tr>
					<th><label>用户编号:</label></th>
					<td><input class="easyui-textbox" name="code"
						data-options="required:true"></input></td>
					<th><label>用户名称:</label></th>
					<td><input class="easyui-textbox" name="name"
						data-options="required:true"></input></td>
				  </tr>
				  <tr>
				  	<th><label>性别:</label></th>
					<td>
						<input type="radio" name="sex" value='M' checked>男</input>
						<input type="radio" name="sex" value='F'>女</input>
					</td>
					<th><label>邮箱:</label></th>
					<td><input class="easyui-textbox" name="email"
						data-options=""></input></td>
				  </tr>
				  <tr>
				  	<th><label>账户状态:</label></th>
					<td>
						<input type="radio" name="status" value="O" checked>在职</input>
						<input type="radio" name="status" value="L">离职</input>
					</td>
					<th><label>是否系统用户:</label></th>
					<td><input type="checkbox" name="isSys" value='1'></input></td>
				  </tr>
				  <tr>
					<th><label>锁定时间:</label></th>
					<td><input class="easyui-datetimebox" name="lockTime"
						data-options=""></input></td>

					<th><label>逾期时间:</label></th>
					<td><input class="easyui-datetimebox" name="expiredTime"
						data-options=""></input></td>
				  </tr>
				  
				  <tr>
					<th><label>移动电话:</label></th>
					<td><input class="easyui-textbox" name="mobeltel"
						data-options=""></input></td>
				 
					<th><label>办公电话:</label></th>
					<td><input class="easyui-textbox" name="worktel"
						data-options=""></input></td>
				  </tr>
				  
				  <tr>
					<th><label>备注:</label></th>
					<td colspan='3'><input class="easyui-textbox" name="note"  style="width:400px;height: 100px"
						data-options="multiline:true"></input></td>
				  </tr>
			</table>
		</form>
</div>
<div id='detailPageButtonsFt' style="text-align:center; padding:2px; top:0px">
	<a href="javascript:void(0)" id='cloBtn' class="easyui-linkbutton" iconCls='icon-cancel'">关闭</a>
</div>
</body>
</html>