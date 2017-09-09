import java.util.ArrayList;

/**
 * Created by Noam on 7/26/2016.
 */
public class Deck1 {

    /*
    * This is a singleton class, since there is only one deck1.
    * This class only has one private variable, which is an array of
    * 52 card2s.
    */

    private static Deck1 deck1 = new Deck1();

    private static ArrayList<Card2> card2s = new ArrayList<>();

    private Deck1()
    {
        int counter = 0;
        for (Constants.SUIT suit : Constants.SUIT.values())
        {
            for (int j = 0; j < 13; j++)
                this.card2s.add(new Card2(((counter * 13) + j), (j + 2), suit));
            counter++;
        }
    }
    public static Deck1 getDeck1()
    {
        return deck1;
    }

    public ArrayList<Card2> getCards() {
        return card2s;
    }

    public Card2 getCard(int num) {return card2s.get(num);}

    public static boolean isUsed(int cardNum)
    {
        return card2s.get(cardNum).isUsed();
    }
}
