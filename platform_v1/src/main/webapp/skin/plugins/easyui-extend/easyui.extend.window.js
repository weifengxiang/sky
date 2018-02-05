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
/** 
 * @requires jquery, EasyUI 1.2.6+ 
 *  
 * 此方法是对 EasyUI.window 和 EasyUI.dialog 的扩展 
 * 可以实现如下功能： 
 * 1、使用框架页面时可以控制窗口是否跨框架弹出在框架最顶层页面，还是框架内当前页面。默认框架最顶层页面 
 * 2、可以控制url加载页面方式，是使用默认方式，还是iframe加载， 默认iframe加载 
 * 3、使用iframe加载页面时，可以实现父页面向子页面传递javaScript对象 
 * 4、使用iframe加载页面时，可以订制iframe onLoad事件 
 * 5、扩展content属性，自动识别是静态文本内容，还是加载页面 
 * 6、通过赋值ID属性，控制弹出窗体唯一性 
 * 7、toolbar、buttons中定义按钮的handler属性，支持弹出窗体iframe中方法调用。 
 * 8、弹出窗体关闭方式更灵活 
 *  
 *  
 * @author zhaojh<zjh527@163.com> 
 * @date 2012.11.15 
 *  
 */
(function($) {
	$.namespace('SKY_EASYUI');

	/** 
	 * 普通窗体 
	 *  
	 * 新增属性说明如下 
	 * @param isFrame   是否开启使用iframe加载给定url页面, 此属性设置为true时则开启使用iframe加载页面。 值：true|false,  默认值true 
	 * @param self      用于框架页面，如果值为true则不跨框架，否则跨框架弹出在框架最顶层页面。 值:true|false, 默认值false 
	 * @param data      用于在使用iframe加载给定页面时，父页面给子页面传递数据。 默认值null 
	 *  
	 * 扩展属性说明如下 
	 * @param onLoad    当使用iframe加载给定url页面时，在iframe加载完成后调用。 
	 *                  默认接收一个参数对象，参数对象属性说明参见下面toolbar、buttons说明第2项。 
	 * @param content   可根据内容前缀关键字'url:',来判断是显示静态文本还是加载页面。 
	 * @param id        此属性用来标识弹出窗体的唯一性，不再用来充当panel的id属性 
	 *  
	 * 特殊属性说明如下 
	 * this.content     iframe方式加载内容页的window对象。 用于onLoad方法中的调用 
	 *  
	 *  
	 * toolbar、buttons  属性定义按钮handler属性扩展说明如下 
	 * 1、当handler 被赋值字符串时，表示调用弹出窗体iframe中已有的与字符串值同名的方法 
	 * 2、被调用方法默认接收一个参数对象，对象属性如下： 
	 *   data: 类型：Object，是对SKY_EASYUI.open方法参数data的引用 
	 *   close: 类型：Function，用来关闭弹出窗体 
	 * example：
	 *   toolbar: [{  
     *       text: '打印',  
     *       iconCls: 'icon-print',  
     *       handler: 'print' //调用弹出窗体iframe中的方法  
     *   },'-',{  
     *       text: '关闭',  
     *       iconCls: 'icon-cancel',  
     *       handler: function(dialog){  
     *           dialog.close();  
     *       }  
     *   }],
     *   buttons:[{  
     *       text: '打印',  
     *       iconCls: 'icon-print',  
     *       handler: 'print' //调用弹出窗体iframe中的方法  
     *   },{  
     *       text: '关闭',  
     *       iconCls: 'icon-cancel',  
     *       handler: function(dialog){  
     *           dialog.close();  
     *       }  
     *   }]
	 *  
	 *  
	 *  
	 * 注：其他属性请参考EasyUI API文档。 
	 *  
	 */
	SKY_EASYUI.open = function(opts) {
		var win;
		var defaults = {
			width : 500,
			height : 400,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			resizable : false,
			modal:false,
			isFrame : true, //是否使用iframe  
			self : false, //用于框架页面，如果值为true则不跨框架，否则跨框架弹出在框架最顶层页面  
			data : null, //iframe方式下用来父页面向弹出窗体中子页面传递数据  
			content : '',
			onLoad : null,
			onClose : function() {
				win.dialog('destroy');
			},
			onBeforeDestroy : function() {
				/**
				 * 在使用extjs、easyui等框架时，每次动态添加iframe，iexplore.exe进程占据的内存都会增加，不会自动释放，最终导致内存溢出。
				 * 例如在easyui中使用富文本编辑器，在IE中打开是，有时会导致页面无法获取焦点。
				 * 在使用富文本编辑器的时候发现个问题，在编辑器卸载的时候，火狐和chrome都正常，
				 * 但是在ie浏览器十有八九导致页面元素无法聚焦，文本框没法获得焦点，文字也不能选中，
				 * 起初以为是被透明的层给遮罩了，于是去看编辑器的代码，一头雾水，调试查看页面元素也无异常，
				 * 但是select下拉框和按钮还有作用，也排除了遮罩层的可能。
				 * 一、问题的原因
				 *  IE 下在移除父层的 DIV 的时候，也就是 IFrame 外层的 DIV 的时候,IE 并没有将内部的 IFrame 从 DOM 中移除,而下次触发函数创建此 IFrame 的时候,使会产生 DOM 冲突,所以解决方法就是在移除父层的 DIV 的时候,先将内部的 IFrame 也移除。
				 * 二、解决方法
				 * 使用js的方法"CollectGarbage()",在移除相应元素后执行该方法，强制回收内存
				 * 三、Easyui中释放Iframe的内存的方法
				 */
				var frame = $('iframe', this);
				if ($.browser.msie) {
					CollectGarbage();
				}
				if (frame.length > 0) {
					frame[0].contentWindow.document.write('');
					frame[0].contentWindow.close();
					frame.src = "";
					frame.remove();
				}
			}
		};

		var options = $.extend({}, defaults, opts);

		//取顶层页面  
		var _doc, _top = (function(w) {
			try {
				_doc = w['top'].document;
				_doc.getElementsByTagName;
			} catch (e) {
				_doc = w.document;
				return w;
			}

			if (options.self
					|| _doc.getElementsByTagName('frameset').length > 0) {
				_doc = w.document;
				return w;
			}

			return w['top'];
		})(window);

		//如填写ID属性，则窗体唯一  
		var winId;
		if (options.id) {
			winId = options.id;
			delete options.id;

			//检查创建窗口是否已经存在，存在则不在创建  
			if ($('#' + winId).length > 0) {
				return;
			}
		}

		//检查content内容是静态文本，还是url地址  
		var isUrl = /^url:/.test(options.content);
		if (isUrl) {
			var url = options.content.substr(4, options.content.length);
			//构建iframe加载方式  
			if (options.isFrame) {
				var iframe = $('<iframe></iframe>').attr('height', '100%')
						.attr('width', '100%').attr('marginheight', '0').attr(
								'marginwidth', '0').attr('frameborder', '0');

				setTimeout(function() {
					iframe.attr('src', url);
				}, 1);

				var _this = this;
				var frameOnLoad = function() {
					_this.content = iframe.get(0).contentWindow;
					options.onLoad && options.onLoad.call(_this, {
						data : options.data,
						close : function() {
							win.dialog('close');
						}
					});
				};

				delete options.content;

			} else {//使用默认页面加载方式  
				options.href = url;
			}
		}

		//加工toolbar和buttons中定义的handler方法，使其可以接收给定参数，用于iframe方式下的父子页面传值和窗口关闭  
		var warpHandler = function(handler) {
			var args = {
				data : options.data,
				close : function() {
					win.dialog('close');
				}
			};
			if (typeof handler == 'function') {
				return function() {
					handler(args);
				};
			}

			if (typeof handler == 'string' && options.isFrame) {
				return function() {
					iframe.get(0).contentWindow[handler](args);
				};
			}
		};

		//处理toolbar数组事件定义,选择器形式不做处理  
		if (options.toolbar && $.isArray(options.toolbar)) {
			for ( var i in options.toolbar) {
				options.toolbar[i].handler = warpHandler(options.toolbar[i].handler);
			}
		}

		//处理buttons数组事件定义,选择器形式不做处理  
		if (options.buttons && $.isArray(options.buttons)) {
			for ( var i in options.buttons) {
				options.buttons[i].handler = warpHandler(options.buttons[i].handler);
			}
		}

		if (options.isFrame && iframe) {
			iframe.bind('load', frameOnLoad);
			win = _top.$('<div>', {
				id : winId
			}).append(iframe).dialog(options);
		} else {
			win = _top.$('<div>', {
				id : winId
			}).dialog(options);
		}
	};

	/** 
	 *  
	 * 模式窗体 
	 *  
	 * 参数说明请看SKY_EASYUI.open 
	 *  
	 */
	SKY_EASYUI.showModalDialog = function(opts) {
		var defaults = $.extend({}, opts, {
			modal : true,
			minimizable : false,
			maximizable : false,
			resizable : false,
			collapsible : false
		});
		SKY_EASYUI.open(defaults);
	};
})(jQuery);