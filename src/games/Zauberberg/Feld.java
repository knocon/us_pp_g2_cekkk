package games.Zauberberg;

import java.util.ArrayList;

public class Feld {
    private int layer;
    private int feldNr;
    private ArrayList<Kobold> kobolde;
    protected Zauberberg zauberberg;
    //private boolean zauberstein;
    //private boolean ereignisplaettchen;


    public Feld(int layer, int feldNr, ArrayList<Kobold> kobolde, Zauberberg zauberberg) {
        this.layer = layer;
        this.feldNr = feldNr;
        this.kobolde = kobolde;
        //this.zauberstein = zauberstein;
        this.zauberberg = zauberberg;
    }


    //TODO Ereignispl�ttchen 
    public void ereignis() {
    }

    public String getClassName() {
        return "Feld";
    }


    public ArrayList<Kobold> getKobolde() {
        return kobolde;
    }

    public void setKobolde(ArrayList<Kobold> kobolde) {
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

