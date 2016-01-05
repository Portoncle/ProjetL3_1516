package serveur.element;

import java.util.HashMap;

public class Guerrier extends Personnage {
	
	private static final long serialVersionUID = 1L;
	private static HashMap<Caracteristique, Integer> caractValues = new HashMap<Caracteristique, Integer>();
	
	public Guerrier(){
		super("Garen", "G2", caractValues);
		this.caracts.put(Caracteristique.VIE, 100);
		this.caracts.put(Caracteristique.FORCE,60 + (int)(Math.random() * ((80 - 60) + 1)) );
		this.caracts.put(Caracteristique.INITIATIVE, 20);
		this.caracts.put(Caracteristique.VITESSE, 1);
		this.caracts.put(Caracteristique.ARMURE, 40);
		
	}
}
