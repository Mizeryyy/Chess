import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessGUI extends JFrame {
    private JPanel chessBoard;
    private JLabel[][] squares;
    private ChessBoard boardLogic;
    // ChessPiece[][] board = new ChessPiece[8][8];
    private ChessPiece selectedPiece;
    private int startX, startY;

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
                        // Get the chess piece associated with this square
                        ChessPiece piece = boardLogic.getPieceAt(finalI, finalJ);
                        if (piece != null) {
                            // Check if it's the correct player's turn
                            if (selectedPiece == null
                                    || selectedPiece.getColor().equals(boardLogic.getCurrentPlayer())) {
                                selectedPiece = piece;
                                startX = finalI;
                                startY = finalJ;
                                System.out
                                        .println("Selected piece: " + selectedPiece.getClass().getSimpleName() + " at ("
                                                + finalI + ", " + finalJ + ")");
                            } else {
                                System.out.println("It's not your turn.");
                            }
                        } else {
                            // If no piece is selected, check if a piece is already selected
                            if (selectedPiece != null) {
                                // Check if the move is valid
                                if (selectedPiece.isValidMove(boardLogic.getBoard(), startX, startY, finalI, finalJ)) {
                                    // Move the piece on the GUI
                                    squares[finalI][finalJ].setIcon(squares[startX][startY].getIcon());
                                    squares[startX][startY].setIcon(null);

                                    // Update the board logic to reflect the move
                                    boardLogic.getBoard()[finalI][finalJ] = selectedPiece;
                                    boardLogic.getBoard()[startX][startY] = null;

                                    // Check if a piece is being captured
                                    ChessPiece capturedPiece = boardLogic.getPieceAt(finalI, finalJ);
                                    if (capturedPiece != null) {
                                        System.out.println(
                                                "Captured " + capturedPiece.getClass().getSimpleName() + " at ("
                                                        + finalI + ", " + finalJ + ")");
                                        // Remove the captured piece from the board logic
                                        boardLogic.getBoard()[finalI][finalJ] = null;
                                    }

                                    // Switch player turns
                                    boardLogic.switchPlayer();
                                    System.out.println("Current player: " + boardLogic.getCurrentPlayer());
                                } else {
                                    System.out.println("Invalid move. Try again.");
                                }
                            } else {
                                System.out.println("No piece selected.");
                            }
                        }
                    }
                });
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChessGUI());
    }
}
