package serveur.element;

import java.util.HashMap;

/**
 * Une potion: un element donnant des bonus aux caracteristiques de celui qui
 * le ramasse.
 */
public class Equipement extends Element {
	protected int indice;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur d'une potion avec un nom, le groupe qui l'a envoyee et ses 
	 * caracteristiques (ajoutees lorsqu'un Personnage ramasse cette potion).
	 * @param nom nom de la potion
	 * @param groupe groupe d'etudiants de la potion
	 * @param caracts caracteristiques de la potion
	 */
	public Equipement(String nom, String groupe, HashMap<Caracteristique, Integer> caracts, int indice) {
		super(nom, groupe, caracts);
		this.indice = indice;
	}
	
	/* Getteur */
	public int getIndice()
	{
		return this.indice;
	}
}

