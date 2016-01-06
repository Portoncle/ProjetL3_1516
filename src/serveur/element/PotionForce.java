package serveur.element;

import java.util.HashMap;
import utilitaires.Calculs;

public class PotionForce extends Potion{
	
	private static final long serialVersionUID = 1L;
	
	protected static HashMap<Caracteristique, Integer> caractsValues = Caracteristique.mapCaracteristiquesMin();
	
	public PotionForce(){
		super("Potion de force","2",caractsValues);
		this.caracts.put(Caracteristique.FORCE, Calculs.nombreAleatoire(10, 40));
	}

}