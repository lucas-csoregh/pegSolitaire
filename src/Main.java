import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();

        System.out.println("\nSelect your starting position");
        Scanner scanner = new Scanner(System.in);

        boolean validAnswer = false;

        while(!validAnswer) {
            System.out.print("\nup: d2,\ndown: d6,\nleft: b4,\nright: f4\n\nEnter up/down/left/right: ");
            String answer = scanner.next();

            switch (answer) {
                case "up":
                    // d2
                    board.spawnPlayer(4, 2);
                    validAnswer = true;
                    break;
                case "down":
                    // d6
                    board.spawnPlayer(4, 6);
                    validAnswer = true;
                    break;
                case "left":
                    // b4
                    board.spawnPlayer(2, 4);
                    validAnswer = true;
                    break;
                case "right":
                    // f4
                    board.spawnPlayer(6, 4);
                    validAnswer = true;
                    break;
                default:
                    break;
            }
        }
    }
}