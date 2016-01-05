package serveur.element;

import java.util.HashMap;

public class PotionInvisibilite extends Potion{
	
	private static final long serialVersionUID = 1L;
	
	protected static HashMap<Caracteristique, Integer> caractsValues = Caracteristique.mapCaracteristiquesMin();
	
	public PotionInvisibilite(){
		super("Potion d'invisibilité","2",caractsValues);
		this.caracts.put(Caracteristique.VISIBILITE, -1);
	}
}
