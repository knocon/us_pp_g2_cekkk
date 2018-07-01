package games.Zauberberg;

public class Kobold {
	private int nummer;
	private Farbe farbe;
	//private int [] position = new int [2];  //[layer, position]
	private int layer; 
	private int feldNr; 
	private Spieler spieler; 
	
	//TODO Ereignisplaettchen von Feld erben Lassen
	//TODO Feldklasse mit Positionsarray, Array mit moeglichen Kobolden, Ereignisplaettchen mit richtigem Ereignis, Zauberstein
	//TODO Spieler wegen Steinen, Karten, Kobolden + Farbe
	//TODO Position jeder Ereigniskarte per Oberklasse Ereignisplaettchenstapel mit Shuffle Methode
	//TODO Attribut Aufgedeckt jede Kartenklasse
	//TODO Position auf Layer per Array
	
	//Methoden bewegen
	//TODO Position im Grid
	public Kobold() {
	    //TODO
	}
	public enum Farbe {
		Schwarz, Rot, Gold, Blau, Gruen
	    }
	
	public void move(int value) {
	    this.feldNr += value; 
	}
	
		
	public int getNummer() {
		return nummer;
	}
	/*public int[] getPosition() {
	    return position;
	}
	public void setPosition(int[] position) {
	    this.position = position;
	}*/
	public void setNummer(int nummer) {
		this.nummer = nummer;
	}
	
	public int getLayer() {
	    return layer;
	}
	public void setLayer(int layer) {
	    this.layer = layer;
	}
	public int getPos() {
	    return feldNr;
	}
	public void setPos(int pos) {
	    this.feldNr = pos;
	}
	
}
