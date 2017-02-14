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
        ans1 = (pair(sortedVals) ?
                (threeOfAKind(sortedVals) ?
                        (fourOfAKind(sortedVals) ? 8 :
                                (fullHouse(sortedVals) ? 7 : 4)) :
                        twoPair(sortedVals) ? 3 : 2)
                : 1);//high card

        ans2 = (flush(sortedCards) ?
                (straightFlush(sortedCards) ?
                        (royalFlush(sortedCards) ? 10 : 9)//royal or straight flush
                        : 6)//flush
                : (straight(sortedVals) ? 5 : 0));//Straight or nothing
        currentHandValue =  (ans1 > ans2 ? ans1 : ans2);
        return currentHandValue;
    }

    private boolean royalFlush(int[] sortedCards)
    {
        for (int i = 0; i < 3; i++)
            if ((sortedCards[i] % Constants.NUM_OF_CARDS_IN_SUIT == 8) //first of 5 cards is a 10
                    && (sortedCards[i + 4] % Constants.NUM_OF_CARDS_IN_SUIT == 12) // last of 5 cards is an A
                    && (sortedCards[i] / Constants.NUM_OF_CARDS_IN_SUIT == sortedCards[i + 4] / Constants.NUM_OF_CARDS_IN_SUIT)) // All five cards are in the same suit
                return true;
        return false;
    }

    private boolean straightFlush(int[] sortedCards)
    {
        for (int i = 0; i < 3; i++)
            if ((sortedCards[i] + 4 == sortedCards[i + 4])
                    && (sortedCards[i] / Constants.NUM_OF_CARDS_IN_SUIT == sortedCards[i + 4] / Constants.NUM_OF_CARDS_IN_SUIT))
                return true;
        for (int i = 0; i < sortedCards.length; i++)
            if (sortedCards[i] % Constants.NUM_OF_CARDS_IN_SUIT  == 12)
            {
                for (int j = 0; j < 4; j++)
                    if ((sortedCards[j] + 3 == sortedCards[j + 3])
                            && (sortedCards[j] % Constants.NUM_OF_CARDS_IN_SUIT == 0)
                            && sortedCards[j] / Constants.NUM_OF_CARDS_IN_SUIT == sortedCards[i] / Constants.NUM_OF_CARDS_IN_SUIT)
                        return true;
            }
        return false;
    }

    private boolean fourOfAKind(int[] sortedVals)
    {
        for (int i = 0; i < 4; i++)
            if (sortedVals[i] == sortedVals[i + 3])
                return true;
        return false;
    }

    private boolean fullHouse(int[] sortedVals)
    {
        for (int i = 0; i < 3; i++)
        {
            if (sortedVals[i] == sortedVals[i + 2])
                for (int j = i + 3; j < sortedVals.length - 1; j++)
                    if (sortedVals[j] == sortedVals[j + 1])
                        return true;
            if (sortedVals[i] == sortedVals[i + 1])
                for (int j = i + 2; j < sortedVals.length - 2; j++)
                    if (sortedVals[j] == sortedVals[j + 2])
                        return true;
        }
        return false;
    }

    private boolean flush(int[] sortedCards)
    {
        int suitOfFirstCard, suitOfFifthCard;
        for (int i = 0; i < 3; i++)
        {
            suitOfFirstCard = sortedCards[i] / Constants.NUM_OF_CARDS_IN_SUIT;
            suitOfFifthCard = sortedCards[i + 4] / Constants.NUM_OF_CARDS_IN_SUIT;
            if (suitOfFirstCard == suitOfFifthCard)
                return true;
        }
        return false;
    }

    private boolean straight(int[] sortedVals)
    {
        int[] valsNoDups = removeDuplicates(sortedVals);

        //if there is an A, and the fourth card is a 5, then we have a straight
        if (valsNoDups.length >= 5 && valsNoDups[3] == 3 && valsNoDups[valsNoDups.length - 1] == 12)
            return true;

        for (int i = 0; i + 4 < valsNoDups.length; i++)
            if (valsNoDups[i] + 4 == valsNoDups[i + 4])
                return true;
        return false;
    }

    private boolean threeOfAKind(int[] sortedVals)
    {
        for (int i = 0; i < sortedVals.length - 2; i++)
            if (sortedVals[i] == sortedVals[i + 2])
                return true;
        return false;
    }

    private boolean twoPair(int[] sortedVals)
    {
        for (int i = 0; i < sortedVals.length - 1; i++)
            if (sortedVals[i] == sortedVals[i + 1])
                for (int j = i + 2; j < sortedVals.length - 1; j++)
                    if (sortedVals[j] == sortedVals[j + 1])
                        return true;
        return false;
    }

    private boolean pair(int[] sortedVals)
    {
        for (int i = 0; i + 1 < sortedVals.length; i++)
            if (sortedVals[i] == sortedVals[i + 1])
                return true;
        return false;
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
}
