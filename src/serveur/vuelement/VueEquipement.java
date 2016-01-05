package serveur.vuelement;

import java.awt.Point;

import serveur.element.Equipement;

/**
 * Donnees que le serveur doit conserver sur chacun de ces clients equipement.
 */
public class VueEquipement extends VueElement<Equipement> implements Comparable<VueEquipement> {
	
	private static final long serialVersionUID = 4227900415029065269L;

	/**
	 * Cree une vue d'une potion personnage.
	 * @param potion potion correspondante
	 * @param position position courante
	 * @param ref reference RMI
	 */
	public VueEquipement(Equipement eq, Point position, int ref) {
		super(eq, position, ref);
	}

	@Override
	public int compareTo(VueEquipement vp2) {
		return vp2.getRefRMI() - this.getRefRMI();
	}
}
