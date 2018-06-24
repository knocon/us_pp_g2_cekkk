package games.Zauberberg;

public class Bewegungskarten {
	private int bewegungsZahl;
	//If Joker == true, dann kann Zahl selbst gewaehlt werden
	private Boolean joker;
	
	
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
