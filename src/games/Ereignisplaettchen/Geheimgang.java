package games.Ereignisplaettchen;

import java.util.ArrayList;

import games.Zauberberg.*;

public class Geheimgang extends Feld { 
    public Geheimgang(int layer, int feldNr,ArrayList<Kobold> list) {
	super(layer,feldNr,list); 
    }
	

public static void aufsteigen (Kobold kobold) {
	switch(kobold.getLayer()) {
	
	case 0: 
	    if(kobold.getPos()>0 && kobold.getPos()<9) {
		kobold.setLayer(1);
		kobold.setPos(kobold.getPos()-1);
	    }
	    if(kobold.getPos()>9 && kobold.getPos()<18) {
		kobold.setLayer(1);
		kobold.setPos(kobold.getPos()-3);
	    }
	    if(kobold.getPos()>18 && kobold.getPos()<27) {
		kobold.setLayer(1);
		kobold.setPos(kobold.getPos()-5);
	    }
	    if(kobold.getPos()>27 && kobold.getPos()<36) {
		kobold.setLayer(1);
		kobold.setPos(kobold.getPos()-7);
	    }
	    break;
	case 1: 
	    if(kobold.getPos()>0 && kobold.getPos()<7) {
		kobold.setLayer(2);
		kobold.setPos(kobold.getPos()-1);
	    }
	    if(kobold.getPos()>7 && kobold.getPos()<14) {
		kobold.setLayer(2);
		kobold.setPos(kobold.getPos()-3);
	    }
	    if(kobold.getPos()>14 && kobold.getPos()<21) {
		kobold.setLayer(2);
		kobold.setPos(kobold.getPos()-5);
	    }
	    if(kobold.getPos()>21 && kobold.getPos()<28) {
		kobold.setLayer(2);
		kobold.setPos(kobold.getPos()-7);
	    }
	    break;
	case 2: 
	    if(kobold.getPos()>0 && kobold.getPos()<5) {
		kobold.setLayer(3);
		kobold.setPos(kobold.getPos()-1);
	    }
	    if(kobold.getPos()>5 && kobold.getPos()<10) {
		kobold.setLayer(3);
		kobold.setPos(kobold.getPos()-3);
	    }
	    if(kobold.getPos()>10 && kobold.getPos()<15) {
		kobold.setLayer(3);
		kobold.setPos(kobold.getPos()-5);
	    }
	    if(kobold.getPos()>15 && kobold.getPos()<20) {
		kobold.setLayer(3);
		kobold.setPos(kobold.getPos()-7);
	    }
	    break;
	case 3: 
	    if(kobold.getPos()>0 && kobold.getPos()<3) {
		kobold.setLayer(4);
		kobold.setPos(kobold.getPos()-1);
	    }
	    if(kobold.getPos()>3 && kobold.getPos()<6) {
		kobold.setLayer(4);
		kobold.setPos(kobold.getPos()-3);
	    }
	    if(kobold.getPos()>6 && kobold.getPos()<9) {
		kobold.setLayer(4);
		kobold.setPos(kobold.getPos()-5);
	    }
	    if(kobold.getPos()>9 && kobold.getPos()<12) {
		kobold.setLayer(4);
		kobold.setPos(kobold.getPos()-7); 
	    }
	    break;
	

	
	
    }
}
}