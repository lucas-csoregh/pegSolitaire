// TODO LATER change the names of everything to stuff that makes more sense like readGamestate instead of refreshBoard, yadayadad
// TODO LATER add detailed documentation for everything

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
        // TODO show the taken pegs in a row of o's under the board, include it w each refresh of the gamestate
        return taken;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public Hole() {
        this.x = 0;
        this.y = 0;
        this.holeStatus = HoleStatus.OFF_LIMITS;
    }

    public Hole(int x, int y, HoleStatus holeStatus) {
        this.x = x;
        this.y = y;
        this.holeStatus = holeStatus;
    }

    public void isEndGame() {
        // TODO berekenen of er meer dan 1 knikker over is
    }

    public boolean isEmpty() {
        return this.peg == null && this.holeStatus == HoleStatus.VACANT;
    }


    @Override
    public String toString() {
        String[] strs = {"a", "b", "c", "d", "e", "f", "g"};
        String str = strs[x-1];
        return str + y;
    }
}
