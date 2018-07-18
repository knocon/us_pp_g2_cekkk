package games.Ereignisplaettchen;

import java.util.ArrayList;

import games.Zauberberg.*;


public class Rabe extends Feld {
    private boolean karteAufgedeckt;
    private boolean karteImSpiel;

    public Rabe(int layer, int feldNr, Zauberberg zauberberg) {
        super(layer, feldNr, zauberberg);
        this.karteAufgedeckt = false;
        this.karteImSpiel = true;
    }

    //Spieler bestimmt Spieler, der einen Zauberstein abgeben muss
    public void execute(Spieler s) {
        s.setAnzahlZaubersteine(s.getAnzahlZaubersteine() - 1);
        setKarteAufgedeckt(false);
    }

    public String getClassName() {
        return "Rabe";
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
