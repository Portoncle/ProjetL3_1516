package serveur.element;

import java.util.HashMap;

public class PotionVitesse extends Potion{
	
	private static final long serialVersionUID = 1L;
	
	protected static HashMap<Caracteristique, Integer> caractsValues = Caracteristique.mapCaracteristiquesMin();
	
	public PotionVitesse(){
		super("Potion de vitesse","2",caractsValues);
		this.caracts.put(Caracteristique.VITESSE, +3);
		this.caracts.put(Caracteristique.DUREE, 15);
	}
}
