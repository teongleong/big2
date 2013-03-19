
import java.util.ArrayList;

/**
 *
 * @author Tay Chit Chin, Lee Chun Kit, Chuah Teong Leong
 */
public class Controller {

    /**
     * enum for debug
     */
    public static enum Debug {

        DEBUG, NO_DEBUG, DEBUG_FILE
    }
    /**
     * DEBUG for debug, NO_DEBUG otherwise
     */
    static Debug debug = Debug.NO_DEBUG;
    /**
     * default player names
     */
    static String[] playerNames = {"Ah Beng", "Ah Lian", "Ah Meng", "Ah Seng"};
    /**
     * array of players 0 1 2 3
     */
    public static Player[] player = new Player[4];
    /**
     * history of tricks that were played.
     */
    static ArrayList<Trick> historyOfTricks = new ArrayList<Trick>();
    
    static int threeDiamondPlayer = -1;

    /**
     * main method
     * 
     * @param args
     * @throws java.lang.Exception
     */
    public static void main(String args[]) throws Exception {

        if (debug == Debug.DEBUG) {
            System.out.println("Yes we're debugging!");
        }

        int temp;

        for (int i = 0; i < 4; i++) {
            player[i] = new FullPlayer(i);
        }


//        boolean[] isHumanPlayer = new boolean[4];//true means human player
//        Scanner scanner = new Scanner(System.in);
//        Scanner scanner2 = new Scanner(System.in);//coz weirdness of nextLine() affecting next() line 37, 41
//        //to catch exception in case inputMismatch i.e. not an int.
//        for (int i = 0; i < 4; i++) {
//            System.out.print("Player " + i + ", enter 0 for computer, 1 for human: ");
//            temp = scanner.nextInt();
//            isHumanPlayer[i] = (temp == 1) ? true : false;
//            if (isHumanPlayer[i]) {
//                System.out.print("Enter human player's name: ");
//                playerNames[i] = scanner2.nextLine();
//            }
//        }
//        //initialize players
//        for (int i = 0; i < 4; i++) {
//            if (isHumanPlayer[i]) {
//                player[i] = new HumanPlayer();
//            } else {
//                player[i] = new FullPlayer();
//            }
//        }


        //make shoe
        Shoe shoe = new Shoe();
        //shuffle shoe
        shoe.shuffle();
        if (debug == Debug.DEBUG) {
            System.out.println("Shoe cards: " + shoe);
        }
        //distribute cards to all players
        int i = 0;
        while (shoe.hasCard()) {
            i %= 4;
            Card card = shoe.drawCard();
//            System.out.println(card);
            player[i].addCard(card);
//            System.out.println(player[i]);
            i++;
        }

        //get player with Three Diamond
        Card threeDiamond = new Card('3', 'D');
        for (i = 0; i < 4; i++) {
            printPlayerCards(i);
        }
        for (i = 0; i < 4; i++) {
            if (player[i].hasThisCard(threeDiamond)) {
                threeDiamondPlayer = i;
                System.out.println("Player " + i + ", " + playerNames[i] + ", has " + threeDiamond.toStringLong() + ".");
                break;
            }
        }

        //start the game playing!!
        for (int j = threeDiamondPlayer; j < 4; j = ++j % 4) {
            System.out.println("");
            System.out.println("");
            System.out.println("Number of cards players 0,1,2,3 have: " + player[0].myHand.size() + "," + player[1].myHand.size() + "," + player[2].myHand.size() + "," + player[3].myHand.size());
            System.out.println("These are the played cards " + historyOfTricks);

            //for netbeans only; command prompt can comment this out
            int historySize = historyOfTricks.size();
            if (historySize >= 3) {
                System.out.println("Last 3 tricks: [" + historyOfTricks.get(historySize - 3) + "," + historyOfTricks.get(historySize - 2) + "," + historyOfTricks.get(historySize - 1) + "]");
            }
            //end for netbeans only   

            System.out.print("Your turn, ");
            printPlayerCards(j);
            historyOfTricks.add(player[j].getLegalTrick());
            System.out.println("Player " + j + ", " + playerNames[j] + " plays: " +
                    historyOfTricks.get(historyOfTricks.size() - 1));

            if (player[j].myHand.isEmpty()) {
                System.out.println("Congratulation player " + j + ", " + playerNames[j] +
                        ", you have won the game.");
                System.out.println("By the way, the player with diamond three is " + threeDiamondPlayer);
                System.out.println("Total number of turns taken is " + historyOfTricks.size());
                System.exit(0);
            }
        }
    }

