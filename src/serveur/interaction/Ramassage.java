package serveur.interaction;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;

import serveur.element.Equipement;
import serveur.element.Personnage;
import serveur.element.Potion;
import serveur.element.PotionForce;
import serveur.element.PotionVie;
import serveur.vuelement.VueElement;

import serveur.vuelement.VuePersonnage;
import utilitaires.Constantes;

/**
 * Represente le ramassage d'une potion par un personnage.
 *
 */
public class Ramassage extends Interaction<VueElement<?>> {

	/**
	 * Cree une interaction de ramassage.
	 * @param arene arene
	 * @param ramasseur personnage ramassant la potion
	 * @param element potion a ramasser
	 */
	public Ramassage(Arene arene, VuePersonnage ramasseur, VueElement<?> element) {
		super(arene, ramasseur, element);
	}
	
	@Override
	public void interagit() {
		try {
			logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " essaye de rammasser " + 
					Constantes.nomRaccourciClient(defenseur));
			
			// si le personnage est vivant
			if(attaquant.getElement().estVivant() ) {
				

				/* Si c'est une potion */
				if(defenseur.getElement() instanceof Potion){
					arene.setPhrase(attaquant.getRefRMI(), "C'est une potion!");
					Potion p = (Potion)defenseur.getElement();
					// caracteristiques de la potion
					HashMap<Caracteristique, Integer> valeursPotion = p.getCaracts();
					
					//Test si l'inventaire est plein, si c'est un guerrier, si la potion est de vie ou de force
					if((attaquant.getElement().isFull()) || 
							(attaquant.getElement().getNom().compareTo("Garen") == 0) || 
							(p instanceof PotionVie) || (p instanceof PotionForce)){
						if(p.getCaract(Caracteristique.DUREE) > 0){
							if(attaquant.getElement().getCaract(Caracteristique.DUREE) > 0)
								attaquant.getElement().delPotionActive();
							attaquant.getElement().addPotionActive(p);
						}
						arene.setPhrase(attaquant.getRefRMI(), "Potion bue");
						for(Caracteristique c : valeursPotion.keySet()) {
							arene.incrementeCaractElement(attaquant, c, valeursPotion.get(c));
						}
						logs(Level.INFO, "Potion bue !");
						// test si mort
						if(!attaquant.getElement().estVivant()) {
							arene.setPhrase(attaquant.getRefRMI(), "Je me suis empoisonne, je meurs ");
							logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " vient de boire un poison... Mort >_<");
						}
						arene.ejectePotion(defenseur.getRefRMI());
					}
					else{
						attaquant.getElement().addPotion((Potion)p);
						arene.setPhrase(attaquant.getRefRMI(), "Potion ajoutee à l'inventaire!");
						arene.setPhrase(attaquant.getRefRMI(),"vide"+attaquant.getElement().isEmpty());
					}
					//suppression de la potion
					arene.ejectePotion(defenseur.getRefRMI());
				}

				/* Si c'est un équipement */
				else if(defenseur.getElement() instanceof Equipement){
					
					String str = null;
					Caracteristique cTemp = null;
					Equipement eq = (Equipement) defenseur.getElement();
					Personnage pers = (Personnage) attaquant.getElement();
					
					switch(eq.getIndice())
					{
						case 0: cTemp = Caracteristique.FORCE;
								str = "d'une epee.";
								break;
						case 1: cTemp = Caracteristique.ARMURE;
								str = "d'une armure.";
								break;
						case 2: cTemp = Caracteristique.VITESSE;
								str = "de bottes.";
								break;
					}
					
					/* Si l'equipement est interessant on le garde */
					if( pers.getStuff()[eq.getIndice()] == null || 
						pers.getStuff()[eq.getIndice()].getCaracts().get(cTemp) < eq.getCaracts().get(cTemp))
					{
						pers.addStuff(eq);
						arene.setPhrase(attaquant.getRefRMI(), "Je m'equipe " + str);
					}
					/* Sinon on le detruit */
					else
					{
						arene.setPhrase(attaquant.getRefRMI(), "Cet equipement m'est inutile, je le detruit !");
					}
					arene.ejecteEquip(defenseur.getRefRMI());
				}
			} else {
				logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " ou " + 
						Constantes.nomRaccourciClient(defenseur) + " est deja mort... Rien ne se passe");
			}
		} catch (RemoteException e) {
			logs(Level.INFO, "\nErreur lors d'un ramassage : " + e.toString());
		}
	}
}
