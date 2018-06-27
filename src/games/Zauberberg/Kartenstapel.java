package games.Zauberberg;

import java.util.ArrayList;

public class Kartenstapel {
    private ArrayList<Bewegungskarte> stapel = new ArrayList<Bewegungskarte>(); 
    
    //Jede Zahl existiert 11 mal, Joker 5 mal 
    public Kartenstapel() { 
	for(int i = 1; i<=11; i++) {
	    for(int j=1; j<=5; j++) {
		stapel.add(new Bewegungskarte(j, false));
	    }
	}
	for(int i = 1; i<=5; i++) {
	    stapel.add(new Bewegungskarte(0, true)); 
	}
	
    }    
}
