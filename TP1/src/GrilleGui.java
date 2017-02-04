import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Grille de jeu rectangulaire d'au maximum MAX_LIGNES par 
 * MAX_COLONNES qui permet d'obtenir s'il y  eu un clic, la position 
 * du clic et modifier le contenu de la case (couleur et texte).
 * 
 * Il est possible aussi d'ajouter des boutons de menu.  Dans ce cas, 
 * estBoutonMenu retourne vrai et getTexteMenu retourne le texte contenu 
 * dans le bouton.  Ces boutons sont créés en bas de la fenêtre à partir d'un
 *  tableau de String fourni au constructeur (mettre null si non désiré).
 * 
 * Utile pour des TP1 en inf111 (jeux tels Sudoku, Binero, 421, ...)
 * 
 * @author Pierre Bélisle (copyright 2016)
 * @version H2017
 */
public class GrilleGui  implements Runnable{

	/*
	 * STRATÉGIE : On met des boutons dans un panneau mais on les retient 
	 * aussi dans une grille.  Une classe interne MonJButton hérite de JButton 
	 * à laquelle on ajoute des attributs pour retenir la position du bouton dans
	 *  la grille.  Tout cela pour éviter la recherche du bouton lors d'un clic 
	 *  (deux boucles en moins).
	 *                        
	 *   Un booléen permet de retenir si un bouton a été cliqué et il 
	 *  est remis à faux après une lecture de la position par son 
	 *   accesseur.
	 */

	// Limite pour voir le texte.
	public static final int MAX_LIGNES = 10;
	public static final int MAX_COLONNES = 10;
	
	// Celle du texte des boutons.
	public static final int TAILLE_CAR = 40;
	
	// Deux modes de fermeture du gui.  On quitte le programme  ou on 
	// dispose juste la fenêtre.
	 public static final int QUITTE = JFrame.EXIT_ON_CLOSE;
	 public static final int DISPOSE = JFrame.DISPOSE_ON_CLOSE;

	// On compose dans un cadre.
	private JFrame cadre = new JFrame();

	// La grille qui sera affichée (classe interne décrite à la fin).
	private MonJButton [][] grille;

	// La position en y,x du dernier clic.
	private Coord dernierClic;

	// Mis à vrai lors d'un clic et à faux dans getPosition.
	private boolean estClique;
	
	// Retenir la taille de la grille.
	private int nbLignes;
	private int nbColonnes;

	// Les couleurs.
	private Color couleurTexte;
	private Color couleurFond;

	// La taille de l'écran.
	private Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

	// Retenir le tableau des options de menu.
	private String [] tabMenus;
	
	// Pour les options de meus du panneau du bas.
	private boolean estBoutonMenu;
	
	// Vaudra le texte du bouton cliqué s'il y a eu un clic sur un des boutons 
	// de menu et il est mis à null après getOptionMenu.
	private String optionClique;
	
	// Retenir le mode de fermeture désirée.
	private int modeFermeture;
	
	// La position de la souris (sans clic) en terme de coordonnée 
	// de case (ligne, col).
	private Coord positionSouris;
	
	/**
	/**
	 * Crée une grille selon les dimensions reçues.
	 * 
	 * @param nbLignes L'axe des Y
	 * @param nbColonnes L'axe des X
	 * @param couleurTexte
	 * @param couleurFond
	 * @param tabMenus Les options du menu du bas
	 */
	public GrilleGui(int nbLignes, int nbColonnes, 
			Color couleurTexte, Color couleurFond,
			String[] tabMenus,
			int modeFermeture){
		
		// On retient la taille et les couleurs de la grille.
		this.nbLignes = (nbLignes>MAX_LIGNES)
				?MAX_LIGNES
						:nbLignes;

		this.nbColonnes = (nbColonnes>MAX_COLONNES)
				?MAX_COLONNES
						:nbColonnes;

		this.couleurFond = couleurFond;
		this.couleurTexte = couleurTexte;

		// On retient les options du menu.
		this.tabMenus = tabMenus;

		this.modeFermeture = modeFermeture;
		
		// On crée le tableau 2D (vide).
		grille = new MonJButton[nbLignes][nbColonnes];

		
		// Rien de cliqué à date.
		estClique = false;
		estBoutonMenu = false;
		
		positionSouris = new Coord(0,0);

		// On crée le panneau du bas avec les boutons de menu.

		// On affiche le cadre dans un thread.
		Thread t = new Thread(this);
		t.start();

	}

