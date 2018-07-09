package games.Zauberberg;

import java.util.ArrayList;

public class Feld {
    private int layer; 
    private int feldNr; 
    private ArrayList<Kobold> kobolde;  
    //private boolean zauberstein;
    //private boolean ereignisplaettchen;
   
    
    public Feld(int layer, int feldNr, ArrayList<Kobold> kobolde) {
	this.layer =layer; 
	this.feldNr = feldNr;
	this.kobolde = kobolde; 
	//this.zauberstein = zauberstein; 
    }    
    
    
    //TODO Ereignisplättchen 
    public void ereignis() {
  
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
      

}

