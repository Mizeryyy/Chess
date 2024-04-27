
// COMPLETED
public class Rook extends ChessPiece {
    public Rook(String color) {
        super(color);
    }
    @Override
    public ChessPiece copyPiece() {
        return new Rook(this.color); // Return a new instance of Rook with the same color
    }
@Override
    public boolean isValidMove(ChessPiece[][] board, int startX, int startY, int endX, int endY) {
        // Check if the start and end coordinates are within the board boundaries
        if (startX < 0 || startX >= 8 || startY < 0 || startY >= 8 || endX < 0 || endX >= 8 || endY < 0 || endY >= 8) {
            return false;
        }
    
        // Check if the start and end positions are the same
        if (startX == endX && startY == endY) {
            return false;
        }
    
        // Rook can move either horizontally or vertically
        if (startX == endX) {
            // Moving vertically
            int step = Integer.compare(endY, startY);
            for (int i = startY + step; i != endY + step; i += step) {
                if (board[startX][i] != null) {
                    if (board[startX][i].getColor().equals(this.color)) {
                        return false; // Path blocked by piece of same color
                    }
                }
            }
            return true; // Valid move if no obstructions found
        } else if (startY == endY) {
            // Moving horizontally
            int step = Integer.compare(endX, startX);
            for (int i = startX + step; i != endX + step; i += step) {
                if (board[i][startY] != null) {
                    if (board[i][startY].getColor().equals(this.color)) {
                        return false; // Path blocked by piece of same color
                    }
                }
            }
            return true; // Valid move if no obstructions found
        }
        return false; // Rook can't move diagonally
    }
    

    public static void main(String[] args) {
        ChessPiece[][] board = new ChessPiece[8][8];
        Rook rook = new Rook("black");
        board[0][0] = rook; // place the rook on the board
        board[7][3] = rook;

        // Place an obstruction on the board
        ChessPiece obstruction = new Pawn("black"); // Assuming there's a generic chess piece class
        board[0][1] = obstruction;
        board[4][0] = obstruction;

        board[7][1] = obstruction;
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
        // Test valid moves

        System.out.println("Testing valid moves:");
        System.out.println("Valid move (horizontal): " + rook.isValidMove(board, 7, 3, 7, 3));

        System.out.println("Valid move (horizontal): " + rook.isValidMove(board, 7, 3, 7, 0));
        System.out.println("Valid move (horizontal): " + rook.isValidMove(board, 0, 0, 3, 0));

        System.out.println("Valid move (vertical): " + rook.isValidMove(board, 0, 0, 0, 1));

        // Test invalid moves
        System.out.println("\nTesting invalid moves:");
        System.out.println("Invalid move (diagonal): " + rook.isValidMove(board, 0, 0, 4, 4));
        System.out.println("Invalid move (obstructed): " + rook.isValidMove(board, 0, 0, 4, 0));
        System.out.println("Invalid move (obstructed): " + rook.isValidMove(board, 0, 0, 0, 4));

    }
}
