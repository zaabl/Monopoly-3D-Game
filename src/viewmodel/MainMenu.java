package viewmodel;

import Local.Local;
import Multiplayer.GameGUI;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioRenderer;
import com.jme3.audio.AudioStream;
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
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.dropdown.DropDownControl;
import java.io.IOException;
import java.util.ArrayList;
import viewmodel.Read;
import viewmodel.Write;
import java.io.FileInputStream;

public class MainMenu extends AbstractAppState implements ScreenController {
    
    private SimpleApplication app;
    private Camera cam;
    private Node rootNode;
    private AssetManager assetManager;
    private InputManager inputManager;
    private ViewPort guiViewPort;
    private AudioRenderer audioRenderer;
    private AppStateManager stateManager;
    private Listener listener;
    
    private Node sceneNode;
    private Nifty nifty;
    private NiftyJmeDisplay niftyDisplay;
    
    private boolean flagSoundEnabled = true;
    
    public static ArrayList<String> Props = new ArrayList<String>();
    public static ArrayList<String> Mortgaged = new ArrayList<String>();
    public static ArrayList<String> Props1 = new ArrayList<String>();
    public static ArrayList<String> Props2 = new ArrayList<String>();
    public static ArrayList<String> Props3 = new ArrayList<String>();
    public static ArrayList<ArrayList> PropList = new ArrayList<ArrayList>();
    
    public static String Avatar;
    public static String updatedgamesplayed;
    public static String updatedgameswon;
    public static String updatedname;
    
    public static int NumberOfPlayers; 
    public static ArrayList<String> Names = new ArrayList<String>();
    public static ArrayList<String> Colours = new ArrayList<String>();
    public static ArrayList<String> Pictures = new ArrayList<String>();
    private String CurrentImage = "Unknown";
    
    private SimpleApplication simpleApplication;

    public MainMenu(SimpleApplication app) {
        this.app = app;
        this.simpleApplication = app;
    }
    
    
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.stateManager = stateManager;
        this.app = (SimpleApplication) app;
        this.cam = this.app.getCamera();
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        this.guiViewPort = this.app.getGuiViewPort();
        this.audioRenderer = this.app.getAudioRenderer();
        //this.app.getFlyByCamera().setEnabled(false);
        this.listener = this.app.getListener();