	/**
	 * Accesseur de la position du dernier clic.  Ne tient pas compte s'il y a eu 
	 * un clic ou non.
	 * 
	 * @return 
	 */
	public Coord getPosition(){

		estClique = false;
		return dernierClic;		
	}

	/**
	 * Accesseur de la position de la souris en terme de coordonnée de la grille.
	 * 
	 * @return La position de la souris en terme de coordonnée de la grille.
	 */
	public Coord getPositionSouris(){

		return positionSouris;		
	}

	/**
	 * Retourne si vrai si un des boutons de menu a été cliqué.
	 * 
	 * @return Si un des boutons de menu a été cliqué.
	 */
	public boolean optionMenuEstCliquee(){
		return estBoutonMenu;
	}
	
	/**
	 * Retourne la dernière option cliquée et null autrement.
	 * 
	 * @return Le texte dans le bouton cliqué s'il y a lieu
	 */
	public String getOptionMenuClique(){
		
		/*
		 * Stratégie : Il est important de remettre l'optionClique à null si
		 * estBoutonMenu est vrai.
		 */
		
		if(estBoutonMenu)
		    estBoutonMenu = false;
		else
			optionClique = null;
		
		return optionClique;
	}
	
	/**
	 * Retourne la valeur contenue dans une case.
	 * 
	 * @param coord La position de la case désirée.
	 * @return
	 */
	public String getValeur(int y, int x){

		return grille[y][x].getText();
	}
	
	/**
	 * Permet de modifier la valeur d'une case de la grille.
	 * 
	 * @param coord La position de la case désirée.
	 * @param valeur La nouvelle valeur.
	 */
	public void setValeur(int y, int x, String valeur){

		/*
		 * Comme c'est un Thread,  il se peut que la grille ne soit pas encore 
		 * créée alors on attend.
		 */
		if(grille[y][x]==null)
			
			// La valeur a été choisie avec test et erreur.  Comme elle n'est utilisée
			// qu'ici, on en n'a pas fait une constante.
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		 
			grille[y][x].setText(valeur);
	}

	/**
	 * Accesseur du nombre de lignes.
	 * 
	 * @return Le nombre de lignes de la grille.
	 */
	public int getNbLignes() {
		return nbLignes;
	}

	/**
	 * Accesseur du nombre de colonnes.
	 * 
	 * @return Le nombre de colonnes de la grille.
	 */
	public int getNbColonnes() {
		return nbColonnes;
	}
	
	/**
	 * Permet de changer la couleur de fond d'une case.
	 * 
	 * @param coord La position de la case
	 * @param couleur La nouvelle couleur
	 */
	public void setCouleurFond(int y, int x, Color couleurFond){
		
	        grille[y][x].setBackground(couleurFond);
	}

	/**
	 * Permet de changer la couleur de texte d'une case.
	 * 
	 * @param coord La position de la case
	 * @param couleur La nouvelle couleur
	 */
	public void setCouleurTexte(int y, int x, Color couleurTexte){
		grille[y][x].setForeground(couleurTexte);
	}
	
	/**
	 * Retourne si un des boutons a été cliqué depuis le dernier appel à 
	 * l'accesseur  de position.
	 * 
	 * @return Si un des boutons a été sélectionné
	 */
	public boolean caseEstCliquee(){
		return estClique;
	}

