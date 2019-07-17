/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import java.net.*;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Land;
//import network.Client;
//import network.Server;

/**
 *
 * @author dell
 */
public class Multiplayer extends AbstractAppState {

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
    private Player player;
    private Selector selector;
    private Property[] property;
    private PropertyIntializer propertyIntializer;
    private boolean rollDoublebool = true, isRolledDouble = false;
    private boolean payTime = false;
    private boolean updatingPosition;
    private boolean firstTime = false;
    private int[] diceRollNumber;
    private boolean movingPhase = false;
    private boolean firstTimeM;
    private boolean stoppingPhase = false;
    //private Client client;
    private int tag;
    //private Server server;
            
    public Multiplayer(SimpleApplication app) {
        rootNode = app.getRootNode();
        assetManager = app.getAssetManager();
        inputManager = app.getInputManager();
        flyByCamera = app.getFlyByCamera();
        camera = app.getCamera();
        property = new Property[28];
        propertyIntializer = new PropertyIntializer();
        property = propertyIntializer.getProperty();

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

        
        /*if (!endTurn) {
            if (rollingPhase && !stoppingPhase) {
                if (!player.isJailed()) {

                    rollMethod();
                    movingPhase = true;
                } else {
                    jailMethod();
                }
            } else if (movingPhase) {
                moveMethod();
                if (!player.isMoving()) {
                    stoppingPhase = true;
                    movingPhase = false;
                }
            } else if (stoppingPhase) {
                if (getPropertyLocation() != -1) {
                    if (property[getPropertyLocation()].getOwner() == 0) {
                        //Buy or Auction
                        String choice = "buy";
                        if ("buy".equals(choice)) {
                            buyMethod();
                            stoppingPhase = false;
                            gamePhase = true;
                        } else if ("auction".equals(choice)) {
                            auctionMethod();
                        }
                    } else {
                        rentMethod();
                        gamePhase = true;
                        stoppingPhase = false;
                    }

                } else if (false) {

                } else if (getPropertyLocation() == -1) {
                    specialLandMethod();
                } else if (gamePhase) {
                    // Sell - Mortgage - DeMortgage - Buy Houses - Buy Hotels - Sell Houses 
                }
            }
        } else {
                endTurn = false;
                stoppingPhase = false;
                turn++;
                if (turn == 3) {
                    turn = 1;
                }
                player.cameraIntialize();
            }*/
        /*
        if(server.getCurrentTurn() == player.getTag()){
            if(rollingPhase){
               generateRollNumber();
               rollMethod();
               client.writeDiceStream(diceRollNumber);
               
            } 
            
        } 
        */
        

    }

    

    

    private void buyMethod() {
        if (getPropertyLocation() != -1) {
            if (property[getPropertyLocation()].getOwner() == 0) {
                if (player.getMoney() >= property[getPropertyLocation()].getBasePrice()) {
                    property[getPropertyLocation()].buy(turn);
                    if (property[0].getClass() == Land.class) {
                        player.withdrawMoney(property[getPropertyLocation()].getBasePrice());
                    }
                    //System.out.println(property[getPropertyLocation()].getBasePrice());
                    //System.out.println(turn + " " + player[turn - 1].getMoney());
                    setLandColor(player.getPosition(), player.getColor());
                    //payTime = false;
                }
            }
        }
    }

    private void auctionMethod() {

    }

    private void jailMethod() {
        String choice = "pay";
        switch (choice) {
            case "pay":
                player.withdrawMoney(100);
                player.setJailed(false);
                break;
            case "roll":
                rollMethod();
                if (diceRollNumber[0] == diceRollNumber[1]) {
                    player.setJailed(false);
                    player.withdrawMoney(100);
                }
                break;
            case "card":
                if (player.isGetOutOfJail()) {
                    player.setJailed(false);
                    player.setGetOutOfJail(false);
                }
                break;
            default:
                break;
        }
    }

    private void specialLandMethod() {
        switch (player.getPosition()) {
            case 0:
                player.depositMoney(200);
                break;
            case 4:
                player.withdrawMoney(200);
                break;
            case 38:
                player.withdrawMoney(100);
                break;
            default:
                break;
        }
    }

    private void rentMethod() {
        if (getPropertyLocation() != -1) {
            player.withdrawMoney(property[getPropertyLocation()].rent());
            //System.out.println(property[getPropertyLocation()].getOwner());
            //player[property[getPropertyLocation()].getOwner() - 1].depositMoney(property[getPropertyLocation()].rent());
            //System.out.println("Rent is " + property[getPropertyLocation()].rent());
            //System.out.println(turn + "Money after rent" + player[turn - 1].getMoney());
            //System.out.println("owner money " + player[property[getPropertyLocation()].getOwner() - 1].getMoney());
            payTime = false;
        }
    }

    private void moveMethod() {
        if (firstTimeM) {
            player.updatePosition(diceRollNumber[0] + diceRollNumber[1]);
            firstTimeM = false;
        }
        player.movePiece(time);
    }

    /*private void generateRollNumber(){
        if (!firstTime) {
            diceRollNumber = controler.getDiceRoll();
            firstTime = true;
        }
    }*/
    
    private void rollMethod() {
        
        dice.roll(diceRollNumber, time);
        if (dice.count == 0) {
            rollingPhase = false;
            firstTime = false;
            firstTimeM = true;
        }
    }

    private int getPropertyLocation() {
        for (int i = 0; i < 28; i++) {
            if (property[i].getLocation() == player.getPosition()) {
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
        }
        mark.setMaterial(mat);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        /*
            
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                server = new Server(12345, 55555);
                server.acceptConnections();
            }
        }   );
        
        t.start();
        */
        //client = new Client("localhost", 9005);
        try {
            //client.run();
        } catch (Exception ex) {
            Logger.getLogger(Multiplayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        //tag = client.getTag();
        super.initialize(stateManager, app);
        rootNode.attachChild(localRootNode);
        scene = assetManager.loadModel("Scenes/current.j3o");
        localRootNode.attachChild(scene);
        flyByCamera.setMoveSpeed(7.0f);
        dice = new Dice(localRootNode);
        intializePlayers();
        time = 0;
        intializeInput();
        diceRollNumber = new int[2];
        
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
                        camera.update();
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
                        break;
                    case "SelectorDown":
                        selector.move("Down");
                        break;
                    case "SelectorEnable":
                        selector.enable();
                        break;
                    case "EndTurn":
                        endTurn = true;
                        gamePhase = false;
                        rollingPhase = false;
                        //client.writeEndTurn();
                    default:
                        break;
                }
            }
        }
    };

    //get from host menu
    private void intializePlayers() {
        player = new Player(tag, localRootNode, "BlackCat", "Kareem", camera);
        System.out.println("here is the el7" + player.getTag());
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
        inputManager.addListener(actionListener, "Dice", "CCam", "FCam", "SelectorUp", "SelectorDown", "SelectorEnable", "EndTurn");
    }
}
