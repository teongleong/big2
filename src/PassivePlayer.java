
import java.util.ArrayList;

public class PassivePlayer extends Player {
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
    public PassivePlayer(int number) {
        playerNumber = number;
//        opponentsHand = new Hand();
//        opponentsHand.addCard(Card.deck());
    }
    
    public Trick think() {
    	//System.out.println("passive player thinking "+myHand);
    	ArrayList<Trick> organziedTricks = myHand.getOrganizedTricks();
    	ArrayList<Trick> myValidTricks = myHand.getLegalTricks(Controller.getCurrentTrick(), organziedTricks);
    	Trick trickOut = myValidTricks.get(0);
    	//System.out.println("valid tricks: " + myValidTricks);
    	
    	removeCards(trickOut);
    	return trickOut;
    }
}