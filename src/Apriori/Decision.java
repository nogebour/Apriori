package Apriori;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

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
	
	/**
	 * Chemin absolu vers les fichiers (ajout du nombre et de .serial pour la désérialisation
	 */
	private String pathInput;

	/**
	 * Création d'un objet Décision
	 * @param pathInput String - Chemin vers les fichiers
	 * @param nbreFichier - Nombre de fichiers
	 */
	public Decision(String pathInput, int nbreFichier)
	{
		this.pathInput = pathInput;
		this.nbreFichierInput = nbreFichier;
	}
	
	/**
	 * Désérialize et affiche les résultats
	 * @return 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("resource")
	public Resultat[] deserialization() throws IOException, ClassNotFoundException
	{
		Resultat[] r = new Resultat[this.nbreFichierInput];
		for(int i = 0; i <= nbreFichierInput; i++)
		{
			FileInputStream fis = new FileInputStream(this.pathInput+i+".serial");
			// création d'un "flux objet" avec le flux fichier
			ObjectInputStream ois= new ObjectInputStream(fis);
			r[i] = (Resultat) ois.readObject();
			System.out.println(r);
			ois.close();
		}

		return r;
	}
}
