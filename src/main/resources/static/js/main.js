// $(document).ready(() => {
// });

const id = document.getElementById("id").value;
document.getElementById("startStop").addEventListener('click', startStop);
document.getElementById("countdownMode").addEventListener('click', changeMode);
document.getElementById("reset").addEventListener('click', reset);
document.getElementById("swap").addEventListener('click', swap);

function startStop(){
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
        url: "/hockey/plus_minus",
        data: {action: param, id: id},
        success: result => {
            const tokens = result.split(":");
            if (tokens[0] == "time")
                $("#time").text(String(~~(tokens[1] / 60)).padStart(2, "0") + ':' + String(~~(tokens[1] % 60)).padStart(2, "0"));
            else
                $("#"+tokens[0]).text(tokens[1]);
        }
    });
};

function changeMode(){
    $.ajax({
        type: "POST",
        url: "/hockey/mode",
        data: {id: id},
    });
};

function changeInput(val, name){
    $.ajax({
        type: "POST",
        url: "/hockey/change_input",
        data: {value: val, name: name, id: id},
    });
};

function reset(){
    $.ajax({
        type: "POST",
        url: "/hockey/reset",
        data: {id: id},
    });
};

function swap(){
    $.ajax({
        type: "POST",
        url: "/hockey/swap",
        data: {id: id},
        success: result => {
            $("#homeName").val(result.homeName)
            $("#awayName").val(result.awayName)
        }
    });
};

function getBoard() {
    $.ajax({
        type: "GET",
        url: "/hockey/get_board",
        data: {id: id},
        success: result => {
            $("#homeName").val(result.homeName)
            $("#awayName").val(result.awayName)
            $("#homeScore").text(result.homeScore)
            $("#time").text(String(~~(result.currentTime / 60)).padStart(2, '0') + ':' + String(result.currentTime % 60).padStart(2, '0'))
            $("#awayScore").text(result.awayScore)
            if (result.period > 0) $("#period").text(result.period)
            else $("#period").text('OT');
        }
    });
}

setInterval(getBoard,1000);
