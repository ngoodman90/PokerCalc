import java.util.ArrayList;
import static java.util.Arrays.sort;

/**
 * Created by Noam on 7/27/2016.
 */
public class Table {

    private static final int NUM_OF_CARDS_IN_SUIT = 13;
    private static final int NUM_OF_CARDS_IN_DECK = 52;

    private static Table table = new Table();

    public static Table getTable(){return table;}

    private Table(){}


    public void startCalculation(ArrayList<Hand> hands)
    {
        int[] winningHands = new int[9];
        int[][] sortedCards = new int[hands.size()][7];
        int[][] sortedVals = new int[hands.size()][7];
        int maxValue;
        int maxIndex;
        int currentHandValue;
        int numberOfWinningHands = 0;

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
            indexes[0] = setTableCard(indexes[0]);
            for (indexes[1] = indexes[0] + 1; indexes[1] < NUM_OF_CARDS_IN_DECK ; indexes[1]++)
            {
                indexes[1] = setTableCard(indexes[1]);
                for (indexes[2] = indexes[1] + 1; indexes[2] < NUM_OF_CARDS_IN_DECK; indexes[2]++)
                {
                    indexes[2] = setTableCard(indexes[2]);
                    for (indexes[3] = indexes[2] + 1; indexes[3] < NUM_OF_CARDS_IN_DECK; indexes[3]++)
                    {
                        indexes[3] = setTableCard(indexes[3]);
                        for (indexes[4] = indexes[3] + 1; indexes[4] < NUM_OF_CARDS_IN_DECK; indexes[4]++)
                        {
                            indexes[4] = setTableCard(indexes[4]);

                            maxValue = 0;
                            for (int i = 0; i < hands.size(); i++)
                            {
                                for (int j = 0; j < 5; j++)
                                {
                                    sortedCards[i][j + 2] = indexes[j];
                                    sortedVals[i][j + 2] = indexes[j] % NUM_OF_CARDS_IN_SUIT;
                                }
                                sort(sortedCards[i]);
                                sort(sortedVals[i]);

                                currentHandValue = handValue(sortedCards[i], sortedVals[i]);
                                if (currentHandValue > maxValue)
                                {
                                    maxValue = currentHandValue;
                                    maxIndex = i;
                                    winningHands = new int[9];
                                    winningHands[0] = maxIndex;
                                    numberOfWinningHands = 1;
                                }
                                else if (currentHandValue == maxValue)
                                    winningHands[numberOfWinningHands++] = i;
                            }
                            for (int k = 0; k < numberOfWinningHands; k++)
                                hands.get(winningHands[k]).incrementHandsWon();
                            Deck.getDeck().getCard(indexes[4]).setUsed(false);
                        }
                        Deck.getDeck().getCard(indexes[3]).setUsed(false);
                    }
                    Deck.getDeck().getCard(indexes[2]).setUsed(false);
                }
                Deck.getDeck().getCard(indexes[1]).setUsed(false);
            }
            Deck.getDeck().getCard(indexes[0]).setUsed(false);
        }
    }

    private int setTableCard(int cardNum)
    {
        while (!Deck.getDeck().getCard(cardNum).isUsed())
            cardNum++;
        Deck.getDeck().getCard(cardNum).setUsed(true);
        return cardNum;
    }

    private int handValue(int[] sortedCards, int[] sortedVals)
    {
        int ans1, ans2;
        ans1 = (pair(sortedVals) ?
                (twoPair(sortedVals) ?
                        (threeOfAKind(sortedVals) ?
                                (fullHouse(sortedVals) ?
                                        (fourOfAKind(sortedVals) ?  8 : 7)//4 of a kind or full house
                                        : 4)//3 of a kind
                                : 3)//two pair
                        : 2)//pair
                : 1);//high card

        ans2 = (straight(sortedVals) ?
                (flush(sortedCards) ?
                        (straightFlush(sortedCards) ?
                                (royalFlush(sortedCards) ? 10 : 9)//royal or straight flush
                                : 6)//flush
                        : 5)//straight
                : (flush(sortedCards) ? 6 : 0));//flush or nothing

        return (ans1 > ans2 ? ans1 : ans2);
    }

    private boolean royalFlush(int[] sortedCards)
    {
        for (int i = 0; i < 3; i++)
            if ((sortedCards[i] % NUM_OF_CARDS_IN_SUIT == 8) //first of 5 cards is a 10
                    && (sortedCards[i + 4] % NUM_OF_CARDS_IN_SUIT == 12) // last of 5 cards is an A
                    && (sortedCards[i] / NUM_OF_CARDS_IN_SUIT == sortedCards[i + 4] / NUM_OF_CARDS_IN_SUIT)) // All five cards are in the same suit
                return true;
        return false;
    }

    private boolean straightFlush(int[] sortedCards)
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
