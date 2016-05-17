package game;

import card.Card;
import poker.Poker;
import poker.PokerFiveCard;
import poker.PokerHoldEm;
import poker.PokerWildCard;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * @author Jeremy Halt
 */
public class Main {
	
	public static void main(String[] args){
        Poker game = createGame(Integer.parseInt(getChoice("What kind of poker will be played? (1: 5 card, 2: 5 card w/ wildcard, 3: Texas Hold-em")));
		game.playGame();
		//System.out.println(game.getUser(0).checkHand());
        //System.out.println(game.getUser(1).checkHand());
		//System.out.println(game.findWinner(game.getUser(0), game.getUser(1), 1).checkHand());
	}
 	/**
	 * @param prompt What the user will see and answer to
	 * @return The user's input
	 */
	public static String getChoice(String prompt){
		System.out.println(prompt);
		return(new Scanner(System.in).nextLine());
	}
	/**
	 * Prints the deck (used for debugging purposes)
	 */
	public static void printList(ArrayList<Card> list){
		for(int i = 0; i < list.size(); i++) {
			System.out.println("Card " + i + ": " + list.get(i).getName() + " " + list.get(i).getType() + list.get(i).getValue()[0]);
		}
	}

	public static Poker createGame(int choice){
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