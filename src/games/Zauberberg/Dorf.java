package games.Zauberberg;


public class Dorf {
	
	//Oben links Dorf 0, Oben Rechts Dorf 1, Unten Rechts Dorf 2, Unten Links Dorf 3
	private int nummer;
	
	private int layer; 
	private int feldNr;
	private int [] position = {layer, feldNr};  //[layer, position]
	
	
	
	
	
	public int getNummer() {
		return nummer;
	}
	public void setNummer(int nummer) {
		this.nummer = nummer;
	}
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
	
}
