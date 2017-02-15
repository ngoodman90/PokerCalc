import java.util.Arrays;

import static java.util.Arrays.sort;

/**
 * Created by Noam on 7/26/2016.
 */
public class Hand {

    private int id;
    private Card card1;
    private Card card2;
    private int currentHandValue = 0;
    private long handsWon = 0;

    private int[] cardsIncludingTable;
    private int[] sortedCards;
    private int[] sortedVals;

    private int straightValue;
    private int straightFlushValue;
    private int[] flushValues = new int[5];
    private int[] fourOfAKindValues = new int[2];
    private int[] fullHouseValues = new int[2];
    private int[] threeOfAKindValues = new int[3];
    private int[] twoPairValues = new int[3];
    private int[] pairValues = new int[4];

    Hand(int id, Card card1, Card card2) {
        this.id = id;
        this.card1 = card1;
        this.card2 = card2;
        cardsIncludingTable = new int[7];
        cardsIncludingTable[0] = card1.getCardNumber();
        cardsIncludingTable[1] = card2.getCardNumber();
    }

    Hand(int[] cardsIncludingTable)
    {
        this.cardsIncludingTable = cardsIncludingTable;
    }

    public int getId() {return id;}

    public long gethandsWon() {
        return handsWon;
    }

    public void incrementHandsWon() {
        this.handsWon++;
    }

