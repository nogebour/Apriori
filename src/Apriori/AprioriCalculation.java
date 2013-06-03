
/**
 *
 * @author Nathan Magnus, under the supervision of Howard Hamilton
 * Copyright: University of Regina, Nathan Magnus and Su Yibin, June 2009.
 * No reproduction in whole or part without maintaining this copyright notice
 * and imposing this condition on any subsequent users.
 *
 * File:
 * Input files needed:
 *      1. config.txt - three lines, each line is an integer
 *          line 1 - number of items per transaction
 *          line 2 - number of transactions
 *          line 3 - minsup
 *      2. transa.txt - transaction file, each line is a transaction, items are separated by a space
 */

package Apriori;
import java.io.*;
import java.util.*;

/*
 * On modifie la classe et on entoure de l'éxécution d'Apriori de notre classe de Resultat
 * pour la sérialisation
 * 
 * On fera la sérialisation après
 */

/******************************************************************************
 * Class Name   : AprioriCalculation
 * Purpose      : generate Apriori itemsets
 *****************************************************************************/
public class AprioriCalculation
{
    Vector<String> candidates=new Vector<String>(); //the current candidates
    //configuration file
    String configFile="config.txt"; 
    //transaction file
    String transaFile="transa.txt";
    String outputFile="apriori-output.txt";//output file
    int numItems; //number of items per transaction
    int numTransactions; //number of transactions
    double minSup; //minimum support for a frequent itemset
    String oneVal[]; //array of value per column that will be treated as a '1'
    String itemSep = " "; //the separator value for items in the database
    
    //Ajout MLBD
    Resultat res;
    int nbreTransactionFichier;
    String outputSerialization;
    

    //MLBD
    /**
     * Création de l'algorithme Apriori
     * @param inputFile String chemin vers les fichiers d'input
     * @param outputFile String Chemin vers les fichiers de serialization
     * @param nbreMots int nombre de mots-clés et donc d'item dans une transaction
     * @param minsup int minsup utilisé dans l'algorithme
     */
    public AprioriCalculation(String inputFile, String outputFile, int nbreMots, int minsup)
    {
    	this.transaFile = inputFile;
    	this.outputSerialization = outputFile;
    	try {
			this.nbreTransactionFichier = this.compteEntree();
		} catch (IOException e) {
			System.out.println("Problème lecture liste de mots");
			e.printStackTrace();
		}
    	//Génération fichier config.txt
    	try {
			this.generationConfig(nbreMots, minsup);
		} catch (IOException e) {
			System.out.println("Problème génération du fichier de configuration");
			e.printStackTrace();
		}
    }
    
    /**
     * Génération du fichier de configuration
     * @param nbreMots Nombre d'item dans une transaction
     * @param minsup int minimum support utilisé dans l'algorithme
     * @throws IOException En cas de problème das l'écriture
     */
    private void generationConfig(int nbreMots, int minsup) throws IOException {
		FileWriter fw = new FileWriter (this.configFile);
		BufferedWriter bw = new BufferedWriter (fw);
		PrintWriter fichierSortie = new PrintWriter (bw);
		fichierSortie.println(nbreMots);
		fichierSortie.println(this.nbreTransactionFichier);
		fichierSortie.println(minsup);
		fichierSortie.println("42");
		bw.close();
	}
    
    /**
     * Compte le nombre de tranactions dans le fichier
     * @return le nombre de transaction dans le fichier
     * @throws IOException En cas de problème de lecture
     */
    private int compteEntree() throws IOException {
		int res = 0;
		InputStream ips=new FileInputStream(this.transaFile); 
		InputStreamReader ipsr=new InputStreamReader(ips);
		BufferedReader br=new BufferedReader(ipsr);
		while ((br.readLine())!=null){
			res++;
		}
		br.close();
		return res;
	}
    
    /**
     * Fonction lançant l'exécution de l'algorithme
     * @throws IOException En cas de problèmes avec les fichiers
     */
	public void execution() throws IOException
    {
    	//Création classe pour résultat
        this.res = new Resultat(this.nbreTransactionFichier);
    	aprioriProcess();
        FileOutputStream fos = new FileOutputStream(this.outputSerialization);
        ObjectOutputStream oos= new ObjectOutputStream(fos);
        //System.out.println(res);
        oos.writeObject(res);
        oos.flush();
        oos.close();
    }
    
    /************************************************************************
     * Method Name  : aprioriProcess
     * Purpose      : Generate the apriori itemsets
     * Parameters   : None
     * Return       : None
     *************************************************************************/

