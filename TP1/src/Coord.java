/**
 * Cet enregistrement représente les coordonnées 
 * possibles dans différents jeux de grille
 * 
 *@author pbelisle
 *@version hiver 2009
 *@revisité hiver 2016
 */
public class Coord {

	/*
	 * Les items choisis par l'utilisateur 
	 */
	public int ligne;
	public int colonne;

	/**
	 * Constructeur par défaut (0,0).
	 */
	public Coord(){
		
		// Initialisation explicite volontaire (bonne pratique).
		ligne = 0;
		colonne = 0;
	}
	
	/**
	 * Constructeurs par copie d'attributs,
	 * 
	 * @param ligne
	 * @param colonne
	 */
	public Coord(int ligne,int colonne){
		this.ligne = ligne;
		this.colonne = colonne;
	}

	/**
	 * Méthode qui compare les lignes et les colonnes des deux coordonnée
	 * (this et coord)
	 * 
	 * @param coord
	 * @return Si coord est égal à this (deep equals)
	 */
	public boolean equals(Coord coord){
		return coord.ligne == ligne && coord.colonne == colonne;
	}
	
		
	/*
	 * Fonction utile pour ordonner 2 coordonnées en ordre croissant 
	 * par ligne.
	 */
	public static void trierParLigne(Coord c1, Coord c2){
		
		// On se fait une copie de c1.
		int tmp = c1.ligne;
		
	
		// On place les lignes en ordre croissant si cela s'applique.
		if(c1.ligne > c2.ligne){
						
				c1.ligne = c2.ligne;
				c2.ligne = tmp;
			}
			
		}
	/*
	 * Fonction utile pour ordonner 2 coordonnées en ordre croissant 
	 * par colonne.
	 */
	public static void trierParColonne(Coord c1, Coord c2){
		
		// On se fait une copie de c1.
		int tmp = c1.colonne;
		
	
		// On place les colonnes en ordre croissant si cela s'applique.
		if(c1.colonne > c2.colonne){
						
				c1.colonne = c2.colonne;
				c2.colonne = tmp;
			}
			
		}

	/*
	 * Une version String d'un élément de type Coord
	 * entre parenthèses.
	 * 
	 * @return (ligne, colonne)
	 */
	public String toString(){
		return "(" + ligne + "," + colonne + ")";
	}	
}
