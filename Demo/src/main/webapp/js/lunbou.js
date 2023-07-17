var count = $(".banner .pic").length;
for (var i = 0; i < count - 1; i++) {
    //生成小圆点
    $("<span class='circle'></span>").appendTo(".yuan")
}
var index = 0;
$(".right").click(function () {
    //向右轮播
    index++;//改变index值得目的是 通过不同得index得值显示不同的图片
    //console.log(index)
    if (index > $(".pic").length - 1) {   //如果index得值达到最大得值  那么他从第一张图片开始轮播
        index = 0;
    }
    //$("pic") 找到所有的图片元素
    //$("pic").eq(index) 找到class为pic元素中下标索引值为index的元素
    //$("pic")。eq(index).fadeIn() 找到class为pic元素中下标索引值为index，让他淡淡出现
    $(".pic").eq(index).fadeIn().siblings().fadeOut()/*找到class为pic元素中下标索引值为index得元素*/
})
$(".left").click(function () {
    index--;
    if (index < 0) {   //如果index的值达到最小的值
        index = $(".pic").length - 1;
    }
    $(".pic").eq(index).fadeIn().siblings().fadeOut()
})




setInterval(function () {
    index--;
    if (index < 0) {
        index = $(".banner .pic").length - 1;
    }
    $(".banner .pic").eq(index).stop().fadeIn().siblings().stop().fadeOut()
    $(".yuan .circle").eq(index).css("opacity", 1).siblings().css("opacity", 0.3)
}, 2000)
$(".banner").mouseenter(function () {

})
//鼠标移出
$(".banner").mouseenter(function () {
    //alert(2)
})
$("banner").hover(function () {
    clearInterval(timeId)
}, function () {
    timeId = setInterval(function () {
        index++;
        if (index > $(".pic").length - 1) {
            index = 0;
        }
        $(".pic").eq(index).fadeIn().siblings().fadeOut()
    }, 1000)
}
)