package serveur.interaction;

import java.rmi.RemoteException;

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
			
			int perteVie;
			
			if ( armure == 0)
				perteVie = forceAttaquant ;
			else
				perteVie = forceAttaquant * (armure/100);
			
			if( pAttaquant.findPotion("Potion de coup crtitique") != -1)
			{
		    	Potion po = new PotionCC();	 
		    	arene.bois(po,attaquant.getRefRMI());
				logs(Level.INFO, "Je prend une potion de coup critique!");
				
			}
			if ( perteVie > 0) //Il a subit des degats
			{
				
			   
				int rnd = Calculs.nombreAleatoire (0,100);
				int chanceDeTir = Calculs.nombreAleatoire(1,4);
				if (chanceDeTir == 4 ) 
				{
					if ( rnd < chanceDeCrit) //Si il crit
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
			
		
			// initiative
			incrementeInitiative(defenseur);
			decrementeInitiative(attaquant);
			
		} catch (RemoteException e) {
			logs(Level.INFO, "\nErreur lors d'une attaque : " + e.toString());
		}
	}

}

