package ui;


import model.ChessBoard;
import persistence.JsonReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

// ButtonClass shows three Button in the JFrame and we can use these three Button to start the game
public class ButtonClass extends JFrame {
    private static final String JSON_STORE = "./data/ChessBoard.json";
    ChessBoard chessBoard;
    JsonReader jsonReader = new JsonReader(JSON_STORE);
    BufferedImage background;
    JButton start = new JButton();
    JButton restart = new JButton();
    JButton hintButton = new JButton();
    Box northBox = Box.createVerticalBox();
    Box hbox = Box.createHorizontalBox();

    //MODIFIES: this
    //EFFECTS: construct the button class
    public ButtonClass() {
        try {
            background = ImageIO.read(new File("C:\\Users\\ltl\\Desktop\\background1.jpg"));
            setName("GomokuApp");
            setSize(400, 567);
            setVisible(true);
            setButton();
            northBox.add(Box.createVerticalStrut(100));
            hbox.add(Box.createHorizontalStrut(10));
            hbox.add(start);
            hbox.add(Box.createHorizontalStrut(10));
            hbox.add(restart);
            hbox.add(Box.createHorizontalStrut(10));
            hbox.add(hintButton);
            hbox.add(Box.createHorizontalStrut(10));
            Box southBox = Box.createHorizontalBox();
            southBox.add(Box.createVerticalStrut(100));
            add(northBox, BorderLayout.NORTH);
            add(hbox, BorderLayout.CENTER);
            add(southBox, BorderLayout.SOUTH);
            setActionListener();
        } catch (Exception e) {
            System.out.println("throw an error");
        }
    }

    //EFFECTS: draw the background of the JFrame
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(background, 0, 0, null);
    }

    //MODIFIES: this
    //EFFECTS: initialize this three Buttons
    public void setButton() {
        URL startUrl = getClass().getResource("/image/start.png");
        URL restartUrl = getClass().getResource("/image/restart.png");
        URL hintUrl = getClass().getResource("/image/hint.png");
        Icon startIcon = new ImageIcon(startUrl);
        Icon restartIcon = new ImageIcon(restartUrl);
        Icon hintIcon = new ImageIcon(hintUrl);
        start = new JButton();
        restart = new JButton();
        hintButton = new JButton();
        start.setIcon(startIcon);
        restart.setIcon(restartIcon);
        hintButton.setIcon(hintIcon);
    }

    //MODIFIES: this
    //EFFECTS: initialize this three Buttons and set the actionListener for each button
    public void setActionListener() {
        iniStartButton();
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadWorkRoom();
                try {
                    App chessBoardB = new App(chessBoard);
                } catch (Exception exception) {
                    System.out.println("throw an exception");
                }
            }

        });
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hintInformation();
            }
        });


    }

    //MODIFIES: this
    //EFFECTS: initialize start button
    public void iniStartButton() {
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name1 = setName1();
                    int a = Integer.parseInt(setPiece());
                    String name2 = setName2();
                    chessBoard = new ChessBoard(19, name1, name2, a);

                    App app = new App(chessBoard);
                } catch (Exception exception) {
                    System.out.println("throw an exception");
                }
            }
        });
    }

    //EFFECTS: pop the Message Dialog to show the game information
    public void hintInformation() {
        JOptionPane.showMessageDialog(this, "Gobang is a pure strategy board game played by"
                + "\n" + "two people. Usually, both sides use black and white pi"
                + "\n" + "eces and take turns to play on the intersection of straight lin"
                + "\n" + "es and horizontal lines. The one who forms five sub-lines on hori"
                + "\n" + "zontal lines, straight lines or diagonal lines first wins.");
    }

    //EFFECTS: let player1 enter his name and return his name as string
    public String setName1() {
        return JOptionPane.showInputDialog("Please enter the first player name");
    }

    //EFFECTS: let player1 choose the chessPiece
    public String setPiece() {
        return JOptionPane.showInputDialog("Did you want to choose black piece(enter 1) or white piece(enter 2)");
    }

    //EFFECTS: let player2 enter his name and return his name as string
    public String setName2() {
        return JOptionPane.showInputDialog("Please enter the second player name");
    }

    // MODIFIES: this
    // EFFECTS: loads chessboard from file
    private void loadWorkRoom() {
        try {
            this.chessBoard = jsonReader.read();
            System.out.println("Loaded the chessboard from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
