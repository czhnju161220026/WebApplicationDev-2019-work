function verify() {
    var name = document.getElementById("name").value;
    var mail = document.getElementById("mail").value;
    var pwd1 = document.getElementById("pwd1").value;
    var pwd2 = document.getElementById("pwd2").value;
    //alert(name+", "+mail+", " +pwd1+", "+pwd2);

    if (name == null || name == "") {
        document.getElementById("message").innerText = "Name mustn't be empty!";
        document.getElementById("window").classList.remove("hide");
        document.getElementById("background").classList.add("blur");
        return false;
    }

    //实际对电子邮箱的验证要用到正则匹配。
    if (mail == null || mail == "") {
        document.getElementById("message").innerText = "E-mail mustn't be empty!";
        document.getElementById("window").classList.remove("hide");
        document.getElementById("background").classList.add("blur");
        return false;
    }

    if (pwd1 == null || pwd1 == "" || pwd1 != pwd2) {
        document.getElementById("message").innerText = "Make sure the password are the same."
        document.getElementById("window").classList.remove("hide");
        document.getElementById("background").classList.add("blur");
        return false;
    }

    var Regx = /^[A-Za-z0-9]*$/;
    if (!Regx.test(name)) {
        document.getElementById("message").innerText = "Name can only contains numbers and letters.";
        document.getElementById("window").classList.remove("hide");
        document.getElementById("background").classList.add("blur");
        return false;
    }

    if (pwd1.length < 6) {
        document.getElementById("message").innerText = "The length of the password mustn't be less than 6."
        document.getElementById("window").classList.remove("hide");
        document.getElementById("background").classList.add("blur");
        return false;
    }

    Regx = /^[A-Za-z0-9_-]+@[A-Za-z0-9_-]+(\.[A-Za-z0-9_-]+)+$/;
    if (!Regx.test(mail)) {
        document.getElementById("message").innerText = "Please check your E-mail.";
        document.getElementById("window").classList.remove("hide");
        document.getElementById("background").classList.add("blur");
        return false;
    }

    return true;
}

function retype() {
    document.getElementById("window").classList.add("hide");
    document.getElementById("background").classList.remove("blur");
}