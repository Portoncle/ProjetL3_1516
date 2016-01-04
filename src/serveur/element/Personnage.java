/**
 * 
 */
package serveur.element;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import utilitaires.Calculs;

/**
 * Un personnage: un element possedant des caracteristiques et etant capable
 * de jouer une strategie.
 * 
 */
public class Personnage extends Element {
	
	private static final long serialVersionUID = 1L;
	
	/* Attributs */
	private Equipement[] stuff;
	private Potion[] consommable;
	

	/**
	 * Cree un personnage avec un nom et un groupe.
	 * @param nom du personnage
	 * @param groupe d'etudiants du personnage
	 * @param caracts caracteristiques du personnage
	 */
	public Personnage(String nom, String groupe, HashMap<Caracteristique, Integer> caracts) {
		super(nom, groupe, caracts);
		this.stuff = new Equipement[3];
		this.consommable = new Potion[2];
	}
	
	/**
	 * Incremente la caracteristique donnee de la valeur donnee.
	 * Si la caracteristique n'existe pas, elle sera cree avec la valeur 
	 * donnee.
	 * @param c caracteristique
	 * @param inc increment (peut etre positif ou negatif)
	 */
	public void incrementeCaract(Caracteristique c, int inc) {		
		if(caracts.containsKey(c)) {
			caracts.put(c, Calculs.restreintCarac(c, caracts.get(c) + inc));
		} else {
			caracts.put(c, Calculs.restreintCarac(c, inc));
		}
	}
	
	/**
	 * Tue ce personnage en mettant son nombre de poins de vie a 0.
	 */
	public void tue() {
		caracts.put(Caracteristique.VIE, 0);
	}

	/**
	 * Teste si le personnage est vivant, i.e., son nombre de points de vie
	 * est strictement superieur a 0.
	 * @return vrai si le personnage est vivant, faux sinon
	 */
	public boolean estVivant() {
		Integer vie = caracts.get(Caracteristique.VIE);
		return vie != null && vie > 0;
	}

	
	/* Getteurs */
	public Equipement[] getStuff() {
		return stuff;
	}

	public Potion[] getConsommable() {
		return consommable;
	}
	
	
	/* Gestion inventaire */
	public void addStuff(Equipement e)
	{
		if(this.stuff[e.indice] == null)
		{
			this.stuff[e.indice] = e;
		}
		/* Dépendra de la classe prochainement */
		if(e.c == Caracteristique.VITESSE && e.val > this.stuff[e.indice].val)
		{
			this.stuff[e.indice] = e;
		}
	}
	
	/*public void addPotion(Potion p)
	{
		Collection fom = p.caracts.values();
		
		
	}*/
	
	void main(){
		
	}
	
	
	
	
}
