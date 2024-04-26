import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class ChessBoard {
    private ChessPiece[][] board;
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
        this.currentPlayer = "white";
    }

    private ChessPiece[][] initializeBoard() {
        ChessPiece[][] board = new ChessPiece[8][8];

        // Placing pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn("white");
            board[6][i] = new Pawn("black");
        }

        // Placing other pieces
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

    public void displayBoard() {
        for (ChessPiece[] row : board) {
            for (ChessPiece piece : row) {
                if (piece != null) {
                    String pieceSymbol = piece.getColor().charAt(0) + piece.getClass().getSimpleName().substring(0, 1);
                    System.out.print(String.format("%-3s", pieceSymbol)); // Adjust the width to 3 characters
                } else {
                    System.out.print(".  "); // Adjust the width to 3 characters
                }
            }
            System.out.println();
        }
        System.out.println();
        // Print the board with piece symbols and coordinates
        for (int i = 0; i < 8; i++) {
            // Print coordinates
            for (int j = 0; j < 8; j++) {
                System.out.print(i + "," + j + " ");
            }
            // Move to the next line after each row
            System.out.println();
        }
        // Print an additional line break for clarity
        System.out.println();
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer.equals("white") ? "black" : "white";
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean makeMove(String move) {

        int startX = Character.getNumericValue(move.charAt(2));
        int startY = Character.getNumericValue(move.charAt(4));
        int endX = Character.getNumericValue(move.charAt(6));
        int endY = Character.getNumericValue(move.charAt(8));

        // Execute the move
        ChessPiece piece = board[startX][startY];
        board[startX][startY] = null;
        board[endX][endY] = piece;

        return true;
    }

    public boolean isCheck() {
        // Get the position of the king
        int kingX = 0;
        int kingY = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && piece.getClass().getSimpleName().equals("king")) {
                    kingX = i;
                    kingY = j;

                }
            }
        }
        ChessPiece king = board[kingX][kingY];
        // Iterate through all the squares on the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                // Check if the piece belongs to the opponent and if it can move to the king's
                // position
                if (piece != null && piece != king && !piece.getColor().equals(king.getColor())
                        && piece.isValidMove(board, i, j, kingX, kingY)) {
                    // The king is in check
                    return true;
                }
            }
        }
        // The king is not in check
        return false;
    }

    public boolean isCheckMate() {
        // Check if the king is in check
        if (!isCheck()) {
            return false; // King is not in check, so it's not checkmate
        }

        // Iterate through all the squares on the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                // Check if the piece belongs to the current player and if it's not null
                if (piece != null && piece.getColor().equals(currentPlayer)) {
                    // Try moving the piece to all possible positions on the board
                    for (int x = 0; x < 8; x++) {
                        for (int y = 0; y < 8; y++) {
                            // Check if the move is valid
                            if (piece.isValidMove(board, i, j, x, y)) {
                                // Make a copy of the board
                                ChessPiece[][] tempBoard = new ChessPiece[8][8];
                                for (int k = 0; k < 8; k++) {
                                    tempBoard[k] = board[k].clone();
                                }
                                // Make the move on the copied board
                                tempBoard[x][y] = tempBoard[i][j];
                                tempBoard[i][j] = null;
                                // Check if the king is still in check after the move
                                if (!isCheck()) {
                                    return false; // King is not in checkmate
                                }
                            }
                        }
                    }
                }
            }
        }
        // King is in checkmate
        return true;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (!isCheckMate()) {
            displayBoard();
            System.out.println(currentPlayer + "'s turn:");
            System.out.print("Enter your move");
            String move = scanner.nextLine();

            int startX = Character.getNumericValue(move.charAt(2));
            int startY = Character.getNumericValue(move.charAt(4));
            int endX = Character.getNumericValue(move.charAt(6));
            int endY = Character.getNumericValue(move.charAt(8));

            ChessPiece piece = board[startX][startY];

            if (isCheck()) {
                // If the king is in check, only allow moving the king
                if (piece instanceof King && piece.isValidMove(board, startX, startY, endX, endY)
                        && piece.getColor().equals(currentPlayer)) {
                    makeMove(move);
                    switchPlayer();
                } else {
                    System.out.println("King is in check. You can only move the king.");
                }
            } else {
                // If the king is not in check, allow moving any piece as usual
                if (piece.isValidMove(board, startX, startY, endX, endY) && piece.getColor().equals(currentPlayer)) {
                    makeMove(move);
                    switchPlayer();
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            }
        }
        System.out.println("Checkmate! " + currentPlayer + " wins!");
        scanner.close();
    }

    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        board.play();
    }
}
