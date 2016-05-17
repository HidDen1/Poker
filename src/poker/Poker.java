package poker;

import card.Card;
import game.Main;
import game.Player;

import java.util.ArrayList;
/**
 * The class containing the main poker
 */
public abstract class Poker {
	private ArrayList<Card> deck = new ArrayList<Card>();
	private Player users[];
	private int maxBet, game, turn, bet;
    private double pool;
	public Poker(){
		for(int i = 1; i < 14; i++){
			for(int j = 0; j < 4; j++){
				if(i == 1)
					deck.add(new Card(new int[]{i, 14}, Card.TYPES[j]));
				deck.add(new Card(new int[]{i}, Card.TYPES[j]));
			}
		}
	}

    protected void changeTurn(int change){ turn = change; }

	public abstract void playGame();
	/**
	 * Shuffles the Deck
	 */
	public void shuffle(){
		for(int i = 0; i < deck.size(); i++){
			int replace = (int)((deck.size() - 1) * Math.random() - 1);
			Card rep = deck.get(i);
			deck.remove(i);
			deck.add(replace, rep);
		}
	}
	/**
	 * Creates the players
	 * @param num The amount of players
	 */
	public void createPlayers(int num){
        users = new Player[num];
        for(int i = 0; i < num; i++)
            users[i] = new Player();
    }
	/**
	 * Sets the player's starting cash
	 * @param num The numerical value to set it to
	 */
	public void setTotal(int num){
		for(int i = 0; i < users.length; i++){
			users[i].setCash(num);
		}
	}
	/**
	 * Deals cards to the player
	 */
	public void dealToPlayers(int amt){
		ArrayList<Card> toDeal = new ArrayList<Card>();
		
		for(Player i : users){
			toDeal.clear();
			for(int j = 0; j < amt; j++){
				toDeal.add(deck.remove(deck.size() - 1));
			}
			i.dealTo(toDeal);
			i.showHand();
		}
	}

    protected void betPhase(int what){
        int raised = -1;
        if(users[getTurn()].getSkip()){
            changeTurn(getTurn() + 1);
            users[getTurn()].skip();
            betPhase(Integer.parseInt(Main.getChoice("Player " + getTurn() + "'s turn. 1: Call; 2: Raise; 3: Fold")));
        } else if(users[getTurn()].getOut()){
            changeTurn(getTurn() + 1);
            betPhase(Integer.parseInt(Main.getChoice("Player " + getTurn() + "'s turn. 1: Call; 2: Raise; 3: Fold")));
        }
        switch(what){
            case 1:
                getUser(getTurn()).call(bet);
                pool += bet;
                break;
            case 2:
                bet = getUser(getTurn()).raise(bet);
                raised = getTurn();
                pool += bet;
                break;
            case 3:
                deck.addAll(0, getUser(getTurn()).fold());
                users[getTurn()].killHand();
                break;
            case 4:
                int all = users[getTurn()].allIn(pool);
                if (all > bet){
                    raised = getTurn();
                    bet = all;
                }
                pool += all;
                break;
        }
        changeTurn(getTurn() + 1);
        if(raised != -1) {
            users[raised].setSkip();
            changeTurn(0);
        }
        if(getTurn() != getUserAmount())
            betPhase(Integer.parseInt(Main.getChoice("Player " + getTurn() + "'s turn. 1: Call; 2: Raise; 3: Fold")));
        //else
            //doPartOne(Integer.parseInt(Main.getChoice("Player " + getTurn() + "'s turn. 1: Call; 2: Raise; 3: Fold")));
    }

	public Player findWinner(Player win, Player lose, int i){
        String winHand = win.checkHand(), loseHand = lose.checkHand();
        if(winHand.substring(winHand.length() - 1, winHand.length()).compareTo(loseHand.substring(loseHand.length() - 1, loseHand.length())) < 0){
            if(i == users.length - 1)
                return win;
            return findWinner(win, users[++i], ++i);
        } else if(winHand.substring(winHand.length() - 1, winHand.length()).compareTo(loseHand.substring(loseHand.length() - 1, loseHand.length())) > 0){
            if(i == users.length - 1)
                return lose;
            return findWinner(lose, users[++i], ++i);
        } else {
            if(winHand.length() > 4){
                if(Integer.parseInt(winHand.substring(winHand.indexOf(".") + 1, winHand.lastIndexOf("."))) > Integer.parseInt(loseHand.substring(loseHand.indexOf(".") + 1, loseHand.lastIndexOf(".")))){
                    if(i == users.length - 1)
                        return win;
                    return findWinner(win, users[++i], ++i);
                } else if(Integer.parseInt(winHand.substring(winHand.indexOf(".") + 1, winHand.lastIndexOf("."))) < Integer.parseInt(loseHand.substring(loseHand.indexOf(".") + 1, loseHand.lastIndexOf(".")))){
                    if(i == users.length - 1)
                        return lose;
                    return findWinner(lose, users[++i], ++i);
                }
            }
            if(Integer.parseInt(winHand.substring(0, 2)) > Integer.parseInt(loseHand.substring(0, 2))){
                if(i == users.length - 1)
                    return win;
                return findWinner(win, users[++i], ++i);
            } else if(Integer.parseInt(winHand.substring(0, 2)) < Integer.parseInt(loseHand.substring(0, 2))){
                if(i == users.length - 1)
                    return lose;
                return findWinner(lose, users[++i], ++i);
            } else {
                if(i == users.length - 1)
                    return null;
                //WORSE CASE SCENARIO; I HAVE NO IDEA WHAT DO
                return findWinner(lose, users[++i], ++i);
            }
        }
    }
	/**
	 * @param bet The number to set the max bet to
	 */
	public void setMaxBet(int bet){ maxBet = bet; }
	/**
	 * When a player discards a card, it must go back in the deck
	 * @param b the card(s) to go back
	 */
	public void backToDeck(ArrayList<Card> b){ deck.addAll(0, b); }
	/**
	 * 1 = 5 card; 2 = 5 card w/ wildcard; 3 = Texas hold em;
	 * @param g the poker to be set
	 */
	public void setGame(int g){ game = g; }

	/**
	 * TO BE MADE PROTECTED; FOR DEBUG
	 */
	public ArrayList<Card> getDeck(){ return(deck); }
	//TODO MAKE PROTECTED
    public Player getUser(int i){ return users[i]; }

    protected int getUserAmount(){ return users.length; }

	protected int getTurn(){ return turn; }
}