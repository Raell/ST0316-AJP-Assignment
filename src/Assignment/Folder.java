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
public class Folder {
    
    private File[] files; //array of all files in the folder
    private ArrayList<String> searchWords; //arraylist of search terms
    private ArrayList<Document> dataSort; //arraylist of data for each file
    private ArrayList<Folder> subfolders; //arraylist of subfolders in the folder
    
    public Folder(String folderName) {
        
        //starts by finding and loading all the files to be processed
        File folder = new File(folderName);
        files = folder.listFiles();
               
        //initialising other objects
        dataSort = new ArrayList<Document>();
        subfolders = new ArrayList<Folder>();
        
        //checks for any subdirectories and does recursive loop
        for(File f : files) {
            
            if(f.isDirectory()) {
                subfolders.add(new Folder(f.getAbsolutePath()));
            }
            else { //calculates data for the file
                dataSort.add(new Document(f.getAbsoluteFile()));
            }
                                   
        }
          
        //combines all files data from any subdirectories into one list
        generateList();
                      
        
    }
    
    public void processSearchWords(ArrayList<String> searchWords) {
        
        //calculates the idf and tfidf based on search terms
        this.searchWords = searchWords;       
        double[] idf = getIDF();
        processTFIDF(idf);
        
    }
    
    private double[] getIDF() {        
                
        //calculate idf for each search term
        double[] idf = new double[searchWords.size()]; 
        
        for(int i = 0; i < searchWords.size(); i++) {
            idf[i] = DocumentProcessing.calculateIDF(dataSort, searchWords.get(i));
        }
        
        return idf;      
        
    }
    
    private void processTFIDF(double[] idf) {
        
        //calculates each documents' tfidf and assigns them
        for(Document a : dataSort) {
            
            double tfidf = DocumentProcessing.calculateTFIDF(idf, a, searchWords);
            a.setTFIDF(tfidf);
            
        }
        
    }
    
    private void generateList() {
        
        //add all subfolders dataSort arraylist to this dataSort
        if(!subfolders.isEmpty()) {
            
            for(Folder sub : subfolders) {
                this.dataSort.addAll(sub.dataSort);              
            }
            
        }
        
    }
    
    public ArrayList<Document> getData() {
        
        //sorts arraylist based of tfidf and returns it
        Collections.sort(dataSort);       
        return dataSort;
        
    }
    
    public  static void checkStopwords(ArrayList<String> searchWords) {
        //function to remove stopwords
        
        //gets list of stopwords from folder
        File file = new File(System.getProperty("user.dir") + "\\stopwords.txt");
        
        try {
            
            Scanner scanner = new Scanner(file);
            ArrayList<String> stopwords = new ArrayList<String>();
            
            while(scanner.hasNextLine()) {
                
                stopwords.add(scanner.nextLine().trim());
                
            }
            
            scanner.close();
            
            int length = searchWords.size();
            
            for(int i = 0; i < length; i++) {
                //scans through each search term for any stopwords and removes them
                if(stopwords.contains(searchWords.get(i))) {
                    searchWords.remove(searchWords.get(i));
                    length--;
                    i--;
                }
                
            }
            
                       
        }
        catch(IOException e) {
            System.out.println("Unable to read file: " + file.getPath() + "!");          
        }
        
    }
    
}
