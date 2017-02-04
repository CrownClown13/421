import java.awt.Color;


public class UtilitaireGrilleGui {
	
	
	public static int [][] obtenirGrille(int[][] grille,String nombre){
		
		for(int i=0; i < grille.length; i++){
	
			for(int j=0; j < grille.length; j++){
				
				grille[i][j]= UtilitaireFonctions.nbAlea(3, 5);
			}
			
		}
		
		return grille;
	}
	
	public static void afficherGrilleGui(GrilleGui gui,int[][] grille){
		
		for(int i=0; i < grille.length; i++){
			
			for(int j=0; j < grille.length; j++){
				
				
				
			}
			
		}
		
	}
	
	
	public static void afficherSolution(GrilleGui gui, Solution[] tabSolu, String combinaison, boolean x){
		
		
		
	}
	
	
	public static Solution [] obtenirTabSolutions(int[][] grille, String nombre ){
		
		Solution [] tabSolution = new Solution[10];
		
		
		return tabSolution;
		
	}
	
	public static void changerCouleurEntre2Coord(GrilleGui gui, Coord depart,Coord posFin, Color couleur){
		
		
	}
	
	public static boolean evaluerSelection(GrilleGui gui, int[][] grille,Solution[] tabSolution, String nombre, Coord depart, Coord Fin){
		boolean x=true;
		
		return x;
	}
	
	public static void reinitialiserCouleur(GrilleGui gui){
		
		
	}
	
	
	
	
	 /**
     * Effectue une pause en mode multi-tâches.
     *
     * @param temps En millisecondes (2000 == 1seconde).
     */
    public static void pause(int temps){

        try {
            Thread.sleep(temps);
        } catch (InterruptedException e) {
            
            e.printStackTrace();
        }        
    }
}
