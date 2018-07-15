package games.Ereignisplaettchen;

import java.util.ArrayList;

import games.Zauberberg.*;

public class Unwetter extends Feld {
    
    private Boolean aufgedeckt;
	
    public Unwetter(int layer, int feldNr, ArrayList<Kobold> list) {
	super(layer,feldNr,list);  
    }
    
    //Keine Javalogik noetig
    //Wenn aufgedeckt auf True wird, Spielfeld anzeige drehen. Muss nichtmal auf Javaebene runter gehen
    //Auf Fairplay Basis (wie besprochen) alle Karten selbst umdrehen

    public Boolean getAufgedeckt() {
	return aufgedeckt;
    }
    
    public void setAufgedeckt(Boolean aufgedeckt) {
	this.aufgedeckt = aufgedeckt;
    }
    
    public String getClassName() {
	return "Unwetter"; 
    }
}
