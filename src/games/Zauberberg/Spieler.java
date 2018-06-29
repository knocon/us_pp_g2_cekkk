package games.Zauberberg;

import java.util.ArrayList;
import java.util.Random;

public class Spieler {
    private int anzahlZaubersteine; 
    private ArrayList<Bewegungskarte> hand;  
    
    public Spieler(ArrayList<Bewegungskarte> hand) {
	this.anzahlZaubersteine = 0;
	this.hand = hand;	
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
    
}