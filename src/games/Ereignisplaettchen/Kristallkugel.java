package games.Ereignisplaettchen;
import java.util.ArrayList;
import games.Zauberberg.*;

public class Kristallkugel extends Feld {
    
    private boolean aufgedeckt;
    
    //Keine Logik n�tig, ausser das sobald aufgedeckt auf true wird, die Karte vom Spielfeld verschwinden muss
        
    public Kristallkugel(int layer, int feldNr, ArrayList<Kobold> list) {
	super(layer, feldNr, list); 
    }
    
    public boolean isAufgedeckt() {
		return aufgedeckt;
    }
    
    public void setAufgedeckt(boolean aufgedeckt) {
	this.aufgedeckt = aufgedeckt;
    }
    
    public String getClassName() {
	return "Kristallkugel"; 
    }
    
}