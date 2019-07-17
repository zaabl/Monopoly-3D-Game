/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import view.Dice;
import view.Piece;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author dell
 */
public class Player {

    private Vector3f location, direction, left, up;
    private String name;
    private int money, tag, position;
    private Piece piece;
    private Camera camera;
    private String Color;
    public boolean rolling = false, jailed = false, isDouble = false;
    public String figure;
    private ArrayList<String> properties;
    private boolean moving = false;
    private float timePlusOne;
    private boolean getOutOfJail = false;
    private boolean Lost;

    public Player(int tag, Node localRootNode, String figure, String name, Camera camera) {
        this.tag = tag;
        this.figure = figure;
        this.camera = camera;
        cameraIntialize();
        this.name = name;
        piece = new Piece(figure, localRootNode);
        position = 0;
        properties = new ArrayList<String>();
        money = 1500;
        timePlusOne = 0;
        if ("Black".equals(figure)) {
            Color = "Black";
        } else if ("Red".equals(figure)) {
            Color = "Red";
        } else if ("Grass".equals(figure)) {
            Color = "Grass";
        } else if ("White".equals(figure)) {
            Color = "White";
        }
    }

    public boolean isLost() {
        return Lost;
    }

    
    
    public boolean isGetOutOfJail() {
        return getOutOfJail;
    }

    public void setGetOutOfJail(boolean getOutOfJail) {
        this.getOutOfJail = getOutOfJail;
    }

    
    
    public void cameraIntialize() {
        switch (tag) {
            case 0:
                location = new Vector3f(6.0224757f, 8.575582f, 5.458682f);
                direction = new Vector3f(0.0063584032f, -0.368082f, -0.92977166f);
                up = new Vector3f(0.002520092f, 0.92979336f, -0.36807334f);
                left = new Vector3f(-0.99997663f, 2.7513597E-6f, -0.006839602f);
                camera.setLocation(location);
                camera.setAxes(left, up, direction);
                break;
            case 1:
                location = new Vector3f(-0.9392064f, 8.829856f, -1.6478758f);
                direction = new Vector3f(0.95273757f, -0.30371466f, 0.006963134f);
                up = new Vector3f(0.30370653f, 0.952763f, 0.0022279322f);
                left = new Vector3f(0.0073108673f, 7.882714E-6f, -0.9999733f);
                camera.setLocation(location);
                camera.setAxes(left, up, direction);
                break;
            case 2:
                location = new Vector3f(6.4631467f, 8.088272f, -8.578067f);
                direction = new Vector3f(-9.4623875E-4f, -0.32873517f, 0.94442165f);
                up = new Vector3f(-3.3179688E-4f, 0.9444221f, 0.328735f);
                left = new Vector3f(0.9999995f, 2.2943423E-6f, 0.0010027222f);
                camera.setLocation(location);
                camera.setAxes(left, up, direction);
                break;
            case 3:
                location = new Vector3f(13.516437f, 8.515985f, -1.5305816f);
                direction = new Vector3f(-0.9343182f, -0.35625845f, 0.011381805f);
                up = new Vector3f(-0.35623208f, 0.93438745f, 0.004336506f);
                left = new Vector3f(0.012179911f, 2.8908253E-6f, 0.99992585f);
                camera.setLocation(location);
                camera.setAxes(left, up, direction);
                break;
            default:
                break;
        }

    }

    public String getColor() {
        return Color;
    }

    
    
    public void roll(int[] diceRollNumber, float time) {
        rolling = true;

    }

    public boolean isMoving() {
        return moving;
    }

    public void movePiece(float time) {
        if (getPosition() != piece.getLocation() && time > timePlusOne) {
            timePlusOne = time + 0.5f;
            piece.moveBody(time);
            moving = true;
        } else if(getPosition() == piece.getLocation()){
            moving = false;
        }
    }
    
    public void movePiece(float time, int number){
        while(piece.getLocation() != getPosition()){
            piece.moveBody(time);
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }
    
    

    /*public boolean jailRoll(float time) {
        rolling = true;
        if (dice.count == 0) {
            rolling = false;
        }
        return dice.jailRoll(time);
    }*/
    public void setJailed(boolean jailed) {
        this.jailed = jailed;
    }

    public int getPosition() {
        return position;
    }

    public boolean isJailed() {
        return jailed;
    }

    public String getFigure() {
        return figure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addProperty(String property) {
        properties.add(property);
    }

    public ArrayList<String> getProperties() {
        return properties;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void depositMoney(int ammount) {
        money += ammount;
    }

    public int getMoney() {
        return money;
    }

    public void withdrawMoney(int ammount) {
        money -= ammount;
    }

    public void updatePosition(int position) {
        this.position += position;

        if (this.position >= 40) {
            this.position -= 40;
        }
    }

}
