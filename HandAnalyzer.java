import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.exit;

/**
 * Created by Noam on 2017-09-15.
 */
public class HandAnalyzer
{

    public static final Logger logger = Logger.getLogger(HandAnalyzer.class.getName());

    public static List<Hand> findBestHands(List<Hand> winningHands)
    {
        if (winningHands.isEmpty())
        {
            logger.log(Level.INFO, "No winning hands, nothing to analyze");
            exit(-1);
        }

        HandValueEnum handValueEnum = winningHands.get(0).getCurrentHandValue();

        for (Hand hand : winningHands)
        {
            if (hand.getCurrentHandValue() != handValueEnum)
            {
                logger.log(Level.INFO, "Different values in winning hands");
                exit(-1);
            }
        }

        return getWinners(winningHands);
    }

    private static List<Hand> getWinners(List<Hand> winningHands)
    {
        int maxVal;
        List<Hand> newWinningHands = new LinkedList<>();

        for (int i = 0; i < winningHands.get(0).getBestCardValues().length; i++)
        {
            maxVal = 0;
            for (Hand hand : winningHands)
            {
                if (hand.getBestCardValues()[i] > maxVal)
                {
                    newWinningHands = new LinkedList<>();
                    newWinningHands.add(hand);
                    maxVal = hand.getBestCardValues()[i];
                }
                if (hand.getBestCardValues()[i] == maxVal)
                {
                    newWinningHands.add(hand);
                }
            }

            winningHands = newWinningHands;

            if (winningHands.size() <= 1)
            {
                break;
            }
        }
        return winningHands;
    }
}





















/*        switch (handValueEnum) {
            case HIGH_CARD:
                winningHands = highCardWinner(winningHands);
                break;
            case PAIR:
                winningHands = pairWinner(winningHands);
                break;
            case TWO_PAIR:
                winningHands = twoPairWinner(winningHands);
                break;
            case THREE_OF_A_KIND:
                winningHands = threeOfAKindWinner(winningHands);
                break;
            case STRAIGHT:
                winningHands = straightWinner(winningHands);
                break;
            case FLUSH:
                winningHands = flushWinner(winningHands);
                break;
            case FULL_HOUSE:
                winningHands = fullHouseWinner(winningHands);
                break;
            case FOUR_OF_A_KIND:
                winningHands = fourOfAKindWInner(winningHands);
                break;
            case STRAIGHT_FLUSH:
                winningHands = straightFlushWinner(winningHands);
                break;
            case ROYAL_FLUSH:
                winningHands = royalFlushWinner(winningHands);
                break;
        }
        return winningHands;



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

    private static List<Hand> royalFlushWinner(List<Hand> winningHands) {
        return null;
    }

    private static List<Hand> straightFlushWinner(List<Hand> winningHands) {
        return null;
    }

    private static List<Hand> fourOfAKindWInner(List<Hand> winningHands) {
        return null;
    }

    private static List<Hand> fullHouseWinner(List<Hand> winningHands) {
        return null;
    }

    private static List<Hand> flushWinner(List<Hand> winningHands) {
        return null;
    }

    private static List<Hand> straightWinner(List<Hand> winningHands) {
        return null;
    }

    private static List<Hand> threeOfAKindWinner(List<Hand> winningHands) {
        return null;
    }

    private static List<Hand> twoPairWinner(List<Hand> winningHands) {
        return null;
    }

    private static List<Hand> pairWinner(List<Hand> winningHands)
    {
        int maxVal;
        List<Hand> newWinningHands = new LinkedList<>();

        for (int i = 0; i < 4; i++) {
            maxVal = 0;
            for (Hand hand : winningHands) {
                if (hand.getPairValues()[i] > maxVal) {
                    maxVal = hand.getPairValues()[i];
                }
            }
            for (Hand hand : winningHands)
            {
                if (hand.getPairValues()[i] == maxVal)
                {
                    newWinningHands.add(hand);
                }
            }

            winningHands = newWinningHands;
            if (winningHands.size() == 1)
                break;
            newWinningHands = new ArrayList<>();
        }
        return null;
    }

    private static List<Hand> highCardWinner(List<Hand> winningHands)
    {
        int maxVal;
        List<Hand> newWinningHands = new LinkedList<>();

        for (int i = Constants.NUM_OF_CARDS_WITH_TABLE - 1; i > Constants.NUM_OF_PLAYER_CARDS - 1; i--)
        {
            maxVal = 0;

            for (Hand hand : winningHands)
            {
                if (hand.getSortedVals()[i] > maxVal)
                {
                    maxVal = hand.getSortedVals()[i];
                }
            }

            for (Hand hand : winningHands)
            {
                if (hand.getSortedVals()[i] == maxVal)
                {
                    newWinningHands.add(hand);
                }
            }
            winningHands = newWinningHands;

            if (winningHands.size() <= 1)
            {
                break;
            }
        }
        return winningHands;
    }*/
