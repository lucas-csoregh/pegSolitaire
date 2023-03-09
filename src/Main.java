import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();

        Scanner scanner = new Scanner(System.in);

        boolean validAnswer = false;

        while(!validAnswer) {
            System.out.print("\nOptions:\nUP\nDOWN\nLEFT\nRIGHT\n\nEnter up/down/left/right: ");
            String answer = scanner.next();
            answer = answer.toLowerCase();

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