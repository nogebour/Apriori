package Apriori;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;
/**
 * 
 * @author Noel Nicolas & Fauvet Morgane
 * Classe Resultat permettant de stocker les resultats de l'algorithme Apriori.
 * Clase serializable
 */
public class Resultat implements Serializable {
	/**
	 * ID servant a la verification lors de la deserialisation
	 */
	private static final long serialVersionUID = -1071825430991686699L;
	
	/**
	 * Vector contenant les doublets de resultat d'Apriori
	 */
	private Vector<Doublet> results;
	
	public Vector<Doublet> getResults() {
		return results;
	}

	/**
	 * Nombre de transaction dans le fichier d'origine
	 */
	private int nbreTransa;
	
	public int getNbreTransa() {
		return nbreTransa;
	}

	/**
	 * Creation d'un classe resultat pour l'execution de l'algorithme sur un fichier
	 * @param nbreTransa -> nombre de transaction dans le fichier d'input
	 */
	public Resultat(int nbreTransa) {
		super();
		this.nbreTransa = nbreTransa;
		results = new Vector<>(); // Attention JDK 7
	}
	
	/**
	 * Permet d'ajouter un doublet dans la classe
	 * @param argument -> itemset
	 * @param d -> frequence de l'itemset
	 */
	public void addResults(String argument, double d)
	{
		String[] att = argument.split("\\s+");
		int[] attributs = new int[att.length];
		for(int i = 0; i<att.length; i++)
		{
			attributs[i] = Integer.parseInt(att[i]);
		}
		results.add(new Doublet(attributs, d));
	}
	
	/**
	 * Permet d'ajouter un doublet dans la classe
	 * @param argument -> itemset
	 * @param d -> frequence de l'itemset
	 */
	public void addResults(Doublet d)
	{
		results.add(d);
	}

	/**
	 * Afffichage du resultat
	 */
	public String toString() {
		String res = "Resultat [results= " ;
		for(Doublet d : results)
			res+=d.toString() + ", ";
		res += " ]";
		return res;
	}

	public String toString(HashMap<String, Integer> motsCles)
	{
		String res = "Resultat [results= " ;
		for(Doublet d : results)
			res+=d.toString(motsCles) + ", ";
		res += " ]";
		return res;
	}
}
