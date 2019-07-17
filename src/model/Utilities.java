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
public class Utilities extends Property {

    private int diceSum, numberOfOwned;
    
    public Utilities(int location, int basePrice, int tax, int owner, int morgageValue, boolean mortaged) {
        super(location, basePrice, tax, owner, morgageValue, mortaged);
    }

    public void getDice(int diceSum){
        this.diceSum = diceSum;
    }
    
    public void getNumberOfOwned(int numberOfOwned){
        this.numberOfOwned = numberOfOwned;
    }
    
    @Override
    public int rent() {
        if(numberOfOwned == 2){
            return diceSum * 4;
        } else {
            return diceSum * 10;
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
