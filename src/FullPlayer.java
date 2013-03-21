
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Tay Chit Chin, Lee Chun Kit, Chuah Teong Leong
 */
public class FullPlayer extends Player {

    /**
     * to contain cards which the opponent may have. is the set of cards minus
     * cards which this computer player has, minus the cards that have been
     * played.
     */
    private int playerNumber;
//    private Hand opponentsHand;
//    private Trick currentTrick = Controller.historyOfTricks.get(
//            Controller.historyOfTricks.size() - 1);
    /**
     * Class constructor.
     * constructs with an opponentshand full of cards
     * @param number player's number
     */
    public FullPlayer(int number) {
        playerNumber = number;
//        opponentsHand = new Hand();
//        opponentsHand.addCard(Card.deck());
    }

    /**
     * when u add a card, it knows it's opponents dont have this card so removed from opponents hand
     * @param card
     */
    //@Override
    //protected void addCard(Card card) { 
    //    super.addCard(card);
//  //      opponentsHand.removeCard(card);
    //}

//    Agent is supposed to return the optimum legal trick here.
//    You have to edit this.
    /**
     * Returns an trick of Cards that is the agent's best attempt to win.
     * Chosen trick is garanteed to be legal moves (TODO player makes sure that trick chosen is legal but the game do not verify that, implement verification of trick)
     * The chosen trick is removed from the player's hand 
     * 
     * @return trick of Cards forming a wellformed trick.
     */
    @Override
    @SuppressWarnings({"empty-statement", "fallthrough"})
    protected Trick think() {

        Trick trickOut;
        Hand play0Hand = new Hand(Controller.player[0].myHand);
        Hand play1Hand = new Hand(Controller.player[1].myHand);
        Hand play2Hand = new Hand(Controller.player[2].myHand);
        Hand play3Hand = new Hand(Controller.player[3].myHand);
        trickOut = fullTreeTraversal(play0Hand, play1Hand, play2Hand, play3Hand,
                Controller.getCurrentTrick(), this.playerNumber, 0, this.playerNumber, 0, new Trick());

        removeCards(trickOut);
        System.out.println(myHand + " size: " + myHand.listOfCards.size());
        return trickOut;//stub
    }
    
    //recursion. currPlayer is the current player. firstPlayer is the player we seek to make win.
    /**
     * Initiate a full tree search to currDepth and returns Trick/path, 
     * when followed, that have the most winning branches 
     * 
     * @param p0Hand
     * @param p1Hand
     * @param p2Hand
     * @param p3Hand
     * @param currTrick
     * @param currPlayer
     * @param currDepth
     * @param firstPlayer
     * @param numPasses
     * @param trickToPlay
     * @return Best Trick based on most possibility of wins assuming every trick by the 
     */
    private static Trick fullTreeTraversal(Hand p0Hand, Hand p1Hand, Hand p2Hand, Hand p3Hand,
    		Trick currTrick, int currPlayer, int currDepth, int firstPlayer, int numPasses, Trick trickToPlay) {

    	Hand currHand;
    	switch (currPlayer) {
    		case 0:
    			currHand = p0Hand;
    			break;
    		case 1:
    			currHand = p1Hand;
    			break;
    		case 2:
    			currHand = p2Hand;
    			break;
    		case 3:
    			currHand = p3Hand;
    			break;
    		default:
    			currHand = new Hand();
    			break;
    	}
    	
    	ArrayList<Trick> myValidTricks = currHand.getLegalTricks( currTrick );
    	
    	if ( Hand.lastTrickLeft( myValidTricks ) )
    		return myValidTricks.get(0);

    	ArrayList<Integer> numOfWinArray = new ArrayList<Integer>();
    	for (Trick trick : myValidTricks) {
    		if (trick.trickType() == Trick.TrickType.PASS) {
    			numPasses++;
    		} else {
    			numPasses = 0;
    		}
    		Hand player0Hand = new Hand(p0Hand);
    		Hand player1Hand = new Hand(p1Hand);
    		Hand player2Hand = new Hand(p2Hand);
    		Hand player3Hand = new Hand(p3Hand);

    		switch (currPlayer) {
    			case 0:
    				player0Hand.removeCard(trick.listOfCards);
    				break;
    			case 1:
    				player1Hand.removeCard(trick.listOfCards);
    				break;
    			case 2:
    				player2Hand.removeCard(trick.listOfCards);
    				break;
    			case 3:
    				player3Hand.removeCard(trick.listOfCards);
    				break;
    			default:
    				break;
    		}
    		
    		if (currDepth == 0) {
    			trickToPlay = trick;
    		}
    		
    		numOfWinArray.add(numOfWinCountFullTreeTraversal(player0Hand, player1Hand, player2Hand, player3Hand,
    				trick, (++currPlayer) % 4, currDepth++, firstPlayer, numPasses, trickToPlay, 0));
    	}
    	int maxWins = Collections.max(numOfWinArray);
    	int maxWinsTrickIndex = numOfWinArray.indexOf(maxWins);
    	System.out.println("max number wins (depth 4 search) is: " + maxWins);
    	return myValidTricks.get(maxWinsTrickIndex);//stub
    }

