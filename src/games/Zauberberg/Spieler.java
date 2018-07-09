package games.Zauberberg;


import java.util.ArrayList;
import java.util.Random;

import userManagement.User;

public class Spieler {
    private Name farbName;
    private int anzahlZaubersteine;
    private ArrayList<Bewegungskarte> hand;
    private static int anz =0; 

    public Spieler() {
	switch(anz) {
	case 0: this.farbName = Name.ROT;
		break;
	case 1: this.farbName = Name.BLAU; 
		break; 
	case 2: this.farbName = Name.GRUEN; 
		break; 
	case 3: this.farbName = Name.GELB; 
		break; 
	case 4: this.farbName = Name.PINK;
		break; 
	}        
        this.anzahlZaubersteine = 0;
        this.hand = new ArrayList<>();
        this.anz++; 
    }
    public enum Name {
	ROT, BLAU, GRUEN, GELB, PINK; 
    }

    public int getAnzahlZaubersteine() {
        return anzahlZaubersteine;
    }

    public void setAnzahlZaubersteine(int anzahlZaubersteine) {
        this.anzahlZaubersteine = anzahlZaubersteine;
    }

    public ArrayList<Bewegungskarte> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Bewegungskarte> hand) {
        this.hand = hand;
    }
    
    public Name getName() {
        return farbName;
    }
    
    /*
    public void karteZiehen() {
        //nur ne Idee
        //Kartenstapel muss am Anfang irgendwo erzeugt werden
        Random random = new Random();
        Kartenstapel k = new Kartenstapel();
        int randomIndex = random.nextInt(k.getStapel().size());
        hand.add(k.getStapel().get(randomIndex));
    }

    public void karteAblegen(Bewegungskarte bk) {
        hand.remove(bk);
        //Kartenstapel wird nur einmal im Projekt erzeugt, s.o.
        //Kartenstapel k = new Kartenstapel();
        //k.getStapel().add(bk);
    }
	*/
    
}
