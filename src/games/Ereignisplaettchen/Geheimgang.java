package games.Ereignisplaettchen;

import java.util.ArrayList;

import games.Zauberberg.*;

public class Geheimgang extends Feld {
    private boolean karteAufgedeckt;

    public Geheimgang(int layer, int feldNr, Zauberberg zauberberg) {
        super(layer, feldNr, zauberberg);
        this.karteAufgedeckt = false;
    }


    public void execute(Kobold kobold, ArrayList<Feld> felder) {
	//Auskommentiert, da in die Kobolde in den Switchcases removed werden
    	/*for(Feld f : felder) {
	    if(f.getLayer() == kobold.getLayer() && f.getFeldNr() == kobold.getFeldNr()) {
	    	f.getKobolde().remove(this);
	    	break; 
	    }	    
	}*/
    	switch (kobold.getLayer()) {
            case 0:
                if (kobold.getFeldNr() == 15) {
                    for(Feld f : felder) {
                    	if(f.getLayer()==1 && f.getFeldNr()==12) { //obere 
                    		if(f.getKobolde().size()==0) {
                    			for(Feld feld : felder) {
                    				if(feld.getLayer() == kobold.getLayer() && feld.getFeldNr() == kobold.getFeldNr()) {
                    					feld.getKobolde().remove(kobold);        
                    					kobold.setLayer(1);
                    					kobold.setFeldNr(12);
                    					f.getKobolde().add(kobold);
                    					break;
                    				}                		    
                    			}
                    		}
                    	}
                    }                    
                }
                break;
            case 1:
                if (kobold.getFeldNr() == 3) {
                    for(Feld f : felder) {
                    	if(f.getLayer()==2 && f.getFeldNr()==2) { //obere 
                    		if(f.getKobolde().size()==0) {
                    			for(Feld feld : felder) {
                    				if(feld.getLayer() == kobold.getLayer() && feld.getFeldNr() == kobold.getFeldNr()) {
                    					feld.getKobolde().remove(kobold);        
                    					kobold.setLayer(2);
                    					kobold.setFeldNr(2);
                    					f.getKobolde().add(kobold);
                    					break;
                    				}                		    
                    			}
                    		}
                    	}
                    }                    
                }
                if (kobold.getFeldNr() == 22) {
                    for(Feld f : felder) {
                    	if(f.getLayer()==2 && f.getFeldNr()==15) { //obere 
                    		if(f.getKobolde().size()==0) {
                    			for(Feld feld : felder) {
                    				if(feld.getLayer() == kobold.getLayer() && feld.getFeldNr() == kobold.getFeldNr()) {
                    					feld.getKobolde().remove(kobold);        
                    					kobold.setLayer(2);
                    					kobold.setFeldNr(15);
                    					f.getKobolde().add(kobold);
                    					break;
                    				}                		    
                    			}
                    		}
                    	}
                    }            
                }
                break;
            case 3:
                if (kobold.getFeldNr() == 10) {
                    for(Feld f : felder) {
                    	if(f.getLayer()==4 && f.getFeldNr()==3) { //obere 
                    		if(f.getKobolde().size()==0) {
                    			for(Feld feld : felder) {
                    				if(feld.getLayer() == kobold.getLayer() && feld.getFeldNr() == kobold.getFeldNr()) {
                    					feld.getKobolde().remove(kobold);        
                    					kobold.setLayer(4);
                    					kobold.setFeldNr(3);
                    					f.getKobolde().add(kobold);
                    					break;
                    				}                		    
                    			}
                    		}
                    	}
                    }                 
                }
                break;
        }
        setKarteAufgedeckt(false);
    }

    public String getClassName() {
        return "Geheimgang";
    }

    public boolean isKarteAufgedeckt() {
        return karteAufgedeckt;
    }

    public void setKarteAufgedeckt(boolean karteAufgedeckt) {
        this.karteAufgedeckt = karteAufgedeckt;
        this.zauberberg.sendGameDataToClientsPublic("UPDATESPIELFELD");
    }
}