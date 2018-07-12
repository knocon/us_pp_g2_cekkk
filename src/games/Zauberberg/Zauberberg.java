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
    private Kobold aktuellerKobold;
    private Spieler playerTurn = null;
    private ArrayList<User> playerList = new ArrayList<User>();
    private ArrayList<Spieler> spielerList = new ArrayList<Spieler>();
    private ArrayList<User> spectatorList = new ArrayList<User>();
    ArrayList<Integer> arrayLayerFeld = new ArrayList<Integer>(); 
    private String recentInfoText = "";
    private String felderWaehlen = "";
    private String closeMsg = "Spiel wurde vom Host beendet!";
   
    
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
        gsonString = gsonString.replaceAll("\u00a7", "{");
        gsonString = gsonString.replaceAll("~", "}");
        System.out.println("JSON Daten: " + gsonString);

        /**
         * Eventnamen für Spielsteuerung abfangen
         */

        if (this.gState == GameState.CLOSED){
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
            for(Spieler s : spielerList) {
        	s.setHand(getRandomCards(3, spiel.getKartenstapel().getStapel()));
                spiel.getKartenstapel().getStapel().remove(s.getHand());
            }
            sendGameDataToClients("STARTGAME");           
            sendGameDataToClients("UPDATEKARTEN"); 
            return;
        }
        if (gState != GameState.RUNNING)
            return;
       
        
        
        
        
      //HIER NICHT USER UND SPIELER OBJEKTE VERGLEICHEN SONDERN DIE NAMEN, DA DIE NAMEN VON SERVERSEITE UNIQUE SIND!!
        if(!user.getName().equals(spieler.getSpielerName())){
        	return;
        }
        
        /*
        if (!user.equals(playerTurn)) {
            return;
        }
        */
        
        
       
        
        
        
        
        
        
        
        
        /**
         * Eventnamen für das eigentliche Spiel
         */
        Gson gson = new GsonBuilder().create();
        HashMap<String, String> dataMap = gson.fromJson(gsonString, HashMap.class);
        switch (dataMap.get("Eventname")) {
            case "KARTENLEGEN":
            	System.out.println("moin");
                int sizeOfHand = spieler.getHand().size();
                switch (dataMap.get("karte1Typ")) {
                    //Karten werden von Hand entfernt und auf Stapel wieder gelegt 
                    case "Normal":
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte1Wert"))) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                //Zwischenspeichern des Kobolds, der mit der ersten Karte gewaehlt wurde
                                aktuellerKobold = spieler.getKoboldList().get(Integer.parseInt(dataMap.get("karte1Wert"))-1);
                                break;
                            }
                        }
                        break;
                    case "Joker":
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getJoker() == true) {
                            	aktuellerKobold = spieler.getKoboldList().get(Integer.parseInt(dataMap.get("karte1Wert"))-1);
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                break;
                            }
                        }
                        break;
                    default: //null nicht m�glich bei erster karte 
                }
                switch (dataMap.get("karte2Typ")) {
                    case "Normal":
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte2Wert"))) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                
                                //CHECKEN, OB KOBOLD SICH BEWEGEN DARF
                                if(aktuellerKobold.getLayer()==-1){
                                	arrayLayerFeld = aktuellerKobold.moeglFelder(aktuellerKobold, Integer.parseInt(dataMap.get("karte2Wert")));
                                	//DAS ARRAY HOCHGEBEN UND ANTWORT DER ART LAYER, FELDNR ZURUECKBEKOMMEN
                                	
                                	
                                	// ( AKTUELLER KOBOLD, INT TEMPLAYER, INT TEMPFELD, INT FELDNUMMER WO DER KOBOLD DRAUFSTEHT IM ALLGEMEINEN FELDERARRAY
                                	//TEMPLAYER, TEMPFELD IST DAS FELD, WO DER SPIELER HINMOECHTE 
                                	//TODO 1,1 ERSETZEN
                                	aktuellerKobold.bewegen(aktuellerKobold, 1, 1, spiel.getFelder().get(aktuellerKobold.getGlobalFeld()));
                                }else{
                                	 if(aktuellerKobold.darfBewegen(aktuellerKobold, spiel.getFelder().get(aktuellerKobold.getGlobalFeld()))==true) {
                                     	//JA
                                     	arrayLayerFeld = aktuellerKobold.moeglFelder(aktuellerKobold, Integer.parseInt(dataMap.get("karte2Wert")));
                                     }else{
                                     	//NEIN
                                     	//TODO HOCHGEBEN, DAS DER KOBOLD SICH NICHT BEWEGEN DARF                                	
                                     }
                                }
                               
                                
                               
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
                    default: //null nicht m�glich bei zweiter karte 
                }
                //BEI KARTE 3 NICHTMEHR CHECKEN, OB ER SICH BEWEGEN DARF, DA ES VORHER ABGEFANGEN WURDE
                switch (dataMap.get("karte3Typ")) {
                    case "Normal":
                        for (int i = 0; i < spieler.getHand().size(); i++) {
                            if (spieler.getHand().get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte3Wert"))) {
                                spiel.getKartenstapel().getStapel().add(spieler.getHand().get(i));
                                spieler.getHand().remove(i);
                                aktuellerKobold.moeglFelder(aktuellerKobold, Integer.parseInt(dataMap.get("karte3Wert")));
                                
                                //TODO 1,1 ERSETZEN DURCH WERTE DIE VON SERVER KOMMEN
                                aktuellerKobold.bewegen(aktuellerKobold, 1, 1, spiel.getFelder().get(aktuellerKobold.getGlobalFeld()));
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
                //todo f�r jede weitere Karte muss einmal KARTENANBIETEN gesendet werden
               
                
                // AUSGELAGERT IN METHODE moeglFelder IN KOBOLD KLASSE
                /*
                ArrayList<Integer> arrayLayerFeld = new ArrayList<Integer>(); 
                //hier werden die m�glichen Fedlder berechnet, Karte1 bestimmt Kobold, Karte2&3 schritte vor und zur�ck
                for(Kobold kobold: spieler.getKoboldList()) {
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
                		//Fragen wie in dieser Form < <layer, feld>,<layer, feld>,<layer, feld>,....> glaub nicht m?glich ohne vorher anzahl von inneren arrays zu kennen
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
                	}                  
                	if(kobold.getLayer()==0) {    
                	    arrayLayerFeld.add((kobold.getFeldNr()+Integer.parseInt(dataMap.get("karte2Wert"))) %36); 
                	    arrayLayerFeld.add((kobold.getFeldNr()+Integer.parseInt(dataMap.get("karte2Wert"))) %36);  //vorw�rts
                	    arrayLayerFeld.add((kobold.getFeldNr()-Integer.parseInt(dataMap.get("karte2Wert"))) %36); // r�ckw�rts
                	    //arrayLayerFeld.add((((Integer.parseInt(dataMap.get("karte3Wert"))))) % 36); //vorw�rts
                	    //arrayLayerFeld.add((-((Integer.parseInt(dataMap.get("karte3Wert"))))) % 36); // r�ckw�rts
                	}
                	if(kobold.getLayer()==1) {                	 
                	    arrayLayerFeld.add((kobold.getFeldNr()+Integer.parseInt(dataMap.get("karte2Wert"))) %28); //vorw�rts
                	    arrayLayerFeld.add((kobold.getFeldNr()-Integer.parseInt(dataMap.get("karte2Wert"))) %28);  // r�ckw�rts
                	    //arrayLayerFeld.add((((Integer.parseInt(dataMap.get("karte3Wert"))))) % 28); //vorw�rts
                	    //arrayLayerFeld.add((-((Integer.parseInt(dataMap.get("karte3Wert"))))) % 28); // r�ckw�rts
                	}
                	if(kobold.getLayer()==2) {                	 
                	    arrayLayerFeld.add((kobold.getFeldNr()+Integer.parseInt(dataMap.get("karte2Wert"))) %20);  //vorw�rts
                	    arrayLayerFeld.add((kobold.getFeldNr()-Integer.parseInt(dataMap.get("karte2Wert"))) %20); // r�ckw�rts
                	    //arrayLayerFeld.add((((Integer.parseInt(dataMap.get("karte3Wert"))))) % 20); //vorw�rts
                	    //arrayLayerFeld.add((-((Integer.parseInt(dataMap.get("karte3Wert"))))) % 20); // r�ckw�rts
                	}
                	if(kobold.getLayer()==3) {                  	 
                	    arrayLayerFeld.add((kobold.getFeldNr()+Integer.parseInt(dataMap.get("karte2Wert"))) %12);  //vorw�rts
                	    arrayLayerFeld.add((kobold.getFeldNr()-Integer.parseInt(dataMap.get("karte2Wert"))) %36);  // r�ckw�rts
                	    //arrayLayerFeld.add((((Integer.parseInt(dataMap.get("karte3Wert"))))) % 12); //vorw�rts
                	    //arrayLayerFeld.add((-((Integer.parseInt(dataMap.get("karte3Wert"))))) % 12); // r�ckw�rts
                	}    
                    }
                }
                */
                this.felderWaehlen = gson.toJson(arrayLayerFeld, ArrayList.class);
                sendGameDataToUser(user,"FELDERANBIETEN");
                System.out.println(felderWaehlen);
                //neue Karten ziehen
                spieler.getHand().addAll(getRandomCards(sizeOfHand - spieler.getHand().size(), spiel.getKartenstapel().getStapel()));
                sendGameDataToUser(user,"UPDATEKARTEN");
                break;

            //dieser case sollte bereits laufen 
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
                        //ausgew�hlter Spieler.setAnzahlZaubersteine(ausgew�hlterSpieler.getAnzahlZaubersteine()-1);  
                	
                        break;
                }
                break; 
            case "FELDAUSWAEHLEN":
        	 dataMap.get("layer");
        	 //move
        	 //check ereigniskarte
        	 //check rest zu verarbeitende karten
        	 dataMap.get("position");
                 for(Feld feld : spiel.getFelder()) {
                     if(feld.getLayer()== Integer.parseInt(dataMap.get("layer")) && feld.getFeldNr()== Integer.parseInt(dataMap.get("position"))){
                 	if(feld.getKobolde().size()<2) {
                 	    //feld.getKobolde().add(/*kobold*/);  
                 	} 
                 	if(feld.getKobolde().size()==2) {
                 	    //falsch, da man die richtung kennen muss von 
                 	    feld.getKobolde().get(1).setFeldNr(feld.getKobolde().get(1).getFeldNr()+1);
                 	    //feld.getKobolde().add(/*kobold*/);     
                 	    //kobold.setLayer(Integer.parseInt(dataMap.get("layer")); 
                             //kobold.setFeldNr(Integer.parseInt(dataMap.get("position"));
                 	}
                 	
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
            Spieler spieler = new Spieler();
            spieler.setSpielerName(user.getName());
            spielerList.add(spieler);

            for (int i = 1; i <= 5; i++) {
                spieler.getKoboldList().add(new Kobold(i, spieler));
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
            //return gson.toJson(kobold.felderwohinkannarray , Arraylist.class
        }
        //todo hier kommen weitere events hin, falls nötig
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