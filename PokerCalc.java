/**
 * Created by Noam on 7/26/2016.
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.*;



public class PokerCalc {

    public static final Logger logger = Logger.getLogger(PokerCalc.class.getName());

    public static final int MAX_HANDS = 9;
    public static final int NUM_OF_CARDS_IN_SUIT = 13;
    public static final int NUM_OF_CARDS_IN_DECK = 52;

    public static void main(String[] args)
    {
        initialize_hands(new Scanner(System.in));
    }

    public static void initialize_hands(Scanner reader)
    {
        logger.setLevel(Level.INFO);
        logger.log(Level.INFO, "Initializing hands");

        ArrayList<Hand> hands = new ArrayList<>();
        while (true) {
            System.out.println("Enter a hand:");
            String hand_string = reader.nextLine();
            if (hand_string.equals(""))
                break;
            else
            {
                if (hands.size() <= MAX_HANDS)
                    hands.add(parseHand(hand_string));
                else
                {
                    System.out.println("No more hands allowed\n");
                    break;
                }
            }
        }
        displayHands(hands);
        logger.log(Level.INFO, "Number of hands: %d", hands.size());
        Table.getTable().startCalculation(hands);
    }

    public static void displayHands(ArrayList<Hand> hands)
    {
        for (int i = 0; i < hands.size(); i++)
        {
            System.out.printf("Hand number %d:\n", i + 1);
            hands.get(i).displayHand();
        }
    }

    public static Hand parseHand(String s)
    {
        Scanner hand_scanner = new Scanner(s);
        /*
        * first int in line is the first value of the first card,
        * second int in line is the suit of the first card.
        * 1 = spade
        * 2 = heart
        * 3 = diamond
        * 4 = club
        * Second card is parsed in similar fashion.
        */
        int first_card = userCardNumber(hand_scanner.nextInt(), hand_scanner.nextInt());
        int second_card = userCardNumber(hand_scanner.nextInt(), hand_scanner.nextInt());
        return new Hand(Deck.getDeck().getCard(first_card), Deck.getDeck().getCard(second_card));
    }

    public static int userCardNumber(int val, int suit)
    {
        Deck.getDeck().getCard(((suit - 1) * 13) +  (val - 2)).setUsed(true);
        return (((suit - 1) * 13) +  (val - 2));
    }
}
