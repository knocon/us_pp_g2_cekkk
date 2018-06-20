/**
 * Schnittstellendefinition für die Kommunikation zwischen Java Server und Javascript Client:
 * Alle Eventnamen außer den Vorgegebenen!
 *
 * Java -> JavaScript:
 *      UPDATEKARTEN: Array mit den Karten, die der entsprechende Spieler auf der Hand hat (Integer Array mit den Zahlenwerten)
 *      UPDATESPIELFELD: todo muss hier noch definiert werden
 *
 * JavaScript -> Java:
 *      KARTENLEGEN: Array mit den Zahlenwerten(Integer) der gelegten Karten
 *      KARTENTAUSCHEN: Array mit den Zahlenwerten(Integer) der Karten, die getauscht werden sollen.
 *
 */



addListener('standardEvent', function (event) {
    var stringFromServer = event.data;
    var arr = stringFromServer.split(',');
    //console.log(arr);

    if (arr.length == 11) {
        for (var i = 0; i < 9; i++) {
            arrFields[i] = +arr[i];
        }
        playerMessage = arr[9];
        var str = arr[10];
        if (str == "HOST") {
            console.log(arr[10]);
            setVisible();
        }


        sentFields = [0, 0, 0, 0, 0, 0, 0, 0, 0];
        document.getElementById("Player").innerHTML = playerMessage;
        redraw();
    }
    statusWait = false;
});
addListener('START', function (event) {
    var stringFromServer = event.data;
    var arr = stringFromServer.split(',');
    playerMessage = arr[9];
    document.getElementById("Player").innerHTML = playerMessage;
    if (arr[10] == "HOST") setVisible();
    statusWait = false;
});
addListener('PLAYERLEFT', function (event) {
    var stringFromServer = event.data;
    playerMessage = stringFromServer;
    document.getElementById("Player").innerHTML = playerMessage;
});
addListener('CLOSE', function (event) {
    document.getElementById("Player").innerHTML = "Spiel wurde vom Host beendet!";
});

playerMessage = "";
var emptyImg = "/Zauberberg/images/empty.png";
var xImg = "/Zauberberg/images/x.jpg";
var oImg = "/Zauberberg/images/o2.png";
var statusWait = true;

var arrFields = [0, 0, 0, 0, 0, 0, 0, 0, 0];
var sentFields = [0, 0, 0, 0, 0, 0, 0, 0, 0];
var currentPlayer = 0;

function setField(x) {
    sentFields[x] = 1;
}

function getImg(x) {
    if (x == 1) return xImg;
    else if (x == 2) return oImg;
    return emptyImg
}

function initFields() {
    var parent = document.getElementById("grid");
    for (var i = 0; i < 9; i++) {
        var img = document.createElement("img");
        img.id = "img" + i;
        img.src = emptyImg;
        img.width = "148";
        img.height = "153";
        img.style.position = "absolute";
        img.style.top = (Math.floor((i / 3)) * 172 + 11) + "px";
        img.style.left = ((i % 3) * 170 + 11) + "px";
        img.onclick = function () {
            if (arrFields[this.id[3]] == 0 && statusWait == false) {
                setField(this.id[3]);
                updateGameState();
            }
        };
        parent.appendChild(img);
    }
}

window.onload = initFields;

function updateGameState() {
    statusWait = true;
    sendDataToServer(sentFields);
}

function redraw() {
    for (var i = 0; i < 9; i++) {
        var img = document.getElementById('img' + i);
        img.src = getImg(arrFields[i]);
    }
}

function restart() {
    statusWait = true;
    sendDataToServer("RESTART");
}

function setVisible() {
    document.getElementById("restartButton").style.visibility = "visible";
    document.getElementById("closeButton").style.visibility = "visible";
}

function closeGame() {
    sendDataToServer("CLOSE");
}


/**
 *  Aktionsfläche Funktionalität
 */
addListener('UPDATEKARTEN', function (event) {
    var stringFromServer = event.data;
    var arr = JSON.parse(stringFromServer);
    var kartencontainer = document.getElementById("karten");
    kartencontainer.innerHTML = "";
    var kartenHTML = "";
    arr.forEach(function (zahl) {
        kartenHTML += "<img src='/Zauberberg/images/Zahlenkarte" + zahl + ".png' class='karte'/>"
    });
    kartencontainer.innerHTML = kartenHTML;
});