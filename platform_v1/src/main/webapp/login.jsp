<!-- 放在第一行 兼容低版本IE-->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" /> 
<%@page import="org.sky.sys.utils.ConfUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="org.sky.sys.utils.BspUtils"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<c:url value="/" var="basepath"></c:url>
<script type="text/javascript">
var basepath='${basepath}';
</script>

<script type="text/javascript" src="${basepath}skin/jquery/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="${basepath}skin/jquery/jquery-migrate-1.1.0.min.js"></script>
<script type="text/javascript" src="${basepath}skin/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${basepath}skin/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="${basepath}skin/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${basepath}skin/js/md5.js"></script>
<script type="text/javascript" src="${basepath}skin/js/map.js"></script>
<script type="text/javascript" src="${basepath}skin/plugins/jquery-easyui-1.5.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${basepath}skin/plugins/jquery-easyui-1.5.4/locale/easyui-lang-zh_CN.js"></script>
<!-- easyUI相关 -->
<link rel="stylesheet" type="text/css" href="${basepath}skin/plugins/jquery-easyui-1.5.4/themes/default/easyui.css" rel="stylesheet" title='themes'>
<!-- 必须下载easyUI相关下面  -->
<link rel="stylesheet" type="text/css" href="${basepath}skin/plugins/jquery-easyui-1.5.4/themes/color.css">
<link rel="stylesheet" type="text/css" href="${basepath}skin/plugins/jquery-easyui-1.5.4/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${basepath}skin/css/sky.css">

<style type="text/css">
body {
	background-size: cover;
	overflow-x: hidden;
	overflow-y: hidden;
	background-color: gray
}

.login_button {
	width: 93px;
	height: 36px;
	border: 0px;
}

.login_form {
	width: 300px;
	height: 200px;
	position: absolute;
	text-align: center;
	left: 0;
	right: 0;
	top: 0;
	bottom: 0;
	margin: auto;
	
}
/*table单元格默认值*/
table tr th{   
	border:0px solid #82B3E1; 
} 
table tr td{   
	border:0px solid #82B3E1;
}
</style>
<security:csrfMetaTags />
</head>
<title>
<%=ConfUtils.getValue("procuctName") %>
</title>

<body>
	<form id="loginform" action="${basepath}j_spring_security_check" method="post" class='login_form'>
		<security:csrfInput/>
		<table>
		<tr>
				<td style="text-align: right">
				<label for="name"><strong><font color='#FFFFFF'>用户名:</font></strong></label> 
				</td>
				<td> 
				<input class="easyui-textbox"  name="username" id="j_username" value=''
					  data-options="prompt:'请输入登录用户名',
					  				required:true,
					  				novalidate:true,
					  				iconCls:'icon-man',    
					  				iconAlign:'right'" 
					  style="width:200px;height:25px;"
					/>
				</td>
		</tr>
		
		<tr>	
				<td style="text-align: right;">
				<label for="password"><strong><font color='#FFFFFF'>密码:</font></strong></label>  
				</td>
				<td>
				<input class="easyui-textbox" type="password"  name="password" id="j_password" value=''
					   data-options="prompt:'请输入登录密码',
					   				 required:true,
					   				 novalidate:true,
					   				 iconCls:'icon-lock',    
					  				 iconAlign:'right'"
					   style="width:200px;height:25px;"
					/>
				</td>
		</tr>
		<tr>
			<td style="text-align: center;" colspan='2'>
				<input id="rememberme" type="checkbox"/>记住我
			</td>
		</tr>
		<tr>
			<td style="text-align: center;" colspan='2'>
				<input type='button' id="login_submit" value='登录' class="login_button"  onclick='login()'></input>
			</td>
		</tr>
		
		<tr>
		<td colspan='2' style="text-align: center;">
			<font color=red>${SPRING_SECURITY_LAST_EXCEPTION.message}</font>
		</td>
		</tr>
		</table>
	</form>
</body>
<script type="text/javascript">
function login() {
	var validate = $("#j_username").textbox('enableValidation').textbox('isValid');
	if(!validate)return ; 
	var validate = $("#j_password").textbox('enableValidation').textbox('isValid');
	if(!validate)return ;
	$("#j_password").textbox('setValue',toMD5Str($("#j_password").textbox('getValue')));
	$("#loginform").submit();
}
function getLeft(){
	var width=screen.width;
	return (width-400)+'px';
}
$(function(){
	initRememberMe();
	$(document).keypress(function(e) {  
		// 回车键事件  
	    if(e.which == 13){
	    	$('#login_submit').click(); 
	    }  
	 }); 
	//用户名录入框获取光标
	$('#j_username').textbox().next('span').find('input').focus();
	
});
//记住密码操作
function initRememberMe(){
	if($.cookie('absms_crm2_username')!=undefined){  
        $("#rememberme").attr("checked", true);  
    }else{  
        $("#rememberme").attr("checked", false);  
    }  
      
    //读取cookie  
    if($('#rememberme:checked').length>0){  
        $('#j_username').textbox('setValue',$.cookie('absms_crm2_username'));  
        $('#j_password').textbox('setValue',$.cookie('absms_crm2_password'));  
    }  
      
    //监听【记住我】事件  
    $("#rememberme").click(function(){  
        if($('#rememberme:checked').length>0){//设置cookie  
            $.cookie('absms_crm2_username', $('#j_username').textbox('getValue'),{ path: '/', expires: 10 });  
            $.cookie('absms_crm2_password', $('#j_password').textbox('getValue'),{ path: '/', expires: 10 });  
        }else{//清除cookie  
        	$.removeCookie('absms_crm2_username',{ path: '/', expires: 10 });  
            $.removeCookie('absms_crm2_password',{ path: '/', expires: 10 });   
        }  
    });  
}
</script>
</HTML>
