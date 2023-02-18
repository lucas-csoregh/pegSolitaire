public class Board {
    final private int maxHoles = 33;
    final private int maxPegs = 32;
    private Peg[] pegs = new Peg[maxPegs];
    /*
    when we loop over the HoleStatus[][] at creation,
    we need to create ... when
        pegs        !HoleStatus.OFF_LIMITS && !HoleStatusEMPTY
        and holes   !HoleStatus.OFF_LIMITS

    this way, we can pass the valid x and y coordinates as values
     */
    private Hole[] validCoordinates = new Hole[maxHoles];

    HoleStatus[][] english_cross = {
            /*
                OLD

                {2, 2, 2, 2, 2, 2, 2, 2},
                {2, 1, 1, 0, 0, 0, 1, 1},
                {2, 1, 1, 0, 0, 0, 1, 1},
                {2, 0, 0, 0, 0, 0, 0, 0},
                {2, 0, 4, 0, 3, 0, 0, 0},
                {2, 0, 0, 0, 0, 0, 0, 0},
                {2, 1, 1, 0, 0, 0, 1, 1},
                {2, 1, 1, 0, 0, 0, 1, 1}

                0 = marble
                1 = off limits
                2 = ruler
                3 = empty
                4 = player

            is still symbolically equivalent to HoleStatus[][] english_cross
            };*/
            {HoleStatus.OFF_LIMITS, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE},
            {HoleStatus.RULER, HoleStatus.MARBLE, HoleStatus.PLAYER, HoleStatus.MARBLE, HoleStatus.EMPTY, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE},
            {HoleStatus.RULER, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.MARBLE, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS}
    };


    public String reset(HoleStatus[][] board) {
        StringBuilder sb = new StringBuilder();
        int size=board.length;
        // Draw the grid (/Looping over the HoleStatus[][])
        for(int y=0; y<size; y++) {
            for(int x=0; x<size; x++) {
                // Conditionally create peg

                // Conditionally create hole

                // Conditionally draw square
                if(board[x][y] == HoleStatus.MARBLE) {
                    sb.append(" o ");
                } else if(board[x][y] == HoleStatus.PLAYER) {
                    // s for selected, selected = which marble the player moves is up to the player
                    sb.append(" S ");
                } else if(board[x][y] == HoleStatus.OFF_LIMITS) {
                    sb.append("   ");
                } else if(board[x][y] == HoleStatus.EMPTY) {
                    sb.append(" . ");
                } else if(board[x][y] == HoleStatus.RULER) {
                    if(y != 0) {
                        sb.append(" "+y+" ");
                    } else {
                        char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g'};
                        sb.append(" "+chars[x -1]+" ");
                    }
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Board() {
        System.out.print(reset(english_cross));
    }
}
