/**
 * Created by Noam on 7/26/2016.
 */
public class Deck {

    Card[] deck = new Card[52];

    Deck()
    {
        int counter = 0;
        for (SUIT suit : SUIT.values())
        {
            for (int j = 0; j < 13; j++)
                deck[(counter * 13) + j] = new Card(j + 2, suit);
            counter++;
        }
    }
}
