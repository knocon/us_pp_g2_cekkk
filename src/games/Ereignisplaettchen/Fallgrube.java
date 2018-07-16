package games.Ereignisplaettchen;

import java.util.ArrayList;

import games.Zauberberg.*;

public class Fallgrube extends Feld {
    private boolean karteAufgedeckt;

    public Fallgrube(int layer, int feldNr, ArrayList<Kobold> list, Zauberberg zauberberg) {
        super(layer, feldNr, list, zauberberg);
        this.karteAufgedeckt = false;
    }

    public void execute(Kobold kobold, ArrayList<Feld> felder) {	
        switch (kobold.getLayer()) {
            case 0:
                kobold.setLayer(-1);
                kobold.setDorf(kobold.getDorf());
            case 1:
                if (kobold.getFeldNr() > 0 && kobold.getFeldNr() < 7) {
                    kobold.setLayer(0);
                    kobold.setFeldNr(kobold.getFeldNr() + 1);
                }
                if (kobold.getFeldNr() > 7 && kobold.getFeldNr() < 14) {
                    kobold.setLayer(0);
                    kobold.setFeldNr(kobold.getFeldNr() + 3);
                }
                if (kobold.getFeldNr() > 14 && kobold.getFeldNr() < 21) {
                    kobold.setLayer(0);
                    kobold.setFeldNr(kobold.getFeldNr() + 5);
                }
                if (kobold.getFeldNr() > 21 && kobold.getFeldNr() < 28) {
                    kobold.setLayer(0);
                    kobold.setFeldNr(kobold.getFeldNr() + 7);
                }
                break;
            case 2:
                if (kobold.getFeldNr() > 0 && kobold.getFeldNr() < 5) {
                    kobold.setLayer(1);
                    kobold.setFeldNr(kobold.getFeldNr() + 1);
                }
                if (kobold.getFeldNr() > 5 && kobold.getFeldNr() < 10) {
                    kobold.setLayer(1);
                    kobold.setFeldNr(kobold.getFeldNr() + 3);
                }
                if (kobold.getFeldNr() > 10 && kobold.getFeldNr() < 15) {
                    kobold.setLayer(1);
                    kobold.setFeldNr(kobold.getFeldNr() + 5);
                }
                if (kobold.getFeldNr() > 15 && kobold.getFeldNr() < 20) {
                    //Fallgrube auf layer 2, feldnr 19
                    for(Feld feld: felder) {
                	if(feld.getLayer() == 1 && feld.getFeldNr()== kobold.getFeldNr()+7) {
                	    if(feld.getKobolde().size()==0) {
                		for(Feld f : felder) {
                            		if(f.getLayer() == kobold.getLayer() && f.getFeldNr() == kobold.getFeldNr()) {
                            		    f.getKobolde().remove(kobold);
                            		    break; 
                            		}                            		
                                }                		
                                kobold.setLayer(1);
                                kobold.setFeldNr(kobold.getFeldNr() + 7);
                                break;
                	    }
                	}
                    }                    
                }
                break;
            case 3:
                if (kobold.getFeldNr() > 0 && kobold.getFeldNr() < 3) {
                    kobold.setLayer(2);
                    kobold.setFeldNr(kobold.getFeldNr() + 1);
                }
                if (kobold.getFeldNr() > 3 && kobold.getFeldNr() < 6) {
                    kobold.setLayer(2);
                    kobold.setFeldNr(kobold.getFeldNr() + 3);
                }
                if (kobold.getFeldNr() > 6 && kobold.getFeldNr() < 9) {
                    //Fallgrube auf layer 3, nr 7
                    for(Feld feld: felder) {
                	if(feld.getLayer() == 2 && feld.getFeldNr()== kobold.getFeldNr()+5) {
                	    if(feld.getKobolde().size()==0) {
                		for(Feld f : felder) {
                            		if(f.getLayer() == kobold.getLayer() && f.getFeldNr() == kobold.getFeldNr()) {
                            		    f.getKobolde().remove(kobold);
                            		    break; 
                            		}                            		
                                }                		
                                kobold.setLayer(2);
                                kobold.setFeldNr(kobold.getFeldNr() + 5);
                                break;
                	    }
                	}
                    }
                }
                if (kobold.getFeldNr() > 9 && kobold.getFeldNr() < 12) {
                    kobold.setLayer(2);
                    kobold.setFeldNr(kobold.getFeldNr() + 7);
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
