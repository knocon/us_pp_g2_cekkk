package games.Zauberberg;

import java.util.ArrayList;

import games.Zauberberg.Kobold.Farbe;

public class Zauberstein extends Feld {
    		
	private Boolean aufFeld;
	
	public Zauberstein(int layer, int feldNr) {
		super(layer,feldNr,null);
		aufFeld = true; 
	}
	
	/*//TODO Richtiges Positionsarray einfuegen
	Zauberstein zStein0 = new Zauberstein(1, 1, position );
	Zauberstein zStein1 = new Zauberstein(1, 19, position);
	Zauberstein zStein2 = new Zauberstein(2, 3, position);
	Zauberstein zStein3 = new Zauberstein(2, 10, position);
	Zauberstein zStein4 = new Zauberstein(2, 19, position);
	Zauberstein zStein5 = new Zauberstein(3, 0, position);
	Zauberstein zStein6 = new Zauberstein(3, 4, position);
	Zauberstein zStein7 = new Zauberstein(3, 8, position);
	//TODO 9. Zauberstein auf Layout
	//Zauberstein zStein8 = new Zauberstein(feldNr, feldNr, position);
	*/
	public Boolean getAufFeld() {
	    return aufFeld;
	}
	public void setAufFeld(Boolean aufFeld) {
	    this.aufFeld = aufFeld;
	}



	
	
}
