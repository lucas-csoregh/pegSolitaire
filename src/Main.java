import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();

        // TODO: write code here to force the user to pick a valid coordinate (not off-limits, etc) with a while loop (/handle wrong input)
        System.out.println("\nSelect your first marble!");
        Scanner scanner = new Scanner(System.in);
        System.out.print("pick a valid number: ");
        int pickedNum = scanner.nextInt();
        System.out.print("pick a valid character: ");
        String pickedChar = scanner.next();
        System.out.printf("%s%d", pickedChar.toUpperCase(), pickedNum);

        board.setPlayerPeg(4, 4);
        // TODO: change the player's peg to the appropriate HoleStatus.PLAYER enum instead of HoleStatus.MARBLE which it has now
        // TODO: draw the next frame based on what has changed to the saved state of the board
        //      (which we keep in our Hole[][] grid, that we generated based on a template with a starting status for everything on the board)
    }
}