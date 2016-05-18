package game;

import poker.Poker;
import poker.PokerFiveCard;
import poker.PokerHoldEm;
import poker.PokerWildCard;

import java.util.Scanner;
/**
 * @author Jeremy Halt
 */
public class Main {
	
	public static void main(String[] args){
        Poker game = createGame(Integer.parseInt(getChoice("What kind of poker will be played? (1: 5 card, 2: 5 card w/ wildcard, 3: Texas Hold-em")));
		assert game != null;
		game.playGame();
	}
 	/**
	 * @param prompt What the user will see and answer to
	 * @return The user's input
	 */
	public static String getChoice(String prompt){
		System.out.println(prompt);
		return(new Scanner(System.in).nextLine());
	}

	private static Poker createGame(int choice){
        switch(choice){
            case 1:
                return new PokerFiveCard();
            case 2:
                return new PokerWildCard();
            case 3:
                return new PokerHoldEm();
            default:
                return null;
        }
    }
}