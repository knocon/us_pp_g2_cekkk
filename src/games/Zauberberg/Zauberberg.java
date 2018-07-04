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
import userManagement.User;

public class Zauberberg extends Game {
    /**
     * gridStatus contains the gameData
     */ 
    private int[] gridStatus = new int[9];
    private User playerTurn = null;
    private ArrayList<User> playerList = new ArrayList<User>();
    private ArrayList<User> spectatorList = new ArrayList<User>();
    private int turnCounter = 0;
    private String recentInfoText = "";
    private String closeMsg = "Spiel wurde vom Host beendet!";


    public int getMaxPlayerAmount() {
        return 5;
    }

    public int getCurrentPlayerAmount() {
        return playerList.size();
    }

    private int[] getGridStatus() {
        return gridStatus;
    }

    private void setGridStatus(int[] gridStatus) {
        this.gridStatus = gridStatus;
    }

    public String getSite() {
        try {
            return FileHelper.getFile("Zauberberg/Zauberberg.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //todo Wenn jemand gewonnen hat, sende CLOSE an alle und verändere davor closeMsg in die entsprechende Nachricht!

    public void execute(Spieler user, String gsonString) {
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
            //////
            Spiel s = new Spiel(); 
            user.setHand(getRandomCards(3, s.getKartenstapel().getStapel()));
            for(int i = 0; i<user.getHand().size(); i++) {
        	s.getKartenstapel().getStapel().remove(user.getHand().get(i)); 
            }
            //s.getKartenstapel().getStapel().remove(user.getHand()); 
            
            
            
            
            
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
                System.out.println(dataMap.get("karte1Typ")); // Beispiel: gibt den Typ der ersten Karte aus
                //todo Logik
                break;
            case "KARTENTAUSCHEN":
                //todo Logik
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

        // Hier ist noch der Kram von TicTacToe
        String[] strArray = gsonString.split(",");
        int[] receivedArray = new int[9];
        for (int i = 0; i < 9; i++) {
            receivedArray[i] = Integer.parseInt(strArray[i]);
        }
        int[] gridStatus = getGridStatus();
        boolean changed = false;
        for (int i = 0; i < 9; i++) {
            if (gridStatus[i] == 0 && receivedArray[i] != 0) {
                gridStatus[i] = playerList.indexOf(user) + 1;
                changed = true;
                turnCounter++;
                break;
            }
        }
        if (changed) {
            for (User u : playerList) {
                if (!u.equals(playerTurn)) {
                    playerTurn = u;
                    break;
                }
            }
            setGridStatus(gridStatus);
            if (turnCounter == 9 || (turnCounter > 4 && gameOver())) {
                this.gState = GameState.FINISHED;
            }
            sendGameDataToClients("standardEvent");
        }

    }

  

    private ArrayList<Bewegungskarte> getRandomCards(int anzahlKarte, ArrayList<Bewegungskarte> stapel) {
	// TODO Auto-generated method stub
	Random r = new Random(); 
	ArrayList<Bewegungskarte> returnList = new ArrayList<Bewegungskarte>(); 
	
	for(int x = 0; x<anzahlKarte; x++) {
	    int random = r.nextInt(stapel.size()); 
	    returnList.add(stapel.get(random)); 
	    stapel.remove(random); 	    
	}
	return returnList;
    }

    public void addUser(User user) {
        if (playerList.size() < 5 && !playerList.contains(user)) {
            playerList.add(user);

            if (playerTurn == null) {
                playerTurn = user;
            }
            sendGameDataToClients("START");
        }
        if (playerList.size() == 2) {
            this.gState = GameState.RUNNING;
            sendGameDataToClients("START");
        }

    }


    public void addSpectator(User user) {
        this.spectatorList.add(user);
    }

    public void playerLeft(User user) {
        playerList.remove(user);
        recentInfoText = user.getName() + " hat das Spiel verlassen";
        sendGameDataToClients("PUSHINFOTXT");
    }

    /**
     * sendGameDataToClients holt sich praktisch die Daten anhand des Eventnamen aus dieser Methode
     */
    public String getGameData(String eventName, User user) {
        String gameData = "";
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

        // TICTACTOE Kram
        int[] grid = getGridStatus();

        for (int i = 0; i < 9; i++) {
            gameData += String.valueOf(grid[i]);
            gameData += ',';
        }

        if (playerList.size() < 2) {
            gameData += "Warte Auf 2ten Spieler...";
            //gameData += isHost(user);
            return gameData;
        }

        if (this.gState == GameState.FINISHED) {
            if (turnCounter == 9 && !gameOver()) {
                gameData += "Unentschieden!";
                //gameData += isHost(user);
                return gameData;
            }
            if (playerTurn.equals(user)) {
                gameData += "Du hast verloren!";
            } else
                gameData += "Du hast gewonnen!";
        } else if (playerTurn.equals(user)) {
            gameData += "Du bist dran!";
        } else
            gameData += playerTurn.getName() + " ist dran!";

        if (playerList.indexOf(user) == 0)
            gameData += " (x)";
        else
            gameData += " (o)";

        //gameData += isHost(user);

        return gameData;
    }


    public boolean isJoinable() {
        return playerList.size() < 5;

    }

    public GameState getGameState() {
        return this.gState;
    }

    public boolean gameOver() {
        int[] grid = getGridStatus();
        if (grid[4] != 0) {
            if (grid[0] == grid[4] && grid[0] == grid[8])
                return true;
            if (grid[2] == grid[4] && grid[2] == grid[6])
                return true;
        }
        for (int i = 0; i < 3; i++) {

            if (grid[i * 3] != 0 && grid[1 + i * 3] != 0
                    && grid[2 + i * 3] != 0) {
                if (grid[0 + i * 3] == grid[1 + i * 3]
                        && grid[0 + i * 3] == grid[2 + i * 3])
                    return true;
            }
            if (grid[0 + i] != 0 && grid[3 + i] != 0 && grid[6 + i] != 0) {
                if (grid[0 + i] == grid[3 + i] && grid[0 + i] == grid[6 + i])
                    return true;
            }
        }
        return false;
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
        //"<script src=\"javascript/Zauberberg.js\"></script>" // Wird durch einen Fehler nicht geladen. Javascript jetzt im HTML Code
    }

    public ArrayList<User> getPlayerList() {
        return this.playerList;
    }

    public ArrayList<User> getSpectatorList() {
        return this.spectatorList;
    }

    @Override
    public void execute(User user, String s) {
	// TODO Auto-generated method stub
	
    }

}