package games.Ereignisplaettchen;

import java.util.ArrayList;

import games.Zauberberg.*;

public class Schreckgespenst extends Feld {
    private boolean karteAufgedeckt;

    public Schreckgespenst(int layer, int feldNr, Zauberberg zauberberg) {
        super(layer, feldNr, zauberberg);
        this.karteAufgedeckt = false;
    }

    //TODO Logik des Ereignisses
    public void execute(Kobold kobold) {
        kobold.setLayer(-1);
        kobold.setFeldNr(kobold.getDorf());
        setKarteAufgedeckt(false);
    }

    public String getClassName() {
        return "Schreckgespenst";
    }

    public boolean isKarteAufgedeckt() {
        return karteAufgedeckt;
    }

    public void setKarteAufgedeckt(boolean karteAufgedeckt) {
        this.karteAufgedeckt = karteAufgedeckt;
        this.zauberberg.sendGameDataToClientsPublic("UPDATESPIELFELD");
    }
}
