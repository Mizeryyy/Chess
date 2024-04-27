// Testing valid moves:
// Valid move (1 square up): true
// Valid move (1 square diagonal): true

// Testing invalid moves:
// Invalid move (2 squares up): false
public class King extends ChessPiece {
    public King(String color) {
        super(color);
    }
    @Override
    public ChessPiece copyPiece() {
        return new King(this.color); // Return a new instance of Rook with the same color
    }
    @Override
    public boolean isValidMove(ChessPiece[][] board, int startX, int startY, int endX, int endY) {
        // check if the destination is within one square in any direction
        if (startX == endX && startY == endY) {
            return false;
        }
        if (Math.abs(endX - startX) <= 1 && Math.abs(endY - startY) <= 1) {
            // check if the destination square is empty or occupied by an opponent's piece
            if (board[endX][endY] == null || !board[endX][endY].color.equals(this.color)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King("white");
        board[4][4] = king; // place the king on the board
        ChessPiece obstruction = new Pawn("black"); // Assuming there's a generic chess piece class
        board[3][3] = obstruction;

        // Test valid moves
        System.out.println("Testing valid moves:");
        System.out.println("Valid move (1 square up): " + king.isValidMove(board, 4, 4, 3, 4));
        System.out.println("Valid move (1 square diagonal): " + king.isValidMove(board, 4, 4, 3, 3));

        // Test invalid moves
        System.out.println("\nTesting invalid moves:");
        System.out.println("Invalid move (2 squares up): " + king.isValidMove(board, 4, 4, 2, 4));
        System.out.println("Invalid move (1 square diagonal, obstructed): " + king.isValidMove(board, 4, 4, 3, 3));
    }
}
