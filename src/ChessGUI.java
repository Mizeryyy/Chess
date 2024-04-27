import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessGUI extends JFrame {
    private JPanel chessBoard;
    private JLabel[][] squares;
    private ChessBoard boardLogic;
    private ChessPiece selectedPiece;
    private int startX, startY;
    private ChessPiece[][] previousBoardState; // To store the previous board state

    public ChessGUI() {
        setTitle("Chess Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize chessboard
        chessBoard = new JPanel(new GridLayout(8, 8));
        getContentPane().add(chessBoard);

        // Create squares array
        squares = new JLabel[8][8];

        // Create ChessBoard instance
        boardLogic = new ChessBoard();

        // Populate chessboard
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JLabel square = new JLabel();
                square.setOpaque(true);
                square.setBackground((i + j) % 2 == 0 ? Color.GRAY : Color.DARK_GRAY);
                chessBoard.add(square);
                squares[i][j] = square;
            }
        }

        // Set piece icons on the board
        setPieceIcons();

        // Add MouseListener to each piece label
        addPieceListeners();

        setVisible(true);
        

    }

    private void setPieceIcons() {
        // Set piece icons on the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = boardLogic.getPieceAt(i, j);
                if (piece != null) {
                    String fileName = "assets/" + piece.getClass().getSimpleName().toLowerCase() + "-"
                            + piece.getColor().substring(0, 1).toLowerCase() + ".png";
                    int width = 75; // Adjust as needed
                    int height = 75; // Adjust as needed
                    ImageIcon icon = loadImage(fileName, width, height);
                    if (icon != null) {
                        squares[i][j].setIcon(icon);
                    }
                }
            }
        }
    }

    private ImageIcon loadImage(String fileName, int width, int height) {
        // Load image from file
        ImageIcon icon = new ImageIcon(getClass().getResource(fileName));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    private void addPieceListeners() {
        // Add MouseListener to each piece label
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JLabel square = squares[i][j];
                final int finalI = i; // Create a final variable to capture the current value of i
                final int finalJ = j;
                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Check if it's the current player's turn
                        if (boardLogic.getCurrentPlayer().equals(selectedPiece != null ? selectedPiece.getColor() : boardLogic.getCurrentPlayer())) {
                            // Get the chess piece associated with this square
checkForCheckMate();
System.out.println(boardLogic.isCheckMate());
                            ChessPiece piece = boardLogic.getPieceAt(finalI, finalJ);
                            if (piece != null) {
                                // If a piece is selected, attempt to move it
                                if (selectedPiece != null) {
                                    // Check if the move is valid
                                                                // Get the chess piece associated with this square
checkForCheckMate();
System.out.println(boardLogic.isCheckMate());
                                    if (selectedPiece.isValidMove(boardLogic.getBoard(), startX, startY, finalI, finalJ)) {
                                        // Create a copy of the board state before the move
                                        previousBoardState = copyBoardState(boardLogic.getBoard());
                                        
                                        // Move the piece on the GUI
                                        squares[finalI][finalJ].setIcon(squares[startX][startY].getIcon());
                                        squares[startX][startY].setIcon(null);
    
                                        // Update the board logic to reflect the move
                                        boardLogic.getBoard()[finalI][finalJ] = selectedPiece;
                                        boardLogic.getBoard()[startX][startY] = null;
    
                                        // Check for check
                                        if (boardLogic.isCheckMate()) {
                                            System.out.println("CHECKMATE");
                                        }
                                                                    // Get the chess piece associated with this square
checkForCheckMate();
System.out.println(boardLogic.isCheckMate());
                                        if (boardLogic.isCheck(boardLogic.getBoard(), boardLogic.getCurrentPlayer())) {
                                            if (boardLogic.isCheckMate()) {
                                                System.out.println("CHECKMATE");
                                            }
                                            System.out.println("Check!");
                                            selectedPiece = null;
                                            restorePreviousBoardState(); // Restore the previous board state
                                            checkForCheckMate();
                                            return; // Do not proceed with the move if in check
                                        }
    
                                        // Reset selectedPiece
                                        selectedPiece = null;
    
                                        // Switch player turn
                                        boardLogic.switchPlayer();
                                        checkForCheckMate();
                                    } else {
                                        System.out.println("Invalid move. Try again.");
                                    }
                                } else {
                                    // Select the piece for movement
                                    selectedPiece = piece;
                                    startX = finalI;
                                    startY = finalJ;
                                    System.out.println("Selected piece: " + selectedPiece.getClass().getSimpleName() + " at ("
                                            + finalI + ", " + finalJ + ")");
                                }
                            } else {
                                // If no piece is present, and a piece is selected, attempt to move the selected piece
                                                            // Get the chess piece associated with this square
checkForCheckMate();
System.out.println(boardLogic.isCheckMate());
                                if (selectedPiece != null) {
                                    // Check if the move is valid
                                                                // Get the chess piece associated with this square
checkForCheckMate();
System.out.println(boardLogic.isCheckMate());
                                    if (selectedPiece.isValidMove(boardLogic.getBoard(), startX, startY, finalI, finalJ)) {
                                        // Create a copy of the board state before the move
                                        previousBoardState = copyBoardState(boardLogic.getBoard());
                                        
                                        // Move the piece on the GUI
                                        squares[finalI][finalJ].setIcon(squares[startX][startY].getIcon());
                                        squares[startX][startY].setIcon(null);
    
                                        // Update the board logic to reflect the move
                                        boardLogic.getBoard()[finalI][finalJ] = selectedPiece;
                                        boardLogic.getBoard()[startX][startY] = null;
                                // Get the chess piece associated with this square
checkForCheckMate();
System.out.println(boardLogic.isCheckMate());
                                        // Check for check
                                        if (boardLogic.isCheck(boardLogic.getBoard(), boardLogic.getCurrentPlayer())) {
                                            if (boardLogic.isCheckMate()) {
                                                System.out.println("CHECKMATE");
                                            }
                                            System.out.println("Check!");
                                            selectedPiece = null;
                                            restorePreviousBoardState(); // Restore the previous board state
                                            checkForCheckMate();
                                            return; // Do not proceed with the move if in check
                                        }
    
                                        // Reset selectedPiece
    
                                        // Switch player turn
                                        boardLogic.switchPlayer();
                                        checkForCheckMate();
                                    } else {
                                                                    // Get the chess piece associated with this square
checkForCheckMate();
System.out.println(boardLogic.isCheckMate());

                                        System.out.println("Invalid move. Try again.");
                                    }
                                } else {
                                                                // Get the chess piece associated with this square
checkForCheckMate();
System.out.println(boardLogic.isCheckMate());
                                    System.out.println("No piece selected.");

                                }
                            }
                        } else {
                                                        // Get the chess piece associated with this square
checkForCheckMate();
System.out.println(boardLogic.isCheckMate());
                            // Reset selectedPiece if it's not the player's turn
                            selectedPiece = null;
                            System.out.println("It's not your turn.");
                        }
                    }
                });
            }
        }
    }
    
    // Method to create a copy of the board state
    private ChessPiece[][] copyBoardState(ChessPiece[][] board) {
        ChessPiece[][] copy = new ChessPiece[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }
    
    // Method to restore the previous board state
// Method to restore the previous board state
// Method to restore the previous board state
private void restorePreviousBoardState() {
    for (int i = 0; i < previousBoardState.length; i++) {
        for (int j = 0; j < previousBoardState[0].length; j++) {
            boardLogic.getBoard()[i][j] = previousBoardState[i][j];
            if (previousBoardState[i][j] == null && boardLogic.getPieceAt(i, j) == null) {
                // Set the icon of the square to null for the square where the attempted move was made
                squares[i][j].setIcon(null);
            } else if (previousBoardState[i][j] != null) {
                // If there was a piece in the previous state, update its position on the GUI
                ChessPiece piece = previousBoardState[i][j];
                String fileName = "assets/" + piece.getClass().getSimpleName().toLowerCase() + "-"
                        + piece.getColor().substring(0, 1).toLowerCase() + ".png";
                int width = 75; // Adjust as needed
                int height = 75; // Adjust as needed
                ImageIcon icon = loadImage(fileName, width, height);
                if (icon != null) {
                    squares[i][j].setIcon(icon);
                }
            }
        }
    }
}
// Inside ChessGUI class

private void checkForCheckMate() {
    if (boardLogic.isCheckMate()) {
        String winner = boardLogic.getCurrentPlayer().equals("white") ? "Black" : "White";
        JOptionPane.showMessageDialog(this, "Checkmate! " + winner + " wins!");
        // You can add additional actions here, such as resetting the game or exiting
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChessGUI());
    }
}
