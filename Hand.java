import java.util.Arrays;

import static java.util.Arrays.sort;

/**
 * Created by Noam on 7/26/2016.
 */
public class Hand {

    //TODO the following functions dont find the biggest, only the first

    //

    private int id;
    private Card[] cards = new Card[Constants.NUM_OF_PLAYER_CARDS];
    private HandValueEnum currentHandValue;
    private long handsWon = 0;

    private int[] cardsIncludingTable = new int[Constants.NUM_OF_CARDS_WITH_TABLE];
    private int[] sortedCards = new int[Constants.NUM_OF_CARDS_WITH_TABLE];
    private int[] sortedVals = new int[Constants.NUM_OF_CARDS_WITH_TABLE];

    private int[] bestCardValues;

    Hand(int id, Card card1, Card card2)
    {
        this.id = id;
        this.cards[0] = card1;
        this.cards[1] = card2;
        cardsIncludingTable[0] = card1.getValue();
        cardsIncludingTable[1] = card2.getValue();
    }

    Hand(int[] cardsIncludingTable)
    {
        this.cardsIncludingTable = cardsIncludingTable;
    }

    public int getId() {return id;}

    public long getHandsWon() {
        return handsWon;
    }

    public void incrementHandsWon() {
        this.handsWon++;
    }

    public void setTableCards(int[] tableCards)
    {
        for (int i = Constants.NUM_OF_PLAYER_CARDS; i < Constants.NUM_OF_CARDS_WITH_TABLE; i++)
        {
            cardsIncludingTable[i] = tableCards[i - Constants.NUM_OF_PLAYER_CARDS];
        }
    }
    public void setTableCard(int index, int cardNumber)
    {
        if (2 <= index && index <= 6)
            cardsIncludingTable[index] = cardNumber;
        else
        {
            System.out.printf("ERROR! Invalid table card index.");
            System.exit(1);
        }
    }

    public int[] getSortedVals() {return sortedVals;}

    public void setSortedCards()
    {
        sortedCards = new int[Constants.NUM_OF_CARDS_WITH_TABLE];
        System.arraycopy(cardsIncludingTable, 0, sortedCards, 0, cardsIncludingTable.length);
        sort(sortedCards);
    }

    public void setSortedVals()
    {
        for (int i = 0; i < cardsIncludingTable.length; i++)
            sortedVals[i] = cardsIncludingTable[i] % Constants.NUM_OF_CARDS_IN_SUIT;
        sort(sortedVals);        
    }

    public HandValueEnum getHandValue()
    {
        /*Hand Values:
        *  1: High card
        *  2: Pair
        *  3: Two Pair
        *  4: Three of a Kind
        *  5: Straight
        *  6: Flush
        *  7: Full House
        *  8: Four of a Kind
        *  9: Straight Flush
        * 10: Royal Flush
        * */
        HandValueEnum ans1, ans2;

        setSortedCards();
        setSortedVals();

        ans1 = (pair() ?
                (threeOfAKind() ?
                        (fourOfAKind() ? HandValueEnum.FOUR_OF_A_KIND :
                                (fullHouse() ? HandValueEnum.FULL_HOUSE :
                                        HandValueEnum.THREE_OF_A_KIND)) :
                        twoPair() ? HandValueEnum.TWO_PAIR :
                                HandValueEnum.PAIR)
                : HandValueEnum.HIGH_CARD);

        ans2 = (flush() ?
                (straightFlush() ?
                        (royalFlush() ? HandValueEnum.ROYAL_FLUSH :
                                HandValueEnum.STRAIGHT_FLUSH)
                        : HandValueEnum.FLUSH)
                : (straight() ? HandValueEnum.STRAIGHT :
                HandValueEnum.HIGH_CARD));

        return ans1.getValue() > ans2.getValue() ? ans1 : ans2;
    }



    public HandValueEnum getCurrentHandValue()
    {
        return currentHandValue;
    }

    public void setCurrentHandValue(HandValueEnum currentHandValue)
    {
        this.currentHandValue = currentHandValue;
    }

