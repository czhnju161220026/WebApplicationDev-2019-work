function verify() {
    //目前对输入的验证仅验证输入非空
    //返回false代表验证失败，将不会触发action
    let name = document.getElementById("name").value;
    let mail = document.getElementById("mail").value;
    let pwd1 = document.getElementById("pwd1").value;
    let pwd2 = document.getElementById("pwd2").value;
    //alert(name+", "+mail+", " +pwd1+", "+pwd2);

    if(name == null || name=="") {
        confirm("Name mustn't be empty!");
        return false;
    }

    //实际对电子邮箱的验证要用到正则匹配。
    if(mail == null || mail == "") {
        confirm("E-mail mustn't be empty!");
        return false;
    }

    if(pwd1 == null || pwd1 == "" || pwd1 != pwd2) {
        confirm("Make sure the password are the same.")
        return false;
    }

    var Regx = /^[A-Za-z0-9]*$/;
    if(!Regx.test(name)) {
        confirm("Name can only contains numbers and letters.");
        return false;
    }

    if(pwd1.length < 6) {
        confirm("The length of the password mustn't be less than 6.");
        return false;
    }

    Regx = /^[A-Za-z0-9_-]+@[A-Za-z0-9_-]+(\.[A-Za-z0-9_-]+)+$/;
    if(!Regx.test(mail)) {
        confirm("Please check your E-mail.");
        return false;
    }

    return true;
}