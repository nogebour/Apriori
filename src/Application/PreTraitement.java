package Application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class PreTraitement {
	String path1 = "C:\\Users\\Noel_Nicolas\\Documents\\Cours\\MLBD\\Apriori\\Ressources\\Article\\articles.100p.txt"; //Liste de mots
	String path2 = "C:\\Users\\Noel_Nicolas\\Documents\\Cours\\MLBD\\Apriori\\Ressources\\Article\\mots.lst";//Articles
	String path3Part1 = "C:\\Users\\Noel_Nicolas\\Documents\\Cours\\MLBD\\Apriori\\Ressources\\InputApriori\\transa";
	String path3Part3 = ".txt";
	String[] attributs;
	HashMap<String, Integer> motsCles;
	int compteurMotsCles;
	int compteurArticle;
	
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
	
	public int run() throws IOException
	{
		parserMotsCles();
		return parserArticle();
		
	}
	
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
			if(((this.compteurArticle % 400) == 0) && (this.compteurArticle >0))
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
		return compteurFichierSortie;
	}

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