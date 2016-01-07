package interfacegraphique.tablemodel;

import java.util.ArrayList;

import serveur.element.Caracteristique;
import serveur.vuelement.VueEquipement;

/**
 * TableModel des Equipements.
 * 
 */
public class EquipementTableModel extends ElementTableModel<VueEquipement> {
	
	private static final long serialVersionUID = 1L;

	
	public EquipementTableModel() {
		colonnes = new ArrayList<InformationColonne<VueEquipement>>();
		indexNom = 1;
		
		// type de la Equipement
		colonnes.add(new InformationColonne<VueEquipement>("Ref", 40, Integer.class, new ValeurColonneRefRMI())); 
		
		// nom de la Equipement (index 1)
		colonnes.add(new InformationColonne<VueEquipement>("Nom", 0, String.class, new ValeurColonneNom())); 
		
		// groupe de la Equipement
		colonnes.add(new InformationColonne<VueEquipement>("Groupe", 0, String.class, new ValeurColonneGroupe()));
		
		// caracteristiques
		for(Caracteristique car : Caracteristique.values()) {
			if(car.equals(Caracteristique.ARMURE) || car.equals(Caracteristique.FORCE) || car.equals(Caracteristique.VITESSE))
			colonnes.add(new InformationColonne<VueEquipement>(car.toString(), 40, Integer.class, new ValeurColonneCaract(car)));
		}
		
	}
   
}
