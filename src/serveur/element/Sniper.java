package serveur.element;

import java.util.HashMap;

public class Sniper extends Personnage {
	
	private static final long serialVersionUID = 1L;
	private static HashMap<Caracteristique, Integer> caractValues = new HashMap<Caracteristique, Integer>();
	
	public Sniper(){
		super("Shooter", "G2", caractValues);
		this.caracts.put(Caracteristique.VIE, 20);
		this.caracts.put(Caracteristique.FORCE, 60 + (int)(Math.random() * ((100 - 60) + 1)));
		this.caracts.put(Caracteristique.INITIATIVE, 40);
		this.caracts.put(Caracteristique.VITESSE, 2);
		
	}
}
