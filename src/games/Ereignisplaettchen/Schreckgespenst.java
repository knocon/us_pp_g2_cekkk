package games.Ereignisplaettchen;

import java.util.ArrayList;

import games.Zauberberg.*;

public class Schreckgespenst extends Feld{ 
    
    public Schreckgespenst(int layer, int feldNr, ArrayList<Kobold> list) {
	super(layer,feldNr,list);  
    }
	//TODO Logik des Ereignisses
    public static void execute (Kobold kobold) {
    	//Verteilung auf Doerfer random, da einfacher
    	int random = (int )(Math.random() * 4 + 0);
    	kobold.setLayer(-1);
	    kobold.setPos(random);	
    }
    
    public String getClassName() {
	return "Schreckgespenst"; 
    }
}
