(function($){  
    $.extend({  
        namespace: function(ns){  
            if(typeof ns != 'string'){  
                throw new Error('namespace must be a string');  
            }  
              
            var ns_arr = ns.split('.');  
            var parent = window;  
            for(var i in ns_arr){  
                parent[ns_arr[i]] = parent[ns_arr[i]] || {};  
                parent = parent[ns_arr[i]];  
            }  
        }  
    });  
 })(jQuery);
(function($) {
	$.namespace('SKY');
	/**
	 * urlCSRF验证
	 */
	SKY.urlCSRF=function(url){
		var param =  $("meta[name='_csrf_parameter']").attr("content");
		var token = $("meta[name='_csrf']").attr("content");
		if(url.indexOf("?")>0){
			url=url+"&"+param+"="+token;
		}else{
			url=url+"?"+param+"="+token;
		}
		return url;
	};
	/**
	 * 枚举字段formatter
	 * @param value
	 * @param rowdata
	 * @param ds
	 */
	SKY.formatterEnum=function(value,rowdata,ds){
		if(!value){
			return "";
		}
		if(!ds){
			return value;
		}
		for(var i=0;i<ds.length;i++){
			if(value==ds[i].code){
				return ds[i].name;
			}
		}
	};
	/**
	 * 字典字段formatter
	 * @param value
	 * @param rowdata
	 * @param ds
	 */
	SKY.formatterDict=function(value,rowdata,ds){
		if(!value){
			return "";
		}
		if(!ds){
			return value;
		}
		for(var i=0;i<ds.length;i++){
			if(value==ds[i].code){
				return ds[i].name;
			}
		}
	};
	SKY.uuid=function(len, radix) {
	    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
	    var uuid = [], i;
	    radix = radix || chars.length;
	 
	    if (len) {
	      // Compact form
	      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
	    } else {
	      // rfc4122, version 4 form
	      var r;
	 
	      // rfc4122 requires these characters
	      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
	      uuid[14] = '4';
	 
	      // Fill in random data.  At i==19 set the high bits of clock sequence as
	      // per rfc4122, sec. 4.1.5
	      for (i = 0; i < 36; i++) {
	        if (!uuid[i]) {
	          r = 0 | Math.random()*16;
	          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
	        }
	      }
	    }
	 
	    return uuid.join('');
	};
})(jQuery);