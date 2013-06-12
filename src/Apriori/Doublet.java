package Apriori;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;


/**
 *  Classe permettant de stocker le doublet d'information (attribut, frequence) d'un itemset 
 * @author Noel Nicolas & Fauvet Morgane
 *
 */
public class Doublet implements Serializable
{
	/**
	 * ID servant a la verification lors de la deserialisation
	 */
	private static final long serialVersionUID = -1555911656755045508L;
	
	/**
	 * Tableau contenant les entiers representant les attributs concernes (itemset)
	 */
	private int[] attributs;
	
	/**
	 * La frequence de l'itemset
	 */
	private double indice;
	
	/**
	 * Getter de l'itemset
	 * @return l'itemset
	 */
	public int[] getAttributs() {
		return attributs;
	}
	
	/**
	 * Pemet de stocker un itemset
	 * @param attributs itemset
	 */
	public void setAttributs(int[] attributs) {
		this.attributs = attributs;
	}
	
	/**
	 * Retourne la frequence de l'itemset
	 * @return la frequence
	 */
	public double getIndice() {
		return indice;
	}
	
	/**
	 * Fixe la frequence d'un itemset
	 * @param indice
	 */
	public void setIndice(float indice) {
		this.indice = indice;
	}
	
	/**
	 * Creation d'un doublet itemset-frequence
	 * @param attributs -> l'itemset
	 * @param indice -> frequence de l'itemset
	 */
	public Doublet(int[] attributs, double indice) {
		super();
		this.attributs = attributs;
		this.indice = indice;
	}
	

	
	/**
	 * Teste l'égalité de deux doublets, considérés égaux si leurs attributs sont les mêmes
	 * @param d 
	 * @return true si d et this sont équivalents, false sinon
	 */
	public boolean equals(Doublet d)
	{
		int i=0;
		for(i=0; i<this.getAttributs().length && i<d.getAttributs().length ; i++)
		{
			if(this.getAttributs()[i] != d.getAttributs()[i])
				return false;
		}
		if(i == this.getAttributs().length)
			return true;
		else
			return false;
	}
	
	/**
	* Affichage des résultats
	*/
	public String toString() {
	return "Doublet [attributs=" + Arrays.toString(attributs)
	+ ", indice=" + indice + "]";
	}
	
	/**
	 * Affichage des resultats
	 * @param motsCles 
	 */
	public String toString(HashMap<String, Integer> motsCles){
		String res = "";
		res += "Doublet [attributs= [" ;
		for(int att : attributs)
			res+=  correspondance(att, motsCles) + " "; 
		res+= "], indice=" + indice + "]";
		return res;
	}

	/**
	 * @param att: rang associé au mot
	 * @param motsCles: hashmap contenant les mots clés et leurs rangs
	 * @return le mot équivalent à l'entier de valeur att dans motsCles
	 */
	private String correspondance(int att, HashMap<String, Integer> motsCles)
	{
		for(String res: motsCles.keySet())
		{
			if(motsCles.get(res) == att-1)
			{
				return res;
			}
		}
		return null;
	}
}