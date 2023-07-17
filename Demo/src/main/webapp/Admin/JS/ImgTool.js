//图片路径获取方法 ImgTool.js
var imgTool = {};
var _imgTool = [];
if (window.location.host.match(/^((localhost:)|(LOCALHOST:))/) || /^(http:\/\/)?192\.\d{1,3}\.\d{1,3}\.\d{1,3}(:\d{1,5})?$/.test(window.location.host)) {
    _imgTool["FTPUri"] = "http://192.168.2.249/";
    _imgTool["HandlerImgUri"] = "http://192.168.2.247:82/ImgHandler";
    _imgTool["ImgShowUri"] = "http://192.168.2.247:802/"; // "http://218.5.27.195:9018/"
    _imgTool["ImgShowYTUri"] = "http://192.168.2.249:90/"; 
    _imgTool["ImgShowIPUri"] = "http://192.168.2.249:80/";
    _imgTool["ImgDownUri"] = "http://192.168.2.247:82/ImgHandler/Down";
    _imgTool["ImgDirectDownUri"] = "http://192.168.2.247:832/Down/";
    _imgTool["ImgBatchDownUri"] = "http://192.168.2.247:82/DownLoadImg/Rar";
    _imgTool["ImgUpUri"] = "http://192.168.2.249:802/Img/UP";//,http://192.168.2.249:802/Img/UP
    _imgTool["ImgWebUpUri"] = "http://192.168.2.249:802/Img/WebUp";
    //_imgTool["ImgUpUri"] = "http://localhost:55254/Img/UP";
    //_imgTool["ImgWebUpUri"] = "http://localhost:55254/Img/WebUp";
    _imgTool["DealImg"] = "http://192.168.2.247:82/DealImg";

    _imgTool["FileUpUri"] = "http://192.168.2.249:802/File/UP";
    _imgTool["FileWebUpUri"] = "http://192.168.2.249:802/File/WebUp";//"http://192.168.2.249:802/File/WebUp";
    _imgTool["FileDownUri"] = "http://192.168.2.249:802/Down";

    _imgTool["VideoUpUri"] = "http://192.168.2.247:812/Show/";
    _imgTool["VideoShowUri"] = "http://192.168.2.247:812/Show/";
    _imgTool["VideoDownUri"] = "http://192.168.2.247:822/Down/";
    _imgTool["ImgJyxt"] = "http://172.16.5.230:8080/FileUploadService/";

    _imgTool["VrShowUri"] = "http://172.16.14.225:8082/web/index.html";
    _imgTool["VrCityID"] = "FZ";
    _imgTool["VrType"]= ["online", "intranet"];
    _imgTool["VrResourceDomain"]= "//172.16.14.225:8082/web/FZVR/";
    _imgTool["VrQrCodeUri"] = "http://192.168.2.33:101/VrQrCodeImages";

} else if (window.location.host.match(/^(www.lovemt)/)) {
    _imgTool["FTPUri"] = "http://192.168.2.215:805/";
    _imgTool["HandlerImgUri"] = "http://192.168.2.215:802/ImgHandler";
    _imgTool["ImgShowUri"] = "http://192.168.2.215:803/";
    _imgTool["ImgShowYTUri"] = "http://192.168.2.215:805/";
    _imgTool["ImgShowIPUri"] = "http://192.168.2.215:805/";
    _imgTool["ImgDownUri"] = "http://192.168.2.215:807/ImgHandler/Down";
    _imgTool["ImgDirectDownUri"] = "http://192.168.2.215:804/Down/";
    _imgTool["ImgBatchDownUri"] = "http://192.168.2.215:807/DownLoadImg/Rar";
    _imgTool["ImgUpUri"] = "http://192.168.2.215:802/Img/UP";
    _imgTool["ImgWebUpUri"] = "http://192.168.2.215:802/Img/WebUp";
    _imgTool["DealImg"] = "http://192.168.2.215:807/DealImg";

    _imgTool["FileUpUri"] = "http://192.168.2.215:802/File/UP";
    _imgTool["FileWebUpUri"] = "http://192.168.2.215:802/File/WebUp";
    _imgTool["FileDownUri"] = "http://192.168.2.215:802/Down";

    _imgTool["VideoShowUri"] = "http://192.168.2.215:808/Show/";
    _imgTool["VideoDownUri"] = "http://192.168.2.215:809/Down/";
    _imgTool["ImgJyxt"] = "http://172.16.5.230:8080/FileUploadService/";

    _imgTool["VrShowUri"] = "http://172.16.14.225:8082/web/index.html";
    _imgTool["VrCityID"] = "FZ";
    _imgTool["VrType"]= ["online", "intranet"];
    _imgTool["VrResourceDomain"] = "//172.16.14.225:8082/web/FZVR/";
    _imgTool["VrQrCodeUri"] = "http://192.168.2.33:101/VrQrCodeImages";
} else {
    _imgTool["FTPUri"] = "http://Fzmtpic.maitian.cn/";
    _imgTool["HandlerImgUri"] = "http://fzpicapi.maitian.cn/ImgHandler";
    _imgTool["ImgShowUri"] = "http://fzpic.maitian.cn/";
    _imgTool["ImgShowYTUri"] = "http://Fzmtpic.maitian.cn/";
    _imgTool["ImgShowIPUri"] = "http://Fzmtpic.maitian.cn/";
    _imgTool["ImgDownUri"] = "http://fzpicapi.maitian.cn/ImgHandler/Down";
    _imgTool["ImgDirectDownUri"] = "http://fzpic.maitian.cn:60072/Down/";
    _imgTool["ImgBatchDownUri"] = "http://fzpicapi.maitian.cn/DownLoadImg/Rar";
    _imgTool["ImgUpUri"] = "http://getfzpic.maitian.cn/Img/UP";
    _imgTool["ImgWebUpUri"] = "http://getfzpic.maitian.cn/Img/WebUP";
    _imgTool["DealImg"] = "http://fzpicapi.maitian.cn/DealImg";

    _imgTool["FileUpUri"] = "http://getfzpic.maitian.cn/File/UP";
    _imgTool["FileWebUpUri"] = "http://getfzpic.maitian.cn/File/WebUp";
    _imgTool["FileDownUri"] = "http://getfzpic.maitian.cn/Down";
    _imgTool["ImgJyxt"] = "http://fztradepic.maitian.cn/FileUploadService/";
    _imgTool["VideoShowUri"] = "http://video.maitian.cn/Show/";
    _imgTool["VideoDownUri"] = "http://videodownload.maitian.cn/Down/";


    _imgTool["VrShowUri"] = "http://vrmt.maitian.cn/web/index.html";
    _imgTool["VrCityID"] = "FZ";
    _imgTool["VrType"] = ["online", "intranet"];
    _imgTool["VrResourceDomain"] = "//vrmt.maitian.cn/web/vrresource/FZVR/";
    _imgTool["VrQrCodeUri"] = "http://fztradeapi.maitian.cn/QRcode/VrQrCodeImages";
}