    private boolean royalFlush()
    {
        for (int i = 0; i < 3; i++)
            if (sortedCards[i] % Constants.NUM_OF_CARDS_IN_SUIT == RankEnum.TEN.getValue()
                    && sortedCards[i + 4] % Constants.NUM_OF_CARDS_IN_SUIT == RankEnum.ACE.getValue()
                    && sameSuit(sortedCards[i], sortedCards[i + 4]))
                return true;
        return false;
    }

    private boolean straightFlush()
    {
        //if there is an A, and the fourth card is a 5 of the same suit, then we have a straight flush
        for (int i = 0; i < sortedCards.length; i++)
        {
            if (sortedCards[i] % Constants.NUM_OF_CARDS_IN_SUIT == RankEnum.ACE.getValue())
            {
                for (int j = 0; j < 4; j++)
                {
                    if ((sortedCards[j] + 3 == sortedCards[j + 3])
                            && (sortedCards[j] % Constants.NUM_OF_CARDS_IN_SUIT == RankEnum.TWO.getValue())
                            && sortedCards[j] / Constants.NUM_OF_CARDS_IN_SUIT == sortedCards[i] / Constants.NUM_OF_CARDS_IN_SUIT)//Two and Ace are same suit
                    {
                        bestCardValues = new int[1];
                        bestCardValues[0] = RankEnum.FIVE.getValue();
                        return true;
                    }
                }
            }
        }
        for (int i = 0; i < 3; i++)
        {
            if (sortedCards[i] + 4 == sortedCards[i + 4]
                    && sameSuit(sortedCards[i], sortedCards[i + 4]))
            {
                bestCardValues = new int[1];
                bestCardValues[0] = sortedCards[i + 4];
                return true;
            }
        }
        for (int i = 0; i < 3; i++)
        {
            if (sortedCards[i] % Constants.NUM_OF_CARDS_IN_SUIT == RankEnum.TWO.getValue()
                && (sortedCards[i + 3] % Constants.NUM_OF_CARDS_IN_SUIT == RankEnum.FIVE.getValue())
                && (sortedCards[i] / Constants.NUM_OF_CARDS_IN_SUIT == sortedCards[i + 3] / Constants.NUM_OF_CARDS_IN_SUIT))
                return false;
            //TODO fix this!!! Doesnt check for ace at the end
        }
        return false;
    }

    private boolean fourOfAKind() {
        for (int i = 0; i < 4; i++)
        {
            if (sortedVals[i] == sortedVals[i + 3])
            {
                bestCardValues = new int[2];
                bestCardValues[0] = sortedVals[i];
                if (i == 3)
                {
                    bestCardValues[1] = sortedVals[i - 1];
                }
                else
                {
                    bestCardValues[1] = sortedVals[6];
                }
                return true;
            }
        }
        return false;
    }

