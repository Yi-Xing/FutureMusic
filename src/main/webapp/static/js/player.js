function tanK_bg() {
    var tanK = document.getElementById('tanK');
    var tanK_bg = document.getElementById('tanK_bg');
    tanK_bg.style.display = 'inline-block';
    document.onclick = function () {
        var e = e || window.event;
        var target = e.target || e.srcElement;
        if (target.id == tanK_bg_con) {
            return false;
        } else {
            tanK.style.display = 'none';
        }
    };
}