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
                   gamestate[x][y].setHoleStatus(Hole.Status.PLAYER);
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
                if(gamestate[x][y].getHoleStatus().equals(Hole.Status.PLAYER)) {
                    this.x = x;
                    this.y = y;
                    System.out.printf("playerpos( chess: %s | x:%d | y:%d )\n", Board.getChessCoordinate(x, y), x, y);
                }
            }
        }
    }




    public boolean takePeg(Hole[][] gamestate, int x, int y) {
        Hole fromHole = gamestate[this.x][this.y];
        Hole toHole = gamestate[x][y];
        Hole takenPegHole = null;


        boolean equalY = toHole.getY() == fromHole.getY();
        boolean toHoleIsRight = (toHole.getX() +2) == fromHole.getX() && equalY;
        if(toHoleIsRight && toHole.isVacant()) {
            takenPegHole = gamestate[x+1][y];
        }
        boolean toHoleIsLeft = (toHole.getX() -2) == fromHole.getX() && equalY;
        if(toHoleIsLeft && toHole.isVacant()) {
            takenPegHole = gamestate[x-1][y];
        }

        boolean equalX = toHole.getX() == fromHole.getX();
        boolean toHoleIsUp = (toHole.getY() -2) == fromHole.getY() && equalX;
        if(toHoleIsUp && toHole.isVacant()) {
            takenPegHole = gamestate[x][y-1];
        }
        boolean toHoleIsDown = (toHole.getY() +2) == fromHole.getY() && equalX;
        if(toHoleIsDown && toHole.isVacant()) {
            takenPegHole = gamestate[x][y+1];
        }


        if(takenPegHole != null && takenPegHole.isPeg()) {
            fromHole.setHoleStatus(Hole.Status.VACANT);
            takenPegHole.setHoleStatus(Hole.Status.VACANT);
            toHole.setHoleStatus(Hole.Status.PLAYER);
            return true;
        } else {
            return false;
        }
    }
}
