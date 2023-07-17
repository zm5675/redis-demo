/**判断是否是手机号**/
function isPhoneNumber(tel) {
    var reg = /^0?1[3|4|5|6|7|8][0-9]\d{8}$/;
    return reg.test(tel);
}
function checkForm() {
    if ($.trim($("#Account").val()).length == 0) {
        alert("帐号不能为空!");
        $("#Account").focus();
        return false;
    }
    if ($.trim($("#Password").val()).length == 0) {
        alert("密码不能为空!");
        $("#Password").focus();
        return false;
    }
    if ($.trim($("#Password").val()).length<6) {
        alert("密码长度不能小于6位,请输入大于六位的密码!");
        $("#Password").focus();
        return false;
    }
    if ($.trim($("#Identity").val()).length == 0) {
        alert("手机号不能为空!");
        $("#Identity").focus();
        return false;
    }
//    alert($.trim($("#Identity").val()));
//    alert(!isPhoneNumber($.trim($("#Identity").val())));
    if (!isPhoneNumber($.trim($("#Identity").val()))) {
        alert("输入的手机格式不正确，请正确输入！");
        return false;
    }
    if ($("#UserName") && $.trim($("#UserName").val()).length == 0) {
        alert("姓名不能为空!");
        $("#UserName").focus();
        return false;
    }
    var list = $('input:radio[name="sex"]:checked').val();
    $.ajax({
        type: "post",
        dataType: "json",
        contentType: "application/json", //注意：WebMethod()必须加这项，否则客户端数据不会传到服务端
        data: "{ 'Account': '" + $.trim($("#Account").val()) + "','Password':'" + $.trim($("#Password").val()) + "','Name':'" + $.trim($("#UserName").val()) + "','Sex':'" + list + "','Identity':'" + $.trim($("#Identity").val()) + "'}",//注意：data参数可以是string个int类型
        url: "Register.aspx/AddRegister",//模拟web服务，提交到方法
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
                alert("注册成功！");
//                var account = $("#Account").val();
                window.location = "Login.aspx";
            }
            else{
                alert("注册失败，该账号已存在，请更换账号！！");
            }
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