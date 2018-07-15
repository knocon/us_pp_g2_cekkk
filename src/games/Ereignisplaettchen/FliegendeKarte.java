package games.Ereignisplaettchen;

import java.util.ArrayList;

import games.Zauberberg.*;

public class FliegendeKarte extends Feld {
    private boolean karteAufgedeckt;

    public FliegendeKarte(int layer, int feldNr, ArrayList<Kobold> list, Zauberberg zauberberg) {
        super(layer, feldNr, list, zauberberg);
        this.karteAufgedeckt = false;
    }

    public void execute(Spieler s, Kartenstapel kartenstapel) {
        int stapelSize = kartenstapel.getStapel().size() - 1;
        int random = (int) (Math.random() * stapelSize + 0);
        s.getHand().add(kartenstapel.getStapel().get(random));
        kartenstapel.getStapel().remove(random);
        setKarteAufgedeckt(false);
    }

    public String getClassName() {
        return "FliegendeKarte";
    }

    public boolean isKarteAufgedeckt() {
        return karteAufgedeckt;
    }

    public void setKarteAufgedeckt(boolean karteAufgedeckt) {
        this.karteAufgedeckt = karteAufgedeckt;
        this.zauberberg.sendGameDataToClientsPublic("UPDATESPIELFELD");
    }
}
