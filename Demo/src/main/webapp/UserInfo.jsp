<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head2" >
    <title></title>
    <link href="${pageContext.request.contextPath}/Admin/CSS/bootstrap.min.css?v=3.3.6" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/Admin/CSS/font-awesome.css?v=4.4.0" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/Admin/CSS/animate.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/Admin/CSS/style.css?v=4.1.0" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/Admin/Styles/layui/css/layui.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/Admin/Styles/admin.css" rel="stylesheet" />
</head>
<body class="gray-bg">
    <div class="page-content-wrap">
        <form class="layui-form" action="${pageContext.request.contextPath}/UpdateUserInfoServlet" method="post">
            <div class="layui-tab" style="margin: 0;">
                <div class="layui-tab-content">
                    <div class="layui-tab-item">个人信息管理</div>
                    <input type="hidden" name="id" value="${user.id}">
                    <div class="layui-tab-item layui-show">
                        <div class="layui-form-item">
                            <label class="layui-form-label">账 号：</label>
                            <div class="layui-input-block">
                                <input type="text" name="userName" readonly="readonly" value="${user.userName}" id="userName" placeholder="请输入账号" autocomplete="off" class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">密 码：</label>
                            <div class="layui-input-block">
                                <input type="text" name="userPassword" value="${user.userPassword}" id="userPassword" placeholder="请输入密码" autocomplete="off" class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">电 话：</label>
                            <div class="layui-input-block">
                                <input type="text" name="userTel" value="${user.userTel}" id="userTel" placeholder="请输入电话" autocomplete="off" class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">地 址：</label>
                            <div class="layui-input-block">
                                <input type="text" name="userAddr" value="${user.userAddr}" id="userAddr" placeholder="请输入地址" autocomplete="off" class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">备 注：</label>
                            <div class="layui-input-block">
                                <input type="text" name="content" value="${user.content}" id="content" placeholder="请输入备注" autocomplete="off" class="layui-input"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-form-item" style="padding-left: 10px;">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-normal" lay-submit lay-filter="formDemo" type="submit">立即提交</button>
                </div>
            </div>
            <div class="alert alert-warning alert-dismissible" role="alert" style="margin-top:55px;height:50px;">
		        <button type="button" class="close" data-dismiss="alert" >
		            <span>&times;</span>
		        </button>
		        <strong>${msg}</strong>
		    </div>
        </form>
    </div>
</body>
</html>