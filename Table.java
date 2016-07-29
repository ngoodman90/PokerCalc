/**
 * Created by Noam on 7/27/2016.
 */
public class Table {

    private Card[] flop = new Card[3];
    private Card turn = new Card();
    private Card river = new Card();
    private boolean isFlop = false;
    private boolean isTurn = false;
    private boolean isRiver = false;

    public Table() {

    }

    public Table(Card[] flop) {
        this.flop = flop;
        this.isFlop = true;
    }

    public Table(Card[] flop, Card turn) {
        this.flop = flop;
        this.turn = turn;
        this.isFlop = true;
        this.isTurn = true;
    }

    public Table(Card[] flop, Card turn, Card river) {
        this.flop = flop;
        this.turn = turn;
        this.river = river;
        this.isFlop = true;
        this.isTurn = true;
        this.isRiver = true;
    }

    public Card[] getFlop() {
        return flop;
    }

    public void setFlop(Card[] flop) {
        this.flop = flop;
    }

    public Card getTurn() {
        return turn;
    }

    public void setTurn(Card turn) {
        this.turn = turn;
    }

    public Card getRiver() {
        return river;
    }

    public void setRiver(Card river) {
        this.river = river;
    }
}
