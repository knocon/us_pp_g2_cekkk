package games.Ereignisplaettchen;

import java.util.ArrayList;

import games.Zauberberg.*;

public class FliegendeKarte extends Feld {
    private boolean karteAufgedeckt;
    private Bewegungskarte fliegendekarte;

    public FliegendeKarte(int layer, int feldNr, Zauberberg zauberberg) {
        super(layer, feldNr, zauberberg);
        this.karteAufgedeckt = false;
    }

    public void execute(Spieler s, Kartenstapel kartenstapel) {
        int stapelSize = kartenstapel.getStapel().size() - 1;
        int random = (int) (Math.random() * stapelSize + 0);
        fliegendekarte = kartenstapel.getStapel().get(random);
        s.getHand().add(fliegendekarte);
        kartenstapel.getStapel().remove(random);
        setKarteAufgedeckt(false);
    }

    public String getClassName() {
        return "Fliegende Karte";
    }

    public boolean isKarteAufgedeckt() {
        return karteAufgedeckt;
    }

    public void setKarteAufgedeckt(boolean karteAufgedeckt) {
        this.karteAufgedeckt = karteAufgedeckt;
        this.zauberberg.sendGameDataToClientsPublic("UPDATESPIELFELD");
    }

    public Bewegungskarte getFliegendekarte() {
        return fliegendekarte;
    }
}
