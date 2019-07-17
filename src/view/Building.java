/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author dell
 */
public class Building {

    private final Node localRootNode;
    private Spatial body;
    private boolean stopped = false;

    public Building(Node localRootNode, String object) {
        this.localRootNode = localRootNode;
        if(object.contains("House")){
            body = localRootNode.getChild("House_01");
        }
        
        setLocation();
    }
    
    public void setLocation(){
        body.setLocalTranslation(2.300353f, 1.06f, 0.42385983f);
    }
    
    public void moveUp(){
        body.move(0,0.01f,0);
        if(body.getLocalTranslation().y == 1.16 ){
            stopped = true;
        }
    }
}
