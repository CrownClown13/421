import javax.swing.JOptionPane;

/**
Le 421 est, entre autres,  un jeu de grille avec les chiffres d'un nombre entr�
 (ex:4,2 et 1).  Il s'agit pour l'utilisateur de trouver ce nombre dans un grille.
 Le nombre peut se trouver � l'horizontale, la verticale ou la diagonale.

        Exemple : http://www.20minutes.fr/services/421


Ici, il s'agit d'une variante de ce jeu. 
    - L'utilisateur choisit lui-m�me le nombre � trouver.
    - Le nombre peut �tre s�lectionn� dans un seul sens. 
                    Par exemple pour 421, l'utilisateur doit obligatoirement
                    cliquer sur un 4 en premier.
    - Le jeu comptablise les mauvais clics comme une erreur.


Dans le cadre du cours inf111
�cole de technologie sup�rieure

 @author Pierre B�lisle (copyright 2017)
 @version H2017
 */

public class Demarrer421 {

    /*
     * Strat�gie globale : On utilise les SP des diff�rents modules sp�cifiques
     * pour obtenir une grille que l'on se sert pour remplir  la fen�tre gui.
     *
     * C'est ici qu'on g�re la boucle principale qui se termine si l'utilisateur
     * quitte ou s'il r�ussit.
     *
     * De plus, on obtient s'il y a eu un clic et selon, on modifie le contenu de
     * la case de la fen�tre en cons�quence ainsi que les couleurs selon les
     * r�gles d�crites dans l'�nonc� OU c'est une option de menu, alors on
     * d�l�gue au module pr�vu.
     *
     * Le reste de la strat�gie est d�crite en commentaire-ligne au fur et � mesure.
     */
   
