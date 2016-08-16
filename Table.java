
import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Arrays.sort;

/**
 * Created by Noam on 7/27/2016.
 */
public class Table {

    private static Table table = new Table();

    private static final int NUM_OF_CARDS_IN_SUIT = 13;
    private static final int NUM_OF_CARDS_IN_DECK = 52;

    private int[] tableCardNumbers = new int[5];

    /*private static boolean isRoyalFlushPossible;
    private static boolean isStraightFlushPossible;
    private static boolean isFourOfAKindAndFullHousePossible;
    private static boolean isFlushPossible;
    private static boolean isStraightPossible;*/



    /*
    * This is the variable that holds the numbers of the cards on the table.
    * We assume that any time a card is changed, the array is sorted.
    */

    public static Table getTable()
    {
        return table;
    }

    private Table()
    {
        for (int i = 0; i < 5; i++)
        {
            tableCardNumbers[i] = 0;
        }
    }

    public void startCalculation(ArrayList<Hand> hands)
    {
        int[][] sortedCards = new int[hands.size()][7];
        int[][] sortedVals = new int[hands.size()][7];

        for (int i = 0; i < hands.size(); i++)
        {
            //first two indexes store the user hand which doesnt change, the rest are the table cards
            sortedCards[i][0] = hands.get(i).getCard1().getCardNumber();
            sortedCards[i][1] = hands.get(i).getCard2().getCardNumber();
            sortedVals[i][0] = hands.get(i).getCard1().getCardNumber() % NUM_OF_CARDS_IN_SUIT;
            sortedVals[i][1] = hands.get(i).getCard2().getCardNumber() % NUM_OF_CARDS_IN_SUIT;
        }
        int[] indexes = new int[5];
        for (indexes[0] = 0; indexes[0] < NUM_OF_CARDS_IN_DECK; indexes[0]++)
        {
            indexes[0] = setTableCard(0, indexes[0]);
            for (indexes[1] = indexes[0] + 1; indexes[1] < NUM_OF_CARDS_IN_DECK ; indexes[1]++)
            {
                indexes[1] = setTableCard(0, indexes[1]);
                for (indexes[2] = indexes[1] + 1; indexes[2] < NUM_OF_CARDS_IN_DECK; indexes[2]++)
                {
                    indexes[2] = setTableCard(0, indexes[2]);
                    for (indexes[3] = indexes[2] + 1; indexes[3] < NUM_OF_CARDS_IN_DECK; indexes[3]++)
                    {
                        indexes[3] = setTableCard(0, indexes[3]);
                        for (indexes[4] = indexes[3] + 1; indexes[4] < NUM_OF_CARDS_IN_DECK; indexes[4]++)
                        {
                            indexes[4] = setTableCard(0, indexes[4]);

                            for (int i = 0; i < hands.size(); i++)
                            {
                                for (int j = 0; j < 5; j++)
                                {
                                    sortedCards[i][j + 2] = indexes[j];
                                    sortedVals[i][j + 2] = indexes[j] % NUM_OF_CARDS_IN_SUIT;
                                }
                                sort(sortedCards[i]);
                                sort(sortedVals[i]);

                                handValue(sortedCards[i], sortedVals[i]);
                            }
                        }
                    }
                }
            }
        }
    }

    private int setTableCard(int i, int cardNum)
    {
        while (!Deck.getDeck().getCard(cardNum).isUsed())
            cardNum++;
        this.tableCardNumbers[i] = cardNum;
        Deck.getDeck().getCard(cardNum).setUsed(true);
        return cardNum;
    }
    
    private int handValue(int[] sortedCards, int[] sortedVals)
    {
        int ans = 0;
        if (pair(sortedVals))
        {
            if (twoPair(sortedVals))
            {
                if (threeOfAKind(sortedVals))
                {
                    if (fullHouse(sortedVals))
                    {
                        if (fourOfAKind(sortedVals))
                        {

                        }
                    }
                }
            }
        }
        if (straight(sortedVals))
        {
            if (flush(sortedCards))
            {
                if (straightFlush(sortedCards, sortedVals))
                {
                    if (royalFlush(sortedCards, sortedVals))
                    {

                    }
                }
            }
        }
        if (flush(sortedCards))
        {

        }
        return ans;
    }