    private boolean fullHouse()
    {
        for (int i = 0; i < 3; i++)
        {
            if (sortedVals[i] == sortedVals[i + 2])
            {
                for (int j = i + 3; j < sortedVals.length - 1; j++)
                {
                    if (sortedVals[j] == sortedVals[j + 1])
                    {
                        bestCardValues = new int[2];
                        bestCardValues[0] = sortedVals[i];
                        bestCardValues[1] = sortedVals[j];
                        return true;
                    }
                }
            }
            if (sortedVals[i] == sortedVals[i + 1])
            {
                for (int j = i + 2; j < sortedVals.length - 2; j++)
                {
                    if (sortedVals[j] == sortedVals[j + 2])
                    {
                        bestCardValues = new int[2];
                        bestCardValues[0] = sortedVals[j];
                        bestCardValues[1] = sortedVals[i];
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean flush()
    {
        for (int i = 0; i < 3; i++)
        {
            if (sameSuit(sortedCards[i], sortedCards[i + 4]))
            {
                bestCardValues = new int[5];
                System.arraycopy(sortedCards, i, bestCardValues, 0, bestCardValues.length);
                return true;
            }
        }
        return false;
    }

    private boolean straight()
    {
        int[] valsNoDups = removeDuplicates(sortedVals);
        //if there is an A, and the fourth card is a 5, then we have a straight
        if (valsNoDups.length >= Constants.NUM_OF_TABLE_CARDS
                && valsNoDups[3] == RankEnum.FIVE.getValue()
                && valsNoDups[valsNoDups.length - 1] == RankEnum.ACE.getValue())
        {
            bestCardValues = new int[1];
            bestCardValues[0] = valsNoDups[3];
            return true;
        }

        for (int i = 0; i + 4 < valsNoDups.length; i++)
        {
            if (valsNoDups[i] + 4 == valsNoDups[i + 4])
            {
                bestCardValues = new int[1];
                bestCardValues[0] = valsNoDups[i + 4];
                return true;
            }
        }
        return false;
    }

    private boolean threeOfAKind()
    {
        for (int i = 0; i < sortedVals.length - 2; i++)
        {
            if (sortedVals[i] == sortedVals[i + 2])
            {
                bestCardValues = new int[3];
                bestCardValues[0] = sortedVals[i];
                if (i == 4)
                {
                    bestCardValues[1] = sortedVals[i - 1];
                    bestCardValues[2] = sortedVals[i - 2];
                } else if (i == 3)
                {
                    bestCardValues[1] = sortedVals[i + 3];
                    bestCardValues[2] = sortedVals[i - 1];
                } else // i is less or equal 2
                {
                    bestCardValues[1] = sortedVals[6];
                    bestCardValues[2] = sortedVals[5];
                }
                return true;
            }
        }
        return false;
    }

    private boolean twoPair()
    {
        for (int i = 0; i < sortedVals.length - 3; i++)
        {
            if (sortedVals[i] == sortedVals[i + 1])
            {
                for (int j = i + 2; j < sortedVals.length - 1; j++)
                {
                    if (sortedVals[j] == sortedVals[j + 1])
                    {
                        bestCardValues = new int[3];
                        bestCardValues[0] = sortedVals[j];
                        bestCardValues[1] = sortedVals[i];
                        if (j < 5)
                            bestCardValues[2] = sortedVals[6];
                        else if (i < 3)
                            bestCardValues[2] = sortedVals[4];
                        else
                            bestCardValues[2] = sortedVals[2];
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean pair()
    {
        for (int i = 0; i < sortedVals.length - 1; i++)
        {
            if (sortedVals[i] == sortedVals[i + 1])
            {
                bestCardValues = new int[4];
                bestCardValues[0] = sortedVals[i];
                if (i == 5)
                {
                    bestCardValues[1] = sortedVals[i - 1];
                    bestCardValues[2] = sortedVals[i - 2];
                    bestCardValues[3] = sortedVals[i - 3];
                } else if (i == 4)
                {
                    bestCardValues[1] = sortedVals[i + 2];
                    bestCardValues[2] = sortedVals[i - 1];
                    bestCardValues[3] = sortedVals[i - 2];
                } else if (i == 3)
                {
                    bestCardValues[1] = sortedVals[i + 3];
                    bestCardValues[2] = sortedVals[i + 2];
                    bestCardValues[3] = sortedVals[i - 1];
                } else
                {
                    bestCardValues[1] = sortedVals[6];
                    bestCardValues[2] = sortedVals[5];
                    bestCardValues[3] = sortedVals[4];
                }
                return true;
            }
        }
        return false;
    }

    private int[] removeDuplicates(int[] arr)
    {
        return Arrays.stream(arr)
                .distinct()
                .toArray();
    }

    private boolean sameSuit(int card1, int card2)
    {
        return card1 / Constants.NUM_OF_CARDS_IN_SUIT == card2 / Constants.NUM_OF_CARDS_IN_SUIT;
    }

    public String toString()
    {
        return String.format("First card: %s, Second card: %s\n", cards[0].toString(), cards[1].toString());
    }

    public int[] getCardsIncludingTable() {
        return cardsIncludingTable;
    }

    public int[] getBestCardValues()
    {
        return bestCardValues;
    }
}
