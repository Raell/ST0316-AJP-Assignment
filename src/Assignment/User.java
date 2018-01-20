/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment;

import javax.swing.*;

/**
 *
 * @author Raell
 */
public class User {
    
    public static void main(String args[]) {
        
        //frame initialisation
        UserInterface searcher = new UserInterface();
        searcher.setTitle("File Searcher");
        searcher.setSize(500, 320);
        searcher.setVisible(true);
        searcher.setResizable(false);
        searcher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
    }
    
}
