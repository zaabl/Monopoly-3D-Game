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
public class Land extends Property {

    private int buildCost, housesNum, hotelsNum, firstHouseRent, secondHouseRent, thirdHouseRent, fourthHouseRent, hotelRent;
    private boolean completeCollection = false;
            
    public Land(int buildCost, int housesNum, int hotelsNum, int firstHouseRent, int secondHouseRent, int thirdHouseRent, int fourthHouseRent, int hotelRent, int location, int basePrice, int tax, int owner, int morgageValue, boolean mortaged) {
        super(location, basePrice, tax, owner, morgageValue, mortaged);
        this.buildCost = buildCost;
        this.housesNum = housesNum;
        this.hotelsNum = hotelsNum;
        this.firstHouseRent = firstHouseRent;
        this.secondHouseRent = secondHouseRent;
        this.thirdHouseRent = thirdHouseRent;
        this.fourthHouseRent = fourthHouseRent;
        this.hotelRent = hotelRent;
    }

    @Override
    public int rent() {
        if(!mortaged){
                if (housesNum == 1) {
                return firstHouseRent;
            } else if (housesNum == 2) {
                return secondHouseRent;
            } else if (housesNum == 3) {
                return thirdHouseRent;
            } else if (housesNum == 4) {
                return fourthHouseRent;
            } else if (hotelsNum == 1) {
                return hotelRent;
            } else if(completeCollection){
                return tax * 2;
            } else{
                return tax;
            }
        } else {
            return 0;
        }
        
    }
   
    public void setCompleteCollection() {
        completeCollection = true;
    }

    public int getHousesNum() {
        return housesNum;
    }
        
    public void build(){
        if(completeCollection && hotelsNum != 1){
            if(housesNum == 4){
                hotelsNum++;
            } else {
                housesNum++;
            }
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
