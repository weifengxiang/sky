/**
 * 下拉框清空
 */
$.fn.combobox.defaults=$.extend({}, $.fn.combobox.defaults, {
	icons:[{
		iconCls:'icon-cut',
		handler: function(e){
			$(e.data.target).combobox('setValue','');
		}
	}],
});