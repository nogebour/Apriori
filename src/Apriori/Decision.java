package Apriori;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

/**
 * 
 * @author Noel Nicolas & Fauvet Morgane
 * Classe faisant la synthèse de tous les fichiers de résultat
 */
public class Decision {

	/**
	 * Nombre de fichier à analyser
	 */
	private int nbreFichierInput;
	
	private int minSup;
	
	/**
	 * Chemin absolu vers les fichiers (ajout du nombre et de .serial pour la désérialisation
	 */
	private String pathInput;

	/**
	 * Creation d'un objet Decision
	 * @param pathInput String - Chemin vers les fichiers
	 * @param nbreFichier - Nombre de fichiers
	 */
	public Decision(String pathInput, int nbreFichier, int ms)
	{
		this.pathInput = pathInput;
		this.nbreFichierInput = nbreFichier;
		this.minSup = ms+5;
	}

	/**
	 * Deserialize et affiche les resultats
	 * @return 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private Resultat[] deserialization() throws IOException, ClassNotFoundException
	{
		Resultat[] r = new Resultat[this.nbreFichierInput];
		for(int i = 0; i < nbreFichierInput; i++)
		{
			FileInputStream fis = new FileInputStream(this.pathInput+i+".serial");
			// creation d'un "flux objet" avec le flux fichier
			ObjectInputStream ois = new ObjectInputStream(fis);
			r[i] = (Resultat) ois.readObject();
			ois.close();
		}
		return r;
	}
	
	/**
	 * Mixe tous les doublets présents dans un seul Vector
	 * @return
	 */
	public Resultat traitementResultat() throws ClassNotFoundException, IOException
	{
		Resultat[] r = deserialization();
		Vector<Doublet> itemsets = new Vector<Doublet>();
		int nbreTransaTotal = 0;
		for(int i = 0; i < r.length ; i++)
		{
			nbreTransaTotal += r[i].getNbreTransa();
			for(Doublet d1 : r[i].getResults())
			{
				Doublet d2 = doubletExistant(d1, itemsets);
				if(d2 == null)
				{
					Doublet nouveau = new Doublet(d1.getAttributs(), d1.getIndice()*r[i].getNbreTransa());
					itemsets.add(nouveau);
				}
				else
				{
					int rang = itemsets.indexOf(d2);
					// et on modifie la valeur dans itemset
					Doublet nouveau = new Doublet(d2.getAttributs(), d2.getIndice()+ d1.getIndice()*r[i].getNbreTransa());
					itemsets.remove(rang);
					itemsets.add(rang, nouveau);
				}
			}
		}
		Resultat res = traitInt(itemsets, nbreTransaTotal);
		System.out.print("Nombre d'articles dans les ficheirs d'entrée : ");
		System.out.println(nbreTransaTotal);
		return res;
	}
	
	/**
	 * effectue la pondération des indices par rapport au nombre de transactions total 
	 * et élimine les doublets non nécessaire
	 * @param itemsets
	 * @param nbreTransaTot
	 * @return
	 */
	private Resultat traitInt(Vector<Doublet> itemsets, int nbreTransaTot)
	{
		Resultat res = new Resultat(itemsets.capacity());
		
		for(Doublet d : itemsets)
		{
			double indice = d.getIndice()/nbreTransaTot;
			if(indice*100 > minSup)
			{
				Doublet nouveau = new Doublet(d.getAttributs(), indice);
				res.addResults(nouveau);
			}
		}
		return res;
	}
	
	/**
	 * Indique si un doublet est présent dans un Vector de doublet 
	 * (en se basant sur les attributs uniquement et non sur l'indice)
	 * @param d1
	 * @param doublets
	 * @return
	 */
	public Doublet doubletExistant(Doublet d1, Vector<Doublet> doublets)
	{
		for(Doublet d2 : doublets)
		{
			if(d1.equals(d2))
				return d2;
		}
		return null;
	}
	
}
