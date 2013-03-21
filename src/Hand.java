
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
    
    public ArrayList<Trick> getAllTricks() {
        ArrayList<Trick> trickArray = new ArrayList<Trick>();
        for (Trick trick : this.getFiveTricks()) {
            trickArray.add(trick);
        }
        for (Trick trick : this.getPairTricks()) {
            trickArray.add(trick);
        }
        for (Trick trick : this.getSingleTricks()) {
            trickArray.add(trick);
        }
        return trickArray;
    }

    
    /**
     * Given a hand, generates an arraylist of all possible single Tricks
     * 
     * @return an arraylist of all possible single Tricks from the cards in hand
     */
    public ArrayList<Trick> getSingleTricks() {
        ArrayList<Trick> trickList = new ArrayList<Trick>();

        //if hand is not sorted, sort it
        if (!this.isSorted()) {
            this.sort();
        }

        for (Card card : this.listOfCards) {
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
    public ArrayList<Trick> getPairTricks() {
        ArrayList<Trick> trickList = new ArrayList<Trick>();

        //if hand is not sorted, sort it
        if (!this.isSorted()) {
            this.sort();
        }

        int sizeHand = this.size();
        if (sizeHand >= 2) {
            Trick pairTrick;
            ArrayList<ArrayList<Card>> arrayArrayCards = new ArrayList<ArrayList<Card>>();
            for (Card.Rank rank : Card.Rank.values()) {
                ArrayList<Card> arrayCards = new ArrayList<Card>();
                for (int i = 0; i < sizeHand; i++) {
                    if (this.listOfCards.get(i).rank() == rank) {
                        arrayCards.add(this.listOfCards.get(i));
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
     * @return an arraylist of all possible poker (five cards) Tricks from the cards in hand
     */
    public ArrayList<Trick> getFiveTricks() {
        ArrayList<Trick> trickList = new ArrayList<Trick>();

        //if hand is not sorted, sort it
        if (!this.isSorted()) {
        	this.sort();
        }
        trickList.addAll(this.getStraightTricks());
        trickList.addAll(this.getFlushTricks());
        trickList.addAll(this.getFullHouseTricks());
        trickList.addAll(this.getFourOfAKindTricks());
        trickList.addAll(this.getStraightFlushTricks());
        return trickList;
    }

    // TODO very inefficient, to optimise
    /**
     * Given a hand, generates an arraylist of all possible straight Tricks
     * 
     * @return an arraylist of all possible straight Tricks from the cards in hand
     */
    public ArrayList<Trick> getStraightTricks() {
        ArrayList<Trick> trickList = new ArrayList<Trick>();

        //if hand is not sorted, sort it
        if (!this.isSorted()) {
        	this.sort();
        }

        int size = this.size();
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
                                        flushTrick.addCard(this.listOfCards.get(i));
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
     * @return an arraylist of all possible flush Tricks from the cards in hand
     */
    public ArrayList<Trick> getFlushTricks() {
        ArrayList<Trick> trickList = new ArrayList<Trick>();

        //if hand is not sorted, sort it
        if (!this.isSorted()) {
        	this.sort();
        }

        Hand spades = new Hand();
        Hand hearts = new Hand();
        Hand clubs = new Hand();
        Hand diamonds = new Hand();

        //put the cards into different hands based on same suits
        for (Card card : this.listOfCards) {
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
     * @return an arraylist of all possible full house Tricks from the cards in hand
     */
    public ArrayList<Trick> getFullHouseTricks() {
        ArrayList<Trick> trickList = new ArrayList<Trick>();

        //if hand is not sorted, sort it
        if (!this.isSorted()) {
        	this.sort();
        }

        int size = this.size();
        if (size >= 5) {
            for (int i = 0; i < size - 2; i++) {
                if (this.listOfCards.get(i).rank() == this.listOfCards.get(i + 2).rank()) {

                    Hand cloneHand = new Hand(this.listOfCards);
                    cloneHand.removeCard(this.listOfCards.get(i));
                    cloneHand.removeCard(this.listOfCards.get(i + 1));
                    cloneHand.removeCard(this.listOfCards.get(i + 2));

                    ArrayList<Trick> pairTrickList = new ArrayList<Trick>();
                    pairTrickList = cloneHand.getPairTricks();

                    Trick fullHouseTrick;

                    for (Trick pairTrick : pairTrickList) {

                        fullHouseTrick = new Trick();
                        fullHouseTrick.addCard(this.listOfCards.get(i));
                        fullHouseTrick.addCard(this.listOfCards.get(i + 1));
                        fullHouseTrick.addCard(this.listOfCards.get(i + 2));
                        fullHouseTrick.addCard(pairTrick.listOfCards);

                        //ensures that only pure fullHouse included, ie, fullHouse which are flush not included
                        //because we are assuming single deck play
                        if (fullHouseTrick.trickType() == Trick.TrickType.FULLHOUSE) {
                            trickList.add(fullHouseTrick);
                        }

                        if (i < size - 3 && this.listOfCards.get(i).rank() == this.listOfCards.get(i + 3).rank()) {
                            fullHouseTrick = new Trick();
                            fullHouseTrick.addCard(this.listOfCards.get(i));
                            fullHouseTrick.addCard(this.listOfCards.get(i + 1));
                            fullHouseTrick.addCard(this.listOfCards.get(i + 3));
                            fullHouseTrick.addCard(pairTrick.listOfCards);

                            //ensures that only pure fullHouse included, ie, fullHouse which are flush not included
                            //because we are assuming single deck play
                            if (fullHouseTrick.trickType() == Trick.TrickType.FULLHOUSE) {
                                trickList.add(fullHouseTrick);
                            }
                            fullHouseTrick = new Trick();
                            fullHouseTrick.addCard(this.listOfCards.get(i));
                            fullHouseTrick.addCard(this.listOfCards.get(i + 2));
                            fullHouseTrick.addCard(this.listOfCards.get(i + 3));
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
     * @return an arraylist of all possible four of a kind Tricks from the cards in hand
     */
    public ArrayList<Trick> getFourOfAKindTricks() {
        ArrayList<Trick> trickList = new ArrayList<Trick>();

        //if hand is not sorted, sort it
        if (!this.isSorted()) {
        	this.sort();
        }
        int size = this.size();
        if (size >= 5) {
            for (int i = 0; i < size - 3; i++) {
                if (this.listOfCards.get(i).rank() == this.listOfCards.get(i + 3).rank()) {

                    for (int j = 0; j < size; j++) {
                        Trick fourOfAKindTrick = new Trick();
                        if (j < i || j > i + 3) {
                            fourOfAKindTrick.addCard(this.listOfCards.get(i));
                            fourOfAKindTrick.addCard(this.listOfCards.get(i + 1));
                            fourOfAKindTrick.addCard(this.listOfCards.get(i + 2));
                            fourOfAKindTrick.addCard(this.listOfCards.get(i + 3));
                            fourOfAKindTrick.addCard(this.listOfCards.get(j));

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
     * @return an arraylist of all possible straight flush Tricks from the cards in hand
     */
    public ArrayList<Trick> getStraightFlushTricks() {
        ArrayList<Trick> trickList = new ArrayList<Trick>();
        //if hand is not sorted, sort it
        if (!this.isSorted()) {
        	this.sort();
        }
        Hand spades = new Hand();
        Hand hearts = new Hand();
        Hand clubs = new Hand();
        Hand diamonds = new Hand();
        //put the cards into different hands based on same suits
        for (Card card : this.listOfCards) {
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
    
    //returns all possible legal tricks that can be formed by this hand (not necessarily wellformed)
    // merged into getLegalTricks
/*    @SuppressWarnings("fallthrough")
    public ArrayList<Trick> getPossibleTrick( Trick.TrickType currentTrickType ) {
    	
        ArrayList<Trick> trickArray = new ArrayList<Trick>();

        switch (currentTrickType) {
            case SINGLE:
                for (Trick trick : this.getSingleTricks()) {
                    trickArray.add(trick);
                }
                return trickArray;
            case PAIR:
                for (Trick trick : this.getPairTricks()) {
                    trickArray.add(trick);
                }
                return trickArray;
            case STRAIGHTFLUSH:
                for (Trick trick : this.getStraightFlushTricks()) {
                    trickArray.add(trick);
                }
            case FOUROFAKIND:
                for (Trick trick : this.getFourOfAKindTricks()) {
                    trickArray.add(trick);
                }
            case FULLHOUSE:
                for (Trick trick : this.getFullHouseTricks()) {
                    trickArray.add(trick);
                }
            case FLUSH:
                for (Trick trick : this.getFlushTricks()) {
                    trickArray.add(trick);
                }
            case STRAIGHT:
                for (Trick trick : this.getStraightTricks()) {
                    trickArray.add(trick);
                }
                return trickArray;
            case PASS:
                return this.getAllTricks();
            default:
                //throw new Exception("Something wrong here!");
                return null;//error here
        }
    }*/
    
    /**
     * Returns an ArrayList of Tricks playable that beats currTrick
     * 
     * @param currTrick Trick to beat 
     * @return an ArrayList of playable tricks
     */
    public ArrayList<Trick> getLegalTricks(Trick currTrick) {
    	ArrayList<Trick> myPossibleTricks;
    	
    	// get appropriate types of tricks (wrt currTrick)
        if (Controller.historyOfTricks.size() == 0 && currTrick == null) { // this is first player so return all legal tricks possible
        	myPossibleTricks = this.getAllTricks();
        } else {
	        switch (currTrick.trickType()) {
	            case SINGLE: 		myPossibleTricks = getSingleTricks(); break;
	            case PAIR: 			myPossibleTricks = getPairTricks(); break;
	            case STRAIGHTFLUSH: myPossibleTricks = getStraightFlushTricks(); break;
	            case FOUROFAKIND: 	myPossibleTricks = getFourOfAKindTricks(); break;
	            case FULLHOUSE: 	myPossibleTricks = getFullHouseTricks(); break;
	            case FLUSH: 		myPossibleTricks = getFlushTricks(); break;
	            case STRAIGHT: 		myPossibleTricks = getStraightTricks(); break;
	            case PASS: 			myPossibleTricks = getAllTricks(); break;
	            default: 			myPossibleTricks = null;//error here
	        }
        }

        //System.out.println("same type tricks: " + myPossibleTricks);
        ArrayList<Trick> myValidTricks = new ArrayList<Trick>();

        //all tricks which are not well-formed are removed (ie it beats currTrick therefore a legal move)
        for (Trick trick : myPossibleTricks) {
            if (Controller.isWellFormed(trick, currTrick)) {
                myValidTricks.add(trick);
            }
        }

        Trick trickOut = new Trick(); //empty/pass trick
        if (Controller.historyOfTricks.size() != 0) {//if not the first player's first move, allow pass
            myValidTricks.add(trickOut);
        }

        //System.out.println("valid tricks: " + myValidTricks);
        return myValidTricks;
    }
    
    /**
     * Checks whether there is exactly one non pass valid trick left
     * valid tricks are assumed to be optained using getPlayableTricks
     * 
     * @param myValidTricks
     * @return
     */
    public static boolean lastTrickLeft( ArrayList<Trick> myValidTricks ) {
    	boolean passTrickExists = false;
    	for (Trick trick : myValidTricks) {
    		if ( trick.trickType() == Trick.TrickType.PASS )
    			passTrickExists = true;
    	}
    	if ( passTrickExists )  // assume pass is added at the back
    		return (myValidTricks.size() == 2);
    	else
    		return (myValidTricks.size() == 1);
    }
}


