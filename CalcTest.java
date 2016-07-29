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
    public void testAdd() {
        String test_str =
                "14 2 13 4\n" + "2 3 7 1\n\n";
        String expected_string =
                "Enter a hand: \n" +
                "Enter a hand: \n" +
                "Enter a hand: \n" +
                "Hand number 1:\n" +
                "First card: \n" +
                "Ace of hearts\n" +
                "Second card: \n" +
                "King of clubs\n" +
                "Hand number 2:\n" +
                "First card: \n" +
                "2 of diamonds\n" +
                "Second card: \n" +
                "7 of spades\n";
        Calculator.initialize_hands(new Scanner(test_str));
        assertEquals(expected_string, outContent.toString());
    }
}

