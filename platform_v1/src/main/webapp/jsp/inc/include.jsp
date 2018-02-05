<!-- 必须要在 <head> 的第一行、放在第一行 兼容低版本IE-->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" /> 
<%@ page import="org.sky.sys.utils.BspUtils"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://sky.org.cn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<c:url value="/" var="basepath"></c:url>

<script type="text/javascript" src="${basepath}skin/jquery/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="${basepath}skin/jquery/jquery-migrate-1.1.0.min.js"></script>
<script type="text/javascript" src="${basepath}skin/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${basepath}skin/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="${basepath}skin/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${basepath}skin/js/md5.js"></script>
<script type="text/javascript" src="${basepath}skin/js/map.js"></script>
<script type="text/javascript" src="${basepath}skin/js/sky.js"></script>
<script type="text/javascript" src="${basepath}skin/plugins/jquery-easyui-1.5.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${basepath}skin/plugins/jquery-easyui-1.5.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${basepath}skin/plugins/easyui-extend/easyui.defaults.js"></script>
<script type="text/javascript" src="${basepath}skin/plugins/easyui-extend/easyui.extend.js"></script>
<script type="text/javascript" src="${basepath}skin/plugins/easyui-extend/easyui.extend.window.js"></script>
<script type="text/javascript" src="${basepath}skin/plugins/easyui-extend/easyui.utils.js"></script>
<!-- easyUI相关 -->
<link rel="stylesheet" type="text/css" href="${basepath}skin/plugins/jquery-easyui-1.5.4/themes/default/easyui.css" rel="stylesheet" title='themes'>
<!-- 必须下载easyUI相关下面  -->
<link rel="stylesheet" type="text/css" href="${basepath}skin/plugins/jquery-easyui-1.5.4/themes/color.css">
<link rel="stylesheet" type="text/css" href="${basepath}skin/plugins/jquery-easyui-1.5.4/themes/icon.css"> 
<link rel="stylesheet" type="text/css" href="${basepath}skin/plugins/IconSky.css"> 
<link rel="stylesheet" type="text/css" href="${basepath}skin/plugins/IconExtension.css">
<link rel="stylesheet" type="text/css" href="${basepath}skin/css/sky.css">
<script type="text/javascript">
var basepath='${basepath}';
var isSecurityFilter='<%=BspUtils.isSecurityFilter() %>';
//事件是页面完全加载完的时候执行
window.onload=function() {
	var c = $.cookie('style');
	var styleName='default';
	if(c){
		styleName=c;
	}
	var href=basepath+"skin/plugins/themes/"+styleName+"/easyui.css";
	$('link[rel=stylesheet][title=themes]').each(function(i) 
	{
		this.href=href;
	});
};
/**禁止后退键 作用于IE, Chrome**/
document.onkeydown=check;
/**禁止后退键 作用于Firefox, Opera**/
document.onkeypress=check;
function check(e){
	var ev = e||window.event;
	var obj = ev.target||ev.srcElement;
	var t=obj.type||obj.getAttribute('type');
	if(ev.keyCode==8 && t!='password' && t!='text' && t!='textarea'){
		return false;
	}else if(ev.keyCode==8&&obj.getAttribute('readonly')){
		return false;
	}
}
$(function(){  
    // 设置jQuery Ajax全局的参数  
    $.ajaxSetup({ 
    	statusCode: {
    		401:function(){
    			$.messager.alert('错误','操作失败,未登录或登录超时','error');
    		  	},
   		  	403:function(){
   		  		$.messager.alert('错误','<font color="red">操作失败,您无权限执行此操作</font>','error');
       		  	},  
  		  	404:function(){
  		  		$.messager.alert('错误','操作失败,请求未找到','error');
      		  	}, 
    		408:function(){
    			$.messager.alert('错误','操作失败,请求超时','error');
      		  	}, 
      		500:function(){
      			$.messager.alert('错误','操作失败,服务器系统内部错误','error');
        		}
    	}
    });  
   
});
$(document).off('dblclick','td.calendar-day');
$(document).on('dblclick','td.calendar-day',function(dbe){
	var clickOk = $(dbe.target).parents('.combo-panel').find("a[datebox-button-index='1']");
	clickOk.trigger("click");
});

</script>