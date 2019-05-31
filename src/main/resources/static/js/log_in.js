function judge() {
    // TODO: 把输入传给后端进行判断, 如果错误, 显示隐藏的控件
    document.getElementById("window").classList.remove("hide");
    document.getElementById("background").classList.add("blur");
    return false;
}

function retype() {
    document.getElementById("window").classList.add("hide");
    document.getElementById("background").classList.remove("blur");
}