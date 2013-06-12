package Application;

import java.io.IOException;
import java.util.Date;
import Apriori.*;

public class Main {
	public static int minSup = 10;
	public int compteurFichierAnalyse=0;
	public int compteurFichierResultat=0;
	public int nbreFichiersTransa;
	private static String directory = System.getProperty("user.dir");
	private static String path1 = directory + "\\Ressources\\Article\\articles.100p.txt"; //Liste de mots
	private static String path2 = directory + "\\Ressources\\Article\\mots.lst";//Articles
	private static String path3 = directory + "\\Ressources\\InputApriori\\transa";//Repertoire transaction
	private static String path4 = directory + "\\Ressources\\OutputApriori\\"; //Serialization
    
	public static void main(String[] args) {
		//start timer
        Date date = new Date();
        long start1 = date.getTime();
        
		Main m = new Main();
		//Preparation des articles
		PreTraitement action = new PreTraitement(path1, path2, path3);
		try {
			m.nbreFichiersTransa = action.run();
			//Code original
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//start timer
        date = new Date();
        long start2 = date.getTime();
        
		for (int i = 0; i< m.nbreFichiersTransa; i++)
		{
			//Calcul des itemsets
			AprioriCalculation ap = new AprioriCalculation(path3+i+".txt",
					path4+"Serialization"+i+".serial", 200, m.minSup);
			try {
				ap.execution();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			m.compteurFichierAnalyse++;
		}
		//Timer total
		date = new Date();
        long end2 = date.getTime();
        double time = (end2 - start2)/1000.0;
        System.out.println("Temps d'exécution total : "+time+ " secondes");
		
        
		//Mise en commun
		Decision d = new Decision(path4+"Serialization", m.nbreFichiersTransa, m.minSup);
		try {
			Resultat res = d.traitementResultat();
			// Affichage :
			System.out.println(res.toString(action.motsCles));
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Probleme fichier, lecture ou deserialization");
			e.printStackTrace();
		}
		//Timer total
		date = new Date();
        long end1 = date.getTime();
        double time1 = (end1 - start1)/1000.0;
        System.out.println("Temps d'exécution total : "+time1+ " secondes");
	}
}
