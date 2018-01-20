/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Raell
 */
public class UserInterface extends JFrame implements ActionListener {
    
    //all objects to be used in frame
    private JPanel searchPanel, displayPanel, mainPanel, browsePanel, topPanel;
    private JButton searchBtn, browseBtn;
    private JLabel searchTitle, resultTitle, directoryTitle;
    private JTextField searchField, directoryPath;
    private JTable resultArea;
    private JScrollPane results;
    private Folder doc;
    
    public UserInterface() {
        
        //main panel initialisation
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), new EtchedBorder()));
        
        //upper panel initialisation
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        
        //directory selection panel initialisation
        browsePanel = new JPanel();
        directoryTitle = new JLabel("Select folder:");    
        directoryPath = new JTextField(System.getProperty("user.dir") + "\\dataFiles", 20);
        browseBtn = new JButton("Browse");
        browseBtn.addActionListener(this);
        browsePanel.add(directoryTitle);
        browsePanel.add(directoryPath);
        browsePanel.add(browseBtn);
        
        topPanel.add(browsePanel, BorderLayout.NORTH);
        
        //search panel initialisation
        searchPanel = new JPanel();
        searchTitle = new JLabel("Enter search terms:");
        searchField = new JTextField(20);
        searchBtn = new JButton("Search");
        searchBtn.addActionListener(this);
        searchPanel.add(searchTitle);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);        
        searchPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        
        topPanel.add(searchPanel, BorderLayout.SOUTH);
        
        //results display panel initialisation
        displayPanel = new JPanel();
        String[] columnNames = {"Filename", "TFIDF"};
        Object[][] rowData = new String[10][2];
        resultArea = new JTable(rowData, columnNames);
        resultArea.setEnabled(false);
        results = new JScrollPane(resultArea);
        resultTitle = new JLabel("Search Results");
        resultTitle.setHorizontalAlignment(displayPanel.getWidth()/2);
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(resultTitle, BorderLayout.NORTH);
        displayPanel.add(results, BorderLayout.CENTER);
        
        //add all panels to main panel
        mainPanel.setLayout(new BorderLayout());       
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(displayPanel, BorderLayout.CENTER);
        
        this.setContentPane(mainPanel);
        
        //begins preprocess of the documents
        preProcessing();
        
    }
    
    private void preProcessing() {
        
        //preprocesses on the directory path stated
        doc = new Folder(directoryPath.getText());
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        //function for checking button presses
        
        //when search button is pressed
        if(e.getSource() == searchBtn) {
            
            //clear results table
            for(int i = 0; i < 10; i++) {

                for(int j = 0; j < 2; j++) {
                    resultArea.setValueAt("", i, j);
                }
                                                  
            }
            
            //checks if user entered search term
            if(!searchField.getText().isEmpty()) {
                
                //extracts search terms
                ArrayList<String> searchWords = new ArrayList<String>(Arrays.asList(searchField.getText().toLowerCase().trim().split(" ")));

                //checks and removes stopwords
                Folder.checkStopwords(searchWords);
                
                //if any entries are found
                boolean found = false;
                
                //checks if there are still search terms left
                if(!searchWords.isEmpty()) {
                    
                    //send the search terms in for checking and retrieve results
                    doc.processSearchWords(searchWords);
                    ArrayList<Document> data = doc.getData();
                                           
                    //displays up to 10 sorted results on the table
                    for(int i = 0; i < data.size() && i < 10; i++) {
                        
                        Document d = data.get(i);
                        
                        if(d.getTFIDF() > 0) {
                            resultArea.setValueAt(d.getFilename(), i, 0);
                            resultArea.setValueAt(Double.toString(d.getTFIDF()), i, 1);
                            found = true;
                        }

                        

                    }
                    
                }
                
                //if no results were found
                if(!found) {
                    
                    JOptionPane.showMessageDialog(null, "No results found!"); 
                    
                }
                               
            }
            
            //if no search terms were entered
            else {
                JOptionPane.showMessageDialog(null, "Enter a search term.");
            }
            
        }  
        
        //if browse button is pressed, opens a directory selection box and does preprocessing on new selected folder
        else if(e.getSource() == browseBtn) {
            
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setSelectedFile(new File(System.getProperty("user.dir") + "\\dataFiles"));
            int choice = chooser.showOpenDialog(null);
            if (choice == JFileChooser.APPROVE_OPTION) {
                File chosenFile = chooser.getSelectedFile();
                directoryPath.setText(chosenFile.getAbsolutePath());
                preProcessing();
            }                      
            
        }
        
    }
    
}
