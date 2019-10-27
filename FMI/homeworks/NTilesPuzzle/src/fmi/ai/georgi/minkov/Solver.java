package fmi.ai.georgi.minkov;

import java.util.Stack;

import fmi.ai.georgi.minkov.movement.Direction;

public class Solver {
	private Board board;
	private int exploredNodes;
	private int movesToTarget;
	private Stack<String> pathOfZero;
	private Stack<Direction> gamePath;
	
	public Solver(int[] board, int indexOfTargetZero) throws Exception {
		this.board = new Board(board, indexOfTargetZero);
		pathOfZero = new Stack<>();
		gamePath = new Stack<>();
		exploredNodes = 0;
		movesToTarget = 0;
	}
	
	public void solve() {
		int bound = 0;
		try {
			bound = board.getHeuristicCostEstimate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		boolean found = false;
		
		while(!found) {
			int result = search(board, 0, bound);
			
			if (result == -1) {
				found = true;
			}
			bound = result;
		}
	}
	
	public Stack<String> getPathOfZero() {
		return pathOfZero;
	}
	
	public Stack<Direction> getGamePath() {
		return gamePath;
	}
	
	public int getExploredNodes() {
		return exploredNodes;
	}
	
	private int search(Board board, int g, int bound) {
		int f = -1;
		try {
			f = g + board.getHeuristicCostEstimate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
		if (f > bound) {
			return f;
		}
		
		if (board.isSolved()) {
			return -1;
		}
		
		int min = (int) Double.POSITIVE_INFINITY;
		Direction previousMove = board.getPrevious();
		for (Direction moveTo : board.getAllAvailableDirection()) {
			if (board.getPrevious() != null && moveTo == board.getReverseOf(board.getPrevious())) {
				continue;
			}
			
			board.makeMove(moveTo);
			exploredNodes++;
			board.setPrevious(moveTo);
			
			pathOfZero.add(moveTo.getDirection());
			gamePath.add(moveTo.reverse());
			
			int tmp = search(board, g + 1, bound);
			if (tmp == -1) {
				return -1;
			} else if (tmp < min) {
				min = tmp;
			}
			
			pathOfZero.pop();
			gamePath.pop();
			board.undoMove(moveTo);
			board.setPrevious(previousMove);
		}
		
		return min;
	}
}
