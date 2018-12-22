import java.lang.String;
import java.lang.StringBuilder;
import java.io.*;
import java.lang.System;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Long;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

							//SEARCH SYMBOL TABLE DATA STRUCTURE (CONTAINING PREVIOUS CHOICES) BEFORE YOU GO TO THE FULL DICTIONARY TRIE!!!!!!!!!!!!import java.util.ArrayList;
							
							
public class ac_test{
	

	public static void main(String[] args) throws IOException, FileNotFoundException {
		DLB dlb = new DLB();
		String currentOneWord;
		String currentLine;
		boolean firstTime = true;
		boolean ALWAYSTRUE = true;
		ArrayList<String> partialReturnStrings;
		ArrayList<String> returnStrings = new ArrayList<String>();
		ArrayList<String> keyList = new ArrayList<String>();		//KEEP KEYS HERE AND IN HASHMAP
		HashMap<String,String> userHistory = null;
		Scanner feed;
		StringBuilder builder = new StringBuilder();
		int loopCounter =0;
			
		BufferedReader bf = new BufferedReader(new FileReader("dictionary.txt"));
		
		while((currentLine= bf.readLine())!=null){
			
			
			dlb.add(currentLine);
				
		}
		
		
		
		/* try
      {
         FileInputStream fis = new FileInputStream("user_history.txt");
         ObjectInputStream ois = new ObjectInputStream(fis);
         userHistory = (HashMap)ois.readObject();
         ois.close();
         fis.close();
      }catch(FileNotFoundException j)
      {
		  userHistory = new HashMap<String, String>();
      }
	  catch(ClassNotFoundException c){
		  userHistory = new HashMap<String, String>();
	  }
	  catch(EOFException c){
		  userHistory = new HashMap<String, String>();
	  } */
		
		do{
			 //WHEN '$" MAKE SURE TO EMPTY CONTENTS OF STRING BUILDER 
			
			if(firstTime){
				System.out.print("Enter your first character: ");
				firstTime = false;
				feed = new Scanner(System.in);
				currentOneWord = feed.next().trim();
				
				if(!currentOneWord.trim().equals("$")&& !currentOneWord.trim().equals("!")){
					builder.append(currentOneWord.trim());
					loopCounter++;
				
/* 					if(userHistory.get(currentOneWord)!=null){
						if(userHistory.get(currentOneWord).contains(" ")){
							String[] words = userHistory.get(currentOneWord).split(" ");
							for(int i =0; i<words.length; i++){
							returnStrings.add(words[i]);
						}
						}
						else{
							String[] words = new String[1];
							words[0]=userHistory.get(currentOneWord);
							for(int i =0; i<words.length; i++){
								returnStrings.add(words[i]);
							
						}
						
							
						}
						
					}
				 */

					//USE HISTORY HERE TO PUT SUGGESTIONS
					
					partialReturnStrings = dlb.findWordsWithPrefix(currentOneWord);
					returnStrings.addAll(partialReturnStrings);
					
					System.out.println("Predictions: ");
					
					if(returnStrings.size()>5){
						
					  for(int i =0; i<5;i++){
						if(i ==0){
							System.out.println("1. " +returnStrings.get(i)+ "\t");
						}
						else if(i ==1){
							System.out.println("2. " + returnStrings.get(i)+ "\t");
						}
						else if(i ==2){
							System.out.println("3. " + returnStrings.get(i)+ "\t");
						}	
						else if(i ==3){
							System.out.println("4. " + returnStrings.get(i)+ "\t");
						}
						else if(i ==4){
							System.out.println("5. " + returnStrings.get(i)+ "\t");
						}
						
					}
				}
					else{
					
					for(int i =0; i<returnStrings.size();i++){
						if(i ==0){
							System.out.println("1. " +returnStrings.get(i)+ "\t");
						}
						else if(i ==1){
							System.out.println("2. " + returnStrings.get(i)+ "\t");
						}
						else if(i ==2){
							System.out.println("3. " + returnStrings.get(i)+ "\t");
						}	
						else if(i ==3){
							System.out.println("4. " + returnStrings.get(i)+ "\t");
						}
						else if(i ==4){
							System.out.println("5. " + returnStrings.get(i)+ "\t");
						}
				}
				}
									
					
					
					
					
				}
				else if(currentOneWord.trim().equals("!")){
					
					System.exit(0);
					
				}
			}	
			
			else{
				
				System.out.print("Enter the next character: ");
				feed = new Scanner(System.in);
				currentOneWord = feed.next().trim();
				if(!currentOneWord.trim().equals("$")&& !currentOneWord.trim().equals("!") &&!currentOneWord.trim().equals("1")&&!currentOneWord.trim().equals("2")&&!currentOneWord.trim().equals("3")&&!currentOneWord.trim().equals("4")&&!currentOneWord.trim().equals("5")){
					builder.append(currentOneWord);
					loopCounter++;
					
					
					/* if(userHistory.get(currentOneWord)!=null){
						if(userHistory.get(currentOneWord).contains(" ")){
							String[] words = userHistory.get(currentOneWord).split(" ");
							for(int i =0; i<words.length; i++){
							returnStrings.add(words[i]);
						}
						}
						else{
							String[] words = new String[1];
							words[0]=userHistory.get(currentOneWord);
							for(int i =0; i<words.length; i++){
								returnStrings.add(words[i]);
							
						}
						
							
						}
						
					} */
					
					returnStrings = dlb.findWordsWithPrefix(builder.toString());
					//returnStrings.addAll(partialReturnStrings);
					
					System.out.println("Predictions: ");
					
					if(returnStrings!=null){
						if(returnStrings.size()>5){
						
					  for(int i =0; i<5;i++){
						if(i ==0){
							System.out.println("1. " +returnStrings.get(i)+ "\t");
						}
						else if(i ==1){
							System.out.println("2. " + returnStrings.get(i)+ "\t");
						}
						else if(i ==2){
							System.out.println("3. " + returnStrings.get(i)+ "\t");
						}	
						else if(i ==3){
							System.out.println("4. " + returnStrings.get(i)+ "\t");
						}
						else if(i ==4){
							System.out.println("5. " + returnStrings.get(i)+ "\t");
						}
						
					}
				}
					else{
					
					for(int i =0; i<returnStrings.size();i++){
						if(i ==0){
							System.out.println("1. " +returnStrings.get(i)+ "\t");
						}
						else if(i ==1){
							System.out.println("2. " + returnStrings.get(i)+ "\t");
						}
						else if(i ==2){
							System.out.println("3. " + returnStrings.get(i)+ "\t");
						}	
						else if(i ==3){
							System.out.println("4. " + returnStrings.get(i)+ "\t");
						}
						else if(i ==4){
							System.out.println("5. " + returnStrings.get(i)+ "\t");
						}
				}
				}
					}
					else{
						
					}
					
				}
				else if(currentOneWord.trim().equals("!")){
					FileOutputStream fos = new FileOutputStream("user_history.txt");
                  ObjectOutputStream oos = new ObjectOutputStream(fos);
				  
					for(int i =0; i<keyList.size(); i++)
					{
						oos.writeObject(keyList.get(i));
						oos.writeObject(userHistory.get(keyList.get(i)));
						oos.writeObject('\n');
					}
             
                  oos.close();
                  fos.close();
				  System.exit(0);
				}
				else if(currentOneWord.trim().equals("$")){
					
					
					for(int i =0; i<builder.toString().length(); i++){
						String thisString = builder.toString();
						
						if(userHistory.get((thisString.substring(0,i+1)))!=null){
							keyList.add(thisString.substring(0,i+1));
							String aString = userHistory.get((thisString.substring(0,i+1)));
							String concat = aString + " " + thisString;
							userHistory.put((thisString.substring(0,i+1)), concat);
						}
						else{
							keyList.add(thisString.substring(0,i+1));
							userHistory.put((thisString.substring(0,i+1)), thisString);
						}
						
					}
				builder = builder.delete(0,loopCounter);
				loopCounter = 0;
					
				}
				else if(currentOneWord.equals("1")){
					String value = returnStrings.get(0);
				for(int i =0; i<builder.toString().length(); i++){
						String thisString = builder.toString();
						
						
						if(userHistory.get((thisString.substring(0,i+1)))!=null){
							keyList.add(thisString.substring(0,i+1));
							String aString = userHistory.get((thisString.substring(0,i+1)));
							String concat = aString + " " + value;
							userHistory.put((thisString.substring(0,i+1)), concat);
						}
						else{
							keyList.add(thisString.substring(0,i+1));
							userHistory.put((thisString.substring(0,i+1)), value);
						}
						
					}
				builder = builder.delete(0,loopCounter); //CHECK THIS CODE HERE!!!!!!!!!
				loopCounter = 0;
				
				System.out.println(returnStrings.get(0));
				returnStrings.clear();
					
				}
				else if(currentOneWord.equals("2")){
					String value = returnStrings.get(1);
				for(int i =0; i<builder.toString().length(); i++){
						String thisString = builder.toString();
						
						
						if(userHistory.get((thisString.substring(0,i+1)))!=null){
							keyList.add(thisString.substring(0,i+1));
							String aString = userHistory.get((thisString.substring(0,i+1)));
							String concat = aString + " " + value;
							userHistory.put((thisString.substring(0,i+1)), concat);
						}
						else{
							keyList.add(thisString.substring(0,i+1));
							userHistory.put((thisString.substring(0,i+1)), value);
						}
						
					}
				builder = builder.delete(0,loopCounter); //CHECK THIS CODE HERE!!!!!!!!!
				loopCounter = 0;
				
				System.out.println(returnStrings.get(1));
				returnStrings.clear();
					
				}
				else if(currentOneWord.equals("3")){
					String value = returnStrings.get(2);
				for(int i =0; i<builder.toString().length(); i++){
						String thisString = builder.toString();
						
						
						if(userHistory.get((thisString.substring(0,i+1)))!=null){
							keyList.add(thisString.substring(0,i+1));
							String aString = userHistory.get((thisString.substring(0,i+1)));
							String concat = aString + " " + value;
							userHistory.put((thisString.substring(0,i+1)), concat);
						}
						else{
							keyList.add(thisString.substring(0,i+1));
							userHistory.put((thisString.substring(0,i+1)), value);
						}
						
					}
				builder = builder.delete(0,loopCounter); //CHECK THIS CODE HERE!!!!!!!!!
				loopCounter = 0;
				
				System.out.println(returnStrings.get(2));
				returnStrings.clear();
					
				}
				else if(currentOneWord.equals("4")){
					String value = returnStrings.get(3);
				for(int i =0; i<builder.toString().length(); i++){
						String thisString = builder.toString();
						
						
						if(userHistory.get((thisString.substring(0,i+1)))!=null){
							keyList.add(thisString.substring(0,i+1));
							String aString = userHistory.get((thisString.substring(0,i+1)));
							String concat = aString + " " + value;
							userHistory.put((thisString.substring(0,i+1)), concat);
						}
						else{
							keyList.add(thisString.substring(0,i+1));
							userHistory.put((thisString.substring(0,i+1)), value);
						}
						
					}
				builder = builder.delete(0,loopCounter); //CHECK THIS CODE HERE!!!!!!!!!
				loopCounter = 0;
				
				System.out.println(returnStrings.get(3));
				returnStrings.clear();
					
				}
				else if(currentOneWord.equals("5")){
					String value = returnStrings.get(4);
				for(int i =0; i<builder.toString().length(); i++){
						String thisString = builder.toString();
						
						
						if(userHistory.get((thisString.substring(0,i+1)))!=null){
							keyList.add(thisString.substring(0,i+1));
							String aString = userHistory.get((thisString.substring(0,i+1)));
							String concat = aString + " " + value;
							userHistory.put((thisString.substring(0,i+1)), concat);
						}
						else{
							keyList.add(thisString.substring(0,i+1));
							userHistory.put((thisString.substring(0,i+1)), value);
						}
						
					}
				builder = builder.delete(0,loopCounter); //CHECK THIS CODE HERE!!!!!!!!!
				loopCounter = 0;
				
				System.out.println(returnStrings.get(4));
				returnStrings.clear();
					
				}
			
			} 
			
			System.out.println(NANOSECONDS.toSeconds(System.nanoTime())+ " ns");
		} while(ALWAYSTRUE);
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	
	
		
	

}
}