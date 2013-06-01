package Apriori;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Decision {

	private int nbreFichierInput;
	private String pathInput;

	public Decision(String pathInput, int nbreFichier)
	{
		this.pathInput = pathInput;
		this.nbreFichierInput = nbreFichier;
	}
	
	public Resultat deserialization() throws IOException, ClassNotFoundException
	{
		Resultat r = null;
		for(int i = 0; i <= nbreFichierInput; i++)
		{
			FileInputStream fis = new FileInputStream(this.pathInput+i+".serial");
			// création d'un "flux objet" avec le flux fichier
			ObjectInputStream ois= new ObjectInputStream(fis);
			r = (Resultat) ois.readObject();
			System.out.println(r);
		}

		return r;
	}
}
