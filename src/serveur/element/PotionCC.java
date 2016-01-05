package serveur.element;

import java.util.HashMap;

public class PotionCC extends Potion{
	
	private static final long serialVersionUID = 1L;
	
	protected static HashMap<Caracteristique, Integer> caractsValues = Caracteristique.mapCaracteristiquesMin();
	
	public PotionCC(){
		super("Potion de coup crtitique","2",caractsValues);
		this.caracts.put(Caracteristique.COUPCRITIQUE, 20);
		this.caracts.put(Caracteristique.FORCE, 30);
		this.caracts.put(Caracteristique.DUREE, 30);
	}

}
