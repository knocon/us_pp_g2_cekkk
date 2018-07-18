package games.Ereignisplaettchen;

import games.Zauberberg.*;

public class Kristallkugel extends Feld {
    private boolean karteAufgedeckt;
    private boolean karteImSpiel;

    public Kristallkugel(int layer, int feldNr, Zauberberg zauberberg) {
        super(layer, feldNr, zauberberg);
        this.karteAufgedeckt = false;
        this.karteImSpiel = true;
    }

    public String getClassName() {
        return "Kristallkugel";
    }

    public boolean isKarteAufgedeckt() {
        return karteAufgedeckt;
    }

    public void setKarteAufgedeckt(boolean karteAufgedeckt) {
        this.karteAufgedeckt = karteAufgedeckt;
        this.zauberberg.sendGameDataToClientsPublic("UPDATESPIELFELD");
    }

    public void karteAusDemSpielNehmen() {
        this.karteImSpiel = false;
        this.karteAufgedeckt = false;
        this.zauberberg.sendGameDataToClientsPublic("UPDATESPIELFELD");
    }

    public boolean isKarteImSpiel() {
        return karteImSpiel;
    }
}