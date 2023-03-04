public class Peg {
    private int x;
    private int y;

    int id;
    static int pegCount =0;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Peg(int x, int y) {
        this.x = x;
        this.y = y;
        pegCount++;
        id = pegCount;
    }

    public Peg() {
        this.x = 0;
        this.y = 0;
        pegCount++;
        id = pegCount;
    }

    @Override
    public String toString() {
        String[] strs = {"a", "b", "c", "d", "e", "f", "g"};
        String str = strs[x-1];
        return "Peg{" +
                "x=" + str +
                ", y=" + y +
                '}';
    }
}
