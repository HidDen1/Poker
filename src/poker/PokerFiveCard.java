package poker;

import game.Main;

public class PokerFiveCard extends Poker {
    @Override
    public void playGame() {
        doPartOne(Integer.parseInt(Main.getChoice("Player " + getTurn() + "'s turn. 1: Call; 2: Raise; 3: Fold")));
    }

    private void doPartOne(int what){
        switch(what){
            case 1:
                getUser(getTurn()).call();
                break;
            case 2:
                getUser(getTurn()).raise();
                break;
            case 3:
                getUser(getTurn()).fold();
                break;
        }
        changeTurn(getTurn() + 1);
        if(getTurn() == getUserAmount())
            doPartTwo();
        else
            doPartOne(Integer.parseInt(Main.getChoice("Player " + getTurn() + "'s turn. 1: Call; 2: Raise; 3: Fold")));
    }

    private void doPartTwo(){
        //Need to find out how many betting phases and what each phase does before continuing. Have wildcard extend this or make it a boolean/int field

    }
}
