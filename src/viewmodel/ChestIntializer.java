/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import model.Chest;

/**
 *
 * @author dell
 */
public class ChestIntializer {

    Chest[] chest;

    public ChestIntializer() {
        chest = new Chest[27];
        communityChest();
        chanceChest();
    }

    public Chest[] getChest() {
        return chest;
    }

    private void communityChest() {
        chest[0] = new Chest(0, "move", "Advance to 'Go'.");
        chest[1] = new Chest(200, "deposit", "Bank error in your favor. Collect $200.");
        chest[2] = new Chest(50, "withdraw", "Doctor's fees. {fee} Pay $50.");
        chest[3] = new Chest(50, "deposit", "From sale of stock you get $50.");
        chest[4] = new Chest(0, "getOutOfJail", "Get Out of Jail Free.");
        chest[5] = new Chest(0, "goToJail", "Go to Jail.");
        chest[6] = new Chest(50, "everyone", "Grand Opera Night Collect $50 from every player for opening night seats.");
        chest[7] = new Chest(100, "deposit", "Holiday Fund matures. Receive $100.");
        chest[8] = new Chest(20, "deposit", "Income tax refund. Collect $20.");
        chest[9] = new Chest(10, "everyone", "It is your birthday. Collect $10 from every player.");
        chest[10] = new Chest(100, "deposit", "Life insurance matures – Collect $100.");
        chest[11] = new Chest(50, "withdraw", "Hospital Fees. Pay $50.");
        chest[12] = new Chest(50, "withdraw", "School fees. Pay $50.");
        chest[13] = new Chest(25, "deposit", "Receive $25 consultancy fee.");
        chest[14] = new Chest(10, "deposit", "You have won second prize in a beauty contest. Collect $10.");
        chest[15] = new Chest(100, "deposit", "You inherit $100.");
    }
    
    private void chanceChest(){
        chest[16] = new Chest(0, "move", "Advance to 'Go'.");
        chest[17] = new Chest(24, "move", "Advance to Illinois Ave.");
        chest[18] = new Chest(11, "move", "Advance to St. Charles Place.");
        chest[19] = new Chest(50, "deposit", "Bank pays you dividend of $50.");
        chest[20] = new Chest(0, "getOutOfJail", "Get out of Jail Free.");
        chest[21] = new Chest(0, "goToJail", "Go to Jail. Go directly to Jail.");
        chest[22] = new Chest(15, "withdraw", "Pay poor tax of $15.");
        chest[23] = new Chest(5, "move", "Take a trip to Reading Railroad.");
        chest[24] = new Chest(50, "withdraw", "You have been elected Chairman of the Board. Pay each player $50.");
        chest[25] = new Chest(150, "deposit", "Your building and loan matures. Receive $150.");
        chest[26] = new Chest(100, "deposit", "You have won a crossword competition. Collect $100.");
    }

}
