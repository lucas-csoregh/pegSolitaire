public class Hole {
    private int x = 0;
    private int y = 0;
    //private Peg peg;

    private HoleStatus holeStatus;

    public void setHoleStatus(HoleStatus holeStatus) {
        this.holeStatus = holeStatus;
    }

    public HoleStatus getHoleStatus() {
        return holeStatus;
    }

    public boolean pegOrPlayer() {
        return holeStatus.equals(HoleStatus.PEG) || holeStatus.equals(HoleStatus.PLAYER);
    }

    public boolean isVacant() {
        return holeStatus.equals(HoleStatus.VACANT);
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

    public String getCoordinate() {
        String[] strs = {"a", "b", "c", "d", "e", "f", "g"};
        String str = strs[x-1];
        return str + y;
    }

    @Override
    public String toString() {
        return "Hole{" +
                getCoordinate() +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
