function verify() {
    //目前对输入的验证仅验证输入非空
    //返回false代表验证失败，将不会触发action
    let name = document.getElementById("name").value;
    let mail = document.getElementById("mail").value;
    let pwd1 = document.getElementById("pwd1").value;
    let pwd2 = document.getElementById("pwd2").value;
    //alert(name+", "+mail+", " +pwd1+", "+pwd2);

    //目前只用alert提示非空
    //考虑使用js控制css或者html，直接显示在页面上
    if(name == null || name=="") {
        alert("姓名不能为空！");
        return false;
    }

    //实际对电子邮箱的验证要用到正则匹配。
    if(mail == null || mail == "") {
        alert("电子邮件不能为空!");
        return false;
    }

    if(pwd1 == null || pwd1 == "" || pwd1 != pwd2) {
        alert("密码输入有误，请检查");
        return false;
    }

    return true;
}