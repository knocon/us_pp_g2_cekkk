package games.Ereignisplaettchen;

import java.util.ArrayList;

import games.Zauberberg.*;


public class Rabe extends Feld {
    private boolean karteAufgedeckt;

    public Rabe(int layer, int feldNr, ArrayList<Kobold> list, Zauberberg zauberberg) {
        super(layer, feldNr, list, zauberberg);
        this.karteAufgedeckt = false;
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
}
