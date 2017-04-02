// STUDENT_NAME: Sam Cleland	
// STUDENT_ID: 260675996

import java.util.*;
import java.io.*;




public class Scrabble{

    static HashSet<String> myDictionary; // this is where the words of the dictionary are stored

    // DO NOT CHANGE THIS METHOD
    // Reads dictionary from file
    public static void readDictionaryFromFile(String fileName) throws Exception {
        myDictionary=new HashSet<String>();

        BufferedReader myFileReader = new BufferedReader(new FileReader(fileName) );

        String word;
        while ((word=myFileReader.readLine())!=null) myDictionary.add(word);

	myFileReader.close();
    }



    /* Arguments: 
        char availableLetters[] : array of characters containing the letters that remain available
        String prefix : Word assembled to date
        Returns: String corresponding to the longest English word starting with prefix, completed with zero or more letters from availableLetters. 
	         If no such word exists, it returns the String ""
     */
     
	public static String longestWord(char availableLetters[], String prefix) {
		String word="";
		String test= "";
		String tmpWord= "";
			 if(myDictionary.contains(prefix)) {
				 word=prefix;
			 }//base case below
			 if(availableLetters.length==0) {
				 return word;		 
			 }
		else{		
			int i;
			int j; 
			for(i=0;i<=availableLetters.length-1;i++){
				//using Arraylists to ignore sizing
				List<Character> storage= new ArrayList<Character>();
				List<Character> availableLettersList= new ArrayList<Character>();
				for(int convert=0;convert<=availableLetters.length-1;convert++) {
				    availableLettersList.add(availableLetters[convert]);
				}
				//creation of the array that will be recursively called, minusing ith term
				for(j=0;j<i;j++){
					storage.add(availableLettersList.get(j));
				}
				for(j=i+1;j<availableLetters.length;j++){
					storage.add(availableLettersList.get(j));
				}
				char [] storageArray= new char[storage.size()];
			    for (int x =0; x <storage.size(); x++) {
			        storageArray[x]=storage.get(x);            
			    }				 
			    //end creation
			    tmpWord= prefix+availableLetters[i];
			    //created tmpWord to create the new prefix that will be used as recursive input
				test=longestWord(storageArray,tmpWord);
				//after recursive calls done, returns the longest word for the purposes of Scrabble
				if(word.length()<test.length())word=test;
			}
		}
			 return word;
		
	}
		
    	
	 /* WRITE YOUR CODE HERE */
	 

	 // example of how to check with a string is in the dictionary. Remove this line when you understand how this works. 
	 /*if (myDictionary.contains(prefix)) System.out.println("The word " + prefix + " is in the dictionary");
    	}*/
    	// String longest = tmpCheck;
	// return longest;


    

 
    
    /* main method
        You should not need to change anything here.
     */
    public static void main (String args[]) throws Exception {
       
	// First, read the dictionary
	try {
	    readDictionaryFromFile("englishDictionary.txt");
        }
        catch(Exception e) {
            System.out.println("Error reading the dictionary: "+e);
        }
        
        
        // Ask user to type in letters
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in) );
        char letters[]; 
        do {
            System.out.println("Enter your letters (no spaces or commas):");
            
            letters = keyboard.readLine().toCharArray();

	    // now, enumerate the words that can be formed
            String longest = longestWord(letters, "");
	    System.out.println("The longest word is "+longest);
        } while (letters.length!=0);

        keyboard.close();
        
    }
}