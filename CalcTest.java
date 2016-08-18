/**
 * Created by Noam on 7/29/2016.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class CalcTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void deck_print_test()
    {
        String expected_string =
                "0) 2 of spades\n" +
                "1) 3 of spades\n" +
                "2) 4 of spades\n" +
                "3) 5 of spades\n" +
                "4) 6 of spades\n" +
                "5) 7 of spades\n" +
                "6) 8 of spades\n" +
                "7) 9 of spades\n" +
                "8) 10 of spades\n" +
                "9) Jack of spades\n" +
                "10) Queen of spades\n" +
                "11) King of spades\n" +
                "12) Ace of spades\n" +
                "13) 2 of hearts\n" +
                "14) 3 of hearts\n" +
                "15) 4 of hearts\n" +
                "16) 5 of hearts\n" +
                "17) 6 of hearts\n" +
                "18) 7 of hearts\n" +
                "19) 8 of hearts\n" +
                "20) 9 of hearts\n" +
                "21) 10 of hearts\n" +
                "22) Jack of hearts\n" +
                "23) Queen of hearts\n" +
                "24) King of hearts\n" +
                "25) Ace of hearts\n" +
                "26) 2 of diamonds\n" +
                "27) 3 of diamonds\n" +
                "28) 4 of diamonds\n" +
                "29) 5 of diamonds\n" +
                "30) 6 of diamonds\n" +
                "31) 7 of diamonds\n" +
                "32) 8 of diamonds\n" +
                "33) 9 of diamonds\n" +
                "34) 10 of diamonds\n" +
                "35) Jack of diamonds\n" +
                "36) Queen of diamonds\n" +
                "37) King of diamonds\n" +
                "38) Ace of diamonds\n" +
                "39) 2 of clubs\n" +
                "40) 3 of clubs\n" +
                "41) 4 of clubs\n" +
                "42) 5 of clubs\n" +
                "43) 6 of clubs\n" +
                "44) 7 of clubs\n" +
                "45) 8 of clubs\n" +
                "46) 9 of clubs\n" +
                "47) 10 of clubs\n" +
                "48) Jack of clubs\n" +
                "49) Queen of clubs\n" +
                "50) King of clubs\n" +
                "51) Ace of clubs\n";
        for (Card c : Deck.getDeck().getCards())
            c.displayCard();
        assertEquals(expected_string, outContent.toString());
    }

    @Test
    public void two_hands_test_1() {
        String test_str =
                "14 2 13 4\n" + "2 3 7 1\n\n";
                //A of Hearts, King of Clubs
                //2 of Diamonds, 7 of Spades
        String expected_string =
                "Enter a hand: \n" +
                "Enter a hand: \n" +
                "Enter a hand: \n" +
                "Hand number 1:\n" +
                "First card: \n" +
                "25) Ace of hearts\n" +
                "Second card: \n" +
                "50) King of clubs\n" +
                "Hand number 2:\n" +
                "First card: \n" +
                "26) 2 of diamonds\n" +
                "Second card: \n" +
                "5) 7 of spades\n";
        PokerCalc.initialize_hands(new Scanner(test_str));
        assertEquals(expected_string, outContent.toString());
    }
}

