var $parentNode = window.parent.document;

function $childNode(name) {
    return window.frames[name]
}


$('.tooltip-demo').tooltip({
    selector: "[data-toggle=tooltip]",
    container: "body"
});

// 使用animation.css修改Bootstrap Modal
$('.modal').appendTo("body");

$("[data-toggle=popover]").popover();

//折叠ibox
$('.collapse-link').click(function () {
    var ibox = $(this).closest('div.ibox');
    var button = $(this).find('i');
    var content = ibox.find('div.ibox-content');
    content.slideToggle(200);
    button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
    ibox.toggleClass('').toggleClass('border-bottom');
    setTimeout(function () {
        ibox.resize();
        ibox.find('[id^=map-]').resize();
    }, 50);
});

//关闭ibox
$('.close-link').click(function () {
    var content = $(this).closest('div.ibox');
    content.remove();
});

//判断当前页面是否在iframe中
if (top == this) {
    var gohome = '<div class="gohome"><a class="animated bounceInUp" href="index.html?v=4.0" title="返回首页"><i class="fa fa-home"></i></a></div>';
    $('body').append(gohome);
}

//animation.css
function animationHover(element, animation) {
    element = $(element);
    element.hover(
        function () {
            element.addClass('animated ' + animation);
        },
        function () {
            //动画完成之前移除class
            window.setTimeout(function () {
                element.removeClass('animated ' + animation);
            }, 2000);
        });
}

