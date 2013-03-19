
import java.util.ArrayList;

/**
 * A Hand consists of an ArrayList of Cards and methods for manipulation of the 
 * Cards in it.
 * 
 * @author Tay Chit Chin, Lee Chun Kit, Chuah Teong Leong
 */
public class Hand extends ListOfCards {

    /**
     * Class constructor
     */
    public Hand() {
        super();
    }

    /**
     * Class constructor
     * @param listOfCards 
     */
    public Hand(ArrayList<Card> listOfCards) {
        super(listOfCards);
    }
    
    /**
     * copy constructor
     * @param hand
     */
    public Hand(Hand hand){
        this(hand.listOfCards);        
    }
}
