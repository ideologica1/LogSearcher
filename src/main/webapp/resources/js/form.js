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

}

function switchColor(hex) {
    sessionStorage.setItem("color", hex);
    var body = document.getElementsByTagName("BODY")[0];
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

}

function hexToRGB(hex) {
    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
    return result ? {
        r: parseInt(result[1], 16),
        g: parseInt(result[2], 16),
        b: parseInt(result[3], 16)
    } : null;
}

jQuery(document).ready(function($) {
    $("#search-form").submit(function(event) {

        // Prevent the form from submitting via the browser.
        event.preventDefault();
        searchViaAjax();

    });
});

function searchViaAjax() {
    var search = {}
    search["regex"] = $("#regex").val();


    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:7001/Spring/form/results', true);
    xhr.send('hello');

    if (xhr.status != 200) {
        alert( xhr.status + ': ' + 'gjitk yf[eq' );
    } else {
        alert( xhr.responseText );
    }
}

window.onload = function () {
    var color = sessionStorage.getItem("color");
    if (color != null) {
        switchColor(color);
    }


};