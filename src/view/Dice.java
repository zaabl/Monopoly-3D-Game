/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import view.Piece;
import view.Piece;
import com.jme3.math.Quaternion;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.UpdateControl;
import java.util.Random;

/**
 *
 * @author dell
 */
public class Dice {

    private Spatial[] body;
    public int count;
    private int randomNumber, sum;
    private float timeStart;
    private float timePlusOne;
    private int moved;
    private boolean isDouble;

    public Dice(Node localRootNode) {
        body = new Spatial[2];
        body[0] = localRootNode.getChild("DiceOne");
        body[1] = localRootNode.getChild("DiceTwo");
        count = 0;
        timePlusOne = 0;
        moved = 0;
        body[0].setLocalTranslation(-100, -100, -100);
        body[1].setLocalTranslation(-100, -100, -100);
    }

    public void roll(int[] diceRollNumber, float time) {
        if (count == 0) {
            Quaternion defaultRotation = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
            body[0].setLocalTranslation(1.2865446f, 2.2365348f, 2.894708f);
            body[0].setLocalRotation(defaultRotation);
            body[1].setLocalTranslation(1.4456403f, 2.2331016f, 2.9217227f);
            body[1].setLocalRotation(defaultRotation);
        }

        if (time > timePlusOne && count < 21) {
            body[0].rotate(45.0f, 45.0f, 0.0f);
            body[0].setLocalTranslation(body[0].getLocalTranslation().x, body[0].getLocalTranslation().y - 0.05f, body[0].getLocalTranslation().z - 0.15f);
            body[1].rotate(45.0f, 45.0f, 0.0f);
            body[1].setLocalTranslation(body[1].getLocalTranslation().x, body[1].getLocalTranslation().y - 0.05f, body[1].getLocalTranslation().z - 0.15f);
            
            
            timePlusOne = time + 0.05f;
            count++;
        } else if (count == 21) {
            sum = 0;
            for (int i = 0; i < 2; i++) {
                sum += diceRollNumber[i];
                switch (diceRollNumber[i]) {
                    case 1:
                        Quaternion oneRotation = new Quaternion(-0.71879184f, -0.11744891f, 0.154088f, 0.6676833f);
                        body[i].setLocalRotation(oneRotation);
                        break;
                    case 2:
                        Quaternion twoRotation = new Quaternion(-0.68317324f, -0.7007567f, 0.17021513f, -0.11506968f);
                        body[i].setLocalRotation(twoRotation);
                        break;
                    case 3:
                        Quaternion threeRotation = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
                        body[i].setLocalRotation(threeRotation);
                        break;
                    case 4:
                        Quaternion fourRotation = new Quaternion(1.0f, 0.0f, 0.0f, -4.371139E-8f);
                        body[i].setLocalRotation(fourRotation);
                        break;
                    case 5:
                        Quaternion fiveRotation = new Quaternion(0.16734494f, 0.11210959f, 0.6838891f, -0.7012294f);
                        body[i].setLocalRotation(fiveRotation);
                        break;
                    case 6:
                        Quaternion sixRotation = new Quaternion(-0.3727068f, -0.4236736f, 0.59733176f, -0.56989884f);
                        body[i].setLocalRotation(sixRotation);
                        break;
                    default:
                        break;
                }
            }
            count++;
            timePlusOne = 0;
        } /*else if (moved < sum && time > timePlusOne) {
            moveBody(piece, time);
        }
        */
        if (count > 21) {
            count = 0;
            //timePlusOne = 0;
            //moved = 0;
        }
        
    }
    
    /*private void moveBody(Piece piece, float time){
        piece.moveBody(1, time, timePlusOne);
            moved += 1;
            timePlusOne = time + 0.5f;
    }*/

    

    public int getSum() {
        return sum;
    }
    
    /*public boolean jailRoll(float time){
        if (count == 0) {
            body[0].setLocalTranslation(1.2865446f, 2.2365348f, 2.894708f);
            body[0].setLocalRotation(defaultRotation);
            body[1].setLocalTranslation(1.4456403f, 2.2331016f, 2.9217227f);
            body[1].setLocalRotation(defaultRotation);
        }

        if (time > timePlusOne && count < 21) {
            body[0].rotate(45.0f, 45.0f, 0.0f);
            body[0].setLocalTranslation(body[0].getLocalTranslation().x, body[0].getLocalTranslation().y - 0.05f, body[0].getLocalTranslation().z - 0.15f);
            body[1].rotate(45.0f, 45.0f, 0.0f);
            body[1].setLocalTranslation(body[1].getLocalTranslation().x, body[1].getLocalTranslation().y - 0.05f, body[1].getLocalTranslation().z - 0.15f);
            timePlusOne = time + 0.05f;
            count++;
        } else if (count == 21) {
            sum = 0;
            for (int i = 0; i < 2; i++) {
                generateRandom();
                if(randomNumber == sum)
                    isDouble = true;
                sum += randomNumber;
                switch (randomNumber) {
                    case 1:
                        body[i].setLocalRotation(oneRotation);
                        break;
                    case 2:
                        body[i].setLocalRotation(twoRotation);
                        break;
                    case 3:
                        body[i].setLocalRotation(threeRotation);
                        break;
                    case 4:
                        body[i].setLocalRotation(fourRotation);
                        break;
                    case 5:
                        body[i].setLocalRotation(fiveRotation);
                        break;
                    case 6:
                        body[i].setLocalRotation(sixRotation);
                        break;
                    default:
                        break;
                }
            }
            count++;
            timePlusOne = 0;
        }
        return isDouble;
    }*/

}
