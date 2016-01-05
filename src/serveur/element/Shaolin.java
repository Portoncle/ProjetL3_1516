package serveur.element;

import java.util.HashMap;

public class Shaolin extends Personnage {
	
	private static final long serialVersionUID = 1L;
	private static HashMap<Caracteristique, Integer> caractValues = new HashMap<Caracteristique, Integer>();
	
	public Shaolin(){
		super("Jackie Chan", "G2", caractValues);
		this.caracts.put(Caracteristique.VIE,35);
		this.caracts.put(Caracteristique.FORCE,10);
		this.caracts.put(Caracteristique.INITIATIVE, 40);
		this.caracts.put(Caracteristique.VITESSE, 3);
		
	}
}
