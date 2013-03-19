
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
     * when u add a card, it knows it's opponents dont have this card so removed from opponentshand
     * @param card
     */
    @Override
    protected void addCard(Card card) {
        super.addCard(card);
//        opponentsHand.removeCard(card);
    }

//    Agent is supposed to return the optimum legal trick here.
//    You have to edit this.
    /**
     * Returns an trick of Cards forming a wellformed trick. You have to remove 
     * the cards in the trick from your hand.
     * 
     * @return trick of Cards forming a wellformed trick.
     */
    @Override
    @SuppressWarnings({"empty-statement", "fallthrough"})
    protected Trick getLegalTrick() {

        Trick trickOut;
        Hand play0Hand = new Hand(Controller.player[0].myHand);
        Hand play1Hand = new Hand(Controller.player[1].myHand);
        Hand play2Hand = new Hand(Controller.player[2].myHand);
        Hand play3Hand = new Hand(Controller.player[3].myHand);
        trickOut = recurseGetLegalTrick(play0Hand, play1Hand, play2Hand, play3Hand,
                Controller.getCurrentTrick(), this.playerNumber, 0, this.playerNumber, 0, new Trick());

        removeCards(trickOut);
        System.out.println(myHand + " size: " + myHand.listOfCards.size());
        return trickOut;//stub
    }

    //recursion. currPlayer is the current player. firstPlayer is the player we seek to make win.
    private static Trick recurseGetLegalTrick(Hand p0Hand, Hand p1Hand, Hand p2Hand, Hand p3Hand,
            Trick currTrick, int currPlayer, int currDepth, int firstPlayer, int numPasses, Trick trickToPlay) {
//        System.out.println("currplayer: " + currPlayer);aaa
//        System.out.println("try trick: " + currTrick);aaa
//        final int MAXDEPTH = 2;//stub

//        if (p0Hand.size() == 0 || p1Hand.size() == 0 || p2Hand.size() == 0 || p3Hand.size() == 0 ||
//                depth == MAXDEPTH || numPasses == 4) {
//        if (firstPlayer == 0 && p0Hand.size() == 0 || firstPlayer == 1 && p1Hand.size() == 0 || firstPlayer == 2 && p2Hand.size() == 0 || firstPlayer == 3 && p3Hand.size() == 0 ||
//                currDepth >= MAXDEPTH || numPasses >= 4) {
////            System.out.println("returning "+trickToPlay);
//            return trickToPlay;
//        }

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
        //this is only for outputing purposes; shows all possible tricks
//        ArrayList<Trick> possibleTricks = getAllTricks(currHand);aaa
//        System.out.println("all possible tricks: " + possibleTricks);aaa
        //

        ArrayList<Trick> myPossibleTricks;
        if (Controller.historyOfTricks.size() != 0) {
            myPossibleTricks = myPossibleTricks(currTrick.trickType(), currHand);
        } else {
            myPossibleTricks = getAllTricks(currHand);
        }

//        switch (currPlayer) {
//            case 0:
//                p0Hand = currHand;
//                break;
//            case 1:
//                p1Hand = currHand;
//                break;
//            case 2:
//                p2Hand = currHand;
//                break;
//            case 3:
//                p3Hand = currHand;
//                break;
//            default:
//                break;
//        }

        System.out.println("same type tricks: " + myPossibleTricks);
        ArrayList<Trick> myValidTricks = new ArrayList<Trick>();

        //all tricks which are not well-formed are removed
        for (Trick trick : myPossibleTricks) {
            if (Controller.isWellFormed(trick, currTrick)) {
                myValidTricks.add(trick);
            }
        }

        if (myValidTricks.size() == 1) {
            return myValidTricks.get(0);
        }
//        Random random = new Random();

        Trick trickOut = new Trick();//empty/pass trick

//            //get random trick;testing purpose
//            trickOut = myValidTricks.get(random.nextInt(myValidTricks.size()));
        if (Controller.historyOfTricks.size() != 0) {//if not the first player's first move, allow pass
            myValidTricks.add(trickOut);
        }

        System.out.println("valid tricks: " + myValidTricks);

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
            numOfWinArray.add(recurseGetLegalTrick(player0Hand, player1Hand, player2Hand, player3Hand,
                    trick, (++currPlayer) % 4, currDepth++, firstPlayer, numPasses, trickToPlay, 0));
        }
        int maxWins = Collections.max(numOfWinArray);
        int maxWinsTrickIndex = numOfWinArray.indexOf(maxWins);
        System.out.println("max number wins (depth 4 search) is: " + maxWins);
        return myValidTricks.get(maxWinsTrickIndex);//stub
    }

    //recursion. currPlayer is the current player. firstPlayer is the player we seek to make win.
    private static int recurseGetLegalTrick(Hand p0Hand, Hand p1Hand, Hand p2Hand, Hand p3Hand,
            Trick currTrick, int currPlayer, int currDepth, int firstPlayer, int numPasses, Trick trickToPlay,
            int numOfWins) {
//        System.out.println("currplayer: " + currPlayer);aaa
//        System.out.println("try trick: " + currTrick);aaa
        final int MAXDEPTH = 4;//stub

//        if (p0Hand.size() == 0 || p1Hand.size() == 0 || p2Hand.size() == 0 || p3Hand.size() == 0 ||
//                depth == MAXDEPTH || numPasses == 4) {
        if (firstPlayer == 0 && p0Hand.size() == 0 || firstPlayer == 1 && p1Hand.size() == 0 || firstPlayer == 2 && p2Hand.size() == 0 || firstPlayer == 3 && p3Hand.size() == 0) {
//            System.out.println("returning "+trickToPlay);
            numOfWins++;
            return numOfWins;
        }
        if (currDepth >= MAXDEPTH || numPasses >= 3) {
//            System.out.println("returning "+trickToPlay);
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
        //this is only for outputing purposes; shows all possible tricks
//        ArrayList<Trick> possibleTricks = getAllTricks(currHand);aaa
//        System.out.println("all possible tricks: " + possibleTricks);aaa
        //

        ArrayList<Trick> myPossibleTricks;
        if (Controller.historyOfTricks.size() != 0) {
            myPossibleTricks = myPossibleTricks(currTrick.trickType(), currHand);
        } else {
            myPossibleTricks = getAllTricks(currHand);
        }

//        switch (currPlayer) {
//            case 0:
//                p0Hand = currHand;
//                break;
//            case 1:
//                p1Hand = currHand;
//                break;
//            case 2:
//                p2Hand = currHand;
//                break;
//            case 3:
//                p3Hand = currHand;
//                break;
//            default:
//                break;
//        }

//        System.out.println("same type tricks: " + myPossibleTricks);
        ArrayList<Trick> myValidTricks = new ArrayList<Trick>();

        //all tricks which are not well-formed are removed
        for (Trick trick : myPossibleTricks) {
            if (Controller.isWellFormed(trick, currTrick)) {
                myValidTricks.add(trick);
            }
        }
//        System.out.println("valid tricks: " + myValidTricks);

//        Random random = new Random();

        Trick trickOut = new Trick();//empty/pass trick

//            //get random trick;testing purpose
//            trickOut = myValidTricks.get(random.nextInt(myValidTricks.size()));
        if (Controller.historyOfTricks.size() != 0) {//if not the first player's first move, allow pass
            myValidTricks.add(trickOut);
        }
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
            numOfWins = recurseGetLegalTrick(player0Hand, player1Hand, player2Hand, player3Hand,
                    trick, (++currPlayer) % 4, currDepth++, firstPlayer, numPasses, trickToPlay, numOfWins);
        }


        return numOfWins;//stub
    }


    //returns all possible legal tricks that can be formed by this hand (not necessarily wellformed)
    @SuppressWarnings("fallthrough")
    private static ArrayList<Trick> myPossibleTricks(Trick.TrickType currentTrickType, Hand thisHand) {
        ArrayList<Trick> trickArray = new ArrayList<Trick>();
        //this is first player so return all legal tricks possible
        if (Controller.historyOfTricks.size() == 0) {
//            System.out.println("here 116");
            return getAllTricks(thisHand);
        }

//                System.out.println("tricktype is " + currentTrickType.toString());
        switch (currentTrickType) {
            case SINGLE:
                for (Trick trick : getSingleTricks(thisHand)) {
                    trickArray.add(trick);
                }
                return trickArray;
            case PAIR:
                for (Trick trick : getPairTricks(thisHand)) {
                    trickArray.add(trick);
                }
                return trickArray;
            case STRAIGHTFLUSH:
                for (Trick trick : getStraightFlushTricks(thisHand)) {
                    trickArray.add(trick);
                }
            case FOUROFAKIND:
                for (Trick trick : getFourOfAKindTricks(thisHand)) {
                    trickArray.add(trick);
                }
            case FULLHOUSE:
                for (Trick trick : getFullHouseTricks(thisHand)) {
                    trickArray.add(trick);
                }
            case FLUSH:
                for (Trick trick : getFlushTricks(thisHand)) {
                    trickArray.add(trick);
                }
            case STRAIGHT:
                for (Trick trick : getStraightTricks(thisHand)) {
                    trickArray.add(trick);
                }
                return trickArray;
            case PASS:
                return getAllTricks(thisHand);
            default:
                //throw new Exception("Something wrong here!");
                return null;//error here
        }
    }

    private static ArrayList<Trick> getAllTricks(Hand aHand) {
        ArrayList<Trick> trickArray = new ArrayList<Trick>();
        for (Trick trick : getFiveTricks(aHand)) {
            trickArray.add(trick);
        }
        for (Trick trick : getPairTricks(aHand)) {
            trickArray.add(trick);
        }
        for (Trick trick : getSingleTricks(aHand)) {
            trickArray.add(trick);
        }
        return trickArray;
    }

    protected ArrayList<Card> searchLegalMove() {

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

    }

    /**
     * Given a hand, generates an arraylist of all possible single Tricks
     * 
     * @param hand the hand
     * @return an arraylist of all possible single Tricks from the cards in hand
     */
    public static ArrayList<Trick> getSingleTricks(Hand hand) {
        ArrayList<Trick> trickList = new ArrayList<Trick>();

        //if hand is not sorted, sort it
        if (!hand.isSorted()) {
            hand.sort();
        }

        for (Card card : hand.listOfCards) {
            Trick singleTrick = new Trick(card);
            //ensures only single tricks entered
            //shouldn't have any issues, just asserting
            if (singleTrick.trickType() == Trick.TrickType.SINGLE) {
                trickList.add(new Trick(card));
            }
        }
        return trickList;
    }

    /**
     * Given a hand, generates an arraylist of all possible pair Tricks
     * 
     * @param hand the hand
     * @return an arraylist of all possible pair Tricks from the cards in hand
     */
    public static ArrayList<Trick> getPairTricks(Hand hand) {
        ArrayList<Trick> trickList = new ArrayList<Trick>();

        //if hand is not sorted, sort it
        if (!hand.isSorted()) {
            hand.sort();
        }

        int sizeHand = hand.size();
        if (sizeHand >= 2) {
            Trick pairTrick;
            ArrayList<ArrayList<Card>> arrayArrayCards = new ArrayList<ArrayList<Card>>();
            for (Card.Rank rank : Card.Rank.values()) {
                ArrayList<Card> arrayCards = new ArrayList<Card>();
                for (int i = 0; i < sizeHand; i++) {
                    if (hand.listOfCards.get(i).rank() == rank) {
                        arrayCards.add(hand.listOfCards.get(i));
                    }
                }
                arrayArrayCards.add(arrayCards);
            }
            for (ArrayList<Card> arrayCards : arrayArrayCards) {
                int sizeArrayCards = arrayCards.size();
                for (int i = 0; i < sizeArrayCards - 1; i++) {
                    for (int j = i + 1; j < sizeArrayCards; j++) {
                        pairTrick = new Trick();
                        pairTrick.addCard(arrayCards.get(i));
                        pairTrick.addCard(arrayCards.get(j));
                        //ensures only pair tricks entered
                        //shouldn't have any issues, just asserting
                        if (pairTrick.trickType() == Trick.TrickType.PAIR) {
                            trickList.add(pairTrick);
                        }
                    }
                }
            }
        }
        return trickList;
    }

    /**
     * Given a hand, generates an arraylist of all possible poker (five cards) Tricks
     * 
     * @param hand the hand
     * @return an arraylist of all possible poker (five cards) Tricks from the cards in hand
     */
    public static ArrayList<Trick> getFiveTricks(Hand hand) {
        ArrayList<Trick> trickList = new ArrayList<Trick>();

        //if hand is not sorted, sort it
        if (!hand.isSorted()) {
            hand.sort();
        }
        trickList.addAll(getStraightTricks(hand));
        trickList.addAll(getFlushTricks(hand));
        trickList.addAll(getFullHouseTricks(hand));
        trickList.addAll(getFourOfAKindTricks(hand));
        trickList.addAll(getStraightFlushTricks(hand));
        return trickList;
    }

    //very inefficient, to optimise
    /**
     * Given a hand, generates an arraylist of all possible straight Tricks
     * 
     * @param hand the hand
     * @return an arraylist of all possible straight Tricks from the cards in hand
     */
    public static ArrayList<Trick> getStraightTricks(Hand hand) {
        ArrayList<Trick> trickList = new ArrayList<Trick>();

        //if hand is not sorted, sort it
        if (!hand.isSorted()) {
            hand.sort();
        }

        int size = hand.size();
        if (size >= 5) {

            boolean[] num = new boolean[size];
            //permute 1111100000
            for (int a = 0; a < size - 5 + 1; a++) {
                for (int b = a + 1; b < size - 4 + 1; b++) {
                    for (int c = b + 1; c < size - 3 + 1; c++) {
                        for (int d = c + 1; d < size - 2 + 1; d++) {
                            for (int e = d + 1; e < size - 1 + 1; e++) {
                                for (int i = 0; i < size; i++) {
                                    num[i] = false;
                                }
                                num[a] = true;
                                num[b] = true;
                                num[c] = true;
                                num[d] = true;
                                num[e] = true;

                                Trick flushTrick = new Trick();
                                for (int i = 0; i < size; i++) {
                                    //for each 1, add the card in that location to a new trick
                                    if (num[i]) {
                                        flushTrick.addCard(hand.listOfCards.get(i));
                                    }
                                }
//                                ensures that only pure straights included, ie, straightflushes not included
                                if (flushTrick.trickType() == Trick.TrickType.STRAIGHT) {
                                    trickList.add(flushTrick);
                                }
                            }
                        }
                    }
                }
            }
        }
        return trickList;
    }

    /**
     * Given a hand, generates an arraylist of all possible flush Tricks
     * 
     * @param hand the
     * @return an arraylist of all possible flush Tricks from the cards in hand
     */
    public static ArrayList<Trick> getFlushTricks(Hand hand) {
        ArrayList<Trick> trickList = new ArrayList<Trick>();

        //if hand is not sorted, sort it
        if (!hand.isSorted()) {
            hand.sort();
        }

        Hand spades = new Hand();
        Hand hearts = new Hand();
        Hand clubs = new Hand();
        Hand diamonds = new Hand();

        //put the cards into different hands based on same suits
        for (Card card : hand.listOfCards) {
            switch (card.suit()) {
                case SPADES:
                    spades.addCard(card);
                    break;
                case HEARTS:
                    hearts.addCard(card);
                    break;
                case CLUBS:
                    clubs.addCard(card);
                    break;
                case DIAMONDS:
                    diamonds.addCard(card);
                    break;
                default:
                    break;
            }
        }

        int spadesSize = spades.size();
        int heartsSize = hearts.size();
        int clubsSize = clubs.size();
        int diamondsSize = diamonds.size();

        if (spadesSize >= 5) {

            boolean[] num = new boolean[spadesSize];
            //permute 1111100000
            for (int a = 0; a < spadesSize - 5 + 1; a++) {
                for (int b = a + 1; b < spadesSize - 4 + 1; b++) {
                    for (int c = b + 1; c < spadesSize - 3 + 1; c++) {
                        for (int d = c + 1; d < spadesSize - 2 + 1; d++) {
                            for (int e = d + 1; e < spadesSize - 1 + 1; e++) {
                                for (int i = 0; i < spadesSize; i++) {
                                    num[i] = false;
                                }
                                num[a] = true;
                                num[b] = true;
                                num[c] = true;
                                num[d] = true;
                                num[e] = true;

                                Trick flushTrick = new Trick();
                                for (int i = 0; i < spadesSize; i++) {
                                    //for each 1, add the card in that location to a new trick
                                    if (num[i]) {
                                        flushTrick.addCard(spades.listOfCards.get(i));
                                    }
                                }
                                //ensures that only pure flushs included, ie, straightflushes not included
                                if (flushTrick.trickType() == Trick.TrickType.FLUSH) {
                                    trickList.add(flushTrick);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (heartsSize >= 5) {

            boolean[] num = new boolean[heartsSize];
            //permute 1111100000
            for (int a = 0; a < heartsSize - 5 + 1; a++) {
                for (int b = a + 1; b < heartsSize - 4 + 1; b++) {
                    for (int c = b + 1; c < heartsSize - 3 + 1; c++) {
                        for (int d = c + 1; d < heartsSize - 2 + 1; d++) {
                            for (int e = d + 1; e < heartsSize - 1 + 1; e++) {
                                for (int i = 0; i < heartsSize; i++) {
                                    num[i] = false;
                                }
                                num[a] = true;
                                num[b] = true;
                                num[c] = true;
                                num[d] = true;
                                num[e] = true;

                                Trick flushTrick = new Trick();
                                for (int i = 0; i < heartsSize; i++) {
                                    //for each 1, add the card in that location to a new trick
                                    if (num[i]) {
                                        flushTrick.addCard(hearts.listOfCards.get(i));
                                    }
                                }
                                //ensures that only pure flushs included, ie, straightflushes not included
                                if (flushTrick.trickType() == Trick.TrickType.FLUSH) {
                                    trickList.add(flushTrick);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (clubsSize >= 5) {

            boolean[] num = new boolean[clubsSize];
            //permute 1111100000
            for (int a = 0; a < clubsSize - 5 + 1; a++) {
                for (int b = a + 1; b < clubsSize - 4 + 1; b++) {
                    for (int c = b + 1; c < clubsSize - 3 + 1; c++) {
                        for (int d = c + 1; d < clubsSize - 2 + 1; d++) {
                            for (int e = d + 1; e < clubsSize - 1 + 1; e++) {
                                for (int i = 0; i < clubsSize; i++) {
                                    num[i] = false;
                                }
                                num[a] = true;
                                num[b] = true;
                                num[c] = true;
                                num[d] = true;
                                num[e] = true;

                                Trick flushTrick = new Trick();
                                for (int i = 0; i < clubsSize; i++) {
                                    //for each 1, add the card in that location to a new trick
                                    if (num[i]) {
                                        flushTrick.addCard(clubs.listOfCards.get(i));
                                    }
                                }
                                //ensures that only pure flushs included, ie, straightflushes not included
                                if (flushTrick.trickType() == Trick.TrickType.FLUSH) {
                                    trickList.add(flushTrick);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (diamondsSize >= 5) {

            boolean[] num = new boolean[diamondsSize];
            //permute 1111100000
            for (int a = 0; a < diamondsSize - 5 + 1; a++) {
                for (int b = a + 1; b < diamondsSize - 4 + 1; b++) {
                    for (int c = b + 1; c < diamondsSize - 3 + 1; c++) {
                        for (int d = c + 1; d < diamondsSize - 2 + 1; d++) {
                            for (int e = d + 1; e < diamondsSize - 1 + 1; e++) {
                                for (int i = 0; i < diamondsSize; i++) {
                                    num[i] = false;
                                }
                                num[a] = true;
                                num[b] = true;
                                num[c] = true;
                                num[d] = true;
                                num[e] = true;

                                Trick flushTrick = new Trick();
                                for (int i = 0; i < diamondsSize; i++) {
                                    //for each 1, add the card in that location to a new trick
                                    if (num[i]) {
                                        flushTrick.addCard(diamonds.listOfCards.get(i));
                                    }
                                }
                                //ensures that only pure flushs included, ie, straightflushes not included
                                if (flushTrick.trickType() == Trick.TrickType.FLUSH) {
                                    trickList.add(flushTrick);
                                }
                            }
                        }
                    }
                }
            }
        }
        return trickList;
    }

    /**
     * Given a hand, generates an arraylist of all possible fullHouse Tricks
     * 
     * @param hand the hand
     * @return an arraylist of all possible full house Tricks from the cards in hand
     */
    public static ArrayList<Trick> getFullHouseTricks(Hand hand) {
        ArrayList<Trick> trickList = new ArrayList<Trick>();

        //if hand is not sorted, sort it
        if (!hand.isSorted()) {
            hand.sort();
        }

        int size = hand.size();
        if (size >= 5) {
            for (int i = 0; i < size - 2; i++) {
                if (hand.listOfCards.get(i).rank() == hand.listOfCards.get(i + 2).rank()) {

                    Hand cloneHand = new Hand(hand.listOfCards);
                    cloneHand.removeCard(hand.listOfCards.get(i));
                    cloneHand.removeCard(hand.listOfCards.get(i + 1));
                    cloneHand.removeCard(hand.listOfCards.get(i + 2));

                    ArrayList<Trick> pairTrickList = new ArrayList<Trick>();
                    pairTrickList = getPairTricks(cloneHand);

                    Trick fullHouseTrick;

                    for (Trick pairTrick : pairTrickList) {

                        fullHouseTrick = new Trick();
                        fullHouseTrick.addCard(hand.listOfCards.get(i));
                        fullHouseTrick.addCard(hand.listOfCards.get(i + 1));
                        fullHouseTrick.addCard(hand.listOfCards.get(i + 2));
                        fullHouseTrick.addCard(pairTrick.listOfCards);

                        //ensures that only pure fullHouse included, ie, fullHouse which are flush not included
                        //because we are assuming single deck play
                        if (fullHouseTrick.trickType() == Trick.TrickType.FULLHOUSE) {
                            trickList.add(fullHouseTrick);
                        }

                        if (i < size - 3 && hand.listOfCards.get(i).rank() == hand.listOfCards.get(i + 3).rank()) {
                            fullHouseTrick = new Trick();
                            fullHouseTrick.addCard(hand.listOfCards.get(i));
                            fullHouseTrick.addCard(hand.listOfCards.get(i + 1));
                            fullHouseTrick.addCard(hand.listOfCards.get(i + 3));
                            fullHouseTrick.addCard(pairTrick.listOfCards);

                            //ensures that only pure fullHouse included, ie, fullHouse which are flush not included
                            //because we are assuming single deck play
                            if (fullHouseTrick.trickType() == Trick.TrickType.FULLHOUSE) {
                                trickList.add(fullHouseTrick);
                            }
                            fullHouseTrick = new Trick();
                            fullHouseTrick.addCard(hand.listOfCards.get(i));
                            fullHouseTrick.addCard(hand.listOfCards.get(i + 2));
                            fullHouseTrick.addCard(hand.listOfCards.get(i + 3));
                            fullHouseTrick.addCard(pairTrick.listOfCards);

                            //ensures that only pure fullHouse included, ie, fullHouse which are flush not included
                            //because we are assuming single deck play
                            if (fullHouseTrick.trickType() == Trick.TrickType.FULLHOUSE) {
                                trickList.add(fullHouseTrick);
                            }
                        }
                    }
                }
            }
        }
        return trickList;
    }

    /**
     * Given a hand, generates an arraylist of all possible fourOfAKind Tricks
     * 
     * @param hand the hand
     * @return an arraylist of all possible four of a kind Tricks from the cards in hand
     */
    public static ArrayList<Trick> getFourOfAKindTricks(Hand hand) {
        ArrayList<Trick> trickList = new ArrayList<Trick>();

        //if hand is not sorted, sort it
        if (!hand.isSorted()) {
            hand.sort();
        }
        int size = hand.size();
        if (size >= 5) {
            for (int i = 0; i < size - 3; i++) {
                if (hand.listOfCards.get(i).rank() == hand.listOfCards.get(i + 3).rank()) {

                    for (int j = 0; j < size; j++) {
                        Trick fourOfAKindTrick = new Trick();
                        if (j < i || j > i + 3) {
                            fourOfAKindTrick.addCard(hand.listOfCards.get(i));
                            fourOfAKindTrick.addCard(hand.listOfCards.get(i + 1));
                            fourOfAKindTrick.addCard(hand.listOfCards.get(i + 2));
                            fourOfAKindTrick.addCard(hand.listOfCards.get(i + 3));
                            fourOfAKindTrick.addCard(hand.listOfCards.get(j));

                            //ensures that only pure fourOfAKind included, ie, fourOfAKind which are flush not included
                            //because we are assuming single deck play
                            if (fourOfAKindTrick.trickType() == Trick.TrickType.FOUROFAKIND) {
                                trickList.add(fourOfAKindTrick);
                            }
                        }
                    }
                }
            }
        }
        return trickList;
    }

    /**
     * Given a hand, generates an arraylist of all possible straightflush Tricks
     * 
     * @param hand the hand
     * @return an arraylist of all possible straight flush Tricks from the cards in hand
     */
    public static ArrayList<Trick> getStraightFlushTricks(Hand hand) {
        ArrayList<Trick> trickList = new ArrayList<Trick>();
        //if hand is not sorted, sort it
        if (!hand.isSorted()) {
            hand.sort();
        }
        Hand spades = new Hand();
        Hand hearts = new Hand();
        Hand clubs = new Hand();
        Hand diamonds = new Hand();
        //put the cards into different hands based on same suits
        for (Card card : hand.listOfCards) {
            switch (card.suit()) {
                case SPADES:
                    spades.addCard(card);
                    break;
                case HEARTS:
                    hearts.addCard(card);
                    break;
                case CLUBS:
                    clubs.addCard(card);
                    break;
                case DIAMONDS:
                    diamonds.addCard(card);
                    break;
                default:
                    break;
            }
        }
        int spadesSize = spades.size();
        int heartsSize = hearts.size();
        int clubsSize = clubs.size();
        int diamondsSize = diamonds.size();
        if (spadesSize >= 5) {
            for (int i = 0; i < spadesSize - 4; i++) {
                Trick straightFlushTrick = new Trick();
                if (spades.listOfCards.get(i).rank().value() + 4 ==
                        spades.listOfCards.get(i + 4).rank().value() &&
                        //exclude JQKA|2
                        spades.listOfCards.get(i + 4).rank() != Card.Rank.TWO) {
                    straightFlushTrick.addCard(spades.listOfCards.get(i));
                    straightFlushTrick.addCard(spades.listOfCards.get(i + 1));
                    straightFlushTrick.addCard(spades.listOfCards.get(i + 2));
                    straightFlushTrick.addCard(spades.listOfCards.get(i + 3));
                    straightFlushTrick.addCard(spades.listOfCards.get(i + 4));
                    //verify straightflush
                    if (straightFlushTrick.trickType() == Trick.TrickType.STRAIGHTFLUSH) {
                        trickList.add(straightFlushTrick);
                    }
                }
            }
            if ( //include 3456|2 and 345|A2
                    spades.listOfCards.get(0).rank() == Card.Rank.THREE &&
                    spades.listOfCards.get(spadesSize - 1).rank() == Card.Rank.TWO) {
                if (//3456|2
                        spades.listOfCards.get(3).rank() == Card.Rank.SIX) {
                    Trick straightFlushTrick = new Trick();
                    straightFlushTrick.addCard(spades.listOfCards.get(0));
                    straightFlushTrick.addCard(spades.listOfCards.get(1));
                    straightFlushTrick.addCard(spades.listOfCards.get(2));
                    straightFlushTrick.addCard(spades.listOfCards.get(3));
                    straightFlushTrick.addCard(spades.listOfCards.get(spadesSize - 1));
                    //verify straightflush
                    if (straightFlushTrick.trickType() == Trick.TrickType.STRAIGHTFLUSH) {
                        trickList.add(straightFlushTrick);
                    }
                }
                if (//345|A2
                        spades.listOfCards.get(spadesSize - 2).rank() == Card.Rank.ACE &&
                        spades.listOfCards.get(2).rank() == Card.Rank.FIVE) {
                    Trick straightFlushTrick = new Trick();
                    straightFlushTrick.addCard(spades.listOfCards.get(0));
                    straightFlushTrick.addCard(spades.listOfCards.get(1));
                    straightFlushTrick.addCard(spades.listOfCards.get(2));
                    straightFlushTrick.addCard(spades.listOfCards.get(spadesSize - 2));
                    straightFlushTrick.addCard(spades.listOfCards.get(spadesSize - 1));
                    //verify straightflush
                    if (straightFlushTrick.trickType() == Trick.TrickType.STRAIGHTFLUSH) {
                        trickList.add(straightFlushTrick);
                    }
                }
            }
        }
        if (heartsSize >= 5) {
            for (int i = 0; i < heartsSize - 4; i++) {
                Trick straightFlushTrick = new Trick();
                if (hearts.listOfCards.get(i).rank().value() + 4 ==
                        hearts.listOfCards.get(i + 4).rank().value() &&
                        //exclude JQKA|2
                        hearts.listOfCards.get(i + 4).rank() != Card.Rank.TWO) {
                    straightFlushTrick.addCard(hearts.listOfCards.get(i));
                    straightFlushTrick.addCard(hearts.listOfCards.get(i + 1));
                    straightFlushTrick.addCard(hearts.listOfCards.get(i + 2));
                    straightFlushTrick.addCard(hearts.listOfCards.get(i + 3));
                    straightFlushTrick.addCard(hearts.listOfCards.get(i + 4));
                    //verify straightflush
                    if (straightFlushTrick.trickType() == Trick.TrickType.STRAIGHTFLUSH) {
                        trickList.add(straightFlushTrick);
                    }
                }
            }
            if ( //include 3456|2 and 345|A2
                    hearts.listOfCards.get(0).rank() == Card.Rank.THREE &&
                    hearts.listOfCards.get(heartsSize - 1).rank() == Card.Rank.TWO) {
                if (//3456|2
                        hearts.listOfCards.get(3).rank() == Card.Rank.SIX) {
                    Trick straightFlushTrick = new Trick();
                    straightFlushTrick.addCard(hearts.listOfCards.get(0));
                    straightFlushTrick.addCard(hearts.listOfCards.get(1));
                    straightFlushTrick.addCard(hearts.listOfCards.get(2));
                    straightFlushTrick.addCard(hearts.listOfCards.get(3));
                    straightFlushTrick.addCard(hearts.listOfCards.get(heartsSize - 1));
                    //verify straightflush
                    if (straightFlushTrick.trickType() == Trick.TrickType.STRAIGHTFLUSH) {
                        trickList.add(straightFlushTrick);
                    }
                }
                if (//345|A2
                        hearts.listOfCards.get(heartsSize - 2).rank() == Card.Rank.ACE &&
                        hearts.listOfCards.get(2).rank() == Card.Rank.FIVE) {
                    Trick straightFlushTrick = new Trick();
                    straightFlushTrick.addCard(hearts.listOfCards.get(0));
                    straightFlushTrick.addCard(hearts.listOfCards.get(1));
                    straightFlushTrick.addCard(hearts.listOfCards.get(2));
                    straightFlushTrick.addCard(hearts.listOfCards.get(heartsSize - 2));
                    straightFlushTrick.addCard(hearts.listOfCards.get(heartsSize - 1));
                    //verify straightflush
                    if (straightFlushTrick.trickType() == Trick.TrickType.STRAIGHTFLUSH) {
                        trickList.add(straightFlushTrick);
                    }
                }
            }
        }
        if (clubsSize >= 5) {
            for (int i = 0; i < clubsSize - 4; i++) {
                Trick straightFlushTrick = new Trick();
                if (clubs.listOfCards.get(i).rank().value() + 4 ==
                        clubs.listOfCards.get(i + 4).rank().value() &&
                        //exclude JQKA|2
                        clubs.listOfCards.get(i + 4).rank() != Card.Rank.TWO) {
                    straightFlushTrick.addCard(clubs.listOfCards.get(i));
                    straightFlushTrick.addCard(clubs.listOfCards.get(i + 1));
                    straightFlushTrick.addCard(clubs.listOfCards.get(i + 2));
                    straightFlushTrick.addCard(clubs.listOfCards.get(i + 3));
                    straightFlushTrick.addCard(clubs.listOfCards.get(i + 4));
                    //verify straightflush
                    if (straightFlushTrick.trickType() == Trick.TrickType.STRAIGHTFLUSH) {
                        trickList.add(straightFlushTrick);
                    }
                }
            }
            if ( //include 3456|2 and 345|A2
                    clubs.listOfCards.get(0).rank() == Card.Rank.THREE &&
                    clubs.listOfCards.get(clubsSize - 1).rank() == Card.Rank.TWO) {
                if (//3456|2
                        clubs.listOfCards.get(3).rank() == Card.Rank.SIX) {
                    Trick straightFlushTrick = new Trick();
                    straightFlushTrick.addCard(clubs.listOfCards.get(0));
                    straightFlushTrick.addCard(clubs.listOfCards.get(1));
                    straightFlushTrick.addCard(clubs.listOfCards.get(2));
                    straightFlushTrick.addCard(clubs.listOfCards.get(3));
                    straightFlushTrick.addCard(clubs.listOfCards.get(clubsSize - 1));
                    //verify straightflush
                    if (straightFlushTrick.trickType() == Trick.TrickType.STRAIGHTFLUSH) {
                        trickList.add(straightFlushTrick);
                    }
                }
                if (//345|A2
                        clubs.listOfCards.get(clubsSize - 2).rank() == Card.Rank.ACE &&
                        clubs.listOfCards.get(2).rank() == Card.Rank.FIVE) {
                    Trick straightFlushTrick = new Trick();
                    straightFlushTrick.addCard(clubs.listOfCards.get(0));
                    straightFlushTrick.addCard(clubs.listOfCards.get(1));
                    straightFlushTrick.addCard(clubs.listOfCards.get(2));
                    straightFlushTrick.addCard(clubs.listOfCards.get(clubsSize - 2));
                    straightFlushTrick.addCard(clubs.listOfCards.get(clubsSize - 1));
                    //verify straightflush
                    if (straightFlushTrick.trickType() == Trick.TrickType.STRAIGHTFLUSH) {
                        trickList.add(straightFlushTrick);
                    }
                }
            }
        }
        if (diamondsSize >= 5) {
            for (int i = 0; i < diamondsSize - 4; i++) {
//                System.out.println("950");
                Trick straightFlushTrick = new Trick();
                if (diamonds.listOfCards.get(i).rank().value() + 4 ==
                        diamonds.listOfCards.get(i + 4).rank().value() &&
                        //exclude JQKA|2
                        diamonds.listOfCards.get(i + 4).rank() != Card.Rank.TWO) {
                    straightFlushTrick.addCard(diamonds.listOfCards.get(i));
                    straightFlushTrick.addCard(diamonds.listOfCards.get(i + 1));
                    straightFlushTrick.addCard(diamonds.listOfCards.get(i + 2));
                    straightFlushTrick.addCard(diamonds.listOfCards.get(i + 3));
                    straightFlushTrick.addCard(diamonds.listOfCards.get(i + 4));
                    //verify straightflush
                    if (straightFlushTrick.trickType() == Trick.TrickType.STRAIGHTFLUSH) {
                        trickList.add(straightFlushTrick);
                    }
                }
            }
            if ( //include 3456|2 and 345|A2
                    diamonds.listOfCards.get(0).rank() == Card.Rank.THREE &&
                    diamonds.listOfCards.get(diamondsSize - 1).rank() == Card.Rank.TWO) {
                if (//3456|2
                        diamonds.listOfCards.get(3).rank() == Card.Rank.SIX) {
                    Trick straightFlushTrick = new Trick();
                    straightFlushTrick.addCard(diamonds.listOfCards.get(0));
                    straightFlushTrick.addCard(diamonds.listOfCards.get(1));
                    straightFlushTrick.addCard(diamonds.listOfCards.get(2));
                    straightFlushTrick.addCard(diamonds.listOfCards.get(3));
                    straightFlushTrick.addCard(diamonds.listOfCards.get(diamondsSize - 1));
                    //verify straightflush
                    if (straightFlushTrick.trickType() == Trick.TrickType.STRAIGHTFLUSH) {
                        trickList.add(straightFlushTrick);
                    }
                }
                if (//345|A2
                        diamonds.listOfCards.get(diamondsSize - 2).rank() == Card.Rank.ACE &&
                        diamonds.listOfCards.get(2).rank() == Card.Rank.FIVE) {
                    Trick straightFlushTrick = new Trick();
                    straightFlushTrick.addCard(diamonds.listOfCards.get(0));
                    straightFlushTrick.addCard(diamonds.listOfCards.get(1));
                    straightFlushTrick.addCard(diamonds.listOfCards.get(2));
                    straightFlushTrick.addCard(diamonds.listOfCards.get(diamondsSize - 2));
                    straightFlushTrick.addCard(diamonds.listOfCards.get(diamondsSize - 1));
                    //verify straightflush
                    if (straightFlushTrick.trickType() == Trick.TrickType.STRAIGHTFLUSH) {
                        trickList.add(straightFlushTrick);
                    }
                }
            }
        }
        return trickList;
    }
}
