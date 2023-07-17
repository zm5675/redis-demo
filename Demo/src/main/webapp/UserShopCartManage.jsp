<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title></title>
    <link href="Styles/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-2.1.0.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>
    <script>
        function deleteShopCart(id){
            //用户安全提示
            if(confirm("您确定要删除吗？")){
                //访问路径
                location.href="${pageContext.request.contextPath}/DelShopCartServlet?id="+id;
            }
        }
    </script>
</head>
<body>
<div class="container">
    <h3 style="text-align: center">订购列表</h3>
    
    <div style="float: right;margin: 5px;">
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/CreateOrderServlet?totalCount=${pb.totalPage}">生成订单</a>
    </div>
    <form id="form">
        <table border="1" class="table table-bordered table-hover">
        <tr class="success">
            <th>编号</th>
            <th>用户</th>
            <th>图书名称</th>
            <th>图书单价</th>
            <th>订购数量</th>
            <th>添加时间</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${pb.list}" var="shopCart" varStatus="s">
            <tr>
                <td>${s.count}</td>
                <td>${shopCart.userName}</td>
                <td>${shopCart.goodName}</td>
                <td>${shopCart.goodUnitPrice}</td>
                <td>${shopCart.goodNum}</td>
                <td>${shopCart.addDate}</td>
                <td>
                    <a class="btn btn-default btn-sm" href="javascript:deleteShopCart(${shopCart.id});">删除</a></td>
            </tr>
        </c:forEach>
    </table>
    </form>
    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${pb.currentPage == 1}">
                    <li class="disabled">
                </c:if>
               
                <c:if test="${pb.currentPage != 1}">
                    <li>
                </c:if>
                    <a href="${pageContext.request.contextPath}/UserShopCartManageServlet?currentPage=${pb.currentPage - 1}&rows=5&GoodName=${condition.GoodName[0]}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <c:forEach begin="1" end="${pb.totalPage}" var="i" >
                    <c:if test="${pb.currentPage == i}">
                        <li class="active"><a href="${pageContext.request.contextPath}/UserShopCartManageServlet?currentPage=${i}&rows=5&GoodName=${condition.GoodName[0]}">${i}</a></li>
                    </c:if>
                    <c:if test="${pb.currentPage != i}">
                        <li><a href="${pageContext.request.contextPath}/UserShopCartManageServlet?currentPage=${i}&rows=5&GoodName=${condition.GoodName[0]}">${i}</a></li>
                    </c:if>

                </c:forEach>
                <li>
                    <a href="${pageContext.request.contextPath}/UserShopCartManageServlet?currentPage=${pb.currentPage + 1}&rows=5&GoodName=${condition.GoodName[0]}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                <span style="font-size: 25px;margin-left: 5px;">
                    共${pb.totalCount}条记录，共${pb.totalPage}页
                </span>
            </ul>
        </nav>
    </div>
    <div class="alert alert-warning alert-dismissible" role="alert" style="margin-top:55px;height:50px;">
		        <button type="button" class="close" data-dismiss="alert" >
		            <span>&times;</span>
		        </button>
		        <strong>${msg}</strong>
		    </div>
</div>
</body>
</html>