/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import Multiplayer.GameGUI;

/**
 *
 * @author DELL
 */
public class Write {
    
   
    
    File file = new File("score.txt"); 
    public void writer() throws FileNotFoundException, IOException{
    //String filename="firstt.txt";  
   
    String name = MainMenu.updatedname;
    String played = MainMenu.updatedgamesplayed;
    String winned = MainMenu.updatedgameswon;
   
    file.createNewFile(); 
    FileOutputStream oFile = new FileOutputStream(file, false);
    
    try{
        
            FileWriter filewriter = new FileWriter(file);
            
            BufferedWriter bufferwriter = new BufferedWriter(filewriter);
            
            bufferwriter.write(MainMenu.Avatar);
            bufferwriter.newLine();
            bufferwriter.write(name);
            bufferwriter.newLine();
            bufferwriter.write(played);
            bufferwriter.newLine();
            bufferwriter.write(winned);
            
            bufferwriter.close();
            
        } catch (IOException ex) {
            System.out.println("Cannot write file");
        }
    }
    
}