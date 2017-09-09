/**
 * Created by Noam on 2017-09-09.
 */
public class Deck {

    private static Deck deck = new Deck();

    private Card[] cards = new Card[Constants.NUM_OF_CARDS_IN_DECK];

    private Deck()
    {
        int cardIndex = 0;
        for (Suit s : Suit.values())
        {
            for (Rank r : Rank.values())
            {
                cards[cardIndex] = new Card(s,r);
                cardIndex++;
            }
        }
    }

    public Deck getDeck()
    {
        return deck;
    }
}
