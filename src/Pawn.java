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
    public ChessPiece copyPiece() {
        return new Pawn(this.color); // Return a new instance of Rook with the same color
    }

    @Override
    public boolean isValidMove(ChessPiece[][] board, int startX, int startY, int endX, int endY) {
        // Check if the pawn is moving forward
        if (startX == endX && startY == endY) {
            return false;
        }
    
        int direction = color.equals("white") ? 1 : -1;
    
        if (endX == startX + direction && board[endX][endY] == null) {
            // Pawn is moving one square forward
            return endY == startY; // Pawn cannot move sideways
        } else if (endX == startX + 2 * direction && startY == endY) {
            // Pawn is attempting to move two squares forward
    
            // Check if the path is clear for both squares
            if ((color.equals("white") && startX == 1) || (color.equals("black") && startX == 6)
                    && board[endX][endY] == null && board[startX + direction][startY] == null) {
    
                // Check if the squares in front of the pawn is clear
                if (board[startX + direction][startY] == null && board[startX + direction*2][startY] == null) {
                    return true; // Pawn can move two squares forward
                }
            }
        } else if (Math.abs(endX - startX) == 1 && endY == startY + Integer.compare(endY, startY)
                && board[endX][endY] != null && !board[endX][endY].color.equals(this.color)) {
            // Pawn is capturing a piece diagonally
            return true;
        }
    
        return false;
    }
    

    public static void main(String[] args) {
        // test Pawn movement logic

        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = new Pawn("white");
        board[3][7] = pawn; // place the pawn on the board
        Pawn pawn2 = new Pawn("black");

        board[3][7] = pawn2;

        // Test valid moves
        System.out.println("Testing valid moves:");
        System.out.println("Valid move (2 squares forward): " + pawn.isValidMove(board, 1, 7, 3, 7));
        System.out.println("Valid move (1 square forward): " + pawn.isValidMove(board, 2, 3, 2, 4));
        System.out.println("Valid move (diagonal capture): " + pawn.isValidMove(board, 2, 3, 3, 4));

        // Test invalid moves
        System.out.println("\nTesting invalid moves:");
        System.out.println("Invalid move (backward): " + pawn.isValidMove(board, 2, 3, 0, 3));
        System.out.println("Invalid move (sideways): " + pawn.isValidMove(board, 2, 3, 1, 4));
        System.out.println("Invalid move (2 squares forward, obstruction): " + pawn.isValidMove(board, 1, 3, 3, 3));
    }
}
