function addDateInterval() {

    var intervalRows = document.getElementsByClassName("date-interval");
    var firstRow = intervalRows.item(0);
    var lastRow = intervalRows.item(intervalRows.length - 1);
    var newRow = firstRow.cloneNode(true);

    var InputType = newRow.getElementsByTagName("input");

        for (var i=0; i<InputType.length; i++){
           InputType[i].value=''; 
        }

    newRow.getElementsByClassName("buttons")[0].removeChild(newRow.getElementsByClassName("buttons")[0].getElementsByClassName("add-interval")[0]);

    lastRow.parentNode.insertBefore(newRow, lastRow.nextSibling);
}

function removeDateInterval(button) {

    var currentIntervalRow = button.parentNode.parentNode;

    var intervalRows = document.getElementsByClassName("date-interval");
    

    if (currentIntervalRow.isSameNode(intervalRows.item(0))) {
        var InputType = currentIntervalRow.getElementsByTagName("input");

        for (var i=0; i<InputType.length; i++){
           InputType[i].value=''; 
        }
    }
    else {
        currentIntervalRow.parentNode.removeChild(currentIntervalRow);
    }

}