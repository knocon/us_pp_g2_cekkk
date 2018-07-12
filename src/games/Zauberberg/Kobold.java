package games.Zauberberg;

import java.util.ArrayList;

public class Kobold {
	private int nummer;
	private Spieler.Name farbe;
	private int layer; 
	private int feldNr;
	private int globalFeld; //DER INDEX DES FELDES IN ARRAYLIST felder AUS KLASSE SPIEL
	private int dorf; 
	private Spieler spieler; 
	private Zauberberg zauberberg;
	
	//Methoden bewegen
	//TODO Position im Grid
	public Kobold(int nummer, Spieler spieler) {
	    this.dorf = (int)(Math.random() * 4); //generate random dorf between in a range 0...3
	    this.layer = -1; 
	    this.feldNr = -1;
	    this.spieler = spieler; 
	    this.nummer = nummer; 
	}
	
	
	//GIBT EIN ARRAY MIT INTWERTEN ZURUECK, WO DER KOBOLD SICH HINBEWEGEN DARF
	//FORM -> (layer,feld,layer,feld..) 
	public ArrayList<Integer> moeglFelder(Kobold kobold, int laufweg) {
		ArrayList<Integer> arrayLayerFeld = new ArrayList<Integer>();
			if(kobold.getLayer()==-1) {
	    	    //Schritt raus aus dem Dorf 
	    	    if(kobold.getDorf()==1) {
	    	    	arrayLayerFeld.add(0); //layer 
	    	    	arrayLayerFeld.add((laufweg-1) % 36); //schritte nach "vorne"
	    	    	arrayLayerFeld.add(0); //layer 
	    	    	arrayLayerFeld.add(((-(laufweg))-1) % 36); // schritte nach "hinten"
	    		//arrayLayerFeld.add(1); 
	    		//arrayLayerFeld.add(Integer.parseInt(dataMap.get("karte2Wert"))-1 % 36); 
	    		//Fragen wie in dieser Form < <layer, feld>,<layer, feld>,<layer, feld>,....> glaub nicht m?glich ohne vorher anzahl von inneren arrays zu kennen
	    	    } else if (kobold.getDorf()==2) {
	    	    	arrayLayerFeld.add(0); //layer 
	    	    	arrayLayerFeld.add((9+(laufweg)-1) % 36); //schritte nach "vorne"
	    	    	arrayLayerFeld.add(0); //layer 
	    	    	arrayLayerFeld.add((9-((laufweg)-1)) % 36); // schritte nach "hinten"
	    	    } else if (kobold.getDorf() ==3) {
	    	    	arrayLayerFeld.add(0); //layer 
	    	    	arrayLayerFeld.add((18+((laufweg)-1)) % 36); //schritte nach "vorne"
	    	    	arrayLayerFeld.add(0); //layer 
	    	    	arrayLayerFeld.add((18-((laufweg)-1)) % 36); // schritte nach "hinten"
	    	    } else if(kobold.getDorf()==4) {
	    	    	arrayLayerFeld.add(0); //layer 
	    	    	arrayLayerFeld.add((27+((laufweg)-1)) % 36); //schritte nach "vorne"
	    	    	arrayLayerFeld.add(0); //layer 
	    	    	arrayLayerFeld.add((27-((laufweg)-1)) % 36); // schritte nach "hinten"
	    	    }                	    
		    }else if(kobold.getLayer()==0) {    
		    	arrayLayerFeld.add((kobold.getFeldNr()+laufweg) %36);  //vorw�rts
		    	arrayLayerFeld.add((kobold.getFeldNr()-laufweg) %36); // r�ckw�rts
		    	//arrayLayerFeld.add((((Integer.parseInt(dataMap.get("karte3Wert"))))) % 36); //vorw�rts
		    	//arrayLayerFeld.add((-((Integer.parseInt(dataMap.get("karte3Wert"))))) % 36); // r�ckw�rts
		    }else if(kobold.getLayer()==1) {                	 
		    	arrayLayerFeld.add((kobold.getFeldNr()+laufweg) %28); //vorw�rts
		    	arrayLayerFeld.add((kobold.getFeldNr()-laufweg) %28);  // r�ckw�rts
		    	//arrayLayerFeld.add((((Integer.parseInt(dataMap.get("karte3Wert"))))) % 28); //vorw�rts
		    	//arrayLayerFeld.add((-((Integer.parseInt(dataMap.get("karte3Wert"))))) % 28); // r�ckw�rts
		   	} else if(kobold.getLayer()==2) {                	 
	       	    arrayLayerFeld.add((kobold.getFeldNr()+laufweg) %20);  //vorw�rts
	            arrayLayerFeld.add((kobold.getFeldNr()-laufweg) %20); // r�ckw�rts
	       	    //arrayLayerFeld.add((((Integer.parseInt(dataMap.get("karte3Wert"))))) % 20); //vorw�rts
	       	    //arrayLayerFeld.add((-((Integer.parseInt(dataMap.get("karte3Wert"))))) % 20); // r�ckw�rts
	       	} else if(kobold.getLayer()==3) {                  	 
	       	    arrayLayerFeld.add((kobold.getFeldNr()+laufweg) %12);  //vorw�rts
	       	    arrayLayerFeld.add((kobold.getFeldNr()-laufweg) %12);  // r�ckw�rts
	       	    //arrayLayerFeld.add((((Integer.parseInt(dataMap.get("karte3Wert"))))) % 12); //vorw�rts
	       	    //arrayLayerFeld.add((-((Integer.parseInt(dataMap.get("karte3Wert"))))) % 12); // r�ckw�rts
	       	}  
		return arrayLayerFeld;                  
	}
	
	//Idee ist, dass die Methode aufgerufen wird nachdem man weiss, auf welches Feld man will.
	public void bewegen(Kobold kobold, int tempLayer, int tempFeldNr, Feld feld) {
		//Bewegung nicht moeglich da einer auf dem Kobold steht	
		if(feld.getKobolde().get(0)==kobold && feld.getKobolde().size()>1) {
		//sollte nicht auftreten, da nur Felder 
		}	
		//Bewegung moeglich aber kein Layerwechsel
		else if(feld.getKobolde().size()==1) {
			kobold.setLayer(tempLayer);
			kobold.setFeldNr(tempFeldNr);
		//Bewegung moeglich MIT LAYERWECHSEL
		}else if(feld.getKobolde().size()>1 && feld.getKobolde().get(1) == kobold) {
			kobold.setLayer(tempLayer);
			kobold.setFeldNr(tempFeldNr);			
		}
						
			
	}
	
	public boolean darfBewegen(Kobold kobold, Feld tempFeld) {
		if(tempFeld.getKobolde().get(0)==kobold && tempFeld.getKobolde().size()>1) {
			//TODO ANWEISUNG WAS PASSIEREN SOLL, WENN DER KOBOLD SICH NICHT BEWEGEN DARF
			return false;
		}else {
			return true;
		}
	}
	
	
	
	
	
	
	/*
	public enum Farbe {
		Schwarz, Rot, Gold, Blau, Gruen
	}
	*/ 
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
	
}
