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
        if (this.value <= 10)
            System.out.printf("%d of  %s\n", this.value, this.suit.toString() + "s");
        else
        {
            String string_value = "";
            switch (this.value) {
                case 11:
                    string_value = "Jack";
                    break;
                case 12:
                    string_value = "Queen";
                    break;
                case 13:
                    string_value = "King";
                    break;
                case 14:
                    string_value = "Ace";
                    break;
            }
            System.out.printf("%s of %s\n", string_value, this.suit.toString() + "s");
        }
    }
}
