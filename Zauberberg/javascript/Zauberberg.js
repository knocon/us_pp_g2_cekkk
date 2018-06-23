/**
 * Schnittstellendefinition für die Kommunikation zwischen Java Server und Javascript Client:
 * Alle Eventnamen außer den Vorgegebenen!
 *
 * Java -> JavaScript:
 *      UPDATEKARTEN: JSON Array mit den Karten, die der entsprechende Spieler auf der Hand hat (String Zahlenwerte und "Joker" für Joker)
 *      UPDATESPIELFELD: todo muss hier noch definiert werden
 *      UPDATESPIELZUSTAND: JSON Map<String, Map<String, String>>
 *         {"Rot": {
 *                  "Aktiv": "1",      // Flag ist 0 oder 1 und kennzeichnet, ob diese Farbe mitspielt
 *                  "Fokus": "0",      // Flag ist 0 oder 1 und kennzeichnet, ob diese Farbe gerade an der Reihe ist
 *                  "Steine: "2",      // Flag ist 0, 1 oder 2 und hält die Anzahl der Steine, die der Spieler besitzt
 *                  },
 *          "Grün": {siehe obige Definition},
 *          "Gelb": {siehe obige Definition},
 *          "Blau": {siehe obige Definition},
 *          "Grau": {siehe obige Definition}}
 *
 *
 *
 * JavaScript -> Java: (Immer eine Map<String, String> als JSON; "Eventname" ist immer der Key zum Eventnamen. Andere Keys beinhalten Daten.)
 *      KARTENLEGEN Bsp:
 *         {"Eventname": "KARTENLEGEN",
 *          "karte1Typ": "Normal",
 *          "karte2Typ": "Joker",
 *          "karte3Typ": "Null",
 *          "karte1Wert": "2",
 *          "karte2Wert": "5",
 *          "karte3Wert": "Null"}
 *
 *      KARTENTAUSCHEN Bsp:
 *          {"Eventname": "KARTENTAUSCHEN",
 *           "karte1": "1",
 *           "karte2": "2",
 *           "karte3": "Joker",
 *           "karte4": "Null",
 *           "karte5": "Null"}
 *
 */


// TicTacToe Beispiel
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
 * Generell
 */
function errorMsg(msg) {
    //todo create Error Message
}

/**
 * Spielfeld
 */


/**
 *  Aktionsfläche Funktionalität
 */
addListener('UPDATESPIELZUSTAND', function (event) {
    var stringFromServer = event.data;
    var data = JSON.parse(stringFromServer);
    //var kartencontainer = document.getElementById("karten-Hand");
    for (var colour in data) {
        if (data[colour]["Aktiv"] === "1") {
            document.getElementById("Spieler" + colour).style.visibility = "visible";
            if (data[colour]["Fokus"] === "1") {
                document.getElementById("Spieler" + colour).children[0].style.textDecoration = "underline solid white";
            } else {
                document.getElementById("Spieler" + colour).children[0].style.textDecoration = "";
            }
            if (data[colour]["Steine"] === "2") {
                document.getElementById(colour + "Stein1").style.visibility = "visible";
                document.getElementById(colour + "Stein2").style.visibility = "visible";
            } else if (data[colour]["Steine"] === "1") {
                document.getElementById(colour + "Stein1").style.visibility = "visible";
                document.getElementById(colour + "Stein2").style.visibility = "hidden";
            } else {
                document.getElementById(colour + "Stein1").style.visibility = "hidden";
                document.getElementById(colour + "Stein2").style.visibility = "hidden";
            }
        } else {
            document.getElementById("Spieler" + colour).style.visibility = "hidden";
        }
    }
});

addListener('UPDATEKARTEN', function (event) {
    var stringFromServer = event.data;
    var arr = JSON.parse(stringFromServer);
    document.getElementById("karten-Legen").innerHTML = "";
    document.getElementById("karten-Tauschen").innerHTML = "";
    var kartencontainer = document.getElementById("karten-Hand");
    kartencontainer.innerHTML = "";
    arr.forEach(function (zahl) { //todo an neuere html anpassen
        kartencontainer.append("<img src='/Zauberberg/images/Zahlenkarte" + zahl + ".png' class='karte' alt='" + zahl + "'/>");// Der Zahlenwert wird im "alt" gespeichert
    });
});