package games.Zauberberg;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Feld {
    private int layer;
    private int feldNr;
    private ObservableList<Kobold> kobolde;
    protected Zauberberg zauberberg;

    public Feld(int layer, int feldNr, Zauberberg zauberberg) {
        this.kobolde = FXCollections.observableArrayList();
        kobolde.addListener((ListChangeListener<Kobold>) c -> zauberberg.sendGameDataToClientsPublic("UPDATESPIELFELD"));
        this.layer = layer;
        this.feldNr = feldNr;
        this.zauberberg = zauberberg;
    }


    //TODO Ereignispl�ttchen 
    public void ereignis() {
    }

    public String getClassName() {
        return "Feld";
    }


    public ObservableList<Kobold> getKobolde() {
        return kobolde;
    }

    public void setKobolde(ObservableList<Kobold> kobolde) {
        this.kobolde = kobolde;
    }

    /*public boolean isZauberstein() {
        return zauberstein;
    }
    public void setZauberstein(boolean zauberstein) {
        this.zauberstein = zauberstein;
    }*/
    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getFeldNr() {
        return feldNr;
    }

    public void setFeldNr(int position) {
        this.feldNr = position;
    }

    public boolean isKarteAufgedeckt() {
        return false;
    }

    public void setKarteAufgedeckt(boolean karteAufgedeckt) {
    }
}

