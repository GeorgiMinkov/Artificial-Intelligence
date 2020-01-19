package ai.fmi.georgi.minkov.graphic;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ai.fmi.georgi.minkov.minimax.Algorithm;
import ai.fmi.georgi.minkov.minimax.AlphaBetaPruning;
import ai.fmi.georgi.minkov.minimax.MiniMax;
import ai.fmi.georgi.minkov.util.Board;

public class UI extends JFrame {
	private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    private Board board;
    private Panel panel;
    private BufferedImage imageBackground, imageX, imageO;

    private enum Mode {Player, AI}
    private Mode mode;
    
    private Point[] cells;
    
    private static final int DISTANCE = 100;
    
    private UI () {
        this(Mode.AI);
    }
    
    private UI(Mode mode) {
        this.mode = mode;
        board = new Board();
        loadCells();
        panel = createPanel();
        setWindowProperties();
        loadImages();
    }
    
    private void loadCells() {
        cells = new Point[9];

        cells[0] = new Point(109, 109);
        cells[1] = new Point(299, 109);
        cells[2] = new Point(489, 109);
        cells[3] = new Point(109, 299);
        cells[4] = new Point(299, 299);
        cells[5] = new Point(489, 299);
        cells[6] = new Point(109, 489);
        cells[7] = new Point(299, 489);
        cells[8] = new Point(489, 489);
    }
    
    private Panel createPanel () {
        Panel panel = new Panel();
        Container cp = getContentPane();
        cp.add(panel);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.addMouseListener(new MyMouseAdapter());
        return panel;
    }
    
    
    private void setWindowProperties() {
        setResizable(false);
        pack();
        setTitle("Tic Tac Toe - AI/come and beat me");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void loadImages () {
        imageBackground = getImage("tic_tac_toe_playground.png");
        imageX = getImage("tic_tac_toe_X.png");
        imageO = getImage("tic_tac_toe_O.png");
    }
    
    private static BufferedImage getImage (String fileName) {

        BufferedImage image = null;
        try {
            image = ImageIO.read((new File("C:\\Users\\georg\\Documents\\workspace-spring-tool-suite-4-4.3.1.RELEASE\\MiniMax\\tic_tac_toe\\" + fileName)));
        } catch (IOException ex) {
            throw new RuntimeException("Image could not be loaded.");
        }

        return image;
    }
    
    private class Panel extends JPanel {

        /**
		 * 
		 */
		private static final long serialVersionUID = 8555233560736293319L;

		@Override
        protected void paintComponent(Graphics el) {
            super.paintComponent(el);
            paintTicTacToe((Graphics2D) el);
        }
		
        private void paintTicTacToe (Graphics2D el) {
            setProperties(el);
            paintBoard(el);
            paintWinner(el);
        }
        
        private void setProperties (Graphics2D el) {
        	el.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        	el.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        	el.drawImage(imageBackground, 0, 0, null);

        	el.drawString("", 0, 0);
        }
        
        private void paintBoard (Graphics2D el) {
            Board.State[][] boardArray = board.toArray();

            int offset = 20;

            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    if (boardArray[y][x] == Board.State.X) {
                        el.drawImage(imageX, offset + 190 * x, offset + 190 * y, null);
                    } else if (boardArray[y][x] == Board.State.O) {
                    	el.drawImage(imageO, offset + 190 * x, offset + 190 * y, null);
                    }
                }
            }
        }

        private void paintWinner (Graphics2D el) {
            if (board.isGameOver()) {
            	el.setColor(new Color(0, 0, 0));
            	el.setFont(new Font("TimesRoman", Font.PLAIN, 50));

                String outcome = null;

                if (board.getWinner() == Board.State.BLANK) {
                	outcome = "Draw";
                } else {
                	outcome = board.getWinner() + " Wins!";
                }

                el.drawString(outcome, 300 - getFontMetrics(el.getFont()).stringWidth(outcome)/2, 315);

            }
        }
    }

    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mouseClicked(e);

            if (board.isGameOver()) {
                board.initializeStartState();
                panel.repaint();
            } else {
                playMove(e);
            }

        }

        private void playMove (MouseEvent e) {
            int move = getMove(e.getPoint());

            if (!board.isGameOver() && move != -1) {
                boolean validMove = board.move(move);
                if (mode == Mode.AI && validMove && !board.isGameOver()) {
                	Algorithm algo = new AlphaBetaPruning(Double.POSITIVE_INFINITY, true);
                	algo.run(board.getTurn(), board);
                	
//                	Algorithm algo = new MiniMax(Double.POSITIVE_INFINITY);
//                	algo.run(board.getTurn(), board);
                	
//                	Algorithm algo = new AlphaBetaPruning(Double.POSITIVE_INFINITY);
//                	algo.run(board.getTurn(), board);
                }
                panel.repaint();
            }
        }
        
        private int getMove (Point point) {
            for (int i = 0; i < cells.length; i++) {
                if (distance(cells[i], point) <= DISTANCE) {
                    return i;
                }
            }
            return -1;
        }

        private double distance (Point p1, Point p2) {
            double xDiff = p1.getX() - p2.getX();
            double yDiff = p1.getY() - p2.getY();

            double xDiffSquared = xDiff * xDiff;
            double yDiffSquared = yDiff * yDiff;

            return Math.sqrt(xDiffSquared + yDiffSquared);
        }
    }

    public static void main(String[] args) {

        if (args.length == 1) {
            System.out.println("Game Mode: Player vs. Player");
            SwingUtilities.invokeLater(() -> new UI(Mode.Player));
        } else {
            System.out.println("Game Mode: Player vs. AI");
            SwingUtilities.invokeLater(() -> new UI(Mode.AI));
        }

    }
}
