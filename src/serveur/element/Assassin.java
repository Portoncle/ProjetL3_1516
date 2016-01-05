package serveur.element;

import java.util.HashMap;

public class Assassin extends Personnage {
	
	private static final long serialVersionUID = 1L;
	private static HashMap<Caracteristique, Integer> caractValues = new HashMap<Caracteristique, Integer>();
	
	public Assassin(){
		super("Assassin", "G2", caractValues);
		this.caracts.put(Caracteristique.VIE, 30);
		this.caracts.put(Caracteristique.FORCE, 40);
		this.caracts.put(Caracteristique.INITIATIVE, 90);
		this.caracts.put(Caracteristique.VITESSE, 4);
		this.caracts.put(Caracteristique.COUPCRITIQUE, 20);
	}
}
