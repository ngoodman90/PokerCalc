/**
 * Created by Noam on 2017-09-09.
 */

public class Card {

    private SuitEnum suit;
    private RankEnum rank;
    private boolean used;

    public Card(SuitEnum suit, RankEnum rank)
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
        return String.format("SuitEnum: %s, RankEnum: %s\n", suit.toString(), rank.toString());
    }
}

