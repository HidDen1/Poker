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

    /**
     * @param index The placement of the card to get
     * @return A card from the player hand
     */
    public Card getFromHand(int index) {
        return (hand.remove(index));
    }

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
        String ayy = "";
        boolean hasAce = checkers.hasAce() != -1, kinds = false;
        int i = 0, size = 2, j = 0, sub = 2;
        //change to other loop to check for kinds if they are more in the middle of the hand
        do{
            kinds = checkers.checkKind(size, j);
            if(size == 2 && kinds){
                if(checkers.checkKind(4, 2)){
                    ayy = checkers.highCard(hasAce) + "two pair";
                } else {
                    ayy = checkers.highCard(hasAce) + "pair";
                }
            } else if (size == 3 && kinds){
                if(checkers.checkKind(5, 3)){
                    ayy = checkers.highCard(hasAce) + "full house";
                } else {
                    ayy = checkers.highCard(hasAce) + "three of a kind";
                }
            } else if(size == 4 && kinds){
                ayy = checkers.highCard(hasAce) + "four of a kind";
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
        if(checkers.checkFlush(0)){
            ayy = checkers.highSuit() + "flush";
        }
        do {
            if(i == 1)
                hasAce = false;
            checkers.orderCards(hasAce);
            //Make straight flush priority first
            if (checkers.checkStraight(0, 1)) {
                ayy = checkers.highCard(hasAce) + "straight";
            } else if (checkers.checkStraight(hand.size(), -1)) {
                ayy = checkers.highCard(hasAce) + "straight";
            }
            i++;
        } while(hasAce);
        return null;
    }



    //REMOVE LATER
    public void showHand(){
        Main.printList(hand);
    }
}