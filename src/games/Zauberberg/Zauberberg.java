package games.Zauberberg;

import gameClasses.Game;
import gameClasses.GameState;
import global.FileHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


//import server.Server;
import sun.security.provider.ConfigFile;
import userManagement.User;

public class Zauberberg extends Game {
    /**
     * gridStatus contains the gameData
     */
    private Spieler playerTurn = null;
    private ArrayList<User> playerList = new ArrayList<User>();
    private ArrayList<Spieler> spielerList = new ArrayList<Spieler>();
    private ArrayList<User> spectatorList = new ArrayList<User>();
    private ArrayList<Kobold> koboldList = new ArrayList<Kobold>(); 
    private String recentInfoText = "";
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
        Spiel s = new Spiel();
        //Vorverarbeitung
        System.out.println("Empfangen: " + gsonString);
        gsonString = gsonString.replaceAll("§", "{");
        gsonString = gsonString.replaceAll("~", "}");
        System.out.println("JSON Daten: " + gsonString);

        /**
         * Eventnamen für Spielsteuerung abfangen
         */

        if (this.gState == GameState.CLOSED) return;

        if (gsonString.equals("CLOSE")) {
            sendGameDataToClients("CLOSE");
            closeGame();
            return;
        }
        if (gsonString.equals("START")) { // Start Button wurde vom Host gedrückt
            sendGameDataToClients("STARTGAME");
            this.gState = GameState.RUNNING;
            //////
            //Karten vom Stapel auf die Hand des Spielers
            
            spieler.setHand(getRandomCards(3, s.getKartenstapel().getStapel()));
            s.getKartenstapel().getStapel().remove(spieler.getHand()); 
            
            //zum Teste der Farbe und Kobolde
            for(int i = 0; i<koboldList.size(); i++) {
        	System.out.println(koboldList.get(i).getNummer() + "\n"+ koboldList.get(i).getSpieler().getName());
            }
            /////
            // todo Logik für den Start z.B. Aufsetzen der Spieler und verteilen von Karten etc. gefolgt von ersten Nachrichten an die Clients bezüglich des Spiels
        }
        if (gState != GameState.RUNNING)
            return;
        if (!user.equals(playerTurn)) {
            return;
        }

        /**
         * Eventnamen für das eigentliche Spiel
         */

        Gson gson = new GsonBuilder().create();
        HashMap<String, String> dataMap = gson.fromJson(gsonString, HashMap.class);
        switch (dataMap.get("Eventname")) {
            case "KARTENLEGEN":
                //System.out.println(dataMap.get("karte1Typ")); // Beispiel: gibt den Typ der ersten Karte aus
        	ArrayList<Bewegungskarte> checkList = new ArrayList<Bewegungskarte>(); 
                checkList = spieler.getHand();
                for(int i = 0; i<checkList.size(); i++) {
                    
                    if((dataMap.get("karte1Typ")=="Normal" && checkList.get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte1Wert"))) || 
                    		(dataMap.get("karte1Typ")=="Joker" && checkList.get(i).getJoker() == true)) {
                	s.getKartenstapel().getStapel().add(spieler.getHand().get(i)); 
                	spieler.getHand().remove(i);       
                	spieler.getHand().addAll(getRandomCards(1,s.getKartenstapel().getStapel())); 
                    } else if ((dataMap.get("karte2Typ")=="Normal" && checkList.get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte2Wert"))) ||
                	    	(dataMap.get("karte2Typ")=="Joker" && checkList.get(i).getJoker() == true)) {
                	s.getKartenstapel().getStapel().add(spieler.getHand().get(i)); 
            		spieler.getHand().remove(i);
            		spieler.getHand().addAll(getRandomCards(1,s.getKartenstapel().getStapel())); 
                    } else if((dataMap.get("karte3Typ")=="Normal" && checkList.get(i).getBewegungsZahl() == Integer.parseInt(dataMap.get("karte3Wert"))) || 
                        	(dataMap.get("karte3Typ")=="Joker" && checkList.get(i).getJoker() == true)) {
                	s.getKartenstapel().getStapel().add(spieler.getHand().get(i)); 
            		spieler.getHand().remove(i);
            		spieler.getHand().addAll(getRandomCards(1,s.getKartenstapel().getStapel())); 
                    }
                     
                }
                
                break;
            case "KARTENTAUSCHEN":
        	ArrayList<Bewegungskarte> handList = new ArrayList<Bewegungskarte>(); 
        	handList = spieler.getHand(); 
               
                
                break;
            case "EREIGNISANTWORT":
                switch (dataMap.get("Ereignis")) {
                    case "Kristallkugel":
                        //todo Logik
                        break;
                    case "Fliegende Karte":
                        //todo Logik
                        break;
                    case "Rabe":
                        //todo Logik
                        break;
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
            spielerList.add(spieler);        
            
            for(int i = 1; i<=5;i++) {
        	koboldList.add(new Kobold(i,spieler)); 
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
            return ""; //todo Array erstellen und über JSON als String zurückgeben (LOGIK)
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