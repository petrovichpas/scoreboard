// $(document).ready(() => {
// });

function startStop(id){
    $.ajax({
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
        type: "POST",
        url: "/hockey/plus-minus",
        data: {action: param, id: document.getElementById('id').value},
        success: result => {
            if (param == "minutesPlus" || param == "minutesMinus" || param == "secondsPlus" || param == "secondsMinus"){
                $("#time").text(String(~~(result / 60)).padStart(2, '0') + ':' + String(~~(result % 60)).padStart(2, '0'));
            } else if (param == "homeScorePlus" || param == "homeScoreMinus"){
                $("#homeScore").text(result);
            } else if (param == "periodPlus" || param == "periodMinus"){
                $('#period').text(result);
            } else if (param == "awayScorePlus" || param == "awayScoreMinus"){
                $("#awayScore").text(result);
            }
        }
    });
};

function changeMode(){
    $.ajax({
        type: "POST",
        url: "/hockey/mode",
        data: {id: document.getElementById('id').value},
    });
};

function changeField(val, name){
    $.ajax({
        type: "POST",
        url: "/hockey/name",
        data: {value: val, name: name, id: document.getElementById('id').value},
    });
};

function reset(){
    $.ajax({
        type: "POST",
        url: "/hockey/reset",
        data: {id: document.getElementById('id').value},
        // success: result => {
        //     $("#homeScore").text(result[0])
        //     $("#time").text(String(~~(result[1] / 60)).padStart(2, '0') + ':' + String(result[1] % 60).padStart(2, '0'))
        //     $("#awayScore").text(result[2])
        //     if (result[3] > 0) $("#period").text(result[3]);
        //     else $("#period").text('OT');
        // }
    });
};

function swap(){
    $.ajax({
        type: "POST",
        url: "/hockey/swap",
        data: {id: document.getElementById('id').value},
    });
};

function time() {
    $.ajax({
        type: "GET",
        url: "/hockey/time",
        data: {id: document.getElementById('id').value},
        success: result => {
            $("#homeScore").text(result[0])
            $("#time").text(String(~~(result[1] / 60)).padStart(2, '0') + ':' + String(result[1] % 60).padStart(2, '0'))
            $("#awayScore").text(result[2])
            if (result[3] > 0) $("#period").text(result[3]);
            else $("#period").text('OT');
        }
    });
}

setInterval(time,1000);
