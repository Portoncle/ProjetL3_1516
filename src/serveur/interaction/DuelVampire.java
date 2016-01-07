package serveur.interaction;

import java.awt.Point;
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
public class DuelVampire extends Duel {
	
	/**
	 * Cree une interaction de duel.
	 * @param arene arene
	 * @param attaquant attaquant
	 * @param defenseur defenseur
	 */
	public DuelVampire(Arene arene, VuePersonnage attaquant, VuePersonnage defenseur) {
		super(arene, attaquant, defenseur);
	}
	
	@Override
	public void interagit() {
		try {
			
			Personnage pAttaquant = attaquant.getElement();
			Personnage pDefenseur = defenseur.getElement();
			
			int forceAttaquant = pAttaquant.getCaract(Caracteristique.FORCE);
			int chanceDeCrit = pAttaquant.getCaract(Caracteristique.COUPCRITIQUE);
			int armure = pDefenseur.getCaract(Caracteristique.ARMURE);
			
			int perteVie = forceAttaquant - (armure/100) ;
		
			Point positionEjection = positionEjection(defenseur.getPosition(), attaquant.getPosition(), forceAttaquant);

			// ejection du defenseur
			defenseur.setPosition(positionEjection);

			// degats
			
			if ( perteVie > 0) //Il a subit des degats
			{
				
			    arene.incrementeCaractElement(defenseur, Caracteristique.ARMURE, 0); //l'armure est cassee on la remet a 0
				int rnd = Calculs.nombreAleatoire (0,100);
				if ( rnd < chanceDeCrit) //Si il crit
				{
					perteVie = (int) (perteVie + forceAttaquant*0.3 ) ;
					arene.incrementeCaractElement(defenseur,Caracteristique.VIE, -perteVie);
					arene.incrementeCaractElement(attaquant, Caracteristique.VIE, forceAttaquant/2);
					logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " a aspire  ("
							+ forceAttaquant/2 + " points de vie " );
					logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " fait un coup critque ("
							+ perteVie + " points de degats) a " + Constantes.nomRaccourciClient(defenseur));
				}
				else
				{
				arene.incrementeCaractElement(defenseur, Caracteristique.VIE,- perteVie);
				arene.incrementeCaractElement(attaquant, Caracteristique.VIE, forceAttaquant/2);
				logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " a aspire  ("
						+ forceAttaquant/2 + " points de vie " );
				logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " colle une beigne ("
						+ perteVie + " points de degats) a " + Constantes.nomRaccourciClient(defenseur));
				}
			
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
}
