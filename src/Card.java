
import java.util.ArrayList;

/**
 * A Card consists of its Rank and Suit which cannot be changed.
 * @author Tay Chit Chin, Lee Chun Kit, Chuah Teong Leong
 */
public class Card implements Comparable<Card> {

    /**
     * Enumeration of rank of a card.
     * Each Rank contains a value for comparison and a char representation.
     */
    //by making enum static, we can use == to compare enums easily instead of 
    //having to use equals()
    public static enum Rank {

        /**
         * Enum of three
         */
        THREE(3, '3'), /**
         * Enum of four
         */
        FOUR(4, '4'), /**
         * Enum of five
         */
        FIVE(5, '5'), /**
         * Enum of six
         */
        SIX(6, '6'), /**
         * Enum of seven
         */
        SEVEN(7, '7'),
        /**
         * Enum of eight
         */
        EIGHT(8, '8'), /**
         * Enum of nine
         */
        NINE(9, '9'), /**
         * Enum of ten
         */
        TEN(10, 'T'), /**
         * Enum of jack
         */
        JACK(11, 'J'), /**
         * Enum of queen
         */
        QUEEN(12, 'Q'),
        /**
         * Enum of king
         */
        KING(13, 'K'), /**
         * Enum of ace
         */
        ACE(14, 'A'), /**
         * Enum of two
         */
        TWO(15, '2');
        /**
         * Value a Rank represents for comparison purposes.
         */
        private int value;
        /**
         * String representation of a Rank.
         */
        private char representationOfRank; //representation of rank

        /**
         * Class constructor.
         * 
         * @param value value a Rank represents for comparison purposes.
         * @param representationOfRank string representation of a Rank.
         */
        private Rank(int value, char representationOfRank) {
            this.value = value;
            this.representationOfRank = representationOfRank;
        }

        /**
         * Returns char representation of a Rank.
         * 
         * @return char representation of a Rank.
         */
        public char representationOfRank() {
            return representationOfRank;
        }

        /**
         * Returns value of a Rank for comparison purposes.<!-- --> Values are
         * consistent with order in which they are declared.
         * 
         * @return value of a Rank for comparison purposes.
         */
        public int value() {
            return value;
        }
    }

    /**
     * Enumeration of suit of a card.
     * Each Suit contains a value for comparison and a char representation.
     */
    public static enum Suit {

        /**
         * Enum of diamonds
         */
        DIAMONDS(0, 'D'), /**
         * Enum of clubs
         */
        CLUBS(1, 'C'), /**
         * Enum of hearts
         */
        HEARTS(2, 'H'), /**
         * Enum of spades
         */
        SPADES(3, 'S');
        /**
         * Value a Suit represents for comparison purposes.
         */
        private int value;
        /**
         * String representation of a Suit.
         */
        private char representationOfSuit;

        /**
         * Class constructor.
         * 
         * @param value value a Suit represents for comparison purposes.
         * @param representationOfSuit string representation of a Suit.
         */
        private Suit(int value, char representationOfSuit) {
            this.value = value;
            this.representationOfSuit = representationOfSuit;
        }

        /**
         * Returns char representation of a Suit.
         * 
         * @return char representation of a Suit.
         */
        public char representationOfSuit() {
            return representationOfSuit;
        }

        /**
         * Returns value of a Suit for comparison purposes.<!-- --> Values are
         * consistent with order in which they are declared.
         * 
         * @return value of a Suit for comparison purposes.
         */
        public int value() {
            return value;
        }
    }
    /**
     * Rank of a Card.
     */
    private final Rank rank;
    /**
     * Suit of a Card.
     */
    private final Suit suit;

    /**
     * Class constructor specifying the rank and suit using enum type.
     * 
     * @param rank enum of Rank.
     * @param suit enum of Suit.
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Class constructor specifying the rank and suit using char type.
     * 
     * @param rank rank in char.
     * @param suit suit in char.
     */
    public Card(char rank, char suit) {
        Rank theRank = null;
        Suit theSuit = null;
        for (Card.Rank rank2 : Card.Rank.values()) {
            if (rank == rank2.representationOfRank()) {
                theRank = rank2;
                break;
            }
        }
        for (Card.Suit suit2 : Card.Suit.values()) {
            if (suit == suit2.representationOfSuit()) {
                theSuit = suit2;
                break;
            }
        }
        this.rank = theRank;
        this.suit = theSuit;
    }

    /**
     * Compares this card with the specified card for order. Returns a
     * negative integer, zero, or a positive integer as this card is less
     * than, equal to, or greater than the specified card. Cards are ordered by
     * value, followed by suit, as per Big Two rules (2 is larger than A). Has natural ordering and
     * consistent with equals.
     * 
     * @param card the card to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     */
    public final int compareTo(Card card) {
        //Card other = (Card) card;
        //Card self = this;
        return (this.rank().value() * 4 + this.suit().value()) - (card.rank().value() * 4 + card.suit().value());
    }

    /**
     * ArrayList to be populated with all 52 cards during construction of deck.
     * 
     * @return ArrayList of the Cards in a Deck.
     */
    public static ArrayList<Card> deck() {
        ArrayList<Card> deck = new ArrayList<Card>();
        for (Card.Rank rank : Card.Rank.values()) {
            for (Card.Suit suit : Card.Suit.values()) {
                deck.add(new Card(rank, suit));
            }
        }
        return deck;
    }

    /**
     * Compares the specified object with this card for equality. 
     * 
     * @param card an object.
     * @return true if the object is a card and the cards have the same rank 
     * and suit; false otherwise.
     */
    @Override
    public boolean equals(Object card) {
        if (card instanceof Card && this.compareTo((Card) card) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the hashcode of this card.
     * 
     * @return int hashcode of this card.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.rank != null ? this.rank.value : 0);
        hash = 29 * hash + (this.suit != null ? this.suit.value : 0);
        return hash;
    }

    /**
     * Returns the rank of a card.
     * 
     * @return the rank of a card.
     * @see Rank
     */
    public Rank rank() {
        return rank;
    }

    /**
     * Returns the suit of a card.
     * 
     * @return the suit of a card.
     * @see Suit
     */
    public Suit suit() {
        return suit;
    }

    /**
     * Returns the condensed string representation of a Card.<!-- -->  e.g.<!-- -->  3D, AS.
     * 
     * @return the card in condensed string representation.
     */
    @Override
    public String toString() {
        return rank.representationOfRank + "" + suit.representationOfSuit;
    }

    /**
     * Returns the full string representation of a Card.<!-- --> e.g.<!-- -->  THREE of DIAMONDS, 
     * ACE of SPADES.
     * 
     * @return the card in full string representation.
     */
    public String toStringLong() {
        return rank + " of " + suit;
    }
}
