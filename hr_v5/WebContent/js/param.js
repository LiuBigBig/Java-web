/**
 * 获得请求参数的jquery全局插件
 */

$.extend({
	getParam:function(key) {
	
		var url = location.href;//获得当前url
		
		var index = url.indexOf('?');//返回?的位置
		
		var str = url.substring(index + 1);//截取？后的参数字符串
		
		var arr = str.split('&');//根据&分解字符串
		
		//遍历数组
		for (var i = 0;i < arr.length;i ++) {
			
			//根据=分解字符串
			var param = arr[i].split('=');
			
			//判断如果传入的key和当前参数名称相同，返回参数值
			if (param[0] == key) {
				return param[1];
			}			
		}
		
	}	
});