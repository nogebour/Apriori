package Application;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import Apriori.*;

public class Main {
	public int minSup = 10;
	public int compteurFichierAnalyse=0;
	public int compteurFichierResultat=0;
	public int nbreFichiersTransa;
	private static String path1 = "C:\\Users\\Noel_Nicolas\\Documents\\Cours\\MLBD\\Apriori\\Ressources\\Article\\articles.100p.txt"; //Liste de mots
	private static String path2 = "C:\\Users\\Noel_Nicolas\\Documents\\Cours\\MLBD\\Apriori\\Ressources\\Article\\mots.lst";//Articles
	private static String path3 = "C:\\Users\\Noel_Nicolas\\Documents\\Cours\\MLBD\\Apriori\\Ressources\\InputApriori\\transa";//Répértoire transaction
	private static String path4 = "C:\\Users\\Noel_Nicolas\\Documents\\Cours\\MLBD\\Apriori\\Ressources\\OutputApriori\\"; //Serialization
	private static String path5 = "C:\\Users\\Noel_Nicolas\\Documents\\Cours\\MLBD\\Apriori\\Ressources\\Resultat\\";//Stockage resultat
    
	public static void main(String[] args) {
		Main m = new Main();
		//Préparation des articles
		PreTraitement action = new PreTraitement(path1, path2, path3);
		try {
			m.nbreFichiersTransa = action.run();
			//Code original
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i<=m.nbreFichiersTransa; i++)
		{
			//Calcul des itemsets
			AprioriCalculation ap = new AprioriCalculation(path3+i+".txt",
					path4+"Serialization"+i+".serial", 200, m.minSup);
			try {
				ap.execution();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			m.compteurFichierAnalyse++;
		}

		//Mise en commun
		Decision d = new Decision(path4+"Serialization", m.nbreFichiersTransa);
		try {
			d.deserialization();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Problème fichier ou lecture ou déserialization");
			e.printStackTrace();
		}
	}
}
