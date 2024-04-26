// Testing valid moves:
// Valid move (2 squares up, 1 square left): true
// Valid move (1 square up, 2 squares right): true

// Testing invalid moves:
// Invalid move (2 squares down, 1 square left): true
// Invalid move (3 squares right): false
public class Knight extends ChessPiece {
    public Knight(String color) {
        super(color);
    }

    @Override
public boolean isValidMove(ChessPiece[][] board, int startX, int startY, int endX, int endY) {
    // Check if the destination square is reachable by a knight's L-shaped move
    int deltaX = Math.abs(endX - startX);
    int deltaY = Math.abs(endY - startY);
    if (!((deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2))) {
        return false; // Not a valid L-shaped move
    }

    // Check if the destination square is empty or occupied by an opponent's piece
    if (board[endX][endY] != null && board[endX][endY].color.equals(this.color)) {
        return false; // Destination square occupied by own piece
    }

    // Valid move
    return true;
}


    public static void main(String[] args) {
 
        ChessPiece[][] board = new ChessPiece[8][8];
        Knight knight = new Knight("white");
        board[4][4] = knight; //place the knight on the board

        // Test valid moves
        System.out.println("Testing valid moves:");
        System.out.println("Valid move (2 squares up, 1 square left): " + knight.isValidMove(board, 4, 4, 2, 3));
        System.out.println("Valid move (1 square up, 2 squares right): " + knight.isValidMove(board, 4, 4, 3, 6));

        // Test invalid moves
        System.out.println("\nTesting invalid moves:");
        System.out.println("Invalid move (2 squares down, 1 square left): " + knight.isValidMove(board, 4, 4, 6, 3));
        System.out.println("Invalid move (3 squares right): " + knight.isValidMove(board, 4, 4, 4, 7));
    }
}
