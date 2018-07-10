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
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


//import server.Server;
import userManagement.User;

public class Zauberberg extends Game {
    /**
     * gridStatus contains the gameData
     */
    private Spiel spiel = new Spiel();
    private Spieler playerTurn = null;
    private ArrayList<User> playerList = new ArrayList<User>();
    private ArrayList<Spieler> spielerList = new ArrayList<Spieler>();
    private ArrayList<User> spectatorList = new ArrayList<User>();
    private ArrayList<Kobold> koboldList = new ArrayList<Kobold>();
    private String recentInfoText = "";
    private String felderWaehlen = "";
    private String closeMsg = "Spiel wurde vom Host beendet!";

    public void ereignisseErzeugen() {
    	
    	Fallgrube fallgrube0 = new Fallgrube(2, 19, koboldList);
    	Fallgrube fallgrube1 = new Fallgrube(3, 7, koboldList);
    	spiel.getFallgrube()[0] = fallgrube0;
    	spiel.getFallgrube()[1] = fallgrube1;
    		
    	
    	FliegendeKarte fliegendeKarte0 = new FliegendeKarte(2, 8, koboldList);
    	FliegendeKarte fliegendeKarte1 = new FliegendeKarte(2, 14, koboldList);
    	spiel.getFliegendeKarte()[0] = fliegendeKarte0;
    	spiel.getFliegendeKarte()[1] = fliegendeKarte1;
    	
    	
    	Geheimgang geheimgang0 = new Geheimgang(0, 15, koboldList);
    	Geheimgang geheimgang1 = new Geheimgang(1, 3, koboldList);
    	Geheimgang geheimgang2 = new Geheimgang(1, 22, koboldList);
    	Geheimgang geheimgang3 = new Geheimgang(3, 10, koboldList);
    	spiel.getGeheimgang()[0] = geheimgang0;
    	spiel.getGeheimgang()[1] = geheimgang1;
    	spiel.getGeheimgang()[2] = geheimgang2;
    	spiel.getGeheimgang()[3] = geheimgang3;
    	
    	
    	Kristallkugel kristallkugel0 = new Kristallkugel(0, 7, koboldList);
    	Kristallkugel kristallkugel1 = new Kristallkugel(0, 25, koboldList);
    	Kristallkugel kristallkugel2 = new Kristallkugel(1, 10, koboldList);
    	spiel.getKristallkugel()[0] = kristallkugel0;
    	spiel.getKristallkugel()[1] = kristallkugel1;
    	spiel.getKristallkugel()[2] = kristallkugel2;
    	
    	
    	Rabe rabe0 = new Rabe(0, 30, koboldList);
    	Rabe rabe1 = new Rabe(1, 17, koboldList);
    	Rabe rabe2 = new Rabe(2, 4, koboldList);
    	spiel.getRabe()[0] = rabe0;
    	spiel.getRabe()[1] = rabe1;
    	spiel.getRabe()[2] = rabe2;
    	
    	
    	Schreckgespenst schreckgespenst0 = new Schreckgespenst(0, 1, koboldList);
    	Schreckgespenst schreckgespenst1 = new Schreckgespenst(1, 4, koboldList);
    	Schreckgespenst schreckgespenst2 = new Schreckgespenst(3, 1, koboldList);
    	spiel.getSchreckgespenst()[0] = schreckgespenst0;
    	spiel.getSchreckgespenst()[1] = schreckgespenst1;
    	spiel.getSchreckgespenst()[2] = schreckgespenst2;

    	
    	Unwetter unwetter0 = new Unwetter(0, 12, koboldList);
    	Unwetter unwetter1 = new Unwetter(0, 19, koboldList);
    	Unwetter unwetter2 = new Unwetter(0, 32, koboldList);
    	spiel.getUnwetter()[0] = unwetter0;
    	spiel.getUnwetter()[1] = unwetter1;
    	spiel.getUnwetter()[2] = unwetter2;
    }
    public void steineErzeugen() {
    	Zauberstein zStein0 = new Zauberstein(1, 0);
    	zStein0.setAufFeld(true);
    	Zauberstein zStein1 = new Zauberstein(1, 19);
    	zStein1.setAufFeld(true);
    	Zauberstein zStein2 = new Zauberstein(1, 8);
    	zStein2.setAufFeld(true);
    	Zauberstein zStein3 = new Zauberstein(2, 3);
    	zStein3.setAufFeld(true);
    	Zauberstein zStein4 = new Zauberstein(2, 10);
    	zStein4.setAufFeld(true);
    	Zauberstein zStein5 = new Zauberstein(2, 19);
    	zStein5.setAufFeld(true);
    	Zauberstein zStein6 = new Zauberstein(3, 0);
    	zStein6.setAufFeld(true);
    	Zauberstein zStein7 = new Zauberstein(3, 4);
    	zStein7.setAufFeld(true);
    	Zauberstein zStein8 = new Zauberstein(3, 8);
    	zStein8.setAufFeld(true);
    	
    }
    public void doerferErzeugen() {
    	Dorf blau = new Dorf();
    	blau.setLayer(-1);
    	blau.setFeldNr(0);
    	Dorf gruen = new Dorf();
    	gruen.setLayer(-1);
    	gruen.setFeldNr(1);
    	Dorf rot = new Dorf();
    	rot.setLayer(-1);
    	rot.setFeldNr(2);
    	Dorf gelb = new Dorf();
    	gelb.setLayer(-1);
    	gelb.setFeldNr(3);
    }
    public void koboldeErzeugen() {
    	//Hardcoded in Dorf 0
    	Kobold blau1 = new Kobold(0, playerTurn);
    	Kobold blau2 = new Kobold(0, playerTurn);
    	Kobold blau3 = new Kobold(0, playerTurn);
    	Kobold blau4 = new Kobold(0, playerTurn);
    	Kobold blau5 = new Kobold(0, playerTurn);
    	blau1.setFarbe(Kobold.Farbe.Blau);
    	blau2.setFarbe(Kobold.Farbe.Blau);
    	blau3.setFarbe(Kobold.Farbe.Blau);
    	blau4.setFarbe(Kobold.Farbe.Blau);
    	blau5.setFarbe(Kobold.Farbe.Blau);
    	
    	Kobold rot1 = new Kobold(0, playerTurn);
    	Kobold rot2 = new Kobold(0, playerTurn);
    	Kobold rot3 = new Kobold(0, playerTurn);
    	Kobold rot4 = new Kobold(0, playerTurn);
    	Kobold rot5 = new Kobold(0, playerTurn);
    	rot1.setFarbe(Kobold.Farbe.Rot);
    	rot2.setFarbe(Kobold.Farbe.Rot);
    	rot3.setFarbe(Kobold.Farbe.Rot);
    	rot4.setFarbe(Kobold.Farbe.Rot);
    	rot5.setFarbe(Kobold.Farbe.Rot);
    	
    	Kobold gold1 = new Kobold(0, playerTurn);
    	Kobold gold2 = new Kobold(0, playerTurn);
    	Kobold gold3 = new Kobold(0, playerTurn);
    	Kobold gold4 = new Kobold(0, playerTurn);
    	Kobold gold5 = new Kobold(0, playerTurn);
    	gold1.setFarbe(Kobold.Farbe.Gold);
    	gold2.setFarbe(Kobold.Farbe.Gold);
    	gold3.setFarbe(Kobold.Farbe.Gold);
    	gold4.setFarbe(Kobold.Farbe.Gold);
    	gold5.setFarbe(Kobold.Farbe.Gold);
    	
    	Kobold schwarz1 = new Kobold(0, playerTurn);
    	Kobold schwarz2 = new Kobold(0, playerTurn);
    	Kobold schwarz3 = new Kobold(0, playerTurn);
    	Kobold schwarz4 = new Kobold(0, playerTurn);
    	Kobold schwarz5 = new Kobold(0, playerTurn);
    	schwarz1.setFarbe(Kobold.Farbe.Schwarz);
    	schwarz2.setFarbe(Kobold.Farbe.Schwarz);
    	schwarz3.setFarbe(Kobold.Farbe.Schwarz);
    	schwarz4.setFarbe(Kobold.Farbe.Schwarz);
    	schwarz5.setFarbe(Kobold.Farbe.Schwarz);
    	
    	Kobold gruen1 = new Kobold(0, playerTurn);
    	Kobold gruen2 = new Kobold(0, playerTurn);
    	Kobold gruen3 = new Kobold(0, playerTurn);
    	Kobold gruen4 = new Kobold(0, playerTurn);
    	Kobold gruen5 = new Kobold(0, playerTurn);
    	gruen1.setFarbe(Kobold.Farbe.Gruen);
    	gruen2.setFarbe(Kobold.Farbe.Gruen);
    	gruen3.setFarbe(Kobold.Farbe.Gruen);
    	gruen4.setFarbe(Kobold.Farbe.Gruen);
    	gruen5.setFarbe(Kobold.Farbe.Gruen);
    }
    
    
    
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
        //TODO Spieler Farbe geben
        
        
        //Vorverarbeitung
        System.out.println("Empfangen: " + gsonString);
        gsonString = gsonString.replaceAll("Â§", "{");
        gsonString = gsonString.replaceAll("~", "}");
        System.out.println("JSON Daten: " + gsonString);

