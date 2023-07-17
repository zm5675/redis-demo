<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head >
    <title></title>
    <link href="CSS/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link type="text/css" href="Styles/index.css" rel="stylesheet" />
    <link type="text/css" href="Styles/lunbou.css" rel="stylesheet" />
    <script src="js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/lunbou.js"> </script>
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
    </style>
</head>
<body>
    <form id="form1">
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
                     <a href="${pageContext.request.contextPath}/GoodListServlet">线上图书列表</a>
                </p>
            </div>
        </div>
    </div>
    <div class="banner">
        <ul id="lunbo">
            <li class="pic">
                <img src="LunBoPic/lunbo1.jpg" alt="" /></li>
            <li class="pic">
                <img src="LunBoPic/lunbo2.jpg" alt="" /></li>
            <li class="pic">
                <img src="LunBoPic/lunbo3.jpg" alt="" /></li>
            <li class="pic">
                <img src="LunBoPic/本草纲目.jpg" alt="" /></li>
            <li class="pic">
                <img src="LunBoPic/鬼谷子.jpg" alt="" /></li>
            <li class="pic">
                <img src="LunBoPic/目送.jpg" alt="" /></li>
            <li class="pic">
                <img src="LunBoPic/羊皮卷.jpg" alt="" /></li>
        </ul>
    </div>
    <div class="yuan">
        <div class="circle">
        </div>
    </div>
    <div style="clear: both;">
 		<div class="wrapbottom">
        <div class="column">
            <div class="column-title">
                <span id="C0">线上图书销售系统介绍</span>
            </div>
        </div>
        <div class="column-flower goods2" id="coffeeContent">
            <%--<asp:Label ID="Label1" runat="server" Text="Label"></asp:Label>--%>
            <p>1、用户皆可对系统线上图书进行订购。</p>

            <p>2、线上图书信息在线上图书列表中可以进行浏览。</p>

            <p>3、用户皆可进行线上图书订购。</p>

            <p>4、订购后确认线上图书无误就可以进行结算生成订单。</p>

            <p>5、每个订单对应唯一的订单号。</p>

            <p>6、欢迎用户进行线上图书订购。</p>
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
                        |
                    <a href="UserLogin.jsp">
                        用户登录</a>
                </div>
            </div>
        </div>
    </div>
    </form>
</body>
</html>