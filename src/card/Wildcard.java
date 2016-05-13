package card;

public class Wildcard extends Card {
	/**
	 * Just to appease java; not actually used
	 * @param val //
	 * @param type //
	 */
	public Wildcard(int[] val, String type) {
		super(val, type);
	}
	/**
	 * Wildcards only get assigned a value when used, not received
	 */
	public Wildcard(){
		
	}
	/**
	 * Assigns the wanted values to the card
	 * @param val The card's value, also sets its name
	 * @param type The card's house
	 */
	public void makeCard(int[] val, String type){
		setValue(val);
		setType(type);
	}
}