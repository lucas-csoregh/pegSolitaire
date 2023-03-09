import java.util.ArrayList;

public class Player {
    public enum Dir {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public Player(Hole[][] gamestate, int x, int y) {
        int size = gamestate.length;
        for(int Y=0; Y<size; Y++) {
            for (int X = 0; X < size; X++) {
               if(X==x && Y==y) {
                   gamestate[x][y].setHoleStatus(HoleStatus.PLAYER);
               }
            }
        }
    }

    private int x = 0;
    private int y = 0;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void getPlayerPos(Hole[][] gamestate) {
        int size = gamestate.length;
        for(int y=0; y<size; y++) {
            for(int x=0; x<size; x++) {
                if(gamestate[x][y].getHoleStatus().equals(HoleStatus.PLAYER)) {
                    this.x = x;
                    this.y = y;
                    System.out.printf("playerpos(x:%d, y:%d)\n", x, y);
                }
            }
        }
    }

    //public void bunnyhop()
    public void jump(Dir direction, Hole gamestate[][]) {
        Hole fromHole = gamestate[x][y];
        // [3][3] is default, just filled it with something to avoid the `.. has might not have been initialized` errors
            // the contents don't matter
        Hole takenPegHole = gamestate[3][3];
        Hole toHole = gamestate[3][3];

        //System.out.println("fromHole jump(Dir): " + fromHole);

        if(direction == Dir.UP) {
            takenPegHole = gamestate[x][y -1];
            toHole = gamestate[x][y -2];
        } else if(direction == Dir.DOWN) {
            takenPegHole = gamestate[x][y +1];
            toHole = gamestate[x][y +2];
        } else if(direction == Dir.LEFT) {
            takenPegHole = gamestate[x -1][y];
            toHole = gamestate[x -2][y];
        } else if(direction == Dir.RIGHT) {
            takenPegHole = gamestate[x +1][y];
            toHole = gamestate[x +2][y];
        }

        //System.out.println("toHole jump(Dir): " + toHole);

        boolean toHoleIsEmpty = toHole.getHoleStatus() == HoleStatus.VACANT;
        if(toHoleIsEmpty) {
            //Board.history.add(fromHole);
            fromHole.setHoleStatus(HoleStatus.VACANT);
            takenPegHole.setHoleStatus(HoleStatus.VACANT);
            //taken.add(takenPegHole.takePeg());
            toHole.setHoleStatus(HoleStatus.PLAYER);
        }
    }
}