        /**
         * Eventnamen fÃ¼r Spielsteuerung abfangen
         */

        if (this.gState == GameState.CLOSED) return;

        if (gsonString.equals("CLOSE")) {
            sendGameDataToClients("CLOSE");
            closeGame();
            return;
        }
        if (gsonString.equals("START")) { // Start Button wurde vom Host gedrÃ¼ckt
            this.gState = GameState.RUNNING;
            //////
            
            
            //Karten vom Stapel auf die Hand des Spielers
            for(Spieler s : spielerList) {
        	s.setHand(getRandomCards(3, spiel.getKartenstapel().getStapel()));
                spiel.getKartenstapel().getStapel().remove(s.getHand());
            }
            

            sendGameDataToClients("STARTGAME");

            /////
            // todo Logik fÃ¼r den Start z.B. Aufsetzen der Spieler und verteilen von Karten etc. gefolgt von ersten Nachrichten an die Clients bezÃ¼glich des Spiels
            sendGameDataToClients("UPDATEKARTEN");

        }
        if (gState != GameState.RUNNING)
            return;
        if (!user.equals(playerTurn)) {
            return;
        }

        /**
         * Eventnamen fÃ¼r das eigentliche Spiel
         */

        Gson gson = new GsonBuilder().create();
        HashMap<String, String> dataMap = gson.fromJson(gsonString, HashMap.class);
        switch (dataMap.get("Eventname")) {
            case "KARTENLEGEN":
                //System.out.println(dataMap.get("karte1Typ")); // Beispiel: gibt den Typ der ersten Karte aus
                //ArrayList<Bewegungskarte> checkList = new ArrayList<Bewegungskarte>();
                //checkList = spieler.getHand();
                int sizeOfHand = spieler.getHand().size();
                switch (dataMap.get("karte1Typ")) {
                    case "Normal":
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte1Wert"))) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                break;
                            }
                        }
                        break;
                    case "Joker":
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getJoker() == true) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                break;
                            }
                        }
                        break;
                    default: //null nicht möglich bei erster karte 
                }

                switch (dataMap.get("karte2Typ")) {
                    case "Normal":
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte2Wert"))) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                break;
                            }
                        }
                        break;
                    case "Joker":
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getJoker() == true) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                break;
                            }
                        }
                        break;
                    default: //null nicht möglich bei zweiter karte 
                }
                switch (dataMap.get("karte3Typ")) {
                    case "Normal":
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte3Wert"))) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                break;
                            }
                        }
                        break;
                    case "Joker":
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getJoker() == true) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                break;
                            }
                        }
                        break;
                }

                //todo Kobold ist karte 1
                //todo für jede weitere Karte muss einmal KARTENANBIETEN gesendet werden
                
                ArrayList<ArrayList<Integer>> arrayFelderAnbieten = new ArrayList<ArrayList<Integer>>();
                ArrayList<Integer> arrayLayerFeld = new ArrayList<Integer>(); 
                //hier werden die möglichen Fedlder berechnet, Karte1 bestimmt Kobold, Karte2&3 schritte vor und zurück
                for(Kobold kobold: koboldList) {
                    if(kobold.getSpieler() == spieler && kobold.getNummer() == Integer.parseInt(dataMap.get("karte1Wert"))) {
                	if(kobold.getLayer()==-1) {
                	    //Schritt raus aus dem Dorf 
                	    if(kobold.getDorf()==1) {
                		arrayLayerFeld.add(1); //layer 
                		arrayLayerFeld.add((((Integer.parseInt(dataMap.get("karte2Wert")))-1)) % 36); //schritte nach "vorne"
                		arrayLayerFeld.add(1); //layer 
                		arrayLayerFeld.add((-((Integer.parseInt(dataMap.get("karte2Wert")))-1)) % 36); // schritte nach "hinten"
                		//arrayLayerFeld.add(1); 
                		//arrayLayerFeld.add(Integer.parseInt(dataMap.get("karte2Wert"))-1 % 36); 
                		//Fragen wie in dieser Form < <layer, feld>,<layer, feld>,<layer, feld>,....> glaub nicht möglich ohne vorher anzahl von inneren arrays zu kennen
                	    } else if (kobold.getDorf()==2) {
                		arrayLayerFeld.add(1); //layer 
                		arrayLayerFeld.add((9+((Integer.parseInt(dataMap.get("karte2Wert")))-1)) % 36); //schritte nach "vorne"
                		arrayLayerFeld.add(1); //layer 
                		arrayLayerFeld.add((9-((Integer.parseInt(dataMap.get("karte2Wert")))-1)) % 36); // schritte nach "hinten"
                	    } else if (kobold.getDorf() ==3) {
                		arrayLayerFeld.add(1); //layer 
                		arrayLayerFeld.add((18+((Integer.parseInt(dataMap.get("karte2Wert")))-1)) % 36); //schritte nach "vorne"
                		arrayLayerFeld.add(1); //layer 
                		arrayLayerFeld.add((18-((Integer.parseInt(dataMap.get("karte2Wert")))-1)) % 36); // schritte nach "hinten"
                	    } else if(kobold.getDorf()==4) {
                	    	arrayLayerFeld.add(1); //layer 
            			arrayLayerFeld.add((27+((Integer.parseInt(dataMap.get("karte2Wert")))-1)) % 36); //schritte nach "vorne"
            			arrayLayerFeld.add(1); //layer 
            			arrayLayerFeld.add((27-((Integer.parseInt(dataMap.get("karte2Wert")))-1)) % 36); // schritte nach "hinten"
                	    }                	    
                	} else if(kobold.getLayer()>=0) { //TODO für jeden layer gibt es andere berechnung zb MOD 36 (layer 0), mod 28 (layer 1), mod 20 (layer2), mod 12 (layer 3)
                	  //selbe prinzip wie oben, array abwechselnd mit layer und feldnr füllen für vorwärts schritte und rückwärts schritte
                	}
                    }                    
                }
                this.felderWaehlen = gson.toJson(arrayLayerFeld, ArrayList.class);
                sendGameDataToClients("KARTENANBIETEN"); //FELDERANBIETEN IST HIER GLAUB GEMEINT 

                //neue Karten ziehen
                spieler.getHand().addAll(getRandomCards(sizeOfHand - spieler.getHand().size(), spiel.getKartenstapel().getStapel()));
                sendGameDataToUser(user,"UPDATEKARTEN");
                break;

            case "KARTENTAUSCHEN":
                int sizeOfHand2 = spieler.getHand().size();
                if (dataMap.get("karte1") != "Null") {
                    if (dataMap.get("karte1") != "Joker") {
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
                if (dataMap.get("karte2") != "Null") {
                    if (dataMap.get("karte2") != "Joker") {
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
                if (dataMap.get("karte3") != "Null") {
                    if (dataMap.get("karte3") != "Joker") {
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
                if (dataMap.get("karte4") != "Null") {
                    if (dataMap.get("karte4") != "Joker") {
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
                if (dataMap.get("karte5") != "Null") {
                    if (dataMap.get("karte5") != "Joker") {
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
                        //ausgewählter Spieler.setAnzahlZaubersteine(ausgewählterSpieler.getAnzahlZaubersteine()-1);  
                	
                        break;
                }
                break;
            case "FELDAUSWAEHLEN":
                dataMap.get("layer");
                dataMap.get("position");
                // todo Kobold entsprechend bewegen
                break;
            default:
                // Fehler!
        }
        //todo Wenn jemand gewonnen hat, sende CLOSE an alle und verÃ¤ndere davor closeMsg in die entsprechende Nachricht!
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
            Spieler spieler = new Spieler();
            spielerList.add(spieler);

            for (int i = 1; i <= 5; i++) {
                koboldList.add(new Kobold(i, spieler));
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
            return ""; //todo Logik
        }
        if (eventName.equals("UPDATESPIELZUSTAND")) {
            return ""; //todo Logik
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
        //todo hier kommen weitere events hin, falls nÃ¶tig
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
}