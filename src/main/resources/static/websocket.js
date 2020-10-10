var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/log', function (logMessage) {
            console.log(logMessage.body);
            showLogMessage(logMessage.body);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function showLogMessage(message) {
    const logHtmlElement = document.getElementById("logs");

    logHtmlElement.value += createUserFriendlyLogMessage(message)
    logHtmlElement.scrollTop = logHtmlElement.scrollHeight;
}

function createUserFriendlyLogMessage(message) {
    const jsonMessage = JSON.parse(message);

    const date = new Date(jsonMessage.dateTime);
    const username = jsonMessage.username;
    const logMessage = jsonMessage.message;

    return date.toLocaleString()
        + " [" + username + "]"
        + ": " + logMessage + "\n";
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
});
