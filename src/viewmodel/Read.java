package viewmodel;

import de.lessvoid.nifty.screen.Screen;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.audio.Listener;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.Timer;
import java.util.TimerTask;
import Multiplayer.GameGUI;

/**
 *
 * @author DELL
 */
public class Read 
{

    private Nifty nifty;
    public  String photopath;
    public static String playerName;
    public String gamesPlayed;
    public String gamesWon;
       
   
    public void reader() throws IOException, IOException, IOException
    {
        String filename2 = "score.txt";
        String line = null;
        
        try{
            FileReader filereader = new FileReader(filename2);
            BufferedReader bufferedReader = new BufferedReader(filereader);
            
            photopath = bufferedReader.readLine();
            playerName = bufferedReader.readLine();
            gamesPlayed = bufferedReader.readLine();
            gamesWon = bufferedReader.readLine();
            MainMenu.Avatar=photopath;
            
            bufferedReader.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file   " + filename2);
        } catch (IOException ex) {
            System.out.println("Cannot read this file   " + filename2);
        }
    }        
}