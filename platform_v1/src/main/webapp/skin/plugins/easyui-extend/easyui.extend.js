/**
 * 行号宽度扩展
 */
$.extend($.fn.datagrid.methods, {
    fixRownumber : function (jq) {
        return jq.each(function () {
            var panel = $(this).datagrid("getPanel");
            //获取最后一行的number容器,并拷贝一份
            var clone = $(".datagrid-cell-rownumber", panel).last().clone();
            //由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
            clone.css({
                "position" : "absolute",
                left : -1000
            }).appendTo("body");
            var width = clone.width("auto").width();
            //默认宽度是25,所以只有大于25的时候才进行fix
            if (width > 25) {
                //多加5个像素,保持一点边距
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
                //修改了宽度之后,需要对容器进行重新计算,所以调用resize
                $(this).datagrid("resize");
                //一些清理工作
                clone.remove();
                clone = null;
            } else {
                //还原成默认状态
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
            }
        });
    },
    /**  
     * 开打提示功能  
     * @param {} jq  
     * @param {} params 提示消息框的样式  
     * @return {}  
     */  
    doCellTip: function(jq, params){  
        function showTip(data, td, e){  
            if ($(td).text() == "")   
                return;  
            var X=0;
            if(e.pageX + 20+$('#celltip').width()>=$(this).width()){
				X=e.pageX-$('#celltip').width()-20;
			}else{
				X=e.pageX+20;
			}
            data.tooltip.text($(td).text()).css({  
                top: (e.pageY + 10) + 'px',  
                left: X + 'px',
                'z-index': $.fn.window.defaults.zIndex,  
                display: 'block'  
            });  
        };  
        return jq.each(function(){  
            var grid = $(this);  
            var options = $(this).data('datagrid');  
            if (!options.tooltip) {  
                var panel = grid.datagrid('getPanel').panel('panel');  
                var defaultCls = {  
                    'border': '1px solid #333',  
                    'padding': '2px',  
                    'color': '#333',  
                    'background': '#f7f5d1',  
                    'position': 'absolute',  
                    'max-width': '200px',  
                    'border-radius' : '0px',  
                    '-moz-border-radius' : '0px',  
                    '-webkit-border-radius' : '0px',  
                    'display': 'none',
                    'word-wrap':'break-word'
                };
                var tooltip = $("<div id='celltip'></div>").appendTo('body');  
                tooltip.css($.extend({}, defaultCls, params.cls));  
                options.tooltip = tooltip;  
                panel.find('.datagrid-body').each(function(){  
                    var delegateEle = $(this).find('> div.datagrid-body-inner').length ? $(this).find('> div.datagrid-body-inner')[0] : this;  
                    $(delegateEle).undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove').delegate('td', {  
                        'mouseover': function(e){  
                            if (params.delay) {  
                                if (options.tipDelayTime)   
                                    clearTimeout(options.tipDelayTime);  
                                var that = this;  
                                options.tipDelayTime = setTimeout(function(){  
                                    showTip(options, that, e);  
                                }, params.delay);  
                            }  
                            else {  
                                showTip(options, this, e);  
                            }  
                              
                        },  
                        'mouseout': function(e){  
                            if (options.tipDelayTime)   
                                clearTimeout(options.tipDelayTime);  
                            options.tooltip.css({  
                                'display': 'none'  
                            });  
                        },  
                        'mousemove': function(e){  
                            var that = this;  
                            if (options.tipDelayTime)   
                                clearTimeout(options.tipDelayTime);  
                            //showTip(options, this, e);  
                            options.tipDelayTime = setTimeout(function(){  
                                    showTip(options, that, e);  
                                }, params.delay);  
                        }  
                    });  
                });  
                  
            }  
              
        });  
    },  
    /**  
     * 关闭消息提示功能  
     *  
     * @param {}  
     *            jq  
     * @return {}  
     */  
    cancelCellTip: function(jq){  
        return jq.each(function(){  
            var data = $(this).data('datagrid');  
            if (data.tooltip) {  
                data.tooltip.remove();  
                data.tooltip = null;  
                var panel = $(this).datagrid('getPanel').panel('panel');  
                panel.find('.datagrid-body').undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove')  
            }  
            if (data.tipDelayTime) {  
                clearTimeout(data.tipDelayTime);  
                data.tipDelayTime = null;  
            }  
        });  
    }  
});
  
