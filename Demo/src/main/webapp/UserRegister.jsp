<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="gb2312">
	<title>注册</title>	
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<script src="js/jquery-2.1.0.min.js"></script>
	<link rel="stylesheet" href="Styles/font-awesome.css">
	<link rel="stylesheet" href="Styles/bootstrap.css">
	<link rel="stylesheet" href="Styles/bootstrap-responsive.css">
    <link rel="stylesheet" href="Styles/application.css">
    <script src="js/bootstrap.min.js"></script>
</head>
<body class="login" style="background:url('img/11.jpg') no-repeat;background-size:100%">
<div class="account-container login stacked">	
	<div class="content clearfix">		
		<form action="${pageContext.request.contextPath}/UserRegisterServlet" method="post">
			<h1>用户注册</h1>					
			<div class="login-fields">				
				<p>注册您的用户账号:</p>				
				<div class="field">
					<label for="username">账 号:</label>
					<input type="text" name="Account" value="" style="padding-left:10px;" id="Account"  placeholder="用户名" class="login username-field" />
				</div>				
				<div class="field">
					<label for="password">密 码:</label>
					<input type="password" name="Password" value="" style="padding-left:10px;" id="Password"  placeholder="密码" class="login password-field"/>
				</div>		
				<div class="field">
					<label for="password">电 话:</label>
					<input type="text" name="Tel" value="" style="padding-left:10px;" id="Tel"  placeholder="电话" class="login password-field"/>
				</div>
				<div class="field">
					<label for="password">地 址:</label>
					<input type="text" name="Addr" value="" style="padding-left:10px;" id="Addr"  placeholder="地址" class="login password-field"/>
				</div>
				<div class="field">
					<label for="password">备 注:</label>
					<input type="text" name="Content" value="" style="padding-left:10px;" id=""Content""  placeholder="备注" class="login password-field"/>
				</div>		
			</div> 			
			<div class="login-actions">
				<input id="Button1" type="submit" class="button btn btn-primary btn-large" value="注 册"/>
			</div> 
			<div>
				 <span style="color:#555">已注册？</span><a href="UserLogin.jsp" style="text-decoration:none;"><span style="color:red;">去登录</span></a>
			</div>
			<!-- 出错显示的信息框 -->
		    <div class="alert alert-warning alert-dismissible" role="alert" style="margin-top:85px;height:32px;">
		        <button type="button" class="close" data-dismiss="alert" >
		            <span>&times;</span>
		        </button>
		        <strong>${login_msg}</strong>
		    </div>
		</form>
	</div> 
</div> 
</body>
</html>