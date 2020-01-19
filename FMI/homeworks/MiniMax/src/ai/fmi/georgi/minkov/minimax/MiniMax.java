package ai.fmi.georgi.minkov.minimax;

import ai.fmi.georgi.minkov.util.Board;
import ai.fmi.georgi.minkov.util.Board.State;

public class MiniMax implements Algorithm{

	private static double maxDepth;
	
	public MiniMax(double maxDepth) {
		MiniMax.maxDepth = maxDepth;
	}
	
	@Override
	public void run(State player, Board board) {
		// TODO Auto-generated method stub
		miniMax(player, board, 0);
	}
	
	private int miniMax(State player, Board board, int currentDepth) {
		if (currentDepth++ == maxDepth || board.isGameOver()) {
			return score(player, board);
		}
		
		if (board.getTurn() == player) {
			return getMax(player, board, currentDepth);
		} else {
			return getMin(player, board, currentDepth);
		}
	}

	private int getMin(State player, Board board, int currentDepth) {
		double bestScore = Double.POSITIVE_INFINITY;
        int indexOfBestMove = -1;

        for (Integer theMove : board.getAvailableMoves()) {

            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(theMove);

            int score = miniMax(player, modifiedBoard, currentDepth);

            if (score <= bestScore) {
                bestScore = score;
                indexOfBestMove = theMove;
            }

        }

        board.move(indexOfBestMove);
        return (int)bestScore;
	}

	private int getMax(State player, Board board, int currentDepth) {
		double bestScore = Double.NEGATIVE_INFINITY;
        int indexOfBestMove = -1;

        for (Integer theMove : board.getAvailableMoves()) {

            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(theMove);

            int score = miniMax(player, modifiedBoard, currentDepth);

            if (score >= bestScore) {
                bestScore = score;
                indexOfBestMove = theMove;
            }

        }

        board.move(indexOfBestMove);
        return (int)bestScore;
	}

	@Override
	public int score(State player, Board board) {
		if (player == State.BLANK) {
            throw new IllegalArgumentException("Player must be X or O.");
        }

        State opponent = (player == State.X) ? State.O : State.X;

        if (board.isGameOver() && board.getWinner() == player) {
            return WINNER_VALUE;
        } else if (board.isGameOver() && board.getWinner() == opponent) {
            return (-1)* WINNER_VALUE;
        } else {
            return DRAW_VALUE;
        }
	}
	
}
