public class Peg {
    private int x;
    private int y;

    public Peg(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Peg() {
        this.x = 0;
        this.y = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        String[] strs = {"???", "b", "c", "d", "e", "f", "g", "!!!"};
        String str = strs[x];
        return "Peg{" +
                "x=" + str +
                ", y=" + y +
                '}';
    }
}
