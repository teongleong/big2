//for my testing purposes
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tay Chit Chin, Lee Chun Kit, Chuah Teong Leong
 */
public class Test {

    public static void main(String args[]) {

        try {
//int k=0;
//            for(int i =0;i<100;i++){
//System.out.println((++k)%4);
//            }
//            
//            Player[] player = new Player[4];
//            player[0] = new ComputerPlayer();
//            player[1] = new ComputerPlayer();
//            player[2] = new ComputerPlayer();
//            player[3] = new ComputerPlayer();
//
//            //make shoe
//            Shoe shoe = new Shoe();
//            //shuffle shoe
//            shoe.shuffle();
//            if (Controller.debug == Controller.Debug.DEBUG) {
//                System.out.println("Shoe cards: " + shoe);
//            }
//            //distribute cards to all players
//            int i = 0;
//            while (shoe.hasCard()) {
//                i %= 4;
//                player[i].addCard(shoe.drawCard());
//                i++;
//            }
//
//            System.out.println(player[0]);
//            System.out.println(player[1]);
//            System.out.println(player[2]);
//            System.out.println(player[3]);


//            System.out.print("[00, 01, 02, 03, 04, 05, 06, 07, 08, 09,");
//            for (int i = 10; i <= 51; i++) {
//                System.out.print(" " + i + ",");
//            }
//            System.out.println("]");
//            System.out.println(Card.deck());
//            Player newbie = new ComputerPlayer();
//            //newbie.addCard(Card.deck());
//            newbie.addCard(Card.deck().get(0));
//            newbie.addCard(Card.deck().get(4));
//            newbie.addCard(Card.deck().get(8));
//            newbie.addCard(Card.deck().get(12));
//            newbie.addCard(Card.deck().get(16));
//            newbie.addCard(Card.deck().get(20));
////            newbie.addCard(Card.deck().get(6));
////            newbie.addCard(Card.deck().get(7));
////            newbie.addCard(Card.deck().get(8));
////            newbie.addCard(Card.deck().get(9));
////            newbie.addCard(Card.deck().get(10));
////            newbie.addCard(Card.deck().get(11));
////            newbie.addCard(Card.deck().get(12));
//
//            System.out.println(newbie);

//            ArrayList<Trick> flushTricks = ComputerPlayer.getFlushTricks(newbie.myHand);
//            System.out.println("flush:");
//            System.out.println(flushTricks);
//
//            ArrayList<Trick> fourOfAKindTricks = ComputerPlayer.getFourOfAKindTricks(newbie.myHand);
//            System.out.println("fourofakind:");
//            System.out.println(fourOfAKindTricks);
//
//            ArrayList<Trick> fullHouseTricks = ComputerPlayer.getFullHouseTricks(newbie.myHand);
//            System.out.println("fullhouse:");
//            System.out.println(fullHouseTricks);
//
//            ArrayList<Trick> pairTricks = ComputerPlayer.getPairTricks(newbie.myHand);
//            System.out.println("pair:");
//            System.out.println(pairTricks);
//
//            ArrayList<Trick> singleTricks = ComputerPlayer.getSingleTricks(newbie.myHand);
//            System.out.println("single:");
//            System.out.println(singleTricks);
//
//            ArrayList<Trick> straightTricks = ComputerPlayer.getStraightTricks(newbie.myHand);
//            System.out.println("straight:");
//            System.out.println(straightTricks);
//
//            ArrayList<Trick> straightFlushTricks = ComputerPlayer.getStraightFlushTricks(newbie.myHand);
//            System.out.println("straightflush:");
//            System.out.println(straightFlushTricks);

//[[3D, 3H], [KC, KS], [QD, QH]]

//            Card kc = new Card('K', 'C');
//            Card ks = new Card('K', 'S');
//            Card qd = new Card('Q', 'D');
//            Card qh = new Card('Q', 'H');
//
//            Trick a = new Trick();
//            Trick b = new Trick();
//
//            a.addCard(kc);
//            a.addCard(ks);
//            b.addCard(qd);
//            b.addCard(qh);
//
//System.out.println(a.trickType());
//System.out.println(b.trickType());
//System.out.println(a.compareTo(b));

//        [[3D, 4D, 6D, 8D, TD], [3H, 5H, TH, QH, 2H], [7S, 8C, 9H, TS, JH]]

//            Card a = new Card('3', 'H');
//            Card b = new Card('5', 'H');
//            Card c = new Card('T', 'H');
//            Card d = new Card('Q', 'H');
//            Card e = new Card('2', 'H');
//            Card f = new Card('7', 'S');
//            Card g = new Card('8', 'C');
//            Card h = new Card('9', 'H');
//            Card i = new Card('T', 'S');
//            Card j = new Card('J', 'H');
//
//            Trick k = new Trick();
//            Trick l = new Trick();
//            k.addCard(a);
//            k.addCard(b);
//            k.addCard(c);
//            k.addCard(d);
//            k.addCard(e);
//            System.out.println(k.trickType());
//            l.addCard(f);
//            l.addCard(g);
//            l.addCard(h);
//            l.addCard(i);
//            l.addCard(j);
//            System.out.println(l.trickType());
//            System.out.println(k.compareTo(l));


//            //fill a computer player with a deck of cards
//            Player com = new ComputerPlayer();
//            com.addCard(Card.deck());
//            //before using get*Trick make sure hand is sorted
//            if (!com.isSorted()) {
//                com.sort();
//            }
//            sop(com);
//
//            //should return 52
//            ArrayList<Trick> singleTrickList = ComputerPlayer.getSingleTricks(com.myHand);
//            sop(singleTrickList);
//            sop(singleTrickList.size());
//            //should return 78
//            ArrayList<Trick> pairTrickList = ComputerPlayer.getPairTricks(com.myHand);
//            sop(pairTrickList);
//            sop(pairTrickList.size());
//            //should return 5108
//            ArrayList<Trick> pairFlushList = ComputerPlayer.getFlushTricks(com.myHand);
//            sop(pairFlushList);
//            sop(pairFlushList.size());
//            //should return 624
//            ArrayList<Trick> pairFourOfAKindList = ComputerPlayer.getFourOfAKindTricks(com.myHand);
//            sop(pairFourOfAKindList);
//            sop(pairFourOfAKindList.size());
//            //should return 3744
//            ArrayList<Trick> pairFullHouseList = ComputerPlayer.getFullHouseTricks(com.myHand);
//            sop(pairFullHouseList);
//            sop(pairFullHouseList.size());
//            //should return 40
//            ArrayList<Trick> pairStraightFlushList = ComputerPlayer.getStraightFlushTricks(com.myHand);
//            sop(pairStraightFlushList);
//            sop(pairStraightFlushList.size());
//            //works, but inefficient
//            //should return 10200
//            ArrayList<Trick> pairStraightList = ComputerPlayer.getStraightTricks(com.myHand);
//            sop(pairStraightList);
//            sop(pairStraightList.size());

        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void sop(Object o) {
        System.out.println(o.toString());
    }
}
