<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
    <link href="Styles/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" href="Styles/index.css" rel="stylesheet" />
    <link type="text/css" href="Styles/lunbou.css" rel="stylesheet" />
    <script src="js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/lunbou.js"> </script>
    <link href="${pageContext.request.contextPath}/Admin/Styles/layui/css/layui.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/Admin/Styles/admin.css" rel="stylesheet" />
    <style type="text/css">
        .footer .ft div
        {
            text-align: center;
            padding: 5px;
        }
        .w990
        {
            width: 990px !important;
            margin: auto;
        }
        /* 清理浮动 */
        .clearfix:after, .clr:after
        {
            visibility: hidden;
            display: block;
            font-size: 0;
            content: " ";
            clear: both;
            height: 0;
        }
        .clearfix, .clr
        {
            zoom: 1; /* for IE6 IE7 */
        }
        a
        {
            text-decoration: none;
        }
        #goodTypeID {
            display: block;
            height: 32px;
            width: 150px;
        }
    </style>
</head>
<body style="">
    <div class="wrap">
        <div class="header">
            <div class="clearfix">
                <p class="right">
                	<%
                		String url="UserLogin.jsp";
                		String str="用户登录";
                		if(session.getAttribute("UserID")!=null){
                			url="UserHome.jsp";
                			str="个人中心";
                		}
                	%>
                     <a href="<%=url%>"><%=str %></a>
                     <a href="${pageContext.request.contextPath}/GoodListServlet">图书列表</a>
                </p>
            </div>
        </div>
    </div>
    <div style="clear: both;">
    	
 		<div class="wrapbottom">
        <div class="column">
            <div class="column-title">
                <a href="#" id="C1">订购明细</a>
            </div>
        </div>
        <div class="column-flower goods2">
            <div class="column-flower-item tbl1">
            	<form action="${pageContext.request.contextPath}/AddShopCartServlet" method="post">
                <table>
                    <tr>
                        <td>
                            <img id="imgGoods" style="height: 300px; width: 280px" src="${good.goodPicUrl}" />
                        </td>
                        <td>
                            <div style="width: 50px;">
                            	<input type="hidden" name="GoodID" id="GoodID" value="${good.id}" />
                            </div>
                        </td>
                        <td>
                            <table>
                                <tr>
                                    <td style="width: 200px;">
                                        <div class="goods-name" style="text-align: left; padding: 8px; width: 200px;">
                                            图书名称：${good.goodName}
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width: 200px;">
                                        <div class="goods-name" style="text-align: left; padding: 8px; width: 200px;">
                                            图书类型：${good.goodTypeName}
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width: 200px;">
                                        <div style="text-align: left; padding: 8px; width: 200px;">
                                            图书数量：${good.goodCount}
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width: 200px;">
                                        <div style="text-align: left; padding: 8px; width: 200px;">
                                            图书单价：${good.goodUnitPrice}
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width: 200px;">
                                        <div style="text-align: left; padding: 8px; width: 200px;">
                                            图书详情：${good.content}
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width: 200px;">
                                        <div style="text-align: left; padding: 8px; width: 200px;">
                                            订购数量：<input type="text" name="GoodNum" value="" id="GoodNum" placeholder="请输入订购数量" autocomplete="off" class="layui-input"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width: 200px;">
                                        <div class="goods-name" style="text-align: left; padding: 8px; width: 200px;">
                                            <button class="layui-btn layui-btn-normal" lay-submit lay-filter="formDemo" type="submit">立即提交</button>
                                        </div>
                                    </td>
                                </tr>
                            </table>                            
                        </td>
                    </tr>
                </table>
                <div class="alert alert-warning alert-dismissible" role="alert" style="margin-top:55px;height:50px;">
		        <button type="button" class="close" data-dismiss="alert" >
		            <span>&times;</span>
		        </button>
		        <strong>${msg}</strong>
		    </div>
                </form>
            </div>
        </div>
    </div>
    </div>
    <div class="footer clr" style="clear: both; padding-top: 50px;">
        <div class="w990 clr">
            <div class="ft">
                <div>
                    线上图书销售系统 |
                    <a href="AdminLogin.jsp">
                        管理员后台登录</a> 
                </div>
            </div>
        </div>
    </div>
    
</body>
</html>