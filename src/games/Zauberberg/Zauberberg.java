package games.Zauberberg;

import gameClasses.Game;
import gameClasses.GameState;
import games.Ereignisplaettchen.Fallgrube;
import games.Ereignisplaettchen.FliegendeKarte;
import games.Ereignisplaettchen.Geheimgang;
import games.Ereignisplaettchen.Kristallkugel;
import games.Ereignisplaettchen.Rabe;
import games.Ereignisplaettchen.Schreckgespenst;
import games.Ereignisplaettchen.Unwetter;
import global.FileHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


//import server.Server;
import userManagement.User;

public class Zauberberg extends Game {
    /**
     * gridStatus contains the gameData
     */
    private Spiel spiel = new Spiel(this);
    private Kobold aktuellerKobold;
    private Spieler playerTurn = null;
    private ArrayList<User> playerList = new ArrayList<User>();
    private ArrayList<Spieler> spielerList = new ArrayList<Spieler>();
    private ArrayList<User> spectatorList = new ArrayList<User>();
    ArrayList<Integer> arrayLayerFeld = new ArrayList<Integer>();
    private String recentInfoText = "";
    private String felderWaehlen = "";
    private String closeMsg = "Spiel wurde vom Host beendet!";
    private User tempUser;


    public int getMaxPlayerAmount() {
        return 5;
    }

    public int getCurrentPlayerAmount() {
        return playerList.size();
    }

