package serveur.element;

public class Equipement{
		
	
	
		protected int indice;	//Pour ranger dans la bonne case de l'inventaire
		protected int val;
		protected Caracteristique c;

		public Equipement(Caracteristique c, int val, int indice)
		{
			this.val = val;
			this.c = c;
			this.indice = indice;
		}
		
		public int getIndice()
		{
			return this.indice;
		}
		
		/* caracts.put(Caracteristique.FORCE, Calculs.nombreAleatoire(Caracteristique.FORCE)); */
}
