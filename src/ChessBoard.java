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

    



    public void restorePreviousBoardState() {
        // Restore the previous state of the board
        for (int i = 0; i < 8; i++) {
            System.arraycopy(previousBoard[i], 0, board[i], 0, 8);
        }
    }
    public ChessPiece[][] deepCopyBoard(ChessPiece[][] originalBoard) {
        ChessPiece[][] copiedBoard = new ChessPiece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = originalBoard[i][j];
                if (piece != null) {
                    copiedBoard[i][j] = piece.copyPiece();
                } else {
                    copiedBoard[i][j] = null;
                }
            }
        }
        return copiedBoard;
    }
    
    public boolean isCheckMate() {
        // Get the opposing current player's color
        String currentPlayerColor = getCurrentPlayer();
        System.out.println(currentPlayerColor);
    
        // Find the position of the current player's king
        int kingX = -1, kingY = -1;
        outer:
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = getPieceAt(i, j);
                if (piece instanceof King && piece.getColor().equals(currentPlayerColor)) {
                    kingX = i;
                    kingY = j;
                    break outer;
                }
            }
        }

        displayBoard();

        // iterate through all pieces of the current player
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = getPieceAt(i, j);
                if (piece != null && piece.getColor().equals(currentPlayerColor)) {
                    // Attempt to move the piece to every square on the board
                    for (int x = 0; x < 8; x++) {
                        for (int y = 0; y < 8; y++) {

                            if (piece.isValidMove(getBoard(), i, j, x, y)) {
                                // simulate the move
                                ChessPiece[][] tempBoard = deepCopyBoard(getBoard());

                                tempBoard[x][y] = tempBoard[i][j];
                                tempBoard[i][j] = null;        
                                ChessPiece[][] tempBoard2 = deepCopyBoard(getBoard());

                                if (!isCheck(tempBoard, currentPlayerColor) && piece.isValidMove(tempBoard2, i, j, x, y)) {
                                    return false; //king can escape checkmate by this move
                                }
                            }
                        }
                    }
                }
            }
        }return true;
    }
    
    

    public boolean isCheck(ChessPiece[][] board, String currentPlayer) {
        int kingX = -1, kingY = -1;
    
        // find the position of the current player's king
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
    
        //check if the king's position is found
        if (kingX == -1 || kingY == -1) {
            return false; //king's position not found, so no check
        }
    
        //iterate through opponent's pieces to see if any threaten the king
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && !piece.getColor().equals(currentPlayer)) {
                    if (piece.isValidMove(board, i, j, kingX, kingY)) {
                        return true; //king is in check
                    }
                }
            }
        }
    
        // king is not in check
        return false;
    }
    

  
    

}