    /**
     * Checks whether this specified trick is valid for this move.
     * 
     * @param trick the specified trick
     * @return true if this specified trick is well-formed for this move; 
     * false otherwise.
     */
    public static boolean isWellFormed(Trick trick) {

        int historySize = historyOfTricks.size();
        boolean isWellFormed = false;

        if (historyOfTricks.size() == 0) {
            if (trick.listOfCards.contains(new Card('3', 'D'))) {
                isWellFormed = true;//the first trick must contain three of diamonds
            }
        } else if (trick.trickType() == Trick.TrickType.PASS) {
            isWellFormed = true;//all players can pass at any time
        } else if (historySize >= 3 && historyOfTricks.get(historySize - 1).trickType() == Trick.TrickType.PASS &&
                historyOfTricks.get(historySize - 2).trickType() == Trick.TrickType.PASS &&
                historyOfTricks.get(historySize - 3).trickType() == Trick.TrickType.PASS) {
            isWellFormed = true;//previous three tricks played were passes
        } else if (trick.size() == historyOfTricks.get(historySize - 1).size()) {
            if (trick.compareTo(historyOfTricks.get(historySize - 1)) >= 0) {
                isWellFormed = true;//tricks must be same as number of cards as previous non-pass trick
            }
        } else if (historySize >= 2 && historyOfTricks.get(historySize - 1).trickType() == Trick.TrickType.PASS &&
                trick.size() == historyOfTricks.get(historySize - 2).size()) {
            if (trick.compareTo(historyOfTricks.get(historySize - 2)) >= 0) {
                isWellFormed = true;//check with most recent non-pass trick
            }
        } else if (historySize >= 3 && historyOfTricks.get(historySize - 1).trickType() == Trick.TrickType.PASS &&
                historyOfTricks.get(historySize - 2).trickType() == Trick.TrickType.PASS &&
                trick.size() == historyOfTricks.get(historySize - 3).size()) {
            if (trick.compareTo(historyOfTricks.get(historySize - 3)) >= 0) {
                isWellFormed = true;//check with most recent non-pass trick
            }
        }
        return isWellFormed;
    }

    /**
     * Checks whether this specified trick is valid for this move.
     * 
     * @param trick the specified trick
     * @param currentTrick the last non-pass trick, or a pass if there were at least three consecutive passes, or null if history of tricks is size==0
     * @return true if this specified trick is well-formed comparing to the currentTrick;
     * false otherwise.
     */
    public static boolean isWellFormed(Trick trick, Trick currentTrick) {

        int historySize = historyOfTricks.size();
        boolean isWellFormed = false;

        if (currentTrick == null) {
            if (trick.listOfCards.contains(new Card('3', 'D'))) {
                isWellFormed = true;//the first trick must contain three of diamonds
            }
        } else if (trick.trickType() == Trick.TrickType.PASS) {
            isWellFormed = true;//all players can pass at any time
        } else if (currentTrick.trickType() == Trick.TrickType.PASS) {
            isWellFormed = true;//previous three tricks played were passes
        } else if (trick.size() == currentTrick.size()) {
            if (trick.compareTo(currentTrick) >= 0) {
                isWellFormed = true;//tricks must be same as number of cards as previous non-pass trick
            }
        }
        return isWellFormed;
    }

    public static Trick getCurrentTrick() {
        int historySize = Controller.historyOfTricks.size();
        //should not happen because computerplayer should implement
        //to check if it has 3D which means it's the first player so no previous trick
        if (historySize == 0) {
            //throw new Exception("No previous tricks");
//            System.out.println("history of tricks == 0");
            return null;//will throw exception here
        } else if (historySize == 1) {
            //historysize is 1, it cannot be a pass so return this trick
            return Controller.historyOfTricks.get(historySize - 1);
        } else if (historySize == 2) {
            //historysize is 2, if latest is pass, return first trick in history
            if (Controller.historyOfTricks.get(historySize - 1).trickType() != Trick.TrickType.PASS) {
                return Controller.historyOfTricks.get(historySize - 1);
            } else {
                return Controller.historyOfTricks.get(historySize - 2);
            }
        } else {//historySize >= 3
            if (Controller.historyOfTricks.get(historySize - 1).trickType() != Trick.TrickType.PASS) {
                return Controller.historyOfTricks.get(historySize - 1);
            } else if (Controller.historyOfTricks.get(historySize - 2).trickType() != Trick.TrickType.PASS) {
                return Controller.historyOfTricks.get(historySize - 2);
            } else if (Controller.historyOfTricks.get(historySize - 3).trickType() != Trick.TrickType.PASS) {
                return Controller.historyOfTricks.get(historySize - 3);
            } else {
                return new Trick();//an empty trick of tricktype pass
            }
        }
    }

    /**
     * Prints the cards which player i has.
     * 
     * @param playerNumber the player number.
     */
    public static void printPlayerCards(int playerNumber) {
        System.out.println("Player " + playerNumber + ", " + playerNames[playerNumber] + ": " + player[playerNumber] + " size: " + player[playerNumber].myHand.size());
    }
}
