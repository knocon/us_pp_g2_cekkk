package games.Ereignisplaettchen;

import java.util.ArrayList;

import games.Zauberberg.*;

public class Kristallkugel extends Feld {
    private boolean karteAufgedeckt;

    public Kristallkugel(int layer, int feldNr, ArrayList<Kobold> list, Zauberberg zauberberg) {
        super(layer, feldNr, list, zauberberg);
        this.karteAufgedeckt = false;
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
}