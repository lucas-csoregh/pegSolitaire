import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();

        // TODO: write code here to force the user to pick a valid coordinate (not off-limits, etc) with a while loop (/handle wrong input)
        // user can still choose anything on the table, including things that aren't marbles
        System.out.println("\nSelect your first marble!");
        Scanner scanner = new Scanner(System.in);

        System.out.print("pick a valid character: ");
        String pickedChar = scanner.next();

        System.out.print("pick a valid number: ");
        int pickedNum = scanner.nextInt();

        System.out.printf("Your choice: %s%d\n", pickedChar.toUpperCase(), pickedNum);

        //char[] characters = {'a', 'b', 'c', 'd', 'e', 'f', 'g'};
        ArrayList<Character> chars = new ArrayList<>();
        chars.add('a');
        chars.add('b');
        chars.add('c');
        chars.add('d');
        chars.add('e');
        chars.add('f');
        chars.add('g');
        int xi = chars.indexOf(pickedChar.charAt(0)) + 1;
        board.setPlayerPeg(xi, pickedNum);
        // [x]: change the player's peg to the appropriate HoleStatus.PLAYER enum instead of HoleStatus.MARBLE which it has now
        // [x]: draw the next frame based on what has changed to the saved state of the board
        //      (which we keep in our Hole[][] grid, that we generated based on a template with a starting status for everything on the board)
    }
}