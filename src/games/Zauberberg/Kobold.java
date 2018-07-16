package games.Zauberberg;

import java.util.ArrayList;
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
    private ArrayList<Integer> kartenWerte = new ArrayList<Integer>();
    Feld tempFeld;
    Gson gson = new GsonBuilder().create();
    private static int anz;

    //Methoden bewegen
    //TODO Position im Grid
    public Kobold(int nummer, Spieler spieler) {
        this.dorf = anz % 4; //gleichmäßiges Verteilen der Kobolde (im Uhrzeigersinn)
        this.layer = -1;
        this.feldNr = this.dorf;
        this.spieler = spieler;
        this.nummer = nummer;
        this.farbe = spieler.getFarbName();
        anz++;
    }

    public void kartenLegen(int kartenWert1, int kartenWert2) {
        this.kartenWerte.add(kartenWert1);
        this.kartenWerte.add(kartenWert2);
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
            if(this.getCorrectFeld(this.getLayer(), this.getFeldNr()).getKobolde().size()==2) {
        	if(this.getCorrectFeld(this.getLayer(), this.getFeldNr()).getKobolde().get(1).equals(this)) {
        	    if(this.getCorrectFeld(this.getLayer()+1, this.getFeldNrNextLayer(0, this.getFeldNr())).getKobolde().size()==0) {
        		arrayLayerFeld.add(1); 
        		arrayLayerFeld.add(this.getFeldNrNextLayer(0, this.getFeldNr())); 
        	    }
        	}        	
            }          
            arrayLayerFeld.add((((this.getFeldNr() + laufweg) % 36) + 36) % 36);  //vorw�rts
            arrayLayerFeld.add((((this.getFeldNr() - laufweg) % 36) + 36) % 36); // r�ckw�rts
        } else if (this.getLayer() == 1) {
            if(this.getCorrectFeld(this.getLayer(), this.getFeldNr()).getKobolde().size()==2) {
        	if(this.getCorrectFeld(this.getLayer(), this.getFeldNr()).getKobolde().get(1).equals(this)) {
        	    if(this.getCorrectFeld(this.getLayer()+1, this.getFeldNrNextLayer(1, this.getFeldNr())).getKobolde().size()==0) {
        		arrayLayerFeld.add(2); 
        		arrayLayerFeld.add(this.getFeldNrNextLayer(1, this.getFeldNr())); 
        	    }
        	}        	
            }            
            arrayLayerFeld.add((((this.getFeldNr() + laufweg) % 28) + 28) % 28); //vorw�rts
            arrayLayerFeld.add((((this.getFeldNr() - laufweg) % 28) + 28) % 28);  // r�ckw�rts
        } else if (this.getLayer() == 2) {
            if(this.getCorrectFeld(this.getLayer(), this.getFeldNr()).getKobolde().size()==2) {
        	if(this.getCorrectFeld(this.getLayer(), this.getFeldNr()).getKobolde().get(1).equals(this)) {
        	    if(this.getCorrectFeld(this.getLayer()+1, this.getFeldNrNextLayer(2, this.getFeldNr())).getKobolde().size()==0) {
        		arrayLayerFeld.add(3); 
        		arrayLayerFeld.add(this.getFeldNrNextLayer(2, this.getFeldNr())); 
        	    }
        	}        	
            }           
            arrayLayerFeld.add((((this.getFeldNr() + laufweg) % 20) + 20) % 20);  //vorw�rts
            arrayLayerFeld.add((((this.getFeldNr() - laufweg) % 20) + 20) % 20); // r�ckw�rts
        } else if (this.getLayer() == 3) {
            if(this.getCorrectFeld(this.getLayer(), this.getFeldNr()).getKobolde().size()==2) {
        	if(this.getCorrectFeld(this.getLayer(), this.getFeldNr()).getKobolde().get(1).equals(this)) {
        	    if(this.getCorrectFeld(this.getLayer()+1, this.getFeldNrNextLayer(3, this.getFeldNr())).getKobolde().size()==0) {
        		arrayLayerFeld.add(4); 
        		arrayLayerFeld.add(this.getFeldNrNextLayer(3, this.getFeldNr())); 
        	    }
        	}        	
            } 
            arrayLayerFeld.add((((this.getFeldNr() + laufweg) % 12) + 12) % 12);  //vorw�rts
            arrayLayerFeld.add((((this.getFeldNr() - laufweg) % 12) + 12) % 12);  // r�ckw�rts
        }
        if (arrayLayerFeld.size() == 0) {
            this.zauberberg.setRecentInfoText("Du kannst den Kobold mit der Nummer " + this.getNummer() + " nicht bewegen.");
            this.zauberberg.sendGameDataToUserPublic("PUSHINFOTEXT");
        } else {
            this.zauberberg.setFelderWaehlen(gson.toJson(arrayLayerFeld, ArrayList.class));
            this.zauberberg.sendGameDataToUserPublic("FELDERANBIETEN");
            kartenWerte.remove(0);
        }
    }
    public Feld getCorrectFeld(int layer, int feldNr) {
	for(Feld f : zauberberg.getSpiel().getFelder()) {
	    if(f.getLayer() == layer && f.getFeldNr() == feldNr) {
		return f; 
	    }
	    break;
	}
	return null;
    }

    public void bewegen(int layer, int feldNr) {
	int layerVorBewegen = this.getLayer(); 
	int feldNrVorBewegen = this.getFeldNr(); 	
	Kobold temp; 
	
        //vom alten Feld entfernen
        for (Feld f : zauberberg.getSpiel().getFelder()) {
            if (f.getLayer() == this.getLayer() && f.getFeldNr() == this.getFeldNr()) {
                f.getKobolde().remove(this);
                break;
            }
        }
        
        //auf neues Feld setzen + Koboldliste einfügen für den Fall, dass da (k)ein Kobold sitzt 
        if(getCorrectFeld(layer, feldNr).getKobolde().size()<2) {
            this.setFeldNr(feldNr);
            this.setLayer(layer); 
            getCorrectFeld(layer,feldNr).getKobolde().add(this); 
        }
        
        if(getCorrectFeld(layer, feldNr).getKobolde().size()==2) {
            temp = getCorrectFeld(layer,feldNr).getKobolde().get(1); 
            getCorrectFeld(layer,feldNr).getKobolde().remove(1); 
            temp.bewegen(temp.getLayer(), temp.getFeldNr()+1); //entwerde +1/-1 "Richtung"
            this.setFeldNr(feldNr);
            this.setLayer(layer); 
            getCorrectFeld(layer,feldNr).getKobolde().add(this); 
        }
        
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
                			this.getSpieler().setAnzahlZaubersteine(this.getSpieler().getAnzahlZaubersteine() + 1);
                			((Zauberstein) f).setAufFeld(false);
                		}
                		break; 
                	case "Fallgrube":
                        Fallgrube fallgrube = (Fallgrube) f;
                        zauberberg.setRecentInfoText(this.spieler.getFarbName() + " ist auf das Ereignis Fallgrube gekommen. Du faellst hinab.");
                        zauberberg.sendGameDataToClientsPublic("PUSHINFOTXT");
                        fallgrube.execute(this, this.zauberberg.getSpiel().getFelder());
                        break;
                    case "Geheimgang":
                        Geheimgang geheimgang = (Geheimgang) f;
                        zauberberg.setRecentInfoText(this.spieler.getFarbName() + " ist auf das Ereignis Geheimgang gekommen. Du steigst auf.");
                        zauberberg.sendGameDataToClientsPublic("PUSHINFOTXT");
                        geheimgang.execute(this, this.zauberberg.getSpiel().getFelder());
                        break;
                    case "Schreckgespenst":
                        Schreckgespenst schreckgespenst = (Schreckgespenst) f;
                        zauberberg.setRecentInfoText(this.spieler.getFarbName() + " ist auf das Ereignis Fallgrube gekommen. Zurueck ins Dorf.");
                        zauberberg.sendGameDataToClientsPublic("PUSHINFOTXT");
                        schreckgespenst.execute(this);
                        break;
                    case "Rabe":
                        Rabe rabe = (Rabe) f;
                        zauberberg.setRecentInfoText(this.spieler.getFarbName() + " ist auf das Ereignis Rabe gekommen. Waehle 1 Spieler, dem der Zauberstein entfernt wird.");
                        zauberberg.sendGameDataToClientsPublic("PUSHINFOTXT");
                        rabe.execute(this.getSpieler()); // TODO hier muss die Ereignisanfrage stattfinden, so ist das ja falsch, dass der Rabe auf den aktuellen Spieler andgewendet wird
                        break;
                    case "Fliegende Karte":
                    	FliegendeKarte fliegendeKarte = (FliegendeKarte) f;
                    	zauberberg.setRecentInfoText(this.spieler.getFarbName() + " ist auf das Ereignis Fliegende Karte gekommen. Du erhaelst 1 Karte und hast die Moeglichkeit, eine Karte auszuspielen.");
                    	zauberberg.sendGameDataToClientsPublic("PUSHINFOTXT");
                    	fliegendeKarte.execute(this.getSpieler(), zauberberg.getSpiel().getKartenstapel());
                        break;
                    case "Kristallkugel":
                        zauberberg.setRecentInfoText(this.spieler.getFarbName() + " ist auf das Ereignis Kristallkugel gekommen. Du darfst 3 Karten deiner Wahl aufdecken.");
                        zauberberg.sendGameDataToClientsPublic("PUSHINFOTXT");
                        // TODO Kristallkugel Karte aus dem Spiel nehmen
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
                        break;
                }               
                break;
            }
        }
        if (this.kartenWerte.size() != 0) {
            this.karteSpielen();
        } else { // Zug ist beendet
            zauberberg.zugBeenden();
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

    public Zauberberg getZauberberg() {
        return zauberberg;
    }

    public void setZauberberg(Zauberberg zauberberg) {
        this.zauberberg = zauberberg;
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

}