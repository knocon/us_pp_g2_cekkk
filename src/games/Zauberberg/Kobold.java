package games.Zauberberg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import games.Ereignisplaettchen.*;


public class Kobold {
    private int nummer;
    private Spieler.Name farbe;
    private int layer;
    private int feldNr;
    private int globalFeld; //DER INDEX DES FELDES IN ARRAYLIST felder AUS KLASSE SPIEL
    private int dorf;
    private Spieler spieler;
    private Zauberberg zauberberg;
    private ArrayList<Bewegungskarte> kartenInstances = new ArrayList<>();
    private ArrayList<Integer> kartenWerte = new ArrayList<>();
    Feld tempFeld;
    Gson gson = new GsonBuilder().create();
    private static int anz;

    //Methoden bewegen
    public Kobold(int nummer, Spieler spieler, Zauberberg zauberberg) {
        this.dorf = anz % 4; //gleichmäßiges Verteilen der Kobolde (im Uhrzeigersinn)
        this.layer = -1;
        this.feldNr = this.dorf;
        this.spieler = spieler;
        this.nummer = nummer;
        this.farbe = spieler.getFarbName();
        this.zauberberg = zauberberg;
        anz++;
    }

    public void kartenLegen(Bewegungskarte instanceKoboldKarte, Bewegungskarte instanceKarte1, Bewegungskarte instanceKarte2, int kartenWert1, int kartenWert2) {
        // Darf der Kobold bewegt werden? Check
        for (Feld f : zauberberg.getSpiel().getFelder()) {
            if (f.getFeldNr() == zauberberg.getAktuellerKobold().getFeldNr() && f.getLayer() == zauberberg.getAktuellerKobold().getLayer()) {
                if (f.getKobolde().size() == 2 && f.getKobolde().get(0) == zauberberg.getAktuellerKobold()) {
                    zauberberg.setRecentInfoText("Du kannst den Kobold mit der Nummer " + this.getNummer() + " nicht bewegen.");
                    zauberberg.sendGameDataToUserPublic("PUSHINFOTEXT");
                    zauberberg.sendGameDataToUserPublic("UPDATEKARTEN");
                    return;
                }
            }
        }
        // Hier gehts weiter wenn der Kobold bewegt werden darf
        // Koboldkarte aus der Hand in den Stapel legen.
        spieler.getHand().remove(instanceKoboldKarte);
        zauberberg.getSpiel().getKartenstapel().getStapel().add(instanceKoboldKarte);
        // Bewegungskarten uebernehmen
        if (instanceKarte1 != null) {
            this.kartenInstances.add(instanceKarte1);
            this.kartenWerte.add(kartenWert1);
        }
        if (instanceKarte1 != null) {
            this.kartenInstances.add(instanceKarte2);
            this.kartenWerte.add(kartenWert2);
        }
        this.karteSpielen();
    }

