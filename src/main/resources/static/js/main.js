$(document).ready(() => {
    $("#plo").click(e => {
        $.ajax({
            type: "POST",
            url: "/hockey/plus",
            success: result => {
                $("#homeScore").text(result)
            },
            error: message => {
                alert(message)
            }
        });
    });

    $("#start").click(e => {
        $.ajax({
            type: "POST",
            url: "/hockey/start",
            success: result => {
                $("#seconds").text(result)
            },
            error: message => {
                alert(message)
            }
        });
    });

    function seconds() {
        $.ajax({
            type: "GET",
            url: "/hockey/seconds",
            success: result => {
                $("#seconds").text(result)
            }
        });
    }

    function minutes() {
        $.ajax({
            type: "GET",
            url: "/hockey/minutes",
            success: result => {
                $("#minutes").text(result)
            }
        });
    }

    setInterval(seconds,1000);
    setInterval(minutes,1000);
});
