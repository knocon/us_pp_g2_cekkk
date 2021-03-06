package games.Zauberberg;

import java.util.ArrayList;

//import games.Zauberberg.Kobold.Farbe;

public class Zauberstein extends Feld {
    private Boolean aufFeld;

    public Zauberstein(int layer, int feldNr, Zauberberg zauberberg) {
        super(layer, feldNr, zauberberg);
        aufFeld = true;
    }

    public Boolean getAufFeld() {
        return aufFeld;
    }

    public void setAufFeld(Boolean aufFeld) {
        this.aufFeld = aufFeld;
        zauberberg.sendGameDataToClientsPublic("UPDATESPIELFELD");
    }

    public String getClassName() {
        return "Zauberstein";
    }
}
