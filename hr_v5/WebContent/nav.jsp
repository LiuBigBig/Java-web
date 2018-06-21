<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html>
<html>
	<head>
		<!--设置视口，只在移动端浏览器下生效，设置视口宽度=当前手机设备宽度，默认缩放1，用户不允许手动缩放-->
		<meta name="viewport" content="width=width-device,initial-scale=1.0,user-scalable=no"/>
		<meta charset="UTF-8">
		<title></title>
		<!--引入bootstrap的核心css-->
		<link rel="stylesheet" href="js/bootstrap/css/bootstrap.css" />
		
		<style>
			* {	
				margin: 0;
				padding: 0;
			}
			
			body {
				background: #333333;
			}	
			
			/*头像区*/
			.touxiang {
				width: 150px;
				height: 150px;
				margin: 20px auto;
				border-radius: 50%;
				border: 5px solid #ffffff;
				overflow: hidden;
			}		
			
			/*左侧导航链接*/
			.nav-left .list-group-item {
				padding: 0!important;
				background: transparent;	
				border: none;
				border-radius: 0!important;	
				text-align: center;	
				color: #eeeeee;	
				font-size: 16px;
				line-height: 45px;
				letter-spacing: 1px;
				border-bottom: 1px solid #444444;
			}
		</style>
	</head>
	<body>		
		<div class="container-fluid">
			
			<!--头像区开始-->
			<div class="row touxiang">
				<a href="UpdateHead.html" title="点击修改用户头像" target="content">
					<img id="empPic" src="images/default2.jpg" class="img-responsive"/>
				</a>
			</div>			
			<!--头像区结束-->
			
			<p class="text-center" style="color: #eeeeee;">
				欢迎您，${empLoginName}
			</p>
			
			<!--左侧导航链接开始-->
			<div class="row nav-left">
				<div class="list-group">
					<shiro:hasRole name="deptAdmin">
						<a href="QueryDept.html" target="content" class="list-group-item"><span class="glyphicon glyphicon-paste"></span> 部门管理</a>
					</shiro:hasRole>
					<shiro:hasRole name="jobAdmin">
						<a href="QueryJob.html" target="content" class="list-group-item"><span class="glyphicon glyphicon-hourglass"></span> 职务管理</a>
					</shiro:hasRole>
					<shiro:hasRole name="empAdmin">
						<a href="QueryEmp.html" target="content" class="list-group-item"><span class="glyphicon glyphicon-baby-formula"></span> 员工管理</a>
					</shiro:hasRole>
					<shiro:hasRole name="anaAdmin">
						<a href="Analyze.html" target="content" class="list-group-item"><span class="glyphicon glyphicon glyphicon-stats"></span> 数据分析</a>
					</shiro:hasRole>
					<shiro:hasRole name="searchAdmin">					
						<a href="SearchEmp.html" target="content" class="list-group-item"><span class="glyphicon glyphicon-education"></span> 数据检索</a>
					</shiro:hasRole>
					<shiro:hasRole name="picAdmin">
						<a href="QueryPic.html" target="content" class="list-group-item"><span class="glyphicon glyphicon-eye-open"></span> 公司图库</a>
					</shiro:hasRole>
					<a href="UpdateEmpPwd.html" target="content" class="list-group-item"><span class="glyphicon glyphicon-modal-window"></span> 修改密码</a>
					<a href="<%=request.getContextPath() %>/logout" target="_top" class="list-group-item"><span class="glyphicon glyphicon-th-large"></span> 退出登录</a>					
				</div>
			</div>			
			<!--左侧导航链接结束-->
			
		</div>
		
		<!--引入jquery-->
		<script type="text/javascript" src="js/jquery.js" ></script>
		<!--引入全局js-->
		<script src="js/global.js"></script>
		<!--引入bootstrap.js，实现js功能-->
		<script type="text/javascript" src="js/bootstrap/js/bootstrap.js" ></script>
		<!--引入layer层-->
		<script src="js/layer/layer.js"></script>
		
		<script>
			$(document).ready(function() {
				
				//如果用户有头像数据，显示用户头像
				$.get(baseUrl + '/emp/getEmpPic',function(data) {
					
					if (data && data != 'null') {
						
						$('#empPic').attr('src',data);
						
					}
					
				})
				.error(function(xhr,errorStatus,errorText) {
					layer.msg('请求头像数据失败,' + errorText);					
				});				
				
			});			
		</script>
	</body>
</html>