package Apriori;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * 
 * @author Noel Nicolas & Fauvet Morgane
 * Classe faisant la synth�se de tous les fichiers de r�sultat
 */
public class Decision {

	/**
	 * Nombre de fichier � analyser
	 */
	private int nbreFichierInput;
	
	/**
	 * Chemin absolu vers les fichiers (ajout du nombre et de .serial pour la d�s�rialisation
	 */
	private String pathInput;

	/**
	 * Cr�ation d'un objet D�cision
	 * @param pathInput String - Chemin vers les fichiers
	 * @param nbreFichier - Nombre de fichiers
	 */
	public Decision(String pathInput, int nbreFichier)
	{
		this.pathInput = pathInput;
		this.nbreFichierInput = nbreFichier;
	}
	
	/**
	 * D�s�rialize et affiche les r�sultats
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
			// cr�ation d'un "flux objet" avec le flux fichier
			ObjectInputStream ois= new ObjectInputStream(fis);
			r[i] = (Resultat) ois.readObject();
			System.out.println(r);
			ois.close();
		}

		return r;
	}
}
