$(document).ready(() => {
    // $('#homeScorePlus').click(e => {
    //     $.ajax({
    //         type: "POST",
    //         url: "/hockey/plus",
    //         success: result => {
    //             $('#homeScore').text(result);
    //         }, error: message => {
    //             alert(message);
    //         }
    //     });
    // });

    // $("#startStop").click(val => {
    //     $.ajax({
    //         type: "POST",
    //         url: "/hockey/start",
    //         data: {id: 2},
    //         success: result => {
    //             $("#startStop").text(result)
    //         },
    //         error: message => {
    //             alert(message)
    //         }
    //     });
    // });

    // function seconds() {
    //     $.ajax({
    //         type: "GET",
    //         url: "/hockey/seconds",
    //         success: result => {
    //             $("#seconds").text(String(result).padStart(2, '0'))
    //         }
    //     });
    // }

    // function time() {
    //     $.ajax({
    //         type: "GET",
    //         url: "/hockey/time",
    //         data: {id: 2},
    //         success: result => {
    //             $("#homeScore").text(result[0])
    //             $("#time").text(String(~~(result[1] / 60)).padStart(2, '0') + ':' + String(result[1] % 60).padStart(2, '0'))
    //             $("#awayScore").text(result[2])
    //             $("#period").text(result[3])
    //         }
    //     });
    // }
    //
    // setInterval(time,1000);
});