    /*private void isRoyalFlushPossible()
    {
        int tenOfSuit;
        int aceOfSuit;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                tenOfSuit = ((j * NUM_OF_CARDS_IN_SUIT) + 8);
                aceOfSuit = ((j * NUM_OF_CARDS_IN_SUIT) + NUM_OF_CARDS_IN_SUIT);
                if ((tenOfSuit <= tableCardNumbers[i]) && (tableCardNumbers[i + 2] <= aceOfSuit)) {
                    isRoyalFlushPossible = true;
                    return;
                }
            }
        }
    }

    private void isStraightFlushPossible()
    {
        for (int i = 0; i < 3; i++) {
            if ((tableCardNumbers[i] + 4 <= tableCardNumbers[i + 2]) &&
                    ((tableCardNumbers[i]) / NUM_OF_CARDS_IN_SUIT) == (tableCardNumbers[i + 2] / NUM_OF_CARDS_IN_SUIT)) {
                isStraightFlushPossible = true;
                return;
            }
        }
    }

    private void isFourOfAKindAndFullHousePossible()
    {
        for (int i = 0; i < 4; i++) {
            if ((tableCardNumbers[i] % NUM_OF_CARDS_IN_SUIT) == (tableCardNumbers[i + 1] % NUM_OF_CARDS_IN_SUIT))
            {
                isFourOfAKindAndFullHousePossible = true;
                return;
            }
        }
    }

    private void isFlushPossible()
    {
        for (int i = 0; i < 3; i++) {
            if ((tableCardNumbers[i] / NUM_OF_CARDS_IN_SUIT) == (tableCardNumbers[i + 2] / NUM_OF_CARDS_IN_SUIT))
            {
                isFlushPossible = true;
                return;
            }
        }
    }

    private void isStraightPossible()
    {
        *//*
        * We check all the possible sets of 3, to see if a straight is possible. This is done in 5 choose 3 = 10,
        * and much better than previous check of going 1 to NUM_OF_CARDS_IN_SUIT and checking if there is a straight possibility.
        * We check that the diff between the lowest of the three lower by 4 or less than the highest. In addition,
        * all 3 cards have to be different. The second case in the if, is when one of the cards is an Ace, and the
        * straight can be formed with the smaller cards.
        *//*
        int[] vals = new int[3];
        for (int i = 0; i < 5; i++)
        {
            for (int j = i + 1; j < 5; j++)
            {
                for (int k = j + 1; k < 5; k++)
                {
                    //if there are three cards in range of 5 cards, then a straight is possible. Mod NUM_OF_CARDS_IN_SUIT since we dont care about the suit
                    vals[0] = tableCardNumbers[i] % NUM_OF_CARDS_IN_SUIT;
                    vals[1] = tableCardNumbers[j] % NUM_OF_CARDS_IN_SUIT;
                    vals[2] = tableCardNumbers[k] % NUM_OF_CARDS_IN_SUIT;
                    Arrays.sort(vals);//not sure this is needed, array is already sorted

                    if (((vals[2] - vals[0] < 5) && (vals[0] != vals[1] && vals[1]!= vals[2])) ||
                            (vals[2] == NUM_OF_CARDS_IN_SUIT - 1 && vals[0] < 4 && vals[1] < 4 && vals[0] != vals[1]))
                    {
                        isStraightPossible = true;
                        return;
                    }
                }
            }
        }
    }

    private void resetPossibilities()
    {
        isRoyalFlushPossible = false;
        isStraightFlushPossible = false;
        isFourOfAKindAndFullHousePossible = false;
        isFlushPossible = false;
        isStraightPossible = false;

        isRoyalFlushPossible();
        isStraightFlushPossible();
        isFourOfAKindAndFullHousePossible();
        isFlushPossible();
        isStraightPossible();


    }*/

