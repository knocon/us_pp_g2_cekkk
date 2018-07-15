package games.Zauberberg;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import games.Ereignisplaettchen.*;



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
    Feld tempFeld; 
    Gson gson = new GsonBuilder().create();
    private static int anz; 
	
    //Methoden bewegen
    //TODO Position im Grid
    public Kobold(int nummer, Spieler spieler) {
	this.dorf = anz % 4; //gleichmäßiges Verteilen der Kobolde (im Uhrzeigersinn)
	this.layer = -1; 
	this.feldNr = this.dorf;
	this.spieler = spieler; 
	this.nummer = nummer;	
	this.farbe = spieler.getFarbName();
	anz++; 
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
    	    if(this.getFeldNr()==0) {
    	    	arrayLayerFeld.add(0); //layer 
    	    	arrayLayerFeld.add( ((0+(laufweg-1) % 36)+36)%36); //schritte nach "vorne"
    	    	arrayLayerFeld.add(0); //layer 
    	    	arrayLayerFeld.add(((0-(laufweg-1)%36)+36)%36); // schritte nach "hinten"	    		
    	    } else if (this.getFeldNr()==1) {
    	    	arrayLayerFeld.add(0); //layer     	    	
    	    	arrayLayerFeld.add((((9+(laufweg-1))%36)+36)%36); 
    	    	arrayLayerFeld.add(0); //layer 
    	    	arrayLayerFeld.add((((9-(laufweg-1))%36)+36)%36);  
    	    } else if (this.getFeldNr() ==2) {
    	    	arrayLayerFeld.add(0); //layer    	    	
    	    	arrayLayerFeld.add((((18+(laufweg-1))%36)+36)%36);
   	    	arrayLayerFeld.add(0); //layer 
    	    	arrayLayerFeld.add((((18-(laufweg-1))%36)+36)%36);
    	    } else if(this.getFeldNr()==3) {
    	    	arrayLayerFeld.add(0); //layer 
    	    	arrayLayerFeld.add((((27+(laufweg-1))%36)+36)%36);
    	    	arrayLayerFeld.add(0); //layer 
    	    	arrayLayerFeld.add((((27-(laufweg-1))%36)+36)%36);
    	    }
	}else if(this.getLayer()==0) {	
	    //prüft ob Kobold auf einem Kobold sitzt, wenn ja nächste Ebene möglich 
	    for(Feld f : zauberberg.getSpiel().getFelder()) {
		if(f.getLayer() == 0 && f.getFeldNr()==this.getFeldNr()){
		    if(f.getKobolde().size()>1 && f.getKobolde().get(1).equals(this)) {
			arrayLayerFeld.add(1); 
			arrayLayerFeld.add(this.getFeldNrNextLayer(0, this.feldNr)); 			
		    }
		    break;
		}
	    }	    
	    arrayLayerFeld.add((((this.getFeldNr()+laufweg)%36)+36)%36);  //vorw�rts
	    arrayLayerFeld.add((((this.getFeldNr()-laufweg)%36)+36)%36); // r�ckw�rts	    
	}else if(this.getLayer()==1) {   
	    for(Feld f : zauberberg.getSpiel().getFelder()) {
		if(f.getLayer() == 1 && f.getFeldNr()==this.getFeldNr()){
		    if(f.getKobolde().size()>1 && f.getKobolde().get(1).equals(this)) {
			arrayLayerFeld.add(2); 
			arrayLayerFeld.add(this.getFeldNrNextLayer(1, this.feldNr)); 			
		    }
		    break;
		}
	    }	    
	    arrayLayerFeld.add((((this.getFeldNr()+laufweg)%28)+28)%28); //vorw�rts
	    arrayLayerFeld.add((((this.getFeldNr()-laufweg)%28)+28)%28);  // r�ckw�rts
	} else if(this.getLayer()==2) {  
	    for(Feld f : zauberberg.getSpiel().getFelder()) {
		if(f.getLayer() == 2 && f.getFeldNr()==this.getFeldNr()){
		    if(f.getKobolde().size()>1 && f.getKobolde().get(1).equals(this)) {
			arrayLayerFeld.add(3); 
			arrayLayerFeld.add(this.getFeldNrNextLayer(2, this.feldNr)); 			
		    }
		    break;
		}
	    }
       	    arrayLayerFeld.add((((this.getFeldNr()+laufweg)%20)+20)%20);  //vorw�rts
            arrayLayerFeld.add((((this.getFeldNr()-laufweg)%20)+20)%20); // r�ckw�rts
       	} else if(this.getLayer()==3) {   
       	for(Feld f : zauberberg.getSpiel().getFelder()) {
		if(f.getLayer() == 3 && f.getFeldNr()==this.getFeldNr()){
		    if(f.getKobolde().size()>1 && f.getKobolde().get(1).equals(this)) {
			arrayLayerFeld.add(4); 
			arrayLayerFeld.add(this.getFeldNrNextLayer(3, this.feldNr)); 			
		    }
		    break;
		}
	    }
       	    arrayLayerFeld.add((((this.getFeldNr()+laufweg)%12)+12)%12);  //vorw�rts
       	    arrayLayerFeld.add((((this.getFeldNr()-laufweg)%12)+12)%12);  // r�ckw�rts
       	}  
	if(arrayLayerFeld.size()==0) {
	    this.zauberberg.setRecentInfoText("Du kannst den Kobold mit der Nummer " + this.getNummer() + " nicht bewegen.");
	    this.zauberberg.sendGameDataToUserPublic("PUSHINFOTEXT"); 
	} else {
	this.zauberberg.setFelderWaehlen(gson.toJson(arrayLayerFeld, ArrayList.class));
	this.zauberberg.sendGameDataToUserPublic("FELDERANBIETEN");
	kartenWerte.remove(0); 	
	}
    }
    public void bewegen(int layer, int feldNr) {
	//vom alten Feld entfernen
	for(Feld f : zauberberg.getSpiel().getFelder()) {
	    if(f.getLayer()==this.getLayer() && f.getFeldNr()==this.getFeldNr()) {
		f.getKobolde().remove(this);
		break; 
	    }
	}
	//auf neues Feld setzen
	this.setFeldNr(feldNr);
	this.setLayer(layer);
		
	for(Feld f : zauberberg.getSpiel().getFelder()) {
	    if(f.getLayer()==layer && f.getFeldNr()==feldNr) {
		switch(f.getClassName()) {
		case "Fallgrube": 
		    Fallgrube.execute(this);
		    break; 
		case "Geheimgang": 
		    Geheimgang.execute(this);
		    break; 
		case "Schreckgespenst": 
		    Schreckgespenst.execute(this);
		    break; 
		case "Zauberstein": 
		    this.getSpieler().setAnzahlZaubersteine(this.getSpieler().getAnzahlZaubersteine()+1);
		    break; 
		case "Rabe": 
		    Rabe.execute(this.getSpieler());
		    break; 
		//case Fliegende Karte & Kristallkugel & Unwetter fehlen hier noch  
		}
		//an Liste auf neuer Pos einfügen
		for(Feld feld : zauberberg.getSpiel().getFelder()) {
		    if(feld.getLayer()==this.getLayer() && feld.getFeldNr()==this.getFeldNr()) {
			feld.getKobolde().add(this); 
			break; 
		    }
		}
		break;
	    }
	}
	if(this.kartenWerte.size()!=0) {
	    this.karteSpielen();
	}
    }
    
    public int getFeldNrNextLayer(int layer, int feldNr) {
	switch(layer) {
	case 0: 
	    if(feldNr>0 && feldNr<9) {
		return feldNr-1; 
	    }
	    if(feldNr>9 && feldNr<18) {
		return feldNr-3; 
	    }
	    if(feldNr>18 && feldNr<27) {
		return feldNr-5; 
	    }
	    if(feldNr>27 && feldNr<36) {
		return feldNr-1; 
	    }
	    break; 
	case 1: 
	    if(feldNr>0 && feldNr<7) {
		return feldNr-1; 
	    }
	    if(feldNr>7 && feldNr<14) {
		return feldNr-3; 
	    }
	    if(feldNr>14 && feldNr<21) {
		return feldNr-5; 
	    }
	    if(feldNr>21 && feldNr<28) {
		return feldNr-7; 
	    }
	    break; 
	case 2: 
	    if(feldNr>0 && feldNr<5) {
		return feldNr-1; 
	    }
	    if(feldNr>5 && feldNr<10) {
		return feldNr-3; 
	    }
	    if(feldNr>10 && feldNr<15) {
		return feldNr-5; 
	    }
	    if(feldNr>15 && feldNr<20) {
		return feldNr-7; 
	    }
	    break;
	case 3: 
	    if(feldNr>0 && feldNr <3) {
		return feldNr-1;
	    }
	    if(feldNr>3 && feldNr <6) {
		return feldNr-3;
	    }if(feldNr>6 && feldNr <9) {
		return feldNr-5;
	    }if(feldNr>9 && feldNr <12) {
		return feldNr-7;
	    }
	    break;
	} 
	return 0; 
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