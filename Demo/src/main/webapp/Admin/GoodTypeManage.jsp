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
        function deleteGoodType(id){
            //用户安全提示
            if(confirm("您确定要删除吗？")){
                //访问路径
                location.href="${pageContext.request.contextPath}/DelGoodTypeServlet?id="+id;
            }
        }
    </script>
</head>
<body>
<div class="container">
    <h3 style="text-align: center">图书类型信息列表</h3>
    <div style="float: left;">
        <form class="form-inline" id="loading" action="${pageContext.request.contextPath}/GoodTypeManageServlet" method="post">
            <div class="form-group">
                <label for="exampleInputName2">类型名称</label>
                <input type="text" name="GoodTypeName" value="${condition.GoodTypeName[0]}" class="form-control" id="exampleInputName2" >
            </div> 
            <button type="submit" id="search" class="btn btn-default">查询</button>
        </form>
    </div>
    <div style="float: right;margin: 5px;">
        <%-- <a class="btn btn-primary" href="${pageContext.request.contextPath}/Admin/AddAdmin.jsp">添加管理员信息</a> --%>
    </div>
    <form id="form">
        <table border="1" class="table table-bordered table-hover">
        <tr class="success">
            <th>类型编号</th>
            <th>类型名称</th>
            <th>添加时间</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${pb.list}" var="GoodType" varStatus="s">
            <tr>
                <td>${GoodType.id}</td>
                <td>${GoodType.goodTypeName}</td>
                <td>${GoodType.addDate}</td>
                <td><a class="btn btn-default btn-sm" href="${pageContext.request.contextPath}/EditGoodTypeServlet?id=${GoodType.id}">修改</a>&nbsp;
                    <a class="btn btn-default btn-sm" href="javascript:deleteGoodType(${GoodType.id});">删除</a></td>
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
                    <a href="${pageContext.request.contextPath}/GoodTypeManageServlet?currentPage=${pb.currentPage - 1}&rows=5&GoodTypeName=${condition.GoodTypeName[0]}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <c:forEach begin="1" end="${pb.totalPage}" var="i" >
                    <c:if test="${pb.currentPage == i}">
                        <li class="active"><a href="${pageContext.request.contextPath}/GoodTypeManageServlet?currentPage=${i}&rows=5&GoodTypeName=${condition.GoodTypeName[0]}">${i}</a></li>
                    </c:if>
                    <c:if test="${pb.currentPage != i}">
                        <li><a href="${pageContext.request.contextPath}/GoodTypeManageServlet?currentPage=${i}&rows=5&GoodTypeName=${condition.GoodTypeName[0]}">${i}</a></li>
                    </c:if>

                </c:forEach>
                <li>
                    <a href="${pageContext.request.contextPath}/GoodTypeManageServlet?currentPage=${pb.currentPage + 1}&rows=5&GoodTypeName=${condition.GoodTypeName[0]}" aria-label="Next">
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
</body>
</html>