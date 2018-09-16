package Display;

import Game.Board;
import Util.Util;
import Engine.Engine;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;


public class Display extends JFrame{

    final Board board;


    DrawCanvas canvas;

    public final int CANVAS_WIDTH = 1000;
    public final int CANVAS_HEIGHT = 1000;

    public Display(Board in) {
        this.board = in;

        canvas = new DrawCanvas();    // Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        // Set the Drawing JPanel as the JFrame's content-pane
        Container cp = getContentPane();
        cp.add(canvas);
        // or "setContentPane(canvas);"

        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        pack();              // Either pack() the components; or setSize()
        setTitle("Chess");  // "super" JFrame sets the title
        setVisible(true);    // "super" JFrame show

        canvas.addMouseListener(new MouseAdapter() {

            public final int NULL = -1;

            int selectPieceX = NULL;
            int selectPieceY = NULL;

            @Override
            public void mousePressed(MouseEvent e) {

                int x = e.getX() / (CANVAS_WIDTH / 8);
                int y = 7 - e.getY() / (CANVAS_HEIGHT / 8);

                if(selectPieceX != NULL) {

                    ArrayList<Integer> listStart = new ArrayList<Integer>();
                    listStart.add(selectPieceX);
                    listStart.add(selectPieceY);

                    ArrayList<Integer> listEnd = new ArrayList<Integer>();
                    listEnd.add(x);
                    listEnd.add(y);

                    if(board.movePieces(listStart, listEnd)) {
                        selectPieceX = NULL;
                        selectPieceY = NULL;
                        canvas.repaint();

                        Engine engine = new Engine(board);
                        ArrayList<ArrayList<ArrayList<Integer>>> temp = engine.evaluateMoves(board, 5);
                        System.out.println(temp.get(1).get(0).get(0) + "," + temp.get(1).get(0).get(1) + "," + temp.get(1).get(1).get(0) + "," + temp.get(1).get(1).get(1));
                    }
                    else {
                        selectPieceX = x;
                        selectPieceY = y;
                    }

                }
                else {
                    selectPieceX = x;
                    selectPieceY = y;
                }

            }
        });
    }



    private class DrawCanvas extends JPanel {
        // Override paintComponent to perform your own painting
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);     // paint parent's background
            setBackground(Color.BLACK);  // set background color for this JPanel

            for(int x = 0; x < 8; x++) {
                for(int y = 0; y < 8; y++) {
                    if((x + y) % 2 == 0) {
                        g.setColor(Color.WHITE);
                    }
                    else {
                        g.setColor(Color.BLACK);
                    }
                    g.fillRect(x * CANVAS_WIDTH / 8, (7 - y) * CANVAS_HEIGHT / 8, CANVAS_WIDTH / 8, CANVAS_HEIGHT / 8);

                    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(null);
                    list.add(x);
                    list.add(y);

                    int temp = Util.searchEqualEntry(board.whitePieces, list);
                    int temp1 = Util.searchEqualEntry(board.blackPieces, list);

                    try {
                        if (temp != Util.NO_ENTRY) {
                            if (board.whitePieces.get(temp).get(0) == board.PIECE_PAWN) {
                                image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/chesspieces/white/pawn.png"));
                            } else if (board.whitePieces.get(temp).get(0) == board.PIECE_KNIGHT) {
                                image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/chesspieces/white/knight.png"));
                            } else if (board.whitePieces.get(temp).get(0) == board.PIECE_BISHOP) {
                                image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/chesspieces/white/bishop.png"));
                            } else if (board.whitePieces.get(temp).get(0) == board.PIECE_ROOK) {
                                image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/chesspieces/white/rook.png"));
                            } else if (board.whitePieces.get(temp).get(0) == board.PIECE_QUEEN) {
                                image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/chesspieces/white/queen.png"));
                            } else if (board.whitePieces.get(temp).get(0) == board.PIECE_KING) {
                                image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/chesspieces/white/king.png"));
                            }
                        } else if (temp1 != Util.NO_ENTRY) {
                            if (board.blackPieces.get(temp1).get(0) == board.PIECE_PAWN) {
                                image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/chesspieces/black/pawn.png"));
                            } else if (board.blackPieces.get(temp1).get(0) == board.PIECE_KNIGHT) {
                                image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/chesspieces/black/knight.png"));
                            } else if (board.blackPieces.get(temp1).get(0) == board.PIECE_BISHOP) {
                                image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/chesspieces/black/bishop.png"));
                            } else if (board.blackPieces.get(temp1).get(0) == board.PIECE_ROOK) {
                                image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/chesspieces/black/rook.png"));
                            } else if (board.blackPieces.get(temp1).get(0) == board.PIECE_QUEEN) {
                                image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/chesspieces/black/queen.png"));
                            } else if (board.blackPieces.get(temp1).get(0) == board.PIECE_KING) {
                                image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/chesspieces/black/king.png"));
                            }
                        }

                        g.drawImage(image, x * CANVAS_WIDTH / 8, (7 - y) * CANVAS_HEIGHT / 8, null);
                    }
                    catch (Exception e) {

                    }
                }
            }


        }
    }

}
