package serveur.element;

import java.util.HashMap;

/*
Assassin
    Sant� 30
    Force 40 + CC 50
    Init 90
    Facult�s
        Rapide
        CC possible
    Comportement
        Check point de vie ennemi : si bless�, attaque ATTENTION AU CAS DU DERNIER SURVIVANT
        Tape puis recule
        Approche d'un objet avant ennemi
*/


public class Assassin {
	private HashMap<Caracteristique, Integer> caractsValues = new HashMap<Caracteristique, Integer>();
	public Assassin(){
		caractsValues.put(Caracteristique.VIE, 30); /*Le perso à 30PDV!*/ 
		
	}
	
}

/*
 * 

package personnages;

public class Assassin extends Personnage{
	public Assassin(){
		this.setNom("Assassin");
		this.setVie(30);
		this.setForce(40);
		this.setInitiative(90);
	}
}

 */
