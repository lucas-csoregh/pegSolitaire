import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();

        // TODO: write code here to force the user to pick a valid coordinate with a while loop (/handle wrong input)
        System.out.println("\nSelect your first marble!");
        Scanner scanner = new Scanner(System.in);
        System.out.print("pick a number between (and incl.) 1 and 7: ");
        int pickedNum = scanner.nextInt();
        System.out.print("pick a character between (and incl.) a and g alphabetically: ");
        String pickedChar = scanner.next();
        System.out.printf("(%s, %s)", pickedChar, pickedNum);
    }
}