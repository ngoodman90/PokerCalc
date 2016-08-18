/**
 * Created by Noam on 7/26/2016.
 */


public class Card {

    private int cardNumber;
    public int value;
    private SUIT suit;
    private boolean used;

    Card(int cardNumber, int v, SUIT s)
    {
        this.cardNumber = cardNumber;
        this.value = v;
        this.suit = s;
        this.used = false;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public boolean isUsed() {return used;}

    public void setUsed(boolean used) {this.used = used;}

    public void displayCard()
    {
        if (this.value <= 10)
            System.out.printf("%d) %d of %s\n", this.getCardNumber(), this.value, this.suit.toString() + "s");
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
            System.out.printf("%d) %s of %s\n", this.getCardNumber(), string_value, this.suit.toString() + "s");
        }
    }
}
