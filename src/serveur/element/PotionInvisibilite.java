package serveur.element;

import java.util.HashMap;

public class PotionInvisibilite extends Potion{
	
	private static final long serialVersionUID = 1L;
	
	protected static HashMap<Caracteristique, Integer> caractsValues = Caracteristique.mapCaracteristiquesMin();
	
	public PotionInvisibilite(){
		super("Potion d'invisibilite","2",caractsValues);
		this.caracts.put(Caracteristique.VISIBILITE, -1);
		this.caracts.put(Caracteristique.DUREE, 15);
	}
}
