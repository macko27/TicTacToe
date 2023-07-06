import java.util.Scanner;

public class Game {
    public static final int SIZE = 9;
    public static final int[][] winnerOptions = { {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                                                    {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                                                    {0, 4, 8}, {2, 4, 6} };
    private char[] board;
    private boolean running;

    public Game() {
        this.board = new char[SIZE];
        this.running = true;

        for (int i = 0; i < SIZE; i++) {
            this.board[i] = '-';
        }
    }

    public void start() {
        showBoard();
        while(running) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Pick: ");
            int choice = scanner.nextInt();
            if (choice > 0 && choice <= SIZE) {
                if (this.board[choice - 1] == '-') {
                    this.board[choice - 1] = 'x';
                }
            }



            showBoard();
            if (this.checkWinner()) running = false;
        }
    }

    public void showBoard() {
        for (int i = 0; i < SIZE; i++) {
            System.out.print(this.board[i]);
            System.out.print(' ');
            if (i % Math.sqrt(SIZE) == 2) {
                System.out.println();
            }
        }
    }

    public boolean checkWinner() {
        for (int i = 0; i < winnerOptions.length; i++) {
            char option1 = this.board[winnerOptions[i][0]];
            char option2 = this.board[winnerOptions[i][1]];
            char option3 = this.board[winnerOptions[i][2]];

            if (option1 == option2 && option1 == option3) {
                if (option1 == 'x') {
                    System.out.println("Player wins");
                    return true;
                } else if (option1 == 'o') {
                    System.out.println("AI wins");
                    return true;
                }
            }
        }
        return false;
    }
}
