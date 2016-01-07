package serveur.vuelement;


import java.awt.Point;

import serveur.element.Element;

/**
 * Donnees que le serveur doit conserver sur chacun de ces clients equipement.
 */
public class VueInventaire extends VueElement<Element> implements Comparable<VueInventaire> {
	
	private static final long serialVersionUID = 4227900415029065269L;

	/**
	 * Cree une vue d'une potion personnage.
	 * @param potion potion correspondante
	 * @param position position courante
	 * @param ref reference RMI
	 */
	private int refPerso;
	public VueInventaire(Element e,int refObjet, Point position, int refPerso){
		super(e,position,refObjet);
		this.refPerso = refPerso;		
	}
	
	public int getRefRMIPerso(){
		return refPerso;
	}
	@Override
	public int compareTo(VueInventaire vp2) {
		return vp2.getRefRMI() - this.getRefRMI();
	}
}
