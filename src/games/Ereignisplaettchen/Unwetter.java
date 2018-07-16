package games.Ereignisplaettchen;

import java.util.ArrayList;

import games.Zauberberg.*;

public class Unwetter extends Feld {
    private boolean karteAufgedeckt;

    public Unwetter(int layer, int feldNr, Zauberberg zauberberg) {
        super(layer, feldNr, zauberberg);
        this.karteAufgedeckt = false;
    }

    //Keine Javalogik noetig
    //Wenn aufgedeckt auf True wird, Spielfeld anzeige drehen. Muss nichtmal auf Javaebene runter gehen
    //Auf Fairplay Basis (wie besprochen) alle Karten selbst umdrehen

    public void execute() {
        setKarteAufgedeckt(false);
    }

    public String getClassName() {
        return "Unwetter";
    }

    public boolean isKarteAufgedeckt() {
        return karteAufgedeckt;
    }

    public void setKarteAufgedeckt(boolean karteAufgedeckt) {
        this.karteAufgedeckt = karteAufgedeckt;
        this.zauberberg.sendGameDataToClientsPublic("UPDATESPIELFELD");
    }
}