        //inputManager.clearMappings();
        initNifty();
    }
    
    @Override
    public void update(float tpf) {
        
    }
    
    private void initNifty() {
        niftyDisplay = new NiftyJmeDisplay(assetManager,inputManager,audioRenderer,guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/GUI.xml", "MainMenu",this);

        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
    }
    
    @Override
    public void onStartScreen() {
        
    }
    @Override
    public void onEndScreen() {
        stateManager.detach(this);
    }
    
    public void openLocal()
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("StartingLocalGame").show();
        DropDownControl DD = screen.findControl("NumberOfPlayers", DropDownControl.class);
        DD.addItem("2");
        DD.addItem("3");
        DD.addItem("4");
    }
    
    
    public void nextPanel()
    {
        Screen screen = nifty.getCurrentScreen();
        DropDownControl DD = screen.findControl("NumberOfPlayers", DropDownControl.class);
        String Num = String.valueOf(DD.getSelection());
        NumberOfPlayers = Integer.valueOf(Num);
        screen.findElementById("DefiningNumber").hide();
        screen.findElementById("StartingLocal").show();
        DropDownControl dropdown = nifty.getCurrentScreen().findControl("pieceColor", DropDownControl.class);
        dropdown.addItem("Black");
        dropdown.addItem("Red");
        dropdown.addItem("Grass");
        dropdown.addItem("White");
    }
    
    int Count = 0;
    int Count2 =1;
    public void startLocal()
    {
        nifty.getCurrentScreen().findElementById("localbtn").setVisibleToMouseEvents(false);
        nifty.getCurrentScreen().findElementById("multiplayerbtn").setVisibleToMouseEvents(false);
        nifty.getCurrentScreen().findElementById("profilebtn").setVisibleToMouseEvents(false);
        nifty.getCurrentScreen().findElementById("creditsbtn").setVisibleToMouseEvents(false);
        nifty.getCurrentScreen().findElementById("exitbtn").setVisibleToMouseEvents(false);
        Screen screen = nifty.getCurrentScreen();
        Button button = screen.findNiftyControl("startBtn", Button.class);
        if(button.getText()=="Start")
        {
            startGame();
            Screen screenGame = nifty.getScreen("GUI");
        }
        else if(NumberOfPlayers > Count)
        {
            Count2++;
            DropDownControl dropdown = screen.findControl("pieceColor", DropDownControl.class);
            TextField TF = screen.findNiftyControl("playerName", TextField.class);
            String Name = TF.getText();
            Names.add(Name);
            String Colour = String.valueOf(dropdown.getSelection());
            Colours.add(Colour);
            dropdown.removeItem(dropdown.getSelection());
            screen.findElementById("LocalImg").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Unknown.png", true));
            Pictures.add(CurrentImage);
            CurrentImage = "Unknown";
            screen.findElementById("PlayerNum").getRenderer(TextRenderer.class).setText("Player " + Count2);
            Count++;
            if(Count == NumberOfPlayers)
            {
                button.setText("Start");
                screen.findElementById("PlayerNum").getRenderer(TextRenderer.class).setText("Player " + NumberOfPlayers);
            }
            TF.setText("");
        }
    }
    
    public void backMenu2()
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("StartingLocalGame").hide();
        screen.findElementById("StartingLocal").hide();
        screen.findElementById("DefiningNumber").show();
        TextField TF = screen.findNiftyControl("NumberOfPlayers", TextField.class);
        TextField TF2 = screen.findNiftyControl("playerName", TextField.class);
        screen.findElementById("LocalImg").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Unknown.png", true));
        TF.setText("");
        TF2.setText("");
        DropDownControl dropdown = screen.findControl("pieceColor", DropDownControl.class);
        dropdown.removeItem(dropdown.getSelection());
        dropdown.removeItem(dropdown.getSelection());
        dropdown.removeItem(dropdown.getSelection());
        dropdown.removeItem(dropdown.getSelection());
        screen.findElementById("PlayerNum").getRenderer(TextRenderer.class).setText("Player 1");
        Button button = screen.findNiftyControl("startBtn", Button.class);
        button.setText("Next");
        
        nifty.getCurrentScreen().findElementById("localbtn").setVisibleToMouseEvents(true);
        nifty.getCurrentScreen().findElementById("multiplayerbtn").setVisibleToMouseEvents(true);
        nifty.getCurrentScreen().findElementById("profilebtn").setVisibleToMouseEvents(true);
        nifty.getCurrentScreen().findElementById("creditsbtn").setVisibleToMouseEvents(true);
        nifty.getCurrentScreen().findElementById("exitbtn").setVisibleToMouseEvents(true);
        Pictures.clear();
        Names.clear();
        Colours.clear();
    }
    
    //Multiplayer Button and its commands
    
    public void openMultiplayer()
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("Create/JoinRoom").show();
        screen.findElementById("MultiplayerPanel").show();
        
        nifty.getCurrentScreen().findElementById("localbtn").setVisibleToMouseEvents(false);
        nifty.getCurrentScreen().findElementById("multiplayerbtn").setVisibleToMouseEvents(false);
        nifty.getCurrentScreen().findElementById("profilebtn").setVisibleToMouseEvents(false);
        nifty.getCurrentScreen().findElementById("creditsbtn").setVisibleToMouseEvents(false);
        nifty.getCurrentScreen().findElementById("exitbtn").setVisibleToMouseEvents(false);
    }
    
    public void backMenu()
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("Create/JoinRoom").hide();
        screen.findElementById("MultiplayerPanel").hide();
        
        nifty.getCurrentScreen().findElementById("localbtn").setVisibleToMouseEvents(true);
        nifty.getCurrentScreen().findElementById("multiplayerbtn").setVisibleToMouseEvents(true);
        nifty.getCurrentScreen().findElementById("profilebtn").setVisibleToMouseEvents(true);
        nifty.getCurrentScreen().findElementById("creditsbtn").setVisibleToMouseEvents(true);
        nifty.getCurrentScreen().findElementById("exitbtn").setVisibleToMouseEvents(true);
    }
    
    public void createRoom()
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("MultiplayerPanel").hide();
        screen.findElementById("Room").show();
        Button button = screen.findElementById("Room").findNiftyControl("buttonHost", Button.class);
        button.setText("Host");
    }
    
    public void joinRoom()
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("MultiplayerPanel").hide();
        screen.findElementById("Room").show();
        Button button = screen.findElementById("Room").findNiftyControl("buttonHost", Button.class);
        button.setText("Join");
    }
    
    public void buttonBack()
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("MultiplayerPanel").show();
        screen.findElementById("Room").hide();
    }
    
    public static String Port;
    public static String Password;
    public void buttonHost()
    {
        Screen screen = nifty.getCurrentScreen();
        TextField TF1 = screen.findElementById("Room").findNiftyControl("txtPort", TextField.class);
        TextField TF2 = screen.findElementById("Room").findNiftyControl("txtPassword", TextField.class);
        Port = TF1.getText();
        Password = TF2.getText();
        Button button = screen.findElementById("Room").findNiftyControl("buttonHost", Button.class);
        if(button.getText().equals("Host"))
        {
            screen.findElementById("Room").hide();
            screen.findElementById("HostingRoom").show();
            //type code here   
        }
        else if(button.getText().equals("Join"))
        {
            screen.findElementById("Room").hide();
            screen.findElementById("HostingRoom").show();
            //type code here
        }
    }
    
    public void backMultiplayer()
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("HostingRoom").hide();
        screen.findElementById("Room").show();
    }
    
    public void startGame()
    {
        GameGUI game = new GameGUI();
        nifty.getCurrentScreen().findElementById("MainMenuLayer").hide();
        nifty.getCurrentScreen().findElementById("Create/JoinRoom").hide();
        nifty.getCurrentScreen().findElementById("StartingLocalGame").hide();
        stateManager.attach(new Local(simpleApplication));
        stateManager.attach(game);
        
    }
    
    //Profile Button and its commands
    
    Read read = new Read();
    public void changetext() throws IOException{
        read.reader();
        nifty.getCurrentScreen().findElementById("ProfilePanel").findElementById("GamesPlayedLbl").getRenderer(TextRenderer.class).setText(read.gamesPlayed);
        nifty.getCurrentScreen().findElementById("ProfilePanel").findElementById("GamesWonLbl").getRenderer(TextRenderer.class).setText(read.gamesWon);
    }
    
    public void openProfile() throws IOException{
        Read reader = new Read();
        reader.reader();
        nifty.getCurrentScreen().findElementById("ProfilePanel").show();
        //nifty.getCurrentScreen().findElementById("profilelayer2").show();
        TextField text = nifty.getCurrentScreen().findElementById("ProfilePanel").findNiftyControl("txtName", TextField.class);
        text.setText(Read.playerName);
        nifty.getCurrentScreen().findElementById("ProfilePic").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Avatar/"+Avatar+".png", initialized));
        changetext();
        nifty.getCurrentScreen().findElementById("localbtn").setVisibleToMouseEvents(false);
        nifty.getCurrentScreen().findElementById("multiplayerbtn").setVisibleToMouseEvents(false);
        nifty.getCurrentScreen().findElementById("profilebtn").setVisibleToMouseEvents(false);
        nifty.getCurrentScreen().findElementById("creditsbtn").setVisibleToMouseEvents(false);
        nifty.getCurrentScreen().findElementById("exitbtn").setVisibleToMouseEvents(false);
    }
    
    public void btnBrowse()
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("AvatarLayer").show();
    }
    
    
    public void avatarClk(String Index)
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("AvatarLayer").hide();
        screen.findElementById("ProfilePanel").findElementById("ProfilePic").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Avatar/Avatar"+Index+".png", true));
        Avatar = "Avatar"+Index;
        screen.findElementById("LocalImg").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Avatar/Avatar"+Index+".png", true));
        CurrentImage = "Avatar"+Index;
        Avatar = "Avatar"+Index;
    }
    
    
    Write write = new Write();
    public void btnSave() throws IOException
    {
        Screen screen = nifty.getCurrentScreen();
        TextField txt = screen.findElementById("ProfilePanel").findNiftyControl("txtName", TextField.class);
        updatedname= txt.getText();
    
        updatedgamesplayed = screen.findElementById("ProfilePanel").findElementById("GamesPlayedLbl").getRenderer(TextRenderer.class).getOriginalText();
        updatedgameswon = screen.findElementById("ProfilePanel").findElementById("GamesWonLbl").getRenderer(TextRenderer.class).getOriginalText();
        write.writer();
    
        nifty.getCurrentScreen().findElementById("ProfileLayer").findElementById("ProfilePanel").hide();
    
        nifty.getCurrentScreen().findElementById("localbtn").setVisibleToMouseEvents(true);
        nifty.getCurrentScreen().findElementById("multiplayerbtn").setVisibleToMouseEvents(true);
        nifty.getCurrentScreen().findElementById("profilebtn").setVisibleToMouseEvents(true);
        nifty.getCurrentScreen().findElementById("creditsbtn").setVisibleToMouseEvents(true);
        nifty.getCurrentScreen().findElementById("exitbtn").setVisibleToMouseEvents(true);
        screen.findElementById("ProfilePanel").hide();
    }
    
    //Exit Button
    
    public void exitbtn()
    {
        app.stop();
    }
    
    public void guiToggleSound() {
        Screen screenStart = nifty.getCurrentScreen();
        Element imageSoundIconStart = screenStart.findElementById("soundIcon");
        ImageRenderer imageRendererStart= imageSoundIconStart.getRenderer(ImageRenderer.class);
        
        if(flagSoundEnabled) {
            imageRendererStart.setImage(nifty.createImage("Materials/Images/soundOff.png", true));
            flagSoundEnabled = false;
            listener.setVolume(0);
        } else {
            imageRendererStart.setImage(nifty.createImage("Materials/Images/soundOn.png", true));
            flagSoundEnabled = true;
            listener.setVolume(1);
        }
    }
    
    private void playMusic(String path)
    {
        /*try
        {
            AudioData data = new AudioStream(new FileInputStream(path)).getData();
        }*/
    }
}