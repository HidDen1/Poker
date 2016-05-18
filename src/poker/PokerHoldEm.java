package poker;

import game.Card;

import java.util.ArrayList;

public class PokerHoldEm extends Poker {
    private ArrayList<Card> board = new ArrayList<>();
    @Override
    public void playGame() {
        super.playGame();
        do{
            setBet(0);
            shuffle();
            dealToPlayers(2);
            dealToBoard(1);
            betPhase();
            if(isAllFolded() == -1) {
                changeTurn(0);
                dealToBoard(2);
                betPhase();
                if (isAllFolded() == -1) {
                    changeTurn(0);
                    dealToBoard(2);
                    betPhase();
                }
            }
            if(isAllFolded() != -1)
                dealOutWinnings(getUser(isAllFolded()));
            else {
                copyBoardToPlayers();
                dealOutWinnings(findWinner(getUser(0), getUser(1), 1));
                unCopyBoardToPlayers();
            }
            for(int i = 0; i < getUserAmount(); i++){
                getDeck().addAll(0, getUser(i).getHand());
                getUser(i).killHand();
                if(getUser(i).isBankrupt())
                    getUser(i).setOut(true);
                else {
                    if (getUser(i).isAllin())
                        getUser(i).unAllIn();
                    getUser(i).setOut(false);
                }
            }
            getDeck().addAll(0, removeFromBoard());
        } while(isVictory() == -1);
        System.out.println("The user with " + getUser(isVictory()).getCash() + " has been named victorious!");
    }

    public void betPhase(){
        System.out.println("Current Cards on the board are: " + board.toString().substring(1, board.toString().length() - 1));
        super.betPhase();
    }

    private void dealToBoard(int num){
        ArrayList<Card> toDeal = new ArrayList<>();
        for(int j = 0; j < num; j++){
                toDeal.add(getDeck().remove(getDeck().size() - 1));
        }
        board.addAll(toDeal);
    }

    private void copyBoardToPlayers(){
        for(int i = 0; i < getUserAmount(); i++){
            getUser(i).dealToHoldEm(board);
        }
    }
    private void unCopyBoardToPlayers(){
        for(int i = 0; i < getUserAmount(); i++){
            getUser(i).getHand().remove(2);
            getUser(i).getHand().remove(3);
            getUser(i).getHand().remove(4);
        }
    }

    private ArrayList<Card> removeFromBoard(){
        ArrayList<Card> bye = new ArrayList<>();
        bye.addAll(board);
        board.clear();
        return bye;
    }
}
