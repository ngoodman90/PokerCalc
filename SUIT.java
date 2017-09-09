/**
 * Created by Noam on 7/26/2016.
 */
public enum Suit {
    SPADE(1),
    HEART(2),
    DIAMOND(3),
    CLUB(4);


    private final int value;

    Suit(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}