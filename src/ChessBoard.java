import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class ChessBoard {
    private ChessPiece[][] board;
    private ChessPiece[][] previousBoard; // To store the previous state of the board
    private String currentPlayer;
    public String a;
    private static final Map<Character, ChessPiece> pieceDictionary = new HashMap<>();

    static {
        pieceDictionary.put('P', new Pawn("white"));
        pieceDictionary.put('R', new Rook("white"));
        pieceDictionary.put('N', new Knight("white"));
        pieceDictionary.put('B', new Bishop("white"));
        pieceDictionary.put('Q', new Queen("white"));
        pieceDictionary.put('K', new King("white"));
    }

    public ChessBoard() {
        this.board = initializeBoard();
        this.previousBoard = new ChessPiece[8][8]; // Initialize previous board
        this.currentPlayer = "white";
    }

    private ChessPiece[][] initializeBoard() {
        ChessPiece[][] board = new ChessPiece[8][8];

        // placing pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn("white");
            board[6][i] = new Pawn("black");
        }

        // placing other pieces
        board[0][0] = new Rook("white");
        board[0][7] = new Rook("white");
        board[7][0] = new Rook("black");
        board[7][7] = new Rook("black");

        board[0][1] = new Knight("white");
        board[0][6] = new Knight("white");
        board[7][1] = new Knight("black");
        board[7][6] = new Knight("black");

        board[0][2] = new Bishop("white");
        board[0][5] = new Bishop("white");
        board[7][2] = new Bishop("black");
        board[7][5] = new Bishop("black");

        board[0][3] = new Queen("white");
        board[7][3] = new Queen("black");

        board[0][4] = new King("white");
        board[7][4] = new King("black");

        return board;
    }

    public ChessPiece getPieceAt(int row, int col) {
        return board[row][col];
    }

    public ChessPiece[][] getBoard() {
        return this.board;
    }

    private ChessPiece[][] copyBoard(ChessPiece[][] originalBoard) {
        ChessPiece[][] copiedBoard = new ChessPiece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = originalBoard[i][j];
                if (piece != null) {
                    copiedBoard[i][j] = piece.copyPiece(); // Assuming ChessPiece has a copyPiece() method
                }
            }
        }
        return copiedBoard;
    }

    public void displayBoard() {
        for (ChessPiece[] row : board) {
            for (ChessPiece piece : row) {
                if (piece != null) {
                    String pieceSymbol = piece.getColor().charAt(0) + piece.getClass().getSimpleName().substring(0, 1);
                    System.out.print(String.format("%-3s", pieceSymbol)); // adjust the width to 3 characters
                } else {
                    System.out.print(".  "); // adjust the width to 3 characters
                }
            }
            System.out.println();
        }
        System.out.println();
        // print the board with piece symbols and coordinates
        for (int i = 0; i < 8; i++) {
            // print coordinates
            for (int j = 0; j < 8; j++) {
                System.out.print(i + "," + j + " ");
            }
            // move to the next line after each row
            System.out.println();
        }
        // print an additional line break for clarity
        System.out.println();
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer.equals("white") ? "black" : "white";
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean makeMove(String move) {
        // Save the previous board state
        ChessPiece[][] previousBoard = getPreviousBoard();
        
        // Execute the move
        int startX = Character.getNumericValue(move.charAt(2));
        int startY = Character.getNumericValue(move.charAt(4));
        int endX = Character.getNumericValue(move.charAt(6));
        int endY = Character.getNumericValue(move.charAt(8));
    
        // Store the piece being moved
        ChessPiece piece = board[startX][startY];
    
        // Move the piece on the board
        board[startX][startY] = null;
        board[endX][endY] = piece;
    
        // Check if the player's king is in check after the move
        if (isCheck(currentPlayer)) {
            // Revert the board to the previous state
            board = previousBoard;
            return false; // Move is invalid
        }
    
        // Move is valid
        return true;
    }
    

    private void savePreviousBoardState() {
        // Copy the current board to the previous board
        for (int i = 0; i < 8; i++) {
            System.arraycopy(board[i], 0, previousBoard[i], 0, 8);
        }
    }

    public void restorePreviousBoardState() {
        // Restore the previous state of the board
        for (int i = 0; i < 8; i++) {
            System.arraycopy(previousBoard[i], 0, board[i], 0, 8);
        }
    }

    public boolean isCheck(ChessPiece[][] board, String currentPlayer) {
        int kingX = -1, kingY = -1;
    
        // Find the position of the current player's king
        outer:
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece instanceof King && piece.getColor().equals(currentPlayer)) {
                    kingX = i;
                    kingY = j;
                    break outer;
                }
            }
        }
    
        // Check if the king's position is found
        if (kingX == -1 || kingY == -1) {
            return false; // King's position not found, so no check
        }
    
        // Iterate through opponent's pieces to see if any threaten the king
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && !piece.getColor().equals(currentPlayer)) {
                    if (piece.isValidMove(board, i, j, kingX, kingY)) {
                        return true; // King is in check
                    }
                }
            }
        }
    
        // King is not in check
        return false;
    }
    

  
    
    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (!isCheckMate()) {
            displayBoard();
            System.out.println(currentPlayer + "'s turn:");
            System.out.print("Enter your move");
            String move = scanner.nextLine();

            // Handle the move
            if (makeMove(move)) {
                // Check for check and checkmate
                if (isCheck()) {
                    System.out.println("Check!");
                    if (isCheckMate()) {
                        System.out.println("Checkmate! " + currentPlayer + " wins!");
                        break; // End the game if checkmate is detected
                    }
                }

                // Switch player turn
                switchPlayer();
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        board.play();
    }
}
