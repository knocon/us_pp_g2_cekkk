package games.Zauberberg;

import java.util.ArrayList;
import games.Ereignisplaettchen.*;
import games.Zauberberg.*;

public class Spiel {
    private Kartenstapel kartenstapel;
    private ArrayList<Feld> felder = new ArrayList<Feld>(); 
    
    public Spiel() {
	kartenstapel = new Kartenstapel(); 
	
	//layer 1
	for(int i=0; i<=35;i++ ) {
	    if(i == 1) {
		felder.add(new Schreckgespenst(0,i,null)); 
	    } else if(i ==7) {
		felder.add(new Kristallkugel(0,i,null)); 
	    } else if(i == 12) {
		felder.add(new Unwetter(0,i,null)); 
	    } else if(i==15) {
		felder.add(new Geheimgang(0,i,null)); 
	    } else if (i == 19) {
		felder.add(new Unwetter(0,i,null)); 
	    } else if(i==25) {
		felder.add(new Kristallkugel(0,i,null)); 
	    } else if (i == 30) {
		felder.add(new Rabe(0,i,null)); 
	    } else if(i == 32) {
		felder.add(new Unwetter(0,i,null)); 
	    } else 
		felder.add(new Feld(0,i,null));     
	}
	//layer 2
	for(int i=0; i<=27;i++ ) {
	    if(i==0) {
		felder.add(new Zauberstein(1,i)); 
	    } else if (i==3) {
		felder.add(new Geheimgang(1,i,null)); 
	    } else if (i == 10) {
		felder.add(new Kristallkugel(1,i,null)); 
	    } else if (i==17) {
		felder.add(new Rabe(1,i,null)); 
	    } else if (i == 19) {
		felder.add(new Zauberstein(1,i)); 
	    } else if (i== 22) {
		felder.add(new Geheimgang(1,i, null)); 
	    } else 
		felder.add(new Feld(1,i,null)); 		      
	}
	//layer 3
	for(int i = 0; i<=19; i++) {
	    if (i == 3) {
		felder.add(new Zauberstein(2,i)); 
	    } else if (i == 4) {
		felder.add(new Rabe(2,i,null)); 
	    } else if(i==8) {
		felder.add(new FliegendeKarte(2,i,null)); 
	    } else if(i==10) {
		felder.add(new Zauberstein(2,i)); 
	    } else if(i==14) {
		felder.add(new FliegendeKarte(2,i,null)); 
	    } else if (i==18) {
		felder.add(new Zauberstein(2,i)); 
	    } else if (i==19) {
		felder.add(new Fallgrube(2,i,null)); 
	    } else 
		felder.add(new Feld(2,i,null));     
	}
	//layer 4 
	for(int i = 0; i<=11; i++) {
	    if (i == 0) {
		felder.add(new Zauberstein(3,i)); 
	    } else if (i == 1) {
		felder.add(new Schreckgespenst(3,i,null)); 
	    } else if(i==4) {
		felder.add(new Zauberstein(3,i)); 
	    } else if(i==7) {
		felder.add(new Fallgrube(3,i,null)); 
	    } else if(i==8) {
		felder.add(new Zauberstein(3,i)); 
	    } else if (i==10) {
		felder.add(new Geheimgang(3,i,null)); 
	    } else 
		felder.add(new Feld(3,i,null));     
	}

	
       
   }
   
   

}


