/**
 * Module de fonctions utilitaires. 
 * 
 * @author pbelisle 
 * @version H2017
 */
public class UtilitaireFonctions {
	
	/**
	 * Retourne un entier choisit aléatoirement entre min et max.
	 * 
	 * Le paramètre min doit être plus petit que max.  Aucune validation. 
	 * 
	 * @param min
	 * @param max
	 * @return Un nombre aléatoire entre min et max.
	 */
    public static int nbAlea(int min, int max){
    	
  	    //Stratégie, on utilise le générateur de Java qui retourne une valeur réelle
  		//entre 0 et 1[  ensuite, on le ramène dans l'intervalle min..max et on 
  		//le transforme en entier
  		return (int) (Math.random() * (max - min + 1) + min); 
  	}

	/*
	 * Utilitaire pour éviter la répétition de code lorsqu'on transforme
	 * une valeur de String à int..
	 * 
	 * Retourne Constantes.INVALIDE si la valeur ne peut se 
	 * transformer en entier.
	 */
	public static int valeurConvertitEnInt(String valeur){
		
		/*
		 * Stratégie : Nous utilisons l'exception retournée par Integer,pasrseInt
		 * pour savoir si la transformation est valide.  
		 * Peu élégant mais fonctionne très bien.
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
