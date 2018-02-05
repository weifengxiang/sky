/**
 * 初始化添加字典表页面
 */
function initAddSysDictPage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
}
/**
 * 初始化编辑字典表页面
 */
function initEditSysDictPage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
	var url=basepath+"sys/SysDict/getSysDictById?id="+paramOpts.data.id;
	$('#addeditsysdictform').form('load',SKY.urlCSRF(url));
	$("#code").textbox('readonly',true);
}
/**
 * 保存添加/编辑字典表
 */
function submitAddEditSysDictForm() {
	var options = { 
	   data:{
    	   "data":function(){
    		   //return JSON.stringify();
    	   }
       },   
       beforeSubmit:function(data){
			return $('#addeditsysdictform').form('enableValidation').form('validate');
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
       url:SKY.urlCSRF(basepath+'sys/SysDict/saveAddEditSysDict'), 
       type:'post',   
       dataType:'json',   
       timeout:-1    
	};  
	$('#addeditsysdictform').ajaxSubmit(options);
	
}