$.extend($.fn.validatebox.defaults.rules, {    
    notChinese: {    
        validator: function(value){    
        	var patrn=/[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi; 
        	if(patrn.exec(value)){ 
        		return false; 
        	}else{ 
        		return true; 
        	} 
        },    
        message: '不能输入中文'   
    },
    onlyNumOrLetters:{
    	validator: function(value){    
        	var patrn=/^[0-9a-zA-Z]*$/g; 
        	if(patrn.exec(value)){ 
        		return true; 
        	}else{ 
        		return false; 
        	} 
        },    
        message: '只能输入数字或者字母'
    },
    md : {
		validator : function(value, param) {
			var d1 = $.fn.datebox.defaults.parser(param[0]);
			var d2 = $.fn.datebox.defaults.parser(value);
			return d2 <= d1;
		},
		message : '日期必须小于或等于{0}'
	},
	// 最小日期
	ld : {
		validator : function(value, param) {
			var d1 = $.fn.datebox.defaults.parser(param[0]);
			var d2 = $.fn.datebox.defaults.parser(value);
			return d2 >= d1;
		},
		message : '日期必须大于或等于{0}'
	},
	// 必须是日期格式
	mustDate : {
		validator : function(value, param) {
			var pat = "^\\d{4}-\\d{1,2}-\\d{1,2}$";
			var re = new RegExp(pat);
			if (!re.test(value)) {
				return false;
			} else {
				return true;
			}
		},
		message : '请输入符合yyyy-MM-dd格式的日期'
	},
	length : {
		validator : function(value, param) {
			var maxLength = value.length;
			var zhongValue = value.replace(/[^\u4e00-\u9fa5]*/g,'');
			if(zhongValue == null || zhongValue == "" || zhongValue == "undefined"){
				
			}else{
				maxLength += zhongValue.length;
			}
			
			if(maxLength >= param[0] && maxLength <= param[1]){
				return true;
			}else{
				return false;
			}
		},
		message : '输入内容长度必须介于{0}和{1}之间,一个汉字占两个长度'
	},
	// 最大数字
	minValue : {
		validator : function(value, param) {
			return value > param[0];
		},
		message : '请输入大于等于{0} 的整数.'
	},
	// 最小数字
	maxValue : {
		validator : function(value, param) {
			return value <= param[0];
		},
		message : '请输入小于等于 {0} 的整数.'
	},
	// 小数位数
	splitNum : {
		validator : function(value, param) {
			var values = value.split(".");
			var pat = "^[0-9]*$";
			var re = new RegExp(pat);
			for(var i=0;i<values.length;i++){
				if (!re.test(values[i])) {
					return false;
				}
			}
			if(values.length == 1){
				return true;
			}
			if (values[1].length == 0 || values[1].length > param[0]) {
				return false;
			}
			return true;
		},
		message : '请输入{0}位小数的数字.'
	},
	// 密码一致
	samePwd : {
		validator : function(value, param) {
			var value1 = $("#"+param[0]).textbox("getValue");
			if(value != value1){
				return false;
			}
			return true;
		},
		message : '两次输入的密码必须一致.'
	},
	/**
	 * 大于等于传入参数1,小于传入参数2的整数]
	 */
	integerNumMinMax : {
		validator : function(value, param) {
			var pat = "^[0-9]*$";
			var re = new RegExp(pat);
			if (!re.test(value)) {
				return false;
			}
			if (parseFloat(value) <= parseFloat(param[0])) {
				return false;
			}
			if (parseFloat(value) > parseFloat(param[1])) {
				return false;
			}
			return true;
		},
		message : '请输入大于{0}小于等于{1}的整数.'
	},
	/**
	 * 大于等于传入参数1,小于传入参数2的整数]
	 */
	integerNumCompare : {
		validator : function(value, param) {
			var pat = "^[0-9]*$";
			var re = new RegExp(pat);
			if (!re.test(value)) {
				return false;
			}
			if (parseFloat(value) < parseFloat(param[0])) {
				return false;
			}
			if (parseFloat(value) > parseFloat(param[1])) {
				return false;
			}
			return true;
		},
		message : '请输入大于等于{0}小于等于{1}的整数.'
	},
	/**
	 * 大于传入参数1的整数]
	 */
	integerNumMin : {
		validator : function(value, param) {
			var pat = "^[0-9]*$";
			var re = new RegExp(pat);
			if (!re.test(value)) {
				return false;
			}
			if (value <= param[0]) {
				return false;
			}
			return true;
		},
		message : '请输入大于{0}的整数.'
	},
	/**
	 * 小于等于传入参数2的整数]
	 */
	integerNumMax : {
		validator : function(value, param) {
			var pat = "^[0-9]*$";
			var re = new RegExp(pat);
			if (!re.test(value)) {
				return false;
			}
			if (value > param[1]) {
				return false;
			}
			return true;
		},
		message : '请输入小于等于{1}的整数.'
	},
	/**
	 * combobox、combogrid 输入值是否在下拉数据源中<br>
	 * 使用方法：validType:[isInDs[param]] param形式为[field,ds,msg]<br>
	 * field:校验字段<br>
	 * ds:字段所对应的数据源json字符串
	 */
	isInDs : {
		validator : function(value, param) {
			var field = param[0];
			var ds = param[1];
			var valid = false;
			if (!field) {
				throw "验证字段为空，请传入待验证字段！";
			}
			if (!ds) {
				throw "无效下拉列表数据源,数据源必须为array或object json字符串！";
			}

			if ($.isArray(ds)) {

			} else if ($.isPlainObject(ds)) {
				ds = ds.rows;
			} else {
				throw "无效下拉列表数据源,数据源必须为array或object json字符串！";
			}

			$.each(ds, function(i, data) {
				if ($.isPlainObject(data)) {
					if (data[field] == value) {
						valid = true;
						return false;
					}
				}
			});
			return valid;
		},
		// 验证无效时的提示信息可以在下拉控件options时设置invalidMessage属性,设置invalidMessage后会覆盖此message
		message : '无效输入。'
	},
	/**
	 * 校验身份证号码合法
	 * @param {Object} value
	 * @param {Object} param
	 * @return {TypeName} 
	 */
	checkIdcard:{
		validator : function(value, param) {
			var flag=checkIdcard(value);
			return flag==null||flag==""?true:false;
		},
		message:"您输入的身份证号非法！"
	},
	/**
	 * 校验电话号码，包括手机和座机
	 * @param {Object} value
	 * @param {Object} param
	 * @return {TypeName} 
	 */
	phonenum:{
		validator : function(value, param) {
			var flag=checkTel(value);
			return flag==null||flag==""?true:false;
		},
		message:"您输入的号码有误！！"
	},
	/**
	 * 校验日期格式为：yyyyMM
	 */
	yyyyMM:{
		validator : function(value, param) {
			var pat = "^\\d{4}\\d{1,2}$";
			var re = new RegExp(pat);
			if (!re.test(value)) {
				return false;
			} else {
				return true;
			}
		},
		message : '请输入符合yyyyMM格式的日期'
	},
	/**
	 * 校验日期格式为：yyyy-MM
	 */
	yyyy_MM:{
		validator : function(value, param) {
			var pat = "^\\d{4}-\\d{1,2}";
			var re = new RegExp(pat);
			if (!re.test(value)) {
				return false;
			} else {
				return true;
			}
		},
		message : '请输入符合yyyy-MM格式的日期'
	},
	/**
	 * 验证长度，大于等于传入参数1,小于等于传入参数2(包含汉字)
	 */
	chineseLength : {
		validator : function(value, param) {
			var valueLength=value.replace(/[^\x00-\xff]/g,"xx").length;
			if (valueLength < param[0]) {
				return false;
			}
			if (valueLength > param[1]) {
				return false;
			}
			return true;
		},
		message : '输入内容长度必须介于{0}和{1}之间,一个汉字占两个长度'
	},
	/**
	 * 根据传入的正则表达式区分，0：正则表达式，1:不符合的时候的提示信息
	 */
	checkByZZ : {
		validator : function(value, param) {
			var re = new RegExp(param[0]);
			if (!re.test(value)) {
				return false;
			} else {
				return true;
			}
		},
		message : '{1}.'
	}
}); 

/**
 * 校验身份证号码
 * @param {Object} idcard
 * @return {TypeName} 
 */
function checkIdcard(idcard) {
	if (typeof(idcard) == 'undefined' || idcard == null || idcard == "") {
        return "";
    }
    var Errors = new Array("验证通过!", "身份证号码位数不对!", "身份证号码出生日期超出范围或含有非法字符!", "身份证号码校验错误!", "身份证地区非法!");
    var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
		21:"辽宁",22:"吉林",23:"黑龙江",
		31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",
		41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",
		50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",
		61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",
		71:"台湾",
		81:"香港",82:"澳门",
		91:"国外"};
    var retflag = false;
    var Y, JYM, S, M;
    var idcard_array = new Array();
    idcard_array = idcard.split("");
    //地区检验
    if (area[parseInt(idcard.substr(0, 2))] == null) {
        return "身份证号码地区不正确！";
    }
    //身份号码位数及格式检验
    switch (idcard.length) {
    case 15:
        if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 || ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0)) {
            ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/; //测试出生日期的合法性
        } else {
            ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/; //测试出生日期的合法性
        }
        if (ereg.test(idcard)) {
        } else {
            return "身份证号码不正确！";
        }
        break;
    case 18:
        //18位身份号码检测
        //出生日期的合法性检查 
        //闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9] | 30) | 02(0[1 - 9] | [1 - 2][0 - 9]))
        //平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9] | 30) | 02(0[1 - 9] | 1[0 - 9] | 2[0 - 8]))
        if (parseInt(idcard.substr(6, 4)) % 4 == 0 || (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard.substr(6, 4)) % 4 == 0)) {
            ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/; //闰年出生日期的合法性正则表达式
        } else {
            ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/; //平年出生日期的合法性正则表达式
        }
        if (ereg.test(idcard)) { //测试出生日期的合法性
            //计算校验位
            S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 + parseInt(idcard_array[7]) * 1 + parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9]) * 3;
            Y = S % 11;
            M = "F";
            JYM = "10X98765432";
            M = JYM.substr(Y, 1); //判断校验位
            if (M == idcard_array[17]) { //检测ID的校验位
            	
            } else {
                return "身份证号码不正确！";
            }
        } else {
            return "身份证号码不正确！";
        }
        break;
    default:
    	return "身份证号码不正确!";
        break;
    }
}

/**
 * 校验普通电话、传真号码：可以“+”开头，除数字外，可含有“-”
 * @param telno 要检验的数据
 */
function checkTel(telno) {
    if (typeof(telno) == 'undefined' || telno == null || telno == "") {
        return "";
    }
    //var patrn = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
    //校验解释：国家代码（2-3位）-区号（2-3位）-电话号码（7-8位）-分机号（3-4位）
    //var patrn1 = (^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,4}))?$);验证普通电话
    //var patrn2 = /(^(?:13\d|15[0-9]|18[0-9])-?\d{5}(\d{3}|\*{3})$)/;验证手机号码
    //需要验证传真号码，则在patrn后继续添加
    var patrn = /(^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,4}))?$)|(^(?:13\d|15[0-9]|18[0-9])-?\d{5}(\d{3}|\*{3})$)/;
    if (!patrn.test(telno)) {
        return "号码格式错误！ 手机：13XXXXXXXXX; 固话：XXXX-XXXXXXX;";
    }
    return "";
}