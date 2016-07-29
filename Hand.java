import java.util.Comparator;

/**
 * Created by Noam on 7/26/2016.
 */
public class Hand{

    private Card card1;
    private Card card2;

    Hand(Card card1, Card card2)
    {
        this.card1 = card1;
        this.card2 = card2;
    }

    public Card getCard1() {
        return card1;
    }

    public void setCard1(Card card1) {
        this.card1 = card1;
    }

    public Card getCard2() {
        return card2;
    }

    public void setCard2(Card card2) {
        this.card2 = card2;
    }

    public void displayHand()
    {
        System.out.println("First card: ");
        this.card1.displayCard();
        System.out.println("Second card: ");
        this.card2.displayCard();
    }

}
