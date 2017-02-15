import java.util.ArrayList;
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

    public ArrayList<Hand> findBestHands(ArrayList<Integer> winningHandsIndexes, int winningHandValue)
    {
    	ArrayList<Hand> winningHands = new ArrayList<>(winningHandsIndexes.size());
        ArrayList<Hand> newWinningHands = new ArrayList<>();
        int maxVal;
        for (Integer i : winningHandsIndexes)
            winningHands.add(hands.get(i));
        switch (winningHandValue)
        {
            case 1://High Card
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
            sum += hand.gethandsWon();
        System.out.printf("Hands played: %d\n", sum);
        long i = 0;
        for (Hand hand : hands)
            System.out.printf("Hand number %d won %d out of %d hands.\n", ++i, hand.gethandsWon(), sum);
    }
}
