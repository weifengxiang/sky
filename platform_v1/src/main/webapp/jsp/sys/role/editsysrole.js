/**
 * 初始化添加角色表页面
 */
function initAddSysRolePage(paramOpts){
	$('#organCode').val(paramOpts.organ.code);
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
}
/**
 * 初始化编辑角色表页面
 */
function initEditSysRolePage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
	var url=basepath+"sys/SysRole/getSysRoleById?id="+paramOpts.data.id;
	$('#addeditsysroleform').form('load',SKY.urlCSRF(url));
	$("#code").textbox('readonly',true);
}
/**
 * 保存添加/编辑角色表
 */
function submitAddEditSysRoleForm() {
	var options = { 
	   data:{
    	   "data":function(){
    		   //return JSON.stringify();
    	   }
       },   
       beforeSubmit:function(data){
			return $('#addeditsysroleform').form('enableValidation').form('validate');
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
       url:SKY.urlCSRF(basepath+'sys/SysRole/saveAddEditSysRole'), 
       type:'post',   
       dataType:'json',   
       timeout:-1    
	};  
	$('#addeditsysroleform').ajaxSubmit(options);
	
}