package poker;

import game.Main;
import game.Player;

import java.util.ArrayList;

public class PokerFiveCard extends Poker {
    @Override
    public void playGame() {
        super.playGame();
        do {
            shuffle();
            dealToPlayers(5);
            betPhase();
            for (int i = 0; i < getUserAmount(); i++) {
                if (getUser(i).hasAce())
                    tradeCards(4, getUser(i));
                else
                    tradeCards(3, getUser(i));
            }
            changeTurn(0);
            betPhase();
            dealOutWinnings(findWinner(getUser(0), getUser(1), 1));
            for(int i = 0; i < getUserAmount(); i++){
                getDeck().addAll(0, getUser(i).getHand());
                getUser(i).killHand();
                if(getUser(i).isBankrupt())
                    getUser(i).setOut(true);
                else
                    getUser(i).setOut(false);
            }
        }while(isVictory() == -1);
    }

    public void tradeCards(int t, Player user){
        int choice = Integer.parseInt(Main.getChoice("It is player " + getTurn() + "'s trade turn. Your cards are: " + user.getHand().toString().substring(1, user.getHand().toString().length() -1) + ". You can trade out " + t + " cards. \nGive the number of the card you want to trade out or -1 to end your turn."));
        if(choice != -1 && choice < 5 && choice > -1) {
            getDeck().add(0, user.tradeOutCard(choice));
            user.dealTo(getDeck().remove(getDeck().size()-1));
            if(t - 1 == 0) {
                System.out.println("Turn ended");
                changeTurn(getTurn() + 1);
            } else
                tradeCards(--t, user);
        } else if(choice == -1) {
            System.out.println("Turn ended");
            changeTurn(getTurn() + 1);
        } else if (choice > 4 || choice < 0)
            tradeCards(t, user);
    }
}
