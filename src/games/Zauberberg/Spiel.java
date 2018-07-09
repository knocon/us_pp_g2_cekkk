package games.Zauberberg;

import java.util.ArrayList;
import games.Ereignisplaettchen.*;
import games.Zauberberg.*;

public class Spiel {
	
	private Kartenstapel kartenstapel;
    private Fallgrube[] fallgrube;
    private FliegendeKarte[] fliegendeKarte;
    private Geheimgang[] geheimgang;
    private Kristallkugel[] kristallkugel;
    private Rabe[] rabe;
    private Schreckgespenst[] schreckgespenst;
    private Unwetter[] unwetter;
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

    public Kartenstapel getKartenstapel() {
        return kartenstapel;
    }

    public void setKartenstapel(Kartenstapel kartenstapel) {
        this.kartenstapel = kartenstapel;
    }

    public ArrayList<Feld> getFelder() {
        return felder;
    }

    public void setFelder(ArrayList<Feld> felder) {
        this.felder = felder;
    }

	public Fallgrube[] getFallgrube() {
		return fallgrube;
	}

	public void setFallgrube(Fallgrube[] fallgrube) {
		this.fallgrube = fallgrube;
	}

	public FliegendeKarte[] getFliegendeKarte() {
		return fliegendeKarte;
	}

	public void setFliegendeKarte(FliegendeKarte[] fliegendeKarte) {
		this.fliegendeKarte = fliegendeKarte;
	}

	public Geheimgang[] getGeheimgang() {
		return geheimgang;
	}

	public void setGeheimgang(Geheimgang[] geheimgang) {
		this.geheimgang = geheimgang;
	}

	public Kristallkugel[] getKristallkugel() {
		return kristallkugel;
	}

	public void setKristallkugel(Kristallkugel[] kristallkugel) {
		this.kristallkugel = kristallkugel;
	}

	public Rabe[] getRabe() {
		return rabe;
	}

	public void setRabe(Rabe[] rabe) {
		this.rabe = rabe;
	}

	public Schreckgespenst[] getSchreckgespenst() {
		return schreckgespenst;
	}

	public void setSchreckgespenst(Schreckgespenst[] schreckgespenst) {
		this.schreckgespenst = schreckgespenst;
	}

	public Unwetter[] getUnwetter() {
		return unwetter;
	}

	public void setUnwetter(Unwetter[] unwetter) {
		this.unwetter = unwetter;
	}
    
    
   
   

}


