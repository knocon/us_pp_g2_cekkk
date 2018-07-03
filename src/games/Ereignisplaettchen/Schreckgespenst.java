package games.Ereignisplaettchen;

import java.util.ArrayList;

import games.Zauberberg.*;

public class Schreckgespenst extends Feld{ 
    
    public Schreckgespenst(int layer, int feldNr, ArrayList<Kobold> list) {
	super(layer,feldNr,list); 
    }
	//TODO Logik des Ereignisses
    public static void execute (Kobold kobold) {
	kobold.setLayer(-1);
	//kobold.setPos(//random Dorf 1..4);
	
    }
}
