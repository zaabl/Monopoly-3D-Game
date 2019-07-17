/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author dell
 */
public class Piece {

    private final String figure;
    private String directory;
    public int location = 0;
    private Node localRootNode;
    private Spatial body;

    public Piece(String figure, Node localRootNode) {
        this.figure = figure;
        this.localRootNode = localRootNode;
        if (figure.equals("Red")) {
            directory = "RedCat";
        } else if(figure.equals("Black")){
            directory = "BlackCat";
        } else if(figure.equals("Grass")){
            directory = "GrassCat";
        } else if(figure.equals("White")){
            directory = "WhiteCat";
        }

        try {
            body = localRootNode.getChild(directory);
        } catch (Exception e) {
            System.out.println("Spatial Directory is empty");
        }
        
        body.setLocalTranslation(2.4403038f, 1.1394217f, 0.5602508f);
    }

    public String getDirectory() {
        return directory;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public void moveBody(float time) {
        if (location < 10) {
            body.setLocalTranslation((body.getLocalTranslation().x - 0.17f), body.getLocalTranslation().y, body.getLocalTranslation().z);
        } else if (location < 20) {
            body.setLocalTranslation(body.getLocalTranslation().x, body.getLocalTranslation().y, (body.getLocalTranslation().z - 0.17f));

        } else if (location < 30) {
            body.setLocalTranslation((body.getLocalTranslation().x + 0.17f), body.getLocalTranslation().y, body.getLocalTranslation().z);

        } else if (location < 40) {
            body.setLocalTranslation(body.getLocalTranslation().x, body.getLocalTranslation().y, (body.getLocalTranslation().z + 0.17f));

        }

        location ++;

        if (location == 40) {
            location = 0;
        }
        
        switch (location) {
            case 10:
                body.rotate(0.0f, 80.0f, 0.0f);
                body.setLocalTranslation(0.60590744f, 1.1394217f, 0.4673968f);
                break;
            case 20:
                body.rotate(0.0f, 80.0f, 0.0f);
                body.setLocalTranslation(0.7002964f, 1.1394217f, -1.3687758f);
                break;
            case 30:
                body.rotate(0.0f, 80.0f, 0.0f);
                body.setLocalTranslation(2.5436373f, 1.1394217f, -1.2999854f);
                break;
            case 0:
                body.rotate(0.0f, -240.0f, 0.0f);
                body.setLocalTranslation(2.4403038f, 1.1394217f, 0.56025076f);
                break;
            default:
                break;
        }

        //System.out.println(location);
    }
//}
}
