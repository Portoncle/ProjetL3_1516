package serveur.interaction;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;
import serveur.element.Personnage;
import serveur.element.Potion;
import serveur.element.PotionCC;
import serveur.vuelement.VuePersonnage;
import utilitaires.Calculs;
import utilitaires.Constantes;

/**
 * Represente un duel entre deux personnages.
 *
 */
public class DuelSniper extends Duel {
	
	/**
	 * Cree une interaction de duel.
	 * @param arene arene
	 * @param attaquant attaquant
	 * @param defenseur defenseur
	 */
	public DuelSniper(Arene arene, VuePersonnage attaquant, VuePersonnage defenseur) {
		super(arene, attaquant, defenseur);
	}
	
	
	public void interagit() {
		try {
			
			Personnage pAttaquant = attaquant.getElement();
			Personnage pDefenseur = defenseur.getElement();
			
			int forceAttaquant = pAttaquant.getCaract(Caracteristique.FORCE);
			int chanceDeCrit = pAttaquant.getCaract(Caracteristique.COUPCRITIQUE);
			int armure = pDefenseur.getCaract(Caracteristique.ARMURE);
			
			int perteVie = forceAttaquant - (armure/100) ;
			
			if( pAttaquant.findPotion("Potion de coup crtitique") != -1)
			{
		    	Potion po = new PotionCC();	 
				pAttaquant.getPotion( pAttaquant.findPotion("Potion de coup crtitique"));
				HashMap<Caracteristique, Integer> valeursPotion = po.getCaracts();
				
				for(Caracteristique c : valeursPotion.keySet()) {
					arene.incrementeCaractElement(attaquant, c, valeursPotion.get(c));
				}
				this.attaquant.getElement().addPotionActive(po);
				logs(Level.INFO, "Je prend une potion de coup critique!");
				
			}
			/* Il a subit des degats */
			if ( perteVie > 0)
			{
				int rnd = Calculs.nombreAleatoire (0,100);
				int chanceDeTir = Calculs.nombreAleatoire(1,4);
				if (chanceDeTir == 4 ) 
				{
					 /* Si il crit */
					if ( rnd < chanceDeCrit)
					{
						perteVie = (int) (perteVie + forceAttaquant*0.3 ) ;
						arene.incrementeCaractElement(defenseur,Caracteristique.VIE, -perteVie);
						logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " fait un coup critque ("
								+ perteVie + " points de degats) a " + Constantes.nomRaccourciClient(defenseur));
					}
					else
					{
					arene.incrementeCaractElement(defenseur, Caracteristique.VIE,- perteVie);
					
					logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " colle une beigne ("
							+ perteVie + " points de degats) a " + Constantes.nomRaccourciClient(defenseur));
					}
				}
			   else 
				 logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + "Rate son tir" );
				
			}
			else
				arene.incrementeCaractElement(defenseur, Caracteristique.ARMURE, armure- forceAttaquant);
			
			/* Initiative */
			incrementeInitiative(defenseur);
			decrementeInitiative(attaquant);
			
		} catch (RemoteException e) {
			logs(Level.INFO, "\nErreur lors d'une attaque : " + e.toString());
		}
	}
}
	

