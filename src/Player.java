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

    /*
    public boolean takePeg(Dir direction, Hole gamestate[][]) {
        Hole fromHole = gamestate[x][y];
        // [3][3] is default, just filled it with something to avoid the 
        // `.. has might not have been initialized` errors
        // the contents don't matter
        Hole takenPegHole = gamestate[3][3];
        Hole toHole = gamestate[3][3];

        //System.out.println("fromHole takePeg(Dir): " + fromHole);
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

        //System.out.println("toHole takePeg(Dir): " + toHole);
        boolean toHoleAvailable = toHole.isVacant();
        if(toHoleAvailable) {
            fromHole.setHoleStatus(Hole.Status.VACANT);
            takenPegHole.setHoleStatus(Hole.Status.VACANT);
            toHole.setHoleStatus(Hole.Status.PLAYER);
            return true;
        }
        return false;
    }

    public boolean toHoleDown(Hole[][] gamestate, Hole toHole, Hole fromHole) {
        boolean equalX = toHole.getX() == fromHole.getX();
        //boolean equalY = toHole.getY() == fromHole.getY();
        Hole bholeDown = new Hole();

        boolean toHoleIsDown = (toHole.getY() +2) == fromHole.getY() && equalX;
        if(toHoleIsDown && toHole.isVacant()) {
            //System.out.println("fromHole is down 2 blocks from toHole");
            bholeDown = gamestate[x][y+1];
            boolean bholeDownCorrectPos = (fromHole.getY()+1) == bholeDown.getY();
            if(bholeDownCorrectPos && bholeDown.isPeg()) {
                //System.out.println("bholeDown is available");
                // MOVE PLAYER
                System.out.println("DOWN");
                return true;
            }
        }


        return false;
    }
    public boolean toHoleUp(Hole[][] gamestate, Hole toHole, Hole fromHole) {
        boolean equalX = toHole.getX() == fromHole.getX();
        //boolean equalY = toHole.getY() == fromHole.getY();
        Hole bholeUp = new Hole();

        boolean toHoleIsUp = (toHole.getY() -2) == fromHole.getY() && equalX;
        if(toHoleIsUp && toHole.isVacant()) {
            //System.out.println("fromHole is Up 2 blocks from toHole");
            bholeUp = gamestate[x][y-1];
            boolean bholeUpCorrectPos = (fromHole.getY()-1) == bholeUp.getY();
            if(bholeUpCorrectPos && bholeUp.isPeg()) {
                //System.out.println("bholeUp is available");
                // MOVE PLAYER
                System.out.println("UP");
                return true;
            }
        }


        return false;
    }
    */


    public boolean toHoleRight(Hole[][] gamestate, Hole toHole, Hole fromHole) {
        boolean equalY = toHole.getY() == fromHole.getY();
        Hole bholeRight = new Hole();

        boolean toHoleIsRight = (toHole.getX() +2) == fromHole.getX() && equalY;
        if(toHoleIsRight && toHole.isVacant()) {
            System.out.println("fromHole is Right 2 blocks from toHole");
            System.out.printf("gamestate.length:%d , x+1: %d\n",  gamestate.length, (x+1));
            if(x+1 < gamestate.length) {
                bholeRight = gamestate[x+1][y];
            } else {
                return false;
            }
            boolean bholeRightCorrectPos = (fromHole.getX()+1) == bholeRight.getX();
            if(bholeRightCorrectPos && bholeRight.isPeg()) {
                //System.out.println("bholeRight is available");
                // MOVE PLAYER
                System.out.println("RIGHT");
                return true;
            }
        }


        return false;
    }

    /*
    public boolean toHoleLeft(Hole[][] gamestate, Hole toHole, Hole fromHole) {
        boolean equalY = toHole.getY() == fromHole.getY();
        Hole bholeLeft = new Hole();

        boolean toHoleIsLeft = (toHole.getX() -2) == fromHole.getX() && equalY;
        if(toHoleIsLeft && toHole.isVacant()) {
            System.out.println("fromHole is Left 2 blocks from toHole");
            if(x-1 >= 2) {
                bholeLeft = gamestate[x - 1][y];
            }
            boolean bholeLeftCorrectPos = (fromHole.getX()-1) == bholeLeft.getX();
            if(bholeLeftCorrectPos && bholeLeft.isPeg()) {
                //System.out.println("bholeLeft is available");
                // MOVE PLAYER
                System.out.println("LEFT");
                return true;
            }
        }


        return false;
    }
    */

    public boolean takePeg(Hole[][] gamestate, int x, int y) {
        Hole fromHole = gamestate[this.x][this.y];
        Hole toHole = gamestate[x][y];
        Hole takenPegHole = null;


        /*
        if(toHoleDown(gamestate, toHole, fromHole)) {
            takenPegHole = gamestate[x][y+1];
        }

        if(toHoleUp(gamestate, toHole, fromHole)) {
            takenPegHole = gamestate[x][y-1];
        }
        */

        if(toHoleRight(gamestate, toHole, fromHole)) {
            takenPegHole = gamestate[x+1][y];
        }

        /*
        if(toHoleLeft(gamestate, toHole, fromHole)) {
            takenPegHole = gamestate[x-1][y];
        }
        */

        if(takenPegHole != null) {
            fromHole.setHoleStatus(Hole.Status.VACANT);
            takenPegHole.setHoleStatus(Hole.Status.VACANT);
            toHole.setHoleStatus(Hole.Status.PLAYER);
            return true;
        } else {
            return false;
        }

        /*
        //System.out.println("fromHole takePeg(Dir): " + fromHole);
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
        */

        //System.out.println("toHole takePeg(Dir): " + toHole);
        /*
        boolean toHoleAvailable = toHole.isVacant();
        if(toHoleAvailable) {
            fromHole.setHoleStatus(Hole.Status.VACANT);
            takenPegHole.setHoleStatus(Hole.Status.VACANT);
            toHole.setHoleStatus(Hole.Status.PLAYER);
            return true;
        }
        */
    }
}
