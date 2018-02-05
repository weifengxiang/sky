/**
 * 初始化添加${comment}页面
 */
function initAdd${className}Page(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
}
/**
 * 初始化编辑${comment}页面
 */
function initEdit${className}Page(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
	var url=basepath+"${urlPrefix}/${className}/get${className}ById?id="+paramOpts.data.id;
	$('#addedit${className?lower_case}form').form('load',SKY.urlCSRF(url));
}
/**
 * 保存添加/编辑${comment}
 */
function submitAddEdit${className}Form() {
	var options = { 
	   data:{
    	   "data":function(){
    		   //return JSON.stringify();
    	   }
       },   
       beforeSubmit:function(data){
			return $('#addedit${className?lower_case}form').form('enableValidation').form('validate');
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
       url:SKY.urlCSRF(basepath+'${urlPrefix}/${className}/saveAddEdit${className}'), 
       type:'post',   
       dataType:'json',   
       timeout:-1    
	};  
	$('#addedit${className?lower_case}form').ajaxSubmit(options);
	
}