package games.Zauberberg;

import java.util.ArrayList;

public class Feld {
    private int layer; 
    private int position; 
    private ArrayList<Kobold> kobolde;  
    private boolean zauberstein;  
    //TODO Ereignisplättchen 
    
    public Feld(int layer, int pos, ArrayList<Kobold> kobolde, boolean zauberstein) {
	this.layer =layer; 
	this.position = pos;
	this.kobolde = kobolde; 
	this.zauberstein = zauberstein; 
    }    
    
    public ArrayList<Kobold> getKobolde() {
        return kobolde;
    }


    public void setKobolde(ArrayList<Kobold> kobolde) {
        this.kobolde = kobolde;
    }    
    public boolean isZauberstein() {
        return zauberstein;
    }
    public void setZauberstein(boolean zauberstein) {
        this.zauberstein = zauberstein;
    }
    public int getLayer() {
        return layer;
    }


    public void setLayer(int layer) {
        this.layer = layer;
    }


    public int getPosition() {
        return position;
    }


    public void setPosition(int position) {
        this.position = position;
    }
      

}

