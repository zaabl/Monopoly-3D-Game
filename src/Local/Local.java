/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Local;

import Multiplayer.GameGUI;
import view.Dice;
import view.Piece;
import model.Player;
import model.Property;
import viewmodel.PropertyIntializer;
import viewmodel.Selector;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.sun.java_cup.internal.runtime.Scanner;
import com.sun.java_cup.internal.runtime.Symbol;
import model.Land;
import Multiplayer.GameGUI;
import com.jme3.math.Vector3f;
import de.lessvoid.nifty.screen.Screen;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import model.Chest;
import view.Building;
import viewmodel.ChestIntializer;
import viewmodel.MainMenu;

/**
 *
 * @author dell
 */
public class Local extends AbstractAppState {

    public final Node localRootNode = new Node("Multiplayer");
    private final AssetManager assetManager;
    private final InputManager inputManager;
    private final FlyByCamera flyByCamera;
    private final Camera camera;
    private ChaseCamera chaseCam;
    private CharacterControl playerControl;
    private final Node rootNode;
    private int pressed;
    private boolean closeCam = false;
    private Dice dice;
    private int turn = 1;
    private Spatial cat;
    private boolean rollingPhase = false, endTurn = false, gamePhase = false;
    private float time;
    private AudioNode audio_nature;
    private Spatial scene;
    private Player[] player;
    private Selector selector;
    private Property[] property;
    private PropertyIntializer propertyIntializer;
    private Chest[] chest;
    private ChestIntializer chestIntializer;
    private boolean rollDoublebool = true, isRolledDouble = false;
    private boolean payTime = false;
    private boolean updatingPosition;
    private boolean firstTime = false;
    private int[] diceRollNumber;
    private boolean movingPhase = false;
    private boolean firstTimeM;
    private boolean stoppingPhase = false;
    private GameGUI gameGui;
    private boolean guiFirstTime;
    private boolean guiSecondTime;
    private int before;

    public Local(SimpleApplication app) {
        rootNode = app.getRootNode();
        assetManager = app.getAssetManager();
        inputManager = app.getInputManager();
        flyByCamera = app.getFlyByCamera();
        camera = app.getCamera();
        property = new Property[28];
        chest = new Chest[27];
        propertyIntializer = new PropertyIntializer();
        property = propertyIntializer.getProperty();
        chestIntializer = new ChestIntializer();
        chest = chestIntializer.getChest();
    }

    @Override
    public void cleanup() {
        rootNode.detachChild(localRootNode);
        super.cleanup();
    }
    @Override
    public void update(float tpf) {
        super.update(tpf);
        time += tpf;
        gameGui.updateFinance(turn, player[turn-1].getMoney());
        
        
        if (!endTurn) {
            gameGui.showPlayerTurn(turn);
            if (rollingPhase && !stoppingPhase) {
                gameGui.showSpacetoRoll();
                if (!player[turn - 1].isJailed()) {
                    rollMethod();
                    movingPhase = true;
                } else {
                    if(!guiSecondTime)
                    {
                        gameGui.showJailOptions(player[turn-1].isGetOutOfJail());
                        guiSecondTime=true;
                    }
                    if(gameGui.getChoice()!="")
                    {
                        jailMethod(gameGui.getChoice());
                        gameGui.setChoice("");
                        guiSecondTime=false;
                    }
                }
            } else if (movingPhase) {
                moveMethod();
                if (!player[turn - 1].isMoving()) {
                    stoppingPhase = true;
                    movingPhase = false;
                    guiFirstTime = false;
                }
            } else if (stoppingPhase && !gamePhase) {
                if (getPropertyLocation() != -1 && getPropertyLocation() != -2 && getPropertyLocation() != -3) {
                    if (property[getPropertyLocation()].getOwner() == 0) {
                        //Buy or Pass
                        if (!guiFirstTime) {
                            gameGui.showBuyOrLeave(player[turn - 1].getPosition(), property[getPropertyLocation()].getBasePrice());
                            guiFirstTime = true;
                        }
                        if ("Buy".equals(gameGui.getResponse())) {
                            buyMethod();
                            gamePhase = true;
                            gameGui.setResponse("");
                        } else if ("No".equals(gameGui.getResponse())) {
                            gamePhase = true;
                            gameGui.setResponse("");
                        }

                    } else {
                        rentMethod();
                        gamePhase = true;
                        stoppingPhase = true;
                    }

                } else if (getPropertyLocation() == -2) {
                    chestsMethod("community");
                    
                } else if (getPropertyLocation() == -3) {
                    chestsMethod("chance");
                    
                } else if (getPropertyLocation() == -1) {
                    miscellaneous();
                    gamePhase = true;
                }

            } else if (gamePhase) {
                // Sell - Mortgage - DeMortgage - Buy Houses - Buy Hotels - Sell Houses
                updateData();
                guiSecondTime = false;
                if (gameGui.getKey().size() != 0 && gameGui.getKey().get(0) == "Mortgaged") {
                    //gameGui.getMortagedData(int propertyNumber, int money)
                    player[turn - 1].depositMoney(property[getPropertyIndexFromLocation(Integer.valueOf(gameGui.getKey().get(1)))].morgage());
                    gameGui.showGreenDollar(turn - 1);
                    //System.out.println(property[getPropertyLocation()].isMortaged());
                    setLandColor(Integer.valueOf(gameGui.getKey().get(1)), "Orange");
                    //changeToMorgaged();
                    gameGui.clearKey();
                } else if (gameGui.getKey().size() != 0 && gameGui.getKey().get(0) == "Redeemed") {
                    player[turn - 1].withdrawMoney(property[getPropertyIndexFromLocation(Integer.valueOf(gameGui.getKey().get(1)))].deMorgage());
                    gameGui.showRedDollar(turn - 1);
                    setLandColor(Integer.valueOf(gameGui.getKey().get(1)), player[turn - 1].getColor());
                    gameGui.clearKey();
                }

            }
        } else {
            endTurn = false;
            stoppingPhase = false;
            turn++;
            gameGui.hideTabImg();
            gameGui.showSpacetoRoll();
            if (turn == MainMenu.NumberOfPlayers+1) {
                turn = 1;
            }
            player[turn - 1].cameraIntialize();
        }

    }
    
