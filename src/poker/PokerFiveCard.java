package poker;

import game.Main;

public class PokerFiveCard extends Poker {
    @Override
    public void playGame() {
        betPhase(Integer.parseInt(Main.getChoice("Player " + getTurn() + "'s turn. 1: Call; 2: Raise; 3: Fold")));
    }

    private void tradeCard(){
        //Need to find out how many betting phases and what each phase does before continuing. Have wildcard extend this or make it a boolean/int field

    }
}
