package interfacegraphique.tablemodel;

import java.util.ArrayList;

import serveur.vuelement.VueInventaire;

/**
 * TableModel de l'inventaire.
 * 
 */
public class InventaireTableModel extends ElementTableModel<VueInventaire> {
	
	private static final long serialVersionUID = 1L;

	
	public InventaireTableModel() {
		colonnes = new ArrayList<InformationColonne<VueInventaire>>();
		indexNom = 1;
		
		// ref du personnage
		colonnes.add(new InformationColonne<VueInventaire>("Ref", 40, Integer.class, new ValeurColonneRefRMIPerso())); 
		
		// nom de l'objet en inventaire (index 1)
		colonnes.add(new InformationColonne<VueInventaire>("Nom", 0, String.class, new ValeurColonneNom())); 
	}
	

	/**
	 * Affiche la reference RMI.
	 * 
	 */
	protected class ValeurColonneRefRMIPerso implements IValeurColonne<VueInventaire> {
		@Override
		public Object valeurColonne(int rowIndex, VueInventaire vue) {
			return vue.getRefRMIPerso();
		}
	}
}
