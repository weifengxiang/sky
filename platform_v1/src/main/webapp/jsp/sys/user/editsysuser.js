/**
 * 初始化添加用户表页面
 */
function initAddSysUserPage(paramOpts){
	$('#organCode').val(paramOpts.organ.code);
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
}
/**
 * 初始化编辑用户表页面
 */
function initEditSysUserPage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
	var url=basepath+"sys/SysUser/getSysUserById?id="+paramOpts.data.id;
	$('#addeditsysuserform').form('load',SKY.urlCSRF(url));
	$("#code").textbox('readonly',true);
}
/**
 * 保存添加/编辑用户表
 */
function submitAddEditSysUserForm() {
	var options = { 
	   data:{
    	   "data":function(){
    		   //return JSON.stringify();
    	   }
       },   
       beforeSubmit:function(data){
			return $('#addeditsysuserform').form('enableValidation').form('validate');
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
       url:SKY.urlCSRF(basepath+'sys/SysUser/saveAddEditSysUser'), 
       type:'post',   
       dataType:'json',   
       timeout:-1    
	};  
	$('#addeditsysuserform').ajaxSubmit(options);
	
}