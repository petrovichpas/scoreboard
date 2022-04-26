// $(document).ready(() => {


const id = document.getElementById("id").value;
document.getElementById("startStop").addEventListener('click', startStop);
document.getElementById("countdownMode").addEventListener('click', changeMode);
// document.getElementById("reset").addEventListener('click', reset);
document.getElementById("swap").addEventListener('click', swap);

// $.ajaxSetup({
//     beforeSend: function(xhr) {
//         xhr.setRequestHeader('X-CSRF-TOKEN', token);
//     }
// });

function startStop(){
    $.ajax({
        beforeSend: (req) => {req.setRequestHeader('X-CSRF-TOKEN', $("meta[name='_csrf']").attr("content"));},
        type: "POST",
        url: "/hockey/start",
        data: {id: id},
        success: result => {
            $("#startStop").text(result)
        }
    });
};

function plusOrMinusOne(param){
    $.ajax({
        beforeSend: (req) => {req.setRequestHeader('X-CSRF-TOKEN', $("meta[name='_csrf']").attr("content"));},
        type: "POST",
        url: "/hockey/plus_minus",
        data: {action: param, id: id},
        success: result => {
            const tokens = result.split("-");
            // if (tokens[0] == "time")
            //     $("#currentTime").text(String(~~(tokens[1] / 60)).padStart(2, "0") + ':' + String(~~(tokens[1] % 60)).padStart(2, "0"));
            // else
                $("#"+tokens[0]).text(tokens[1]);

        }
    });
};

function changeMode(){
    $.ajax({
        beforeSend: (req) => {req.setRequestHeader('X-CSRF-TOKEN', $("meta[name='_csrf']").attr("content"));},
        type: "POST",
        url: "/hockey/mode",
        data: {id: id}
    });
};

function getBoard() {
    // if (document.getElementById("startStop").value == "Start") return;

    $.ajax({
        type: "GET",
        url: "/hockey/get_board",
        data: {id: id},
        success: result => {
            $("#currentTime").text(result.currentTime);
            // if (result.startStop == "Stop") {
            // $("#penaltyTime").val(String(~~(result.penaltyTime / 60)).padStart(2, '0') + ':' + String(result.penaltyTime % 60).padStart(2, '0'));
            $("#penaltyTime").val(result.penaltyTime);
            // if (result.currentTime > 0) {
            // $("#time").text(String(~~(result.currentTime / 60)).padStart(2, '0') + ':' + String(result.currentTime % 60).padStart(2, '0'));
            // } else $("#time").text('00:00');
            // }
            // $("#awayScore").text(result.awayScore)
            // if (result.period <= 3 ) $("#period").text(result.period)
            // else $("#period").text('OT');
        }
    });
}

function changeInput(val, name){
    $.ajax({
        beforeSend: (req) => {req.setRequestHeader('X-CSRF-TOKEN', $("meta[name='_csrf']").attr("content"));},
        type: "POST",
        url: "/hockey/change_input",
        data: {value: val, name: name, id: id},
        // success: result => {
        //     $("#penaltyTime").val(result.penaltyTime);
        // }
    });
};

// function reset(){
//     $.ajax({
//         beforeSend: (req) => {req.setRequestHeader('X-CSRF-TOKEN', $("meta[name='_csrf']").attr("content"));},
//         type: "POST",
//         url: "/hockey/reset",
//         data: {id: id},
//     });
// };

function swap(){
    $.ajax({
        beforeSend: (req) => {req.setRequestHeader('X-CSRF-TOKEN', $("meta[name='_csrf']").attr("content"));},
        type: "POST",
        url: "/hockey/swap",
        data: {id: id},
        success: result => {
            $("#homeName").val(result.homeName);
            $("#awayName").val(result.awayName);
        }
    });
};


setInterval(getBoard,1000);
// });