
import java.util.ArrayList;

/**
 *
 * @author Tay Chit Chin, Lee Chun Kit, Chuah Teong Leong
 */
public class Trick extends ListOfCards implements Comparable<Trick> {

    public static enum TrickType {

        SINGLE(-1),
        PAIR(-1),
        STRAIGHT(1),
        FLUSH(2),
        FULLHOUSE(3),
        FOUROFAKIND(4),
        STRAIGHTFLUSH(5),
        INVALID(-1), //not a proper trick
        PASS(0); //means an empty trick or a pass
        private int value;

        private TrickType(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }
    private TrickType trickType = TrickType.INVALID;

    public Trick() {
        super();
        evaluateTrickType();
    }

    public Trick(ArrayList<Card> listOfCards) {
        super(listOfCards);
        evaluateTrickType();
    }

    public Trick(Card card) {
        super(card);
        evaluateTrickType();
    }

    @Override
    protected void addCard(Card card) {
        super.addCard(card);
        evaluateTrickType();
    }

    @Override
    protected void addCard(ArrayList<Card> cards) {
        super.addCard(cards);
        evaluateTrickType();
    }

    /**
     * this applies only to a SINGLE-deck game of big two; only tricks of the
     * same size can be compared; positive/negative means left/right is bigger 
     * than right/left; actually it's not to have a value of 0 for SINGLE-deck 
     * game as there won't be ties.<!-- --> Remember to sort the tricks.
     * 
     * @param specifiedTrick
     * @return
     */
    public int compareTo(Trick specifiedTrick) {
        if (//tricks are of different size
                this.listOfCards.size() != specifiedTrick.listOfCards.size() ||
                //tricks are not valid
                this.trickType == TrickType.INVALID ||
                specifiedTrick.trickType == TrickType.INVALID) {
            throw new UnsupportedOperationException("Does not support comparing tricks of different length or non-valid tricks.");
        } else if (//single tricks
                this.trickType == TrickType.SINGLE) {
            return this.listOfCards.get(0).compareTo(specifiedTrick.listOfCards.get(0));
        } else if (//pair tricks
                this.trickType == TrickType.PAIR) {
            if (!this.listOfCards.get(1).equals(specifiedTrick.listOfCards.get(1))) {
                return this.listOfCards.get(1).compareTo(specifiedTrick.listOfCards.get(1));
            } else {
                return this.listOfCards.get(0).compareTo(specifiedTrick.listOfCards.get(0));
            }
        } else //trick of five
        //  straight < flush < full house < four of a kind < straight flush
        {
            int difference = this.trickType.value - specifiedTrick.trickType.value;
            if (difference != 0) {
                return difference;
            } else if (//full house or four of a kind; compare 3rd of sorted cards
                    this.trickType == TrickType.FOUROFAKIND || this.trickType == TrickType.FULLHOUSE) {
                return this.listOfCards.get(2).compareTo(specifiedTrick.listOfCards.get(2));
            } else if (//flush; compare rank of highest card, followed by suit; compare 5th of sorted cards
                    this.trickType == TrickType.FLUSH) {
                return this.listOfCards.get(4).compareTo(specifiedTrick.listOfCards.get(4));
            } else {//straight and straightflush comparison
                //2-3-4-5-6 > 10-J-Q-K-A > However, A-2-3-4-5 is lowest possible straight for strategic purposes.

                //test for A-2-3-4-5
                if (//this trick is smallest straight
                        this.listOfCards.get(3).rank().representationOfRank() == 'A' &&
                        this.listOfCards.get(4).rank().representationOfRank() == 'T') {
                    if (//specified trick is also smallest straight; compare 3rd of sorted cards
                            specifiedTrick.listOfCards.get(3).rank().representationOfRank() == 'A' &&
                            specifiedTrick.listOfCards.get(4).rank().representationOfRank() == 'T') {
                        return this.listOfCards.get(2).compareTo(specifiedTrick.listOfCards.get(2));
                    } else {//specified trick is bigger
                        return -1;
                    }
                } else if (//specified trick is also smallest straight but this trick is not
                        specifiedTrick.listOfCards.get(3).rank().representationOfRank() == 'A' &&
                        specifiedTrick.listOfCards.get(4).rank().representationOfRank() == 'T') {
                    return 1;
                }

                //return comparison of largest card provided neither trick is A-2-3-4-5
                return this.listOfCards.get(4).compareTo(specifiedTrick.listOfCards.get(4));

//                return 0;

            }
        }
//        return 0;
    //stub
    }

//always evaluate trick type whenever you add/remove cards
    private void evaluateTrickType() {

        int trickLength = listOfCards.size();

        switch (trickLength) {
            case 0:
                if (Controller.debug == Controller.Debug.DEBUG) {
                    sop("Is a pass/empty trick.");
                }
                trickType = TrickType.PASS;
                break;
            case 1: //single card always valid
                if (Controller.debug == Controller.Debug.DEBUG) {
                    sop("Is a single card");
                }
                trickType = TrickType.SINGLE;
                break;
            case 2: //double card valid if pair (rank)
                if (listOfCards.get(0).rank() == listOfCards.get(1).rank()) {
                    if (Controller.debug == Controller.Debug.DEBUG) {
                        sop("Is a pair");
                    }
                    trickType = TrickType.PAIR;
                } else {
                    if (Controller.debug == Controller.Debug.DEBUG) {
                        sop("Is a double but not a pair");
                    }
                    trickType = TrickType.INVALID;
                }
                break;
            case 5: //straight < flush < full house < four of a kind < straight flush
//                aces  2 3 4 5 lowest straight
                trickType = typeOfFive();
                if (Controller.debug == Controller.Debug.DEBUG) {
                    if (trickType != TrickType.INVALID) {
                        sop("Is a proper trick of five cards");
                        sop("to be exact it is a: " + trickType.toString());
                    } else {
                        sop("Is five cards but not a proper trick");
                    }
                }
                break;
            default: //trick is neither a pass/empty trick, single, double or five
                trickType = TrickType.INVALID;
                if (Controller.debug == Controller.Debug.DEBUG) {
                    sop("Is neither a proper trick nor five cards");
                }
                break;
        }
    }

