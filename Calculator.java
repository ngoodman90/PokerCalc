import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by Noam on 7/26/2016.
 */
public class Calculator {

    private static final int MAX_HANDS = 9;

    public static void main(String[] args)
    {
        initialize_hands(new Scanner(System.in));
        calculate_odds();
    }

    public static void initialize_hands(Scanner reader)
    {
        Hand[] hands = new Hand[9];
        boolean no_more_hands = false;
        int hand_counter = 0;
        while (!no_more_hands) {
            System.out.println("Enter a hand: ");
            String hand_string = reader.nextLine(); // Scans the next token of the input as an int.
            if (hand_string.equals(""))
                no_more_hands = true;
            else
            {
                if (hand_counter <= MAX_HANDS)
                    hands[hand_counter++] = parseHand(hand_string);
                else
                {
                    System.out.println("No more hands allowed\n");
                    break;
                }
            }
        }
        displayHands(hand_counter, hands);
    }

    public static void displayHands(int hand_counter, Hand[] hands)
    {
        for (int i = 0; i < hand_counter; i++)
        {
            System.out.printf("Hand number %d:\n", i + 1);
            hands[i].displayHand();
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

    private static void calculate_odds()
    {
        findPossibleHands();
        findBestHand();

    }

    private static void findBestHand() {

    }

    private static void findPossibleHands() {

    }

}
