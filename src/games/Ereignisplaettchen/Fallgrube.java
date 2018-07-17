package games.Ereignisplaettchen;

import java.util.ArrayList;

import games.Zauberberg.*;

public class Fallgrube extends Feld {
    private boolean karteAufgedeckt;

    public Fallgrube(int layer, int feldNr, Zauberberg zauberberg) {
        super(layer, feldNr, zauberberg);
        this.karteAufgedeckt = false;
    }

    public void execute(Kobold kobold, ArrayList<Feld> felder) {
        switch (kobold.getLayer()) {
            case 2:
                if (kobold.getFeldNr()==19) {
                    //Fallgrube auf layer 2, feldnr 19
                    for(Feld feld: felder) {
                    	if(feld.getLayer() == 1 && feld.getFeldNr()== 26) {
                    		if(feld.getKobolde().size()==0) {
                    			for(Feld f : felder) {
                            		if(f.getLayer() == kobold.getLayer() && f.getFeldNr() == kobold.getFeldNr()) {
                            			f.getKobolde().remove(kobold);
                            			kobold.setLayer(1);
                                        kobold.setFeldNr(26);
                                        feld.getKobolde().add(kobold);
                            		    break; 
                            		}                            		
                    			}                		
                                break;
                    		}
                	}
                    }                    
                }
                break;
            case 3:
                if (kobold.getFeldNr()== 7) {
                    //Fallgrube auf layer 3, nr 7
                	for(Feld feld: felder) {
                    	if(feld.getLayer() == 2 && feld.getFeldNr()== 12) {
                    		if(feld.getKobolde().size()==0) {
                    			for(Feld f : felder) {
                    				if(f.getLayer() == kobold.getLayer() && f.getFeldNr() == kobold.getFeldNr()) {
                    					f.getKobolde().remove(kobold);
                    					kobold.setLayer(2);
                            			kobold.setFeldNr(12);
                            			feld.getKobolde().add(kobold);
                            			break; 
                            		}                            		
                                }                		
                                break;
                	    }
                	}
                    }
                }
                break;
        }
        setKarteAufgedeckt(false);
    }

    public String getClassName() {
        return "Fallgrube";
    }

    public boolean isKarteAufgedeckt() {
        return karteAufgedeckt;
    }

    public void setKarteAufgedeckt(boolean karteAufgedeckt) {
        this.karteAufgedeckt = karteAufgedeckt;
        this.zauberberg.sendGameDataToClientsPublic("UPDATESPIELFELD");
    }
}
