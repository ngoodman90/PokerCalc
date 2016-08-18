import java.util.ArrayList;
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
        int[][] userAndTableVals = new int[hands.size()][7];

        setUserCards(hands, userAndTableCards, userAndTableVals);
        randomizeTableCards(hands, userAndTableCards, userAndTableVals);
    }

    private void randomizeTableCards(ArrayList<Hand> hands, int[][] userAndTableCards, int[][] userAndTableVals) {
        int[] winningHands = new int[PokerCalc.MAX_HANDS];
        int maxValue;
        int maxIndex;
        int currentHandValue;
        int numberOfWinningHands = 0;
        int[] indexes = new int[5];
        int[] tempUserAndTableCards;
        int[] tempUserAndTableVals;

        for (indexes[0] = 0; indexes[0] < PokerCalc.NUM_OF_CARDS_IN_DECK; indexes[0]++)
        {
            indexes[0] = setTableCard(indexes[0]);
            for (int i = 0; i < hands.size(); i++)
            {
                userAndTableCards[i][2] = indexes[0];
                userAndTableVals[i][2] = indexes[0] % PokerCalc.NUM_OF_CARDS_IN_SUIT;
            }
            for (indexes[1] = indexes[0] + 1; indexes[1] < PokerCalc.NUM_OF_CARDS_IN_DECK ; indexes[1]++)
            {
                indexes[1] = setTableCard(indexes[1]);
                for (int i = 0; i < hands.size(); i++)
                {
                    userAndTableCards[i][3] = indexes[1];
                    userAndTableVals[i][3] = indexes[1] % PokerCalc.NUM_OF_CARDS_IN_SUIT;
                }
                for (indexes[2] = indexes[1] + 1; indexes[2] < PokerCalc.NUM_OF_CARDS_IN_DECK; indexes[2]++) {
                    indexes[2] = setTableCard(indexes[2]);
                    for (int i = 0; i < hands.size(); i++)
                    {
                        userAndTableCards[i][4] = indexes[2];
                        userAndTableVals[i][4] = indexes[2] % PokerCalc.NUM_OF_CARDS_IN_SUIT;
                    }
                    for (indexes[3] = indexes[2] + 1; indexes[3] < PokerCalc.NUM_OF_CARDS_IN_DECK; indexes[3]++)
                    {
                        indexes[3] = setTableCard(indexes[3]);
                        for (int i = 0; i < hands.size(); i++)
                        {
                            userAndTableCards[i][5] = indexes[3];
                            userAndTableVals[i][5] = indexes[3] % PokerCalc.NUM_OF_CARDS_IN_SUIT;
                        }
                            for (indexes[4] = indexes[3] + 1; indexes[4] < PokerCalc.NUM_OF_CARDS_IN_DECK; indexes[4]++)
                        {
                            indexes[4] = setTableCard(indexes[4]);
                            for (int i = 0; i < hands.size(); i++)
                            {
                                userAndTableCards[i][6] = indexes[4];
                                userAndTableVals[i][6] = indexes[4] % PokerCalc.NUM_OF_CARDS_IN_SUIT;
                            }
                            maxValue = 0;
                            for (int i = 0; i < hands.size(); i++)
                            {
                                tempUserAndTableCards = userAndTableCards[i];
                                tempUserAndTableVals = userAndTableVals[i];
                                sort(tempUserAndTableCards);
                                sort(tempUserAndTableVals);

                                currentHandValue = handValue(tempUserAndTableCards, tempUserAndTableVals);
                                if (currentHandValue > maxValue)
                                {
                                    maxValue = currentHandValue;
                                    maxIndex = i;
                                    winningHands = new int[PokerCalc.MAX_HANDS];
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

    private void setUserCards(ArrayList<Hand> hands, int[][] userAndTableCards, int[][] userAndTableVals) {
        int iterator = 0;
        for (Hand hand : hands)
        {
            //first two indexes store the user hand which doesnt change, the rest are the table cards
            userAndTableCards[iterator][0] = hand.getCard1().getCardNumber();
            userAndTableCards[iterator][1] = hand.getCard2().getCardNumber();
            userAndTableVals[iterator][0] = hand.getCard1().getCardNumber() % PokerCalc.NUM_OF_CARDS_IN_SUIT;
            userAndTableVals[iterator][1] = hand.getCard2().getCardNumber() % PokerCalc.NUM_OF_CARDS_IN_SUIT;
            iterator++;
        }
    }

    private int setTableCard(int cardNum)
    {
        while (Deck.getDeck().getCard(cardNum).isUsed())
            cardNum++;
        Deck.getDeck().getCard(cardNum).setUsed(true);
        return cardNum;
    }

    private int handValue(int[] userAndTableCards, int[] userAndTableVals)
    {
        int ans1, ans2;
        ans1 = (pair(userAndTableVals) ?
                (twoPair(userAndTableVals) ?
                        (threeOfAKind(userAndTableVals) ?
                                (fullHouse(userAndTableVals) ?
                                        (fourOfAKind(userAndTableVals) ?  8 : 7)//4 of a kind or full house
                                        : 4)//3 of a kind
                                : 3)//two pair
                        : 2)//pair
                : 1);//high card

        ans2 = (straight(userAndTableVals) ?
                (flush(userAndTableCards) ?
                        (straightFlush(userAndTableCards) ?
                                (royalFlush(userAndTableCards) ? 10 : 9)//royal or straight flush
                                : 6)//flush
                        : 5)//straight
                : (flush(userAndTableCards) ? 6 : 0));//flush or nothing
        return (ans1 > ans2 ? ans1 : ans2);
    }

    private boolean royalFlush(int[] userAndTableCards)
    {
        for (int i = 0; i < 3; i++)
            if ((userAndTableCards[i] % PokerCalc.NUM_OF_CARDS_IN_SUIT == 8) //first of 5 cards is a 10
                    && (userAndTableCards[i + 4] % PokerCalc.NUM_OF_CARDS_IN_SUIT == 12) // last of 5 cards is an A
                    && (userAndTableCards[i] / PokerCalc.NUM_OF_CARDS_IN_SUIT == userAndTableCards[i + 4] / PokerCalc.NUM_OF_CARDS_IN_SUIT)) // All five cards are in the same suit
                return true;
        return false;
    }

    private boolean straightFlush(int[] userAndTableCards)
    {
        for (int i = 0; i < 3; i++)
        {
            if ((userAndTableCards[i] + 4 == userAndTableCards[i + 4])
                && (userAndTableCards[i] / PokerCalc.NUM_OF_CARDS_IN_SUIT == userAndTableCards[i + 4] / PokerCalc.NUM_OF_CARDS_IN_SUIT))
                return true;
        }
        return false;
    }

    private boolean fourOfAKind(int[] userAndTableVals)
    {
        for (int i = 0; i < 3; i++)
        {
            if (userAndTableVals[i] == userAndTableVals[i + 3])
                return true;
        }
        return false;
    }

    private boolean fullHouse(int[] userAndTableVals)
    {
        for (int i = 0; i < 3; i++)
        {
            if (userAndTableVals[i] == userAndTableVals[i + 3])
            {
                for (int j = i + 3; j < 7; j++)
                    if (userAndTableVals[j] == userAndTableVals[j + 1])
                        return true;
            }
            if (userAndTableVals[i] == userAndTableVals[i + 2])
            {
                for (int j = i + 2; j < 7; j++)
                    if (userAndTableVals[j] == userAndTableVals[j + 2])
                        return true;
            }
        }
        return false;
    }

    private boolean flush(int[] userAndTableCards)
    {
        for (int i = 0; i < 3; i++)
        {
            if ((userAndTableCards[i] / PokerCalc.NUM_OF_CARDS_IN_SUIT == userAndTableCards[i + 1] / PokerCalc.NUM_OF_CARDS_IN_SUIT)
                && (userAndTableCards[i] / PokerCalc.NUM_OF_CARDS_IN_SUIT == userAndTableCards[i + 2] / PokerCalc.NUM_OF_CARDS_IN_SUIT)
                && (userAndTableCards[i] / PokerCalc.NUM_OF_CARDS_IN_SUIT == userAndTableCards[i + 3] / PokerCalc.NUM_OF_CARDS_IN_SUIT)
                && (userAndTableCards[i] / PokerCalc.NUM_OF_CARDS_IN_SUIT == userAndTableCards[i + 4] / PokerCalc.NUM_OF_CARDS_IN_SUIT))
                return true;

        }
        return false;
    }

    private boolean straight(int[] userAndTableVals)
    {
        /*TODO delete duplicates and check case were A through 5 is the straight*/
        for (int i = 0; i < 3; i++)
        {
            if ((userAndTableVals[i] + 1 == userAndTableVals[i + 1])
                && (userAndTableVals[i] + 2 == userAndTableVals[i + 2])
                && (userAndTableVals[i] + 3 == userAndTableVals[i + 3])
                && (userAndTableVals[i] + 4 == userAndTableVals[i + 4]))
                return true;

        }
        return false;
    }

    private boolean threeOfAKind(int[] userAndTableVals)
    {
        for (int i = 0; i < 4; i++)
        {
            if (userAndTableVals[i] == userAndTableVals[i + 2])
                return true;
        }
        return false;
    }

    private boolean twoPair(int[] userAndTableVals)
    {
        for (int i = 0; i < 3; i++)
        {
            if (userAndTableVals[i] == userAndTableVals[i + 1])
            {
                for (int j = i + 2; j < 6; j++)
                    if (userAndTableVals[j] == userAndTableVals[j + 1])
                        return true;
            }
        }
        return false;
    }

    private boolean pair(int[] userAndTableVals)
    {
        for (int i = 0; i < 3; i++)
        {
            if (userAndTableVals[i] == userAndTableVals[i + 1])
                return true;
        }
        return false;
    }
}
