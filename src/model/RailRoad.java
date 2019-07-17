/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author dell
 */
public class RailRoad extends Property {
    private final int ownedTwoRent = 50, ownedThreeRent = 100, ownedFourRent = 200;
    private int numberOfOwned;

    public RailRoad(int location, int basePrice, int tax, int owner, int morgageValue, boolean mortaged) {
        super(location, basePrice, tax, owner, morgageValue, mortaged);
    }

    public void getNumberOfOwned(int numberOfOwned){
        this.numberOfOwned = numberOfOwned;
    }
        
    @Override
    public int rent() {
        if(!mortaged){
            switch (numberOfOwned) {
                case 2:
                    return ownedTwoRent;
                case 3:
                    return ownedThreeRent;
                case 4:
                    return ownedFourRent;
                default:
                    return tax;
            }
        } else {
            return 0;
        }
    }
    
    @Override
    public int morgage() {
        mortaged = true;
        return morgageValue;
    }

    @Override
    public int deMorgage() {
            mortaged = false;
            return morgageValue + morgageValue/10;
    }
    
}