    public TrickType trickType() {
        return trickType;
    }

//must be trick of length 5
    private TrickType typeOfFive() {

        //start by sorting so subsequent algorithm for testing type is 
        //based on sorted cards.
        if (!isSorted()) {
            sort();
        }

        boolean straight = false;
        boolean flush = false;
        boolean fullHouse = false;
        boolean fourOfAKind = false;

        //  straight < flush < full house < four of a kind < straight flush

        //if 1,4th same or 2/3rd same, fourOfAKind
        if ((listOfCards.get(0).rank() == listOfCards.get(3).rank()) ||
                (listOfCards.get(1).rank() == listOfCards.get(4).rank())) {
            fourOfAKind = true;
        //if 1,3rd same & 4,5th same or 1,2nd & 3,5th same, fullHouse
        } else if (((listOfCards.get(0).rank() == listOfCards.get(2).rank()) &&
                (listOfCards.get(3).rank() == listOfCards.get(4).rank())) ||
                ((listOfCards.get(0).rank() == listOfCards.get(1).rank()) &&
                (listOfCards.get(2).rank() == listOfCards.get(4).rank()))) {
            fullHouse = true;
        //if all same suits, flush
        } else if (listOfCards.get(0).suit() == listOfCards.get(1).suit() &&
                listOfCards.get(1).suit() == listOfCards.get(2).suit() &&
                listOfCards.get(2).suit() == listOfCards.get(3).suit() &&
                listOfCards.get(3).suit() == listOfCards.get(4).suit()) {
            flush = true;
        }
//implement test for straight here outside above if-else-if as it is 
//independant from flush.
//special cases are 345|A2 and 3456|2 with values of:
//3,4,5,14,15 and 3,4,5,6,15 respectively.
        int a = listOfCards.get(0).rank().value();
        int b = listOfCards.get(1).rank().value();
        int c = listOfCards.get(2).rank().value();
        int d = listOfCards.get(3).rank().value();
        int e = listOfCards.get(4).rank().value();
//        sop(a);
//        sop(b);
//        sop(c);
//        sop(d);
//        sop(e);

        if (//1st three cards +1 sequentially
                ((a + 1) == b && (b + 1) == c) &&
                (//all cards +1 sequentially, exclude J-Q-K-A-2 with e != 15
                ((c + 1) == d && (d + 1) == e && e != 15) ||
                //3,4,5,14,15 -- 345|A2
                //3,4,5,6,15 -- 3456|2
                ((c == 5) && (e == 15)) &&
                ((d == 14) || (d == 6)))) {
            straight = true;
        }

//i dont care i just like to use multiple returns =P
        if (straight && flush) {
            return TrickType.STRAIGHTFLUSH;
        } else if (fourOfAKind) {
            return TrickType.FOUROFAKIND;
        } else if (fullHouse) {
            return TrickType.FULLHOUSE;
        } else if (flush) {
            return TrickType.FLUSH;
        } else if (straight) {
            return TrickType.STRAIGHT;
        } else {
            return TrickType.INVALID;
        }

//        return TrickType.INVALID;
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
