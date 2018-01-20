/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment;

import java.util.*;
import java.io.*;

/**
 *
 * @author Raell
 */
public class DocumentProcessing {
       
    public static HashMap<String, Integer> wordFrequency(File file) {
        
        //opens counts the words in the document
        int words = 0;
        HashMap<String, Integer> wordCount = new HashMap<String, Integer>();
        
        try {

            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                
                String[] line = scanner.nextLine().split(" ");
                words += line.length;
                
                for(int i = 0; i < line.length; i++) {
                    
                    line[i] = line[i].replaceAll("[^a-zA-Z']", ""); //remove all non-word related chars with
                    
                    if(!line[i].matches("[a-zA-Z0-9]+")) { //does not count word if it does not contain at least one alphabet or number
                        words--;
                    }
                    
                    line[i] = line[i].toLowerCase();
                    
                    if(!line[i].equals("")) { //if it is not an empty line, update word count
                                      
                        wordCount.put(line[i], wordCount.containsKey(line[i]) ? wordCount.get(line[i]) + 1 : 1);                                                           

                    }

                }
                
            }
            
            scanner.close();
                       
        }
        catch(IOException e) {
            System.out.println("Unable to read file: " + file.getPath() + "!");          
        }
        
        wordCount.put("totalWords", words);
                
        return wordCount;
          
    }
    
    public static double calculateIDF(ArrayList<Document> data, String searchTerm) {             
       
        //calculates idf of search term
        double totalDocs = data.size();
        double docsWithTerm = 1;

        for(Document a : data) {
                
            if(a.getFrequency(searchTerm) != 0) { //finds number of docs with search term
                docsWithTerm++;
            }
            
        }
        
        return Math.log10(totalDocs/docsWithTerm);
        
    }
    
    public static double calculateTFIDF(double[] idf, Document data, ArrayList<String> searchWords) {
        
        //calculate tfidf for each document
        double tfidf = 0;
        
        //add up tdidf of each search term
        for(int i = 0; i < searchWords.size(); i++) {
            
            double tf = (double)data.getFrequency(searchWords.get(i)) / data.getFrequency("totalWords");                     
            tfidf += tf * idf[i];
                       
        }

        return tfidf;
        
    }
        
    
}
