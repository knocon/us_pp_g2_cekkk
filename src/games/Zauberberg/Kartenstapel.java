package games.Zauberberg;

import java.util.ArrayList;

public class Kartenstapel {
    private ArrayList<Bewegungskarte> stapel = new ArrayList<Bewegungskarte>(); 
    
    //Jede Zahl existiert 11 mal, Joker 5 mal 
    public Kartenstapel() { 
	for(int i = 1; i<=11; i++) {
	    for(int j=1; j<=5; j++) {
		getStapel().add(new Bewegungskarte(j, false));
	    }
	}
	for(int i = 1; i<=5; i++) {
	    getStapel().add(new Bewegungskarte(0, true)); 
	}
	
    }

    public ArrayList<Bewegungskarte> getStapel() {
	return stapel;
    }

    public void setStapel(ArrayList<Bewegungskarte> stapel) {
	this.stapel = stapel;
    }    
}
