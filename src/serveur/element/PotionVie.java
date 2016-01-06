package serveur.element;

import java.util.HashMap;
import utilitaires.Calculs;

public class PotionVie extends Potion{
	
	private static final long serialVersionUID = 1L;
	
	protected static HashMap<Caracteristique, Integer> caractsValues = Caracteristique.mapCaracteristiquesMin();
	
	public PotionVie(){
		super("Potion de vie","2",caractsValues);
		this.caracts.put(Caracteristique.VIE, Calculs.nombreAleatoire(10, 40));
	}

}