    private void chestsMethod(String chestType){
        int shuffle = 0;
        
        if(chestType.equals("community")){
            shuffle = getShuffleCommunity();
        } else if(chestType.equals("chance")){
            shuffle = getShuffleChance();
        }
        
        gameGui.showChestCard(chest[shuffle].getText(), chestType);
        
        if(null != chest[shuffle].getAction())switch (chest[shuffle].getAction()) {
            case "deposit":
                player[turn-1].depositMoney(chest[shuffle].getNumber());
                gameGui.showGreenDollar(turn - 1);
                gamePhase = true;
                break;
            case "withdraw":
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                @Override
                public void run() {
                }
                }, 3000);
                player[turn-1].withdrawMoney(chest[shuffle].getNumber());
                gameGui.showRedDollar(turn - 1);
                gamePhase = true;
                break;
            case "move":
                player[turn-1].setPosition(chest[shuffle].getNumber());
                player[turn-1].movePiece(time, chest[shuffle].getNumber());
                break;
            case "getOutOfJail":
                player[turn-1].setGetOutOfJail(true);
                gamePhase = true;
                break;
            case "goToJail":
                player[turn-1].setJailed(true);
                Spatial prison = localRootNode.getChild("Models/cage/cage.blend");
                prison.setLocalTranslation(0.6658814f, 1.1049194f, 0.5559678f);
                player[turn-1].setPosition(10);
                player[turn-1].movePiece(time, 0);
                gameGui.showJailed(turn);
                break;
            default:
                break;
        }
        
        System.out.println(chest[shuffle].getText() + chest[shuffle].getAction() + player[turn-1].getMoney());
    }

    private int[] getDiceRoll() {
        int[] diceRollNumber = new int[2];
        Random rand = new Random();
        
        diceRollNumber[0] = rand.nextInt(6) + 1;
        diceRollNumber[1] = rand.nextInt(6) + 1;
        
        return diceRollNumber;
    }
    
    private int getShuffleCommunity(){
        int shuffle;
        Random rand = new Random();
        
        shuffle = rand.nextInt(16);
        
        return shuffle;
    }
    
    private int getShuffleChance(){
        int shuffle;
        Random rand = new Random();
        
        shuffle = rand.nextInt(11) + 15;
        
        return shuffle;
    }
    
    private void updateData() {

        ArrayList<String> originalList = player[turn - 1].getProperties();
        ArrayList<String> MortgagedList = new ArrayList<String>();
        ArrayList<String> NotMortgagedList = new ArrayList<String>();

        for (int i = 0; i < originalList.size(); i++) {

            if (property[getPropertyIndexFromLocation(Integer.parseInt(originalList.get(i)))].isMortaged()) {
                MortgagedList.add(originalList.get(i));
                //System.out.println("test here" + MortgagedList.get(0));
            } else {
                NotMortgagedList.add(originalList.get(i));
            }
        }

        gameGui.setProps(NotMortgagedList);
        gameGui.setMortgaged(MortgagedList);

    }

    private void buyMethod() {
        if (getPropertyLocation() != -1) {
            if (property[getPropertyLocation()].getOwner() == 0) {
                if (player[turn - 1].getMoney() >= property[getPropertyLocation()].getBasePrice()) {
                    property[getPropertyLocation()].buy(turn);
                    if (property[0].getClass() == Land.class) {
                        player[turn - 1].withdrawMoney(property[getPropertyLocation()].getBasePrice());
                        gameGui.showRedDollar(turn - 1); ////////
                        player[turn - 1].addProperty(String.valueOf(player[turn - 1].getPosition()));
                        gameGui.playerBoughtProp(turn,String.valueOf(player[turn - 1].getPosition()),String.valueOf(property[getPropertyLocation()].getBasePrice())); //////
                    }
                    //System.out.println(property[getPropertyLocation()].getBasePrice());
                    //System.out.println(turn + " " + player[turn - 1].getMoney());
                    setLandColor(player[turn - 1].getPosition(), player[turn - 1].getColor());
                    //payTime = false;
                }
            }
        }
    }

    private void auctionMethod() {

    }
    
    private void jailMethod(String choice) {
        switch (choice) {
            case "pay":
                player[turn - 1].withdrawMoney(100);
                gameGui.showRedDollar(turn - 1);
                player[turn - 1].setJailed(false);
                Spatial prison = localRootNode.getChild("Models/cage/cage.blend");
                prison.setLocalTranslation(0.6658814f, -14.940121f, 0.5559678f);
                break;
            case "roll":
                System.out.println("Im at Roll");
                rollMethod();
                if (diceRollNumber[0] == diceRollNumber[1]) {
                    player[turn - 1].setJailed(false);
                    prison = localRootNode.getChild("Models/cage/cage.blend");
                    prison.setLocalTranslation(0.6658814f, -14.940121f, 0.5559678f);
                }
                else{
                    //end turn
                }
                break;
            case "card":
                if (player[turn - 1].isGetOutOfJail()) {
                    player[turn - 1].setJailed(false);
                    prison = localRootNode.getChild("Models/cage/cage.blend");
                    prison.setLocalTranslation(0.6658814f, -14.940121f, 0.5559678f);
                    player[turn - 1].setGetOutOfJail(false);
                }
                break;
            default:
                break;
        }
    }

    private void specialLandMethod() {
        switch (player[turn - 1].getPosition()) {
            case 0:
                player[turn - 1].depositMoney(200);
                gameGui.showGreenDollar(turn - 1);
                break;
            case 4:
                player[turn - 1].withdrawMoney(200);
                gameGui.showRedDollar(turn - 1);
                break;
            case 38:
                player[turn - 1].withdrawMoney(100);
                gameGui.showRedDollar(turn - 1);
                break;
            default:
                break;
        }
    }

    private void rentMethod() {
        if (getPropertyLocation() != -1) {
            player[turn - 1].withdrawMoney(property[getPropertyLocation()].rent());
            gameGui.showRedDollar(turn - 1);
            //System.out.println(property[getPropertyLocation()].getOwner());
            player[property[getPropertyLocation()].getOwner() - 1].depositMoney(property[getPropertyLocation()].rent());
            gameGui.showGreenDollar(property[getPropertyLocation()].getOwner() - 1);
            //System.out.println("Rent is " + property[getPropertyLocation()].rent());
            //System.out.println(turn + "Money after rent" + player[turn - 1].getMoney());
            //System.out.println("owner money " + player[property[getPropertyLocation()].getOwner() - 1].getMoney());
            gameGui.updateFinance(property[getPropertyLocation()].getOwner(), player[property[getPropertyLocation()].getOwner() - 1].getMoney());
            payTime = false;
        }
    }

    private void moveMethod() {
        if (firstTimeM) {
            
            player[turn - 1].updatePosition(diceRollNumber[0] + diceRollNumber[1]);
            int after = player[turn-1].getPosition();
            if(after < before){
                player[turn-1].depositMoney(200);
            }
            firstTimeM = false;
        }
        player[turn - 1].movePiece(time);
    }

    private void rollMethod() {
        if (!firstTime) {
            diceRollNumber = getDiceRoll();
            firstTime = true;
            before = player[turn-1].getPosition();
        }
        dice.roll(diceRollNumber, time);
        if (dice.count == 0) {
            rollingPhase = false;
            firstTime = false;
            firstTimeM = true;
            gameGui.hideSpacetoRoll();
            gameGui.showTabImg();
        }
    }

    private int getPropertyLocation() {
        for (int i = 0; i < 28; i++) {
            if (property[i].getLocation() == player[turn - 1].getPosition()) {
                return i;
            }
        }
        if (player[turn - 1].getPosition() == 2 || player[turn - 1].getPosition() == 17 || player[turn - 1].getPosition() == 33) {
            return -2;
        } else if (player[turn - 1].getPosition() == 7 || player[turn - 1].getPosition() == 22 || player[turn - 1].getPosition() == 36) {
            return -3;
        }
        return -1;
    }

    private void miscellaneous() {
        switch (player[turn - 1].getPosition()) {
            case 4:
                player[turn - 1].withdrawMoney(200);
                gameGui.showRedDollar(turn - 1);
                break;
            case 38:
                player[turn - 1].withdrawMoney(100);
                gameGui.showRedDollar(turn - 1);
                break;
            case 30:
                player[turn - 1].setJailed(true);
                Spatial prison = localRootNode.getChild("Models/cage/cage.blend");
                prison.setLocalTranslation(0.6658814f, 1.1049194f, 0.5559678f);
                player[turn - 1].updatePosition(20);
                player[turn - 1].movePiece(time, 20);
                gameGui.showJailed(turn);
                break;
            default:
                break;
        }
    }

    private int getPropertyIndexFromLocation(int location) {
        for (int i = 0; i < 28; i++) {
            if (property[i].getLocation() == location) {
                return i;
            }
        }
        return -1;
    }

    private void setLandColor(int location, String color) {
        String landDirectory = "owner_" + location;
        Spatial mark = localRootNode.getChild(landDirectory);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        if (color.equals("Red")) {
            mat.setColor("Color", ColorRGBA.Red);
        } else if (color.equals("Black")) {
            mat.setColor("Color", ColorRGBA.Black);
        }   else if(color.equals("White")){
            mat.setColor("Color", ColorRGBA.White);
        } else if(color.equals("Grass")){
            mat.setColor("Color", ColorRGBA.Green);
        }
        else if (color.equals("Orange")) {
            mat.setColor("Color", ColorRGBA.Orange);
        }
        mark.setMaterial(mat);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        rootNode.attachChild(localRootNode);
        scene = assetManager.loadModel("Scenes/current.j3o");
        localRootNode.attachChild(scene);
        flyByCamera.setMoveSpeed(7.0f);
        dice = new Dice(localRootNode);
        player = new Player[4];
        gameGui = new GameGUI();
        gameGui = stateManager.getState(GameGUI.class);
        intializePlayers();
        time = 0;
        intializeInput();
        diceRollNumber = new int[2];
        flyByCamera.setMoveSpeed(0);
        //music();
        //setLandColor(1, "Red");
    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (!keyPressed) {
                switch (name) {
                    case "Dice":
                        rollingPhase = true;
                        //camera.update();
                        break;
                    case "FCam":
                        if (closeCam == false) {
                            flyByCamera.setDragToRotate(true);
                            closeCam = true;
                        }
                        break;
                    case "CCam":
                        flyByCamera.setDragToRotate(false);
                        closeCam = false;
                        break;
                    case "SelectorUp":
                        selector.move("Up");
                        gameGui.updateCard(selector.getLocation());
                        break;
                    case "SelectorDown":
                        selector.move("Down");
                        gameGui.updateCard(selector.getLocation());
                        break;
                    case "SelectorEnable":
                        selector.enable();
                        /*if (property[getPropertyIndexFromLocation(player[turn-1].getPosition())] instanceof Land) {
                                Land land = (Land) property[0];
                                land.build();
                                property[getPropertyIndexFromLocation(player[turn-1].getPosition())] = land;
                                //System.out.println(getPropertyIndexFromLocation(player[turn-1].getPosition())].ge);
                            }*/
                        break;
                    case "house":
                        Building house = new Building(localRootNode, "House");
                    case "EndTurn":
                        if(gamePhase){
                        endTurn = true;
                        gamePhase = false;
                        rollingPhase = false;
                        }
                    default:
                        break;
                }
            }
        }
    };

    //get from host menu
    private void intializePlayers() {
        for(int i=MainMenu.NumberOfPlayers-1; i>=0; i--){
        player[i] = new Player(i, localRootNode, MainMenu.Colours.get(i), MainMenu.Names.get(i), camera);
        //player[0].roll(time);
        }
    }

    /*private void music()
    {
        audio_nature = new AudioNode(assetManager, "Sounds/background.wav", DataType.Stream);
        audio_nature.setLooping(true);  // activate continuous playing
        audio_nature.setPositional(true);
        audio_nature.setVolume(3);
        localRootNode.attachChild(audio_nature);
        audio_nature.play(); 
    }*/
    private void intializeInput() {
        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_MEMORY);
        selector = new Selector(localRootNode, assetManager);
        inputManager.addMapping("Dice", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("CCam", new KeyTrigger(KeyInput.KEY_C));
        inputManager.addMapping("FCam", new KeyTrigger(KeyInput.KEY_F));
        inputManager.addMapping("SelectorUp", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("SelectorDown", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("SelectorEnable", new KeyTrigger(KeyInput.KEY_O));
        inputManager.addMapping("EndTurn", new KeyTrigger(KeyInput.KEY_TAB));
        inputManager.addMapping("house", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addListener(actionListener, "Dice", "CCam", "FCam", "SelectorUp", "SelectorDown", "SelectorEnable", "EndTurn", "house");
    }
}