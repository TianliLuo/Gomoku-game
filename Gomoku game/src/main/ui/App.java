package ui;


import model.ChessBoard;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

//app class realizes to use some program to run this game
public class App extends JFrame {
    private static final String JSON_STORE = "./data/ChessBoard.json";
    JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    //JButton start;
    private ChessBoard chessboard;
    private int[][] board;
    private ArrayList pieceArray = new ArrayList();
    BufferedImage background;
    JButton saveButton;

    //MODIFIES: this
    //EFFECTS: construct the App Class
    public App(ChessBoard chessBoard) {
        importBackground();
        setName("GomokuApp");
        setSize(500, 500);
        setBackground(Color.BLUE);
        Container content = getContentPane();
        content.setLayout(new FlowLayout());
        setResizable(false);
        setVisible(true);
        this.chessboard = chessBoard;
        this.board = chessBoard.getBoard();
        setSaveButton();
        content.add(saveButton);
        transArrayToArraylist();
        System.out.println(ifPlayer1Turn());
        addHintInformation();
        addMouseListener();
    }

    //MODIFIES:this
    //EFFECTS:import the background using IO
    public void importBackground() {
        try {
            background = ImageIO.read(new File("C:\\Users\\ltl\\Desktop\\background.jpg"));
        } catch (IOException e) {
            System.out.println("cannot find the file");
        }

    }

    //EFFECTS: if it is the player1 turn, give hint to let player1 put piece,
    //give the hint to let player2 put piece otherwise.
    public void addHintInformation() {
        if (ifPlayer1Turn()) {
            JOptionPane.showMessageDialog(this,
                    "this is the " + chessboard.getPlayer1().getName()
                            + " turn, please put the chessPiece into the chessBoard");
        } else {
            JOptionPane.showMessageDialog(this,
                    "this is the " + chessboard.getPlayer2().getName()
                            + " turn, please put the chessPiece into the chessBoard");
        }
    }

    //EFFECTS: judge if this is the player1 turn, if it is, return true, return false Otherwise.
    public Boolean ifPlayer1Turn() {
        if (pieceArray.size() % 2 == 0 && chessboard.getPlayer1().getPiece() == 1) {
            return true;
        }
        if (pieceArray.size() % 2 == 1 && chessboard.getPlayer1().getPiece() == 1) {
            return false;
        }
        if (pieceArray.size() % 2 == 0 && chessboard.getPlayer1().getPiece() == 2) {
            return false;
        }
        if (pieceArray.size() % 2 == 1 && chessboard.getPlayer1().getPiece() == 2) {
            return true;
        }
        return false;

    }

