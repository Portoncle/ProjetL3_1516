package serveur.element;

import java.util.HashMap;

public class PotionBu extends Potion{
	private static final long serialVersionUID = 1L;

	public PotionBu(String nom, String groupe, HashMap<Caracteristique, Integer> caracts){
		super(nom, groupe, caracts);
	}
	
	public boolean fini(){
		int duree = this.getCaract(Caracteristique.DUREE);
		if(duree==1){
			return true;
		}
		this.caracts.put(Caracteristique.DUREE, (duree-1));
		return false;
	}
}