    public int getCurrentHandValue() {
        return currentHandValue;
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

    public int[] getSortedCards() {return sortedCards;}

    public int[] getSortedVals() {return sortedVals;}

    public void setSortedCards()
    {
        sortedCards = new int[Constants.NUM_OF_CARDS_WITH_TABLE];
        for (int i = 0; i < Constants.NUM_OF_CARDS_WITH_TABLE; i++)
            sortedCards[i] = cardsIncludingTable[i];
        sort(sortedCards);
    }

    public void setSortedVals() {
        sortedVals = new int[Constants.NUM_OF_CARDS_WITH_TABLE];
        for (int i = 0; i < Constants.NUM_OF_CARDS_WITH_TABLE; i++)
            sortedVals[i] = cardsIncludingTable[i] % Constants.NUM_OF_CARDS_IN_SUIT;
        sort(sortedVals);        
    }

    public int setHandValue()
    {
        /*Hand Values:
        *  1: High Card
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
        int ans1, ans2;
        setSortedCards();
        setSortedVals();
        ans1 = (pair() ?
                (threeOfAKind() ?
                        (fourOfAKind() ? 8 :
                                (fullHouse() ? 7 : 4)) :
                        twoPair() ? 3 : 2)
                : 1);//high card

        ans2 = (flush() ?
                (straightFlush() ?
                        (royalFlush() ? 10 : 9)//royal or straight flush
                        : 6)//flush
                : (straight() ? 5 : 0));//Straight or nothing
        currentHandValue =  (ans1 > ans2 ? ans1 : ans2);
        return currentHandValue;
    }

    private boolean royalFlush()
    {
        boolean ans = false;
        for (int i = 0; i < 3; i++)
            if ((sortedCards[i] % Constants.NUM_OF_CARDS_IN_SUIT == 8) //first of 5 cards is a 10
                    && (sortedCards[i + 4] % Constants.NUM_OF_CARDS_IN_SUIT == 12) // last of 5 cards is an A
                    && (sortedCards[i] / Constants.NUM_OF_CARDS_IN_SUIT == sortedCards[i + 4] / Constants.NUM_OF_CARDS_IN_SUIT)) // All five cards are in the same suit
                ans  = true;
        return ans;
    }

    private boolean straightFlush()
    {
        boolean ans = false;
        //if there is an A, and the fourth card is a 5 of the same suit, then we have a straight flush
        for (int i = 0; i < sortedCards.length; i++)
            if (sortedCards[i] % Constants.NUM_OF_CARDS_IN_SUIT  == 12) {
                for (int j = 0; j < 4; j++)
                    if ((sortedCards[j] + 3 == sortedCards[j + 3])
                            && (sortedCards[j] % Constants.NUM_OF_CARDS_IN_SUIT == 0)
                            && sortedCards[j] / Constants.NUM_OF_CARDS_IN_SUIT == sortedCards[i] / Constants.NUM_OF_CARDS_IN_SUIT) {
                        straightFlushValue = 12;
                        ans = true;
                    }
            }
        for (int i = 0; i < 3; i++)
            if ((sortedCards[i] + 4 == sortedCards[i + 4])
                    && (sortedCards[i] / Constants.NUM_OF_CARDS_IN_SUIT == sortedCards[i + 4] / Constants.NUM_OF_CARDS_IN_SUIT))
            {
                straightFlushValue = sortedCards[i + 4];
                ans = true;
            }
        return ans;
    }

    private boolean fourOfAKind() {
        boolean ans = false;
        for (int i = 0; i < 4; i++)
            if (sortedVals[i] == sortedVals[i + 3])
            {
                fourOfAKindValues[0] = sortedVals[i];
                if (i == 3)
                    fourOfAKindValues[1] = sortedVals[i - 1];
                else
                    fourOfAKindValues[1] = sortedVals[6];
                ans = true;
            }

        return ans;
    }

    private boolean fullHouse()
    {
        boolean ans = false;
        for (int i = 0; i < 3; i++)
        {
            if (sortedVals[i] == sortedVals[i + 2])
                for (int j = i + 3; j < sortedVals.length - 1; j++)
                    if (sortedVals[j] == sortedVals[j + 1])
                    {
                        fullHouseValues[0] = sortedVals[i];
                        fullHouseValues[1] = sortedVals[j];
                        ans = true;
                    }
            if (sortedVals[i] == sortedVals[i + 1])
                for (int j = i + 2; j < sortedVals.length - 2; j++)
                    if (sortedVals[j] == sortedVals[j + 2])
                    {
                        fullHouseValues[0] = sortedVals[j];
                        fullHouseValues[1] = sortedVals[i];
                        ans = true;
                    }
        }
        return ans;
    }

    private boolean flush()
    {
        boolean ans = false;
        int suitOfFirstCard, suitOfFifthCard;
        for (int i = 0; i < 3; i++)
        {
            suitOfFirstCard = sortedCards[i] / Constants.NUM_OF_CARDS_IN_SUIT;
            suitOfFifthCard = sortedCards[i + 4] / Constants.NUM_OF_CARDS_IN_SUIT;
            if (suitOfFirstCard == suitOfFifthCard)
            {
                for (int j = 0; j < 5; j++)
                    flushValues[j] = sortedCards[i + 4 - j];
                ans = true;
            }
        }
        return ans;
    }

    private boolean straight()
    {
        boolean ans = false;
        int[] valsNoDups = removeDuplicates(sortedVals);
        //if there is an A, and the fourth card is a 5, then we have a straight
        if (valsNoDups.length >= 5 && valsNoDups[3] == 3 && valsNoDups[valsNoDups.length - 1] == 12)
        {
            straightValue = 12;
            ans = true;
        }
        for (int i = 0; i + 4 < valsNoDups.length; i++)
            if (valsNoDups[i] + 4 == valsNoDups[i + 4])
            {
                straightValue = valsNoDups[i + 4];
                ans = true;
            }
        return ans;
    }

    private boolean threeOfAKind()
    {
        boolean ans = false;
        for (int i = 0; i < sortedVals.length - 2; i++)
            if (sortedVals[i] == sortedVals[i + 2])
            {
                threeOfAKindValues[0] = sortedVals[i];
                if (i == 4)
                {
                    threeOfAKindValues[1] = sortedVals[i - 1];
                    threeOfAKindValues[2] = sortedVals[i - 2];
                }
                else if (i == 3)
                {
                    threeOfAKindValues[1] = sortedVals[i + 3];
                    threeOfAKindValues[2] = sortedVals[i - 1];
                }
                else // i is less or equal 2
                {
                    threeOfAKindValues[1] = sortedVals[6];
                    threeOfAKindValues[2] = sortedVals[5];
                }
                ans = true;
            }
        return ans;
    }

    private boolean twoPair()
    {
        boolean ans = false;
        for (int i = 0; i < sortedVals.length - 3; i++)
            if (sortedVals[i] == sortedVals[i + 1])
                for (int j = i + 2; j < sortedVals.length - 1; j++)
                    if (sortedVals[j] == sortedVals[j + 1])
                    {
                        twoPairValues[0] = sortedVals[j];
                        twoPairValues[1] = sortedVals[i];
                        if (j < 5)
                            twoPairValues[2] = sortedVals[6];
                        else if (i < 3)
                            twoPairValues[2] = sortedVals[4];
                        else
                            twoPairValues[2] = sortedVals[2];
                        ans = true;
                    }
        return ans;
    }

    private boolean pair()
    {
        boolean ans = false;
        for (int i = 0; i < sortedVals.length - 1; i++)
            if (sortedVals[i] == sortedVals[i + 1])
            {
                pairValues[0] = sortedVals[i];
                if (i == 5)
                {
                    pairValues[1] = sortedVals[i - 1];
                    pairValues[2] = sortedVals[i - 2];
                    pairValues[3] = sortedVals[i - 3];
                }
                else if (i == 4)
                {
                    pairValues[1] = sortedVals[i + 2];
                    pairValues[2] = sortedVals[i - 1];
                    pairValues[3] = sortedVals[i - 2];
                }
                else if (i == 3)
                {
                    pairValues[1] = sortedVals[i + 3];
                    pairValues[2] = sortedVals[i + 2];
                    pairValues[3] = sortedVals[i - 1];
                }
                else
                {
                    pairValues[1] = sortedVals[6];
                    pairValues[2] = sortedVals[5];
                    pairValues[3] = sortedVals[4];
                }
                ans = true;
            }
        return ans;
    }

    private int[] removeDuplicates(int[] arr)
    {
        return Arrays.stream(arr)
                .distinct()
                .toArray();
    }

    public void displayHand()
    {
        System.out.println("First card: ");
        this.card1.displayCard();
        System.out.println("Second card: ");
        this.card2.displayCard();
    }


    public int getStraightFlushValue() {
        return straightFlushValue;
    }

    public int getStraightValue() {
        return straightValue;
    }

    public int[] getfourOfAKindValues() {
        return fourOfAKindValues;
    }

    public int[] getFullHouseValues() {
        return fullHouseValues;
    }

    public int[] getFlushValues() {
        return flushValues;
    }

    public int[] getThreeOfAKindValues() {
        return threeOfAKindValues;
    }

    public int[] getTwoPairValues() {
        return twoPairValues;
    }

    public int[] getPairValues() {
        return pairValues;
    }

    public int[] getCardsIncludingTable() {
        return cardsIncludingTable;
    }
}
