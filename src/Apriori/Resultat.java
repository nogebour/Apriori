package Apriori;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;
/**
 * 
 * @author Noel Nicolas & Fauvet Morgane
 * Classe Resultat permettant de stocker les r�sultats de l'algorithme Apriori.
 * Clase s�rializable
 */
public class Resultat implements Serializable {
	/**
	 * ID servant � la v�rification lors de la d�s�rialisation
	 */
	private static final long serialVersionUID = -1071825430991686699L;
	
	/**
	 * Vector contenant les doublets de r�sultat d'Apriori
	 */
	private Vector<Doublet> results;
	
	/**
	 * Nombre de transaction dans le fichier d'origine
	 */
	@SuppressWarnings("unused")
	private int nbreTransa;
	
	/**
	 *  Classe permettant de stocker le doublet d'information (attribut, fr�quence) pour un item set 
	 * @author Noel Nicolas & Fauvet Morgane
	 *
	 */
	public class Doublet implements Serializable
	{
		/**
		 * ID servant � la v�rification lors de la d�s�rialisation
		 */
		private static final long serialVersionUID = -1555911656755045508L;
		
		/**
		 * Tableau contenant les entiers repr�sentant les attributs concern�s (itemset)
		 */
		private String[] attributs;
		
		/**
		 * La fr�quence de l'itemset
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
		 * Retourne la fr�quence de l'itemset
		 * @return la fr�quence
		 */
		public double getIndice() {
			return indice;
		}
		
		/**
		 * Fixe la fr�quence d'un itemset
		 * @param indice
		 */
		public void setIndice(float indice) {
			this.indice = indice;
		}
		
		/**
		 * Cr�ation d'un doublet itemset-fr�quence
		 * @param attributs l'itemset
		 * @param indice fr�quence de l'itemset
		 */
		public Doublet(String[] attributs, double indice) {
			super();
			this.attributs = attributs;
			this.indice = indice;
		}
		
		/**
		 * Affichage des r�sultats
		 */
		public String toString() {
			return "Doublet [attributs=" + Arrays.toString(attributs)
					+ ", indice=" + indice + "]";
		}
	}
	
	/**
	 * Cr�ation d'un classe r�sultat pour l'ex�cution de l'algorithme sur un fichier
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
	 * @param d  - fr�quence de l'itemset
	 */
	public void addResults(String argument, double d)
	{
		results.add(new Doublet(argument.split("\\s+"), d));
	}

	/**
	 * Afffichage du r�sultat
	 */
	public String toString() {
		return "Resultat [results=" + results + "]";
	}
}
