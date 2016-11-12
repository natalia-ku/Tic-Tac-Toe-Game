package assignment5;

import java.util.Scanner;

/**
 * @author Natalia Kuleniuk
 */
public class TicTacToeNew2 {

    private char[][] board = {{'1', '2', '3', '4'}, {'5', '6', '7', '8'}, {'9', 'a', 'b', 'c'}, {'d', 'e', 'f', 'g'}};
    private static String status; //could be done, or stillPlaying
    private char winner; //could be X, O or T(tie)
    private char whoseTurn; // X or O
    //Game works great even if board is not 4*4; you could simply change length to 5 and uncomment board for 5*5 elements
    private int length = 4;
    //Board for 5*5 elements:
    // private char[][] board = {{'1', '2', '3', '4', '5'}, {'6', '7', '8', '9', 'a'}, {'b', 'c', 'd', 'e', 'f'}, {'g', 'h', 'i', 'j', 'k'}, {'l', 'm', 'n', 'o', 'p'}};
    //private char[][] board = {{'1', '2', '3'}, {'6', '7', '8'}, {'b', 'c', 'd'}};

    public TicTacToeNew2() //constructor that creates the initial setting
    {
        status = "stillPlaying";
        winner = ' ';
        whoseTurn = 'X';
    }

    public void printBoard() { //to display board for user
        System.out.println();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                System.out.print(board[i][j] + " ");

            }
            System.out.println();

        }
        System.out.println();
    }

    public void input() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(whoseTurn + ", your turn to play. Enter characters (1-9, a-g): ");
        String result = scanner.nextLine().trim();
        boolean flag = false; //will be true if values in array was succesfully changed to X or O
        boolean gameResult = false;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (result.equals(String.valueOf(board[i][j]))) { //check if use enter valid char that represents cell on the board; valueOf- because method equals couldn't be applied to char
                    if (board[i][j] != whoseTurn) { // check If place that user chose has been already taken 
                        flag = true;
                        board[i][j] = whoseTurn; // if place was not already taken - change to user input
                        // I couldn't call method analyzeBoard() not from input(), because analyzeBoard() passes current i and j indexes, not all board. This is more efficient, then analyze full board each time 
                        gameResult = analyzeBoard(i, j);

                        if (gameResult) {
                            winner = whoseTurn;
                            status = "done";
                        }
                    }
                }
            }
        }
        if (!flag) { // if user enter illegal character or place that user chose already taken
            System.out.println("Illegal character or place has been already taken. TRY AGAIN! ");
        } else {
            whoseTurn = changePlayer(whoseTurn);
        }

        if (full(board)) { // to check if all board is filled up with X and O and no place is left 
            System.out.println("TIE!!!");
            winner = 'T';
            status = "done";
        }
    }//end of input()

    /**
     * @param board - array to check
     * @return true if count of X and O in array equals to number of elements in
     * array
     */
    public boolean full(char[][] board) {
        int count = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (board[i][j] == 'X' || board[i][j] == 'O') {
                    count++;
                }
            }
        }
        if (count == length * length) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param i - current row of array
     * @param j - current column of array
     * @return true if there are 3 elements (X or O) in a sequence
     */
    public boolean analyzeBoard(int i, int j) {
        //CHECK DIAGONALS:
        int count = 1;
        //check upper left diagonal:
        int iUp = i - 1;
        int jLeft = j - 1;
        while (iUp >= 0 && jLeft >= 0 && board[iUp][jLeft] == whoseTurn) {
            --iUp;
            --jLeft;
            count++;
        }
        // check down right diagonal:
        int iDown = i + 1;
        int jRight = j + 1;
        while (iDown <= length - 1 && jRight <= length - 1 && board[iDown][jRight] == whoseTurn) {
            ++iDown;
            ++jRight;
            count++;
        }
        if (count < 3) {
            count = 1;
        }
        //check upper right diagonal:
        iUp = i - 1;
        jRight = j + 1;
        while (iUp >= 0 && jRight <= length - 1 && board[iUp][jRight] == whoseTurn) {
            --iUp;
            ++jRight;
            count++;
        }
        //check down left diagonal:
        iDown = i + 1;
        jLeft = j - 1;
        while (iDown <= length - 1 && jLeft >= 0 && board[iDown][jLeft] == whoseTurn) {
            ++iDown;
            --jLeft;
            count++;
        }
        if (count >= 3) {
            return true;
        }
        count = 1;//if count = 3, player wins
        // CHECK ROWS:
        jLeft = j - 1; //left check
        while (jLeft >= 0 && board[i][jLeft] == whoseTurn) {
            --jLeft;
            ++count;
        }
        jRight = j + 1; // to check cells to the right from current cell
        while (jRight <= length - 1 && board[i][jRight] == whoseTurn) {
            ++jRight;
            ++count;
        }
        if (count < 3) {
            count = 1;
        }
        // CHECK COLUMNS:
        iDown = i + 1;// to check cells in column that are under current cell
        while (iDown <= length - 1 && board[iDown][j] == whoseTurn) {
            ++iDown;
            ++count;
        }
        iUp = i - 1;// to check cells in column that are above current cell
        while (iUp >= 0 && board[iUp][j] == whoseTurn) {
            --iUp;
            ++count;
        }
        if (count >= 3) {
            return true;
        }
        return false;
    }

    /**
     * @param player - current player (X or O)
     * @return changed player (opposite to current)
     */
    private static char changePlayer(char player) { //to change player 
        if (player == 'X') {
            return 'O';
        } else {
            return 'X';
        }
    }

    /**
     * @return true if game end (tie or a winner); false if game still continues
     */
    public static boolean done() { // to check if users still playing
        if (status == "stillPlaying") {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @return winner
     */
    public char whoWon() {
        return winner;
    }

    public static void main(String[] args) {
        TicTacToeNew2 game2 = new TicTacToeNew2();
        while (!done()) {
            game2.printBoard();
            game2.input();
        }
        game2.printBoard();
        System.out.println();
        if (game2.whoWon() == 'T') {
            System.out.println("IT IS A TIE! NOBODY WON");
        } else {
            System.out.println("WINNER IS : " + game2.whoWon() + " !! ");
        }

    }
} //end of class