    public static void main(String[] args) {

        //         // Sert � obtenir le nombre de digits valide du nombre
        // s�lectionn� par l'utilisateur.
        int nbTrouve = 0;

        // Si l'utilisateur ne s�lectionne pas un bon nombre.
        int nbErreurs = 0;

        //  Sert � obtenir un tableau avec les cases faisant partie de la solution.
        Solution[] tabSolutions = null;

        // Sert � obtenir retenir les chiffres de la grille solution.
        int[][]     grille =
                new int[GrilleGui.MAX_LIGNES][GrilleGui.MAX_COLONNES];

        //Cr�ation de l'interface graphique qui permet de jouer
        // � partir de la taille de la grille pr�c�dente.  Il reste � la
        // remplir.
        GrilleGui gui = new GrilleGui(grille.length, grille[0].length,
                Constantes.COULEUR_TEXTE_DEFAUT,
                Constantes.COULEUR_FOND_DEFAUT,
                Constantes.OPTIONS,
                GrilleGui.QUITTE);

        // On obtient un premier nombre � trouver.
        String nombre = UtilitaireES.obtenirNombre();

        //  L'utilisateur n'a pas annul�.
        if(nombre != null){

            // On obtient une grille remplie et son tableau de solutions en valeur
            // de retour (voir la classe Solution et l'�nonc�).
            tabSolutions = UtilitaireES.nouvelleGrille(grille, nombre);

            //Boucle infinie qui se termine si l'utilisateur gagne ou s'il quitte
            //en cliquant sur X.
            while(true){

                // Affiche les chiffres de la grille dans le gui.
                UtilitaireGrilleGui.afficherGrilleGui(gui, grille);
               
                // Affiche seulement les nombres trouv�s par l'utilisateur.
                UtilitaireGrilleGui.afficherSolution(gui, tabSolutions, nombre, false);

                //On attend un premier clic.
                if(gui.caseEstCliquee()){

                    // Il y a eu un clic, on obtient la coordonn�e de la case.
                    Coord depart = gui.getPosition();

                    // Tant que ce n'est pas un deuxi�me clic, on colore les cases
                    // valides s�lectionn�es par rapport � la position de la souris.
                    while(!gui.caseEstCliquee()){

                        // L'aide est fournie dans une proc�dure locale.
                        fournirAide(gui, grille, tabSolutions, nombre, depart);

                    }

                    // Au sortir de la boucle, les valeurs entre le clic de d�part et celui
                    // de fin doivent former le nombre � trouver.
                    Coord fin = gui.getPosition();

                    // La fonction retourne 1 si la solution est bonne et qu'elle n'a pas
                    // d�j� �t� trouv�e.
                    if(UtilitaireGrilleGui.evaluerSelection(gui,
                            grille,
                            tabSolutions,
                            nombre,
                            depart,
                            fin)){

                        nbTrouve++;

                    }
                    else{
                        nbErreurs++;
                    }

                    //S'il a fini, bravo et on quitte.  On n'a pas fait de SP pour �a,
                    if(nbTrouve == tabSolutions.length){       

                        JOptionPane.showMessageDialog(null,
                                "Bravo!!! Vous avez trouv� les " + 
                                        tabSolutions.length + " combinaisons en " +
                                        (nbErreurs + tabSolutions.length)+ " coups");

                        System.exit(0);
                    }
                }

                //L'utilisateur a cliqu� sur un des boutons d'options
                else if(gui.optionMenuEstCliquee()){

                    // On r�cup�re la cha�ne de l'option choisie.
                    String reponse = gui.getOptionMenuClique();

                    // Si ce n'est pas le bouton de nouvelle partie, on g�re le menu 
                    // avec la proc�dure pr�vue dans l'utilitaireMenu.
                    if(!reponse.equals(
                            Constantes.OPTIONS[Constantes.NOUVELLE_PARTIE])){
                       
                        // Elle retourne un nouveau tableau de solutions s'il
                        // y a demande d'une nouvelle grille.
                        tabSolutions =     UtilitaireES.gererMenu(grille,
                                                                                             gui,
                                                                                             tabSolutions,
                                                                                             nombre,
                                                                                             reponse,
                                                                                             nbTrouve,
                                                                                             nbErreurs);
                    }

                    // Si c'est une nouvelle partie, on redemande un nombre.
                    else{

                        // On obtient un nouveau nombre.
                        String str = UtilitaireES.obtenirNombre();

                        // Si l'utilisateur n'a pas annul�, on g�n�re une nouvelle grille
                        // et on retient le nouveau nombre. 
                        // Autrement, elle reste inchang�e pour le prochain tour.
                        if(str != null && !str.equals("")){

                            UtilitaireGrilleGui.reinitialiserCouleur(gui);

                            tabSolutions = UtilitaireES.nouvelleGrille(grille, str);
                           
                            nombre = str;
                            nbErreurs = 0;
                        }
                    }
                }
            }
        }   
    }
   
    /*
     * Fonction locale existante principalement pour r�duire le code
     * dans le main().
     *
     * C'est ici qu'on appelle les fonctions utilitaires pour que l'utilisateur
     * re�oivent de l'aide au d�placement de la souris.  Les cases entre
     * depart et la position de la souris sont colori�es.
     *
     */
    public static void fournirAide(GrilleGui gui,
                                                        int[][] grille,
                                                        Solution[] tabSolutions,
                                                        String nombre,
                                                        Coord depart){
       
        UtilitaireGrilleGui.reinitialiserCouleur(gui);           
       
        UtilitaireGrilleGui.afficherSolution(gui,
                                                                     tabSolutions,
                                                                     nombre,
                                                                     false);

        // On obtient la position actuelle de la souris.
        Coord posFin = gui.getPositionSouris();                   

        // On effectue le changement de couleur
        // des cases s�lectionn�es pour fournir de l'aide.
        UtilitaireGrilleGui.changerCouleurEntre2Coord(gui, depart, posFin,
                                                            Constantes.COULEUR_FOND_AIDE);

        // Laisse le temps de voir la s�lection
        // (important sinon le gui n'intercepte pas les clics de souris
        // parce que la boucle tourne trop vite).
        UtilitaireGrilleGui.pause(1);       
    }
}