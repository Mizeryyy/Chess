import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessGUI extends JFrame {
    private JPanel chessBoard;
    private JLabel[][] squares;
    private ChessBoard boardLogic;
    private ChessPiece selectedPiece;
    private int startX, startY;
    private ChessPiece[][] previousBoardState; // to store the previous board state

    public ChessGUI() {
        setTitle("Chess Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // initialize chessboard
        chessBoard = new JPanel(new GridLayout(8, 8));
        getContentPane().add(chessBoard);

        // create squares array
        squares = new JLabel[8][8];

        // create ChessBoard instance
        boardLogic = new ChessBoard();

        // populate chessboard
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JLabel square = new JLabel();
                square.setOpaque(true);
                square.setBackground((i + j) % 2 == 0 ? Color.GRAY : Color.DARK_GRAY);
                chessBoard.add(square);
                squares[i][j] = square;
            }
        }

        // set piece icons on the board
        setPieceIcons();

        // add MouseListener to each piece label
        addPieceListeners();

        setVisible(true);
    }

    private void setPieceIcons() {
        // set piece icons on the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = boardLogic.getPieceAt(i, j);
                if (piece != null) {
                    String fileName = "assets/" + piece.getClass().getSimpleName().toLowerCase() + "-"
                            + piece.getColor().substring(0, 1).toLowerCase() + ".png";
                    int width = 75; // adjust as needed
                    int height = 75; // adjust as needed
                    ImageIcon icon = loadImage(fileName, width, height);
                    if (icon != null) {
                        squares[i][j].setIcon(icon);
                    }
                }
            }
        }
    }

    private ImageIcon loadImage(String fileName, int width, int height) {
        // load image from file
        ImageIcon icon = new ImageIcon(getClass().getResource(fileName));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    private void addPieceListeners() {
        // add MouseListener to each tile
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JLabel square = squares[i][j];
                final int finalI = i; // create a final variable to capture the current value of i
                final int finalJ = j;
                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        // check if it's the current player's turn and if there is a piece on this tile
                        ChessPiece piece = boardLogic.getPieceAt(finalI, finalJ);
                        if (piece != null && piece.getColor().equals(boardLogic.getCurrentPlayer())) {
                            // select the piece for movement
                            selectedPiece = piece;
                            startX = finalI;
                            startY = finalJ;
                            System.out.println("Selected piece: " + selectedPiece.getClass().getSimpleName() + " at ("
                                    + finalI + ", " + finalJ + ")");
                        } else {
                            // if no piece is present, and a piece is selected, attempt to move the selected piece
                            if (selectedPiece != null) {
                                
                                // check if the move is valid
                                if (selectedPiece.isValidMove(boardLogic.getBoard(), startX, startY, finalI, finalJ)) {
                                    
                                    // create a copy of the board state before the move
                                    previousBoardState = copyBoardState(boardLogic.getBoard());
    
                                    // move the piece on the GUI
                                    squares[finalI][finalJ].setIcon(squares[startX][startY].getIcon());
                                    squares[startX][startY].setIcon(null);
    
                                    // update the board logic to reflect the move
                                    boardLogic.getBoard()[finalI][finalJ] = selectedPiece;
                                    boardLogic.getBoard()[startX][startY] = null;
    
                                    
    
                                    // reset selectedPiece
                                    selectedPiece = null;
                                    if (boardLogic.isCheck(boardLogic.getBoard(), boardLogic.getCurrentPlayer())) {
                                        if (boardLogic.isCheckMate()) {
                                            System.out.println("CHECKMATE");
                                        }
                                        System.out.println("Check!");
                                        selectedPiece = null;
                                        restorePreviousBoardState(); // restore the previous board state
                                        checkForCheckMate();
                                        return; // do not proceed with the move if in check
                                    }
                                    // switch player turn
                                    boardLogic.switchPlayer();
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
    
    
    // method to create a copy of the board state
    private ChessPiece[][] copyBoardState(ChessPiece[][] board) {
        ChessPiece[][] copy = new ChessPiece[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }
    
    // method to restore the previous board state
    private void restorePreviousBoardState() {
        for (int i = 0; i < previousBoardState.length; i++) {
            for (int j = 0; j < previousBoardState[0].length; j++) {
                boardLogic.getBoard()[i][j] = previousBoardState[i][j];
                if (previousBoardState[i][j] == null && boardLogic.getPieceAt(i, j) == null) {
                    // set the icon of the square to null for the square where the attempted move was made
                    squares[i][j].setIcon(null);
                } else if (previousBoardState[i][j] != null) {
                    // if there was a piece in the previous state, update its position on the GUI
                    ChessPiece piece = previousBoardState[i][j];
                    String fileName = "assets/" + piece.getClass().getSimpleName().toLowerCase() + "-"
                            + piece.getColor().substring(0, 1).toLowerCase() + ".png";
                    int width = 75; // adjust as needed
                    int height = 75; // adjust as needed
                    ImageIcon icon = loadImage(fileName, width, height);
                    if (icon != null) {
                        squares[i][j].setIcon(icon);
                    }
                }
            }
        }
    }

    // method to check for checkmate
    private void checkForCheckMate() {
        if (boardLogic.isCheckMate()) {
            String winner = boardLogic.getCurrentPlayer().equals("white") ? "Black" : "White";
            JOptionPane.showMessageDialog(this, "Checkmate! " + winner + " wins!");
            // you can add additional actions here, such as resetting the game or exiting
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChessGUI());
    }
}

