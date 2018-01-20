/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment;

import java.io.*;
import java.util.*;

/**
 *
 * @author Raell
 */
public class Document implements Comparable {
    
    private HashMap<String, Integer> wordCount; //word count of each word in file
    private File file; //file of document
    private double tfidf; //tfidf value of document
    
    public Document(File file) {
                
        //initializing values
        wordCount = new HashMap<String, Integer>();
        this.file = file;
        
        //obtain word frequency count of document
        wordCount = DocumentProcessing.wordFrequency(file);
              
    }
   
    public int getFrequency(String searchTerm) {
        
        //returns count of word or 0 if word not found
        int count = wordCount.containsKey(searchTerm) ? wordCount.get(searchTerm) : 0;
        return count;
        
    }     
    
    public void setTFIDF(double tfidf) {
        
        //sets tfidf of document
        this.tfidf = tfidf * 1000; //to make the numbers larger for ease of reading, no affect to rankings 
        
    }
    
    @Override
    public int compareTo(Object data) {
        
        //sort files based on tfidf in descending order
        if(tfidf > ((Document)data).getTFIDF()) {
            return -1;
        }
        else if(tfidf == ((Document)data).getTFIDF()) {
            return 0;
        }
        else {
            return 1;
        }
        
    }
    
    public String getFilename() {
        return file.getName();
    }
    
    public double getTFIDF() {
        return tfidf;
    }
    
}
