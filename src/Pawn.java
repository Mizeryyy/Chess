/**
 * Testing valid moves:
 * Valid move (2 squares forward): true
 * Valid move (1 square forward): true
 * Valid move (diagonal capture): false
 * 
 * Testing invalid moves:
 * Invalid move (backward): false
 * Invalid move (sideways): false
 * Invalid move (2 squares forward, obstruction): true
 */
public class Pawn extends ChessPiece {
    public Pawn(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(ChessPiece[][] board, int startX, int startY, int endX, int endY) {
        // check if the pawn is moving forward
        int direction = color.equals("white") ? 1 : -1;
        if (endX == startX + direction && board[endX][endY] == null) {

            return endY == startY; // Pawn cannot move sideways
        }

        else if (endX == startX + 2 * direction) {
            // check if the pawn is in its starting position and the path is clear
            if ((color.equals("white") && startX == 1) || (color.equals("black") && startX == 6)
                    && board[endX][endY] == null && board[startX + direction][startY] == null) {
                // check if the destination square and the square in between are empty
                return endY == startY; // pawn cannot move sideways
            }
        } else if (Math.abs(endX - startX) == 1 && endY == startY + Integer.compare(endY, startY)
                && board[endX][endY] != null
                && !board[endX][endY].color.equals(this.color)) {
            // check if the pawn is capturing a piece diagonally
            System.out.println("wha");
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        // test Pawn movement logic

        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = new Pawn("white");
        board[2][3] = pawn; // place the pawn on the board
        Pawn pawn2 = new Pawn("black");

        board[3][4] = pawn2;

        // Test valid moves
        System.out.println("Testing valid moves:");
        System.out.println("Valid move (2 squares forward): " + pawn.isValidMove(board, 2, 3, 3, 3));
        System.out.println("Valid move (1 square forward): " + pawn.isValidMove(board, 2, 3, 2, 4));
        System.out.println("Valid move (diagonal capture): " + pawn.isValidMove(board, 2, 3, 3, 4));

        // Test invalid moves
        System.out.println("\nTesting invalid moves:");
        System.out.println("Invalid move (backward): " + pawn.isValidMove(board, 2, 3, 0, 3));
        System.out.println("Invalid move (sideways): " + pawn.isValidMove(board, 2, 3, 1, 4));
        System.out.println("Invalid move (2 squares forward, obstruction): " + pawn.isValidMove(board, 1, 3, 3, 3));
    }
}
