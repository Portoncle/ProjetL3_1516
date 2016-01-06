package serveur.interaction;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;
import serveur.element.Equipement;
import serveur.element.Potion;
import serveur.element.Guerrier;
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
				if(defenseur.getElement() instanceof Potion){
					if((attaquant.getElement().isFull()) || (attaquant.getElement() instanceof Guerrier)){
						Potion p = (Potion)defenseur.getElement();
						// caracteristiques de la potion
						HashMap<Caracteristique, Integer> valeursPotion = p.getCaracts();
						
						
	
						//Test si la potion a un duree et l'ajoute a sa potion active si c'est le cas
						if(defenseur.getElement().getCaract(Caracteristique.DUREE) > 0 &&  
								(attaquant.getElement().potionDejaActive())){
							this.attaquant.getElement().addPotionActive(p);
							for(Caracteristique c : valeursPotion.keySet()) {
								arene.incrementeCaractElement(attaquant, c, valeursPotion.get(c));
							}
							arene.setPhrase(attaquant.getRefRMI(), "Potion ACTIVE!");
						}
						
						//Test si la potion a deja ete bue et ajoute une duree si c'est le cas
						else if((defenseur.getElement().getCaract(Caracteristique.DUREE) > 0) && 
								(attaquant.getElement().potionDejaBue((Potion)defenseur.getElement()))){
							int tmp = this.defenseur.getElement().getCaract(Caracteristique.DUREE);
							this.attaquant.getElement().incrementeCaract(Caracteristique.DUREE, tmp);
							arene.setPhrase(attaquant.getRefRMI(), "Potion deja bue!");
						}
						logs(Level.INFO, "Potion bue !");
						// test si mort
						if(!attaquant.getElement().estVivant()) {
							arene.setPhrase(attaquant.getRefRMI(), "Je me suis empoisonne, je meurs ");
							logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " vient de boire un poison... Mort >_<");
						}
					}
					else{
						attaquant.getElement().addPotion((Potion)defenseur.getElement());
						arene.setPhrase(attaquant.getRefRMI(), "Potion ajouté à l'inventaire!");
					}
					//suppression de la potion
					arene.ejectePotion(defenseur.getRefRMI());
				}
				else if(defenseur.getElement() instanceof Equipement){
					attaquant.getElement().addStuff((Equipement)defenseur.getElement());
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
