/**
 * Created by Noam on 2017-09-09.
 */

public class Card {

    private Suit    suit;
    private Rank    rank;
    private boolean used;

    public Card(Suit suit, Rank rank)
    {
        this.suit = suit;
        this.rank = rank;
    }

    public int getValue()
    {
        return suit.getValue() * rank.getValue();
    }

    public boolean isUsed() {return used;}

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String toString()
    {
        return String.format("Suit: %s, Rank: %s", suit.toString(), rank.toString());
    }
}

