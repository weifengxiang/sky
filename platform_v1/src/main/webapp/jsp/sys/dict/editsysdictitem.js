/**
 * 初始化添加字典项表页面
 */
function initAddSysDictItemPage(paramOpts){
	$('#dictCode').val(paramOpts.data.code);
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
}
/**
 * 初始化编辑字典项表页面
 */
function initEditSysDictItemPage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
	var url=basepath+"sys/SysDict/getSysDictItemById?id="+paramOpts.data.id;
	$('#addeditsysdictitemform').form('load',SKY.urlCSRF(url));
	$("#code").textbox('readonly',true);
}
/**
 * 保存添加/编辑字典项表
 */
function submitAddEditSysDictItemForm() {
	var options = { 
	   data:{
    	   "data":function(){
    		   //return JSON.stringify();
    	   }
       },   
       beforeSubmit:function(data){
			return $('#addeditsysdictitemform').form('enableValidation').form('validate');
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
       url:SKY.urlCSRF(basepath+'sys/SysDict/saveAddEditSysDictItem'), 
       type:'post',   
       dataType:'json',   
       timeout:-1    
	};  
	$('#addeditsysdictitemform').ajaxSubmit(options);
	
}