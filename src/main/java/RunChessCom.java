import Engine.Engine;
import Game.Board;
import Display.Display;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RunChessCom {

    static boolean playerColor;

    public static void main(String[] args) throws Exception{
//        WebClient client = new WebClient(BrowserVersion.CHROME);
//
//
//        HtmlPage page = client.getPage("https://lichess.org/login?referrer=/");
//        ((HtmlInput) page.getFirstByXPath("//input[@id='form3-username']")).setValueAttribute("lawrence.tang.20@falmouth.k12.ma.us");
//        ((HtmlInput) page.getFirstByXPath("//input[@id='form3-password']")).setValueAttribute("mychess123");
//        page = ((HtmlButton) page.getFirstByXPath("//button[@type='submit']")).click();

        //requires specific sized board
        TimeUnit.SECONDS.sleep(5);
        int[] test = getPieceType(317,  802);
        if(test[1] == COLOR_WHITE) {
            playerColor = Board.WHITE;
        }
        else {
            playerColor = Board.BLACK;
            click(1559, 629);
        }

        while(true) {

            Board board = new Board();
            board.turn = playerColor;
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    ArrayList<Integer> array = new ArrayList<Integer>();
                    int[] a = getPieceType(x * 95 + 317, -y * 95 + 802);
                    array.add(a[0]);
                    array.add(x);
                    array.add(y);
                    if (a[1] == COLOR_WHITE) {
                        board.whitePieces.add(array);
                    } else if (a[1] == COLOR_BLACK) {
                        board.blackPieces.add(array);
                    }
                }
            }
            Engine engine = new Engine(board);
            ArrayList<ArrayList<ArrayList<Integer>>> temp = engine.evaluateMoves(board, 3);
            movePieces(temp.get(1).get(0), temp.get(1).get(1));
//            Display display = new Display(board);
//            TimeUnit.SECONDS.sleep(3);
//            display.dispatchEvent(new WindowEvent(display, WindowEvent.WINDOW_CLOSING));
            TimeUnit.SECONDS.sleep(10);
            
        }
    }

    public static void movePieces(ArrayList<Integer> from, ArrayList<Integer> to) throws Exception{
        System.out.println("(" + from.get(1) + ", " + from.get(2) + "), (" + to.get(1) + ", " + to.get(2) + ")");
        click(327 + 95 * from.get(1), 812 - 95 * from.get(2));
        click(327 + 95 * to.get(1), 812 - 95 * to.get(2));
    }

    public static void click(int x, int y) throws Exception{
        Robot bot = new Robot();
        bot.mouseMove(x, y);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        TimeUnit.MILLISECONDS.sleep(100);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        TimeUnit.MILLISECONDS.sleep(100);
    }

    static int squareSize = 95;

    //returns {type, color}
    public static int[] getPieceType(int x, int y) throws Exception{
        Robot robot = new Robot();

        Color color = robot.getPixelColor(x + 49 * 95 / 82, y + 20 * 95 / 82);
        Color color1 = robot.getPixelColor(x + 33 * 95 / 82, y + 20 * 95 / 82);
        Color color2 = robot.getPixelColor(x + 28 * 95 / 82, y + 54 * 95 / 82);
        if(getTypeColor(color) == COLOR_BOARD && getTypeColor(color1) == COLOR_BOARD) {
            if(getTypeColor(color2) == COLOR_WHITE) {
                return new int[]{Board.PIECE_ROOK, COLOR_WHITE};
            }
            if(getTypeColor(color2) == COLOR_BLACK) {
                return new int[]{Board.PIECE_ROOK, COLOR_BLACK};
            }
        }
        //413, 137
        //450, 152
        color = robot.getPixelColor(x + 34, y + 53);
        color1 = robot.getPixelColor(x + 37, y + 25);
        if(getTypeColor(color) == COLOR_BOARD) {
            if(getTypeColor(color1) == COLOR_WHITE) {
                return new int[]{Board.PIECE_KNIGHT, COLOR_WHITE};
            }
            if(getTypeColor(color1) == COLOR_BLACK) {
                return new int[]{Board.PIECE_KNIGHT, COLOR_BLACK};
            }
        }

        color = robot.getPixelColor(x + 47, y + 43);
        color1 = robot.getPixelColor(x + 47, y + 16);
        if(getTypeColor(color) == COLOR_BOARD) {
            if(getTypeColor(color1) == COLOR_WHITE) {
                return new int[]{Board.PIECE_BISHOP, COLOR_WHITE};
            }
            if(getTypeColor(color1) == COLOR_BLACK) {
                return new int[]{Board.PIECE_BISHOP, COLOR_BLACK};
            }
        }


        color = robot.getPixelColor(x + 60, y + 15);
        color1 = robot.getPixelColor(x + 15, y + 31);

        if(getTypeColor(color1) == COLOR_WHITE && getTypeColor(color) == COLOR_WHITE) {
            return new int[]{Board.PIECE_QUEEN, COLOR_WHITE};
        }
        if(getTypeColor(color1) == COLOR_BLACK && getTypeColor(color) == COLOR_BLACK) {
            return new int[]{Board.PIECE_QUEEN, COLOR_BLACK};
        }

        //697, 136
        // 746, 148
        color = robot.getPixelColor(x + 58, y + 49);
        color1 = robot.getPixelColor(x + 49, y + 12);
        if(getTypeColor(color) == COLOR_BOARD) {
            if(getTypeColor(color1) == COLOR_WHITE) {
                return new int[]{Board.PIECE_KING, COLOR_WHITE};
            }
            if(getTypeColor(color1) == COLOR_BLACK) {
                return new int[]{Board.PIECE_KING, COLOR_BLACK};
            }
        }

        color = robot.getPixelColor(x + 52 * 95 / 82, y + 54 * 95 / 82);
        color1 = robot.getPixelColor(x + 35 * 95 / 82, y + 63 * 95 / 82);
        if(getTypeColor(color) == COLOR_BOARD) {
            if(getTypeColor(color1) == COLOR_WHITE) {
                return new int[]{Board.PIECE_PAWN, COLOR_WHITE};
            }
            if(getTypeColor(color1) == COLOR_BLACK) {
                return new int[]{Board.PIECE_PAWN, COLOR_BLACK};
            }
        }
        return new int[]{-1, -1};
    }

    final static int COLOR_BOARD = 0;
    final static int COLOR_WHITE = 1;
    final static int COLOR_BLACK = 2;
    public static int getTypeColor(Color color) {
        if(Math.abs(color.getRed() - color.getBlue()) < 10 && Math.abs(color.getRed() - color.getGreen()) < 10) {
            if(color.getRed() < 130) {
                return COLOR_BLACK;
            }
            else {
                return COLOR_WHITE;
            }
        }
        else {
            return COLOR_BOARD;
        }
    }
}