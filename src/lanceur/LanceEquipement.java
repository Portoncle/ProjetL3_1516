package lanceur;

import java.io.IOException;
import java.util.HashMap;

import logger.LoggerProjet;
import serveur.IArene;
import serveur.element.Caracteristique;
import serveur.element.Equipement;
import utilitaires.Calculs;
import utilitaires.Constantes;

public class LanceEquipement {
	
	private static String usage = "USAGE : java " + LanceEquipement.class.getName() + " [ port [ ipArene ] ]";

	public static void main(String[] args) {
		
		String groupe = "G2"; 
		
		// init des arguments
		int port = Constantes.PORT_DEFAUT;
		String ipArene = Constantes.IP_DEFAUT;
		
		if (args.length > 0) {
			if (args[0].equals("--help") || args[0].equals("-h")) {
				ErreurLancement.aide(usage);
			}
			
			if (args.length > 2) {
				ErreurLancement.TROP_ARGS.erreur(usage);
			}
			
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				ErreurLancement.PORT_NAN.erreur(usage);
			}
			
			if (args.length > 1) {
				ipArene = args[1];
			}
		}
		
		
		HashMap<Caracteristique, Integer> caractsEquip = new HashMap<Caracteristique, Integer>();
		
		int indice;
		String nom = null;
		indice = Calculs.nombreAleatoire(0, 2);
		
		// creation du logger
		LoggerProjet logger = null;
		try {
			
			if(indice == 0)
			{
				caractsEquip.put(Caracteristique.FORCE, Calculs.valeurCaracAleatoire(Caracteristique.FORCE));
				nom = "Epée";
			}
			else if(indice == 1)
			{
				caractsEquip.put(Caracteristique.ARMURE, Calculs.valeurCaracAleatoire(Caracteristique.ARMURE));
				nom = "Bouclier";
			}
			else
			{
				caractsEquip.put(Caracteristique.VITESSE, Calculs.valeurCaracAleatoire(Caracteristique.VITESSE));
				nom = "Bottes";
			}
			logger = new LoggerProjet(true, "equipement_"+nom+groupe);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(ErreurLancement.suivant);
		}
		
		// lancement de l'équipement
		try {
			IArene arene = (IArene) java.rmi.Naming.lookup(Constantes.nomRMI(ipArene, port, "Arene"));

			logger.info("Lanceur", "Lancement de l'équipement sur le serveur...");
			
			// caracteristiques de l'équipement
			
			
			
			
			
			
			// ajout de l'équipement
			arene.ajouteEquipement(new Equipement(nom, groupe, caractsEquip, indice), Calculs.positionAleatoireArene());
			logger.info("Lanceur", "Lancement de l'équipement reussi");
			
		} catch (Exception e) {
			logger.severe("Lanceur", "Erreur lancement :\n" + e.getCause());
			e.printStackTrace();
			System.exit(ErreurLancement.suivant);
		}
	}
}
