/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author dell
 */
public class Selector {
    
    Spatial quad;
    boolean enabled = false;
    int location = 1;
    
    public Selector(Node localRootNode, AssetManager assetManager){
        quad = localRootNode.getChild("Selector");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        quad.setMaterial(mat);
        enable();
        
    }
    public void move(String direction){
        if( location >= 0 && location < 10 && direction.equals("Up")){
               location++;
               quad.move(-0.17f,0,0);
        } else if(location > 0 && location <= 10 && direction.equals("Down")){
               location--;
               quad.move(0.17f,0,0);
        } else if(location >= 10 && location < 20 && direction.equals("Up")){
               location++;
               quad.move(0,0,-0.185f);
        } else if(location > 10 && location <= 20 && direction.equals("Down")){
               location--;
               quad.move(0,0,0.185f);
        } else if(location > 20 && location <= 30 && direction.equals("Down")){
               location--;
               quad.move(-0.17f,0,0);
        } else if(location >= 20 && location < 30 && direction.equals("Up")){
               location++;
               quad.move(0.17f,0,0);
        } else if(location > 30 && location <= 40 && direction.equals("Down")){
               location--;
               quad.move(0,0,-0.185f);
        } else if(location >= 30 && location < 40 && direction.equals("Up")){
               location++;
               quad.move(0,0,0.185f);
        } else if(location == 0 && direction.equals("Up")){
            location = 1;
            quad.move(-0.17f,0,0);
        } else if(location == 0 && direction.equals("Down")){
            location = 39;
            quad.move(0,0,-0.185f);
        }
        
        if(location == 40)
            location = 0;
    }
    public void enable()
    {
        if(enabled){
            enabled = false;
            quad.move(0f, 30f, 0f);
        }else if(!enabled){
            enabled = true;
            quad.move(0f, -30f, 0f);
        }
    }

    public int getLocation() {
        return location;
    }
    
    
}
