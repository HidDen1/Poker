package poker;

import game.Card;

public class PokerWildCard extends PokerFiveCard{
    @Override
    public void playGame() {
        setRandomWildcard();
        super.playGame();
    }

    private void setRandomWildcard(){
        int i = (int)((13 - 1) * Math.random() + 1);
        for(int j = 0; j < getDeck().size(); j++){
            if(getDeck().get(j).getValue()[0] == i){
                getDeck().set(j, new Card());
            }
        }
    }
}
