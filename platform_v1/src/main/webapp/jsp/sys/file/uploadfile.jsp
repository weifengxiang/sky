<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/jsp/inc/include.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<security:csrfMetaTags/>
<script type="text/javascript">
$(function() {
	
});
function initUploadFile(opts){
	var bizType=opts.bizType;
	var bizCode=opts.bizCode;
	//回调函数
	var callBack = opts.callBack;
	if(null==bizType||""==bizType||undefined==bizType){
		$.messager.alert("请传入业务类型");
		return;
	}
	if(null==bizCode||""==bizCode||undefined==bizCode){
		$.messager.alert("请传入业务编号");
		return;
	}
	var parentdiv = $(
			'<div id="attachmentupload" style="width:400px;height:290px;padding:10px 20px">'+
			'<form id="attachform" method="post" enctype="multipart/form-data" novalidate>'+
            '<table id="attachtable"></table>'+
			'</form>'+
			'</div>'); //创建一个父div
	parentdiv.appendTo('body'); //将父div添加到body中
	$("#attachmentupload").dialog({
		title: '',
        modal: true,
        closable:false,
        fit:true,
        border:false,
        collapsible: false,             //设置可折叠
        toolbar: [{                          //设置工具栏数组
            text: '添加附件',
            iconCls: 'icon-add',
            handler: function () {
            	var id=SKY.uuid(32);
                $('#attachtable').append('<tr><td><input name="attachcount" type="checkbox" value="'+id+'"></td><td><input id="'+id+'" name="attachfiles'+id+'" type="text" style="width:300px"></td></tr>');
                $('#'+id).filebox({    
                    buttonText: '选择文件', 
                    buttonAlign: 'right' ,
                    required:true
                });
            }
        }, {
            text: '删除',
            iconCls: 'icon-cut',
            handler: function () {
            	var checked = $("input[type='checkbox'][name='attachcount']:checked"); 
            	//获取选中的复选框，然后循环遍历删除
                if(checked.size()==0){
                   $.messager.alert("提示","要删除指定行，需选中要删除的行！","error");
                   return;
                }
                checked.each(function(){
                   $(this).parent().parent().remove();
                });
            }
        }],
        buttons: [{                    //设置下方按钮数组
            text: '提交',
            iconCls: 'icon-ok',
            handler: function () {
            	var checked = $("input[type='file']"); 
            	//获取选中的复选框，然后循环遍历删除
                if(checked.size()==0||
                		!$('#attachform').form('enableValidation').form('validate')){
                   $.messager.alert("提示","未添加附件，不能保存！","error");
                   return;
                }
            	SKY_EASYUI.mask('正在上传，请稍等...');
            	var options = { 
				    data:{
			    	   "data":function(){
			    		   return JSON.stringify({"bizCode":bizCode,"bizType":bizType});
			    	   }
			        },   
	                beforeSubmit:function(data){
						return $('#attachform').form('enableValidation').form('validate');
			        },   
			        success:function(data){
			           SKY_EASYUI.unmask();
			           $.messager.alert('提示',data.name,'info',function(){		
			        	   if(typeof callBack == 'function'){
			        		   callBack();
			        	   }
			    	   });
			        },
			        url:SKY.urlCSRF(basepath+'sys/SysFile/upLoadFiles'), 
			        type:'post',   
			        dataType:'json',   
			        timeout:-1    
            	};  
            	$('#attachform').ajaxSubmit(options); 
            }
        }, {
            text: '取消',
            iconCls: 'icon-cancel',
            handler: function () {
            	callBack();
            }
        }]
    });
}
</script> 
</head>
<body>
</body>
</html>