    public void aprioriProcess()
    {
        Date d; //date object for timing purposes
        long start, end; //start and end time
        int itemsetNumber=0; //the current itemset being looked at
        //get config
        getConfig();
                
        System.out.println("Apriori algorithm has started.");

        //start timer
        d = new Date();
        start = d.getTime();

        //while not complete
        do
        {
            //increase the itemset that is being looked at
            itemsetNumber++;

            //generate the candidates
            generateCandidates(itemsetNumber);

            //determine and display frequent itemsets
            calculateFrequentItemsets(itemsetNumber);
            if(candidates.size()!=0)
            {
                //System.out.println("Frequent " + itemsetNumber + "-itemsets");
                //System.out.println(candidates);
            }
        //if there are <=1 frequent items, then its the end. This prevents reading through the database again. When there is only one frequent itemset.
        }while(candidates.size()>1);

        //end timer
        d = new Date();
        end = d.getTime();

        //display the execution time
        System.out.println("Execution time is: "+((double)(end-start)/1000) + " seconds.");
    }

    /************************************************************************
     * Method Name  : getInput
     * Purpose      : get user input from System.in
     * Parameters   : None
     * Return       : String value of the users input
     *************************************************************************/
    public static String getInput()
    {
        String input="";
        //read from System.in
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //try to get users input, if there is an error print the message
        try
        {
            input = reader.readLine();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return input;
    }

    /************************************************************************
     * Method Name  : getConfig
     * Purpose      : get the configuration information (config filename, transaction filename)
     *              : configFile and transaFile will be change appropriately
     * Parameters   : None
     * Return       : None
     *************************************************************************/
    private void getConfig()
    {
        FileWriter fw;
        BufferedWriter file_out;

        //ask if want to change the config
        System.out.println("Default Configuration: ");
        //System.out.println("\tRegular transaction file with '" + itemSep + "' item separator.");
        System.out.println("\tConfig File: " + configFile);
        System.out.println("\tTransa File: " + transaFile);
        System.out.println("\tSerialization File: " + this.outputSerialization);


        try
        {
             FileInputStream file_in = new FileInputStream(configFile);
             BufferedReader data_in = new BufferedReader(new InputStreamReader(file_in));
             //number of items
             numItems=Integer.valueOf(data_in.readLine()).intValue();

             //number of transactions
             numTransactions=Integer.valueOf(data_in.readLine()).intValue();

             //minsup
             minSup=(Double.valueOf(data_in.readLine()).doubleValue());

             //output config info to the user
             System.out.print("Input configuration: "+numItems+" items, "+numTransactions+" transactions, ");
             System.out.println("minsup = "+minSup+"%");
             //System.out.println();
             minSup/=100.0;

            oneVal = new String[numItems];
            for(int i=0; i<oneVal.length; i++)
            {
            	oneVal[i]="1";
            }
            //create the output file
            fw= new FileWriter(outputFile);
            file_out = new BufferedWriter(fw);
            //put the number of transactions into the output file
            file_out.write(numTransactions + "\n");
            file_out.write(numItems + "\n******\n");
            file_out.close();
            data_in.close();
        }
        //if there is an error, print the message
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    /************************************************************************
     * Method Name  : generateCandidates
     * Purpose      : Generate all possible candidates for the n-th itemsets
     *              : these candidates are stored in the candidates class vector
     * Parameters   : n - integer value representing the current itemsets to be created
     * Return       : None
     *************************************************************************/
    private void generateCandidates(int n)
    {
        Vector<String> tempCandidates = new Vector<String>(); //temporary candidate string vector
        String str1, str2; //strings that will be used for comparisons
        StringTokenizer st1, st2; //string tokenizers for the two itemsets being compared

        //if its the first set, candidates are just the numbers
        if(n==1)
        {
            for(int i=1; i<=numItems; i++)
            {
                tempCandidates.add(Integer.toString(i));
            }
        }
        else if(n==2) //second itemset is just all combinations of itemset 1
        {
            //add each itemset from the previous frequent itemsets together
            for(int i=0; i<candidates.size(); i++)
            {
                st1 = new StringTokenizer(candidates.get(i));
                str1 = st1.nextToken();
                for(int j=i+1; j<candidates.size(); j++)
                {
                    st2 = new StringTokenizer(candidates.elementAt(j));
                    str2 = st2.nextToken();
                    tempCandidates.add(str1 + " " + str2);
                }
            }
        }
        else
        {
            //for each itemset
            for(int i=0; i<candidates.size(); i++)
            {
                //compare to the next itemset
                for(int j=i+1; j<candidates.size(); j++)
                {
                    //create the strigns
                    str1 = new String();
                    str2 = new String();
                    //create the tokenizers
                    st1 = new StringTokenizer(candidates.get(i));
                    st2 = new StringTokenizer(candidates.get(j));

                    //make a string of the first n-2 tokens of the strings
                    for(int s=0; s<n-2; s++)
                    {
                        str1 = str1 + " " + st1.nextToken();
                        str2 = str2 + " " + st2.nextToken();
                    }

                    //if they have the same n-2 tokens, add them together
                    if(str2.compareToIgnoreCase(str1)==0)
                        tempCandidates.add((str1 + " " + st1.nextToken() + " " + st2.nextToken()).trim());
                }
            }
        }
        //clear the old candidates
        candidates.clear();
        //set the new ones
        candidates = new Vector<String>(tempCandidates);
        tempCandidates.clear();
    }

    /************************************************************************
     * Method Name  : calculateFrequentItemsets
     * Purpose      : Determine which candidates are frequent in the n-th itemsets
     *              : from all possible candidates
     * Parameters   : n - iteger representing the current itemsets being evaluated
     * Return       : None
     *************************************************************************/
    private void calculateFrequentItemsets(int n)
    {
        Vector<String> frequentCandidates = new Vector<String>(); //the frequent candidates for the current itemset
        FileInputStream file_in; //file input stream
        BufferedReader data_in; //data input stream
        FileWriter fw;
        BufferedWriter file_out;

        StringTokenizer st, stFile; //tokenizer for candidate and transaction
        boolean match; //whether the transaction has all the items in an itemset
        boolean trans[] = new boolean[numItems]; //array to hold a transaction so that can be checked
        int count[] = new int[candidates.size()]; //the number of successful matches

        try
        {
                //output file
                fw= new FileWriter(outputFile, true);
                file_out = new BufferedWriter(fw);
                //load the transaction file
                file_in = new FileInputStream(transaFile);
                data_in = new BufferedReader(new InputStreamReader(file_in));

                //for each transaction
                for(int i=0; i<numTransactions; i++)
                {
                    //System.out.println("Got here " + i + " times"); //useful to debug files that you are unsure of the number of line
                    stFile = new StringTokenizer(data_in.readLine(), itemSep); //read a line from the file to the tokenizer
                    //put the contents of that line into the transaction array
                    for(int j=0; j<numItems; j++)
                    {
                        trans[j]=(stFile.nextToken().compareToIgnoreCase(oneVal[j])==0); //if it is not a 0, assign the value to true
                    }

                    //check each candidate
                    for(int c=0; c<candidates.size(); c++)
                    {
                        match = false; //reset match to false
                        //tokenize the candidate so that we know what items need to be present for a match
                        st = new StringTokenizer(candidates.get(c));
                        //check each item in the itemset to see if it is present in the transaction
                        while(st.hasMoreTokens())
                        {
                            match = (trans[Integer.valueOf(st.nextToken())-1]);
                            if(!match) //if it is not present in the transaction stop checking
                                break;
                        }
                        if(match) //if at this point it is a match, increase the count
                            count[c]++;
                    }

                }
                for(int i=0; i<candidates.size(); i++)
                {
                    //  System.out.println("Candidate: " + candidates.get(c) + " with count: " + count + " % is: " + (count/(double)numItems));
                    //if the count% is larger than the minSup%, add to the candidate to the frequent candidates
                    if((count[i]/(double)numTransactions)>=minSup)
                    {
                        frequentCandidates.add(candidates.get(i));
                        //put the frequent itemset into the output file
                        
                        //System.out.println("Candidats :"+candidates.get(i));
                        //MLBD
                        //Ajout résultat pour sérialisation
                        this.res.addResults(candidates.get(i), count[i]/(double)numTransactions);
                        file_out.write(candidates.get(i) + "," + count[i]/(double)numTransactions + "\n");
                    }
                }
                file_out.write("-\n");
                file_out.close();
        }
        //if error at all in this process, catch it and print the error messate
        catch(IOException e)
        {
            System.out.println(e);
        }
        //clear old candidates
        candidates.clear();
        //new candidates are the old frequent candidates
        candidates = new Vector<String>(frequentCandidates);
        frequentCandidates.clear();
    }
}