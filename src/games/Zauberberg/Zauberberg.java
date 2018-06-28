package games.Zauberberg;

import gameClasses.Game;
import gameClasses.GameState;
import global.FileHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
    private String playerLeft = null;


    @Override
    public int getMaxPlayerAmount() {
        return 5;
    }

    @Override
    public int getCurrentPlayerAmount() {
        return playerList.size();
    }

    private int[] getGridStatus() {
        return gridStatus;
    }

    private void setGridStatus(int[] gridStatus) {
        this.gridStatus = gridStatus;
    }

    @Override
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

    @Override
    public void addUser(User user) {
        if (playerList.size() < 2 && !playerList.contains(user)) {
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


    @Override
    public void addSpectator(User user) {
        this.spectatorList.add(user);
    }

    @Override
    public void playerLeft(User user) {
        playerList.remove(user);
        playerLeft = user.getName();
        sendGameDataToClients("PLAYERLEFT");
    }

    /**
     * sendGameDataToClients holt sich praktisch die Daten anhand des Eventnamen aus dieser Methode
     */
    @Override
    public String getGameData(String eventName, User user) {
        String gameData = "";
        if (eventName.equals("PLAYERLEFT")) {
            return playerLeft + " hat das Spiel verlassen!";
        }
        if (eventName.equals("CLOSE")) {
            return "CLOSE";
        }

        /**
         * Eigentliche Spielevents
         */
        if (eventName.equals("UPDATEKARTEN")) {
            return ""; //todo Array erstellen und über JSON als String zurückgeben (LOGIK)
        }
        //todo hier kommen weitere events hin!

        // TICTACTOE Kram
        int[] grid = getGridStatus();

        for (int i = 0; i < 9; i++) {
            gameData += String.valueOf(grid[i]);
            gameData += ',';
        }

        if (playerList.size() < 2) {
            gameData += "Warte Auf 2ten Spieler...";
            gameData += isHost(user);
            return gameData;
        }

        if (this.gState == GameState.FINISHED) {
            if (turnCounter == 9 && !gameOver()) {
                gameData += "Unentschieden!";
                gameData += isHost(user);
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

        gameData += isHost(user);

        return gameData;
    }

    private String isHost(User user) {
        if (user == creator) return ",HOST";
        else return ",NOTTHEHOST";
    }

    @Override
    public boolean isJoinable() {
        return playerList.size() < 2;

    }

    @Override
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

    @Override
    public String getCSS() {
        try {
            return global.FileHelper.getFile("Zauberberg/css/Zauberberg.css");
        } catch (IOException e) {
            System.err.println("Loading of file Zauberberg/css/Zauberberg.css failed");
        }
        return null;
    }

    @Override
    public String getJavaScript() {
        return "<script src=\"javascript/Sortable.min.js\"></script>";
        //"<script src=\"javascript/Zauberberg.js\"></script>" // Wird durch einen Fehler nicht geladen. Javascript jetzt im HTML Code
    }

    @Override
    public ArrayList<User> getPlayerList() {
        return this.playerList;
    }

    @Override
    public ArrayList<User> getSpectatorList() {
        return this.spectatorList;
    }

}