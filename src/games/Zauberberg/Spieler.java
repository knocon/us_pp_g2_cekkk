package games.Zauberberg;


import java.util.ArrayList;
import java.util.Random;

import userManagement.User;

public class Spieler {
    private String spielerName;
    private Name farbName;
    private ArrayList<Kobold> koboldList = new ArrayList<Kobold>();
    private int anzahlZaubersteine;
    private ArrayList<Bewegungskarte> hand;
    private static int anz =0;
    private Zauberberg zauberberg;

    public Spieler(Zauberberg zauberberg) {
        this.zauberberg = zauberberg;
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
        zauberberg.sendGameDataToClientsPublic("UPDATESPIELZUSTAND");
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

	public Name getFarbName() {
		return farbName;
	}

	public void setFarbName(Name farbName) {
		this.farbName = farbName;
	}

	public static int getAnz() {
		return anz;
	}

	public static void setAnz(int anz) {
		Spieler.anz = anz;
	}

	public ArrayList<Kobold> getKoboldList() {
		return koboldList;
	}

	public void setKoboldList(ArrayList<Kobold> koboldList) {
		this.koboldList = koboldList;
	}

	public String getSpielerName() {
		return spielerName;
	}

	public void setSpielerName(String spielerName) {
		this.spielerName = spielerName;
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
