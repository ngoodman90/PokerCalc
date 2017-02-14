import java.util.ArrayList;

/**
 * Created by Noam on 7/26/2016.
 */
public class Deck {

    /*
    * This is a singleton class, since there is only one deck.
    * This class only has one private variable, which is an array of
    * 52 cards.
    */

    private static Deck deck = new Deck();

    private ArrayList<Card> cards = new ArrayList<>();

    private Deck()
    {
        int counter = 0;
        for (Constants.SUIT suit : Constants.SUIT.values())
        {
            for (int j = 0; j < 13; j++)
                this.cards.add(new Card(((counter * 13) + j), (j + 2), suit));
            counter++;
        }
    }
    public static Deck getDeck()
    {
        return deck;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getCard(int num) {return cards.get(num);}
}
