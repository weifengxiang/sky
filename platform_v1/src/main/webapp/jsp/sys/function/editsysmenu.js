function init(){
	$("input[name='isLeaf']").on("click",function(){
		if($(this).is(":checked")&&
				'root'==$('#parentCode').val()){
			$(this).removeAttr("checked");
			 $.messager.alert('错误','一级菜单不能是末级菜单','error');
		}
	});
}
/**
 * 初始化添加菜单页面
 */
function initAddMenuPage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
	$('#parentCode').val(paramOpts.data.code);
}
/**
 * 初始化编辑菜单页面
 */
function initEditMenuPage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
	var url=basepath+"sys/function/getSysMenuById?id="+paramOpts.data.id;
	$('#editsysmenuform').form('load',SKY.urlCSRF(url));
	$("#code").textbox('readonly',true);
}
/**
 * 表单提交
 */
function submitAddEditSysMenuForm() {
	var options = { 
	   data:{
    	   "data":function(){
    		   //return JSON.stringify();
    	   }
       },   
       beforeSubmit:function(data){
			return $('#editsysmenuform').form('enableValidation').form('validate');
       },   
       success:function(data){
    	   $.messager.alert('提示',data.name,'info',function(){
    		   if(data.code=='1'){
    			   _callbacks.fire();  
    		   }  	
    	   });   
       },
       url:SKY.urlCSRF(basepath+'sys/function/saveAddEditSysMenu'), 
       type:'post',   
       dataType:'json',   
       timeout:-1    
	};  
	$('#editsysmenuform').ajaxSubmit(options);
	
}