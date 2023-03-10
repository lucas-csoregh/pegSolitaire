public class Hole {
    public enum Status {
        PEG,
        OFF_LIMITS,
        RULER,
        VACANT,
        PLAYER
    }
    
    private int x = 0;
    private int y = 0;

    private Status status;

    public void setHoleStatus(Status status) {
        this.status = status;
    }

    public Status getHoleStatus() {
        return status;
    }

    public boolean isPegOrPlayer() {
        return status.equals(Hole.Status.PEG) || status.equals(Hole.Status.PLAYER);
    }

    public boolean isPeg() {
        return status.equals(Hole.Status.PEG);
    }
    public boolean isVacant() {
        return status.equals(Hole.Status.VACANT);
    }

    public Hole() {
        this.x = 0;
        this.y = 0;
        this.status = Status.OFF_LIMITS;
    }

    public Hole(int x, int y, Status holeStatus) {
        this.x = x;
        this.y = y;
        this.status = holeStatus;
    }

    public int getX() { return x; }
    public int getY() { return y; }


    @Override
    public String toString() {
        return "Hole{" +
                getCoordinate() +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
    public String getCoordinate() {
        String[] strs = {"a", "b", "c", "d", "e", "f", "g"};
        String str = strs[x-1];
        return str + y;
    }


}
