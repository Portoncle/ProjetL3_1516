package interfacegraphique.tablemodel;

import java.util.ArrayList;

import interfacegraphique.tablemodel.ElementTableModel.ValeurColonneCaract;
import interfacegraphique.tablemodel.ElementTableModel.ValeurColonneGroupe;
import interfacegraphique.tablemodel.ElementTableModel.ValeurColonneNom;
import interfacegraphique.tablemodel.ElementTableModel.ValeurColonnePhrase;
import interfacegraphique.tablemodel.ElementTableModel.ValeurColonneRefRMI;
import serveur.element.Caracteristique;
import serveur.vuelement.VuePotion;

public class InventaireTableModel extends ElementTableModel<VuePotion> {
	
	private static final long serialVersionUID = 1L;

	
	public InventaireTableModel() {
		colonnes = new ArrayList<InformationColonne<VuePotion>>();
		indexNom = 1;
		
		// type de la potion
		colonnes.add(new InformationColonne<VuePotion>("Ref", 40, Integer.class, new ValeurColonneRefRMI())); 
		
		// nom de la potion (index 1)
		colonnes.add(new InformationColonne<VuePotion>("Nom", 0, String.class, new ValeurColonneNom())); 
		
		// groupe de la potion
		colonnes.add(new InformationColonne<VuePotion>("Groupe", 0, String.class, new ValeurColonneGroupe()));
		
		// caracteristiques
		for(Caracteristique car : Caracteristique.values()) {
			colonnes.add(new InformationColonne<VuePotion>(car.toString(), 40, Integer.class, new ValeurColonneCaract(car)));
		}
		
		// phrase du personnage
		colonnes.add(new InformationColonne<VuePotion>("Phrase", 300, String.class, new ValeurColonnePhrase())); 
	}
}
   
