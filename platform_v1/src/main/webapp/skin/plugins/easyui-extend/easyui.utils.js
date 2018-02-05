(function($){  
    $.extend({  
        namespace: function(ns){  
            if(typeof ns != 'string'){  
                throw new Error('namespace must be a string');  
            }  
              
            var ns_arr = ns.split('.');  
            var parent = window;  
            for(var i in ns_arr){  
                parent[ns_arr[i]] = parent[ns_arr[i]] || {};  
                parent = parent[ns_arr[i]];  
            }  
        }  
    });  
 })(jQuery);
(function($) {
	$.namespace('SKY_EASYUI');
	/**
	 *树节点刷新选中节点
	 */
	SKY_EASYUI.refreshSelectTreeNode=function(id){
		var selected = $('#'+id).tree('getSelected');
		if(null==selected){
			return;
		}
		$('#'+id).tree('append',
				{parent:selected.target,
				 data:[{
						id:'',
						text:''
						}]
				}
		);
		var target = selected.target;
		$('#'+id).tree('reload',target);
	};
	/**
	 *树节点刷新选中节点父节点
	 */
	SKY_EASYUI.refreshSelectTreeParentNode=function(id){
		var selected = $('#'+id).tree('getSelected');
		if(null==selected){
			return;
		}
		var target = selected.target;
		var parentNode = $('#'+id).tree('getParent',target);
		$('#'+id).tree('reload',parentNode.target);
	};
	var maskDefault = {
		mask:null,
		maskMsg:null,
		defMsg:"正在处理请稍等..."
	};
	/**
	 * 遮罩显示
	 */
	SKY_EASYUI.mask=function(msg){
		if(!maskDefault.mask){
			maskDefault.mask = $("<div class=\"datagrid-mask skymask\"></div>").appendTo("body");
		}
		if(!maskDefault.maskMsg){
			maskDefault.maskMsg = $("<div class=\"datagrid-mask-msg skymask\">"+maskDefault.defMsg+"</div>").appendTo("body").css({'font-size':'12px'});
		}
		maskDefault.mask.css({width:"100%",height:$(document).height()});
		var scrollTop = $(document.body).scrollTop();
		maskDefault.maskMsg.css({
			left:( $(document.body).outerWidth(true) - 190 ) / 2
			,top:( ($(window).height() - 45) / 2 ) + scrollTop
		});
		maskDefault.mask.show();
		maskDefault.maskMsg.html(msg||maskDefault.defMsg).show();
	};
	/**
	 * 遮罩关闭
	 */
	SKY_EASYUI.unmask=function(){
		maskDefault.mask.hide();
		maskDefault.maskMsg.hide();
	};
	/**
	 * 表格停止编辑
	 */
	SKY_EASYUI.endEditing=function(gridId){
		var dgrows = $('#'+gridId).datagrid("getRows");
		for(var i=0;i<dgrows.length;i++){
			if ($('#'+gridId).datagrid('validateRow', i)){
				$('#'+gridId).datagrid('endEdit', i);
			}else{
				return false;
			}
		}
		return true;
	};
	/**
	 * 表格开始编辑行
	 */
	SKY_EASYUI.beginEdit=function(gridId,rowIndex){
		SKY_EASYUI.endEditing(gridId);
		$('#'+gridId).datagrid('beginEdit',rowIndex);
	};
	/**
	 * 附件上传
	 */
	SKY_EASYUI.upLoad=function(opts){
		var bizType=opts.bizType;
		var bizCode=opts.bizCode;
		//回调函数
		var callback = opts.callback;
		if(null==bizType||""==bizType||undefined==bizType){
			$.messager.alert("请传入业务类型");
			return;
		}
		if(null==bizCode||""==bizCode||undefined==bizCode){
			$.messager.alert("请传入业务编号");
			return;
		}
		var diaOpts={
				id:'uploadSysFile',
				title:'附件上传',
				width:600,
				height:450,
				modal:true,
				content:'url:'+SKY.urlCSRF(basepath+'sys/SysFile/initSysFileUploadPage'),
				onLoad: function(dialog){ 
		            if(this.content && this.content.initUploadFile){//判断弹出窗体iframe中的driveInit方法是否存在 
		                opts.dialog=dialog;
		                opts.callBack=function(){
		                	dialog.close();
		                	opts.callback();
		                };
		            	this.content.initUploadFile(opts);//调用并将参数传入，此处当然也可以传入其他内容 
		            } 
		        }
			  };
	SKY_EASYUI.open(diaOpts);
	};
	/**
	 * 附件下载
	 */
	SKY_EASYUI.downLoad=function(filename){
		var url = basepath+'sys/SysFile/downzip';
		url=SKY.urlCSRF(url);
		var form=$("<form>");//定义一个form表单
		form.attr("style","display:none");
		form.attr("target","");
		form.attr("method","post");
		form.attr("action",url);
		var input1=$("<input>");
		input1.attr("type","hidden");
		input1.attr("name","filerealpath");
		input1.attr("value",filename);
		$("body").append(form);//将表单放置在web中
		form.append(input1);
		form.submit();//表单提交 
	};
})(jQuery);