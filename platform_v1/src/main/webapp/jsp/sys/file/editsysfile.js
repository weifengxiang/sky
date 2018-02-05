/**
 * 初始化添加附件表页面
 */
function initAddSysFilePage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
}
/**
 * 初始化编辑附件表页面
 */
function initEditSysFilePage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
	var url=basepath+"sys/SysFile/getSysFileById?id="+paramOpts.data.id;
	$('#addeditsysfileform').form('load',SKY.urlCSRF(url));
}
/**
 * 保存添加/编辑附件表
 */
function submitAddEditSysFileForm() {
	var options = { 
	   data:{
    	   "data":function(){
    		   //return JSON.stringify();
    	   }
       },   
       beforeSubmit:function(data){
			return $('#addeditsysfileform').form('enableValidation').form('validate');
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
       url:SKY.urlCSRF(basepath+'sys/SysFile/saveAddEditSysFile'), 
       type:'post',   
       dataType:'json',   
       timeout:-1    
	};  
	$('#addeditsysfileform').ajaxSubmit(options);
	
}