function judge() {
    document.getElementById("window").classList.remove("hide");
    document.getElementById("background").classList.add("blur");
    return false;
}

function retype() {
    document.getElementById("window").classList.add("hide");
    document.getElementById("background").classList.remove("blur");
}