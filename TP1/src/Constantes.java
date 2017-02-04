import java.awt.Color;

/**
 * Contient les déclarations de constantes globales pour le projet de jeu 421
 * à titre de travail pratique numéro 1 (voir énoncé fourni).
 * 
 * @author Pierre Bélisle (copyright 2017)
 * @version H2017
 */
public class Constantes {
	
	//Constantes initiales pour les couleurs du jeu (valeur arbitraires)
	public static final Color COULEUR_SOLUTION = Color.RED;
	public static final Color COULEUR_FOND_DEFAUT = Color.WHITE;
	public static final Color COULEUR_FOND_AIDE = Color.LIGHT_GRAY;
	public static final Color COULEUR_TEXTE_DEFAUT = Color.BLACK;
	
	/***Les bornes de validation***/
	
	// Nombre de solutions minimum (qui correspondent
	// au nombre cherché) acceptable dans la grille .	
	public static final int MIN_SOLUTIONS = 3;
	
	// Nombre de digits maximum permis pour un nombre (arbitraire).
	public static final int MIN_DIGITS = 3;
	public static final int MAX_DIGITS = 5;
	
	// Retour d'une valeur invalide d'une fonction.
	public static final int INVALIDE = -1;
	
	//Nécessaire à l'affichage des options du menu dans GrilleGui.
	public static final String[] OPTIONS = {"Solution", 
			                                                              "Indice", 
			                                                              "Nouvelle grille", 
			                                                              "Nouvelle partie"};

	//Position dans le tableau des options du menu précédent.
	public static final int MONTRER_SOLUTION = 0;
	public static final int MONTRER_INDICE = 1;
	public static final int NOUVELLE_GRILLE = 2;
	public static final int NOUVELLE_PARTIE = 3;
	
	// Les directions possibles servant à l'algorithme de recherche.
	public static final int NORD_OUEST = 0;
	public static final int NORD = 1;
	public static final int NORD_EST = 2;
	public static final int OUEST = 3;
	public static final int EST = 4;
	public static final int SUD_OUEST = 5;
	public static final int SUD = 6;
	public static final int SUD_EST = 7;
	
}