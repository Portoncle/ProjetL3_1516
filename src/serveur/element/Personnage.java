package serveur.element;

import java.util.HashMap;

import utilitaires.Calculs;

/**
 * Un personnage: un element possedant des caracteristiques et etant capable
 * de jouer une strategie.
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
	
	public boolean potionDejaBue(Potion potion){
		return (potionActive == potion);
	}
	
	public void addPotionActive(Potion potionBu){
		this.potionActive = potionBu;
	}
	
	public boolean potionDejaActive(){
		return this.potionActive != null;
	}
	
	public void delPotionActive(){
		
		HashMap<Caracteristique, Integer> valeursPotion = potionActive.getCaracts();
		for(Caracteristique c : valeursPotion.keySet()) {
			this.decrementeCaract(c, valeursPotion.get(c));;
		}
		this.potionActive = null;
		this.decrementeCaract(Caracteristique.DUREE, 1);
	}
	
	/*********************************/
	/* Gestion inventaire EQUIPEMENT */
	/*********************************/
	/* Permet d'ajouter des equipements s'ils sont avantageux */
	public void addStuff(Equipement e)
	{
			Caracteristique cTemp = null;
			
			switch(e.getIndice())
			{
				case 0: cTemp = Caracteristique.FORCE;
						break;
				case 1: cTemp = Caracteristique.ARMURE;
						break;
				case 2: cTemp = Caracteristique.VITESSE;
						break;
			}
			
			if(this.stuff[e.getIndice()] == null)
			{
				this.incrementeCaract(cTemp, e.caracts.get(cTemp));
				this.stuff[e.getIndice()] = e;
			}
			else
			{
				this.decrementeCaract(cTemp, this.stuff[e.getIndice()].caracts.get(cTemp));
				this.incrementeCaract(cTemp, e.caracts.get(cTemp));
				this.stuff[e.getIndice()] = e;
			}
	}
	
	
	
	
	/*****************************/
	/* Gestion inventaire POTION */
	/*****************************/
	public void addPotion(Potion p)
	{
		if(this.consommable[0] == null)
		{
			this.consommable[0] = p;
		}
		else if(this.consommable[1] == null)
		{
			this.consommable[1] = p;
		}
	}
	
	/* permet de recuperer une potion */
	public Potion getPotion ( int indice )
	{
		return this.consommable[indice];
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
	
	
	/* Permet de savoir si un potion est présente. 
	/* Renvoie l'indice si oui, -1 sinon.
	 */
	public int findPotion(String p)
	{
		if(this.isEmpty())
		{
			return -1;
		}
		else if(this.consommable[0].getNom().compareTo(p) == 0)
		{
			return 0;
		}
		else if (this.consommable[1].getNom().compareTo(p) == 0)
		{
			return 1;
		}
		else
			return -1;
	}
	
	
	
	/*********************************/
	/* Permet de gerer la visibilite */
	/*********************************/
	
	/* Teste si le personnage est visible 
	 * return vrai si le personnage est visible, faux sinon
	 */
	public boolean estVisible(){
		return caracts.get(Caracteristique.VISIBILITE) == 1;
	}
}
