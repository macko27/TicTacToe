import java.util.Random;
import java.util.Scanner;

public class Game {
    public static final int SIZE = 9;
    public static final int[][] WINNER_OPTIONS = { {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                                                    {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                                                    {0, 4, 8}, {2, 4, 6} };
    private char[] board;
    private boolean running;
    private int round;

    public Game() {
        this.board = new char[SIZE];
        this.running = true;
        this.round = 1;

        for (int i = 0; i < SIZE; i++) {
            this.board[i] = '-';
        }
    }

    public void start() {
        showBoard();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            while(true) {
                System.out.print("Pick: ");
                int choice = scanner.nextInt();
                if (choice > 0 && choice <= SIZE) {
                    if (this.board[choice - 1] == '-') {
                        this.board[choice - 1] = 'x';
                        break;
                    } else {
                        System.out.println("Pick empty space!");
                    }
                }
            }
            if (this.checkEndGame()) {
                break;
            }

            //ai
            this.botMove();

            if (this.checkEndGame()) {
                break;
            }

            this.round++;
        }
    }

    public void botMove() {
        Random generator = new Random();
        //0,1,2
        int choice = generator.nextInt(100) + 1;
        if ((round == 1 && choice <= 50) || (round == 2 && choice <= 5)) {
            while (true) {
                choice = generator.nextInt(SIZE);
                if (this.board[choice] == '-') {
                    this.board[choice] = 'o';
                    break;
                }
            }
            showBoard();
        } else {
            this.board[this.bestChoice()] = 'o';
            showBoard();
        }
    }

    private int bestChoice() {
        int bestScore = -9999999;
        int indexOfBestScore = -1;
        for (int i = 0; i < SIZE; i++) {
            if (this.board[i] == '-') {
                this.board[i] = 'o';
                int score = minimax(0,false);
                this.board[i] = '-';
                if (score > bestScore) {
                    bestScore = score;
                    indexOfBestScore = i;
                }
            }
        }
        return indexOfBestScore;
    }

    private int minimax(int depth, boolean isMaximizing) {
        Winner winner = checkWinner();

        if (winner == Winner.PLAYER) {
            return -1;
        }
        if (winner == Winner.AI) {
            return 1;
        }
        if (winner == Winner.TIE) {
            return 0;
        }
        if (isMaximizing) {
            int bestScore = -9999999;
            for (int i = 0; i < SIZE; i++) {
                if (this.board[i] == '-') {
                    this.board[i] = 'o';
                    int score = minimax(depth + 1, false);
                    this.board[i] = '-';
                    bestScore = Math.max(bestScore, score);
                }
            }
            return bestScore;
        } else {
            int bestScore = 9999999;
            for (int i = 0; i < SIZE; i++) {
                if (this.board[i] == '-') {
                    this.board[i] = 'x';
                    int score = minimax(depth + 1, true);
                    this.board[i] = '-';
                    bestScore = Math.min(bestScore, score);
                }
            }
            return bestScore;
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


    public Winner checkWinner() {
        for (int i = 0; i < WINNER_OPTIONS.length; i++) {
            char option1 = this.board[WINNER_OPTIONS[i][0]];
            char option2 = this.board[WINNER_OPTIONS[i][1]];
            char option3 = this.board[WINNER_OPTIONS[i][2]];

            if (option1 == option2 && option1 == option3) {
                if (option1 == 'x') {
                    return Winner.PLAYER;
                } else if (option1 == 'o') {
                    return Winner.AI;
                }
            }
        }
        boolean tie = true;
        for (int i = 0; i < SIZE; i++) {
            if (this.board[i] == '-') {
                tie = false;
                break;
            }
        }
        if (tie) return Winner.TIE;
        return Winner.NOONE;
    }

    public boolean checkEndGame() {
        Winner winner = checkWinner();

        if (winner == Winner.PLAYER) {
            System.out.println("Player wins");
            return true;
        }
        if (winner == Winner.AI) {
            System.out.println("AI wins");
            return true;
        }
        if (winner == Winner.TIE) {
            System.out.println("TIE");
            return true;
        }
        return false;
    }
}
