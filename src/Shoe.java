

/*
 * a shoe is a box that can contain many decks of cards. you can shuffle the 
 * cards in a shoe and remove cards.
 */
import java.util.ArrayList;
import java.util.Collections;

/**
 * A Shoe is a box that contains Cards and has methods to shuffle and draw.
 * 
 * @author Tay Chit Chin, Lee Chun Kit, Chuah Teong Leong
 */
public class Shoe {

    private ArrayList<Card> shoe;

    /**
     * Class constructor; inserts one deck.
     * 
     * @throws java.lang.Exception
     */
    public Shoe() throws Exception {
        this(1);
    }

    /**
     * Class constructor; inserts specified number of Decks.
     * 
     * @param number
     * @throws java.lang.Exception 
     */
    public Shoe(int number) throws Exception {

        //        Deck.java deprecated         
        //        Deck deck = new Deck();
        shoe = new ArrayList<Card>();
        if (number < 0) {
            throw new Exception("Shoe cannot have negative number of decks!");
        } else if (number == 0) {
            shoe = null;
        } else {
            for (int i = number; i >= 1; i--) {
                shoe.addAll(Card.deck());
            }
        }
    }

    /**
     * Returns the first Card in the Shoe and remove it.
     * 
     * @return the first Card in the Shoe and remove it.
     */
    public Card drawCard() {
        return shoe.remove(0);
    }

    /**
     * Checks if Shoe has Card.
     * 
     * @return true if Shoe has Card; false otherwise.
     */
    public boolean hasCard() {
        return !shoe.isEmpty();
    }

    /**
     * Shuffles the Cards in the Shoe.
     */
    public void shuffle() {
        Collections.shuffle(shoe);
    }

    /**
     * Returns the string representation of a Deck.
     * 
     * @return the string representation of a Deck.
     */
    @Override
    public String toString() {
        return shoe.toString();
    }
}
