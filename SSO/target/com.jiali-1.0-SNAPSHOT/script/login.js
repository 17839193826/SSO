function login() {
    var fm=$("#fm1")
    $.ajax({
        url:fm.attr("action"),
        method:fm.attr("method"),
        data:fm.serialize(),
        success:function (obj) {
            if(obj.code==0){
                console.log("sso index")
                location.href="/index.html";
            }else{
                alert("登录失败");
            }
        }
    })
}

$(function () {
    $.get("http://localhost:8080/usercheck.do",function (obj) {
        if(obj.code==0){
            //已登录
            location.href="index.html"
        }
    })
})


