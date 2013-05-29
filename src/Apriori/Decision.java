package Apriori;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Decision {

	public Decision()
	{
	}
	
	public Resultat deserialization() throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream("res.serial");
		// création d'un "flux objet" avec le flux fichier
		ObjectInputStream ois= new ObjectInputStream(fis);
		Resultat r = (Resultat) ois.readObject();
		System.out.println(r);
		return r;
	}
}