    public void karteSpielen() {
        int laufweg = this.kartenWerte.get(0);
        ArrayList<Integer> arrayLayerFeld = new ArrayList<Integer>();
        if (this.getLayer() == -1) {            //Schritt raus aus dem Dorf
            if (this.getFeldNr() == 0) {
        	arrayLayerFeld.add(0); //layer
                arrayLayerFeld.add(((0 + (laufweg - 1) % 36) + 36) % 36); //schritte nach "vorne"
                arrayLayerFeld.add(0); //layer
                arrayLayerFeld.add(((0 - (laufweg - 1) % 36) + 36) % 36); // schritte nach "hinten"
            } else if (this.getFeldNr() == 1) {
                arrayLayerFeld.add(0); //layer
                arrayLayerFeld.add((((9 + (laufweg - 1)) % 36) + 36) % 36);
                arrayLayerFeld.add(0); //layer
                arrayLayerFeld.add((((9 - (laufweg - 1)) % 36) + 36) % 36);
            } else if (this.getFeldNr() == 2) {
                arrayLayerFeld.add(0); //layer
                arrayLayerFeld.add((((18 + (laufweg - 1)) % 36) + 36) % 36);
                arrayLayerFeld.add(0); //layer
                arrayLayerFeld.add((((18 - (laufweg - 1)) % 36) + 36) % 36);
            } else if (this.getFeldNr() == 3) {
                arrayLayerFeld.add(0); //layer
                arrayLayerFeld.add((((27 + (laufweg - 1)) % 36) + 36) % 36);
                arrayLayerFeld.add(0); //layer
                arrayLayerFeld.add((((27 - (laufweg - 1)) % 36) + 36) % 36);
            }
        } else if (this.getLayer() == 0) {
            //prüft ob Kobold auf einem Kobold sitzt, wenn ja nächste Ebene möglich
            if(this.getCorrectFeld(this.getLayer(), this.getFeldNr()).getKobolde().size()==2) { //auf aktuellem Feld 2 Kobolde
            	if(this.getCorrectFeld(this.getLayer(), this.getFeldNr()).getKobolde().get(1).equals(this)) { //zu bewegender Kobold ist der obere
            	    if(this.getFeldNr()!= 0 && this.getFeldNr()!= 9 && this.getFeldNr()!= 18 && this.getFeldNr()!= 27) { //nicht auf einem Eckpunkt
            		if(this.getCorrectFeld(this.getLayer()+1, this.getFeldNrNextLayer(0, this.getFeldNr())).getKobolde().size()==0) { //nächste Ebene ist frei
            			arrayLayerFeld.add(1);
            			arrayLayerFeld.add(this.getFeldNrNextLayer(0, this.getFeldNr()));
            		}
        	    }
        	}
            }
            arrayLayerFeld.add(0); 
            arrayLayerFeld.add((((this.getFeldNr() + laufweg) % 36) + 36) % 36);  //vorw�rts
            arrayLayerFeld.add(0); 
            arrayLayerFeld.add((((this.getFeldNr() - laufweg) % 36) + 36) % 36); // r�ckw�rts
        } else if (this.getLayer() == 1) {
            if(this.getCorrectFeld(this.getLayer(), this.getFeldNr()).getKobolde().size()==2) { //auf aktuellem Feld 2 Kobolde
            	if(this.getCorrectFeld(this.getLayer(), this.getFeldNr()).getKobolde().get(1).equals(this)) { // zu bewegender Kobold ist der obere
            	    if(this.getFeldNr() != 0 && this.getFeldNr() != 7 && this.getFeldNr() != 14 && this.getFeldNr() != 21) { //kein Eckpunkt
            		if(this.getCorrectFeld(this.getLayer()+1, this.getFeldNrNextLayer(1, this.getFeldNr())).getKobolde().size()==0) { //nächste Ebene frei
            			arrayLayerFeld.add(2);
            			arrayLayerFeld.add(this.getFeldNrNextLayer(1, this.getFeldNr()));
            		}
            	    }
        	}
            }
            arrayLayerFeld.add(1); 
            arrayLayerFeld.add((((this.getFeldNr() + laufweg) % 28) + 28) % 28); //vorw�rts
            arrayLayerFeld.add(1); 
            arrayLayerFeld.add((((this.getFeldNr() - laufweg) % 28) + 28) % 28);  // r�ckw�rts
        } else if (this.getLayer() == 2) {
            if(this.getCorrectFeld(this.getLayer(), this.getFeldNr()).getKobolde().size()==2) { //auf aktuellem Feld 2 Kobolde
        	if(this.getCorrectFeld(this.getLayer(), this.getFeldNr()).getKobolde().get(1).equals(this)) {// zu bewegender Kobold ist der obere
        	    if(this.feldNr!= 0 && this.feldNr!= 5 && this.feldNr!= 10 && this.feldNr!= 15) { //kein Eckpunkt
        		if(this.getCorrectFeld(this.getLayer()+1, this.getFeldNrNextLayer(2, this.getFeldNr())).getKobolde().size()==0) { //nächstene Ebene frei
        		    arrayLayerFeld.add(3);
        		    arrayLayerFeld.add(this.getFeldNrNextLayer(2, this.getFeldNr()));
        		}
        	    }
        	}
            }
            arrayLayerFeld.add(2); 
            arrayLayerFeld.add((((this.getFeldNr() + laufweg) % 20) + 20) % 20);  //vorw�rts
            arrayLayerFeld.add(2); 
            arrayLayerFeld.add((((this.getFeldNr() - laufweg) % 20) + 20) % 20); // r�ckw�rts
        } else if (this.getLayer() == 3) {
            if(this.getCorrectFeld(this.getLayer(), this.getFeldNr()).getKobolde().size()==2) { //auf aktuellem Feld 2 Kobolde
        	if(this.getCorrectFeld(this.getLayer(), this.getFeldNr()).getKobolde().get(1).equals(this)) { // zu bewegender Kobold ist der obere
        	    if(this.getFeldNr() !=0 && this.getFeldNr() !=3 && this.getFeldNr() !=6  && this.getFeldNr() !=9 ) { //kein Eckpunkt
        		if(this.getCorrectFeld(this.getLayer()+1, this.getFeldNrNextLayer(3, this.getFeldNr())).getKobolde().size()==0) { //nächste Ebene frei
        		    arrayLayerFeld.add(4);
        		    arrayLayerFeld.add(this.getFeldNrNextLayer(3, this.getFeldNr()));
        		}
        	    }
        	}
            }
            arrayLayerFeld.add(3);
            arrayLayerFeld.add((((this.getFeldNr() + laufweg) % 12) + 12) % 12);  //vorw�rts
            arrayLayerFeld.add(3); 
            arrayLayerFeld.add((((this.getFeldNr() - laufweg) % 12) + 12) % 12);  // r�ckw�rts
        }
        if (arrayLayerFeld.size() == 0) {
            this.zauberberg.setRecentInfoText("Du kannst den Kobold mit der Nummer " + this.getNummer() + " nicht bewegen.");
            this.zauberberg.sendGameDataToUserPublic("PUSHINFOTEXT");
        } else {
            this.zauberberg.setFelderWaehlen(gson.toJson(arrayLayerFeld, ArrayList.class));
            this.zauberberg.sendGameDataToUserPublic("FELDERANBIETEN");
        }
    }
    public Feld getCorrectFeld(int layer, int feldNr) {
	for(Feld f : zauberberg.getSpiel().getFelder()) {
	    if(f.getLayer() == layer && f.getFeldNr() == feldNr) {
		return f;
	    }	    
	}
	return null;
    }

