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
	private int maxBet, game, turn;
    private double pool, bet;
	public Poker(){
		for(int i = 1; i < 14; i++){
			for(int j = 0; j < 4; j++){
				if(i == 1)
					deck.add(new Card(new int[]{i, 14}, Card.TYPES[j]));
				deck.add(new Card(new int[]{i}, Card.TYPES[j]));
			}
		}
	}

    public int isVictory(){
        int got = 0, count = 0;
        for(Player i : users){
            if(!i.isBankrupt()) {
                got = count;
                count++;
            }
        }
        if(count == 1){
            return got;
        } else
            return -1;
    }

    protected void changeTurn(int change){ turn = change; }

	public void playGame(){
        createPlayers(Integer.parseInt(Main.getChoice("How many players will be in this poker? (up to 5)")));
        setMaxBet(Integer.parseInt(Main.getChoice("What will be the max bet?")));
        setCash(Double.parseDouble(Main.getChoice("What should all player's starting cash be?")));
    }
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
		}
	}

	public void dealOutWinnings(Player winner){
		if(winner.getMaxEarn() != 0){
            winner.setCash(winner.getCash() + winner.getMaxEarn());
            pool = 0;
        } else {
            winner.setCash(winner.getCash() + pool);
            pool = 0;
        }
	}

    protected void betPhase(){
        if(users[getTurn()].getSkip()){
            changeTurn(getTurn() + 1);
            users[getTurn()].skip();
        } else if(users[getTurn()].getOut()){
            changeTurn(getTurn() + 1);
        }
        int raised = -1, what = Integer.parseInt(Main.getChoice("Player " + getTurn() + "'s turn. You have " + users[getTurn()].getCash() + ". Your cards are: \n" + users[getTurn()].getHand().toString().substring(1, users[getTurn()].getHand().toString().length() -1) + "\nThe current bet is " + bet + "\n1: Call; 2: Raise; 3: Fold, 4: All in"));
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
                double all = users[getTurn()].allIn(pool);
                if (all > bet){
                    raised = getTurn();
                    bet = all;
                }
                pool += all;
                break;
        }
        System.out.println(getTurn() + "|" + getUserAmount());
        changeTurn(getTurn() + 1);
        System.out.println(getTurn() + "|" + getUserAmount());
        if(raised != -1) {
            users[raised].setSkip();
            changeTurn(0);
        }
        if(getTurn() != getUserAmount())
            betPhase();
        //else
            //doPartOne(Integer.parseInt(Main.getChoice("Player " + getTurn() + "'s turn. 1: Call; 2: Raise; 3: Fold")));
        changeTurn(0);
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

    public void setCash(double c){
        for(Player i : users){
            i.setCash(c);
        }
    }

	/**
	 * TO BE MADE PROTECTED; FOR DEBUG
	 */
	public ArrayList<Card> getDeck(){ return(deck); }
	//TODO MAKE PROTECTED
    public Player getUser(int i){ return users[i]; }

    protected int getUserAmount(){ return users.length; }

	protected int getTurn(){ return turn; }
}