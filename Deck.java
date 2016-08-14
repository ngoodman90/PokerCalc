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

    private Card[] cards = new Card[52];

    private Deck()
    {
        int counter = 0;
        for (SUIT suit : SUIT.values())
        {
            for (int j = 0; j < 13; j++)
                this.cards[(counter * 13) + j] = new Card(((counter * 13) + j), (j + 2), suit);
            counter++;
        }
    }
    public static Deck getDeck()
    {
        return deck;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }
}
