/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

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
import de.lessvoid.nifty.elements.render.PanelRenderer;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.Timer;
import java.util.TimerTask;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.TextField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import javafx.scene.paint.Color;
import viewmodel.MainMenu;

public class GameGUI extends AbstractAppState implements ScreenController {
    
    private SimpleApplication app;
    private Camera cam;
    private Node rootNode;
    private AssetManager assetManager;
    private InputManager inputManager;
    private ViewPort guiViewPort;
    private AudioRenderer audioRenderer;
    private AppStateManager stateManager;
    private Listener listener;
    private FlyByCamera flyCam;
    
    private Node sceneNode;
    public Nifty nifty;
    private NiftyJmeDisplay niftyDisplay;
    
    private boolean flagSoundEnabled = true;
    private ArrayList<String> key = new ArrayList<String>();

    public ArrayList<String> getKey() {
        return key;
    }

    public void clearKey() {
        key.clear();
    }
    
    private ArrayList<String> Props = new ArrayList<String>();

    public void setProps(ArrayList<String> Props) {
        this.Props = Props;
    }
    private ArrayList<String> Mortgaged = new ArrayList<String>();

    public ArrayList<String> getMortgaged() {
        return Mortgaged;
    }

    public void setMortgaged(ArrayList<String> Mortgaged) {
        this.Mortgaged = Mortgaged;
    }
    public static ArrayList<String> Props1 = new ArrayList<String>();
    public static ArrayList<String> Props2 = new ArrayList<String>();
    public static ArrayList<String> Props3 = new ArrayList<String>();
    public static ArrayList<ArrayList> PropList = new ArrayList<ArrayList>();
    
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
        flyCam = this.app.getFlyByCamera();
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
        nifty.fromXml("Interface/GUI.xml", "GUI", this);

        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);
        Screen screenGame = nifty.getCurrentScreen();
        for(int i = 0; i < MainMenu.NumberOfPlayers; i++)
            {
                String Index = String.valueOf(i+1);
                screenGame.findElementById("AllUsers").findElementById("User"+Index+"Img").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Avatar/"+MainMenu.Pictures.get(i)+".png", true));
                screenGame.findElementById("AllUsers").findElementById("User"+Index+"Name").getRenderer(TextRenderer.class).setText(MainMenu.Names.get(i));
            }
    }
    
    public void quitGame() {
        app.stop();
    }
    
    public void updateFinance(int index, int money){
        String sIndex = String.valueOf(index);
        String sMoney = String.valueOf(money);
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("User"+sIndex+"Money").getRenderer(TextRenderer.class).setText(sMoney);
    }
    
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        //System.out.println("bind( " + screen.getScreenId() + ")");
    }
    
    @Override
    public void onStartScreen() {
        
    }
    @Override
    public void onEndScreen() {
    }
    
    public void Show()//User1 All Users
    {
        nifty.getCurrentScreen().findElementByName("panel1").show();
        //nifty.getCurrentScreen().findElementById("All Users").findElementById("User1").findElementById("PanelUser1Name").findElementById("User1Name").getRenderer(TextRenderer.class).setText("aaaaaaaaaaaaaaaaaaaaaaaaa");
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                 //To change body of generated methods, choose Tools | Templates.
                 nifty.getCurrentScreen().findElementByName("panel1").hide();
            }
        },3000);
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
    
    private static String choice="";

    public static void setChoice(String choice) {
        GameGUI.choice = choice;
    }

    public String getChoice() {
        return choice;
    }
    
    public void jailRollBtn(){
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("jailedPanel").hide();
        choice = "roll";
    }
    
    public void jailCardBtn(){
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("jailedPanel").hide();
        choice = "card";
    }
    
    public void jailPayBtn(){
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("jailedPanel").hide();
        choice = "pay";
    }
    
    public void showJailOptions(boolean card){
        Screen screen = nifty.getCurrentScreen();
        if(!card)
        {
            screen.findElementById("BtnCardJail").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/UseCardDark.png", true));
            screen.findElementById("BtnCardJailHover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/UseCardDark.png", true));
        }
        else{
            screen.findElementById("BtnCardJail").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/UseCard.png", true));
            screen.findElementById("BtnCardJailHover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/UseCardHover.png", true));
        }
        screen.findElementById("jailedPanel").show();
        
    }
    
    public void setAllBtnsDark()
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("BtnTrade").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/HotkeysDark.png", true));
        screen.findElementById("BtnTradeHover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/HotkeysDark.png", true));
        screen.findElementById("BtnTrade").setVisibleToMouseEvents(false);
                
        screen.findElementById("BtnBuild").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/BuildDark.png", true));
        screen.findElementById("BtnBuildHover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/BuildDark.png", true));
        screen.findElementById("BtnBuild").setVisibleToMouseEvents(false);
        
        screen.findElementById("BtnSell").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/SellDark.png", true));
        screen.findElementById("BtnSellHover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/SellDark.png", true));
        screen.findElementById("BtnSell").setVisibleToMouseEvents(false);
        
        screen.findElementById("BtnMortgage").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/MortgageDark.png", true));
        screen.findElementById("BtnMortgageHover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/MortgageDark.png", true));
        screen.findElementById("BtnMortgage").setVisibleToMouseEvents(false);
        
        screen.findElementById("BtnRedeem").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/RedeemDark.png", true));
        screen.findElementById("BtnRedeemHover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/RedeemDark.png", true));
        screen.findElementById("BtnRedeem").setVisibleToMouseEvents(false);
        
        screen.findElementById("BtnView").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/ViewDark.png", true));
        screen.findElementById("BtnViewHover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/ViewDark.png", true));
        screen.findElementById("BtnView").setVisibleToMouseEvents(false);
    }
    
    public void unSetAllBtnsDark()
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("BtnTrade").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Hotkeys.png", true));
        screen.findElementById("BtnTradeHover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/HotkeysHover.png", true));
        screen.findElementById("BtnTrade").setVisibleToMouseEvents(true);
        
        screen.findElementById("BtnBuild").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Build.png", true));
        screen.findElementById("BtnBuildHover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/BuildHover.png", true));
        screen.findElementById("BtnBuild").setVisibleToMouseEvents(true);
        
        screen.findElementById("BtnSell").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Sell.png", true));
        screen.findElementById("BtnSellHover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/SellHover.png", true));
        screen.findElementById("BtnSell").setVisibleToMouseEvents(true);
        
        screen.findElementById("BtnMortgage").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Mortgage.png", true));
        screen.findElementById("BtnMortgageHover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/MortgageHover.png", true));
        screen.findElementById("BtnMortgage").setVisibleToMouseEvents(true);
        
        screen.findElementById("BtnRedeem").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Redeem.png", true));
        screen.findElementById("BtnRedeemHover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/RedeemHover.png", true));
        screen.findElementById("BtnRedeem").setVisibleToMouseEvents(true);
        
        screen.findElementById("BtnView").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/View.png", true));
        screen.findElementById("BtnViewHover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/ViewHover.png", true));
        screen.findElementById("BtnView").setVisibleToMouseEvents(true);
    }
    
    private boolean tradeOpen = false;
    public void btnTrade()
    {
        setAllBtnsDark();
        Screen screen = nifty.getCurrentScreen();
        if(tradeOpen)
        {
            nifty.getCurrentScreen().findElementById("PanelHotKeys").hide();
            tradeOpen = false;
            unSetAllBtnsDark();
        }
        else
        {
            nifty.getCurrentScreen().findElementById("PanelHotKeys").show();
            tradeOpen = true;
            setAllBtnsDark();
            screen.findElementById("BtnTrade").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Hotkeys.png", true));
            screen.findElementById("BtnTradeHover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/HotkeysHover.png", true));
            screen.findElementById("BtnTrade").setVisibleToMouseEvents(true);
        }
        //screen.findElementById("BuyLayer").findElementById("buyPanel").setVisible(true);
        //screen.findElementById("buyingPropImg").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/"+Prop+".png", true));
    }
    
    private boolean buildOpen = false;
    
    public void showOptionPanel(String Option)
    {
        Screen screen = nifty.getCurrentScreen();
        Button button = screen.findElementById("Options").findNiftyControl("btnOption", Button.class);
        button.setText(Option);
        if(buildOpen)
        {
            screen.findElementById("Options").hide();
            buildOpen = false;
            hidingOptionPanel();
            unSetAllBtnsDark();
        }
        else
        {
            screen.findElementById("Options").show(); buildOpen = true;
            for(int i = 0; i < Props.size(); i++)
            {
                String S = Props.get(i);
                int A = i+1;
                String D = String.valueOf(A);
                screen.findElementById("Options").findElementById("PropOneImg"+D).show();
                screen.findElementById("Options").findElementById("PropOneImg"+D).getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Trade/"+S+".png", true));
                
            }
            setAllBtnsDark();
            screen.findElementById("Btn"+Option).getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/"+Option+".png", true));
            screen.findElementById("Btn"+Option+"Hover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/"+Option+"Hover.png", true));
            screen.findElementById("Btn"+Option).setVisibleToMouseEvents(true);
        }
    }
    
    public void hidingOptionPanel()
    {
        Screen screen = nifty.getCurrentScreen();
        for(int i = 1; i <=28; i++)
            {
                String B = String.valueOf(i);
                screen.findElementById("Options").findElementById("PropOneImg"+B).getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Trade/EmptyTrade.png", true));
                screen.findElementById("Options").findElementById("PropOneImg"+B).hide();
            }
    }
    
    public void resetingMortgagedPanel()
    {
        Screen screen = nifty.getCurrentScreen();
        for(int i = 0; i < Props.size(); i++)
            {
                String S = Props.get(i);
                int A = i+1;
                String D = String.valueOf(A);
                screen.findElementById("Options").findElementById("PropOneImg"+D).getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Trade/"+S+".png", true));
            }
        for(int i = Props.size()+1; i <=28; i++)
        {
            String B = String.valueOf(i);
            screen.findElementById("Options").findElementById("PropOneImg"+B).hide();
            screen.findElementById("Options").findElementById("PropOneImg"+B).getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Trade/EmptyTrade.png", true));
        }
    }
    
    public void resetingRedeemedPanel()
    {
        Screen screen = nifty.getCurrentScreen();
        for(int i = 0; i < Mortgaged.size(); i++)
            {
                String S = Mortgaged.get(i);
                int A = i+1;
                String D = String.valueOf(A);
                screen.findElementById("Options").findElementById("PropOneImg"+D).getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Trade/"+S+".png", true));
            }
        for(int i = Mortgaged.size()+1; i <=28; i++)
        {
            String B = String.valueOf(i);
            screen.findElementById("Options").findElementById("PropOneImg"+B).hide();
            screen.findElementById("Options").findElementById("PropOneImg"+B).getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Trade/EmptyTrade.png", true));
        }
    }
    
    public void btnBuild()
    {
        showOptionPanel("Build");
        
    }
    
    public void btnSell()
    {
        showOptionPanel("Sell");
    }
    
    public void btnMortgage()
    {
        showOptionPanel("Mortgage");
    }
    
    private boolean redeemOpen = false;
    public void btnRedeem()
    {
        Screen screen = nifty.getCurrentScreen();
        Button button = screen.findElementById("Options").findNiftyControl("btnOption", Button.class);
        button.setText("Redeem");
        if(buildOpen)
        {
            screen.findElementById("Options").hide();
            buildOpen = false;
            hidingOptionPanel();
            unSetAllBtnsDark();
        }
        else
        {
            screen.findElementById("Options").show(); buildOpen = true;
            for(int i = 0; i < Mortgaged.size(); i++)
            {
                String S = Mortgaged.get(i);
                int A = i+1;
                String D = String.valueOf(A);
                screen.findElementById("Options").findElementById("PropOneImg"+D).show();
                screen.findElementById("Options").findElementById("PropOneImg"+D).getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Trade/"+S+".png", true));
            }
            setAllBtnsDark();
            screen.findElementById("BtnRedeem").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Redeem.png", true));
            screen.findElementById("BtnRedeemHover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/RedeemHover.png", true));
            screen.findElementById("BtnRedeem").setVisibleToMouseEvents(true);
        }
    }
    
    int Position = 1;
    boolean viewOpen = false;
    public void btnView()
    {
        Screen screen = nifty.getCurrentScreen();
        if(viewOpen)
        {screen.findElementById("PanelView").hide(); viewOpen = false;}
        else
        {screen.findElementById("PanelView").show(); viewOpen = true;}
        screen.findElementById("ViewClick").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Property/"+Position+".png", true));
    }
    
    public void updateCard(int index){
        String sIndex = String.valueOf(index);
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("ViewClick").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Property/"+sIndex+".png", true));

    }
    
    String SelectedIndex = "";
    public void optionsBtn(String Index)
    {
        Screen screen = nifty.getCurrentScreen();
        if(SelectedIndex == "")
        {
            screen.findElementById("Selected_"+Index).show();
            SelectedIndex = Index;
        }
        else
        {
            screen.findElementById("Selected_"+SelectedIndex).hide();
            screen.findElementById("Selected_"+Index).show();
            SelectedIndex = Index;
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String key1 = "";
    private String key2 = "";
    public void btnOption()
    {
        Screen screen = nifty.getCurrentScreen();
        if(SelectedIndex != "")
        {
            Button button = screen.findNiftyControl("btnOption", Button.class);
            if(button.getText() == "Build")
            {
                
            }
            
            else if(button.getText() == "Sell")
            {
                
            }
            
            else if(button.getText() == "Mortgage")
            {
                int a = Integer.valueOf(SelectedIndex);
                a--;
                String B = String.valueOf(a);
                Mortgaged.add(Props.get(a));
                key.clear();
                key.add("Mortgaged");
                key.add(Props.get(a));
                Props.remove(a);
                resetingMortgagedPanel();
                screen.findElementById("Selected_"+SelectedIndex).hide();
                SelectedIndex = "";
            }
            
            else if(button.getText() == "Redeem")
            {
                int a = Integer.valueOf(SelectedIndex);
                a--;
                String B = String.valueOf(a);
                Props.add(Mortgaged.get(a));
                key.clear();
                key.add("Redeemed");
                key.add(Mortgaged.get(a));
                Mortgaged.remove(a);
                resetingRedeemedPanel();
                screen.findElementById("Selected_"+SelectedIndex).hide();
                SelectedIndex = "";
            }
        }
    }
    
    public void btnTradePlayer(String Index)
    {
        setAllBtnsDark();
        PropList.add(Props1);
        PropList.add(Props2);
        PropList.add(Props3);
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("MainPanel").show();
        ArrayList<String> PropsTrade = PropList.get(Integer.valueOf(Index)-1);
        for(int i = 0; i < Props.size(); i++)
        {
            String S = Props.get(i);
            int A = i+1;
            String D = String.valueOf(A);
            screen.findElementById("PlayerOnePanel").findElementById("PropOneImg"+D).show();
            screen.findElementById("PlayerOnePanel").findElementById("PropOneImg"+D).getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Trade/"+S+".png", true));
        }
        
        for(int i = 0; i < PropsTrade.size(); i++)
        {
            String S = PropsTrade.get(i);
            int A = i+1;
            String D = String.valueOf(A);
            screen.findElementById("PlayerTwoPanel").findElementById("PropTwoImg"+D).show();
            screen.findElementById("PlayerTwoPanel").findElementById("PropTwoImg"+D).getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Trade/"+S+".png", true));
        }
        screen.findElementById("Trade").findElementById("MainPanel").show();
        nifty.getCurrentScreen().findElementById("PanelTrade").hide();
    }
    
    
    public ArrayList<String> Player1TradeIndex = new ArrayList<String>();
    public ArrayList<String> Player1Trade = new ArrayList<String>();
    public void propOneClick(String Index)
    {
        Screen screen = nifty.getCurrentScreen();
        boolean alreadySelected = false;
        int itsIndex = 0;
        for(int i = 0; i < Player1TradeIndex.size(); i++)
        {
            if(Player1TradeIndex.get(i).equals(Index))
            {
                alreadySelected = true;
                itsIndex = i;
            }
        }
        if(alreadySelected)
        {
            screen.findElementById("PlayerOnePanel").findElementById("Arrow_"+Index).hide();
            Player1TradeIndex.remove(itsIndex);
            Player1Trade.remove(itsIndex);
        }
        else
        {
            screen.findElementById("PlayerOnePanel").findElementById("Arrow_"+Index).show();
            Player1TradeIndex.add(Index);
            Player1Trade.add(Props.get(Integer.valueOf(Index)-1));
        }
    }
    
    public ArrayList<String> Player2TradeIndex = new ArrayList<String>();
    public ArrayList<String> Player2Trade = new ArrayList<String>();
    public void propTwoClick(String Index)
    {
        Screen screen = nifty.getCurrentScreen();
        boolean alreadySelected = false;
        int itsIndex = 0;
        for(int i = 0; i < Player2TradeIndex.size(); i++)
        {
            if(Player2TradeIndex.get(i).equals(Index))
            {
                alreadySelected = true;
                itsIndex = i;
            }
        }
        if(alreadySelected)
        {
            screen.findElementById("PlayerTwoPanel").findElementById("Arrow_"+Index).hide();
            Player2TradeIndex.remove(itsIndex);
            Player2Trade.remove(itsIndex);
        }
        else
        {
            screen.findElementById("PlayerTwoPanel").findElementById("Arrow_"+Index).show();
            Player2TradeIndex.add(Index);
            Player2Trade.add(Props.get(Integer.valueOf(Index)-1));
        }
    }
    
    public void btnOffer()
    {
        
    }
    
    public void btnCancel()
    {
        Screen screen = nifty.getCurrentScreen();
        unSetAllBtnsDark();
        screen.findElementById("Trade").findElementById("MainPanel").hide();
        for(int i = 1; i <=28; i++)
            {
                String B = String.valueOf(i);
                screen.findElementById("PlayerOnePanel").findElementById("PropOneImg"+B).getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Trade/EmptyTrade.png", true));
                screen.findElementById("PlayerOnePanel").findElementById("PropOneImg"+B).hide();
                screen.findElementById("PlayerTwoPanel").findElementById("PropTwoImg"+B).getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Trade/EmptyTrade.png", true));
                screen.findElementById("PlayerTwoPanel").findElementById("PropTwoImg"+B).hide();
            }
    }
    
    public void playerBoughtProp(int Turn,String Prop, String Price)
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("BoughtPanel").show();
        screen.findElementById("boughtCard").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Property/"+Prop+".png", true));
        screen.findElementById("priceImg").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/$"+Price+".png", true));
        screen.findElementById("BoughtUserImg").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Avatar/"+MainMenu.Pictures.get(Turn-1)+".png", true));
        screen.findElementById("boughtUserName").getRenderer(TextRenderer.class).setText(MainMenu.Names.get(Turn-1));
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                Screen screen = nifty.getCurrentScreen();
                screen.findElementById("BoughtPanel").hide();
            }
        }, 3000);
    }
    
    public void playerRedeemedProp(String Name,String Pic,int Prop)
    {
        
    }
    
    public void btnAddPressed(String Amount)
    {
        Screen screen = nifty.getCurrentScreen();
        String Sum = screen.findElementById("yourBidding").getRenderer(TextRenderer.class).getOriginalText();
        int Sum1 = Integer.valueOf(Sum);
        int Sum2 = Integer.valueOf(Amount);
        Sum = String.valueOf(Sum1+Sum2);
        screen.findElementById("yourBidding").getRenderer(TextRenderer.class).setText(Sum);
    }
    
    public void btnClear()
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("yourBidding").getRenderer(TextRenderer.class).setText("0");
    }
    
    private String Response = "";

        public String getResponse() {
        String temp = Response;
        return temp;
    }

    public void setResponse(String Response) {
         this.Response = Response;
    }
    public void showPropToBuy(int Prop)
    {
        String PropT = String.valueOf(Prop);
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("buyPanel").show();
        screen.findElementById("buyingPropImg").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/"+PropT+".png", true));
    }
    
    public void buyBtn()
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("buyPanel").hide();
        setResponse("Buy");
        
    }
    
    public void auctionBtn()
    {
        /*Screen screen = nifty.getCurrentScreen();
        screen.findElementById("buyPanel").hide();
        screen.findElementById("AuctionPanel").show();
        screen.findElementById("BtnBid").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/BidDark.png", true));
        screen.findElementById("BtnBidHover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/BidDark.png", true));
        screen.findElementById("BtnBid").setVisibleTo
        MouseEvents(false);
        screen.findElementById("BtnFold").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/FoldDark.png", true));
        screen.findElementById("BtnFoldHover").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/FoldDark.png", true));
        screen.findElementById("BtnFold").setVisibleToMouseEvents(false);*/
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("buyPanel").hide();
        setResponse("No");
    }
    
    public void showBuyOrLeave(int index, int price){
        String sIndex = String.valueOf(index);
        String sPrice = String.valueOf(price);
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("buyPanel").show();
        screen.findElementById("buyingPropImg").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Property/" + sIndex + ".png", true));
        screen.findElementById("propPriceImg").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/$"+sPrice+".png", true));
        flyCam.setDragToRotate(true);
    }
    
    public void showChestCard(String text, String type){
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("CardText").getRenderer(TextRenderer.class).setText(text);
        Timer time = new Timer();
        if(type == "Cummunity")
        {
            screen.findElementById("Chest/Chance").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Treasure.png", true));
        }
        else{
            screen.findElementById("Chest/Chance").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/LuckyImg.png", true));
        }
        screen.findElementById("Cards").show();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                Screen screen2 = nifty.getCurrentScreen();
                screen2.findElementById("Cards").hide();
            }
        }, 5000);
    }
    
    public void showSpacetoRoll()
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("PressSpace").show();
    }
    
    public void hideSpacetoRoll()
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("PressSpace").hide();
    }
    
    public void showTabImg()
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("PressTab").show();
    }
    
    public void hideTabImg()
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("PressTab").hide();
    }
    
    public void showGreenDollar(int Player)
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("User"+String.valueOf(Player+1)+"MoneyImg").show();
        screen.findElementById("User"+String.valueOf(Player+1)+"MoneyImg").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/GreenDollar.png", true));
        Timer time = new Timer();
        final int Turn = Player;
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                Screen screen = nifty.getCurrentScreen();
                screen.findElementById("User"+String.valueOf(Turn+1)+"MoneyImg").hide();
            }
        }, 1500);
    }
    
    public void showRedDollar(int Player)
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("User"+String.valueOf(Player+1)+"MoneyImg").show();
        screen.findElementById("User"+String.valueOf(Player+1)+"MoneyImg").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/RedDollar.png", true));
        Timer time = new Timer();
        final int Turn = Player;
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                Screen screen = nifty.getCurrentScreen();
                screen.findElementById("User"+String.valueOf(Turn+1)+"MoneyImg").hide();
            }
        }, 1500);
    }
    
    public void showPlayerTurn(int Player)
    {
        String Turn = String.valueOf(Player);
        String pastTurn = String.valueOf(Player-1);
        if(Player == 1)
            pastTurn = String.valueOf(MainMenu.NumberOfPlayers);
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("User"+Turn+"Turn").show();
        screen.findElementById("User"+pastTurn+"Turn").hide();
    }
    
    public void showJailed(int Turn)
    {
        Screen screen = nifty.getCurrentScreen();
        screen.findElementById("JailedPanel").show();
        screen.findElementById("jailedUserImg").getRenderer(ImageRenderer.class).setImage(nifty.createImage("Materials/Images/Avatar/"+MainMenu.Pictures.get(Turn-1)+".png", true));
        screen.findElementById("jailedUserName").getRenderer(TextRenderer.class).setText(MainMenu.Names.get(Turn-1));
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                Screen screen = nifty.getCurrentScreen();
                screen.findElementById("JailedPanel").hide();
            }
        }, 3000);
    }
}
