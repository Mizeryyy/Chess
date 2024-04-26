import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;


public class ChessBoard {
    private ChessPiece[][] board;
    private String currentPlayer;
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

    public boolean isValidMove(String move) {
        // Check if the move string has valid format
        if (move == null || move.length() != 9) {
            return false;
        }
        
        char pieceSymbol = move.charAt(0);
        int startX = Character.getNumericValue(move.charAt(2));
        int startY = Character.getNumericValue(move.charAt(4));
        int endX = Character.getNumericValue(move.charAt(6));
        int endY = Character.getNumericValue(move.charAt(8));
    
        // Check if the start and end coordinates are within the board boundaries
        if (startX < 0 || startX >= 8 || startY < 0 || startY >= 8 || endX < 0 || endX >= 8 || endY < 0 || endY >= 8) {
            return false;
        }
    
        ChessPiece piece = board[startX][startY];
        // Check if there is a piece at the start position
        if (piece == null) {
            return false;
        }
    
        // Check if it's the current player's piece
        if (!piece.getColor().equals(currentPlayer)) {
            return false;
        }
    
        // Check if the move is valid for the specific piece
        return piece.isValidMove(board, startX, startY, endX, endY);
    }
    
    public boolean makeMove(String move) {
        if (!isValidMove(move)) {
            return false;
        }
    
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
    
    
    

    public boolean isCheckmate() {
        //checkmate detection logic here
        return false; 
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (!isCheckmate()) {
            displayBoard();
            System.out.println(currentPlayer + "'s turn:");
            System.out.print("Enter your move");
            String move = scanner.nextLine();
            if (isValidMove(move)) {
                makeMove(move);
                switchPlayer();
            } else {
                System.out.println("Invalid move. Try again.");
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
