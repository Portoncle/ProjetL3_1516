/**
 * 
 */
package serveur.element;

import java.util.HashMap;
import java.util.logging.Level;

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
	private Potion potionActive;
	

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
		this.potionActive = null;
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
	 * Decremente la caracteristique donnee de la valeur donnee.
	 * Si la caracteristique n'existe pas, elle sera cree avec la valeur 
	 * donnee.
	 * @param c caracteristique
	 * @param dec decrement (peut etre positif ou negatif)
	 */
	public void decrementeCaract(Caracteristique c, int dec) {		
		if(caracts.containsKey(c)) {
			caracts.put(c, Calculs.restreintCarac(c, caracts.get(c) - dec));
		} else {
			caracts.put(c, Calculs.restreintCarac(c, dec));
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
	
	public Potion getPotionBu(){
		return potionActive;
	}
	
	public void addPotionActive(Potion potionBu){
		this.potionActive = potionBu;
	}
	
	public void delPotionActive(){
		this.potionActive = null;
	}
	
	/*********************************/
	/* Gestion inventaire EQUIPEMENT */
	/*********************************/
	public void addStuff(Equipement e)
	{
			Caracteristique cTemp = null;
			switch(e.indice)
			{
				case 0: cTemp = Caracteristique.FORCE;
				case 1: cTemp = Caracteristique.ARMURE;
				case 2: cTemp = Caracteristique.VITESSE;
			}
			
			if(this.stuff[e.indice] == null)
			{
				//logs(Level.INFO, "Objet detruit");
				this.incrementeCaract(cTemp, e.caracts.get(cTemp));
				this.stuff[e.indice] = e;
			}
			else if(this.stuff[e.indice].caracts.get(cTemp) < e.caracts.get(cTemp))
			{
				this.incrementeCaract(cTemp, e.caracts.get(cTemp));
				this.stuff[e.indice] = e;
			}
	}
	
	
	
	
	/*****************************/
	/* Gestion inventaire POTION */
	/*****************************/
	public void addPotion(Potion p)
	{
		System.out.println(this.consommable[0].toString());
		if(this.consommable[0] == null)
		{
			System.out.println("fdp");
			this.consommable[0] = p;
		}
		else if(this.consommable[1] == null)
		{
			this.consommable[1] = p;
		}
	}
	
	/* Permet de savoir si on stocke ou on consomme */
	public boolean isFull()
	{
		return (this.consommable[0] != null && this.consommable[1] != null); 
	}
	
	/* Permet de savoir si le tableau est vide */
	public boolean isEmpty()
	{
		return (this.consommable[0] == null && this.consommable[1] == null); 
	}
	
	/* Supprimer potion */
	public void delPotion(Potion p)
	{
		if(this.consommable[0] == p)
		{
			this.consommable[0] = null;
		}
		if(this.consommable[1] == p)
		{
			this.consommable[1] = null;
		}
	}
	/* public void delPotion(Caracteristique c); */	
	
	
	/* Permet de savoir si un potion est présente. 
	/* Renvoie l'indice si oui, -1 sinon.
	 */
	public int findPotion(Potion p)
	{
		if(this.isEmpty())
		{
			return -1;
		}
		else if(this.consommable[0] == p)
		{
			return 0;
		}
		else
			return 1;
	}
	
	/* Teste si le personnage est visible 
	 * return vrai si le personnage est visible, faux sinon
	 */
	public boolean estVisible(){
		return caracts.get(Caracteristique.VISIBILITE) == 1;
	}
}
