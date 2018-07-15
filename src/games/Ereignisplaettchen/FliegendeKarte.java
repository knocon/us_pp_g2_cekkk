package games.Ereignisplaettchen;

import java.util.ArrayList;

import games.Zauberberg.*;

public class FliegendeKarte extends Feld {
    
    public FliegendeKarte(int layer, int feldNr, ArrayList<Kobold> list) {
	super(layer,feldNr,list);
    }    
    
    public static void execute (Spieler s, Kartenstapel kartenstapel ) {
    	int stapelSize = kartenstapel.getStapel().size()-1;
    	int random = (int )(Math.random() * stapelSize + 0);
    	s.getHand().add(kartenstapel.getStapel().get(random));
    	kartenstapel.getStapel().remove(random);
    }
    
    public String getClassName() {
	return "FliegendeKarte"; 
    }


}
