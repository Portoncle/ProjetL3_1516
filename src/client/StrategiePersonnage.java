 package client;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;

import client.controle.Console;
import logger.LoggerProjet;
import serveur.IArene;
import serveur.element.Caracteristique;
import serveur.element.Element;
import serveur.element.Personnage;
import serveur.element.Potion;
import utilitaires.Calculs;
import utilitaires.Constantes;
/**
 * Strategie d'un personnage. 
 */
public class StrategiePersonnage {
	
	/**
	 * Console permettant d'ajouter une phrase et de recuperer le serveur 
	 * (l'arene).
	 */
	protected Console console;

	/**
	 * Cree un personnage, la console associe et sa strategie.
	 * @param ipArene ip de communication avec l'arene
	 * @param port port de communication avec l'arene
	 * @param ipConsole ip de la console du personnage
	 * @param nom nom du personnage
	 * @param groupe groupe d'etudiants du personnage
	 * @param nbTours nombre de tours pour ce personnage (si negatif, illimite)
	 * @param position position initiale du personnage dans l'arene
	 * @param logger gestionnaire de log
	 */
	public StrategiePersonnage(String ipArene, int port, String ipConsole, 
			String nom, String groupe, HashMap<Caracteristique, Integer> caracts,
			int nbTours, Point position, LoggerProjet logger) {
		
		logger.info("Lanceur", "Creation de la console...");
		
		try {
			console = new Console(ipArene, port, ipConsole, this, 
					new Personnage(nom, groupe, caracts), 
					nbTours, position, logger);
			logger.info("Lanceur", "Creation de la console reussie");
			
		} catch (Exception e) {
			logger.info("Personnage", "Erreur lors de la creation de la console : \n" + e.toString());
			e.printStackTrace();
		}
	}

