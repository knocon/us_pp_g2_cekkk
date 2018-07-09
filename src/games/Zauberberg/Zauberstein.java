package games.Zauberberg;

import java.util.ArrayList;

//import games.Zauberberg.Kobold.Farbe;

public class Zauberstein extends Feld {
    		
	private Boolean aufFeld;
	
	public Zauberstein(int layer, int feldNr) {
		super(layer,feldNr,null); 
		aufFeld = true; 
	}
	
	/*//TODO Richtiges Positionsarray einfuegen
	 * 
	 * 
	
	
	
	
	*/
	public Boolean getAufFeld() {
	    return aufFeld;
	}
	public void setAufFeld(Boolean aufFeld) {
	    this.aufFeld = aufFeld;
	}



	
	
}
