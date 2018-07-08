package games.Zauberberg;

import java.util.Iterator;

public class Bewegungskarte {
	private int bewegungsZahl;
	
	
	
	public static void checkJoker(Spieler s, int zahl) {
		Iterator<Bewegungskarte> it = s.getHand().iterator(); 
		
		while (it.hasNext()) {
			
			
			Bewegungskarte tmp = it.next(); 
			
			if(tmp.joker == true) {
				
				// Hier der Teil mit der Zahl. 
				
				
				
			}else {
				 
			}
			
			
			
		}
		
		
		
	}
	
	//If Joker == true, dann kann Zahl selbst gewaehlt werden
	private Boolean joker;
	
	public Bewegungskarte(int value, boolean joker) {
	    this.bewegungsZahl = value; 
	    this.joker = joker; 
	}
	
	public int getBewegungsZahl() {
		return bewegungsZahl;
	}
	public void setBewegungsZahl(int bewegungsZahl) {
		this.bewegungsZahl = bewegungsZahl;
	}
	public Boolean getJoker() {
		return joker;
	}
	public void setJoker(Boolean joker) {
		this.joker = joker;
	}
}
