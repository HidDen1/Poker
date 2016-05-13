package poker;

import card.Card;
import game.Player;

import java.util.ArrayList;
/**
 * The class containing the main poker
 */
public abstract class Poker {
	private ArrayList<Card> deck = new ArrayList<Card>();
	private Player users[];
	private int maxBet, game, turn;
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

    protected Player getUser(int i){ return users[i]; }

    protected int getUserAmount(){ return users.length; }

	protected int getTurn(){ return turn; }
}