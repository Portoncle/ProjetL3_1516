package serveur.element;

import java.util.HashMap;

public class Mob extends Personnage {
	
	private static final long serialVersionUID = 1L;
	private static HashMap<Caracteristique, Integer> caractValues = new HashMap<Caracteristique, Integer>();
	
	public Mob(){
		super("Mob", "G2", caractValues);
		this.caracts.put(Caracteristique.VIE,10 );
		this.caracts.put(Caracteristique.FORCE,20 );
		this.caracts.put(Caracteristique.INITIATIVE, 40);
		this.caracts.put(Caracteristique.VITESSE, 2);
		
	}
}
