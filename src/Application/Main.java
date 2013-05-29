package Application;
import java.io.IOException;

import Apriori.*;

public class Main {
	private int compteurFichierAnalyse =0 ;
	private int compteurFichierResultat =0 ;
	
	public static void main(String[] args) {
		//Préparation des articles
		PreTraitement action = new PreTraitement();
		try {
			action.run();
			//Code original
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Calcul des itemsets
		AprioriCalculation ap = new AprioriCalculation();
		try {
			ap.execution();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Mise en commun
		Decision d = new Decision();
		try {
			d.deserialization();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
