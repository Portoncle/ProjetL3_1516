package serveur.element;

import java.util.HashMap;

public class Invocateur extends Personnage {
	
	private static final long serialVersionUID = 1L;
	private static HashMap<Caracteristique, Integer> caractValues = new HashMap<Caracteristique, Integer>();
	
	public Invocateur(){
		super("Belzebuth", "G2", caractValues);
		this.caracts.put(Caracteristique.VIE,35);
		this.caracts.put(Caracteristique.FORCE,40);
		this.caracts.put(Caracteristique.INITIATIVE, 40);
		this.caracts.put(Caracteristique.VITESSE, 2);
		
	}
}
