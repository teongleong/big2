
import java.util.ArrayList;

// was thinking whether to extend Player from Hand or not. Decision is not to
// as Player is not a type of Hand, rather Player has a Hand (and other info).
/**
 * A player has a hand.<!-- --> He also remembers the cards his opponents does 
 * not have based on which Cards he has and the Cards played on the table.
 * 
 * @author Tay Chit Chin, Lee Chun Kit, Chuah Teong Leong
 */
public abstract class Player {

    public static enum PokerHand {

        STRAIGHT, FLUSH, FULLHOUSE, FOUROFAKIND, STRAIGHTFLUSH, NONE
    }
    /**
     * The Hand of a Player.
     */
    protected Hand myHand;
    private boolean sorted = false;

    /**
     * Class constructor
     */
    public Player() {
        myHand = new Hand();
    }

    /**
     * Class constructor
     * @param card 
     */
    protected void addCard(Card card) {
        myHand.addCard(card);
        sorted = myHand.isSorted();
    }

    /**
     * The Cards to add to Player's Hand, not sorted.
     * 
     * @param cards 
     */
    protected void addCard(ArrayList<Card> cards) {
        myHand.addCard(cards);
        sorted = myHand.isSorted();
    }

    //was a bug
    protected void addCard(Trick trick) {
        for (Card specifiedCard : trick.listOfCards) {
            myHand.addCard(specifiedCard);
        }
    }

    /**
     * Subclasses should implement this method to return a legal trick; both 
     * Valid and WellFormed.<!-- --> Test WellFormness using Controller's method
     * isWellFormed().
     * 
     * @return ArrayList of Cards of a legal trick.
     */
    protected abstract Trick think();

    /**
     * Checks if the Player's Hand has this specified Card.<!-- --> Method is
     * intentionally set as protected to prevent other players from access
     * opponent's hand.
     * 
     * @param card the specified Card.
     * @return true if Player's Hand has this specified Card; false otherwise.
     */
    protected boolean hasThisCard(Card card) {
        return myHand.hasThisCard(card);
    }

    /**
     * is the player's hand sorted?
     * 
     * @return true if sorted; false otherwise.
     */
    protected boolean isSorted() {
        return sorted;
    }

    /**
     * remove these specified cards
     * 
     * @param cards the specified cards
     */
    protected void removeCards(ArrayList<Card> cards) {

        //remove the cards I'm passing out of my hand
        for (Card specifiedCard : cards) {
            myHand.removeCard(specifiedCard);
        }
    }

    /**
     * remove these specified cards in a trick
     * 
     * @param trick 
     */
    protected void removeCards(Trick trick) {

        //remove the cards I'm passing out of my hand
        for (Card specifiedCard : trick.listOfCards) {
            myHand.removeCard(specifiedCard);
        }
    }

    /**
     * The card to remove.<!-- --> If there exist more than of this card in the
     * hand, only one card is removed.
     * 
     * @param specifiedCard
     * @return 
     */
    protected boolean removeCard(Card specifiedCard) {
        return myHand.removeCard(specifiedCard);
    }

    /**
     * Sorts the Cards in a Player's Hand in the natural ordering according to
     * compareTo()
     * 
     * @see Card#compareTo(Card card)
     */
    protected void sort() {
        if (!myHand.isSorted()) {
            myHand.sort();
        }
    }

    /**
     * Returns string representation of a Player's Hand.
     *
     * @return string representation of a Player's Hand.
     */
    @Override
    public String toString() {
        return myHand.toString();
    }

    /**
     * for convenient printing.
     * 
     * @param o object to be printed
     */
    public static void sop(Object o) {
        System.out.println(o.toString());
    }
}
