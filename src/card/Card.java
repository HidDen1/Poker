package card;
/**
 * A card to be used as part of a deck
 */
public class Card {
	private int[] value;
	public static final String NAMES[] = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
	public static final String TYPES[] = {"S", "C", "H", "D"};
	private String type, name;
	/**
	 * @param val The card's point worth. Also translates into its name.
	 * @param type The house the card is from (like spades)
	 */
	public Card(int[] val, String type){
		value = val;
		this.type = type;
		name = NAMES[val[0] -1];
	}
	
	public Card(){
		
	}
	/**
	 * @return The card's face name
	 */
	public String getName(){
		return(name);
	}
	/**
	 * @return The house of the card
	 */
	public String getType(){
		return(type);
	}
	/**
	 * @return The card's point value
	 */
	public int[] getValue(){
		return(value);
	}
	
	protected void setValue(int[] val){
		value = val;
		name = NAMES[val[0] - 1];
	}
	
	protected void setType(String type){
		this.type = type;
	}
}