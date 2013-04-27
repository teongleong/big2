
import java.util.Collections;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Tay Chit Chin, Lee Chun Kit, Chuah Teong Leong
 */
public class HumanPlayer extends Player {

	 ArrayList<Trick> choices;
    /**
     * to contain cards which the opponent may have. is the set of cards minus
     * cards which this computer player has, minus the cards that have been
     * played.
     */
    private int playerNumber;
//	    private Hand opponentsHand;
//	    private Trick currentTrick = Controller.historyOfTricks.get(
//	            Controller.historyOfTricks.size() - 1);
    /**
     * Class constructor.
     * constructs with an opponentshand full of cards
     * @param number player's number
     */
    public HumanPlayer(int number) {
        playerNumber = number;
//	        opponentsHand = new Hand();
//	        opponentsHand.addCard(Card.deck());
    }
	    
    /**
     * Returns an ArrayList of Cards forming a WellFormed trick
     * 
     * @return ArrayList of Cards forming a wellformed trick.
     */
    @Override
    protected Trick think() {

        boolean firstTime = true;
/*        do {
            if (!firstTime) {
                System.out.println("You have made an invalid input!");
            }
            //System.out.println("Enter the trick in the format AS,2S,3D,5C,KH or enter pp for pass: ");
            legalTrickOut = getTrick();
            //trick = new Trick(legalTrickOut);
            firstTime = false;
        } while (legalTrickOut.trickType() == Trick.TrickType.INVALID || !Controller.isWellFormed(legalTrickOut));
//        keep asking for the trick as long as it's of tricktype INVALID or not wellformed
*/
        Trick legalTrickOut = getTrick();
//        remove the cards i've used
        removeCards(legalTrickOut);

        return legalTrickOut;//stub
    }

    /**
     * Gets a sorted trick from the human player from the System.in.
     * 
     * @return a sorted trick from the human player.
     */
    private Trick getTrick() {
    	System.out.println("human player get trick");
        ArrayList<Card> trickOut = new ArrayList<Card>();
        Scanner scanner = new Scanner(System.in);
        String input = "";
        
        /*Card.Rank rank = null;
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

        return trickOut;*/
        Trick chosenTrick = null;
        while(chosenTrick == null) {
        	System.out.print("command> ");
        	chosenTrick = processCommand(scanner.nextLine());
        }
        return chosenTrick;
    }
    
    private Trick processCommand(String command) {
    	String [] breakdown = command.split(" ");

    	if( breakdown[0].equals("filter")) {
    		//System.out.println("went into filter");
    		if (breakdown[1].equals("fives") || breakdown[1].equals("five")) {
    			choices = this.myHand.getFiveTricks();
    		} else if (breakdown[1].equals("pairs") || breakdown[1].equals("pair")) {
    			choices = this.myHand.getPairTricks();
    		} else if (breakdown[1].equals("singles") || breakdown[1].equals("single")) {
    			choices = this.myHand.getSingleTricks();
    		} else if (breakdown[1].equals("straights") || breakdown[1].equals("straight")) {
    			choices = this.myHand.getStraightTricks();
    		} else if (breakdown[1].equals("flush")) {
    			choices = this.myHand.getFlushTricks();
    		} else if (breakdown[1].equals("house")) {
    			choices = this.myHand.getFullHouseTricks();
    		} else if (breakdown[1].equals("four")) {
    			choices = this.myHand.getFourOfAKindTricks();
    		}
    		System.out.print(breakdown[1]);
    		System.out.println("Choice "+choices);
    	} else if ( isInteger( breakdown[0] ) ) { // choosing what to play from choice
    		int index = Integer.parseInt(breakdown[0]);
    		System.out.println("numeric "+choices);
    		if (choices == null ) {
    			System.out.println("filter first");
    		} else if ( index >= choices.size() ) {
    			System.out.println(choices);
    			System.out.println("choose within the indices of choice");
    		} else {
    			Trick chosenTrick = choices.get(index);
    			System.out.println("playing "+chosenTrick);
    			choices = null;
    			return chosenTrick;
    		}
    	//helper functions to visualize and think
    	} else if( breakdown[0].equals("hand")) {
    		myHand.sort();
    		System.out.println(myHand);
    	} else if( breakdown[0].equals("suit")) {
    		System.out.println(myHand.sortBySuit());
    	} else if( breakdown[0].equals("legal")) {
    		ArrayList<Trick> legal_tricks = myHand.getLegalTricks(Controller.getCurrentTrick(), myHand.getOrganizedTricks());
    		choices = legal_tricks;
    		if (choices.size() == 1) { // auto pass if that is the only legal move
    			System.out.println("Can only pass");
    			return new Trick();
    		} else {
    			System.out.println("legal "+legal_tricks);
    		}
    	} else if ( breakdown[0].equals("pass") ) {
    		return new Trick();
    	} else if ( breakdown[0].equals("extras")) {
    		System.out.println("extras "+ Hand.filterTrickType( myHand.getOrganizedTricks(), Trick.TrickType.SINGLE) );
    	} else if ( command.equals("countfive")) {
    		System.out.println("count five "+ this.myHand.getFiveTricks().size() );
    	} else if ( command.equals("counttwo")) {
    		System.out.println("count five "+ this.myHand.getPairTricks().size() );
    	} else if ( command.equals("summary")) {
    		System.out.println("Summary");
    		System.out.println("No of flush :"+this.myHand.getFlushTricks().size());
    		System.out.println("No of fours :"+this.myHand.getFourOfAKindTricks().size());
    		System.out.println("No of straight flush :"+this.myHand.getStraightFlushTricks().size());
    	}
    	else {
    		System.out.println("invalid command");
    	}
    	return null;
    }
    
    public static boolean isInteger( String input )  
    {  
       try  
       {  
          Integer.parseInt( input );  
          return true;  
       }  
       catch( Exception e)  
       {  
          return false;  
       }  
    }
}
