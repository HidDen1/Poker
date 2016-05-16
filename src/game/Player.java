package game;

import card.Card;

import java.util.ArrayList;

public class Player {
    private int cash;
    private ArrayList<Card> hand = new ArrayList<Card>();

    /**
     * Sets the player's cash
     *
     * @param money Value to set the cash
     */
    public void setCash(int money) {
        cash = money;
    }

    /**
     * Gets the player's cash
     */
    public int getCash() {
        return (cash);
    }

    public ArrayList<Card> getHand() {
        return (hand);
    }

    public void killHand(){ hand.clear(); }

    /**
     * @param cards The cards to add to the player's hand
     */
    public void dealTo(ArrayList<Card> cards) {
        hand.addAll(cards);
    }

    //possibly have these return things
    public void call() {

    }

    public void fold() {

    }

    public void raise() {

    }

    public String checkHand() {
        HandChecker checkers = new HandChecker(hand);
        boolean hasAce = checkers.hasAce() != -1, kinds;
        String ayy = "";
        int i = 0, size = 2, j = 0, sub = 2;
        checkers.orderCards(hasAce);
        //change to other loop to check for kinds if they are more in the middle of the hand
        do{
            kinds = checkers.checkKind(size, j);
            if(size - j == 2 && kinds){
                if(checkers.checkKind(4, 2)){
                    ayy = checkers.highCard(hasAce) + "." + checkers.getPairVal() + "." + "G";
                } else {
                    ayy = checkers.highCard(hasAce) + "." + checkers.getPairVal() + "." +  "H";
                }
            } else if (size - j == 3 && kinds){
                if(checkers.checkKind(5, 3)){
                    ayy = checkers.highCard(hasAce) + "." + checkers.getPairVal() + "." + "C";
                } else {
                    ayy = checkers.highCard(hasAce) + "." + checkers.getPairVal() + "." + "F";
                }
            } else if(size - j == 4 && kinds){
                ayy = checkers.highCard(hasAce) + "." + checkers.getPairVal() + "." + "B";
            }
            if(size != 5) {
                size++;
                j++;
            } else {
                j = 0;
                size -= sub;
                sub--;
            }
        } while(sub != 0);
        if(!ayy.contains("B") && checkers.checkFlush(0)){
            ayy = checkers.highCard(hasAce) + "." + checkers.highSuit() + "." + "D";
        }
        do {
            if(i == 1) {
                hasAce = false;
                checkers.orderCards(hasAce);
            }
            if (checkers.checkStraight(0, 1)) {
                if(ayy.contains("D")){
                    ayy = checkers.highCard(hasAce) + "." + checkers.highSuit() + "." + "A";
                } else if(!ayy.contains("B")){
                    ayy = checkers.highCard(hasAce) + "." + "E";
                }
            } else if (checkers.checkStraight(hand.size() - 2, -1)) {
                if(ayy.contains("D")){
                    ayy = checkers.highCard(hasAce) + "." + checkers.highSuit() + "." + "A";
                } else if(!ayy.contains("B")){
                    ayy = checkers.highCard(hasAce) + "." + "E";
                }
            }
            i++;
        } while(hasAce && (!ayy.contains("E") || !ayy.contains("A")));
        if(ayy.isEmpty()){
            ayy = checkers.highCard(hasAce) + "." + "I";
        }
        return ayy;
    }

    //REMOVE LATER
    public void showHand(){
        Main.printList(hand);
    }
}