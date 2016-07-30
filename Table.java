/**
 * Created by Noam on 7/27/2016.
 */
public class Table {

    private static  Table table = new Table();

    private static int[] tableCardNumbers = new int[5];
    private static boolean isRoyalFlushPossible;
    private static boolean isStraightFlushPossible;
    private static boolean isFourOfAKindAndFullHousePossible;
    private static boolean isFlushPossible;
    private static boolean isStraightPossible;



    /*
    * This is the variable that holds the numbers of the cards on the table.
    * We assume that any time a card is changed, the array is sorted.
    */

    public Table getTable()
    {
        return table;
    }

    public Table()
    {

    }

    private void isRoyalFlushPossible()
    {
        int tenOfSuit;
        int aceOfSuit;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                tenOfSuit = ((j * 13) + 8);
                aceOfSuit = ((j * 13) + 12);
                if ((tenOfSuit <= tableCardNumbers[i]) && (tableCardNumbers[i + 2] <= aceOfSuit)) {
                    isRoyalFlushPossible = true;
                    return;
                }
            }
        }
    }

    private void isStraightFlushPossible()
    {
        for (int i = 0; i < 3; i++) {
            if ((tableCardNumbers[i] + 4 <= tableCardNumbers[i + 2]) &&
                    ((tableCardNumbers[i]) / 13) == (tableCardNumbers[i + 2] / 13)) {
                isStraightFlushPossible = true;
                return;
            }
        }
    }

    private void isFourOfAKindAndFullHousePossible()
    {
        for (int i = 0; i < 4; i++) {
            if ((tableCardNumbers[i] % 13) == (tableCardNumbers[i + 1] % 13))
            {
                isFourOfAKindAndFullHousePossible = true;
                return;
            }
        }
    }

    private void isFlushPossible()
    {
        for (int i = 0; i < 3; i++) {
            if ((tableCardNumbers[i] / 13) == (tableCardNumbers[i + 2] / 13))
            {
                isFlushPossible = true;
                return;
            }
        }
    }

    private void isStraightPossible()
    {
        /* TODO */
    }

    public void resetPossibilities()
    {
        isRoyalFlushPossible = false;
        isStraightFlushPossible = false;
        isFourOfAKindAndFullHousePossible = false;
        isFlushPossible = false;
        isStraightPossible = false;

        isRoyalFlushPossible();
        isStraightFlushPossible();
        isFourOfAKindAndFullHousePossible();
        isFlushPossible();
        isStraightPossible();


    }
}
