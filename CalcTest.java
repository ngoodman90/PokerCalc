/**
 * Created by Noam on 7/29/2016.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class CalcTest {

    private final Deck deck = Deck.getDeck();

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
        Deck.getDeck().getCards().forEach(Card::displayCard);
        assertEquals(expected_string, outContent.toString());
    }

    @Test
    public void pairTest1() {
        int[] cards = {0, 1, 2 ,3, 22, 35, 50};
        for (int i = 0; i < cards.length; i++)
            deck.getCard(cards[i]).displayCard();
        int ans = Table.handValue(cards);
        assertEquals(2, ans);
    }

    @Test
    public void pairTest2() {
        int[] cards = {0, 10, 23, 30, 38, 40, 50};
        for (int i = 0; i < cards.length; i++)
            deck.getCard(cards[i]).displayCard();
        int ans = Table.handValue(cards);
        assertEquals(2, ans);
    }
    @Test
    public void twoPairTest1() {
        int[] cards = {10, 11, 12, 15, 28, 31, 44};
        for (int i = 0; i < cards.length; i++)
            deck.getCard(cards[i]).displayCard();
        int ans = Table.handValue(cards);
        assertEquals(3, ans);
    }
    @Test
    public void twoPairTest2() {
        int[] cards = {7, 19, 32, 31, 38, 41, 44};
        for (int i = 0; i < cards.length; i++)
            deck.getCard(cards[i]).displayCard();
        int ans = Table.handValue(cards);
        assertEquals(3, ans);
    }
    @Test
    public void threeOfAKindTest1() {
        int[] cards = {12, 25, 38, 45, 46, 47, 48};
        for (int i = 0; i < cards.length; i++)
            deck.getCard(cards[i]).displayCard();
        int ans = Table.handValue(cards);
        assertEquals(4, ans);
    }
    @Test
    public void threeOfAKindTest2() {
        int[] cards = {6, 19, 32, 35, 38, 41, 44};
        for (int i = 0; i < cards.length; i++)
            deck.getCard(cards[i]).displayCard();
        int ans = Table.handValue(cards);
        assertEquals(4, ans);
    }
    @Test
    public void threeOfAKindTest3() {
        int[] cards = {6, 19, 32, 35, 38, 41, 44};
        for (int i = 0; i < cards.length; i++)
            deck.getCard(cards[i]).displayCard();
        int ans = Table.handValue(cards);
        assertEquals(4, ans);
    }
    @Test
    public void fullHouseTest1() {
        int[] cards = {0, 13, 26, 6, 19, 27, 34};
        for (int i = 0; i < cards.length; i++)
            deck.getCard(cards[i]).displayCard();
        int ans = Table.handValue(cards);
        assertEquals(ans, 7);
    }
    @Test
    public void fullHouseTest2() {
        int[] cards = {7, 20, 33, 16, 42, 43, 1};
        for (int i = 0; i < cards.length; i++)
            deck.getCard(cards[i]).displayCard();
        int ans = Table.handValue(cards);
        assertEquals(ans, 7);
    }@Test
    public void fourOfAKindTest1() {
        int[] cards = {1, 14, 27, 40, 22, 37, 46};
        for (int i = 0; i < cards.length; i++)
            deck.getCard(cards[i]).displayCard();
        int ans = Table.handValue(cards);
        assertEquals(ans, 8);
    }
    @Test
    public void fourOfAKindTest2() {
        int[] cards = {12, 25, 38, 51, 1, 7, 29};
        for (int i = 0; i < cards.length; i++)
            deck.getCard(cards[i]).displayCard();
        int ans = Table.handValue(cards);
        assertEquals(8, ans);
    }
    @Test
    public void straightTest1() {
        int[] cards = {3, 30, 12, 44, 29, 19, 46};
        for (int i = 0; i < cards.length; i++)
            deck.getCard(cards[i]).displayCard();
        int ans = Table.handValue(cards);
        assertEquals(5, ans);
    }
    @Test
    public void straightTest2() {
        int[] cards = {51, 13, 25, 42, 2, 40, 1};
        for (int i = 0; i < cards.length; i++)
            deck.getCard(cards[i]).displayCard();
        int ans = Table.handValue(cards);
        assertEquals(5, ans);
    }
    @Test
    public void flushTest1() {
        int[] cards = {13, 16, 19, 0, 3, 21, 22};
        for (int i = 0; i < cards.length; i++)
            deck.getCard(cards[i]).displayCard();
        int ans = Table.handValue(cards);
        assertEquals(6, ans);
    }
    @Test
    public void flushTest2() {
        int[] cards = {35, 30, 31, 10, 50, 26, 28};
        for (int i = 0; i < cards.length; i++)
            deck.getCard(cards[i]).displayCard();
        int ans = Table.handValue(cards);
        assertEquals(6, ans);
    }
    @Test
    public void straightFlushTest1() {
        int[] cards = {38, 28, 48, 29, 26, 49, 27};
        for (int i = 0; i < cards.length; i++)
            deck.getCard(cards[i]).displayCard();
        int ans = Table.handValue(cards);
        assertEquals(9, ans);
    }
    @Test
    public void straightFlushTest2() {
        int[] cards = {18, 17, 19, 22, 21, 30, 20};
        for (int i = 0; i < cards.length; i++)
            deck.getCard(cards[i]).displayCard();
        int ans = Table.handValue(cards);
        assertEquals(9, ans);
    }
    @Test
    public void royalFlushTest1() {
        int[] cards = {47, 20, 48, 51, 2, 50, 49};
        for (int i = 0; i < cards.length; i++)
            deck.getCard(cards[i]).displayCard();
        int ans = Table.handValue(cards);
        assertEquals(10, ans);
    }
}

