package serveur.interaction;

import java.rmi.RemoteException;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;
import serveur.element.Personnage;
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
			
			int perteVie = forceAttaquant - armure ;
			
			if ( perteVie > 0) //Il a subit des degats
			{
				
			    arene.incrementeCaractElement(defenseur, Caracteristique.ARMURE, 0); //l'armure est cassee on la remet a 0
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
			else
				arene.incrementeCaractElement(defenseur, Caracteristique.ARMURE, armure- forceAttaquant);
			// initiative
			incrementeInitiative(defenseur);
			decrementeInitiative(attaquant);
			
		} catch (RemoteException e) {
			logs(Level.INFO, "\nErreur lors d'une attaque : " + e.toString());
		}
	}


	/**
	 * Incremente l'initiative du defenseur en cas de succes de l'attaque. 
	 * @param defenseur defenseur
	 * @throws RemoteException
	 */
	private void incrementeInitiative(VuePersonnage defenseur) throws RemoteException {
		arene.incrementeCaractElement(defenseur, Caracteristique.INITIATIVE, 
				Constantes.INCR_DECR_INITIATIVE_DUEL);
	}
	
	/**
	 * Decremente l'initiative de l'attaquant en cas de succes de l'attaque. 
	 * @param attaquant attaquant
	 * @throws RemoteException
	 */
	private void decrementeInitiative(VuePersonnage attaquant) throws RemoteException {
		arene.incrementeCaractElement(attaquant, Caracteristique.INITIATIVE, 
				-Constantes.INCR_DECR_INITIATIVE_DUEL);
	}
}
	