	// TODO etablir une strategie afin d'evoluer dans l'arene de combat
	// une proposition de strategie (simple) est donnee ci-dessous
	/** 
	 * Decrit la strategie.
	 * Les methodes pour evoluer dans le jeu doivent etre les methodes RMI
	 * de Arene et de ConsolePersonnage. 
	 * @param voisins element voisins de cet element (elements qu'il voit)
	 * @throws RemoteException
	 */
	public void executeStrategie(Personnage perso, HashMap<Integer, Point> voisins) throws RemoteException {
		// arene
		IArene arene = console.getArene();
		
		// reference RMI de l'element courant
		int refRMI = 0;
		
		// position de l'element courant
		Point position = null;
		
		try {
			refRMI = console.getRefRMI();
			position = arene.getPosition(refRMI);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		// Choix de la stratagie a partir du nom du personnage
		switch(console.getPerso().getNom()){
			case("Altair"):{
				execStratAssassin(perso, position, voisins, arene, refRMI);
				break;
			}
			case("Shooter"):{
				execStratSniper(position, voisins, arene, refRMI);
				break;
			}
			case("Garen"):{
				execStratGuerrier(perso, position, voisins, arene, refRMI);
				break;
			}
			case("Dracula"):{
				execStratVampire(perso, position, voisins, arene, refRMI);
				break;
			}
			case("Belzebuth"):{
				execStratInvocateur(perso, position, voisins, arene, refRMI);
				break;
			}
			default: execStratPersonnage(perso, position, voisins, arene, refRMI);
		}
	}
	
	public void execStratPersonnage(Personnage perso, Point position, HashMap<Integer, Point> voisins, IArene arene, int refRMI) throws RemoteException{
		if (voisins.isEmpty()) { // je n'ai pas de voisins, j'erre
			console.setPhrase("J'erre...");
			arene.deplace(refRMI, 0); 
			
		} else {
			int refCible = Calculs.chercheElementProche(position, voisins);
			int distPlusProche = Calculs.distanceChebyshev(position, arene.getPosition(refCible));

			Element elemPlusProche = arene.elementFromRef(refCible);

			if(distPlusProche <= Constantes.DISTANCE_MIN_INTERACTION) { // si suffisamment proches
				// j'interagis directement
				if(elemPlusProche instanceof Potion) { // potion
					// ramassage
					console.setPhrase("Je ramasse une potion");
					arene.ramassePotion(refRMI, refCible);

				} else { // personnage
					// duel
					console.setPhrase("Je fais un duel avec " + elemPlusProche.getNom());
					arene.lanceAttaque(refRMI, refCible);
				}
				
			} else { // si voisins, mais plus eloignes
				// je vais vers le plus proche
				console.setPhrase("Je vais vers mon voisin " + elemPlusProche.getNom());
				arene.deplace(refRMI, refCible);
			}
		}
	}
	
	public void execStratAssassin(Personnage assassin, Point position, HashMap<Integer, Point> voisins, IArene arene, int refRMI) throws RemoteException {
		if (voisins.isEmpty()) { // je n'ai pas de voisins, j'erre
			console.setPhrase("J'erre...");
			arene.deplace(refRMI, 0); 
		}
		else{
			int refCible = Calculs.chercheElementProche(position, voisins);
			int distPlusProche = Calculs.distanceChebyshev(position, arene.getPosition(refCible));

			Element elemPlusProche = arene.elementFromRef(refCible);
			
			if(distPlusProche <= Constantes.DISTANCE_MIN_INTERACTION) { // si suffisamment proches
				// j'interagis directement
				if(elemPlusProche instanceof Potion) { // potion
					// ramassage
					console.setPhrase("Je ramasse une potion");
					arene.ramassePotion(refRMI, refCible);
				}
				else { // personnage
					// duel
					// AJOUTER NOTION COUP CRITIQUE
					
					console.setPhrase("Je fais un duel avec " + elemPlusProche.getNom());
					arene.lanceAttaque(refRMI, refCible);
				}
			}
			else { // si voisins, mais plus eloignes
				// si potion, aller vers elle
				if(elemPlusProche instanceof Potion){ //Potion
					console.setPhrase("Je vais vers mon voisin " + elemPlusProche.getNom());
					arene.deplace(refRMI, refCible);
				}
				// sinon, aller vers l'ennemi s'il a moins de vie que l'assassin
				else{
					if(elemPlusProche.getCaract(Caracteristique.VIE) < assassin.getCaract(Caracteristique.FORCE)){
						console.setPhrase("Je vais vers mon voisin " + elemPlusProche.getNom());
						arene.deplace(refRMI, refCible);
					}
					else{ // Sinon errer
						console.setPhrase("J'erre...");
						arene.deplace(refRMI, 0); 
					}
				}
			}
		}
	}

	private void execStratSniper(Point position, HashMap<Integer, Point> voisins, IArene arene, int refRMI) throws RemoteException {
		if (voisins.isEmpty()) { // je n'ai pas de voisins, j'erre
			console.setPhrase("J'erre...");
			arene.deplace(refRMI, 0); 
			
		} else {
			int refCible = Calculs.chercheElementProche(position, voisins);
			int distPlusProche = Calculs.distanceChebyshev(position, arene.getPosition(refCible));
	
			Element elemPlusProche = arene.elementFromRef(refCible);
	
			if(distPlusProche <= 20) { // si suffisamment proches
				// j'interagis directement
				if(elemPlusProche instanceof Potion && distPlusProche == Constantes.DISTANCE_MIN_INTERACTION) { // potion
					// ramassage
					console.setPhrase("Je ramasse une potion");
					arene.ramassePotion(refRMI, refCible);
	
				} else { // personnage
					// duel
					console.setPhrase("Je fais un duel avec " + elemPlusProche.getNom());
					 arene.lanceAttaqueSniper(refRMI, refCible);
					 
				}
				
			} else { // si voisins, mais plus eloignes
				// je vais vers le plus proche
				console.setPhrase("Je vais vers mon voisin " + elemPlusProche.getNom());
				arene.deplace(refRMI, refCible);
			}
		}
		
	}
	
	public void execStratGuerrier(Personnage guerrier, Point position, HashMap<Integer, Point> voisins, IArene arene, int refRMI) throws RemoteException{
		
	}
	
	public void execStratInvocateur(Personnage invocateur, Point position, HashMap<Integer, Point> voisins, IArene arene, int refRMI) throws RemoteException{
		
	}
	
	public void execStratVampire(Personnage vampire, Point position, HashMap<Integer, Point> voisins, IArene arene, int refRMI) throws RemoteException{
		
	}
}
