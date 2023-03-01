public class Player {
    public enum Dir {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    Peg playerPeg;

    public Player(Peg playerPeg) {
        this.playerPeg = playerPeg;
    }

    public boolean hasPeg() {
        return playerPeg != null;
    }

    public void jump(Dir direction, Hole[][] board) {
        if(direction == Dir.UP) {
            // check what is 2 pegs ahead
            Hole hole = board[playerPeg.getX()][playerPeg.getY() +2];
            boolean holeIsEmpty = hole.getHoleStatus() == HoleStatus.EMPTY;
            if(holeIsEmpty) {
                // BINGO, initiate jump sequence
                // - leave start position vacant (/HoleStatus.EMPTY)
                // TODO: create an arraylist or a fixed array w length maxPegs to hold the removed pegs
            }
        } else if(direction == Dir.DOWN) {
            // check what is 2 pegs ahead
            Hole hole = board[playerPeg.getX()][playerPeg.getY() -2];
            boolean holeIsEmpty = hole.getHoleStatus() == HoleStatus.EMPTY;
            if(holeIsEmpty) {
                // BINGO, initiate jump sequence
            }
        } else if(direction == Dir.LEFT) {
            // check what is 2 pegs ahead
            Hole hole = board[playerPeg.getX() -2][playerPeg.getY()];
            boolean holeIsEmpty = hole.getHoleStatus() == HoleStatus.EMPTY;
            if(holeIsEmpty) {
                // BINGO, initiate jump sequence
            }
        } else if(direction == Dir.RIGHT) {
            // check what is 2 pegs ahead
            Hole hole = board[playerPeg.getX() +2][playerPeg.getY()];
            boolean holeIsEmpty = hole.getHoleStatus() == HoleStatus.EMPTY;
            if(holeIsEmpty) {
                // BINGO, initiate jump sequence
            }
        }
    }
}
