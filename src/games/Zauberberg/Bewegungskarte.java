package games.Zauberberg;

public class Bewegungskarte {
	private int bewegungsZahl;
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
