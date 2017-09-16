import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Noam on 7/27/2016.
 */
public class Table {

	private ArrayList<Hand> hands;

    private static final Table table = new Table();

    public static Table getTable(){return table;}

    private Table(){}

    public void setTableHands(ArrayList<Hand> hands) {this.hands = hands;}

    public void startCalculation()
    {
        randomizeTableCards();
    }

    private void randomizeTableCards() {
        ArrayList<Integer> winningHandsIndexes = new ArrayList<>();
        ArrayList<Hand> winningHands = new ArrayList<>();
        int maxValue;
        int handCounter = 0;
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
                            if (!setTableCardInHands(4, hands, cardIterator))
                                continue;
                            handCounter++;
                            maxValue = 0;
                            for (Hand hand : hands)
                            {
                                hand.setHandValue();
                                if (hand.getCurrentHandValue() > maxValue)
                                {
                                    maxValue = hand.getCurrentHandValue();
                                    //creating a new list with current hand in it
                                    winningHandsIndexes = new ArrayList<>();
                                    winningHandsIndexes.add(hand.getId());
                                }
                                else if (hand.getCurrentHandValue() == maxValue)
                                    winningHandsIndexes.add(hand.getId());
                            }
                            if (winningHandsIndexes.size() > 1)
                                winningHands = findBestHands(winningHandsIndexes, maxValue);
                            if (winningHands.size() < hands.size())
                                winningHands.forEach(winningHand -> winningHand.incrementHandsWon());
                            Deck1.getDeck1().getCard(cardIterator[4]).setUsed(false);
                        }
                        Deck1.getDeck1().getCard(cardIterator[3]).setUsed(false);
                    }
                    Deck1.getDeck1().getCard(cardIterator[2]).setUsed(false);
                }
                Deck1.getDeck1().getCard(cardIterator[1]).setUsed(false);
            }
            Deck1.getDeck1().getCard(cardIterator[0]).setUsed(false);
        }
        System.out.println("Hand Counter :" + handCounter);
        printStatistics(hands);
    }

    public List<Hand> getWinningHands()
    {
        HandValueEnum maxValue;
        HandValueEnum currentHandValueEnum;
        List<Hand> winningHands = new LinkedList<>();

        maxValue = HandValueEnum.HIGH_CARD;

        for (Hand hand : hands)
        {
            currentHandValueEnum = hand.getHandValue();
            hand.setCurrentHandValue(currentHandValueEnum);

            if (currentHandValueEnum.getValue() > maxValue.getValue())
            {
                maxValue = currentHandValueEnum;
                winningHands = new LinkedList<>();
                winningHands.add(hand);
            }

            if (currentHandValueEnum.getValue() == maxValue.getValue())
            {
                winningHands.add(hand);
            }
        }
        return winningHands;
    }

    public void recursiveTableGenerator(int[] tableCards, int positionIndex, int cardIndex)
    {
        if (positionIndex == 5)
            analyzeTable();
        else if (cardIndex >= Constants.NUM_OF_CARDS_IN_DECK)
            return ;
        for (tableCards[positionIndex] = tableCards[positionIndex - 1] + 1;
             tableCards[positionIndex] < Constants.NUM_OF_CARDS_IN_DECK;
             tableCards[positionIndex]++)
        {
            if (Deck1.isUsed(cardIndex))
                continue;
            recursiveTableGenerator(tableCards, positionIndex + 1, cardIndex + 1);
        }
    }



    private int setTableCard(int cardNum)
    {
        while (cardNum < Constants.NUM_OF_CARDS_IN_DECK && Deck1.getDeck1().getCard(cardNum).isUsed())
            cardNum++;
        if (cardNum < Constants.NUM_OF_CARDS_IN_DECK)
            Deck1.getDeck1().getCard(cardNum).setUsed(true);
        return cardNum;
    }

    private boolean setTableCardInHands(int index, ArrayList<Hand> hands, int[] cardIterator)
    {
        if ((cardIterator[index] = setTableCard(cardIterator[index])) >= Constants.NUM_OF_CARDS_IN_DECK)
            return false;
        for (Hand hand : hands)
            hand.setTableCard(index + 2, cardIterator[index]);
        return true;
    }

    public void setTableCardsInHands(int[] tableCards)
    {
        for (Hand hand : hands)
        {
            hand.setTableCards(tableCards);
        }
    }

    public ArrayList<Hand> findBestHands(ArrayList<Integer> winningHandsIndexes, int winningHandValue)
    {
    	ArrayList<Hand> winningHands = new ArrayList<>(winningHandsIndexes.size());
        ArrayList<Hand> newWinningHands = new ArrayList<>();
        int maxVal;
        for (Integer i : winningHandsIndexes)
            winningHands.add(hands.get(i));
        switch (winningHandValue)
        {
            case 1://High Card2
                for (int i = 6; i > 1; i--)
                {
                    maxVal = 0;
                    for (Hand hand : winningHands)
                        if (hand.getSortedVals()[i] > maxVal)
                            maxVal = hand.getSortedVals()[i];
                    for (Hand hand : winningHands)
                        if (hand.getSortedVals()[i] == maxVal)
                            newWinningHands.add(hand);
                    winningHands = newWinningHands;
                    if (winningHands.size() == 1)
                        break;
                    newWinningHands = new ArrayList<>();
                }
                break;
            case 2://Pair
                for (int i = 0; i < 4; i++)
                {
                    maxVal = 0;
                    for (Hand hand : winningHands)
                        if (hand.getPairValues()[i] > maxVal)
                            maxVal = hand.getPairValues()[i];
                    for (Hand hand : winningHands)
                        if (hand.getPairValues()[i] == maxVal)
                            newWinningHands.add(hand);
                    winningHands = newWinningHands;
                    if (winningHands.size() == 1)
                        break;
                    newWinningHands = new ArrayList<>();
                }
                break;
            case 3://Two Pair
                for (int i = 0; i < 3; i++)
                {
                    maxVal = 0;
                    for (Hand hand : winningHands)
                        if (hand.getTwoPairValues()[i] > maxVal)
                            maxVal = hand.getTwoPairValues()[i];
                    for (Hand hand : winningHands)
                        if (hand.getTwoPairValues()[i] == maxVal)
                            newWinningHands.add(hand);
                    winningHands = newWinningHands;
                    if (winningHands.size() == 1)
                        break;
                    newWinningHands = new ArrayList<>();
                }
                break;
            case 4://Three of a Kind
                for (int i = 0; i < 3; i++)
                {
                    maxVal = 0;
                    for (Hand hand : winningHands)
                        if (hand.getThreeOfAKindValues()[i] > maxVal)
                            maxVal = hand.getThreeOfAKindValues()[i];
                    for (Hand hand : winningHands)
                        if (hand.getThreeOfAKindValues()[i] == maxVal)
                            newWinningHands.add(hand);
                    winningHands = newWinningHands;
                    if (winningHands.size() == 1)
                        break;
                    newWinningHands = new ArrayList<>();
                }
                break;
            case 5://Straight
                maxVal = 0;
                for (Hand hand : winningHands)
                    if (hand.getStraightValue() > maxVal)
                        maxVal = hand.getStraightValue();
                for (Hand hand : winningHands)
                    if (hand.getStraightValue() == maxVal)
                        newWinningHands.add(hand);
                winningHands = newWinningHands;
                break;
            case 6://Flush
                for (int i = 0; i < 5; i++)
                {
                    maxVal = 0;
                    for (Hand hand : winningHands)
                        if (hand.getFlushValues()[i] > maxVal)
                            maxVal = hand.getFlushValues()[i];
                    for (Hand hand : winningHands)
                        if (hand.getFlushValues()[i] == maxVal)
                            newWinningHands.add(hand);
                    winningHands = newWinningHands;
                    if (winningHands.size() == 1)
                        break;
                    newWinningHands = new ArrayList<>();
                }
                break;
            case 7://Full House
                for (int i = 0; i < 2; i++)
                {
                    maxVal = 0;
                    for (Hand hand : winningHands)
                        if (hand.getFullHouseValues()[i] > maxVal)
                            maxVal = hand.getFullHouseValues()[i];
                    for (Hand hand : winningHands)
                        if (hand.getFullHouseValues()[i] == maxVal)
                            newWinningHands.add(hand);
                    winningHands = newWinningHands;
                    newWinningHands = new ArrayList<>();
                }
                break;
            case 8://Four of a Kind
                for (int i = 0; i < 2; i++)
                {
                    maxVal = 0;
                    for (Hand hand : winningHands)
                        if (hand.getfourOfAKindValues()[i] > maxVal)
                            maxVal = hand.getfourOfAKindValues()[i];
                    for (Hand hand : winningHands)
                        if (hand.getfourOfAKindValues()[i] == maxVal)
                            newWinningHands.add(hand);
                    winningHands = newWinningHands;
                    newWinningHands = new ArrayList<>();
                }
                break;
            case 9://Straight Flush
                maxVal = 0;
                for (Hand hand : winningHands)
                    if (hand.getStraightFlushValue() > maxVal)
                        maxVal = hand.getStraightFlushValue();
                for (Hand hand : winningHands)
                    if (hand.getStraightFlushValue() == maxVal)
                        newWinningHands.add(hand);
                winningHands = newWinningHands;
                break;
            case 10://Royal Flush
                break;
        }
        return winningHands;
    }

    private void printStatistics(ArrayList<Hand> hands)
    {
        long sum = 0;
        for (Hand hand : hands)
            sum += hand.getHandsWon();
        System.out.printf("Hands played: %d\n", sum);
        long i = 0;
        for (Hand hand : hands)
        {
            double percent = (((double)hand.getHandsWon() / (double)sum) * 100f);
            System.out.printf("Hand number %d won %d out of %d hands.\n", ++i, hand.getHandsWon(), sum);
            System.out.println(Math.round(percent) + "%");
        }
    }
}
