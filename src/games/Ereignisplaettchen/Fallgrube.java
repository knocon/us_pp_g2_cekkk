package games.Ereignisplaettchen;

import java.util.ArrayList;

import games.Zauberberg.Feld;
import games.Zauberberg.Kobold;

public class Fallgrube extends Feld { 
    
    public Fallgrube(int layer, int feldNr, ArrayList<Kobold> list) {
	super(layer,feldNr,list); 
    }
	
    public static void execute (Kobold kobold) { 
	switch(kobold.getLayer()) {
	case 0: 
	    int random = (int )(Math.random() * 4 + 0);
	    kobold.setLayer(-1);
	    kobold.setPos(random);
	    break;
	case 1: 
	    if(kobold.getFeldNr()>0 & kobold.getFeldNr()<7) {
		kobold.setLayer(0);
		kobold.setFeldNr(kobold.getFeldNr()+1);
	    }
	    if(kobold.getFeldNr()>7 & kobold.getFeldNr()<14) {
		kobold.setLayer(0);
		kobold.setFeldNr(kobold.getFeldNr()+3);
	    }
	    if(kobold.getFeldNr()>14 & kobold.getFeldNr()<21) {
		kobold.setLayer(0);
		kobold.setFeldNr(kobold.getFeldNr()+5);
	    }
	    if(kobold.getFeldNr()>21 & kobold.getFeldNr()<28) {
		kobold.setLayer(0);
		kobold.setFeldNr(kobold.getFeldNr()+7);
	    }
	    break; 
	case 2: 
	    if(kobold.getFeldNr()>0 & kobold.getFeldNr()<5) {
		kobold.setLayer(1);
		kobold.setFeldNr(kobold.getFeldNr()+1);
	    }
	    if(kobold.getFeldNr()>5 & kobold.getFeldNr()<10) {
		kobold.setLayer(1);
		kobold.setFeldNr(kobold.getFeldNr()+3);
	    }
	    if(kobold.getFeldNr()>10 & kobold.getFeldNr()<15) {
		kobold.setLayer(1);
		kobold.setFeldNr(kobold.getFeldNr()+5);
	    }
	    if(kobold.getFeldNr()>15 & kobold.getFeldNr()<20) {
		kobold.setLayer(1);
		kobold.setFeldNr(kobold.getFeldNr()+7);
	    }
	    break; 
	case 3: 
	    if(kobold.getFeldNr()>0 & kobold.getFeldNr()<3) {
		kobold.setLayer(2);
		kobold.setFeldNr(kobold.getFeldNr()+1);
	    }
	    if(kobold.getFeldNr()>3 & kobold.getFeldNr()<6) {
		kobold.setLayer(2);
		kobold.setFeldNr(kobold.getFeldNr()+3);
	    }
	    if(kobold.getFeldNr()>6 & kobold.getFeldNr()<9) {
		kobold.setLayer(2);
		kobold.setFeldNr(kobold.getFeldNr()+5);
	    }
	    if(kobold.getFeldNr()>9 & kobold.getFeldNr()<12) {
		kobold.setLayer(2);
		kobold.setFeldNr(kobold.getFeldNr()+7);
	    }
	    break; 
	}
	
	
	
    }
}
