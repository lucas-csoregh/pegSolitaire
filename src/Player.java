import java.util.ArrayList;

public class Player {
    public enum Dir {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    ArrayList<Peg> taken = new ArrayList<>();

    Peg playerPeg;

    public void setPlayerPeg(Peg playerPeg) {
        this.playerPeg = playerPeg;
    }

    public Player(Peg playerPeg) {
        this.playerPeg = playerPeg;
    }

    /*
    public boolean hasPeg() {
        return playerPeg != null;
    }

    Hole up;
    Hole down;
    Hole left;
    Hole right;


    public Hole getUp(Hole[][] grid, int x, int y) {
        return grid[x][y-2];
    }
    public Hole getDown(Hole[][] grid, int x, int y) {
        return grid[x][y+1];
    }
    public Hole getRight(Hole[][] grid, int x, int y) {
        return grid[x+1][y];
    }
    public Hole getLeft(Hole[][] grid, int x, int y) {
        return grid[x-2][y];
    }
    */

    //public void bunnyhop()
    public void jump(Dir direction, Hole board[][]) {
        Hole fromHole = board[playerPeg.getX()][playerPeg.getY()];
        System.out.println("fromHole jump(Dir): " + fromHole);
        // [3][3] is default, just filled it with something to avoid the `.. has might not have been initialized` errors
            // the contents don't matter
        Hole takenPegHole = board[3][3];
        Hole toHole = board[3][3];

        if(direction == Dir.UP) {
            takenPegHole = board[playerPeg.getX()][playerPeg.getY() -1];
            toHole = board[playerPeg.getX()][playerPeg.getY() -2];
        } else if(direction == Dir.DOWN) {
            takenPegHole = board[playerPeg.getX()][playerPeg.getY() +1];
            toHole = board[playerPeg.getX()][playerPeg.getY() +2];
        } else if(direction == Dir.LEFT) {
            takenPegHole = board[playerPeg.getX() -1][playerPeg.getY()];
            toHole = board[playerPeg.getX() -2][playerPeg.getY()];
        } else if(direction == Dir.RIGHT) {
            takenPegHole = board[playerPeg.getX() +1][playerPeg.getY()];
            toHole = board[playerPeg.getX() +2][playerPeg.getY()];
        }

        System.out.println("toHole jump(Dir): " + toHole);

        boolean toHoleIsEmpty = toHole.getHoleStatus() == HoleStatus.VACANT;
        if(toHoleIsEmpty) {
            Board.history.add(fromHole);
            fromHole.setHoleStatus(HoleStatus.VACANT);
            takenPegHole.setHoleStatus(HoleStatus.VACANT);
            taken.add(takenPegHole.takePeg());
            toHole.setHoleStatus(HoleStatus.PLAYER);
            toHole.setPeg(playerPeg);
        }
    }
}
