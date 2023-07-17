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
                     <a href="${pageContext.request.contextPath}/GoodListServlet">图书列表</a>
                </p>
            </div>
        </div>
    </div>
    <div style="float: left;margin-left:100px;">
        <form class="form-inline" id="loading" action="${pageContext.request.contextPath}/GoodListServlet" method="post">
            <div class="form-group">
                <label for="exampleInputName2">图书名称</label>
                <input type="text" name="GoodName" value="${condition.GoodName[0]}" class="form-control" id="exampleInputName2" >
                <label for="exampleInputName2">图书类型</label>
                <select id="goodTypeID" name="goodTypeID">
					<c:forEach items="${goodType}" var="i" varStatus="s">
					      <option value ="${i.id}">${i.goodTypeName}</option>
				    </c:forEach>
				</select>
                <button type="submit" id="search" class="btn btn-default">搜索</button>
                <a href="UserIndex.jsp" class="btn btn-default">返回</a>
            </div> 
            
        </form>
    </div>
    <div style="clear: both;">
    	
 		<div class="wrapbottom">
        <div class="column">
            <div class="column-title">
                <a href="#" id="C1">图书订购</a>
            </div>
        </div>
        <div class="column-flower goods2">
            <c:forEach items="${pb.list}" var="good" varStatus="s">
                    <div class="column-flower-item" style="border:1px solid #999;margin:10px;">
                        <a href="${pageContext.request.contextPath}/GoodDetailServlet?id=${good.id}">
                            <img id="imgGoods" style="height: 300px; width: 277px;border:0;" src='${good.goodPicUrl}'
                                 /></a>
                        <div class="goods">
                            <div class="goods-name" style="text-align: left; padding: 5px;">
                                图书名称：${good.goodName}
                            </div>
                            <div class="goods-name" style="text-align: left; padding: 5px;">
                                图书类型：${good.goodTypeName}
                            </div>
                            <div class="goods-name" style="text-align: left; padding: 5px;">
                                图书数量：${good.goodCount}
                            </div>
                            <div class="goods-name" style="text-align: left; padding: 5px;">
                                图书单价：${good.goodUnitPrice}
                            </div>
                            <div class="goods-name" style="text-align: left; padding: 5px;">
                                <a href="${pageContext.request.contextPath}/GoodDetailServlet?id=${good.id}" style="color: Red; text-decoration: none;">
                                    查看详情</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
        </div>
        <div style="clear:both;"></div>
        <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${pb.currentPage == 1}">
                    <li class="disabled">
                </c:if>
               
                <c:if test="${pb.currentPage != 1}">
                    <li>
                </c:if>
                    <a href="${pageContext.request.contextPath}/GoodListServlet?currentPage=${pb.currentPage - 1}&rows=8&GoodName=${condition.GoodName[0]}&goodTypeID=${condition.goodTypeID[0]}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <c:forEach begin="1" end="${pb.totalPage}" var="i" >
                    <c:if test="${pb.currentPage == i}">
                        <li class="active"><a href="${pageContext.request.contextPath}/GoodListServlet?currentPage=${i}&rows=8&GoodName=${condition.GoodName[0]}&goodTypeID=${condition.goodTypeID[0]}">${i}</a></li>
                    </c:if>
                    <c:if test="${pb.currentPage != i}">
                        <li><a href="${pageContext.request.contextPath}/GoodListServlet?currentPage=${i}&rows=8&GoodName=${condition.GoodName[0]}&goodTypeID=${condition.goodTypeID[0]}">${i}</a></li>
                    </c:if>

                </c:forEach>
                <li>
                    <a href="${pageContext.request.contextPath}/GoodListServlet?currentPage=${pb.currentPage + 1}&rows=8&GoodName=${condition.GoodName[0]}&goodTypeID=${condition.goodTypeID[0]}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                <span style="font-size: 25px;margin-left: 5px;">
                    共${pb.totalCount}条记录，共${pb.totalPage}页
                </span>
            </ul>
        </nav>
    </div>
    </div>
    </div>
    <div class="footer clr" style="clear: both; padding-top: 50px;">
        <div class="w990 clr">
            <div class="ft">
                <div>
                    线上图书销售系统 |
                    <a href="AdminLogin.jsp">
                        管理员后台登录</a> |
                    <a href="UserLogin.jsp">
                        用户登录</a>
                </div>
            </div>
        </div>
    </div>
    </form>
</body>
</html>