package game;
/**
 * A card to be used as part of a deck
 */
public class Card {
	private int[] value = new int[2];
	private static final String NAMES[] = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
	public static final String TYPES[] = {"Spades", "Clubs", "Hearts", "Diamonds"};
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
        name = "Wildcard";
        type = "Wildtype";
	}
	/**
	 * @return The card's face name
	 */
    String getName(){
		return(name);
	}
	/**
	 * @return The house of the card
	 */
    String getType(){
		return(type);
	}
	/**
	 * @return The card's point value
	 */
	public int[] getValue(){
		return(value);
	}
	
	public void setName(String name){
		this.name = name;
		determineValue(name);
	}

	private void determineValue(String name){
        for (int i1 = 0, namesLength = NAMES.length; i1 < namesLength; i1++) {
            String i = NAMES[i1];
            if (i.equalsIgnoreCase(name))
                value[0] = i1 + 1;
        }
	}
	
	public void setType(String type){
		this.type = type.substring(0, 1).toUpperCase() + type.substring(1);
	}

	public String toString(){
		return name + " of " + type;
	}

}