imgTool.Uri = function (key) {
    return _imgTool[key];
}

//图片-查看/下载 imgPath:图片路径; imgInfo:图片类型;imgType:图片归类; isD:默认展示 1下载; wT:0彩色水印 1白色水印 2浅色水印(户型图) 3斜白水印 -1无水印 -2无水印且裁图不压缩 json:1 批量获取时返回  
//showType FTP：FTP服务器 FTPHandle：FTP服务器,通过接口打水印 FTPYT:FTP服务器原图保存  Local:本地
imgTool.GetImgUrl = function (o) {
    if (!o && !o.imgPath) { return ""; }
    switch (o.showType) {
        case "FTPHandle": {
            if (!o.isD) { o.isD = 0 } if (o.wT == -1 || o.wT == null || o.wT == undefined) { o.wT = 1; }
            if (o.path == null || o.path == undefined) { o.path = ""; }
            return imgTool.Uri("HandlerImgUri") + "?wT=" + o.wT + "&isD=" + o.isD + "&Img=" + imgTool.Uri("FTPUri") + o.path + o.imgPath;
        } break;
        case "FTPYT": {
            var uri = (o.isSmall ? imgTool.Uri("ImgShowUri") : imgTool.Uri("ImgShowYTUri"));
            if (!o.isD) { return uri + (o.isSmall ? "SmallPic/" : "") + (o.isBig ? "BigPic/" : "") + o.imgPath; }
            else { return imgTool.Uri("ImgDownUri") + "?Img=" + uri + (o.isSmall ? "SmallPic/" : "") + (o.isBig ? "BigPic/" : "") + o.imgPath; }
        } break;
        case "FTPIP": {
            var uri = (o.isSmall ? imgTool.Uri("ImgShowUri") : imgTool.Uri("ImgShowIPUri"));
            if (!o.isD) { return uri + (o.isSmall ? "SmallPic/" : "") + (o.isBig ? "BigPic/" : "") + o.imgPath; }
            else { return imgTool.Uri("ImgDownUri") + "?Img=" + uri + (o.isSmall ? "SmallPic/" : "") + (o.isBig ? "BigPic/" : "") + o.imgPath; }
        } break;
        case "ImgJyxt": {
            var uri = imgTool.Uri("ImgJyxt");
            if (!o.isD) { return uri + o.imgPath; }
            else { return imgTool.Uri("ImgDownUri") + "?Img=" + uri + o.imgPath; }
        } break;
        case "FTP":
        default: {
            if (!o.isD) { return imgTool.Uri("ImgShowUri") + (o.isSmall ? "SmallPic/" : "") + (o.isBig ? "BigPic/" : "") + o.imgPath; }
            else { return imgTool.Uri("ImgDirectDownUri") + (o.isSmall ? "SmallPic/" : "") + (o.isBig ? "BigPic/" : "") + o.imgPath; }
        } break;
    }
    return "";
}

function GetHandlerImgUri() {
    return imgTool.Uri("HandlerImgUri");
}

function GetFtpUri() {
    return imgTool.Uri("FTPUri");
}

function GetDealImgUri() {
    return imgTool.Uri("DealImg");
}

