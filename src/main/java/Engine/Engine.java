package Engine;

import Game.Board;

import java.util.ArrayList;

public class Engine {

    Board originalBoard;

    public Engine(Board board) {
        this.originalBoard = board;
    }

    public int getMoveValue(ArrayList<Integer> move, Board board) {
        board = new Board(board.whitePieces, board.blackPieces);
    }

}
