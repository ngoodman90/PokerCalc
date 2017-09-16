import java.util.ArrayList;

/**
 * Created by Noam on 2017-09-09.
 */
public class Deck {

    private static Deck deck = new Deck();

    private ArrayList<Card> cards = new ArrayList<>(Constants.NUM_OF_CARDS_IN_DECK);

    private Deck()
    {
        for (SuitEnum s : SuitEnum.values())
        {
            for (RankEnum r : RankEnum.values())
            {
                cards.add(new Card(s,r));
            }
        }
    }

    public Deck getDeck()
    {
        return deck;
    }
}