    //MODIFIES: this
    //EFFECTS: set the save Button
    public void setSaveButton() {
        saveButton = new JButton();
        URL saveUrl = getClass().getResource("/image/save.jpg");
        Icon saveIcon = new ImageIcon(saveUrl);
        saveButton.setIcon(saveIcon);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save(chessboard);

            }
        });
    }


    //MODIFIES: This
    //EFFECTS: translate the Array into the Arraylist and save in the arraylist
    public void transArrayToArraylist() {
        int sideLength = (int) (getWidth() - 100) / (19 - 1);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 1) {
                    pieceArray.add(new PiecePosition(50 + i * sideLength, 50 + j * sideLength, 1));
                }
                if (board[i][j] == 2) {
                    pieceArray.add(new PiecePosition(50 + i * sideLength, 50 + j * sideLength, 2));
                }

            }
        }
    }

    //MODIFIES: this
    //EFFECTS: add the MouseListener to monitor the mouse event in order to put the chessPiece
    public void addMouseListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    super.mouseClicked(e);
                    int sideLength = (int) (getWidth() - 100) / (19 - 1);
                    int x = (int) (e.getX() - 50 + 1 / 2 * sideLength) / sideLength;
                    int y = (int) (e.getY() - 50 + 1 / 2 * sideLength) / sideLength;
                    if (ifHasPiece(x, y)) {
                        addPositionToArrayList(x, y);
                        ChessBoard chessBoard = new ChessBoard(19, "player1",
                                "player2", 1, board);
                        if (!chessBoard.ifWin()) {
                            repaint();
                        } else {
                            repaintMethod();
                        }
                    }
                } catch (Exception exception) {
                    System.out.println("throw error");
                }
            }


        });
    }


    //EFFECT: repaint the chessBoard
    public void repaintMethod() {
        if (ifPlayer1Turn()) {
            repaint();
            player2Win();
            System.exit(0);
        } else {
            repaint();
            player1Win();
            System.exit(0);
        }
    }


    //EFFECTS: consume the x-coordinate and y-coordinate,add this information to the 2D-Array and the arraylist.
    public void addPositionToArrayList(int x, int y) {
        int sideLength = (int) (getWidth() - 100) / (19 - 1);
        if (pieceArray.size() % 2 == 0) {
            board[x][y] = 1;
            pieceArray.add(new PiecePosition(50 + x * sideLength, 50 + y * sideLength, 1));
            chessboard.putChessPieceO(x + 1, y + 1);
        } else {
            board[x][y] = 2;
            pieceArray.add(new PiecePosition(50 + x * sideLength, 50 + y * sideLength, 2));
            chessboard.putChessPieceX(x + 1, y + 1);
        }
    }

    //EFFECT: judge if this place has a piece, if it is, return true, return false otherwise.
    public Boolean ifHasPiece(int x, int y) {
        if (board[x][y] == 1) {
            JOptionPane.showMessageDialog(this, "This Position has a piece, please try again");
            return false;
        }
        if (board[x][y] == 2) {
            JOptionPane.showMessageDialog(this, "This Position has a piece, please try again");
            return false;
        }
        return true;
    }

    //EFFECTS: pop the showMessageDialog if the black won the game
    public void player1Win() {
        JOptionPane.showMessageDialog(this,
                "Congratulation! " + chessboard.getPlayer1().getName() + " win the game, see you next time");
    }

    //EFFECTS: pop the showMessageDialog if the white won the game
    public void player2Win() {
        JOptionPane.showMessageDialog(this,
                "Congratulation! " + chessboard.getPlayer2().getName() + " win the game, see you next time");
    }

    //EFFECTS: paint the line in the chessBoard
    public void paintPieces(Graphics g) {
        for (int i = 0; i < pieceArray.size(); i++) {
            PiecePosition piecePosition = (PiecePosition) pieceArray.get(i);
            int sideLength = (int) (getWidth() - 100) / (19 - 1);
            if (piecePosition.getBlack() == 1) {
                g.setColor(Color.BLACK);
                g.fillOval(piecePosition.getPositionX(),
                        piecePosition.getPositionY(), sideLength, sideLength);
            }
            if (piecePosition.getBlack() == 2) {
                g.setColor(Color.WHITE);
                g.fillOval(piecePosition.getPositionX(),
                        piecePosition.getPositionY(), sideLength, sideLength);
            }
        }


    }


    //EFFECTS: draw the JFrame.
    public void paint(Graphics g) {
        try {
            super.paint(g);
            g.drawImage(background, 0, 0, null);
            for (int i = 0; i < 19; i++) {
                int sideLength = (int) (getWidth() - 100) / (19 - 1);
                g.drawLine(50, 50 + sideLength * i, 50 + sideLength * 18, 50 + sideLength * i);
                g.drawLine(50 + sideLength * i, 50, 50 + sideLength * i, 50 + sideLength * 18);
            }
            g.getFont();
            if (ifPlayer1Turn()) {
                g.drawString("this is " + chessboard.getPlayer1().getName() + " turn", 60, 50);
            } else {
                g.drawString("this is " + chessboard.getPlayer2().getName() + " turn", 60, 50);
            }

            paintPieces(g);
        } catch (Exception e) {
            System.out.println("throw an exception");
        }
    }

    // EFFECTS: saves the chessboard to file
    public void save(ChessBoard chessBoard) {
        try {
            jsonWriter.open();
            jsonWriter.write(chessBoard);
            jsonWriter.close();
            System.out.println("you have save this game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}
