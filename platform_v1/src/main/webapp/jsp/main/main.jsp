<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="../../jsp/inc/include.jsp"%> 
<%@page import="org.sky.sys.utils.ConfUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>
<%=ConfUtils.getValue("procuctName") %>
</title>
<security:csrfMetaTags />
<script type="text/javascript" src="${basepath}jsp/main/main.js"></script>

<script type="text/javascript">
var basepath='${basepath}';
var WEBSOCKETADDRESS='<%=ConfUtils.getValue("websocketaddress") %>';
var websocket;
$(function() {
	initNavMenu();
	initThemes();
	createWorkbench();
	addTabMenuEventListener();
	(new Clock()).display($('#clock')[0]);
});

function initThemes(){
	var themes = [
	          	{value:'default',text:'Default',group:'Base'},
	    		{value:'gray',text:'Gray',group:'Base'},
	    		{value:'metro',text:'Metro',group:'Base'},
	    		{value:'material',text:'Material',group:'Base'},
	    		{value:'bootstrap',text:'Bootstrap',group:'Base'},
	    		{value:'black',text:'Black',group:'Base'},
	    		{value:'metro-blue',text:'Metro Blue',group:'Metro'},
	    		{value:'metro-gray',text:'Metro Gray',group:'Metro'},
	    		{value:'metro-green',text:'Metro Green',group:'Metro'},
	    		{value:'metro-orange',text:'Metro Orange',group:'Metro'},
	    		{value:'metro-red',text:'Metro Red',group:'Metro'},
	    		{value:'ui-cupertino',text:'Cupertino',group:'UI'},
	    		{value:'ui-dark-hive',text:'Dark Hive',group:'UI'},
	    		{value:'ui-pepper-grinder',text:'Pepper Grinder',group:'UI'},
	    		{value:'ui-sunny',text:'Sunny',group:'UI'}
	];
	$('#cb-theme').combobox({
		groupField:'group',
		data: themes,
		editable:false,
		panelHeight:'auto',
		onChange:switchStyle,
		onLoadSuccess:function(){
			var c=$.cookie('style');
			if(c){
				$(this).combobox('setValue', c);
			}
		}
	});
}
function logout(){
	window.location.href=basepath+"logout";
}
//样式切换
function switchStyle(styleName){
	var href=basepath+"skin/plugins/themes/"+styleName+"/easyui.css";
	$('link[rel=stylesheet][title=themes]').each(function(i) 
	{
		this.href=href;
	});
	
	$("iframe").contents().find('link[rel=stylesheet][title=themes]').each(function(i) 
	{
		this.href=href;
	});
	$.cookie('style',styleName,{path: '/', expires: 10 });
}
</script>
</head>
<body class="easyui-layout">
	<div region="north" border="false" style="height:83px;padding:0px 0px 0px 0px;overflow-x:visible;overflow-y:visible;">
	<div style="height:56px;background-color: #1586E7;">
		<img alt="LOGO" src="${basepath}skin/images/logo.png" style="position: absolute;top:0px">
		<div style="text-align: right;margin:0 auto;float:right;padding-top:0px">
			<div style="overflow:hidden;">
		        <a class="easyui-menubutton" data-options="menu:'#mm3',iconCls:'icon-user'">
		        	<STRONG><font color='#FFFFFF'>用户中心</font></STRONG>
		        </a>
	    	</div>
	    	<div id="mm3" style="width:100px;">
		        <div data-options="iconCls:'icon-head_ico'" onclick="logout()">
		        	退出
		        </div>
		        <div class="menu-sep"></div>
		        <div data-options="iconCls:'icon-key'" onclick="openUpdatePwd()" >
		        	修改密码
		        </div>
		        <div class="menu-sep"></div>
		        <div data-options="iconCls:'icon-color'" onclick="showTheme()" >
		        	设置皮肤
		        </div>
    		</div>
		</div>
		</div>
	   
	    <div class="easyui-panel" style="padding:1 0 1 0;background-color: #95B8E7;width: 100%" 
	    	 data-options="border:false,bodyCls:'panel-header'">
	    	<div style="text-align:left;float:left;padding:5 0 5 0;">
		    	<span id="welmsg" style="font-weight:bold;color: blue;">
		    		&nbsp;&nbsp;欢迎使用<%=ConfUtils.getValue("procuctName") %>
		    	</span>
	    	</div>
	    </div>
	</div>
	<div region="west" border="true" split="true" title="系统导航" style="width: 210px" iconCls='icon-root'>
		<div class="easyui-accordion" id='navmenu' fit="true" border="false">
		</div>
	</div>
	<div id="mainPanle" region="center" border="true" border="false">
		 <div id="tabs" class="easyui-tabs"  fit="true" border="false" 
		 	  data-options="">
        </div>
	</div>

	<div region="south" border="false">
		<div style="float: left; ">
			<span>
				<c:out value="${LoginUserMsg}"></c:out>
			</span>
		</div>
		<div style="float: right; ">
			<span id='clock' ></span>&nbsp;
		</div>
	</div>
	<div id="mm" class="easyui-menu">
		<div data-options="iconCls:'icon-arrow_refresh'" id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div data-options="iconCls:'icon-decline'" id="mm-tabclose">关闭</div>
		<div data-options="iconCls:'icon-decline'" id="mm-tabcloseother">关闭其他</div>
		<div data-options="iconCls:'icon-decline'" id="mm-tabcloseall">关闭全部</div>
		<div data-options="iconCls:'icon-decline'" id="mm-tabcloseright">关闭右侧全部</div>
		<div data-options="iconCls:'icon-decline'" id="mm-tabcloseleft">关闭左侧全部</div>
		<div class="menu-sep"></div>
		<div data-options="iconCls:'icon-user_go'" id="mm-exit">退出</div>
	</div>
	
	<div id='selfupdatePwd' class='easyui-dialog' title='修改密码' style="width:250px;height:200px;"
		 data-options="
		 	closed:true,
		 	modal:true,
		 	buttons:[
		 		{
		 			text:'确定',
		 			iconCls:'icon-save',
		 			handler:function(){
		 				saveUpdatePwd();
		 			}
		 		},
		 		{
		 			text:'取消',
		 			iconCls:'icon-cancel',
		 			handler:function(){
		 				resetUpdatePwd();
		 			}
		 		}
		 	]
		 "
	>
		<form id='updatePwd' style="text-align: center ">
			<table style="width: 100%">
				<tr>
					<td>原密码：</td>
					<td><input class='easyui-textbox' type='password' id='oldpwd' name='oldpwd' data-options="required:true"></td>
				</tr>
				<tr>
					<td>新密码：</td>
					<td><input class='easyui-textbox' type='password' id='newpwd' name='newpwd' 
						data-options="required:true,
									  validType:['length[6,10]']"></td>
				</tr>
				<tr>
					<td>新密码：</td>
					<td><input class='easyui-textbox' type='password' id='configpwd' name='configpwd' data-options="required:true"></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="themedlg" class="easyui-dialog" title="设置皮肤" style="width:200px;height:60px;padding:0px,0px,0px,0px"
			data-options="
				top:0,
				iconCls: 'icon-color',
				closed:true,
				modal:true
			">
		<select id="cb-theme" style="width:100%;height:100%;padding:0px,0px,0px,0px"></select>		
	</div>
</body>
</html>