    public void bewegen(int layer, int feldNr) {
	//int layerVorBewegen = this.getLayer();
	//int feldNrVorBewegen = this.getFeldNr();
	int modulo=0;
	int vorZurueck =0;
	Kobold koboldOnTop;

        //zu bewegender Kobold vom alten Feld entfernen
        for (Feld f : zauberberg.getSpiel().getFelder()) {
            if (f.getLayer() == this.getLayer() && f.getFeldNr() == this.getFeldNr()) {
                f.getKobolde().remove(this);
                break;
            }
        }
        //auf neues Feld setzen + Koboldliste einfuegen fuer den Fall, dass da (k)ein Kobold sitzt 
        if(getCorrectFeld(layer, feldNr).getKobolde().size()<2) {
            this.setFeldNr(feldNr);
            this.setLayer(layer);
            getCorrectFeld(layer,feldNr).getKobolde().add(this);
        }
        //auf neuem Feld sitzen schon 2 Kobolde --> Weiterschieben 
        if(getCorrectFeld(layer, feldNr).getKobolde().size()==2) {
            koboldOnTop = getCorrectFeld(layer,feldNr).getKobolde().get(1);
            switch(koboldOnTop.getLayer()) {
            case 0: modulo = 36;
            	break;
            case 1 : modulo = 28;
            	break;
            case 2:  modulo = 20;
            	break;
            case 3:  modulo = 12;
            }
            if (((((feldNr - this.getFeldNr())%modulo)+modulo)%modulo) < ((((this.getFeldNr() - feldNr)%modulo)+modulo)%modulo )){
        	vorZurueck = 1;
            } else {
        	vorZurueck = -1;
            }
            getCorrectFeld(layer,feldNr).getKobolde().remove(1); //remove den oberen, das ist KoboldOnTop
            this.setFeldNr(feldNr); //ursprünglichen Kobold setzen und bei feld.getKobold() einfügen
            this.setLayer(layer);
            getCorrectFeld(layer,feldNr).getKobolde().add(this);

            boolean check = false;
            while(!check) {
        	if(getCorrectFeld(koboldOnTop.getLayer(),koboldOnTop.getFeldNr()+vorZurueck).getKobolde().size()<2) {
        	    koboldOnTop.setFeldNr(koboldOnTop.getFeldNr() + vorZurueck);
        	    getCorrectFeld(koboldOnTop.getLayer(),koboldOnTop.getFeldNr()+vorZurueck).getKobolde().add(koboldOnTop);
        	    check = true;
        	} else {
        	    getCorrectFeld(koboldOnTop.getLayer(), koboldOnTop.getFeldNr()+vorZurueck).getKobolde().add(1, koboldOnTop );
        	    koboldOnTop = getCorrectFeld(koboldOnTop.getLayer(),koboldOnTop.getFeldNr()+vorZurueck).getKobolde().get(2); // das ist der obere vom nächsten feld
        	}
            }
        }

        //Karte zurück in den Stapel
        kartenWerte.remove(0);
        spieler.getHand().remove(kartenInstances.get(0));
        zauberberg.getSpiel().getKartenstapel().getStapel().add(kartenInstances.get(0));
        kartenInstances.remove(0);

        //Feld auf Ereignisse prüfen
        for (Feld f : zauberberg.getSpiel().getFelder()) {
            if (f.getLayer() == layer && f.getFeldNr() == feldNr) {
                switch (f.getClassName()) {
                	case "Zauberstein":
                		if(f.getClass().equals("Zauberstein") && ((Zauberstein) f).getAufFeld() == false){
                			zauberberg.setRecentInfoText(this.spieler.getFarbName() + " ist auf keinen Zauberstein gestossen.");
                			zauberberg.sendGameDataToClientsPublic("PUSHINFOTXT");
                		}else {
                			zauberberg.setRecentInfoText(this.spieler.getFarbName() + " ist auf einen Zauberstein gestossen. Du erhaelst 1 Zauberstein.");
                			zauberberg.sendGameDataToClientsPublic("PUSHINFOTXT");
                			if(this.getSpieler().getAnzahlZaubersteine()<2) {
                			    this.getSpieler().setAnzahlZaubersteine(this.getSpieler().getAnzahlZaubersteine() + 1);
                			}
                			((Zauberstein) f).setAufFeld(false);
                		}
                		this.bewegenBeenden();
                		break;
                	case "Fallgrube":
                        Fallgrube fallgrube = (Fallgrube) f;
                        zauberberg.setRecentInfoText(this.spieler.getFarbName() + " ist auf das Ereignis Fallgrube gekommen. Du faellst hinab.");
                        zauberberg.sendGameDataToClientsPublic("PUSHINFOTXT");
                        fallgrube.execute(this, this.zauberberg.getSpiel().getFelder());
                        this.bewegenBeenden();
                        break;
                    case "Geheimgang":
                        Geheimgang geheimgang = (Geheimgang) f;
                        zauberberg.setRecentInfoText(this.spieler.getFarbName() + " ist auf das Ereignis Geheimgang gekommen. Du steigst auf.");
                        zauberberg.sendGameDataToClientsPublic("PUSHINFOTXT");
                        geheimgang.execute(this, this.zauberberg.getSpiel().getFelder());
                        this.bewegenBeenden();
                        break;
                    case "Schreckgespenst":
                        Schreckgespenst schreckgespenst = (Schreckgespenst) f;
                        zauberberg.setRecentInfoText(this.spieler.getFarbName() + " ist auf das Ereignis Fallgrube gekommen. Zurueck ins Dorf.");
                        zauberberg.sendGameDataToClientsPublic("PUSHINFOTXT");
                        schreckgespenst.execute(this);
                        this.bewegenBeenden();
                        break;
                    case "Rabe":
                        Rabe rabe = (Rabe) f;
                        HashMap<String, String> output = new HashMap<>();
                        zauberberg.setRecentInfoText(this.spieler.getFarbName() + " ist auf das Ereignis Rabe gekommen. Waehle 1 Spieler, dem der Zauberstein entfernt wird.");
                        zauberberg.sendGameDataToClientsPublic("PUSHINFOTXT");
                        output.put("Ereignis", "Rabe");
                        zauberberg.setEreignisAnfrage(gson.toJson(output, HashMap.class));
                        zauberberg.sendGameDataToUserPublic("EREIGNISANFRAGE");
                        break;
                    case "Fliegende Karte":
                    	FliegendeKarte fliegendeKarte = (FliegendeKarte) f;
                        HashMap<String, String> output2 = new HashMap<>();
                    	zauberberg.setRecentInfoText(this.spieler.getFarbName() + " ist auf das Ereignis Fliegende Karte gekommen. Du erhaelst 1 Karte und hast die Moeglichkeit, eine Karte auszuspielen.");
                    	zauberberg.sendGameDataToClientsPublic("PUSHINFOTXT");
                    	fliegendeKarte.execute(this.getSpieler(), zauberberg.getSpiel().getKartenstapel());
                        output2.put("Ereignis", "Fliegende Karte");
                        zauberberg.setEreignisAnfrage(gson.toJson(output2, HashMap.class));
                        zauberberg.sendGameDataToUserPublic("EREIGNISANFRAGE");
                        break;
                    case "Kristallkugel":
                        zauberberg.setRecentInfoText(this.spieler.getFarbName() + " ist auf das Ereignis Kristallkugel gekommen. Du darfst 3 Karten deiner Wahl aufdecken.");
                        zauberberg.sendGameDataToClientsPublic("PUSHINFOTXT");
                        // TODO Kristallkugel Karte aus dem Spiel nehmen
                        this.bewegenBeenden();
                        break;
                    case "Unwetter":
                        // manuelles umdrehen aller Karten
                        Unwetter unwetter = (Unwetter) f;
                        unwetter.execute();
                        zauberberg.setRecentInfoText(this.spieler.getFarbName() + " ist auf das Ereignis Unwetter gekommen.");
                        zauberberg.sendGameDataToClientsPublic("PUSHINFOTXT");
                        zauberberg.setRecentInfoText("Alle Ereigniskarten muessen zugedeckt werden. Das Spielfeld wird im Uhrzeigersinn gedreht.");
                        zauberberg.sendGameDataToClientsPublic("PUSHINFOTXT");
                        zauberberg.sendGameDataToClientsPublic("FELDDREHEN");
                        this.bewegenBeenden();
                        break;
                    default:
                        this.bewegenBeenden();
                }
                break;
            }
        }
        //zauberberg.sendGameDataToUserPublic("UPDATESPIELFELD");
    }

