function init(){
	var url=SKY.urlCSRF(basepath+'sys/common/queryComboBoxData');
	$('#type').combobox(
		{url : url,
		 queryParams: {
			"tablename" : "sys_organ_type",                                
			"fields" : "code,name"
		 }
		});

}
/**
 * 初始化添加组织机构表页面
 */
function initAddSysOrganPage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
	$('#parCode').val(paramOpts.data.code);
}
/**
 * 初始化编辑组织机构表页面
 */
function initEditSysOrganPage(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
	var url=basepath+"sys/SysOrgan/getSysOrganById?id="+paramOpts.data.id;
	$('#addeditsysorganform').form('load',SKY.urlCSRF(url));
	$("#code").textbox('readonly',true);
}
/**
 * 保存添加/编辑组织机构表
 */
function submitAddEditSysOrganForm() {
	var options = { 
	   data:{
    	   "data":function(){
    		   //return JSON.stringify();
    	   }
       },   
       beforeSubmit:function(data){
			return $('#addeditsysorganform').form('enableValidation').form('validate');
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
       url:SKY.urlCSRF(basepath+'sys/SysOrgan/saveAddEditSysOrgan'), 
       type:'post',   
       dataType:'json',   
       timeout:-1    
	};  
	$('#addeditsysorganform').ajaxSubmit(options);
	
}