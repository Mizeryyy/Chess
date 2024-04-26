// Testing valid moves:
// Valid move (vertical): true
// Valid move (horizontal): true

// Testing invalid moves:
// Invalid move (diagonal): false
// Invalid move (obstructed): true


// BUG INVERSE CHECKING CHECKING DOWN TO MOVE LEFT UP
public class Rook extends ChessPiece {
    public Rook(String color) {
        super(color);
    }

    public void printRow(ChessPiece[][] board, int row) {
        for (int j = 0; j < board[row].length; j++) {
            ChessPiece piece = board[row][j];
            if (piece != null) {
                String pieceSymbol = piece.getColor().charAt(0) + piece.getClass().getSimpleName().substring(0, 1);
                System.out.print(String.format("%-3s", pieceSymbol)); // Adjust the width to 3 characters
            } else {
                System.out.print(".  "); // Adjust the width to 3 characters
            }
        }
        System.out.println(); // Move to the next line after printing the row
    }
    public void printCell(ChessPiece[][] board, int row, int col) {
        ChessPiece piece = board[row][col];
        if (piece != null) {
            String pieceSymbol = piece.getColor().charAt(0) + piece.getClass().getSimpleName().substring(0, 1);
            System.out.print(String.format("%-3s", pieceSymbol)); // Adjust the width to 3 characters
        } else {
            System.out.print(".  "); // Adjust the width to 3 characters
        }
    }
    
    @Override

    public boolean isValidMove(ChessPiece[][] board, int startX, int startY, int endX, int endY) {
        // Check if the start and end coordinates are within the board boundaries
        if (startX < 0 || startX >= 8 || startY < 0 || startY >= 8 || endX < 0 || endX >= 8 || endY < 0 || endY >= 8) {
            return false;
        }
    
        // Rook can move either horizontally or vertically
        if (startX == endX) {
            
            // Moving vertically
            int step = Integer.compare(endY, startY);
            for (int i = startY+step; i != endY+step; i += step) {


                if (board[startY][i] != null) {
                    if (board[startY][i].getColor().equals(this.color)) {
                        return false; // Path blocked by piece of same color
                    }
                }
            }
            return true; // Valid move if no obstructions found
        } else if (startY == endY) {
            // Moving horizontally
            int step = Integer.compare(endX, startX);
            for (int i = startX + step; i != endX+step; i += step) {
                printCell(board, startX,i);


                if (board[i][startX] != null) {

                    if (board[i][startX].getColor().equals(this.color)) {
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
        Rook rook = new Rook("white");
        board[0][0] = rook; // place the rook on the board
    
        // Place an obstruction on the board
        ChessPiece obstruction = new Pawn("white"); // Assuming there's a generic chess piece class
        board[0][4] = obstruction;
        board[4][0] = obstruction;


        
        // Test valid moves
        System.out.println("Testing valid moves:");
        System.out.println("Valid move (horizontal): " + rook.isValidMove(board, 0, 0, 3, 0));
        System.out.println("Valid move (vertical): " + rook.isValidMove(board, 0, 0, 0, 3));
    
        // Test invalid moves
        System.out.println("\nTesting invalid moves:");
        System.out.println("Invalid move (diagonal): " + rook.isValidMove(board, 0, 0, 4, 4));
        System.out.println("Invalid move (obstructed): " + rook.isValidMove(board, 0, 0, 4, 0));
        System.out.println("Invalid move (obstructed): " + rook.isValidMove(board, 0, 0, 0, 4));

    }
}
