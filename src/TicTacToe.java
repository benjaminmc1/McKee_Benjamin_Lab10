import java.util.Scanner;

public class TicTacToe {
    public static final int CROSS = 0;
    public static final int NOUGHT = 1;
    public static final int NO_SEED = 2;

    public static final int ROWS = 3;
    public static final int COLS = 3;
    public static int[][] board = new int[ROWS][COLS];

    public static int currentPlayer;

    public static final int PLAYING = 0;
    public static final int DRAW = 1;
    public static final int CROSS_WON = 2;
    public static final int NOUGHT_WON = 3;

    public static int currentState;

    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        boolean playAgain;
        do {
            clearBoard();
            display();
            do {
                playerMove();

                display();

                if(currentState == CROSS_WON) {
                    System.out.println("X won.");
                } else if (currentState == NOUGHT_WON) {
                    System.out.println("O won.");
                } else if (currentState == DRAW) {
                    System.out.println("It is a draw.");
                }

                currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
            }while(currentState == PLAYING);
            playAgain = SafeInput.getYNConfirm(in, "Would you like to play again?");
        }while(playAgain);
        
    }

    public static void clearBoard() {
        for(int row = 0; row < ROWS; ++row) {
            for(int col = 0; col < COLS; ++col) {
                board[row][col] = NO_SEED;
            }
        }
        currentPlayer = CROSS;
        currentState = PLAYING;
    }

    public static void playerMove() {
        boolean validInput = false;
        do {
            if (currentPlayer == CROSS) {
                System.out.print("It is X's turn. enter your move: ");
            } else {
                System.out.print("It is O's turn. Enter your move: ");
            }
            int row = SafeInput.getRangedInt(in, "Row: ", 1, 3);
            int col = SafeInput.getRangedInt(in, "Column: ", 1, 3);
            row -= 1;
            col -= 1;
            
            if(isValidMove(row, col)) {
                validInput = true;
            } else {
                System.out.println("This is an invalid move. Try again.");
            }
        }while(!validInput);
    }

    public static int gameUpdate(int player, int selectedRow, int selectedCol) {
        board[selectedRow][selectedCol] = player;

        if(isWin(player, selectedRow, selectedCol)) {
            return (player == CROSS) ? CROSS_WON : NOUGHT_WON;
        } else {
            for(int row = 0; row < ROWS; ++row) {
                for(int col = 0; col < COLS; ++col) {
                    if(board[row][col] == NO_SEED) {
                        return PLAYING;
                    }
                }
            }
            return DRAW;
        }
    }

    public static boolean isValidMove(int selectedRow, int selectedCol) {
        if(selectedRow >= 0 && selectedRow < ROWS && selectedCol >= 0 && selectedCol < COLS && board[selectedRow][selectedCol] == NO_SEED) {
            currentState = gameUpdate(currentPlayer, selectedRow, selectedCol);
            return true;
        }
        return false;
    }

    public static boolean isWin(int player, int selectedRow, int selectedCol) {
        if(isRowWin(player, selectedRow) || isColWin(player, selectedCol) || isDiagonalWin(player, selectedRow, selectedCol)) {
            return true;
        }
        return false;
    }

    public static boolean isRowWin(int player, int selectedRow) {
        if(board[selectedRow][0] == player && board[selectedRow][1] == player && board[selectedRow][2] == player) {
            return true;
        }
        return false;
    }

    public static boolean isColWin(int player, int selectedCol) {
        if(board[0][selectedCol] == player && board[1][selectedCol] == player && board[2][selectedCol] == player) {
            return true;
        }
        return false;
    }

    public static boolean isDiagonalWin(int player, int selectedRow, int selectedCol) {
        if(selectedRow == selectedCol && board[0][0] == player && board[1][1] == player && board[2][2] == player || selectedRow + selectedCol == 2 && board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }
        return false;
    }

    public static void display() {
        for(int row = 0; row < ROWS; ++row) {
            for(int col = 0; col < COLS; ++col) {
                gameCell(board[row][col]);
                if(col != COLS - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (row != ROWS - 1) {
                System.out.println("-----------");
            }
        }
        System.out.println();
    }

    public static void gameCell(int content) {
        switch (content) {
            case CROSS: System.out.print(" X "); break;
            case NOUGHT: System.out.print(" O "); break;
            case NO_SEED: System.out.print("   "); break;
        }
    }
}