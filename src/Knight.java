//COMPLETED
public class Knight extends ChessPiece {
    public Knight(String color) {
        super(color);
    }
    @Override
    public ChessPiece copyPiece() {
        return new Knight(this.color); // Return a new instance of Rook with the same color
    }

    @Override
    public boolean isValidMove(ChessPiece[][] board, int startX, int startY, int endX, int endY) {
        // check if the destination square is reachable by a knight's L-shaped move
        int deltaX = Math.abs(endX - startX);
        int deltaY = Math.abs(endY - startY);
        if (startX == endX && startY == endY) {
            return false;
        }
        if (!((deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2))) {
            return false; // not a valid L-shaped move
        }

        // check if the destination square is empty or occupied by an opponent's piece
        if (board[endX][endY] != null && board[endX][endY].color.equals(this.color)) {
            return false; //destination square occupied by own piece
        }

        // valid move
        return true;
    }

    public static void main(String[] args) {

        ChessPiece[][] board = new ChessPiece[8][8];
        Knight knight = new Knight("white");
        board[4][4] = knight; // place the knight on the board

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
