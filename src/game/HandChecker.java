package game;

import card.Card;

import java.util.ArrayList;

public class HandChecker {
    ArrayList<Card> hand = new ArrayList<>();

    public HandChecker(ArrayList<Card> hand){
        this.hand = hand;
    }
    /**
     * @param i Start position; 0 for low, size - 2 for high
     * @param j Inc/Dec amt; 1 for low, -1 for high
     * @return whether a straight exists or not
     */
    public boolean checkStraight(int i, int j){
        if(hand.get(i).getValue()[0] + 1 == hand.get(i + 1).getValue()[0]){
            if((i == hand.size() - 2 && j == 1) || (i == 0 && j == -1))
                return true;
            return checkStraight(i + j, j);
        }
        return false;
    }
    public int hasAce(){
        for(Card card : hand){
            if(card.getName().equals("Ace"))
                return hand.indexOf(card);
        }
        return -1;
    }
    /**
     * Makes the player's cards be in order
     * @param ace Make true to set the ace to its high value
     */
    public void orderCards(boolean ace){
        for(int i = 0; i < hand.size() - 1; i++) {
            for (int j = i + 1; j < hand.size(); j++) {
                if(hand.get(i).getValue()[0] > hand.get(j).getValue()[0]){
                    Card card = hand.get(i);
                    hand.set(i, hand.get(j));
                    hand.set(j, card);
                }
            }
        }
        if(ace)
            hand.add(hand.remove(hasAce()));
    }

    /**
     * @param ace Whether the given hand has an ace
     * @return The value of the highest Card
     */
    public int highCard(boolean ace){
        if(ace){
            return 14;
        } else {
            int val = hand.get(0).getValue()[0];
            for(Card card : hand){
                if(card.getValue()[0] > val){
                    val = card.getValue()[0];
                }
            }
            return val;
        }
    }

    /**
     * @param i Starting pos
     * @return Whether the given hand does have a flush
     */
    public boolean checkFlush(int i){
        if(hand.get(i).getType().equals(hand.get(i + 1).getType())){
            if(i == hand.size() - 2)
                return true;
            return checkFlush(++i);
        }
        return false;
    }
    //TODO: may need to change later to find highest suit

    /**
     * @return The value of the highest suit
     */
    public int highSuit(){
        if(hand.get(0).getType().equals("C")){
            return 1;
        } else if(hand.get(0).getType().equals("D")){
            return 2;
        } else if(hand.get(0).getType().equals("H")){
            return 3;
        } else {
            return 4;
        }
    }

    /**
     * @param size The size of the pair to find
     * @param i Starting pos
     * @return Whether the hand has a kind of the size
     */
    public boolean checkKind(int size, int i){
        if(hand.get(i).getValue()[0] == hand.get(i + 1).getValue()[0]){
            if(i == size - 2)
                return true;
            else
                return checkKind(size, ++i);
        } else
            return false;
    }
}
