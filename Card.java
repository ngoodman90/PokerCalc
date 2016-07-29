/**
 * Created by Noam on 7/26/2016.
 */


public class Card {

    public int value;
    private SUIT suit;
    private boolean used = false;

    Card()
    {
        this.value = 0;
        this.suit = SUIT.spade;
    }

    Card(int v, SUIT s)
    {
        this.value = v;
        this.suit = s;
    }

    public void setCard(int v, SUIT s)
    {
        this.value = v;
        this.suit = s;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void displayCard()
    {
        System.out.printf("Value: %d, Suit: %s\n", this.value, this.suit.toString());
    }
}
