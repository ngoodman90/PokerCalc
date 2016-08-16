/**
 * Created by Noam on 7/26/2016.
 */

import java.util.ArrayList;
import java.util.Scanner;

public class PokerCalc {

    private static final int MAX_HANDS = 9;

    public static void main(String[] args)
    {
        initialize_hands(new Scanner(System.in));
    }

    public static void initialize_hands(Scanner reader)
    {
        ArrayList<Hand> hands = new ArrayList<>();
        while (true) {
            System.out.println("Enter a hand: ");
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
        Card first_card = cardCreator(hand_scanner.nextInt(), hand_scanner.nextInt());
        Card second_card = cardCreator(hand_scanner.nextInt(), hand_scanner.nextInt());
        return new Hand(first_card, second_card);
    }

    public static Card cardCreator(int val, int suit)
    {
        Deck.getDeck().getCards()[((suit - 1) * 13) +  (val - 2)].setUsed(true);
        return Deck.getDeck().getCards()[((suit - 1) * 13) +  (val - 2)];
    }
}