    public void bewegenBeenden(){
        if (this.kartenWerte.size() != 0) {
            this.karteSpielen();
        } else { // Zug ist beendet
            zauberberg.zugBeenden();
            // Neue Karten ziehen
            spieler.getHand().addAll(zauberberg.getRandomCards(3 + (spieler.getAnzahlZaubersteine() < 0 ? 0 : spieler.getAnzahlZaubersteine()) - spieler.getHand().size(), zauberberg.getSpiel().getKartenstapel().getStapel()));
            zauberberg.sendGameDataToUserPublic("UPDATEKARTEN");
        }
    }

    public int getFeldNrNextLayer(int layer, int feldNr) {
        switch (layer) {
            case 0:
                if (feldNr > 0 && feldNr < 9) {
                    return feldNr - 1;
                }
                if (feldNr > 9 && feldNr < 18) {
                    return feldNr - 3;
                }
                if (feldNr > 18 && feldNr < 27) {
                    return feldNr - 5;
                }
                if (feldNr > 27 && feldNr < 36) {
                    return feldNr - 1;
                }
                break;
            case 1:
                if (feldNr > 0 && feldNr < 7) {
                    return feldNr - 1;
                }
                if (feldNr > 7 && feldNr < 14) {
                    return feldNr - 3;
                }
                if (feldNr > 14 && feldNr < 21) {
                    return feldNr - 5;
                }
                if (feldNr > 21 && feldNr < 28) {
                    return feldNr - 7;
                }
                break;
            case 2:
                if (feldNr > 0 && feldNr < 5) {
                    return feldNr - 1;
                }
                if (feldNr > 5 && feldNr < 10) {
                    return feldNr - 3;
                }
                if (feldNr > 10 && feldNr < 15) {
                    return feldNr - 5;
                }
                if (feldNr > 15 && feldNr < 20) {
                    return feldNr - 7;
                }
                break;
            case 3:
                if (feldNr > 0 && feldNr < 3) {
                    return feldNr - 1;
                }
                if (feldNr > 3 && feldNr < 6) {
                    return feldNr - 3;
                }
                if (feldNr > 6 && feldNr < 9) {
                    return feldNr - 5;
                }
                if (feldNr > 9 && feldNr < 12) {
                    return feldNr - 7;
                }
                break;
        }
        return 0;
    }


    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getPos() {
        return feldNr;
    }

    public void setPos(int pos) {
        this.feldNr = pos;
    }

    public int getFeldNr() {
        return feldNr;
    }

    public void setFeldNr(int feldNr) {
        this.feldNr = feldNr;
    }

    public Spieler getSpieler() {
        return spieler;
    }

    public void setSpieler(Spieler spieler) {
        this.spieler = spieler;
    }

    public int getDorf() {
        return dorf;
    }

    public void setDorf(int dorf) {
        this.dorf = dorf;
    }

    public Spieler.Name getFarbe() {
        return farbe;
    }

    public void setFarbe(Spieler.Name farbe) {
        this.farbe = farbe;
    }

    public int getGlobalFeld() {
        return globalFeld;
    }

    public void setGlobalFeld(int globalFeld) {
        this.globalFeld = globalFeld;
    }

    public ArrayList<Integer> getKarten() {
        return kartenWerte;
    }

    public void setKarten(ArrayList<Integer> karten) {
        this.kartenWerte = karten;
    }

    public ArrayList<Bewegungskarte> getKartenInstances() {
        return kartenInstances;
    }

    public ArrayList<Integer> getKartenWerte() {
        return kartenWerte;
    }
}