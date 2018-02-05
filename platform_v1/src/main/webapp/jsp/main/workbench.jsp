<%@page import="org.sky.sys.utils.EnumUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../jsp/inc/include.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的工作台</title>
<script type="text/javascript"
	src="${basepath}skin/plugins/portal/jquery.portal.js"></script>
<script type="text/javascript">
//widget集合
var _panels = new Array();
var portal;
var myHeight = 200;
//portal列
var p_columns=2;
//在一起的用，分割，不在一起的用：分割
var localPosition='';
if ($.cookie("portal_panel_position")) {
	localPosition = $.cookie("portal_panel_position");
}
$(function() {
	
	_panels = converJSON2Panels(JSON.parse('${wdlist}'));
	portal = $('#portal').portal({
           border: false,
           fit: true,
           onStateChange: function () {
        	   $.cookie('portal_panel_position', getPanelsPosition(), {
                   expires: 7
               });
           }
       });
	renderPanel();
	portal.portal('resize');
	
});
function converJSON2Panels(wdlist){
	var panels = new Array();
	for ( var i = 0; i < wdlist.length; i++) {
		var opts = new Object();
		var wd = wdlist[i];
		opts.id = wd.id;
		opts.title = wd.name;
		opts.height=myHeight; 
		opts.collapsible = false;
		opts.closable = true;
		opts.content = '<iframe scrolling="auto" frameborder="0" style="width:100%;height:100%;"></iframe>';
		opts.src=getUrl(wd.src);
		//覆盖属性
		var panel = $.extend({}, opts, wd.opts);
		panels.push(panel);
	}
	return panels;
}
function getUrl(url){
	if(url.indexOf('www')<0&&url.indexOf('http')<0){
		url=basepath+url;
	}
	return url;
}
//获取当前位置状态
function getPanelsPosition() {
    var positions = [];
    for (var i = 0; i < p_columns; i++) {
        var temp = [];
        var pl = portal.portal("getPanels", i);
        for (var j = 0; j < pl.length; j++) {
            temp.push(pl[j].attr("id"));
        }
        positions.push(temp.join(","));
    }
    return positions.join(':');
}
//根据ID获取panel
function getPanelById(id) {
    for (var i = 0; i < _panels.length; i++) {
        if (_panels[i].id == id) {
            return _panels[i];
        }
    }
    return undefined;
}
//渲染panel
function renderPanel() {
	//如果本地cookie没记录或者 cookie的记录与_panels不一致则不适用cookie
	if(''==localPosition||!equalsCookieTPanel(localPosition,_panels)){
		$.cookie('portal_panel_position', null); 
		for(var j = 0; j < _panels.length; j++) {
			var op = _panels[j];
			var p = $('<div></div>').attr('id', op.id).appendTo('body');
			p.panel(op);
			portal.portal('add', {
				panel : p,
				columnIndex :j%2
			});
			$('#'+op.id+' iframe').attr('src',op.src);
		}
	}else{
	    var cols = localPosition.split(":");
	    for (var i = 0; i < cols.length; i++) {
	        var rows = cols[i].split(",");
	        for (var j = 0; j < rows.length; j++) {
	            var op = getPanelById(rows[j]);
	            if(op){
		            var p = $('<div></div>').attr('id', op.id).appendTo('body');
		            p.panel(op);
		            portal.portal('add', {
		                panel: p,
		                columnIndex: i
		            });
		            $('#'+op.id+' iframe').attr('src',op.src);
	            }
	        }
	    }
	}
}
//校验cookie与panel相等
function equalsCookieTPanel(c,p){
	//判断所有的panel是否都在cookie
	for(var i=0;i<p.length;i++){
		if(c.indexOf(p[i].id)==-1){
			return false;
		}
	}
	//判断所有的cookie是否都在panel
	var cols = c.split(":");
	for(var i=0;i<cols.length;i++){
		var col = cols[i].split(',');
		for(j=0;j<col.length;j++){
			if(!getPanelById(col[j])){
				return false;
			}
		}
	}
	return true;
}
</script>
</head>
<body class="easyui-layout">
	<div region="center" border="false">
		<div id="portal">
			<div style="width: 50%;"></div>
			<div style="width: 50%;"></div>
		</div>
	</div>
</body>
</html>