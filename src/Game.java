public class Game {
    private char[] board;
    private int size;

    public Game(int gameSize) {
        this.board = new char[gameSize];
        this.size = gameSize;

        for (int i = 0; i < gameSize; i++) {
            this.board[i] = '-';
        }
    }

    public void showBoard() {
        for (int i = 0; i < Math.sqrt(this.size); i++) {
            for (int j = 0; j < Math.sqrt(this.size); j++) {
                System.out.print(this.board[i]);
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    public boolean pickChoice(char character, int order) {
        if (this.board[order] == '-') {
            this.board[order] = character;
            return true;
        }
        return false;
    }
}
