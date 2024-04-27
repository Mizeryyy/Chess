//Code from bishop and rook improved

//COMPLETED
public class Queen extends ChessPiece {
    public Queen(String color) {
        super(color);
    }

    @Override
    public ChessPiece copyPiece() {
        return new Queen(this.color); // return a new instance of Rook with the same color
    }

    @Override
    public boolean isValidMove(ChessPiece[][] board, int startX, int startY, int endX, int endY) {
        // check if the movement is horizontal, vertical, or diagonal
        if (startX == endX && startY == endY) {
            return false;
        }
        if ((startX == endX || startY == endY) || Math.abs(endX - startX) == Math.abs(endY - startY)) {
            // check if the path is clear for horizontal movement
            if (startX == endX) {
                int step = (endY - startY) > 0 ? 1 : -1;
                for (int i = startY + step; i != endY; i += step) {
                    if (board[startX][i] != null) {
                        return false; // path blocked
                    }
                }
            }
            // check if the path is clear for vertical movement
            else if (startY == endY) {
                int step = (endX - startX) > 0 ? 1 : -1;
                for (int i = startX + step; i != endX; i += step) {
                    if (board[i][startY] != null) {
                        return false; // path blocked
                    }
                }
            }
            // check if the path is clear for diagonal movement
            else {
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
        Queen queen = new Queen("white");
        board[4][4] = queen; // place the queen on the board

        // Test valid moves
        System.out.println("Testing valid moves:");
        System.out.println("Valid move (horizontal): " + queen.isValidMove(board, 4, 4, 4, 7));
        System.out.println("Valid move (vertical): " + queen.isValidMove(board, 4, 4, 7, 4));
        System.out.println("Valid move (diagonal): " + queen.isValidMove(board, 4, 4, 7, 7));

        // Test invalid moves
        System.out.println("\nTesting invalid moves:");
        System.out.println("Invalid move (obstructed): " + queen.isValidMove(board, 4, 4, 6, 6));
        System.out.println(
                "Invalid move (not diagonal, horizontal, or vertical): " + queen.isValidMove(board, 4, 4, 5, 6));
    }
}