//拖动面板
function WinMove() {
    var element = "[class*=col]";
    var handle = ".ibox-title";
    var connect = "[class*=col]";
    $(element).sortable({
        handle: handle,
        connectWith: connect,
        tolerance: 'pointer',
        forcePlaceholderSize: true,
        opacity: 0.8,
    })
        .disableSelection();
};
//进行发布新讯
$("#sendMessage").click(function () {
    var mes = $("#showMessage").val();
    var account = $("#hidAccount").val()
    //标准的写法：
    $.ajax({
        type: "post",
        dataType: "json",
        contentType: "application/json", //注意：WebMethod()必须加这项，否则客户端数据不会传到服务端
        data: "{ 'mes': '" + mes + "','account':'" + account + "'}",//注意：data参数可以是string个int类型
        url: "Index_v1.aspx/SendMessage",//模拟web服务，提交到方法
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
            $("#message").append("<p>" + mes + "</p>");
        },
        complete: function () {
            //do something.
            //$("#btnClick").removeAttr("disabled");
            // 隐藏loading图片
            $("#showMessage").val("");
        },
        error: function (data) {
            alert("error: " + data.d);
        }
    });
});
$.ajax({
    type: "post",
    dataType: "json",
    contentType: "application/json", //注意：WebMethod()必须加这项，否则客户端数据不会传到服务端
    //data: "{ 'mes': '" + mes + "','account':'" + account + "'}",//注意：data参数可以是string个int类型
    data: "{'flag': '1'}",
    url: "Index_v1.aspx/GetIndexNews",//模拟web服务，提交到方法
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
        var arr = data.d.split("|");
        for (var i = 0; i < arr.length; i++) {
            $("#message").append("<p>" + arr[i] + "</p>");
        }
    },
    complete: function () {
        //do something.
        //$("#btnClick").removeAttr("disabled");
        // 隐藏loading图片
    },
    error: function (data) {
        alert("error: " + data.d);
    }
});
//进行修改输入框的显示
$("#ShowStar").click(function () {
    $(".showTo").toggle();
});
$.ajax({
    type: "post",
    dataType: "json",
    contentType: "application/json", //注意：WebMethod()必须加这项，否则客户端数据不会传到服务端
    //data: "{ 'mes': '" + mes + "','account':'" + account + "'}",//注意：data参数可以是string个int类型
    data: "{'flag': '1'}",
    url: "Index_v1.aspx/GetIndexDetail",//模拟web服务，提交到方法
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
        if (data.d != "") {
            var arr = data.d.split("|");
            $("#Name").text(arr[0]);
            $("#Detail").text(arr[1]);
            $("#Status").text(arr[2]);
            $("#Note").text(arr[3]);
            $("#ChineseName").text(arr[4]);
            $("#EnglishName").text(arr[5]);
            $("#OtherName").text(arr[6]);
            $("#Country").text(arr[7]);
            $("#zu").text(arr[8]);
            $("#BirthPlace").text(arr[9]);
            $("#BirthDate").text(arr[10]);
            $("#School").text(arr[11]);
            $("#Height").text(arr[12]);
            $("#Weight").text(arr[13]);
            //进行按钮的赋值
            $("#Name1").val(arr[0]);
            $("#Detail1").val(arr[1]);
            $("#Status1").val(arr[2]);
            $("#Note1").val(arr[3]);
            $("#ChineseName1").val(arr[4]);
            $("#EnglishName1").val(arr[5]);
            $("#OtherName1").val(arr[6]);
            $("#Country1").val(arr[7]);
            $("#zu1").val(arr[8]);
            $("#BirthPlace1").val(arr[9]);
            $("#BirthDate1").val(arr[10]);
            $("#School1").val(arr[11]);
            $("#Height1").val(arr[12]);
            $("#Weight1").val(arr[13]);
        }

    },
    complete: function () {
        //do something.
        //$("#btnClick").removeAttr("disabled");
        // 隐藏loading图片
    },
    error: function (data) {
        alert("error: " + data.d);
    }
});
$("#SureEdit").click(function () {
    var Name1 = $("#Name1").val();
    var Detail1 = $("#Detail1").val();
    var Status1 = $("#Status1").val();
    var Note1 = $("#Note1").val();
    var ChineseName1 = $("#ChineseName1").val();
    var EnglishName1 = $("#EnglishName1").val();
    var OtherName1 = $("#OtherName1").val();
    var Country1 = $("#Country1").val();
    var zu1 = $("#zu1").val();
    var BirthPlace1 = $("#BirthPlace1").val();
    var BirthDate1 = $("#BirthDate1").val().substring(0,10);
    var School1 = $("#School1").val();
    var Height1 = $("#Height1").val();
    var Weight1 = $("#Weight1").val();
    $.ajax({
        type: "post",
        dataType: "json",
        contentType: "application/json", //注意：WebMethod()必须加这项，否则客户端数据不会传到服务端
        //data: "{ 'mes': '" + mes + "','account':'" + account + "'}",//注意：data参数可以是string个int类型
        data: "{'Name': '" + Name1 + "','Detail': '" + Detail1 + "','Status': '" + Status1 + "','Note': '" + Note1 + "','ChineseName': '" + ChineseName1 + "','EnglishName': '" + EnglishName1 + "','OtherName': '" + OtherName1 + "','Country': '" + Country1 + "','zu': '" + zu1 + "','BirthPlace': '" + BirthPlace1 + "','BirthDate': '" + BirthDate1 + "','School': '" + School1 + "','Height': '" + Height1 + "','Weight': '" + Weight1 + "'}",
        url: "Index_v1.aspx/UpdateIndexDetail",//模拟web服务，提交到方法
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
            if (data.d == "1") {
                var arr = data.d.split("|");
                $("#Name").text(Name1);
                $("#Detail").text(Detail1);
                $("#Status").text(Status1);
                $("#Note").text(Note1);
                $("#ChineseName").text(ChineseName1);
                $("#EnglishName").text(EnglishName1);
                $("#OtherName").text(OtherName1);
                $("#Country").text(Country1);
                $("#zu").text(zu1);
                $("#BirthPlace").text(BirthPlace1);
                $("#BirthDate").text(BirthDate1);
                $("#School").text(School1);
                $("#Height").text(Height1);
                $("#Weight").text(Weight1);
                //进行按钮的赋值
                $("#Name1").val(Name1);
                $("#Detail1").val(Detail1);
                $("#Status1").val(Status1);
                $("#Note1").val(Note1);
                $("#ChineseName1").val(ChineseName1);
                $("#EnglishName1").val(EnglishName1);
                $("#OtherName1").val(OtherName1);
                $("#Country1").val(Country1);
                $("#zu1").val(zu1);
                $("#BirthPlace1").val(BirthPlace1);
                $("#BirthDate1").val(BirthDate1);
                $("#School1").val(School1);
                $("#Height1").val(Height1);
                $("#Weight1").val(Weight1);
            }

        },
        complete: function () {
            //do something.
            //$("#btnClick").removeAttr("disabled");
            // 隐藏loading图片
        },
        error: function (data) {
            alert("error: " + data.d);
        }
    });
});