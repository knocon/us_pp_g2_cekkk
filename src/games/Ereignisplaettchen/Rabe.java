package games.Ereignisplaettchen;

import games.Zauberberg.Spieler;

public class Rabe {

    //Spieler bestimmt Spieler, der einen Zauberstein abgeben muss
    public static void execute(Spieler s) {
	s.setAnzahlZaubersteine(s.getAnzahlZaubersteine()-1);
    }
}
