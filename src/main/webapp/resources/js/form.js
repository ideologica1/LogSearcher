function addDateInterval() {

    var intervalRows = document.getElementsByClassName("date-interval");
    var firstRow = intervalRows.item(0);
    var lastRow = intervalRows.item(intervalRows.length - 1);
    var newRow = firstRow.cloneNode(true);

    var InputType = newRow.getElementsByTagName("input");

    for (var i = 0; i < InputType.length; i++) {
        InputType[i].value = '';
    }

    newRow.getElementsByClassName("buttons")[0].removeChild(newRow.getElementsByClassName("buttons")[0].getElementsByClassName("add-interval")[0]);
    lastRow.parentNode.insertBefore(newRow, lastRow.nextSibling);
    $(".interval-field").mask("99-99-9999 99:99:99");
}

function removeDateInterval(button) {

    var currentIntervalRow = button.parentNode.parentNode;

    var intervalRows = document.getElementsByClassName("date-interval");


    if (currentIntervalRow.isSameNode(intervalRows.item(0))) {
        var InputType = currentIntervalRow.getElementsByTagName("input");

        for (var i = 0; i < InputType.length; i++) {
            InputType[i].value = '';
        }
    }
    else {
        currentIntervalRow.parentNode.removeChild(currentIntervalRow);
    }


}

function clearAllFields() {

    var elements = document.getElementsByTagName("input");
    for (var ii = 0; ii < elements.length; ii++) {
        if (elements[ii].type != 'submit') {
            elements[ii].value = "";
        }
    }
    $('.date-interval').not(':first').remove();

}

function switchColor(hex) {
    sessionStorage.setItem("color", hex);
    var body = document.getElementsByTagName("BODY")[0];
    var allowedColors = ["#b4ffff", "#b4f0f0", "#b4ffc8", "#c5dbbf", "#ffedcc", "#fae6c8", "#ccb7cc", "#b4b4ff", "#b4c8ff", "#b8b6bd", "#f2f0c8", "#f0fadc", "#f2f0b4", "#e9fab4"];
    if (allowedColors.includes(hex)) {
        body.style.backgroundColor = hex;
        var inputField = document.getElementsByTagName("input");
        var backgroundColor = hexToRGB(hex);
        var red = backgroundColor['r'] * 1.15 < 255 ? backgroundColor['r'] * 1.15 : 255;
        var green = backgroundColor['g'] * 1.15 < 255 ? backgroundColor['g'] * 1.15 : 255;
        var blue = backgroundColor['b'] * 1.15 < 255 ? backgroundColor['b'] * 1.15 : 255;
        var fieldColor = 'rgb(' + Math.round(red) + ',' + Math.round(green) + ',' + Math.round(blue) + ')';
        for (var i = 0; i < inputField.length; i++) {
            inputField[i].style.backgroundColor = fieldColor;
        }

        $.ajax({
            url: 'http://localhost:7001/Spring/form/switch', // url where to submit the request
            type: "POST", // type of action POST || GET
            dataType: 'json', // data type
            data: hex, // post data || get data
            success: function (data) {
                alert('ok');
            },
            error: function (xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        });
    }

    else {
        alert('Выбранный цвет недоступен, он будет установлен случайным образом');
        var random = Math.round(Math.random() * 14);
        var randomColor = allowedColors[random];
        switchColor(randomColor);
    }

}

function hexToRGB(hex) {
    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
    return result ? {
        r: parseInt(result[1], 16),
        g: parseInt(result[2], 16),
        b: parseInt(result[3], 16)
    } : null;
}


function searchViaAjax() {
        $.ajax({
            url: 'http://localhost:7001/Spring/form/create', // url where to submit the request
            type: "POST", // type of action POST || GET
            dataType: 'json', // data type
            data: $("#search-form").serialize(), // post data || get data
            success: function (data) {
                // you can see the result from the console
                // tab of the developer tools
                console.log(data);
                $('#modalResponse').html(data.response);
                $('#myModal').modal('show');
            },
            error: function (xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        });
}

window.onload = function () {
    var color = sessionStorage.getItem("color");
    if (color != null) {
        switchColor(color);
    }


};