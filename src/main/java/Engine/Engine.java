package Engine;

import Game.Board;
import Util.Util;

import java.util.ArrayList;

public class Engine {

    Board originalBoard;

    public Engine(Board board) {
        this.originalBoard = board;
    }

    public boolean turn;
    public int startingTurns = -1;

    public ArrayList<ArrayList<ArrayList<Integer>>> evaluateMoves(Board board, int turnsLeft) {
        if(startingTurns == -1) {
            startingTurns = turnsLeft;
        }

        if(turnsLeft == 0) {
            ArrayList<ArrayList<ArrayList<Integer>>> out = new ArrayList<ArrayList<ArrayList<Integer>>>();
            out.add(new ArrayList<ArrayList<Integer>>());
            out.get(0).add(new ArrayList<Integer>());
            out.get(0).get(0).add(board.evaluatePosition(turn) - board.evaluatePosition(!turn));
            return out;
        }
        else {
            ArrayList<ArrayList<ArrayList<Integer>>> moves = board.getPossibleMoves();

            int bestMove = 0;
            ArrayList<ArrayList<ArrayList<Integer>>> maxScoreAndMoves = null;
            for(int i = 0; i < moves.size(); i++) {
                Board copy = new Board(board.whitePieces, board.blackPieces, turn);
                if(copy.movePieces(moves.get(i).get(1), moves.get(i).get(2))) {
                    ArrayList<ArrayList<ArrayList<Integer>>> scoreAndMoves = evaluateMoves(copy, turnsLeft - 1);
                    if(maxScoreAndMoves == null) {
                        maxScoreAndMoves = scoreAndMoves;
                        bestMove = i;
                    }
                    if((startingTurns - turnsLeft) % 2 == 0) {
                        if (scoreAndMoves.get(0).get(0).get(0) > maxScoreAndMoves.get(0).get(0).get(0)) {
                            maxScoreAndMoves = scoreAndMoves;
                            bestMove = i;
                        }
                    }
                    else {
                        if (scoreAndMoves.get(0).get(0).get(0) < maxScoreAndMoves.get(0).get(0).get(0)) {
                            maxScoreAndMoves = scoreAndMoves;
                            bestMove = i;
                        }
                    }
                }
            }

            maxScoreAndMoves.add(1, moves.get(bestMove));
            return maxScoreAndMoves;
        }

    }

}
