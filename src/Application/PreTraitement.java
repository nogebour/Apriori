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
	String path1 = "articles.10p.txt"; //Liste de mots
	String path2 = "mots.lst"; //Articles
	String[] attributs;
	HashMap<String, Integer> motsCles;
	int compteurMotsCles;
	int compteurArticle;
	
	public PreTraitement()
	{
		this.compteurArticle = 0;
		this.compteurMotsCles = 0;
		attributs = new String[100];
		this.motsCles = new HashMap<String, Integer>();
	}
	
	public void run() throws IOException
	{
		parserMotsCles();
		parserArticle();
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
	private void parserArticle() throws IOException
	{
		String ligne;
		//Article
		InputStream ips=new FileInputStream(path1); 
		InputStreamReader ipsr=new InputStreamReader(ips);
		BufferedReader br=new BufferedReader(ipsr);
		//Sortie
		FileWriter fw = new FileWriter ("transa.txt");
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
		}
		br.close();
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
