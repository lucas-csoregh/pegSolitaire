public class Hole {
    private int x = 0;
    private int y = 0;
    private Peg peg;

    private HoleStatus holeStatus;

    public void setHoleStatus(HoleStatus holeStatus) {
        this.holeStatus = holeStatus;
    }

    public HoleStatus getHoleStatus() {
        return holeStatus;
    }

    public void setPeg(Peg peg) {
        this.peg = peg;
    }

    public Peg getPeg() {
        return peg;
    }
    public Peg takePeg() {
        Peg taken = peg;
        peg = null;
        // TODO remove the appropriate peg from the `private Peg[] pegs;` var in Board.java
        return taken;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public Hole(int x, int y, HoleStatus holeStatus) {
        this.x = x;
        this.y = y;
        this.holeStatus = holeStatus;
    }

    public void isEndGame() {
        // TODO berekenen of er meer dan 1 knikker over is
    }

    public boolean isEmpty() {
        return this.peg == null && this.holeStatus == HoleStatus.EMPTY;
    }


    @Override
    public String toString() {
        String[] strs = {"a", "b", "c", "d", "e", "f", "g"};
        String str = strs[x-1];
        return str + y;
    }
}
