package games.Zauberberg;

import java.util.ArrayList;

import games.Zauberberg.Kobold.Farbe;

public class Zauberstein  {

	//TODO Vererbung von Feld
	
	
	
	
	//TODO Position auf Spielfeld in Klasse einbringen
	private int layer; 
	private int feldNr;
	private int [] position = {layer, feldNr};  //[layer, position]
	
	private Boolean aufFeld;


	public Zauberstein(int layer, int feldNr, int[] position) {
		super();
		this.layer = layer;
		this.feldNr = feldNr;
		this.position = position;
	}
	
	//TODO Richtiges Positionsarray einfuegen
	Zauberstein zStein0 = new Zauberstein(1, 1, position );
	Zauberstein zStein1 = new Zauberstein(1, 19, position);
	Zauberstein zStein2 = new Zauberstein(2, 3, position);
	Zauberstein zStein3 = new Zauberstein(2, 10, position);
	Zauberstein zStein4 = new Zauberstein(2, 19, position);
	Zauberstein zStein5 = new Zauberstein(3, 0, position);
	Zauberstein zStein6 = new Zauberstein(3, 4, position);
	Zauberstein zStein7 = new Zauberstein(3, 8, position);
	//TODO 9. Zauberstein auf Layout
	//Zauberstein zStein8 = new Zauberstein(feldNr, feldNr, position);



	public int getLayer() {
		return layer;
	}
	public void setLayer(int layer) {
		this.layer = layer;
	}
	public int getFeldNr() {
		return feldNr;
	}
	public void setFeldNr(int feldNr) {
		this.feldNr = feldNr;
	}
	public int[] getPosition() {
		return position;
	}
	public void setPosition(int[] position) {
		this.position = position;
	}
	public Boolean getAufFeld() {
		return aufFeld;
	}
	public void setAufFeld(Boolean aufFeld) {
		this.aufFeld = aufFeld;
	}
	
}
