package Application;
import Apriori.*;

public class Main {
	
	public static void main(String[] args) {
		//Pr�paration des articles
		PreTraitement action = new PreTraitement();
		try {
			action.run();
			//Code original
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Calcul des itemsets
		AprioriCalculation ap = new AprioriCalculation();
		ap.aprioriProcess();
		
		//Mise en commun
		
	}
}
