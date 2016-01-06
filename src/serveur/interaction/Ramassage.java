package serveur.interaction;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;

import serveur.element.Guerrier;

import serveur.element.Equipement;

import serveur.element.Potion;


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
					if((attaquant.getElement().isFull()) || (attaquant.getElement() instanceof Guerrier)){
						Potion p = (Potion)defenseur.getElement();
						// caracteristiques de la potion
						HashMap<Caracteristique, Integer> valeursPotion = p.getCaracts();
						
						for(Caracteristique c : valeursPotion.keySet()) {
							arene.incrementeCaractElement(attaquant, c, valeursPotion.get(c));
						}
	
						
						if(defenseur.getElement().getCaract(Caracteristique.DUREE) > 0 && ( 
								(attaquant.getElement().getPotionBu() == null) || 
								attaquant.getElement().getPotionBu() == defenseur.getElement())){
							this.attaquant.getElement().addPotionActive(p);
							logs(Level.INFO, "Potion bue !");
						}
	
						// test si mort
						if(!attaquant.getElement().estVivant()) {
							arene.setPhrase(attaquant.getRefRMI(), "Je me suis empoisonne, je meurs ");
							logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " vient de boire un poison... Mort >_<");
						}

					}
					else{
						attaquant.getElement().addPotion((Potion)defenseur.getElement());
						arene.setPhrase(attaquant.getRefRMI(), "Potion ajoutee à l'inventaire!");
					}
					arene.ejectePotion(defenseur.getRefRMI());
				}

				/* Si c'est un équipement */
				else if(defenseur.getElement() instanceof Equipement){
					attaquant.getElement().addStuff((Equipement)defenseur.getElement());
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
