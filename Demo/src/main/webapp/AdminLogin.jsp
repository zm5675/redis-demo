<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
    
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="gb2312">
	<title>登录</title>	
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
		<form action="${pageContext.request.contextPath}/AdminLoginServlet" method="post">
			<h1>线上图书销售系统</h1>					
			<div class="login-fields">				
				<p>登录您的管理员账号:</p>				
				<div class="field">
					<label for="username">账 号:</label>
					<input type="text" name="Account" value="" style="padding-left:10px;" id="Account"  placeholder="用户名" class="login username-field" />
				</div>				
				<div class="field">
					<label for="password">密 码:</label>
					<input type="password" name="Password" value="" style="padding-left:10px;" id="Password"  placeholder="密码" class="login password-field"/>
				</div>				
			</div> 			
			<div class="login-actions">
				<input id="Button1" type="submit" class="button btn btn-primary btn-large" value="登 录"/>
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