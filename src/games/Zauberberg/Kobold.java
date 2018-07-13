package games.Zauberberg;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



public class Kobold {
    private int nummer;
    private Spieler.Name farbe;
    private int layer; 
    private int feldNr;
    private int globalFeld; //DER INDEX DES FELDES IN ARRAYLIST felder AUS KLASSE SPIEL
    private int dorf; 
    private Spieler spieler; 
    private Zauberberg zauberberg;
    private ArrayList<Integer> kartenWerte = new ArrayList<Integer>(); 
    Gson gson = new GsonBuilder().create();
	
    //Methoden bewegen
    //TODO Position im Grid
    public Kobold(int nummer, Spieler spieler) {
	this.dorf = (int)(Math.random() * 4); //generate random dorf between in a range 0...3
	this.layer = -1; 
	this.feldNr = -1;
	this.spieler = spieler; 
	this.nummer = nummer; 
    }
    
    public void kartenLegen(int kartenWert1, int kartenWert2) {
	this.kartenWerte.add(kartenWert1);
	this.kartenWerte.add(kartenWert2);
	this.karteSpielen(); 
    }
    public void karteSpielen() {
	int laufweg = this.kartenWerte.get(0); 
	ArrayList<Integer> arrayLayerFeld = new ArrayList<Integer>();
	if(this.getLayer()==-1) {    	    //Schritt raus aus dem Dorf 
    	    if(this.getDorf()==0) {
    	    	arrayLayerFeld.add(0); //layer 
    	    	arrayLayerFeld.add( ((0+(laufweg-1) % 36)+36)%36); //schritte nach "vorne"
    	    	arrayLayerFeld.add(0); //layer 
    	    	arrayLayerFeld.add(((0-(laufweg-1)%36)+36)%36); // schritte nach "hinten"	    		
    	    } else if (this.getDorf()==1) {
    	    	arrayLayerFeld.add(0); //layer     	    	
    	    	arrayLayerFeld.add((((9+(laufweg-1))%36)+36)%36); //schritte nach "vorne"
    	    	arrayLayerFeld.add(0); //layer 
    	    	arrayLayerFeld.add((((9-(laufweg-1))%36)+36)%36); // schritte nach "hinten"
    	    } else if (this.getDorf() ==2) {
    	    	arrayLayerFeld.add(0); //layer    	    	
    	    	arrayLayerFeld.add((((18+(laufweg-1))%36)+36)%36); //schritte nach "vorne"
   	    	arrayLayerFeld.add(0); //layer 
    	    	arrayLayerFeld.add((((18-(laufweg-1))%36)+36)%36); // schritte nach "hinten"
    	    } else if(this.getDorf()==3) {
    	    	arrayLayerFeld.add(0); //layer 
    	    	arrayLayerFeld.add((((27+(laufweg-1))%36)+36)%36); //schritte nach "vorne"
    	    	arrayLayerFeld.add(0); //layer 
    	    	arrayLayerFeld.add((((27-(laufweg-1))%36)+36)%36); // schritte nach "hinten"
    	    }
	}else if(this.getLayer()==0) {	    
	    arrayLayerFeld.add((((this.getFeldNr()+laufweg)%36)+36)%36);  //vorw�rts
	    arrayLayerFeld.add((((this.getFeldNr()-laufweg)%36)+36)%36); // r�ckw�rts	    
	}else if(this.getLayer()==1) {                	 
	    arrayLayerFeld.add((((this.getFeldNr()+laufweg)%28)+28)%28); //vorw�rts
	    arrayLayerFeld.add((((this.getFeldNr()-laufweg)%28)+28)%28);  // r�ckw�rts
	} else if(this.getLayer()==2) {                	 
       	    arrayLayerFeld.add((((this.getFeldNr()+laufweg)%20)+20)%20);  //vorw�rts
            arrayLayerFeld.add((this.getFeldNr()-laufweg) %20); // r�ckw�rts
       	} else if(this.getLayer()==3) {                  	 
       	    arrayLayerFeld.add((((this.getFeldNr()+laufweg)%12)+12)%12);  //vorw�rts
       	    arrayLayerFeld.add((((this.getFeldNr()-laufweg)%12)+12)%12);  // r�ckw�rts
       	}  
	this.zauberberg.setFelderWaehlen(gson.toJson(arrayLayerFeld, ArrayList.class));
	this.zauberberg.sendGameDataToUserPublic("FELDERANBIETEN");
	kartenWerte.remove(0); 	
    }
    public void bewegen(int layer, int feldNr) {
	//prüfe ob Zauberstein oder so ein Kram
	this.setFeldNr(feldNr);
	this.setLayer(layer);
	
	if(this.kartenWerte.size()!=0) {
	    this.karteSpielen();
	}
    }
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
    public int getPos() {
	return feldNr;
    }
    public void setPos(int pos) {
	this.feldNr = pos;
    }
    public int getFeldNr() {
	return feldNr;
    }
    public void setFeldNr(int feldNr) {
	this.feldNr = feldNr;
    }
    public Spieler getSpieler() {
	return spieler;
    }
    public void setSpieler(Spieler spieler) {
	this.spieler = spieler;
    }
    public int getDorf() {
	return dorf;
    }
    public void setDorf(int dorf) {
	this.dorf = dorf;
    }
    public Zauberberg getZauberberg() {
	return zauberberg;
    }
    public void setZauberberg(Zauberberg zauberberg) {
	this.zauberberg = zauberberg;
    }
    public Spieler.Name getFarbe() {
	return farbe;
    }
    public void setFarbe(Spieler.Name farbe) {
	this.farbe = farbe;
    }
    public int getGlobalFeld() {
	return globalFeld;
    }
    public void setGlobalFeld(int globalFeld) {
	this.globalFeld = globalFeld;
    }

    public ArrayList<Integer> getKarten() {
        return kartenWerte;
    }
    public void setKarten(ArrayList<Integer> karten) {
        this.kartenWerte = karten;
    }
    
}