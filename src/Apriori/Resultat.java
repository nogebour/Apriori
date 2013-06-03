package Apriori;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;
/**
 * 
 * @author Noel Nicolas & Fauvet Morgane
 * Classe Resultat permettant de stocker les résultats de l'algorithme Apriori.
 * Clase sérializable
 */
public class Resultat implements Serializable {
	/**
	 * ID servant à la vérification lors de la désérialisation
	 */
	private static final long serialVersionUID = -1071825430991686699L;
	
	/**
	 * Vector contenant les doublets de résultat d'Apriori
	 */
	private Vector<Doublet> results;
	
	/**
	 * Nombre de transaction dans le fichier d'origine
	 */
	@SuppressWarnings("unused")
	private int nbreTransa;
	
	/**
	 *  Classe permettant de stocker le doublet d'information (attribut, fréquence) pour un item set 
	 * @author Noel Nicolas & Fauvet Morgane
	 *
	 */
	public class Doublet implements Serializable
	{
		/**
		 * ID servant à la vérification lors de la désérialisation
		 */
		private static final long serialVersionUID = -1555911656755045508L;
		
		/**
		 * Tableau contenant les entiers représentant les attributs concernés (itemset)
		 */
		private String[] attributs;
		
		/**
		 * La fréquence de l'itemset
		 */
		private double indice;
		
		/**
		 * Getter de l'itemset
		 * @return l'itemset
		 */
		public String[] getAttributs() {
			return attributs;
		}
		
		/**
		 * Pemet de stocker un itemset
		 * @param attributs itemset
		 */
		public void setAttributs(String[] attributs) {
			this.attributs = attributs;
		}
		
		/**
		 * Retourne la fréquence de l'itemset
		 * @return la fréquence
		 */
		public double getIndice() {
			return indice;
		}
		
		/**
		 * Fixe la fréquence d'un itemset
		 * @param indice
		 */
		public void setIndice(float indice) {
			this.indice = indice;
		}
		
		/**
		 * Création d'un doublet itemset-fréquence
		 * @param attributs l'itemset
		 * @param indice fréquence de l'itemset
		 */
		public Doublet(String[] attributs, double indice) {
			super();
			this.attributs = attributs;
			this.indice = indice;
		}
		
		/**
		 * Affichage des résultats
		 */
		public String toString() {
			return "Doublet [attributs=" + Arrays.toString(attributs)
					+ ", indice=" + indice + "]";
		}
	}
	
	/**
	 * Création d'un classe résultat pour l'exécution de l'algorithme sur un fichier
	 * @param nbreTransa nobre de transaction dans le fichier d'input
	 */
	public Resultat(int nbreTransa) {
		super();
		this.nbreTransa = nbreTransa;
		results = new Vector<>(); // Attention JDK 7
	}
	
	/**
	 * Permet d'ajouter un doublet dans la classe
	 * @param argument  - itemset
	 * @param d  - fréquence de l'itemset
	 */
	public void addResults(String argument, double d)
	{
		results.add(new Doublet(argument.split("\\s+"), d));
	}

	/**
	 * Afffichage du résultat
	 */
	public String toString() {
		return "Resultat [results=" + results + "]";
	}
}
