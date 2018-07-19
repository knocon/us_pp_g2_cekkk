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
    private Spiel spiel;
    private Kobold aktuellerKobold;
    private Spieler playerTurn = null;
    private ArrayList<User> playerList = new ArrayList<User>();
    private ArrayList<Spieler> spielerList = new ArrayList<Spieler>();
    private ArrayList<User> spectatorList = new ArrayList<User>();
    ArrayList<Integer> arrayLayerFeld = new ArrayList<Integer>();
    private String recentInfoText = "";
    private String felderWaehlen = "";
    private String ereignisAnfrage = "";
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
        try {
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
                spiel = new Spiel(this);
                //jeder Spieler bekommnt 3 zuf�llige Karten vom Stapel
                for (Spieler s : spielerList) {
                    s.setHand(getRandomCards(3, spiel.getKartenstapel().getStapel()));
                    spiel.getKartenstapel().getStapel().remove(s.getHand());

                    for (Kobold kobold : s.getKoboldList()) {
                        for (Feld feld : spiel.getFelder()) {
                            if (feld.getFeldNr() == kobold.getFeldNr() && feld.getLayer() == kobold.getLayer()) {
                                feld.getKobolde().add(kobold);
                                break;
                            }
                        }
                    }
                }
                sendGameDataToClients("STARTGAME");
                sendGameDataToClients("UPDATEKARTEN");
                sendGameDataToClients("UPDATESPIELZUSTAND");
                sendGameDataToClients("UPDATESPIELFELD");
                return;
            }

            if (gState != GameState.RUNNING) {
                return;
            }

            //if (!spieler.equals(playerTurn)) {
            //    return; //nur wenn der Spieler dran ist, darf er auch Daten schicken
            //}

            /**
             * Eventnamen für das eigentliche Spiel
             */
            Gson gson = new GsonBuilder().create();
            HashMap<String, String> dataMap = gson.fromJson(gsonString, HashMap.class);
            switch (dataMap.get("Eventname")) {
                case "KARTENLEGEN":

                    Bewegungskarte instanceKoboldKarte = null;
                    Bewegungskarte instance2Karte = null;
                    Bewegungskarte instance3Karte = null;
                    int value2Karte = 0;
                    int value3Karte = 0;

                    if (dataMap.get("karte1Typ").equals("Normal")) {
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte1Wert"))) {
                                instanceKoboldKarte = spieler.getHand().get(i);
                                aktuellerKobold = spieler.getKoboldList().get(Integer.parseInt(dataMap.get("karte1Wert")) - 1);
                                break;
                            }
                        }
                    } else if (dataMap.get("karte1Typ").equals("Joker")) {
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getJoker()) {
                                instanceKoboldKarte = spieler.getHand().get(i);
                                aktuellerKobold = spieler.getKoboldList().get(Integer.parseInt(dataMap.get("karte1Wert")) - 1);
                                break;
                            }
                        }
                    }
                    if (dataMap.get("karte2Typ").equals("Normal")) {
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte2Wert"))) {
                                instance2Karte = spieler.getHand().get(i);
                                value2Karte = Integer.parseInt(dataMap.get("karte2Wert"));
                                break;
                            }
                        }
                    } else if (dataMap.get("karte2Typ").equals("Joker")) {
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getJoker()) {
                                instance2Karte = spieler.getHand().get(i);
                                value2Karte = Integer.parseInt(dataMap.get("karte2Wert"));
                                break;
                            }
                        }
                    }


                    if (dataMap.get("karte3Typ").equals("Normal")) {
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte3Wert"))) {
                                instance3Karte = spieler.getHand().get(i);
                                value3Karte = Integer.parseInt(dataMap.get("karte3Wert"));
                                break;
                            }
                        }
                    } else if (dataMap.get("karte3Typ").equals("Joker")) {
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getJoker()) {
                                instance3Karte = spieler.getHand().get(i);
                                value3Karte = Integer.parseInt(dataMap.get("karte3Wert"));
                                break;
                            }
                        }
                    }
                    //Karten verarbeiten
                    tempUser = user;
                    aktuellerKobold.kartenLegen(instanceKoboldKarte, instance2Karte, instance3Karte, value2Karte, value3Karte);
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
                    spieler.getHand().addAll(getRandomCards((spieler.getAnzahlZaubersteine() >= 0 ? spieler.getAnzahlZaubersteine() + 3 - spieler.getHand().size() : 3 - spieler.getHand().size()), spiel.getKartenstapel().getStapel()));
                    sendGameDataToUser(user, "UPDATEKARTEN");
                    //Zug beenden
                    this.zugBeenden();
                    break;
                case "EREIGNISANTWORT":
                    switch (dataMap.get("Ereignis")) {
                        case "Fliegende Karte":
                            if (dataMap.get("karte1Typ").equals("Normal")) {
                                for (int i = 0; i < spieler.getHand().size(); i++) {
                                    if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte1Wert"))) {
                                        aktuellerKobold.getKartenInstances().add(spieler.getHand().get(i));
                                        aktuellerKobold.getKartenWerte().add(Integer.parseInt(dataMap.get("karte1Wert")));
                                        break;
                                    }
                                }
                            } else if (dataMap.get("karte1Typ").equals("Joker")) {
                                for (int i = 0; i < spieler.getHand().size(); i++) {
                                    if (spieler.getHand().get(i).getJoker()) {
                                        aktuellerKobold.getKartenInstances().add(spieler.getHand().get(i));
                                        aktuellerKobold.getKartenWerte().add(Integer.parseInt(dataMap.get("karte1Wert")));
                                        break;
                                    }
                                }
                            }
                            if (dataMap.get("karte2Typ").equals("Normal")) {
                                for (int i = 0; i < spieler.getHand().size(); i++) {
                                    if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte2Wert"))) {
                                        aktuellerKobold.getKartenInstances().add(spieler.getHand().get(i));
                                        aktuellerKobold.getKartenWerte().add(Integer.parseInt(dataMap.get("karte2Wert")));
                                        break;
                                    }
                                }
                            } else if (dataMap.get("karte2Typ").equals("Joker")) {
                                for (int i = 0; i < spieler.getHand().size(); i++) {
                                    if (spieler.getHand().get(i).getJoker()) {
                                        aktuellerKobold.getKartenInstances().add(spieler.getHand().get(i));
                                        aktuellerKobold.getKartenWerte().add(Integer.parseInt(dataMap.get("karte2Wert")));
                                        break;
                                    }
                                }
                            }
                            aktuellerKobold.bewegenBeenden();
                            break;
                        case "Rabe":
                            for (Spieler sp : spielerList) {
                                if (String.valueOf(sp.getFarbName()).equals(dataMap.get("Spieler").toUpperCase())) {
                                    sp.setAnzahlZaubersteine(sp.getAnzahlZaubersteine() - 1);
                                    if (sp.getAnzahlZaubersteine() >= 0) {
                                        this.spiel.getKartenstapel().getStapel().add(sp.getHand().get(0));
                                        sp.getHand().remove(0);
                                        User u = this.playerList.get(this.spielerList.indexOf(sp));
                                        sendGameDataToUser(u, "UPDATEKARTEN");
                                    }
                                    break;
                                }
                            }
                            aktuellerKobold.bewegenBeenden();
                            sendGameDataToClients("UPDATESPIELZUSTAND");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        //todo Wenn jemand gewonnen hat, sende CLOSE an alle und verändere davor closeMsg in die entsprechende Nachricht!
    }

    public void aufstAbstCheck() {
        Feld tempFeld = null;
        for (Feld f : spiel.getFelder()) {
            //Zuerst die Geheimgaenge
            //Nur 3 Geheimgaenge checken, da der vierte zum Sieg fuehrt
            if (f.getLayer() == 0 && f.getFeldNr() == 15) {
                for (Feld g : spiel.getFelder()) {
                    if (g.getLayer() == 1 && g.getFeldNr() == 12) {
                        tempFeld = g;
                    }
                }
                if (f.getKobolde().size() == 1 && tempFeld.getKobolde().size() == 0) {
                    f.getKobolde().get(0).setLayer(1);
                    f.getKobolde().get(0).setFeldNr(12);
                    aktuellerKobold = f.getKobolde().get(0);
                    f.getKobolde().remove(0);
                    tempFeld.getKobolde().add(aktuellerKobold);
                } else if (f.getKobolde().size() == 2 && tempFeld.getKobolde().size() == 0) {
                    f.getKobolde().get(1).setLayer(1);
                    f.getKobolde().get(1).setFeldNr(12);
                    aktuellerKobold = f.getKobolde().get(1);
                    f.getKobolde().remove(1);
                    tempFeld.getKobolde().add(aktuellerKobold);
                }
            } else if (f.getLayer() == 1 && f.getFeldNr() == 3) {
                for (Feld g : spiel.getFelder()) {
                    if (g.getLayer() == 2 && g.getFeldNr() == 2) {
                        tempFeld = g;
                    }
                }
                if (f.getKobolde().size() == 1 && tempFeld.getKobolde().size() == 0) {
                    f.getKobolde().get(0).setLayer(2);
                    f.getKobolde().get(0).setFeldNr(2);
                    aktuellerKobold = f.getKobolde().get(0);
                    f.getKobolde().remove(0);
                    tempFeld.getKobolde().add(aktuellerKobold);
                } else if (f.getKobolde().size() == 2 && tempFeld.getKobolde().size() == 0) {
                    f.getKobolde().get(1).setLayer(2);
                    f.getKobolde().get(1).setFeldNr(2);
                    aktuellerKobold = f.getKobolde().get(1);
                    f.getKobolde().remove(1);
                    tempFeld.getKobolde().add(aktuellerKobold);
                }
            } else if (f.getLayer() == 1 && f.getFeldNr() == 22) {
                for (Feld g : spiel.getFelder()) {
                    if (g.getLayer() == 2 && g.getFeldNr() == 15) {
                        tempFeld = g;
                    }
                }
                if (f.getKobolde().size() == 1 && tempFeld.getKobolde().size() == 0) {
                    f.getKobolde().get(0).setLayer(2);
                    f.getKobolde().get(0).setFeldNr(15);
                    aktuellerKobold = f.getKobolde().get(0);
                    f.getKobolde().remove(0);
                    tempFeld.getKobolde().add(aktuellerKobold);
                } else if (f.getKobolde().size() == 2 && tempFeld.getKobolde().size() == 0) {
                    f.getKobolde().get(1).setLayer(2);
                    f.getKobolde().get(1).setFeldNr(15);
                    aktuellerKobold = f.getKobolde().get(1);
                    f.getKobolde().remove(1);
                    tempFeld.getKobolde().add(aktuellerKobold);
                }
            }//hier beginnen die Fallgruben
            else if (f.getLayer() == 2 && f.getFeldNr() == 19) {
                for (Feld g : spiel.getFelder()) {
                    if (g.getLayer() == 1 && g.getFeldNr() == 26) {
                        tempFeld = g;
                    }
                }
                if (f.getKobolde().size() == 1 && tempFeld.getKobolde().size() == 0) {
                    f.getKobolde().get(0).setLayer(1);
                    f.getKobolde().get(0).setFeldNr(26);
                    aktuellerKobold = f.getKobolde().get(0);
                    f.getKobolde().remove(0);
                    tempFeld.getKobolde().add(aktuellerKobold);
                } else if (f.getKobolde().size() == 2 && tempFeld.getKobolde().size() == 0) {
                    f.getKobolde().get(1).setLayer(1);
                    f.getKobolde().get(1).setFeldNr(26);
                    aktuellerKobold = f.getKobolde().get(1);
                    f.getKobolde().remove(1);
                    tempFeld.getKobolde().add(aktuellerKobold);
                }
            } else if (f.getLayer() == 3 && f.getFeldNr() == 7) {
                for (Feld g : spiel.getFelder()) {
                    if (g.getLayer() == 2 && g.getFeldNr() == 12) {
                        tempFeld = g;
                    }
                }
                if (f.getKobolde().size() == 1 && tempFeld.getKobolde().size() == 0) {
                    f.getKobolde().get(0).setLayer(2);
                    f.getKobolde().get(0).setFeldNr(12);
                    aktuellerKobold = f.getKobolde().get(0);
                    f.getKobolde().remove(0);
                    tempFeld.getKobolde().add(aktuellerKobold);
                } else if (f.getKobolde().size() == 2 && tempFeld.getKobolde().size() == 0) {
                    f.getKobolde().get(1).setLayer(2);
                    f.getKobolde().get(1).setFeldNr(12);
                    aktuellerKobold = f.getKobolde().get(0);
                    f.getKobolde().remove(0);
                    tempFeld.getKobolde().add(aktuellerKobold);
                }
            }

        }
    }


    public ArrayList<Bewegungskarte> getRandomCards(int anzahlKarten, ArrayList<Bewegungskarte> stapel) {
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
                spieler.getKoboldList().add(new Kobold(i, spieler, this));
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
                    for (int i = 0; i < 8; i++) {
                        output.put("d" + feld.getFeldNr() + "_" + i + "#Kobold1-Farbe", "Null");
                        output.put("d" + feld.getFeldNr() + "_" + i + "#Kobold1-Nummer", "Null");
                    }
                    for (Kobold kobold : feld.getKobolde()) {
                        output.put("d" + feld.getFeldNr() + "_" + counter + "#Kobold1-Farbe", String.valueOf(kobold.getFarbe()));
                        output.put("d" + feld.getFeldNr() + "_" + counter + "#Kobold1-Nummer", String.valueOf(kobold.getNummer()));
                        counter++;
                    }
                } else { //Feld ist ein Feld des Spielfeldes
                    String offenGeschlossen = "0";
                    if (!feld.getClassName().equals("Feld")) {
                        if (feld.getClassName().equals("Zauberstein")) {
                            if (((Zauberstein) feld).getAufFeld()) {
                                offenGeschlossen = "1";
                            }
                        } else if (feld.getClassName().equals("Kristallkugel")) {
                            if (((Kristallkugel) feld).isKarteImSpiel()) {
                                if (feld.isKarteAufgedeckt()) {
                                    offenGeschlossen = "1";
                                }
                            }
                        } else if (feld.getClassName().equals("Rabe")) {
                            if (((Rabe) feld).isKarteImSpiel()) {
                                if (feld.isKarteAufgedeckt()) {
                                    offenGeschlossen = "1";
                                }
                            }
                        } else {
                            if (feld.isKarteAufgedeckt()) {
                                offenGeschlossen = "1";
                            }
                        }
                    }
                    output.put(feld.getLayer() + "_" + feld.getFeldNr() + "#EreigniskarteSteinOffen", offenGeschlossen);
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
            return gson.toJson(output, HashMap.class);
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
            return ereignisAnfrage;
        }
        if (eventName.equals("FELDERANBIETEN")) {
            System.out.println("Felderanbieten: " + this.felderWaehlen);
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

    public void setFelderWaehlen(String felderWaehlen) {
        this.felderWaehlen = felderWaehlen;
    }

    public void sendGameDataToUserPublic(String event) {
        sendGameDataToUser(tempUser, event);
    }

    public void sendGameDataToClientsPublic(String event) {
        sendGameDataToClients(event);
    }

    public void setRecentInfoText(String recentInfoText) {
        this.recentInfoText = recentInfoText;
    }

    public void setEreignisAnfrage(String ereignisAnfrage) {
        this.ereignisAnfrage = ereignisAnfrage;
    }

    public void setPlayerTurn(Spieler playerTurn) {
        this.playerTurn = playerTurn;
        sendGameDataToClients("UPDATESPIELZUSTAND");
    }

    public void zugBeenden() {
        if (this.spielerList.indexOf(this.playerTurn) + 1 == this.spielerList.size()) {
            this.setPlayerTurn(this.spielerList.get(0));
        } else {
            this.setPlayerTurn(this.spielerList.get(this.spielerList.indexOf(this.playerTurn) + 1));
        }
        //Methode die Ereignisfelder Geheimgang / Fallgrube checkt und ggf. Kobolde hoch / runter schmeisst
        aufstAbstCheck();
    }

    public Kobold getAktuellerKobold() {
        return aktuellerKobold;
    }

    public void setAktuellerKobold(Kobold aktuellerKobold) {
        this.aktuellerKobold = aktuellerKobold;
    }

    public String getCloseMsg() {
        return closeMsg;
    }

    public void setCloseMsg(String closeMsg) {
        this.closeMsg = closeMsg;
    }


}