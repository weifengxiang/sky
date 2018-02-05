/**
 * 初始化添加菜单页面
 */
function initAddSysOperationPage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
	$('#menuCode').val(paramOpts.data.code);
}
/**
 * 初始化编辑操作页面
 */
function initEditSysOperationPage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
	var url=basepath+"sys/function/getSysOperationById?id="+paramOpts.data.id;
	$('#editsysoperationform').form('load',SKY.urlCSRF(url));
	$("#code").textbox('readonly',true);
}
function submitAddEditSysOperationForm() {
	var options = { 
	   data:{
    	   "data":function(){
    		   //return JSON.stringify();
    	   }
       },   
       beforeSubmit:function(data){
			return $('#editsysoperationform').form('enableValidation').form('validate');
       },   
       success:function(data){
    	   $.messager.alert('提示',data.name,'info',function(){
    		   if(data.code=='1'){
    			   _callbacks.fire();  
    		   }  	
    	   });	   
       },
       error:function(e){
    	   $.messager.alert('提示',JSON.stringify(e),'info');
       },
       url:SKY.urlCSRF(basepath+'sys/function/saveAddEditSysOperation'), 
       type:'post',   
       dataType:'json',   
       timeout:-1    
	};  
	$('#editsysoperationform').ajaxSubmit(options);
}