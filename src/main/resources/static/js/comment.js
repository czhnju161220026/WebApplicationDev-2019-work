function judgeComment() {
    var content = document.getElementById("content").value;
    var Regx = /\S/;
    if(!Regx.test(content)) {
        alert("请输入评论内容！");
        return false;
    }
    return true;
}