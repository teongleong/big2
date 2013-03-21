
import java.util.Collections;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Tay Chit Chin, Lee Chun Kit, Chuah Teong Leong
 */
public class HumanPlayer extends Player {

    /**
     * Returns an ArrayList of Cards forming a WellFormed trick
     * 
     * @return ArrayList of Cards forming a wellformed trick.
     */
    @Override
    protected Trick getLegalTrick() {

        //boolean isLegalTrick = false;

        ArrayList<Card> legalTrickOut = new ArrayList<Card>();
        Trick trick;
        boolean firstTime = true;
        do {
            if (!firstTime) {
                System.out.println("You have made and invalid input!");
            }
            System.out.println("Enter the trick in the format AS,2S,3D,5C,KH or enter pp for pass: ");
            legalTrickOut = getTrick();
            trick = new Trick(legalTrickOut);
            firstTime = false;
        } while (trick.trickType() == Trick.TrickType.INVALID || !Controller.isWellFormed(trick));
//        keep asking for the trick as long as it's of tricktype INVALID or not wellformed

//        remove the cards i've used
        removeCards(trick);

        return trick;//stub
    }

    /**
     * Gets a sorted trick from the human player from the System.in.
     * 
     * @return a sorted trick from the human player.
     */
    private static ArrayList<Card> getTrick() {
        ArrayList<Card> trickOut = new ArrayList<Card>();
        Scanner scanner = new Scanner(System.in);
        String input = "";
        Card.Rank rank = null;
        Card.Suit suit = null;

        String string = scanner.next();
        //strangely can't close this or will get exception
        //scanner.close();

        Scanner scanner2 = new Scanner(string);
        scanner2.useDelimiter(",");
        while (scanner2.hasNext()) {
            input = scanner2.next();
            char charRank = input.charAt(0);
            char charSuit = input.charAt(1);
            //if passing, return pass/empty trick
            if (charRank == 'p' && charSuit == 'p') {
                return trickOut;
            }
            for (Card.Rank rank2 : Card.Rank.values()) {
                if (charRank == rank2.representationOfRank()) {
                    rank = rank2;
                }
            }
            for (Card.Suit suit2 : Card.Suit.values()) {
                if (charSuit == suit2.representationOfSuit()) {
                    suit = suit2;
                }
            }
            trickOut.add(new Card(rank, suit));
        }
        scanner2.close();

        Collections.sort(trickOut);

        return trickOut;
    }
}
