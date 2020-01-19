package ai.fmi.georgi.minkov.minimax;

import ai.fmi.georgi.minkov.util.Board;
import ai.fmi.georgi.minkov.util.Board.State;

public interface Algorithm {
	public static final int WINNER_VALUE = 10;
	public static final int DRAW_VALUE = 0;
	
	void run (State player, Board board);
	
	default int getMax() {
		return -1;
	}
	
	default int getMin() {
		return -1;
	}
	
	int score(State player, Board board);
}
