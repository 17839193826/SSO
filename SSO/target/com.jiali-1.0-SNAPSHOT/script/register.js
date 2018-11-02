$(function () {
    // var json=getData();
    // if(json.length>0){
    //     var js=decodeURI(json);
    //     var obj=JSON.parse(js);
    //     console.log(obj);
    //     //令牌有效
    //     app1.username=obj.username;
    //     app1.phone=obj.phone;
    //
    // }else{
    var token=getCK("userauth");
    if(token.length==0){
        //没有令牌
        location.href="login.html";
    }else{
        $.get("http://localhost:8080/usercheck.do",null,function (obj) {
            if(obj.code==0){
                //令牌有效
                app1.username=obj.username;
                app1.phone=obj.phone;
            }
        })
    }
    // }

})
//获取Cookie
function getCK(name) {
    var cks1=document.cookie;
    var arrc1=cks1.split(";");
    for(i=0;i<arrc1.length;i++){
        var ck1=arrc1[i];
        var arrc2=ck1.split("=");
        if(arrc2[0]==name){
            return arrc2[1];
        }
    }
    return "";
}

//获取传递的参数信息
function getData() {
    var url=window.location.search;
    index=url.indexOf("?");
    if(index>-1){
        var str = url.substr(index+1);
        if(str.indexOf('=')){
            return str.split('=')[1];
        }
    }
    return "";
}
</script>