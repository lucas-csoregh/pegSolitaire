import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board gamestate = new Board();

        Scanner scanner = new Scanner(System.in);

        boolean validAnswer = false;

        while(!validAnswer) {
            System.out.print("\nOptions:\nUP\nDOWN\nLEFT\nRIGHT\n\nEnter up/down/left/right: ");
            String answer = scanner.next();
            answer = answer.toLowerCase();

            switch (answer) {
                case "up":
                    // d2
                    gamestate.spawnPlayer(4, 2);
                    validAnswer = true;
                    break;
                case "down":
                    // d6
                    gamestate.spawnPlayer(4, 6);
                    validAnswer = true;
                    break;
                case "left":
                    // b4
                    gamestate.spawnPlayer(2, 4);
                    validAnswer = true;
                    break;
                case "right":
                    // f4
                    gamestate.spawnPlayer(6, 4);
                    validAnswer = true;
                    break;
                default:
                    break;
            }
        }
    }
}