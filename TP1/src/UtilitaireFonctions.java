/**
 * Module de fonctions utilitaires. 
 * 
 * @author pbelisle 
 * @version H2017
 */
public class UtilitaireFonctions {
	
	/**
	 * Retourne un entier choisit al�atoirement entre min et max.
	 * 
	 * Le param�tre min doit �tre plus petit que max.  Aucune validation. 
	 * 
	 * @param min
	 * @param max
	 * @return Un nombre al�atoire entre min et max.
	 */
    public static int nbAlea(int min, int max){
    	
  	    //Strat�gie, on utilise le g�n�rateur de Java qui retourne une valeur r�elle
  		//entre 0 et 1[  ensuite, on le ram�ne dans l'intervalle min..max et on 
  		//le transforme en entier
  		return (int) (Math.random() * (max - min + 1) + min); 
  	}

	/*
	 * Utilitaire pour �viter la r�p�tition de code lorsqu'on transforme
	 * une valeur de String � int..
	 * 
	 * Retourne Constantes.INVALIDE si la valeur ne peut se 
	 * transformer en entier.
	 */
	public static int valeurConvertitEnInt(String valeur){
		
		/*
		 * Strat�gie : Nous utilisons l'exception retourn�e par Integer,pasrseInt
		 * pour savoir si la transformation est valide.  
		 * Peu �l�gant mais fonctionne tr�s bien.
		 */
		
		int i;
		
		try{
			i = Integer.valueOf(valeur);
			
		}catch(Exception e){
			i = Constantes.INVALIDE;
		}
		return i;
	}
	

}
