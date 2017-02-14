import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Noam on 7/27/2016.
 */
public class Table {

    private static final Table table = new Table();

    public static Table getTable(){return table;}

    private Table(){}


    public void startCalculation(ArrayList<Hand> hands)
    {
        randomizeTableCards(hands);
    }

    private void randomizeTableCards(ArrayList<Hand> hands) {
        ArrayList<Hand> winningHands = new ArrayList<>();
        int maxValue;
        int[] cardIterator = new int[5];

        for (cardIterator[0] = 0; cardIterator[0] < Constants.NUM_OF_CARDS_IN_DECK; cardIterator[0]++) {
            setTableCardInHands(0, hands, cardIterator);
            for (cardIterator[1] = cardIterator[0] + 1; cardIterator[1] < Constants.NUM_OF_CARDS_IN_DECK ; cardIterator[1]++) {
                setTableCardInHands(1, hands, cardIterator);
                for (cardIterator[2] = cardIterator[1] + 1; cardIterator[2] < Constants.NUM_OF_CARDS_IN_DECK; cardIterator[2]++) {
                    setTableCardInHands(2, hands, cardIterator);
                    for (cardIterator[3] = cardIterator[2] + 1; cardIterator[3] < Constants.NUM_OF_CARDS_IN_DECK; cardIterator[3]++){
                        setTableCardInHands(3, hands, cardIterator);
                        for (cardIterator[4] = cardIterator[3] + 1; cardIterator[4] < Constants.NUM_OF_CARDS_IN_DECK; cardIterator[4]++) {
                            setTableCardInHands(4, hands, cardIterator);
                            maxValue = 0;
                            for (Hand hand : hands)
                            {
                                hand.setHandValue();
                                if (hand.getCurrentHandValue() > maxValue)
                                {
                                    maxValue = hand.getCurrentHandValue();
                                    //creating a new list with current hand in it
                                    //TODO change to something efficient e.g just the hand indexes
                                    winningHands = new ArrayList<>(Arrays.asList(hand));
                                }
                                else if (hand.getCurrentHandValue() == maxValue)
                                    winningHands.add(hand);
                            }
                            if (winningHands.size() > 1)
                                findBestHands(winningHands, maxValue);
                            for (Hand winningHand : winningHands)
                                winningHand.incrementHandsWon();
                            Deck.getDeck().getCard(cardIterator[4]).setUsed(false);
                        }
                        Deck.getDeck().getCard(cardIterator[3]).setUsed(false);
                    }
                    Deck.getDeck().getCard(cardIterator[2]).setUsed(false);
                }
                Deck.getDeck().getCard(cardIterator[1]).setUsed(false);
            }
            Deck.getDeck().getCard(cardIterator[0]).setUsed(false);
        }
        printStatistics(hands);
    }

    private int setTableCard(int cardNum)
    {
        while (Deck.getDeck().getCard(cardNum).isUsed())
            cardNum++;
        Deck.getDeck().getCard(cardNum).setUsed(true);
        return cardNum;
    }

    private void setTableCardInHands(int index, ArrayList<Hand> hands, int[] cardIterator)
    {
        cardIterator[index] = setTableCard(cardIterator[index]);
        for (Hand hand : hands)
            hand.setTableCard(index + 2, cardIterator[index]);
    }

    private void findBestHands(ArrayList<Hand> winningHands, int maxValue)
    {
        switch (maxValue)
        {
            case 1://High Card

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
}
