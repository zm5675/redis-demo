function getInitData(page,method) {
    var pi = $("#pi").val();
    var ps = $("#ps").val();
    $.ajax({
        type: "post",
        dataType: "json",
        contentType: "application/json", //注意：WebMethod()必须加这项，否则客户端数据不会传到服务端
        data: "{ 'pi': '" + pi + "','ps': '" + ps + "'}", //注意：data参数可以是string个int类型
        url: "" + page + "/" + method+"", //模拟web服务，提交到方法
        // 可选的 async:false,阻塞的异步就是同步
        beforeSend: function () {                   // do something.
            // 一般是禁用按钮等防止用户重复提交
            //$("#btnClick").attr({disabled:"disabled"});
            // 或者是显示loading图片
        },
        success: function (data) {
            //alert("success: " + data.d);//注意这里：必须通过data.d才能获取到服务器返回的值
            // 服务端可以直接返回Model，也可以返回序列化之后的字符串，如果需要反序列化：string json = JSON.parse(data.d);
            // 有时候需要嵌套调用ajax请求，也是可以的
            $(".tbl").append(data.d);
            $(".pageth").html($("#pi").val());
        },
        complete: function () {
            //do something.
            //$("#btnClick").removeAttr("disabled");
            // 隐藏loading图片
            //$("#showMessage").val("");
        },
        error: function (data) {
            alert("error: " + data.d);
        }
    });
}

function delRowData(page,method) {
    $(".tbl").on("click", ".btn", function () {
        var id = $(this).attr("id");
        var that = $(this);
        $.ajax({
            type: "post",
            dataType: "json",
            contentType: "application/json", //注意：WebMethod()必须加这项，否则客户端数据不会传到服务端
            data: "{ 'id': '" + id + "'}", //注意：data参数可以是string个int类型
            url: "" + page + "/" + method+"", //模拟web服务，提交到方法
            success: function (data) {
                if (data.d == "1") {
                    alert("删除信息成功");
                    that.parent().parent().remove();
                }
                else {
                    alert("删除信息失败！");
                }
                $(".pageth").html($("#pi").val());
            },
            error: function (data) {
                alert("error: " + data.d);
            }
        });
    });
}

function PageSplit(page,method) {
    //分页
    upPage(page, method);
    downPage(page, method);
}

function showPage(page, method) {
    $(".showpage").click(function () {
        var pi = $(this).text();
        $("#pi").val($(this).text());
        var ps = $("#ps").val();
        //加载页面
        $.ajax({
            type: "post",
            dataType: "json",
            contentType: "application/json", //注意：WebMethod()必须加这项，否则客户端数据不会传到服务端
            data: "{ 'pi': '" + pi + "','ps': '" + ps + "'}", //注意：data参数可以是string个int类型
            url: "" + page + "/" + method + "", //模拟web服务，提交到方法
            // 可选的 async:false,阻塞的异步就是同步
            beforeSend: function () {                   // do something.
                // 一般是禁用按钮等防止用户重复提交
                //$("#btnClick").attr({disabled:"disabled"});
                // 或者是显示loading图片
                $(".tblbody").html("");
            },
            success: function (data) {
                //alert("success: " + data.d);//注意这里：必须通过data.d才能获取到服务器返回的值
                // 服务端可以直接返回Model，也可以返回序列化之后的字符串，如果需要反序列化：string json = JSON.parse(data.d);
                // 有时候需要嵌套调用ajax请求，也是可以的
                //                alert("1234");
                //                alert($("#navSearch"));
                //                    alert(data.d);
                $(".tblbody").append(data.d);
                $(".pageth").html($("#pi").val());
            },
            complete: function () {
                //do something.
                //$("#btnClick").removeAttr("disabled");
                // 隐藏loading图片
                //$("#showMessage").val("");
            },
            error: function (data) {
                alert("error: " + data.d);
            }
        });
    });
}