    //recursion. currPlayer is the current player. firstPlayer is the player we seek to make win.
    private static int numOfWinCountFullTreeTraversal(Hand p0Hand, Hand p1Hand, Hand p2Hand, Hand p3Hand,
    		Trick currTrick, int currPlayer, int currDepth, int firstPlayer, int numPasses, Trick trickToPlay,
    		int numOfWins)
    {

    	final int MAXDEPTH = 4;//stub

    	if (firstPlayer == 0 && p0Hand.size() == 0 || 
    		firstPlayer == 1 && p1Hand.size() == 0 || 
    		firstPlayer == 2 && p2Hand.size() == 0 || 
    		firstPlayer == 3 && p3Hand.size() == 0)
    	{
    		numOfWins++;
    		return numOfWins;
    	}
    	
    	if (currDepth >= MAXDEPTH || numPasses >= 3) {
    		return numOfWins;
    	}

    	Hand currHand;
    	switch (currPlayer) {
    		case 0:
    			currHand = p0Hand;
    			break;
    		case 1:
    			currHand = p1Hand;
    			break;
    		case 2:
    			currHand = p2Hand;
    			break;
    		case 3:
    			currHand = p3Hand;
    			break;
    		default:
    			currHand = new Hand();
    			break;
    	}
    	
    	ArrayList<Trick> myValidTricks = currHand.getLegalTricks( currTrick );
    	
    	for (Trick trick : myValidTricks) {
    		if (trick.trickType() == Trick.TrickType.PASS) {
    			numPasses++;
    		} else {
    			numPasses = 0;
    		}
    		Hand player0Hand = new Hand(p0Hand);
    		Hand player1Hand = new Hand(p1Hand);
    		Hand player2Hand = new Hand(p2Hand);
    		Hand player3Hand = new Hand(p3Hand);

    		switch (currPlayer) {
    			case 0:
    				player0Hand.removeCard(trick.listOfCards);
    				break;
    			case 1:
    				player1Hand.removeCard(trick.listOfCards);
    				break;
    			case 2:
    				player2Hand.removeCard(trick.listOfCards);
    				break;
    			case 3:
    				player3Hand.removeCard(trick.listOfCards);
    				break;
    			default:
    				break;
    		}
    		
    		if (currDepth == 0) {
    			trickToPlay = trick;
    		}
    		
    		numOfWins = numOfWinCountFullTreeTraversal(player0Hand, player1Hand, player2Hand, player3Hand, 
    						trick, (++currPlayer) % 4, currDepth++, firstPlayer, numPasses, trickToPlay, numOfWins);
    	}
    	return numOfWins; //stub
    }

    // not used now
/*    protected ArrayList<Card> searchLegalMove() {

        ArrayList<Card> move = new ArrayList<Card>();
        Card threeDiamond = new Card('3', 'D');
        if (hasThisCard(threeDiamond)) {
            // form a trick with 3D
            move.add(threeDiamond);
        } else {
            Trick currTrick = Controller.getCurrentTrick();
            // search for a trick that is higher than currTrick
            // search from myHand the first card that is valid and well-formed

            ArrayList<Card> nextMove = myHand.listOfCards;
            Trick nextTrick;
            int i = 0;
            do {
                System.out.println("I have " + nextMove);
                nextTrick = new Trick(nextMove.get(i));
                if (!(nextTrick.trickType() == Trick.TrickType.INVALID) && Controller.isWellFormed(nextTrick)) {
                    move.add(nextMove.get(i));
                }
                i++;
            } while (nextTrick.trickType() == Trick.TrickType.INVALID || !Controller.isWellFormed(nextTrick) ||
                    i == myHand.size() - 1);


        }
        //if (getCurrentTrick().size() == 5) {

        //  }

        return move;
    }*/
}
