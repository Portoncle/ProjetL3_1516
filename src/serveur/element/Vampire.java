package serveur.element;

import java.util.HashMap;

public class Vampire extends Personnage {
	
	private static final long serialVersionUID = 1L;
	private static HashMap<Caracteristique, Integer> caractValues = new HashMap<Caracteristique, Integer>();
	
	public Vampire(){
		super("Dracula", "G2", caractValues);
		this.caracts.put(Caracteristique.VIE, 40);
		this.caracts.put(Caracteristique.FORCE,30 + (int)(Math.random() * ((40- 30) + 1)) );
		this.caracts.put(Caracteristique.INITIATIVE, 70);
		this.caracts.put(Caracteristique.VITESSE, 2);
		
	}
}