function upPage(page, method) {
    $(".up_page").click(function () {
        if ((parseInt($("#pi").val()) - 1) >= 1) {
            $("#pi").val((parseInt($("#pi").val()) - 1).toString());
            var pi = $("#pi").val();
            var ps = $("#ps").val();
            //加载页面
            $.ajax({
                type: "post",
                dataType: "json",
                contentType: "application/json", //注意：WebMethod()必须加这项，否则客户端数据不会传到服务端
                data: "{ 'pi': '" + pi + "','ps': '" + ps + "'}", //注意：data参数可以是string个int类型
                url: "" + page + "/" + method + "", //模拟web服务，提交到方法
                // 可选的 async:false,阻塞的异步就是同步
                beforeSend: function () {                   // do something.
                    // 一般是禁用按钮等防止用户重复提交
                    //$("#btnClick").attr({disabled:"disabled"});
                    // 或者是显示loading图片
                    $(".tblbody").html("");
                },
                success: function (data) {
                    //alert("success: " + data.d);//注意这里：必须通过data.d才能获取到服务器返回的值
                    // 服务端可以直接返回Model，也可以返回序列化之后的字符串，如果需要反序列化：string json = JSON.parse(data.d);
                    // 有时候需要嵌套调用ajax请求，也是可以的
                    //                alert("1234");
                    //                alert($("#navSearch"));
                    //                    alert(data.d);
                    $(".tblbody").append(data.d);
                    $(".pageth").html($("#pi").val());
                },
                complete: function () {
                    //do something.
                    //$("#btnClick").removeAttr("disabled");
                    // 隐藏loading图片
                    //$("#showMessage").val("");
                },
                error: function (data) {
                    alert("error: " + data.d);
                }
            });
        }
    });
}

function downPage(page, method) {
    $(".down_page").click(function () {
        if ((parseInt($("#pi").val()) - 1) >= 0) {
            $("#pi").val((parseInt($("#pi").val()) + 1).toString());
            var pi = $("#pi").val();
            var ps = $("#ps").val();
            //加载页面
            $.ajax({
                type: "post",
                dataType: "json",
                contentType: "application/json", //注意：WebMethod()必须加这项，否则客户端数据不会传到服务端
                data: "{ 'pi': '" + pi + "','ps': '" + ps + "'}", //注意：data参数可以是string个int类型
                url: "" + page + "/" + method + "", //模拟web服务，提交到方法
                // 可选的 async:false,阻塞的异步就是同步
                beforeSend: function () {                   // do something.
                    // 一般是禁用按钮等防止用户重复提交
                    //$("#btnClick").attr({disabled:"disabled"});
                    // 或者是显示loading图片
                    $(".tblbody").html("");
                },
                success: function (data) {
                    //alert("success: " + data.d);//注意这里：必须通过data.d才能获取到服务器返回的值
                    // 服务端可以直接返回Model，也可以返回序列化之后的字符串，如果需要反序列化：string json = JSON.parse(data.d);
                    // 有时候需要嵌套调用ajax请求，也是可以的
                    $(".tblbody").append(data.d);
                    $(".pageth").html($("#pi").val());
                },
                complete: function () {
                    //do something.
                    //$("#btnClick").removeAttr("disabled");
                    // 隐藏loading图片
                    //$("#showMessage").val("");
                },
                error: function (data) {
                    alert("error: " + data.d);
                }
            });
        }
    });
}

