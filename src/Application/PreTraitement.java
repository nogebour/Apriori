package Application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Classe faisant la lecture des articles (input de l'application) 
 * pour les transformer en transaction (input d'Apriori)
 * @author Noel Nicolas & Fauvet Morgane
 */
public class PreTraitement {
	/**
	 * Variable limitant le nombre de transaction par fichier
	 */
	private static final int TRANSACTION_PER_FILE = 1000;

	String directory = System.getProperty("user.dir");
	String path1 = directory + "\\Ressources\\Article\\articles.100p.txt"; //Liste de mots
	String path2 = directory +  "\\Ressources\\Article\\mots.lst"; //Articles
	String path3Part1 = directory + "\\Ressources\\InputApriori\\transa";
	String path3Part3 = ".txt";
	String[] attributs;
	public HashMap<String, Integer> motsCles;
	int compteurMotsCles;
	int compteurArticle;
	
	/**
	 * Fonction de création de la classe PreTraitement
	 * @param input String - Chemin absolu vers le fichier en input
	 * @param mots String - Chemin absolu vers la liste de mots clé
	 * @param inputApriori String - Chemin absolu vers les fichiers de stockage des transaction (On ajoutera le numéro du fichier et le suffixe à ce String 
	 */
	public PreTraitement(String input, String mots, String inputApriori)
	{
		this.path1 = input;
		this.path2 = mots;
		this.path3Part1 = inputApriori;
		this.compteurArticle = 0;
		this.compteurMotsCles = 0;
		attributs = new String[100];
		this.motsCles = new HashMap<String, Integer>();
	}
	
	/**
	 * Parse les mots-clés et crée les fichiers d'input
	 * @return le nombre de fichiers d'input crée
	 * @throws IOException En cas de problème d'ouverture, de fermeture ou de lecture
	 */
	public int run() throws IOException
	{
		parserMotsCles();
		return parserArticle();
	}
	
	/**
	 * Traite le fichier de mots-cles et remplit le tableau d'attribut avec
	 * @throws IOException
	 */
	private void parserMotsCles() throws IOException
	{
		String ligne = "";
		//Ouverture fichier
			InputStream ips=new FileInputStream(path2); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			while ((ligne=br.readLine())!=null){
				//System.out.println(ligne+" numéro ="+this.compteurMotsCles);
				if(this.compteurMotsCles == this.attributs.length)
					{
						String[] temp = new String[this.attributs.length*2];
						for(int i = 0; i < this.attributs.length; i++)
						{
							temp[i] = this.attributs[i];
						}
						this.attributs = temp;
					}
				this.attributs[compteurMotsCles] = ligne;
				compteurMotsCles++;
			}
			br.close();
	}
	
	//TODO Problèmes parsing -> pas assez d'article dans le fichier final, en mettre deux ?
	/**
	 * Parse le fichier d'input et crée les fichiers de transaction à la destination donné
	 * @return nombre de fichier crée
	 * @throws IOException En cas de problèmes de lecture, d'écriture, ou d'ouverture de fichier
	 */
	private int parserArticle() throws IOException
	{
		String ligne;
		//Article
		InputStream ips=new FileInputStream(path1); 
		InputStreamReader ipsr=new InputStreamReader(ips);
		BufferedReader br=new BufferedReader(ipsr);
		//Sortie
		int compteurFichierSortie = 0;
		FileWriter fw = new FileWriter (this.path3Part1+compteurFichierSortie+this.path3Part3);
		BufferedWriter bw = new BufferedWriter (fw);
		PrintWriter fichierSortie = new PrintWriter (bw);
		//Création HashMap pour retrouver plus facilement les mots clés
		for(int i = 0; i<this.compteurMotsCles; i++)
		{
			motsCles.put(attributs[i], i);
		}
		//Lecture ligne par ligne des articles
		while ((ligne=br.readLine())!=null)
		{
			if(((this.compteurArticle % PreTraitement.TRANSACTION_PER_FILE) == 0) && (this.compteurArticle >0))
			{
				bw.close();
				compteurFichierSortie++;
				fw = new FileWriter (this.path3Part1+compteurFichierSortie+this.path3Part3);
				bw = new BufferedWriter (fw);
				fichierSortie = new PrintWriter (bw);
			}
			int[] transaction = new int[this.compteurMotsCles];
			for(int i = 0; i<transaction.length; i++)
			{
				transaction[i]=0;
			}
			String[] motsArticle = ligne.split("\\s+");
			for(int i = 0; i<motsArticle.length; i++)
			{
				if(motsCles.containsKey(motsArticle[i]))
				{
					transaction[motsCles.get(motsArticle[i])] = 1;
				}
			}
			fichierSortie.println(this.sortieFichier(transaction));
			this.compteurArticle ++;
		}
		bw.close();
		br.close();
		return compteurFichierSortie+1;
	}

	/**
	 * Cré le string représentant une trasaction et qui sera écrite dans un fichier de transaction
	 * @param transaction
	 * @return la chaine à écrire dans une fichier
	 */
	private String sortieFichier(int[] transaction) {
		String res = "";
		if(transaction.length > 0)
		{
			res += transaction[0];
		}
		for(int i = 1; i<transaction.length; i++)
		{
			res += " "+transaction[i];
		}
		return res;
	}
}