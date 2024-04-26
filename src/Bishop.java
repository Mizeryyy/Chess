// Testing valid moves:
// Valid move (diagonal up-right): true
// Valid move (diagonal down-left): true

// Testing invalid moves:
// Invalid move (horizontal): false
// Invalid move (vertical): false
//COMPLETED
public class Bishop extends ChessPiece {
    public Bishop(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(ChessPiece[][] board, int startX, int startY, int endX, int endY) {
        // check if the movement is diagonal
        if (Math.abs(endX - startX) == Math.abs(endY - startY)) {
            // check if the path is clear
            int stepX = (endX - startX) > 0 ? 1 : -1;
            int stepY = (endY - startY) > 0 ? 1 : -1;
            int currentX = startX + stepX;
            int currentY = startY + stepY;
            while (currentX != endX && currentY != endY) {
                if (board[currentX][currentY] != null) {
                    return false; // path blocked
                }
                currentX += stepX;
                currentY += stepY;
            }
            // check if the destination square is empty or occupied by an opponent's piece
            if (board[endX][endY] == null || !board[endX][endY].color.equals(this.color)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        ChessPiece[][] board = new ChessPiece[8][8];
        Bishop bishop = new Bishop("white");
        Pawn obstruction = new Pawn("white");
        board[4][4] = bishop; // place the bishop on the board
        board[7][7] = obstruction;

        // Test valid moves
        System.out.println("Testing valid moves:");
        System.out.println("Valid move (diagonal up-right): " + bishop.isValidMove(board, 4, 4, 6, 6));
        System.out.println("Valid move (diagonal down-left): " + bishop.isValidMove(board, 4, 4, 2, 2));

        // Test invalid moves
        System.out.println("\nTesting invalid moves:");
        System.out.println("Invalid move (horizontal): " + bishop.isValidMove(board, 4, 4, 4, 6));
        System.out.println("Invalid move (vertical): " + bishop.isValidMove(board, 4, 4, 2, 4));
        System.out.println("Valid move (diagonal up-right): " + bishop.isValidMove(board, 4, 4, 7, 7));

    }
}
