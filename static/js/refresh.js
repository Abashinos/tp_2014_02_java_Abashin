function refresher(seconds) {
var secondsLeft = seconds;

    setInterval(function() {
        if (secondsLeft < 4) {
            $("#doge").attr('src',"/img/sad_doge.jpg");
            if (secondsLeft < 3) {
                $("#refresh").text("Refreshing now");
            }
        }
        --secondsLeft;
    }, 1000);

    setTimeout(function() {
        location.reload();
    }, seconds * 1000);
}

jQuery(document).ready(function($) {
    $("#refresh").text("Nothing yet");
    refresher(7);
});