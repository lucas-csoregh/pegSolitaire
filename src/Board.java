public class Board {
    final private int maxHoles = 33;
    final private int maxPegs = 32;
    private Peg[] pegs;
    /*
    when we loop over the HoleStatus[][] at creation,
    we need to create ... when
        pegs        !HoleStatus.OFF_LIMITS && !HoleStatusEMPTY
        and holes   !HoleStatus.OFF_LIMITS

    this way, we can pass the valid x and y coordinates as values
     */
    private Hole[] validCoordinates;
    private int pegCount = 0;
    private int holeCount = 0;

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

                peg = !off limits, !ruler, !empty
                hole = !off limits, !ruler

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

    // draw the right thing based on the current enum from the loop
    public void drawSquare(HoleStatus[][] board, int x, int y, StringBuilder sb) {
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

    public String reset(HoleStatus[][] boardTemplate) {
        System.out.println("-- RESET --\n");
        pegCount = 0;
        holeCount = 0;
        pegs = new Peg[maxPegs];
        validCoordinates = new Hole[maxHoles];
        StringBuilder sb = new StringBuilder();

        StringBuilder graph = new StringBuilder();

        int size=boardTemplate.length;
        // Draw the grid (/Looping over the HoleStatus[][])
        for(int y=0; y<size; y++) {
            for(int x=0; x<size; x++) {
                // Conditionally create peg
                boolean createPeg = (boardTemplate[x][y] != HoleStatus.OFF_LIMITS) && (boardTemplate[x][y] != HoleStatus.EMPTY)  && (boardTemplate[x][y] != HoleStatus.RULER);
                Peg peg = new Peg(0,0);
                if(createPeg) {
                    peg = new Peg(x, y);
                    pegs[pegCount] = peg;
                    pegCount++;
                }

                // Conditionally create hole
                boolean createHole = (boardTemplate[x][y] != HoleStatus.OFF_LIMITS) && (boardTemplate[x][y] != HoleStatus.RULER);
                if(createHole) {
                    Hole hole = new Hole(x, y);
                    hole.setPeg(peg);
                    validCoordinates[holeCount] = hole;
                    holeCount++;
                }

                // Conditionally draw square
                drawSquare(boardTemplate, x, y, sb);
                graph.append("{x="+x+", y="+y+"}");
            }
            sb.append("\n");
            graph.append("\n");
        }
        //return sb.toString();
        return graph.toString();
    }

    public Board() {
        //reset(english_cross);

        // Command Line Representation
        System.out.print(reset(english_cross));

        System.out.println();

        for(Hole hole: validCoordinates) {
            System.out.println(hole.getPeg());
        }
    }
}
