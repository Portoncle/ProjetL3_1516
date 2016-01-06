package serveur.interaction;

import java.awt.Point;
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
public class DuelAssassin extends Duel {
	
	/**
	 * Cree une interaction de duel.
	 * @param arene arene
	 * @param attaquant attaquant
	 * @param defenseur defenseur
	 */
	public DuelAssassin(Arene arene, VuePersonnage attaquant, VuePersonnage defenseur) {
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
			
			int perteVie = forceAttaquant * armure/100 ;
			
		   
			  
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

	
	/**
	 * Retourne la position ou le defenseur se retrouvera apres ejection.
	 * @param posDefenseur position d'origine du defenseur
	 * @param positionAtt position de l'attaquant
	 * @param forceAtt force de l'attaquant
	 * @return position d'ejection du personnage
	 */
	private Point positionEjection(Point posDefenseur, Point positionAtt, int forceAtt) {
		int distance = forceVersDistance(forceAtt);
		
		// abscisses 
		int dirX = posDefenseur.x - positionAtt.x;
		
		if (dirX > 0) {
			dirX = distance;
		}
		
		if (dirX < 0) {
			dirX = -distance;
		}
		
		// ordonnees
		int dirY = posDefenseur.y - positionAtt.y;
		
		if (dirY > 0) {
			dirY = distance;
		}
		
		if (dirY < 0) {
			dirY = -distance;
		}
		
		int x = posDefenseur.x + dirX;
		int y = posDefenseur.y + dirY;
		
		return Calculs.restreintPositionArene(new Point(x, y));
	}
	
	/**
	 * Calcule la distance a laquelle le defenseur est projete suite a un coup.
	 * @param forceAtt force de l'attaquant
	 * @return distance de projection
	 */
	private int forceVersDistance(int forceAtt) {
		int max = Caracteristique.FORCE.getMax();
		
		int quart = (int) (4 * ((float) (forceAtt - 1) / max)); // -1 pour le cas force = 100
		
		return Constantes.DISTANCE_PROJECTION[quart];
	}
}
