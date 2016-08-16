import com.sun.xml.internal.bind.v2.TODO;

import java.util.Arrays;

/**
 * Created by Noam on 7/27/2016.
 */
public class Table {

    private static Table table = new Table();

    private static final int NUM_OF_CARDS_IN_SUIT = 13;
    private static final int NUM_OF_CARDS_IN_DECK = 52;

    private int[] tableCardNumbers = new int[5];

    private static boolean isRoyalFlushPossible;
    private static boolean isStraightFlushPossible;
    private static boolean isFourOfAKindAndFullHousePossible;
    private static boolean isFlushPossible;
    private static boolean isStraightPossible;



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

    public void startCalculation()
    {
        for (int index0 = 0; index0 < NUM_OF_CARDS_IN_DECK; index0++)
        {
            index0 = setTableCard(0, index0);
            for (int index1 = index0 + 1; index1 < NUM_OF_CARDS_IN_DECK ; index1++)
            {
                index1 = setTableCard(1, index1);
                for (int index2 = index1 + 1; index1 < NUM_OF_CARDS_IN_DECK; index1++)
                {
                    index2 = setTableCard(2, index2);
                    for (int index3 = index2 + 1; index1 < NUM_OF_CARDS_IN_DECK; index1++)
                    {
                        index3 = setTableCard(3, index3);
                        for (int index4 = index3 + 1; index1 < NUM_OF_CARDS_IN_DECK; index1++)
                        {
                            index4 = setTableCard(4, index4);

                            resetPossibilities();
                            /*TODO*/


                        }
                    }
                }
            }
        }
    }
    
    private int handValue()
    {
        int ans = 0;
        if (pair())
            return 10;
        if (twoPair())
            return 10;
        if (threeOfAKind())
            return 10;
        if (straight())
            return 10;
        if (flush())
            return 10;
        if (fullHouse())
            return 10;
        if (fourOfAKind())
            return 10;
        if (straightFlush())
            ans = 9;
        if (royalFlush())
            ans = 10;
        
        
        return ans;
    }

    private int setTableCard(int i, int cardNum)
    {
        while (!Deck.getDeck().getCard(cardNum).isUsed())
            cardNum++;
        this.tableCardNumbers[i] = cardNum;
        Deck.getDeck().getCard(cardNum).setUsed(true);
        return cardNum;
    }

    /*public void setCard(int index, int val)
    {
        this.tableCardNumbers[index] = val;
        Deck.getDeck().getCard(val).setUsed(true);
    }*/

    private void isRoyalFlushPossible()
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
        /*
        * We check all the possible sets of 3, to see if a straight is possible. This is done in 5 choose 3 = 10,
        * and much better than previous check of going 1 to NUM_OF_CARDS_IN_SUIT and checking if there is a straight possibility.
        * We check that the diff between the lowest of the three lower by 4 or less than the highest. In addition,
        * all 3 cards have to be different. The second case in the if, is when one of the cards is an Ace, and the
        * straight can be formed with the smaller cards.
        */
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


    }

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

    }
}
