import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();

        // TODO: write code here to force the user to pick a valid coordinate (not off-limits, etc) with a while loop (/handle wrong input)
        // user can still choose anything on the table, including things that aren't marbles

        /* Conditions (to check if move valid):
            - the position you want to take over must be of type marble and
            - must be right behind one of the 4 marbles adjacent to the hole you're jumping towards
            - you must know from (a to b) where to where the player wants to move in order to be able to tell the player if
              that move is legal or not and whether they will be asked to put in a valid one
        */

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
    }
}