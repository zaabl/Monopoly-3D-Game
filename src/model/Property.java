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
public abstract class Property {

    protected int location, basePrice, tax, owner, morgageValue;
    protected boolean mortaged;

    public Property(int location, int basePrice, int tax, int owner, int morgageValue, boolean mortaged) {
        this.location = location;
        this.basePrice = basePrice;
        this.tax = tax;
        this.owner = owner;
        this.morgageValue = morgageValue;
        this.mortaged = mortaged;
    }

    public abstract int rent();
    public abstract int morgage();
    public abstract int deMorgage();

    public int getLocation() {
        return location;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
    
    public int sell(){
        owner = 0;
        return basePrice/2;
    }

    public boolean isMortaged() {
        return mortaged;
    }
    
    
    
    public int getBasePrice() {
        return basePrice;
    }
    
    public void buy(int owner){
        setOwner(owner);
    }
    
}








