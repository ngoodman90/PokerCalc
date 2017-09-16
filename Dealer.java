import java.util.List;
import java.util.LinkedList;

/**
 * Created by Noam on 2017-09-09.
 */
public class Dealer
{

    private int[] tableCardValues = new int[Constants.NUM_OF_TABLE_CARDS];

    public void tableGenerator(int card, int index)
    {

        //set the card at index position
        tableCardValues[index] = card;

        if (index == Constants.NUM_OF_TABLE_CARDS - 1)
        {
            List<Hand> winningHands;
            Table table = Table.getTable();

            table.setTableCardsInHands(tableCardValues);
            winningHands = table.getWinningHands();

            if (winningHands.size() > 1)
            {
                table.findBestHands(winningHands);
            }
        }

        //if the index equals 5, compute the winning hand

        //if index is smaller than 5 call recursively
        tableGenerator(card++, index++);
    }
}
