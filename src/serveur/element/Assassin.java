package serveur.element;

import java.util.HashMap;

public class Assassin extends Personnage {
	
	private static final long serialVersionUID = 1L;
	private static HashMap<Caracteristique, Integer> caractValues = new HashMap<Caracteristique, Integer>();
	
	public Assassin(){
		super("Altair", "G2", caractValues);
		this.caracts.put(Caracteristique.VIE, 100);
		this.caracts.put(Caracteristique.FORCE, 10);
		this.caracts.put(Caracteristique.INITIATIVE, 90);
		this.caracts.put(Caracteristique.VITESSE, 4);
		this.caracts.put(Caracteristique.COUPCRITIQUE, 20);
	}
}
