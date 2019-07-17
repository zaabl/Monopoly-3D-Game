/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import model.Land;
import model.Property;
import model.RailRoad;
import model.Utilities;

/**
 *
 * @author dell
 */
public class PropertyIntializer{
    Property[] property;

    public PropertyIntializer() {
        property = new Property[28];
        intializeLands();
        intializeRailRoad();
        intializeUtilities();
    }
    
    public Property[] getProperty(){
        return property;
    }
    
    private void intializeLands(){
        property[0] = new Land(50, 0, 0, 10, 20, 30, 160, 250, 1, 60, 2, 0, 30, false);
        property[1] = new Land(50, 0, 0, 20, 60, 180, 320, 450, 3, 60, 4, 0, 30, false);
        property[2] = new Land(50, 0, 0, 30, 90, 270, 400, 550, 6, 100, 6, 0, 50, false);
        property[3] = new Land(50, 0, 0, 30, 90, 275, 400, 550, 8, 100, 6, 0, 50, false);
        property[4] = new Land(50, 0, 0, 40, 100, 300, 450, 600, 9, 120, 8, 0, 60, false);
        property[5] = new Land(100, 0, 0, 50, 150, 450, 625, 750, 11, 140, 10, 0, 70, false);
        property[6] = new Land(100, 0, 0, 50, 150, 450, 625, 750, 13, 140, 10, 0, 70, false);
        property[7] = new Land(100, 0, 0, 60, 180, 500, 700, 900, 14, 160, 12, 0, 80, false);
        property[8] = new Land(100, 0, 0, 70, 200, 550, 750, 950, 16, 180, 14, 0, 90, false);
        property[9] = new Land(100, 0, 0, 70, 200, 550, 750, 950, 18, 180, 14, 0, 90, false);
        property[10] = new Land(100, 0, 0, 80, 220, 600, 800, 1000, 19, 200, 16, 0, 100, false);
        property[11] = new Land(150, 0, 0, 90, 250, 700, 875, 1050, 21, 220, 18, 0, 110, false);
        property[12] = new Land(150, 0, 0, 90, 250, 700, 875, 1050, 23, 220, 18, 0, 110, false);
        property[13] = new Land(150, 0, 0, 100, 300, 750, 925, 1100, 24, 240, 20, 0, 120, false);
        property[14] = new Land(150, 0, 0, 110, 330, 800, 975, 1150, 26, 260, 22, 0, 130, false);
        property[15] = new Land(150, 0, 0, 110, 330, 800, 975, 1150, 27, 260, 22, 0, 130, false);
        property[16] = new Land(150, 0, 0, 120, 360, 850, 1025, 1200, 29, 280, 24, 0, 140, false);
        property[17] = new Land(200, 0, 0, 130, 390, 900, 1100, 1275, 31, 300, 26, 0, 150, false);
        property[18] = new Land(200, 0, 0, 130, 390, 900, 1100, 1275, 32, 300, 26, 0, 150, false);
        property[19] = new Land(200, 0, 0, 150, 390, 900, 1200, 1400, 34, 320, 28, 0, 160, false);
        property[20] = new Land(200, 0, 0, 175, 500, 1100, 1300, 1500, 37, 350, 35, 0, 175, false);
        property[21] = new Land(200, 0, 0, 175, 500, 1100, 1700, 2000, 39, 400, 50, 0, 200, false);
    }
    
    private void intializeRailRoad(){
        property[22] = new RailRoad(5, 200, 25, 0, 100, false);
        property[23] = new RailRoad(15, 200, 25, 0, 100, false);
        property[24] = new RailRoad(25, 200, 25, 0, 100, false);
        property[25] = new RailRoad(35, 200, 25, 0, 100, false);
    }
    
    private void intializeUtilities(){
        property[26] = new Utilities(12, 150, 0, 0, 75, true);
        property[27] = new Utilities(28, 150, 0, 0, 75, true);
    }
    
}