function searchAdminContract() {
    var pi = $("#pi").val();
    var ps = $("#ps").val();
    $.ajax({
        type: "post",
        dataType: "json",
        contentType: "application/json", //注意：WebMethod()必须加这项，否则客户端数据不会传到服务端
        data: "{ 'pi': '" + pi + "','ps': '" + ps + "','txt1':'" + $("#txt1").val() + "','txt2':'" + $("#txt2").val() + "','txt3':'" + $("#txt3").val() + "','txt4':'" + $("#txt4").val() + "','txt5':'" + $("#txt5").val() + "'}", //注意：data参数可以是string个int类型
        url: "AdminContractSearch.aspx/loadContractSearch", //模拟web服务，提交到方法
        // 可选的 async:false,阻塞的异步就是同步
        beforeSend: function () {                   // do something.
            // 一般是禁用按钮等防止用户重复提交
            //$("#btnClick").attr({disabled:"disabled"});
            // 或者是显示loading图片
            $(".tblbody").html("");
        },
        success: function (data) {
            //alert("success: " + data.d);//注意这里：必须通过data.d才能获取到服务器返回的值
            // 服务端可以直接返回Model，也可以返回序列化之后的字符串，如果需要反序列化：string json = JSON.parse(data.d);
            // 有时候需要嵌套调用ajax请求，也是可以的
            $(".tblbody").append(data.d);
            $(".pageth").html($("#pi").val());
        },
        complete: function () {
            //do something.
            //$("#btnClick").removeAttr("disabled");
            // 隐藏loading图片
            //$("#showMessage").val("");
        },
        error: function (data) {
            alert("error: " + data.d);
        }
    });
}

function searchContract() {
    var pi = $("#pi").val();
    var ps = $("#ps").val();
    $.ajax({
        type: "post",
        dataType: "json",
        contentType: "application/json", //注意：WebMethod()必须加这项，否则客户端数据不会传到服务端
        data: "{ 'pi': '" + pi + "','ps': '" + ps + "','UserID':'" + $("#UserID").val() + "','txt1':'" + $("#txt1").val() + "','txt2':'" + $("#txt2").val() + "','txt3':'" + $("#txt3").val() + "','txt4':'" + $("#txt4").val() + "'}", //注意：data参数可以是string个int类型
        url: "ContractSearch.aspx/loadContractSearch", //模拟web服务，提交到方法
        // 可选的 async:false,阻塞的异步就是同步
        beforeSend: function () {                   // do something.
            // 一般是禁用按钮等防止用户重复提交
            //$("#btnClick").attr({disabled:"disabled"});
            // 或者是显示loading图片
            $(".tblbody").html("");
        },
        success: function (data) {
            //alert("success: " + data.d);//注意这里：必须通过data.d才能获取到服务器返回的值
            // 服务端可以直接返回Model，也可以返回序列化之后的字符串，如果需要反序列化：string json = JSON.parse(data.d);
            // 有时候需要嵌套调用ajax请求，也是可以的
            $(".tblbody").append(data.d);
            $(".pageth").html($("#pi").val());
        },
        complete: function () {
            //do something.
            //$("#btnClick").removeAttr("disabled");
            // 隐藏loading图片
            //$("#showMessage").val("");
        },
        error: function (data) {
            alert("error: " + data.d);
        }
    });
}

function updateContractRowData(page, method) {
    $(".tbl").on("click", ".btn1", function () {
        var id = $(this).attr("id");
        var that = $(this);
        $.ajax({
            type: "post",
            dataType: "json",
            contentType: "application/json", //注意：WebMethod()必须加这项，否则客户端数据不会传到服务端
            data: "{ 'id': '" + id + "'}", //注意：data参数可以是string个int类型
            url: "" + page + "/" + method + "", //模拟web服务，提交到方法
            success: function (data) {
                if (data.d == "1") {
                    alert("更新信息成功");
                    that.parent().parent().find(".ContractState").text("已签订");
                    //flag = 1;
                }
                else {
                    alert("更新信息失败！");
                }
                $(".pageth").html($("#pi").val());
            },
            error: function (data) {
                alert("error: " + data.d);
            }
        });
    });
}

function exportContract(page, method) {
    $("#export").click(function () {
        $.ajax({
            type: "post",
            dataType: "json",
            contentType: "application/json", //注意：WebMethod()必须加这项，否则客户端数据不会传到服务端
            data: "{ 'id': '1'}", //注意：data参数可以是string个int类型
            url: "" + page + "/" + method + "", //模拟web服务，提交到方法
            success: function (data) {
                if (data.d) {
                    window.location = "Excel/SelectedExport.xls";
                }
            },
            error: function (data) {
                alert("error: " + data.d);
            }
        });
    });
}