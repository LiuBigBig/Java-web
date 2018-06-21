//后端服务器地址
var baseUrl = "http://127.0.0.1:7777/hr_v5";

//全局ajax事件回调，处理ajax请求session超时
$.ajaxSetup({
	complete:function(xhr,textStatus) {
		//判断是否session超时
		var sessionStatus = xhr.getResponseHeader('sessionStatus');
		
		if (sessionStatus == 'timeout') {
			alert('登录超时，请重新登录');
			location.href = baseUrl + '/logout.jsp';
		}
		
	}	
});