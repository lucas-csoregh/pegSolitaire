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
        Player player = Peg.playerPeg;
    }
}