    public String getSite() {
        try {
            return FileHelper.getFile("Zauberberg/Zauberberg.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void execute(User user, String gsonString) {
        Spieler spieler = spielerList.get(playerList.indexOf(user));
        //Vorverarbeitung
        System.out.println("Empfangen: " + gsonString);
        gsonString = gsonString.replaceAll("\u00a7", "{");
        gsonString = gsonString.replaceAll("~", "}");
        System.out.println("JSON Daten: " + gsonString);

        /**
         * Eventnamen für Spielsteuerung abfangen
         */

        if (this.gState == GameState.CLOSED) {
            return;
        }

        if (gsonString.equals("CLOSE")) {
            sendGameDataToClients("CLOSE");
            closeGame();
            return;
        }

        if (gsonString.equals("START")) { // Start Button wurde vom Host gedrückt
            this.gState = GameState.RUNNING;
            //jeder Spieler bekommnt 3 zuf�llige Karten vom Stapel
            for (Spieler s : spielerList) {
                s.setHand(getRandomCards(3, spiel.getKartenstapel().getStapel()));
                spiel.getKartenstapel().getStapel().remove(s.getHand());
            }
            sendGameDataToClients("STARTGAME");
            sendGameDataToClients("UPDATEKARTEN");
            sendGameDataToClients("UPDATESPIELZUSTAND");
            return;
        }

        if (gState != GameState.RUNNING) {
            return;
        }

        if (!spieler.equals(playerTurn)) {
            return; //nur wenn der Spieler dran ist, darf er auch Daten schicken
        }

        /**
         * Eventnamen für das eigentliche Spiel
         */
        Gson gson = new GsonBuilder().create();
        HashMap<String, String> dataMap = gson.fromJson(gsonString, HashMap.class);
        switch (dataMap.get("Eventname")) {
            case "KARTENLEGEN":
                int value2Karte = 0;
                int value3Karte = 0;
                int sizeOfHand = spieler.getHand().size();

                if (dataMap.get("karte1Typ").equals("Normal")) {
                    for (int i = 0; i < spieler.getHand().size(); i++) {
                        if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte1Wert"))) {
                            spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                            spieler.getHand().remove(i);
                            aktuellerKobold = spieler.getKoboldList().get(Integer.parseInt(dataMap.get("karte1Wert")) - 1);
                            break;
                        }
                    }
                }
                if (dataMap.get("karte1Typ").equals("Joker")) {
                    for (int i = 0; i < spieler.getHand().size(); i++) {
                        if (spieler.getHand().get(i).getJoker()) {
                            spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                            spieler.getHand().remove(i);
                            aktuellerKobold = spieler.getKoboldList().get(Integer.parseInt(dataMap.get("karte1Wert")) - 1);
                            break;
                        }
                    }
                }
                if (dataMap.get("karte2Typ").equals("Normal")) {
                    for (int i = 0; i < spieler.getHand().size(); i++) {
                        if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte2Wert"))) {
                            spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                            spieler.getHand().remove(i);
                            value2Karte = Integer.parseInt(dataMap.get("karte2Wert"));
                            break;
                        }
                    }
                }
                if (dataMap.get("karte2Typ").equals("Joker")) {
                    for (int i = 0; i < spieler.getHand().size(); i++) {
                        if (spieler.getHand().get(i).getJoker()) {
                            spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                            spieler.getHand().remove(i);
                            value2Karte = Integer.parseInt(dataMap.get("karte2Wert"));
                            break;
                        }
                    }
                }


                if (dataMap.get("karte3Typ").equals("Normal")) {
                    for (int i = 0; i < spieler.getHand().size(); i++) {
                        if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte3Wert"))) {
                            spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                            spieler.getHand().remove(i);
                            value3Karte = Integer.parseInt(dataMap.get("karte3Wert"));
                            break;
                        }
                    }
                }
                if (dataMap.get("karte3Typ").equals("Joker")) {
                    for (int i = 0; i < spieler.getHand().size(); i++) {
                        if (spieler.getHand().get(i).getJoker()) {
                            spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                            spieler.getHand().remove(i);
                            value3Karte = Integer.parseInt(dataMap.get("karte3Wert"));
                            break;
                        }
                    }
                }
                //Karten verarbeiten
                tempUser = user;
                aktuellerKobold.kartenLegen(value2Karte, value3Karte);
                System.out.println(felderWaehlen);
                //neue Karten ziehen
                spieler.getHand().addAll(getRandomCards(sizeOfHand - spieler.getHand().size(), spiel.getKartenstapel().getStapel()));
                sendGameDataToUser(user, "UPDATEKARTEN");
                break;

            //dieser case läuft!
            case "KARTENTAUSCHEN":
                int sizeOfHand2 = spieler.getHand().size();
                if (!dataMap.get("karte1").equals("Null")) {
                    if (!dataMap.get("karte1").equals("Joker")) {
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte1"))) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                break;
                            }
                        }
                    } else {
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getJoker() == true) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                break;
                            }
                        }
                    }
                }
                //karte2
                if (!dataMap.get("karte2").equals("Null")) {
                    if (!dataMap.get("karte2").equals("Joker")) {
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte2"))) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                break;
                            }
                        }
                    } else {
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getJoker() == true) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                break;
                            }
                        }
                    }
                }
                //karte 3
                if (!dataMap.get("karte3").equals("Null")) {
                    if (!dataMap.get("karte3").equals("Joker")) {
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte3"))) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                break;
                            }
                        }
                    } else {
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getJoker() == true) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                break;
                            }
                        }
                    }
                }
                //karte 4
                if (!dataMap.get("karte4").equals("Null")) {
                    if (!dataMap.get("karte4").equals("Joker")) {
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte4"))) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                break;
                            }
                        }
                    } else {
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getJoker() == true) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                break;
                            }
                        }
                    }
                }
                //karte 5
                if (!dataMap.get("karte5").equals("Null")) {
                    if (!dataMap.get("karte5").equals("Joker")) {
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte5"))) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                break;
                            }
                        }
                    } else {
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getJoker() == true) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                break;
                            }
                        }
                    }
                }
                //neue Karten ziehen bei KARTENTAUSCH
                spieler.getHand().addAll(getRandomCards(sizeOfHand2 - spieler.getHand().size(), spiel.getKartenstapel().getStapel()));
                sendGameDataToUser(user, "UPDATEKARTEN");
                break;
            case "EREIGNISANTWORT":
                switch (dataMap.get("Ereignis")) {
                    case "Fliegende Karte":
                        //todo Logik
                        break;
                    case "Rabe":
                        //ausgew�hlter Spieler.setAnzahlZaubersteine(ausgewählterspielr.getAnzahlZaubersteine()-1);  
                        break;
                }
                break;
            case "FELDAUSWAEHLEN":
                aktuellerKobold.bewegen(Integer.parseInt(dataMap.get("layer")), Integer.parseInt(dataMap.get("position")));
                break;
            case "EREIGNISKARTEFLIP":
                for (Feld feld : this.spiel.getFelder()) {
                    if (feld.getLayer() == Integer.parseInt(dataMap.get("layer")) && feld.getFeldNr() == Integer.parseInt(dataMap.get("position"))) {
                        if (!feld.getClassName().equals("Feld")) {
                            feld.setKarteAufgedeckt(!feld.isKarteAufgedeckt());
                        } else {
                            this.recentInfoText = "Du kannst nur Ereigniskarten umdrehen.";
                            sendGameDataToUser(user, "PUSHINFOTXT");
                        }
                        break;
                    }
                }
                break;
            default:
                // Fehler!
        }
        //todo Wenn jemand gewonnen hat, sende CLOSE an alle und verändere davor closeMsg in die entsprechende Nachricht!
    }


    private ArrayList<Bewegungskarte> getRandomCards(int anzahlKarten, ArrayList<Bewegungskarte> stapel) {
        Random r = new Random();
        ArrayList<Bewegungskarte> returnList = new ArrayList<Bewegungskarte>();

        for (int x = 0; x < anzahlKarten; x++) {
            int random = r.nextInt(stapel.size());
            returnList.add(stapel.get(random));
            stapel.remove(random);
        }
        return returnList;
    }


    public void addUser(User user) {
        if (playerList.size() < 5 && !playerList.contains(user)) {
            playerList.add(user);
            Spieler spieler = new Spieler(this);
            spieler.setSpielerName(user.getName());
            spielerList.add(spieler);

            for (int i = 1; i <= 5; i++) {
                spieler.getKoboldList().add(new Kobold(i, spieler));
            }
            for (Kobold k : spieler.getKoboldList()) {
                k.setZauberberg(this);
            }
            if (playerTurn == null) {
                playerTurn = spieler;
            }
            sendGameDataToClients("START");
        }
    }


    public void addSpectator(User user) {
        this.spectatorList.add(user);
    }

    public void playerLeft(User user) {
        recentInfoText = spielerList.get(playerList.indexOf(user)).getName() + " hat das Spiel verlassen";
        spielerList.remove(playerList.indexOf(user));
        playerList.remove(user);
        sendGameDataToClients("PUSHINFOTXT");
    }

    /**
     * sendGameDataToClients holt sich praktisch die Daten anhand des Eventnamen aus dieser Methode
     */
    public String getGameData(String eventName, User user) {
        Spieler spieler = spielerList.get(playerList.indexOf(user));
        Gson gson = new GsonBuilder().create();
        if (eventName.equals("CLOSE")) {
            return closeMsg;
        }
        if (eventName.equals("START")) {
            return (user == creator) ? "HOST" : "NOTTHEHOST";
        }
        if (eventName.equals("STARTGAME")) {
            return "STARTGAME";
        }


        /**
         * Eigentliche Spielevents
         */
        if (eventName.equals("UPDATEKARTEN")) {
            ArrayList<String> output = new ArrayList<>();
            for (Bewegungskarte bewegungskarte : spieler.getHand()) {
                if (bewegungskarte.getJoker()) {
                    output.add("Joker");
                } else {
                    output.add(String.valueOf(bewegungskarte.getBewegungsZahl()));
                }
            }
            return gson.toJson(output, ArrayList.class);
        }
        if (eventName.equals("UPDATESPIELFELD")) {
            HashMap<String, String> output = new HashMap<>();
            for (Feld feld : this.spiel.getFelder()) {
                if (feld.getLayer() == -1) { //Feld ist ein Dorf
                    int counter = 0;
                    for (Kobold kobold : feld.getKobolde()) {
                        output.put("d" + feld.getFeldNr() + "_" + counter + "#Kobold1-Farbe", String.valueOf(kobold.getFarbe()));
                        output.put("d" + feld.getFeldNr() + "_" + counter + "#Kobold1-Nummer", String.valueOf(kobold.getNummer()));
                        counter++;
                    }
                } else { //Feld ist ein Feld des Spielfeldes
                    output.put(feld.getLayer() + "_" + feld.getFeldNr() + "#EreigniskarteSteinOffen",
                            (!feld.getClassName().equals("Feld") && ((feld.getClassName().equals("Zauberstein") && ((Zauberstein) feld).getAufFeld()) || feld.isKarteAufgedeckt()) ? "1" : "0"));
                    output.put(feld.getLayer() + "_" + feld.getFeldNr() + "#Kobold1-Farbe", "Null");
                    output.put(feld.getLayer() + "_" + feld.getFeldNr() + "#Kobold1-Nummer", "Null");
                    output.put(feld.getLayer() + "_" + feld.getFeldNr() + "#Kobold2-Farbe", "Null");
                    output.put(feld.getLayer() + "_" + feld.getFeldNr() + "#Kobold2-Nummer", "Null");
                    if (feld.getKobolde().size() > 0) {
                        output.put(feld.getLayer() + "_" + feld.getFeldNr() + "#Kobold1-Farbe", String.valueOf(feld.getKobolde().get(0).getFarbe()));
                        output.put(feld.getLayer() + "_" + feld.getFeldNr() + "#Kobold1-Nummer", String.valueOf(feld.getKobolde().get(0).getNummer()));
                    }
                    if (feld.getKobolde().size() > 1) {
                        output.put(feld.getLayer() + "_" + feld.getFeldNr() + "#Kobold2-Farbe", String.valueOf(feld.getKobolde().get(1).getFarbe()));
                        output.put(feld.getLayer() + "_" + feld.getFeldNr() + "#Kobold2-Nummer", String.valueOf(feld.getKobolde().get(1).getNummer()));
                    }
                }
            }
            return gson.toJson(output, ArrayList.class);
        }
        if (eventName.equals("UPDATESPIELZUSTAND")) {
            HashMap<String, String> output = new HashMap<>();
            for (Spieler sp : spielerList) {
                output.put(sp.getFarbName() + "Aktiv", "1"); // todo immer Aktiv wegen KI ?
                output.put(sp.getFarbName() + "Fokus", sp.equals(this.playerTurn) ? "1" : "0");
                output.put(sp.getFarbName() + "BistDu", sp.equals(spieler) ? "1" : "0");
                output.put(sp.getFarbName() + "Steine", "" + sp.getAnzahlZaubersteine());
            }
            return gson.toJson(output, HashMap.class);
        }
        if (eventName.equals("PUSHINFOTXT")) {
            return recentInfoText;
        }
        if (eventName.equals("EREIGNISANFRAGE")) {
            return ""; //todo Logik
        }
        if (eventName.equals("FELDERANBIETEN")) {
            return this.felderWaehlen;
        }
        return "";
    }


    public boolean isJoinable() {
        return (playerList.size() < 5) && this.gState != GameState.RUNNING;
    }

    public GameState getGameState() {
        return this.gState;
    }

    public String getCSS() {
        try {
            return global.FileHelper.getFile("Zauberberg/css/Zauberberg.css");
        } catch (IOException e) {
            System.err.println("Loading of file Zauberberg/css/Zauberberg.css failed");
        }
        return null;
    }

    public String getJavaScript() {
        return "<script src=\"javascript/Sortable.min.js\"></script>";
    }

    public ArrayList<User> getPlayerList() {
        return this.playerList;
    }

    public ArrayList<User> getSpectatorList() {
        return this.spectatorList;
    }

    public Spiel getSpiel() {
        return spiel;
    }

    public void setSpiel(Spiel spiel) {
        this.spiel = spiel;
    }

    public String getFelderWaehlen() {
        return felderWaehlen;
    }

    public void setFelderWaehlen(String felderWaehlen) {
        this.felderWaehlen = felderWaehlen;
    }

    //Emre
    public void sendGameDataToUserPublic(String event) {
        sendGameDataToUser(tempUser, event);
    }

    public void sendGameDataToClientsPublic(String event) {
        sendGameDataToClients(event);
    }

    public String getRecentInfoText() {
        return recentInfoText;
    }

    public void setRecentInfoText(String recentInfoText) {
        this.recentInfoText = recentInfoText;
    }

    public void setPlayerTurn(Spieler playerTurn) {
        this.playerTurn = playerTurn;
        sendGameDataToClients("UPDATESPIELZUSTAND");
    }

}