	@Override
	public void run() {

		// Plein écran.
		cadre.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// On quitte sur X.
		cadre.setDefaultCloseOperation(modeFermeture);

		// Obtention de la référence sur le contentPane (évite pls appels).
		JPanel panneauPrincipal = (JPanel) cadre.getContentPane();

		// Le panneau contenant la grille.
		JPanel panneauHaut = new JPanel();

		// Une disposition en grille pour celui du haut.
		panneauHaut.setLayout(new GridLayout(nbLignes, nbColonnes));

		// On ajoute les boutons vides.
		ajouterBoutons(panneauHaut);

		if(tabMenus != null){

			// Les boutons de menu s'il y en a (FlowLayout par défaut).
			JPanel panneauBas = new JPanel();		

			Dimension dh = new Dimension (d.width, (int)(d.height*.8));
			Dimension db = new Dimension (d.width, (int)(d.height*.1));

			// La dimension pour l'allure de la fenêtre.
			panneauHaut.setMinimumSize(dh);
			panneauHaut.setMaximumSize(dh);
			panneauHaut.setPreferredSize(dh);


			panneauBas.setMinimumSize(db);
			panneauBas.setMaximumSize(db);
			panneauBas.setPreferredSize(db);

			ajouterMenu(panneauBas);

			panneauPrincipal.add(panneauHaut, BorderLayout.PAGE_START);
			panneauPrincipal.add(panneauBas, BorderLayout.PAGE_END);			
		}

		else
			
			// Le panneau du haut est plein écran s'il n'y a pas de menu.
			panneauPrincipal.add(panneauHaut);

		cadre.setVisible(true);		
	}

	/**
	 * Le clic de cette case n'a plus d'effet.
	 * @param y
	 * @param x
	 */
	public void desactiverCase(int y, int x){
		
		grille[y][x].setEnabled(false);
	}
	
	/*
	 * Procédure local privée : Ajoute des boutons de menu (S'il y en a) au 
	 * panneau. 
	 * 
	 * Si on est ici, on est certains qu'il y a des options de menu
	 */
	private void ajouterMenu(JPanel panneau){

		JButton b;

		for(int i = 0; i < tabMenus.length; i++){

			b =new JButton(tabMenus[i]);

			//La dimension d'un bouton dépend de la taille de l'écran, on 
			// centre la grille.			
			b.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {

					estClique = false;
					optionClique = ((JButton)e.getSource()).getText();
					estBoutonMenu = true;
				}	
			});


			panneau.add(b);
		}

	}
	/*
	 * Ajoute les boutons dans la grille et dans le panneau.
	 * 
	 * Principalement pour la lisibilité du code.
	 */
	private void ajouterBoutons(JPanel panneau){

		for(int i = 0; i < nbLignes;i++)
			for(int j = 0; j <nbColonnes;j++){

				grille[i][j] =  new MonJButton(i,j, " ",  couleurTexte, couleurFond);
				panneau.add(grille[i][j]);
			}	
	}

	/**
	 * Classe interne qui ajoute à un JButton la position (x,y) où il se trouve 
	 * dans la grille.
	 * 
	 * Cela évite de chercher cette position lors d'un clic.
	 */
	private class MonJButton extends JButton{

		//Juste enlever le warning
		private static final long serialVersionUID = 1L;
		
		//Coordonnée ligne colonne du bouton dans le gui
		private int ligne;
		private int colonne;

		/**
		 * Constructeur avec la position du bouotn et sa valeur.
		 * @param y La position en ligne
		 * @param x La position en colonne
		 * @param valeur La valeur à afficher
		 */
		private MonJButton(int ligne, int colonne, 
				String valeur, 
				Color couleurTexte, 
				Color couleurFond){

			// On passe le texte à la classe parent.
			super(valeur);

			this.ligne = ligne;
			this.colonne = colonne;

			setForeground(couleurTexte);
			setBackground(couleurFond);
			setFont(new Font("sans serif", Font.BOLD, TAILLE_CAR));

			// La dimension d'un bouton dépend de la taille de l'écran,
			// on centre la grille.
			addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {

					// On obtient la référence du bouton cliqué.
					MonJButton b = (MonJButton) e.getSource();

					// On retient la position du clic.
					dernierClic =  new Coord();
					dernierClic.ligne = b.ligne;
					dernierClic.colonne = b.colonne;

					estClique = true;		
					estBoutonMenu = false;
				}	
			});

			addMouseListener(new MouseAdapter(){

				@Override
				public void mouseEntered(MouseEvent e) {

					/*
					 * La souris est entré dans l'espace d'un bouton, on retient la
					 * coordonnée de cette case dans l'attribut positionSouris.
					 */
					MonJButton b = (MonJButton) e.getSource();

					positionSouris = new Coord(b.ligne, b.colonne);
				}
			});
			
		}
	}
}