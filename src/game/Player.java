package game;

import java.util.ArrayList;

import static game.Main.getChoice;

public class Player {
    private ArrayList<Card> hand = new ArrayList<>();
    private boolean skip, out, allin;
    private double maxEarn, cash;
    private int placement;

    public int getPlacement(){ return placement; }

    public Player(int place){
        placement = place;
    }
    /**
     * Sets the player's cash
     *
     * @param money Value to set the cash
     */
    public void setCash(double money) {
        cash = money;
    }

    public boolean hasAce(){ return new HandChecker(hand).hasAce() != -1; }

    public Card tradeOutCard(int i){ return hand.remove(i); }

    public boolean isAllin(){ return allin; }

    /**
     * Gets the player's cash
     */
    public double getCash() {
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
    public void dealTo(Card card){ hand.add(card); }
    public void dealToHoldEm(ArrayList<Card> card){
        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(card);
        for(int i = 0; i < 3; i++){
            int j = Integer.parseInt(getChoice("Player " + placement + ". Choose the card by number (starting at 0) that you would like to count towards your winning deck. Your deck is " + hand.toString().substring(1, hand.toString().length() - 1) + "\nThe board has " + cards.toString().substring(1, cards.toString().length() - 1) + ". You can add " + (3 - i) + " more cards."));
            hand.add(cards.remove(j));
        }
    }

    //possibly have these return things
    public void call(double bet) {
        cash -= bet;
    }

    public boolean isBankrupt(){ return cash == 0; }

    public ArrayList<Card> fold() {
        out = true;
        return hand;
    }

    public int raise(double bet, int maxBet) {
        int raise = Integer.parseInt(getChoice("What would you like to raise the bet of " + bet + " too?"));
        if(raise > maxBet)
            return raise(bet, maxBet);
        else {
            cash -= raise;
            return raise;
        }
    }

    public double allIn(double pool){
        double out = cash;
        cash = 0;
        maxEarn = out + pool;
        allin = true;
        return out;
    }

    public void unAllIn(){ allin = false; }

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
            ayy = checkers.highCard(hasAce) + "." + "D";
        }
        checkers.orderCards(hasAce);
        do {
            if(i == 1) {
                hasAce = false;
                checkers.orderCards(false);
            }
            if (checkers.checkStraight(0, 1)) {
                if(ayy.contains("D")){
                    ayy = checkers.highCard(hasAce) + "." + "A";
                } else if(!ayy.contains("B")){
                    ayy = checkers.highCard(hasAce) + "." + "E";
                }
            } else if (checkers.checkStraight(hand.size() - 2, -1)) {
                if(ayy.contains("D")){
                    ayy = checkers.highCard(hasAce) + "." + "A";
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

    public void setSkip(){ skip = true; }
    public boolean getSkip(){ return skip; }
    public void skip(){ skip = false; }
    public boolean getOut(){ return out; }
    public double getMaxEarn(){ return maxEarn; }
    public void setOut(boolean out){ this.out = out; }
}