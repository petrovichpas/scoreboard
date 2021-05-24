$(document).ready(() => {
    // $("#plo").click(e => {
    //     $.ajax({
    //         type: "POST",
    //         url: "/hockey/plus",
    //         success: result => {
    //             $("#homeScore").text(result)
    //         },
    //         error: message => {
    //             alert(message)
    //         }
    //     });
    // });

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

    // function seconds() {
    //     $.ajax({
    //         type: "GET",
    //         url: "/hockey/seconds",
    //         success: result => {
    //             $("#seconds").text(String(result).padStart(2, '0'))
    //         }
    //     });
    // }
    //
    // function minutes() {
    //     $.ajax({
    //         type: "GET",
    //         url: "/hockey/minutes",
    //         success: result => {
    //             $("#minutes").text(String(result).padStart(2, '0'))
    //         }
    //     });
    // }

    function time() {
        $.ajax({
            type: "GET",
            url: "/hockey/time",
            success: result => {
                $("#homeScore").text(result[0])
                $("#time").text(String(result[1]).padStart(2, '0') +':' + String(result[2]).padStart(2, '0'))
                $("#awayScore").text(result[3])
            }
        });
    }

    // setInterval(seconds,1000);
    // setInterval(minutes,1000);
    setInterval(time,1000);
});
