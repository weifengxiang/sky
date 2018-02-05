function initNavMenu(){
	var url=basepath + "sys/getNavMenu";
	url=SKY.urlCSRF(url);
	$.ajax({
		url : url,
		type:'POST',
		async: false,
		dataType:'json',  
		success:function(data){
			$.each(data, function (i, n) {
			var iconCls=null==n.accordion.icon?'icon-module':n.accordion.icon;
			$('#navmenu').accordion('add', {
			               title: "<span style='padding:0px 0px 0px 2px'>"+n.accordion.name+"<span>",
			               iconCls: iconCls,
			               selected: false,
			               content: '<div style="padding:2px 0px 0px 15px"><ul id=\''+n.accordion.code+'\'></ul></div>'
			            });
			if(n.tree){
				$('#'+n.accordion.code).tree({
					animate:true,
					lines:true,
					data:n.tree,
					method:'POST',
					onClick: function(node){
						if(node!=null){
							if(node.defUrl!=null){
								addTab(node.text,node.defUrl,node.iconCls,true);
							}
						}
					}
				});
			}
			});
		}
	});
}
function addTab(title,url,iconCls,closable){
	if(url.indexOf('www')<0&&url.indexOf('http')<0){
		url=basepath+url;
	}
	if ($('#tabs').tabs('exists', title)){
		$('#tabs').tabs('select', title);// 选中并刷新
		var currTab = $('#tabs').tabs('getSelected');
		url = $(currTab.panel('options').content).attr('src');
		if(url != undefined && currTab.panel('options').title != '工作台') {
			$('#tabs').tabs('update',{
				tab:currTab,
				options:{
					content:createFrame(url)
				}
			});
		}
	} else {
		var content = createFrame(url);
		$('#tabs').tabs('add',{
			title:title,
			iconCls:null==iconCls?'icon-module':iconCls,
			content:content,
			closable:(undefined==closable?true:closable)
		});
	}
	addTabsEventListener();
}
function createFrame(url) {
	var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}
		
function addTabsEventListener() {
	/* 双击关闭TAB选项卡 */
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close',subtitle);
	});
	/* 为选项卡绑定右键 */
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});
		var subtitle =$(this).children(".tabs-title").text();
		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}		
//右键菜单点击事件
function addTabMenuEventListener() {
	// 刷新
	$('#mm-tabupdate').click(function(){
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		if(url != undefined && currTab.panel('options').title != '工作台') {
			$('#tabs').tabs('update',{
				tab:currTab,
				options:{
					content:createFrame(url)
				}
			});
		}
	});
	// 关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		if('工作台'==currtab_title){
			$.messager.alert('提示',"工作台不能关闭",'info');
			return;
		}
		$('#tabs').tabs('close',currtab_title);
	});
	// 全部关闭
	$('#mm-tabcloseall').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		var nextall = $('.tabs-selected').nextAll();		
		if(prevall.length==0&&nextall.length==0){
			$.messager.alert('提示',"没有可以关闭的了~",'info');
			return;
		}
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			if(t != '工作台') {
				$('#tabs').tabs('close',t);
			}
		});
	});
	// 关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		var nextall = $('.tabs-selected').nextAll();		
		if(prevall.length==1&&nextall.length==0){
			$.messager.alert('提示',"没有其他可以关闭的了~",'info');
			$('#mm').menu('hide');
			return;
		}
		if(prevall.length>0){
			prevall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				if(t != '工作台') {
					$('#tabs').tabs('close',t);
				}
			});
		}
		if(nextall.length>0) {
			nextall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				if(t != '工作台') {
					$('#tabs').tabs('close',t);
				}
			});
		}
		$('#mm').menu('hide');
		return false;
	});
	// 关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			$.messager.alert('提示',"右侧没有其他可以关闭的了~",'info');
			$('#mm').menu('hide');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			if(t != '工作台') {
				$('#tabs').tabs('close',t);
			}
		});
		$('#mm').menu('hide');
		return false;
	});
	// 关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			$.messager.alert('提示',"左侧没有其他可以关闭的了~",'info');
			$('#mm').menu('hide');
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			if(t != '工作台') {
				$('#tabs').tabs('close',t);
			}
		});
		$('#mm').menu('hide');
		return false;
	});

	// 退出
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	});
}
function Clock() {
	var date = new Date();
	this.year = date.getFullYear();
	this.month = date.getMonth() + 1;
	this.date = date.getDate();
	this.day = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")[date.getDay()];
	this.hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
	this.minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
	this.second = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

	this.toString = function() {
		return "现在是:" + this.year + "年" + this.month + "月" + this.date + "日 " + this.hour + ":" + this.minute + ":" + this.second + " " + this.day; 
	};
	
	this.toSimpleDate = function() {
		return this.year + "-" + this.month + "-" + this.date;
	};
	
	this.toDetailDate = function() {
		return this.year + "-" + this.month + "-" + this.date + " " + this.hour + ":" + this.minute + ":" + this.second;
	};
	
	this.display = function(ele) {
		var clock = new Clock();
		ele.innerHTML = clock.toString();
		window.setTimeout(function() {clock.display(ele);}, 1000);
	};
}
//修改密码页面
function openUpdatePwd(){
	$("#selfupdatePwd").window("open");
}
//保存修改密码
function saveUpdatePwd(){
	var url=basepath+'sys/SysUser/updatePwd';
	
	url=SKY.urlCSRF(url);
	var options = {    
       beforeSubmit:function(data){
    	   if($("#newpwd").textbox("getValue")!=$("#configpwd").textbox("getValue")){
    		   $.messager.alert('提示','两次输入密码不一致','info');
    		   return false;
    	   }
		   return $('#updatePwd').form('enableValidation').form('validate');
       },   
       success:function(data){
    	   if(data.code=='1'){
    		   $.messager.alert('提示','修改成功,请重新登录','info',function(){
    			  window.location.href=basepath+'logout';  
    		   });
    	   }else{
    		   $.messager.alert('提示',data.name,'info');
    	   }
    	   
       },
       data:{
    	   "data":function(){
    	   }
       },
       url:url, 
       type:'post',   
       dataType:'json',   
       timeout:-1    
	};  
	$('#updatePwd').ajaxSubmit(options);
}
function resetUpdatePwd(){
	$("#updatePwd").form('clear');
	$("#selfupdatePwd").window('close');
}
function createWorkbench(){
	var url ="sys/workbench";
	addTab('工作台',url,'icon-home',false);
}
function showTheme(){
	$('#themedlg').window('open');
}