<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <style type="text/css">
        #goodTypeID {
            display: block;
            height: 32px;
            width: 150px;
        }
    </style>
</head>
<body class="gray-bg">
    <div class="page-content-wrap">
        <form class="layui-form" action="${pageContext.request.contextPath}/UpdateGoodServlet" method="post">
            <div class="layui-tab" style="margin: 0;">
                <div class="layui-tab-content">
                    <div class="layui-tab-item">图书信息修改</div>
                    <input type="hidden" name="id" value="${good.id}">
                    <div class="layui-tab-item layui-show">
                    	<div class="layui-form-item">
                            <label class="layui-form-label">图书类型：</label>
                            <div class="layui-input-block">
                                <select id="goodTypeID" name="goodTypeID">
								  	<c:forEach items="${goodType}" var="i" varStatus="s">
					            		<option value ="${i.id}">${i.goodTypeName}</option>
					        		</c:forEach>
								</select>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">图书名称：</label>
                            <div class="layui-input-block">
                                <input type="text" name="goodName" value="${good.goodName}" id="goodName" placeholder="请输入图书名称" autocomplete="off" class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">图书数量：</label>
                            <div class="layui-input-block">
                                <input type="text" name="goodCount" value="${good.goodCount}" id="goodCount" placeholder="请输入图书数量" autocomplete="off" class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">图书单价：</label>
                            <div class="layui-input-block">
                                <input type="text" name="goodUnitPrice" value="${good.goodUnitPrice}" id="goodUnitPrice" placeholder="请输入图书单价" autocomplete="off" class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">备注信息：</label>
                            <div class="layui-input-block">
                                <input type="text" name="content" value="${good.content}" id="content" placeholder="请输入备注信息" autocomplete="off" class="layui-input"/>
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