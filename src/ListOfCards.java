
import java.util.Collections;
import java.util.ArrayList;

/**
 * A Hand consists of an ArrayList of Cards and methods for manipulation of the 
 * Cards in it.
 * 
 * @author Tay Chit Chin, Lee Chun Kit, Chuah Teong Leong
 */
public abstract class ListOfCards {

    /**
     * An ArrayList of Cards.
     */
    protected ArrayList<Card> listOfCards = new ArrayList<Card>();
    private boolean sorted = false;

    /**
     * Class constructor.
     */
    public ListOfCards() {
    //cannot do this or will get null values, just let the ArrayList
    //grow by itself
    //listOfCards.setSize(13);
    }

    /**
     * Class constructor.
     * @param listOfCards 
     */
    public ListOfCards(ArrayList<Card> listOfCards) {
        this.listOfCards.addAll(listOfCards);
    }

    /**
     * Class constructor.
     * @param card 
     */
    public ListOfCards(Card card) {
        this.listOfCards.add(card);
    }

    /**
     * Adds the card, does not sort.
     * 
     * @param card the card to add.
     */
    protected void addCard(Card card) {
        listOfCards.add(card);
        sorted = false;
    }

    /**
     * Adds the cards, does not sort.
     * 
     * @param cards 
     */
    protected void addCard(ArrayList<Card> cards) {
        listOfCards.addAll(cards);
        sorted = false;
    }

    /**
     * Checks if listOfCards contains specified card.
     * 
     * @param card the specified card.
     * @return true if the listOfCards contains the specified card; false otherwise.
     */
    protected boolean hasThisCard(Card card) {
        return listOfCards.contains(card);
    }

    /**
     * Checks if listOfCards is void of cards.
     * 
     * @return true if listOfCards has no cards; false otherwise.
     */
    protected boolean isEmpty() {
        return listOfCards.isEmpty();
    }

    /**
     * has the list of cards been sorted?
     * 
     * @return true if list has been sorted, false (may still be sorted) 
     * otherwise.
     */
    protected boolean isSorted() {
        return sorted;
    }

    /**
     * The card to remove.<!-- --> If there exist more than of this card in the
     * listOfCards, only one card is removed.
     * 
     * @param specifiedCard
     * @return true if a card existed and is removed; false otherwise.
     */
    protected boolean removeCard(Card specifiedCard) {
        return listOfCards.remove(specifiedCard);
    }

    /**
     * The cards to remove.<!-- --> After removal, there will be no cards common
     * with any of the cards in the specifiedCards list.
     * 
     * @param specifiedCards
     * @return true listofcards is changed; false otherwise.
     */
    protected boolean removeCard(ArrayList<Card> specifiedCards) {
        return listOfCards.removeAll(specifiedCards);
    }

    /**
     * Returns the number of cards a listOfCards has.
     * 
     * @return the number of cards a listOfCards has.
     */
    protected int size() {
        return listOfCards.size();
    }

    /**
     * Sorts the cards in their natural order, as ordered by compareTo().
     * 
     * @see Card#compareTo(Card card)
     */
    protected void sort() {
        Collections.sort(listOfCards);
        sorted = true;
    }

    /**
     * Returns the sorted string representation of a listOfCards.
     * 
     * @return the string representation of a listOfCards.
     */
    @Override
    public String toString() {
        /*if (!sorted) {
            sort();
        }*/
        return listOfCards.toString();
    }
}
