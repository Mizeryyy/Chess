// ChessPiece.java
public abstract class ChessPiece {
    protected String color;

    public ChessPiece(String color) {
        this.color = color;
    }
    

    public abstract boolean isValidMove(ChessPiece[][] board, int startX, int startY, int endX, int endY);
    public String getColor() {
        return color;
    }
  
}
