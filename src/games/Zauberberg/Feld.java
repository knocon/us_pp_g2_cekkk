package games.Zauberberg;

import java.util.ArrayList;

public class Feld {
    private int position[] = new int[2]; //[layer, position]
    private ArrayList<Kobold> kobolde;  
    private boolean zauberstein;  
    //TODO Ereignisplättchen 
    
    public Feld(int[] pos, ArrayList<Kobold> kobolde, boolean zauberstein) {
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


    public int[] getPosition() {
        return position;
    }    
    public void setPosition(int[] position) {
        this.position = position;
    }
    public boolean isZauberstein() {
        return zauberstein;
    }
    public void setZauberstein(boolean zauberstein) {
        this.zauberstein = zauberstein;
    }
      

}

