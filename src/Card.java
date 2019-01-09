public class Card {
    // Fields
    private String rank;
    private String suit;
    private int value;

    /**
     * Main Constructor that builds a Card based on given parameters.
     * @param rank String thar represents the rank of the Card.
     * @param suit String thar represents the suit of the Card.
     * @param value Integer that represents the value of the Card. (*Usually the rank but in Integer form*)
     */
    public Card (String rank, String suit, int value) {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
    }

    // Getters & Setters
    public String getRank() { return rank; }
    public void setRank(String rank) { this.rank = rank; }

    public String getSuit() { return suit; }
    public void setSuit(String suit) { this.suit = suit; }

    public int getPointValue() { return value; }
    public void setPointValue(int pointValue) { this.value = pointValue; }

    /**
     * Checks if a Cards value is equal to another Cards value.
     * @param other Any other Card.
     * @return True if both values are equal. Otherwise false.
     */
    public boolean equals(Card other) {
        if (this.value == other.getPointValue()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Generates a readable format for each Card.
     * @return String that describes the card. (" 'Rank' - 'Suit' - Value ")
     */
    @Override
    public String toString() { return this.rank + "-" + this.suit + "-" + this.value; }
}