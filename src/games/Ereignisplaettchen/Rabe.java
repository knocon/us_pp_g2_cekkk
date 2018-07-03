package games.Ereignisplaettchen;
import java.util.ArrayList;
import games.Zauberberg.*;


public class Rabe extends Feld { 
    
    public Rabe(int layer, int feldNr, ArrayList<Kobold> list) {
	super(layer,feldNr,list); 
    }
    

    //Spieler bestimmt Spieler, der einen Zauberstein abgeben muss
    public static void execute(Spieler s) {
	s.setAnzahlZaubersteine(s.getAnzahlZaubersteine()-1);
    }
}
