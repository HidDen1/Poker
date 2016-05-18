package poker;

import game.Card;
import game.HandChecker;
import game.Main;
import game.Player;

import java.util.ArrayList;
/**
 * The class containing the main poker
 */
public abstract class Poker {
	private ArrayList<Card> deck = new ArrayList<>();
	private Player users[];
	private int maxBet, turn;
    private double pool, bet;
	Poker(){
		for(int i = 1; i < 14; i++){
			for(int j = 0; j < 4; j++){
				if(i == 1)
					deck.add(new Card(new int[]{i, 14}, Card.TYPES[j]));
                else
				    deck.add(new Card(new int[]{i}, Card.TYPES[j]));
			}
		}
	}

    int isVictory(){
        int got = 0, count = 0;
        for(Player i : users){
            if(!i.isBankrupt()) {
                got = count;
                count++;
            }
        }
        if(count == 1)
            return got;
        else
            return -1;
    }

    int isAllFolded(){
        int got = 0, count = 0;
        for(Player i : users){
            if(!i.getOut()){
                got = count;
                count++;
            }
        }
        if(count == 1)
            return got;
        else
            return -1;
    }

    void changeTurn(int change){ turn = change; }

	public void playGame(){
        createPlayers(Integer.parseInt(Main.getChoice("How many players will be in this poker? (up to 5)")));
        setMaxBet(Integer.parseInt(Main.getChoice("What will be the max bet?")));
        setCash(Double.parseDouble(Main.getChoice("What should all player's starting cash be?")));
    }
	/**
	 * Shuffles the Deck
	 */
    void shuffle(){
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
    private void createPlayers(int num){
        users = new Player[num];
        for(int i = 0; i < num; i++)
            users[i] = new Player(i);
    }
	/**
	 * Deals cards to the player
	 */
    void dealToPlayers(int amt){
		ArrayList<Card> toDeal = new ArrayList<>();
		
		for(int i = 0; i < users.length; i++){
			toDeal.clear();
			for(int j = 0; j < amt; j++){
				toDeal.add(deck.remove(deck.size() - 1));
			}
			users[i].dealTo(toDeal);
            int p = new HandChecker(toDeal).hasWildCard();
            while(p != -1){
                toDeal.get(p).setName(Main.getChoice("Player " + i +". You have a wildcard! Give it a new face name(aka '2' or 'King') Your current hand looks like this " + toDeal.toString().substring(1, toDeal.toString().length() - 1)));
                toDeal.get(p).setType(Main.getChoice("Give this card a suit(Spades, Clubs...)"));
                p = new HandChecker(toDeal).hasWildCard();
            }
		}
	}

	void dealOutWinnings(Player winner){
        if(winner == null){
            System.out.println("No one won. Pot is split");
            users[0].setCash(users[0].getCash() + (pool / 2));
            users[1].setCash(users[1].getCash() + (pool / 2));
            pool = 0;
        } else {
            System.out.print("Player " + winner.getPlacement() + " was the winner and won ");
            if (winner.getMaxEarn() != 0) {
                System.out.println(winner.getMaxEarn());
                winner.setCash(winner.getCash() + winner.getMaxEarn());
                pool = 0;
            } else {
                System.out.println(pool);
                winner.setCash(winner.getCash() + pool);
                pool = 0;
            }
        }
	}

    protected void betPhase(){
        if(users[getTurn()].getSkip()){
            users[getTurn()].skip();
            changeTurn(getTurn() + 1);
        } else if(users[getTurn()].getOut())
            changeTurn(getTurn() + 1);
        else if(users[getTurn()].isAllin() || users[getTurn()].getCash() == 0)
            changeTurn(getTurn() + 1);
        if(getTurn() != getUserAmount() && isAllFolded() == -1) {
            int raised = -1, what = Integer.parseInt(Main.getChoice("Player " + getTurn() + "'s turn. You have " + users[getTurn()].getCash() + ". Your cards are: \n" + users[getTurn()].getHand().toString().substring(1, users[getTurn()].getHand().toString().length() - 1) + "\nThe current bet is " + bet + "\n1: Call; 2: Raise; 3: Fold, 4: All in"));
            switch (what) {
                case 1:
                    double mun = getUser(getTurn()).call(bet);
                    pool += mun;
                    break;
                case 2:
                    bet = getUser(getTurn()).raise(bet, maxBet);
                    raised = getTurn();
                    pool += bet;
                    break;
                case 3:
                    deck.addAll(0, getUser(getTurn()).fold());
                    users[getTurn()].killHand();
                    break;
                case 4:
                    double all = users[getTurn()].allIn(pool);
                    if (all > bet) {
                        raised = getTurn();
                        bet = all;
                    }
                    pool += all;
                    break;
            }
            changeTurn(getTurn() + 1);
            if (raised != -1) {
                users[raised].setSkip();
                changeTurn(0);
            }
            if (getTurn() != getUserAmount())
                betPhase();
            //else
            //doPartOne(Integer.parseInt(Main.getChoice("Player " + getTurn() + "'s turn. 1: Call; 2: Raise; 3: Fold")));
            changeTurn(0);
        }
        changeTurn(0);
    }

	Player findWinner(Player win, Player lose, int i){
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
    private void setMaxBet(int bet){ maxBet = bet; }

    private void setCash(double c){
        for(Player i : users){
            i.setCash(c);
        }
    }

	ArrayList<Card> getDeck(){ return(deck); }
    Player getUser(int i){ return users[i]; }

    int getUserAmount(){ return users.length; }

	int getTurn(){ return turn; }

    void setBet(double bet){ this.bet = bet; }
}