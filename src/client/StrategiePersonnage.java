 package client;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;


import client.controle.Console;
import logger.LoggerProjet;

import serveur.IArene;
import serveur.element.Caracteristique;
import serveur.element.Element;
import serveur.element.Equipement;
import serveur.element.Personnage;
import serveur.element.Potion;
import serveur.element.PotionCC;
import serveur.element.PotionInvisibilite;
import serveur.element.PotionVitesse;

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
	public void executeStrategie(Personnage perso,HashMap<Integer, Point> voisins) throws RemoteException {
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
				execStratSniper(perso,position, voisins, arene, refRMI);
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
			case("Jackie Chan"):{
				execStratShaolin(perso, position, voisins, arene, refRMI);
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
				}
				else if(elemPlusProche instanceof Equipement){ //equipement
					// ramassage
					console.setPhrase("Je ramasse un equipement");
					arene.ramasseEquipement(refRMI, refCible);
					
				} else { // personnage
					// duel
					console.setPhrase("Je fais un duel avec " + elemPlusProche.getNom());
					arene.lanceAttaque(refRMI, refCible);
				}
				
			} else { // si voisins, mais plus eloignes
				// je vais vers le plus proche
				console.setPhrase("Je vais vers " + elemPlusProche.getNom());
				arene.deplace(refRMI, refCible);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/************/
	/* ASSASSIN */
	/************/
	public void execStratAssassin(Personnage assassin, Point position, HashMap<Integer, Point> voisins, IArene arene, int refRMI) throws RemoteException {
		
		assassin = (Personnage)arene.elementFromRef(refRMI);
	    
	    
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
				else if(elemPlusProche instanceof Equipement){ // Equipement
					console.setPhrase("Je ramasse un equipement");
					arene.ramasseEquipement(refRMI, refCible);
				}
				else{ // Personnage
					console.setPhrase("Je fais un duel avec " + elemPlusProche.getNom());
					arene.lanceAttaqueAssassin(refRMI, refCible);
				}
			}
			else { // si voisins, mais plus eloignes
				// si potion ou equipement, s'y diriger
			
				if(elemPlusProche instanceof Potion || elemPlusProche instanceof Equipement){ //Potion ou equipement
					console.setPhrase("Je vais vers " + elemPlusProche.getNom());
					arene.deplace(refRMI, refCible);
				}
				
				// sinon, aller vers l'ennemi s'il a moins de vie que la force de l'assassin
				else if(elemPlusProche.getCaract(Caracteristique.VIE) < assassin.getCaract(Caracteristique.FORCE))
				{
						console.setPhrase("Je vais vers " + elemPlusProche.getNom());
						arene.deplace(refRMI, refCible);
				}
				else{ // Sinon fuit 
						console.setPhrase("Je fuis");
						
						
					
						if ( assassin.findPotion("Potion d'invisibilite") != -1 )
						{
					
						    PotionInvisibilite po = new PotionInvisibilite ();
							arene.bois(po,refRMI );
							console.setPhrase("Je consomme une Potion d'invisibilite");
							console.setPhrase ("FindPotion "+ assassin.findPotion("Potion d'invisibilite"));
							
						}
						else if (assassin.findPotion("Potion de vitesse") != -1 )
						{
							PotionVitesse po  =new PotionVitesse();
							arene.bois(po,refRMI );
							console.setPhrase("Je consomme une Potion d'invisibilite");
							console.setPhrase ("FindPotion "+ assassin.findPotion("Potion d'invisibilite"));
						}

						arene.deplace(refRMI, 0); 
					
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**********/
	/* SNIPER */
	/**********/
	private void execStratSniper(Personnage sniper,Point position, HashMap<Integer, Point> voisins, IArene arene, int refRMI) throws RemoteException {

			
		sniper = (Personnage)arene.elementFromRef(refRMI);
		Potion po ;
		/* Pas de voisin */
		if (voisins.isEmpty()) {

			/* Si possede potion vitesse */
			if ( sniper.findPotion("Potion de vitesse") != -1 )
			{
				po = new PotionVitesse();
				arene.bois(po,refRMI );
				console.setPhrase("Je consomme une Potion de vitesse");
			
				
			}
			console.setPhrase("J'erre...");
			arene.deplace(refRMI, 0); 
			
		}
		/* Il a des voisins */
		else{
			int refCible = Calculs.chercheElementProche(position, voisins);
			int distPlusProche = Calculs.distanceChebyshev(position, arene.getPosition(refCible));
	
			Element elemPlusProche = arene.elementFromRef(refCible);

			/* Si on voit des elements */
			if(distPlusProche <= 30) { 
				/* Si un personage est assez proche, tirer */
				if(elemPlusProche instanceof Personnage && distPlusProche <= 20){
					console.setPhrase("Je fais un duel avec " + elemPlusProche.getNom());
					arene.lanceAttaqueSniper(refRMI, refCible);
				}		
				
				/* Personnage pas a portee de tir */
				else if(elemPlusProche instanceof Personnage)
				{
					console.setPhrase("Je vais vers " + elemPlusProche.getNom());
					/* Consomme invisibilite si possible */
					if ( sniper.findPotion("Potion d'invisibilite") != -1 )
					{
						 po  =new PotionInvisibilite();
						arene.bois(po, refRMI);
						console.setPhrase("Je consomme une Potion d'invisibilite");
					}
					/* Se rapproche de la cible */
					arene.deplace(refRMI, refCible);

				}
				/* Si c'est une potion */
				else if(elemPlusProche instanceof Potion){
					
					/* Ramassage ou deplacement */
					if(distPlusProche <= Constantes.DISTANCE_MIN_INTERACTION){
						console.setPhrase("Je ramasse une potion");
						arene.ramassePotion(refRMI, refCible);
					}
					else{
						console.setPhrase("Je vais vers " + elemPlusProche.getNom());
						arene.deplace(refRMI, refCible);
					}
				} 
				
				/* Si c'est un equipement */
				else{
					console.setPhrase("J'erre...");
					arene.deplace(refRMI, 0); 
				}
				
			}
			else{
				console.setPhrase("J'erre...");
				arene.deplace(refRMI, 0); 
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	/************/
	/* GUERRIER */
	/************/

	public void execStratGuerrier(Personnage guerrier, Point position, HashMap<Integer, Point> voisins, IArene arene, int refRMI) throws RemoteException{
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
				
				else if(elemPlusProche instanceof Equipement){
					console.setPhrase("Je ramasse un equipement");
					arene.ramasseEquipement(refRMI, refCible);
				}

				else{ // personnage
					// duel
					console.setPhrase("Je fais un duel avec " + elemPlusProche.getNom());
					arene.lanceAttaque(refRMI, refCible);
				}
				
			} 
			else{ // si voisins, mais plus eloignes
				// je vais vers le plus proche
				console.setPhrase("Je vais vers " + elemPlusProche.getNom());
				arene.deplace(refRMI, refCible);
			}
		}
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/***********/
	/* SHAOLIN */
	/***********/
	/* Pacifiste et tres difficile a vaincre au corps a corps */
	public void execStratShaolin(Personnage shaolin, Point position, HashMap<Integer, Point> voisins, IArene arene, int refRMI) throws RemoteException{
		
		if (voisins.isEmpty()) { // je n'ai pas de voisins, j'erre
			
			if ( shaolin.findPotion("Potion de vitesse") != -1 )
			{
				PotionVitesse po =new PotionVitesse();
				arene.bois(po,refRMI);
				console.setPhrase("Je consomme une potion de Vitesse");
				
			}
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
				else if(elemPlusProche instanceof Personnage){ // personnage
					// duel
					console.setPhrase("Je fais un duel avec " + elemPlusProche.getNom());
					arene.lanceAttaque(refRMI, refCible);
				}
				else{
					console.setPhrase("J'erre...");
					arene.deplace(refRMI, 0); 
				}
			}
			// si voisin, mais plus eloignes
			else
			{ 
				
				if(elemPlusProche instanceof Potion){
					console.setPhrase("Je vais vers " + elemPlusProche.getNom());
					arene.deplace(refRMI, refCible);
				}
				else if(elemPlusProche instanceof Equipement){
					// Si equipement, non interesse, errance
					console.setPhrase("J'erre...");
					arene.deplace(refRMI, 0); 
				}
				else if(elemPlusProche.getNom().equals("Jackie Chan")){
					// Evite le cas de deux Shaolin en attente
					console.setPhrase("Je vais vers " + elemPlusProche.getNom());
					arene.deplace(refRMI, refCible);
				}
				
				// personnage
				else{ 
					console.setPhrase("Pret pour defense");
				}
					while ( ! shaolin.isEmpty() ) //il consomme toute ces potion pour se preparer au combat
					{
						if ( shaolin.findPotion("Potion d'invisibilite") != -1 )
						{
							PotionInvisibilite po = new PotionInvisibilite();
							arene.bois(po, refRMI);
							console.setPhrase("Je consomme une Potion d'invisibilite");
							
						}
						else if ( shaolin.findPotion("Potion de coup critique") != -1 )
						{
							PotionCC po  =new PotionCC();
							arene.bois(po, refRMI);
							console.setPhrase("Je consomme de coup critique");
							
						}
					}

				}
				
				// else = personnage. Arret en attente d'attaque pour se defendre
			}
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/***********/
	/* VAMPIRE */
	/***********/
	public void execStratVampire(Personnage vampire, Point position, HashMap<Integer, Point> voisins, IArene arene, int refRMI) throws RemoteException{
		if (voisins.isEmpty()) { // je n'ai pas de voisins, j'erre
			console.setPhrase("J'erre...");
			arene.deplace(refRMI, 0); 
			
		} 
		else{
			int refCible = Calculs.chercheElementProche(position, voisins);
			int distPlusProche = Calculs.distanceChebyshev(position, arene.getPosition(refCible));

			Element elemPlusProche = arene.elementFromRef(refCible);

			if(distPlusProche <= Constantes.DISTANCE_MIN_INTERACTION && elemPlusProche instanceof Personnage) { // si suffisamment proches
				// j'interagis directement

				// duel
				console.setPhrase("Je fais un duel avec " + elemPlusProche.getNom());
				arene.lanceAttaqueVampire(refRMI, refCible);				

			} 
			else if(elemPlusProche instanceof Personnage){ // si voisins, mais plus eloignes
				// je vais vers le plus proche
				console.setPhrase("Je vais vers " + elemPlusProche.getNom());
				arene.deplace(refRMI, refCible);
			}
			else{
				console.setPhrase("J'erre...");
				arene.deplace(refRMI, 0); 
			}
		}
	}
}
