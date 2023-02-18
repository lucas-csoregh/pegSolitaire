public class Hole {
    private int x = 0;
    private int y = 0;
    private Peg peg;

    public void setPeg(Peg peg) {
        this.peg = peg;
    }

    public Peg getPeg() {
        return peg;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public Hole(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void isEndGame() {
        // berekenen of de knikker in de middelste positie/coordinaat zit
    }

    public boolean isEmpty() {
        return this.peg == null;
    }
}
