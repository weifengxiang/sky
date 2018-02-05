/**
 * 初始化${comment}详细页面
 */
function initDetail${className}Page(paramOpts){
	_callbacks.add(paramOpts.callBack);
	$('#cloBtn').on('click',function(){
		paramOpts.dialog.close();
	});
	var url=basepath+"${urlPrefix}/${className}/get${className}ById?id="+paramOpts.data.id;
	$('#detail${className?lower_case}form').form('load',SKY.urlCSRF(url));
}
