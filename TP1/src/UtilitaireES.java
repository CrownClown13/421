import javax.swing.JOptionPane;

/**
 * Classe qui contient les SP pour gérer les boutons d'options
 * de menu et une fonction utilitaire d'entrée-sortie (obtenirNombre).
 * 
 * S'il y a ajout de bouton au menu, il faut modifier cette classe et y ajouter
 * le comportement désiré.
 * 
 * @author Pierre Bélisle (copyright 2017)
 * @version H2017
 *
 */
public class UtilitaireES {
	
	/**
	 * Vérifie quelle option de menu a été choisie et appelle le sous-programme
	 * correspondant.
	 * 
	 * @param grille Grille à considérer pour les options
	 * @param gui La fenêtre graphique (montrer solution)
	 * @param tabSolutions Le tableau contenant les solutions
	 * @param nombre Le nombre à trouver 
	 * @param optionChoisie L'option choisie du menu.
	 * @param nbTrouve Le nombre de solutions actuellement trouvées.
	 * 
	 * @return Le nouveau tableau de solutions si la grille a été modifiée.
	 */
	public static Solution[] gererMenu(int[][] grille, 
													 GrilleGui gui, 
													 Solution[] tabSolutions, 
													 String nombre,
													 String optionChoisie,
													 int nbTrouve,
													 int nbErreurs){

		/*
		 * Stratégie : Agit simplement comme distributeur de tâche selon l'option 
		 * du menu choisi par l'utlisateur du gui.
		 *                  
		 * On a créé un sous-programme pour chaque situation même si c'était 
		 * possible de réutiliser en une seule ligne de code (voir: montrer_solution)
		 *                  
		 * Doit être modifié si on ajoute des options de menu dans 
		 * le tableau-constante Constantes.OPTIONS
		 */
		
		if(optionChoisie.equals(Constantes.
				OPTIONS[Constantes.MONTRER_SOLUTION])){
			
			montrerSolution(gui, tabSolutions, nombre);

		}
		else if (optionChoisie.equals(Constantes.
				OPTIONS[Constantes.MONTRER_INDICE])){
			
			montrerIndice(tabSolutions, nbTrouve, nbErreurs);
		}
		else if (optionChoisie.equals(Constantes.
				OPTIONS[Constantes.NOUVELLE_GRILLE])){
			
			UtilitaireGrilleGui.reinitialiserCouleur(gui);
	    	tabSolutions = nouvelleGrille(grille, nombre);
				
		}		
				
		return tabSolutions;

	}
	/**
	 * Affiche la solution dans la fenêtre graphique.
	 * 
	 * @param gui
	 * @param tabSolution
	 * @param combinaison
	 */
	public static void montrerSolution(GrilleGui gui, 
																Solution[] tabSolution,
																String combinaison){
        
		// Le booléen stipule si on veut toutes les solutions.
		UtilitaireGrilleGui.afficherSolution(gui, tabSolution, combinaison, true);

	}
	
	/**
	 * Montre le nombre de coups à trouver restant.
	 * 
	 * @param tabSolutions
	 * @param nbTrouve
	 */
	public static void montrerIndice( Solution[] tabSolutions, 
			int nbTrouve, int nbErreurs){
		
			JOptionPane.showMessageDialog(null, "Il vous en reste " + 
					(tabSolutions.length - nbTrouve) + " à trouver et vous avez fait " +
					nbErreurs + " erreurs");
	}

	
	/**
	 * Remplit la grille reçue avec les chiffres de la combinaisons.
	 * 
	 * Les chiffres sont répartits équitablement et il doit y avoir au moins
	 * MIN_SOLUTIONS * la combinaison dans la grille.
	 * 
	 * @param grille  La grille à remplir.
	 * @param tabSolution Le tableau des solutions trouvées
	 * @param nombre La combinaison à retrouver dans la grille.
	 * @return
	 */
	public static Solution[] nouvelleGrille(int[][]grille,																	   
																	  String nombre){
		Solution[] tabSolutions;
		
		// À la recherche d'une grille avec au moins MIN_SOLUTIONS.
		do{
			UtilitaireGrilleGui.obtenirGrille(grille, nombre);			
			tabSolutions = UtilitaireGrilleGui.obtenirTabSolutions(grille, nombre);
		}while(tabSolutions.length <= Constantes.MIN_SOLUTIONS);		
		
		return tabSolutions;
	}
	
		
    /**
     * Obtient et retourne un nombre entre MIN_DIGITS et MAX_DIGITS.
     * Il doit y avoir au moins 2 chiffres différents (ex: 21111, 442, 1113)
     * 
     * @return Un nombre entre MIN_DIGITS et MAX_DIGITS.
     */
	public static String obtenirNombre(){
		
		String combinaison;

		do{
			combinaison = JOptionPane.showInputDialog(
											"Entrez une combinaison (entre " +
														Constantes.MIN_DIGITS +" et " + 
															Constantes.MAX_DIGITS + " chiffres");

			
		// Peut se lire:  Termine si la combinaison est nulle ou valide. 
		// Les cas de validité étant : un nombre entier entre MIN et MAX digits.
		}while(combinaison != null && 
				
				    (UtilitaireFonctions.valeurConvertitEnInt(combinaison) == 
				    											  Constantes.INVALIDE ||
				    											  
				    combinaison.length()  < Constantes.MIN_DIGITS ||
				    combinaison.length() > Constantes.MAX_DIGITS || 
				    combinaison.equals("")));

		return combinaison;
	}

}
