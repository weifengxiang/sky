/**
 * 初始化添加微件页面
 */
function initAddSysWidgetPage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
}
/**
 * 初始化编辑微件页面
 */
function initEditSysWidgetPage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
	var url=basepath+"sys/SysWidget/getSysWidgetById?id="+paramOpts.data.id;
	$('#addeditsyswidgetform').form('load',SKY.urlCSRF(url));
}
/**
 * 保存添加/编辑微件
 */
function submitAddEditSysWidgetForm() {
	var options = { 
	   data:{
    	   "data":function(){
    		   //return JSON.stringify();
    	   }
       },   
       beforeSubmit:function(data){
			return $('#addeditsyswidgetform').form('enableValidation').form('validate');
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
       url:SKY.urlCSRF(basepath+'sys/SysWidget/saveAddEditSysWidget'), 
       type:'post',   
       dataType:'json',   
       timeout:-1    
	};  
	$('#addeditsyswidgetform').ajaxSubmit(options);
	
}