    private boolean royalFlush(int[] sortedCards, int[] sortedVals)
    {
        for (int i = 0; i < 3; i++)
            if ((sortedCards[i] % NUM_OF_CARDS_IN_SUIT == 8) //first of 5 cards is a 10
                    && (sortedCards[i + 4] % NUM_OF_CARDS_IN_SUIT == 12) // last of 5 cards is an A
                    && (sortedCards[i] / NUM_OF_CARDS_IN_SUIT == sortedCards[i + 4] / NUM_OF_CARDS_IN_SUIT)) // All five cards are in the same suit
                return true;
        return false;
    }

    private boolean straightFlush(int[] sortedCards, int[] sortedVals)
    {
        for (int i = 0; i < 3; i++)
        {
            if ((sortedCards[i] + 4 == sortedCards[i + 4])
                && (sortedCards[i] / NUM_OF_CARDS_IN_SUIT == sortedCards[i + 4] / NUM_OF_CARDS_IN_SUIT))
                return true;
        }
        return false;
    }

    private boolean fourOfAKind(int[] sortedVals)
    {
        for (int i = 0; i < 3; i++)
        {
            if (sortedVals[i] == sortedVals[i + 3])
                return true;
        }
        return false;
    }

    private boolean fullHouse(int[] sortedVals)
    {
        for (int i = 0; i < 3; i++)
        {
            if (sortedVals[i] == sortedVals[i + 3])
            {
                for (int j = i + 3; j < 7; j++)
                    if (sortedVals[j] == sortedVals[j + 1])
                        return true;
            }
            if (sortedVals[i] == sortedVals[i + 2])
            {
                for (int j = i + 2; j < 7; j++)
                    if (sortedVals[j] == sortedVals[j + 2])
                        return true;
            }
        }
        return false;
    }

    private boolean flush(int[] sortedCards)
    {
        for (int i = 0; i < 3; i++)
        {
            if ((sortedCards[i] / NUM_OF_CARDS_IN_SUIT == sortedCards[i + 1] / NUM_OF_CARDS_IN_SUIT)
                && (sortedCards[i] / NUM_OF_CARDS_IN_SUIT == sortedCards[i + 2] / NUM_OF_CARDS_IN_SUIT)
                && (sortedCards[i] / NUM_OF_CARDS_IN_SUIT == sortedCards[i + 3] / NUM_OF_CARDS_IN_SUIT)
                && (sortedCards[i] / NUM_OF_CARDS_IN_SUIT == sortedCards[i + 4] / NUM_OF_CARDS_IN_SUIT))
                return true;

        }
        return false;
    }

    private boolean straight(int[] sortedVals)
    {
        /*TODO delete duplicates and check case were A through 5 is the straight*/
        for (int i = 0; i < 3; i++)
        {
            if ((sortedVals[i] + 1 == sortedVals[i + 1])
                && (sortedVals[i] + 2 == sortedVals[i + 2])
                && (sortedVals[i] + 3 == sortedVals[i + 3])
                && (sortedVals[i] + 4 == sortedVals[i + 4]))
                return true;

        }
        return false;
    }

    private boolean threeOfAKind(int[] sortedVals)
    {
        for (int i = 0; i < 4; i++)
        {
            if (sortedVals[i] == sortedVals[i + 2])
                return true;
        }
        return false;
    }

    private boolean twoPair(int[] sortedVals)
    {
        for (int i = 0; i < 3; i++)
        {
            if (sortedVals[i] == sortedVals[i + 1])
            {
                for (int j = i + 2; j < 7; j++)
                    if (sortedVals[j] == sortedVals[j + 1])
                        return true;
            }
        }
        return false;
    }

    private boolean pair(int[] sortedVals)
    {
        for (int i = 0; i < 3; i++)
        {
            if (sortedVals[i] == sortedVals[i + 1])
                return true;
        }
        return false;
    }
}
