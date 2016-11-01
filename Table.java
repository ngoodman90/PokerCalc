import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Arrays.sort;

/**
 * Created by Noam on 7/27/2016.
 */
public class Table {

    private static final Table table = new Table();

    public static Table getTable(){return table;}

    private Table(){}


    public void startCalculation(ArrayList<Hand> hands)
    {
        int[][] userAndTableCards = new int[hands.size()][7];

        setUserCards(hands, userAndTableCards);
        randomizeTableCards(hands, userAndTableCards);
    }

    private void setUserCards(ArrayList<Hand> hands, int[][] userAndTableCards) {
        int iterator = 0;
        for (Hand hand : hands)
        {
            //first two indexes store the user hand which doesnt change, the rest are the table cards
            userAndTableCards[iterator][0] = hand.getCard1().getCardNumber();
            userAndTableCards[iterator][1] = hand.getCard2().getCardNumber();
            iterator++;
        }
    }

    private void randomizeTableCards(ArrayList<Hand> hands, int[][] userAndTableCards) {
        ArrayList<Hand> winningHands = new ArrayList<>();
        int maxValue;
        int handIterator;
        int[] indexes = new int[5];
        int[] tempUserAndTableCards;

        for (indexes[0] = 0; indexes[0] < PokerCalc.NUM_OF_CARDS_IN_DECK; indexes[0]++)
        {
            indexes[0] = setTableCard(indexes[0]);
            for (int i = 0; i < hands.size(); i++)
                userAndTableCards[i][2] = indexes[0];
            for (indexes[1] = indexes[0] + 1; indexes[1] < PokerCalc.NUM_OF_CARDS_IN_DECK ; indexes[1]++) {
                indexes[1] = setTableCard(indexes[1]);
                for (int i = 0; i < hands.size(); i++)
                    userAndTableCards[i][3] = indexes[1];
                for (indexes[2] = indexes[1] + 1; indexes[2] < PokerCalc.NUM_OF_CARDS_IN_DECK; indexes[2]++) {
                    indexes[2] = setTableCard(indexes[2]);
                    for (int i = 0; i < hands.size(); i++)
                        userAndTableCards[i][4] = indexes[2];
                    for (indexes[3] = indexes[2] + 1; indexes[3] < PokerCalc.NUM_OF_CARDS_IN_DECK; indexes[3]++){
                        indexes[3] = setTableCard(indexes[3]);
                        for (int i = 0; i < hands.size(); i++)
                            userAndTableCards[i][5] = indexes[3];
                        for (indexes[4] = indexes[3] + 1; indexes[4] < PokerCalc.NUM_OF_CARDS_IN_DECK; indexes[4]++) {
                            indexes[4] = setTableCard(indexes[4]);
                            for (int i = 0; i < hands.size(); i++)
                                userAndTableCards[i][6] = indexes[4];
                            maxValue = 0;
                            /*for (int i = 0; i < hands.size(); i++) What is this line supposed to do????*/
                            handIterator = 0;
                            for (Hand hand : hands)
                            {
                                tempUserAndTableCards = userAndTableCards[handIterator].clone();
                                hand.setCurrentHandValue(handValue(tempUserAndTableCards));
                                if (hand.getCurrentHandValue() > maxValue)
                                {
                                    maxValue = hand.getCurrentHandValue();
                                    //creating a new list with current hand in it
                                    winningHands = new ArrayList<>(Arrays.asList(hand));
                                }
                                else if (hand.getCurrentHandValue() == maxValue)
                                    winningHands.add(hand);
                            }
                            if (winningHands.size() > 1)
                                findBestHands(winningHands, maxValue);
                            for (Hand winningHand : winningHands)
                                winningHand.incrementHandsWon();
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
        printStatistics(hands);
    }

    private void findBestHands(ArrayList<Hand> winningHands, int maxValue)
    {
        switch (maxValue)
        {
            case 1://High Card
                int maxCard = 0;

                break;
            case 2://Pair

                break;
            case 3://Two Pair

                break;
            case 4://Three of a Kind

                break;
            case 5://Straight

                break;
            case 6://Flush

                break;
            case 7://Full House

                break;
            case 8://Four of a Kind

                break;
            case 9://Straight Flush

                break;
            case 10://Royal Flush

                break;
        }
    }

    private void printStatistics(ArrayList<Hand> hands)
    {
        int sum = 0;
        for (Hand hand : hands)
            sum += hand.gethandsWon();
        System.out.printf("Hands played: %d\n", sum);
        int i = 0;
        for (Hand hand : hands)
            System.out.printf("Hand number %d won %d out of %d hands.\n", ++i, hand.gethandsWon(), sum);
    }

    private int setTableCard(int cardNum)
    {
        while (Deck.getDeck().getCard(cardNum).isUsed())
            cardNum++;
        Deck.getDeck().getCard(cardNum).setUsed(true);
        return cardNum;
    }

    public static int handValue(int[] userAndTableCards)
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
        int [] userAndTableVals = getVals(userAndTableCards);
        sort(userAndTableCards);
        sort(userAndTableVals);
        ans1 = (pair(userAndTableVals) ?
                (threeOfAKind(userAndTableVals) ?
                        (fourOfAKind(userAndTableVals) ? 8 :
                                (fullHouse(userAndTableVals) ? 7 : 4)) :
                        twoPair(userAndTableVals) ? 3 : 2)
                : 1);//high card

        ans2 = (flush(userAndTableCards) ?
                (straightFlush(userAndTableCards) ?
                        (royalFlush(userAndTableCards) ? 10 : 9)//royal or straight flush
                        : 6)//flush
                : (straight(userAndTableVals) ? 5 : 0));//Straight or nothing
        return (ans1 > ans2 ? ans1 : ans2);
    }

    private static int[] getVals(int[] userAndTableCards)
    {
        int[] vals = new int[userAndTableCards.length];
        for (int i = 0; i < userAndTableCards.length; i++)
            vals[i] = userAndTableCards[i] % PokerCalc.NUM_OF_CARDS_IN_SUIT;
        return vals;
    }

    private static boolean royalFlush(int[] userAndTableCards)
    {
        for (int i = 0; i < 3; i++)
            if ((userAndTableCards[i] % PokerCalc.NUM_OF_CARDS_IN_SUIT == 8) //first of 5 cards is a 10
                    && (userAndTableCards[i + 4] % PokerCalc.NUM_OF_CARDS_IN_SUIT == 12) // last of 5 cards is an A
                    && (userAndTableCards[i] / PokerCalc.NUM_OF_CARDS_IN_SUIT == userAndTableCards[i + 4] / PokerCalc.NUM_OF_CARDS_IN_SUIT)) // All five cards are in the same suit
                return true;
        return false;
    }

    private static boolean straightFlush(int[] userAndTableCards)
    {
        for (int i = 0; i < 3; i++)
            if ((userAndTableCards[i] + 4 == userAndTableCards[i + 4])
                    && (userAndTableCards[i] / PokerCalc.NUM_OF_CARDS_IN_SUIT == userAndTableCards[i + 4] / PokerCalc.NUM_OF_CARDS_IN_SUIT))
                return true;
        for (int i = 0; i < userAndTableCards.length; i++)
            if (userAndTableCards[i] % PokerCalc.NUM_OF_CARDS_IN_SUIT  == 12)
            {
                for (int j = 0; j < 4; j++)
                    if ((userAndTableCards[j] + 3 == userAndTableCards[j + 3])
                            && (userAndTableCards[j] % PokerCalc.NUM_OF_CARDS_IN_SUIT == 0)
                            && userAndTableCards[j] / PokerCalc.NUM_OF_CARDS_IN_SUIT == userAndTableCards[i] / PokerCalc.NUM_OF_CARDS_IN_SUIT)
                        return true;
            }
        return false;
    }

    private static boolean fourOfAKind(int[] userAndTableVals)
    {
        for (int i = 0; i < 4; i++)
            if (userAndTableVals[i] == userAndTableVals[i + 3])
                return true;
        return false;
    }

    private static boolean fullHouse(int[] userAndTableVals)
    {
        for (int i = 0; i < 3; i++)
        {
            if (userAndTableVals[i] == userAndTableVals[i + 2])
                for (int j = i + 3; j < userAndTableVals.length - 1; j++)
                    if (userAndTableVals[j] == userAndTableVals[j + 1])
                        return true;
            if (userAndTableVals[i] == userAndTableVals[i + 1])
                for (int j = i + 2; j < userAndTableVals.length - 2; j++)
                    if (userAndTableVals[j] == userAndTableVals[j + 2])
                        return true;
        }
        return false;
    }

    private static boolean flush(int[] userAndTableCards)
    {
        int suitOfFirstCard, suitOfFifthCard;
        for (int i = 0; i < 3; i++)
        {
            suitOfFirstCard = userAndTableCards[i] / PokerCalc.NUM_OF_CARDS_IN_SUIT;
            suitOfFifthCard = userAndTableCards[i + 4] / PokerCalc.NUM_OF_CARDS_IN_SUIT;
            if (suitOfFirstCard == suitOfFifthCard)
                return true;
        }
        return false;
    }

    private static boolean straight(int[] userAndTableVals)
    {
        int[] valsNoDups = removeDuplicates(userAndTableVals);

        //if there is an A, and the fourth card is a 5, then we have a straight
        if (valsNoDups.length >= 5 && valsNoDups[3] == 3 && valsNoDups[valsNoDups.length - 1] == 12)
            return true;

        for (int i = 0; i + 4 < valsNoDups.length; i++)
            if (valsNoDups[i] + 4 == valsNoDups[i + 4])
                return true;
        return false;
    }

    private static boolean threeOfAKind(int[] userAndTableVals)
    {
        for (int i = 0; i < userAndTableVals.length - 2; i++)
            if (userAndTableVals[i] == userAndTableVals[i + 2])
                return true;
        return false;
    }

    private static boolean twoPair(int[] userAndTableVals)
    {
        for (int i = 0; i < userAndTableVals.length - 1; i++)
            if (userAndTableVals[i] == userAndTableVals[i + 1])
                for (int j = i + 2; j < userAndTableVals.length - 1; j++)
                    if (userAndTableVals[j] == userAndTableVals[j + 1])
                        return true;
        return false;
    }

    private static boolean pair(int[] userAndTableVals)
    {
        for (int i = 0; i + 1 < userAndTableVals.length; i++)
            if (userAndTableVals[i] == userAndTableVals[i + 1])
                return true;
        return false;
    }

    private static int[] removeDuplicates(int[] arr) {
        return Arrays.stream(arr)
                .distinct()
                .toArray();
    }
}
