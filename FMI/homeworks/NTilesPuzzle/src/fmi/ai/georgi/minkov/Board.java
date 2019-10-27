package fmi.ai.georgi.minkov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fmi.ai.georgi.minkov.movement.Direction;

public class Board {
	private Direction previousDirection;
	private int[] currentState;
	private int[] goalState;
	
	private int size; // this is size of the column and row
	private int indexOfZero;
	private int indexOfTargetZero;
	
	public Board(int[] initialState, int indexOfTargetZero) throws Exception {
		previousDirection = null; // when we initialise board there are no movement done
		this.currentState = Arrays.copyOf(initialState, initialState.length);
		this.size = (int)Math.sqrt(initialState.length);
		this.indexOfZero = findIndexOfNumberInCurrentState(0);
		this.indexOfTargetZero = indexOfTargetZero == -1 ? size * size - 1 : indexOfTargetZero;
		generateGoalTarget();
	}
	
	public List<Direction> getAllAvailableDirection() {
		List<Direction> legalDirection = new ArrayList<>();
		int xOfZero = indexOfZero / size;
		int yOfZero = indexOfZero % size;
		
		if (xOfZero > 0) {
			legalDirection.add(Direction.UP);
		}
		
		if (xOfZero < size - 1) {
			legalDirection.add(Direction.DOWN);
		}
		
		if (yOfZero > 0) {
			legalDirection.add(Direction.LEFT);
		}
		
		if (yOfZero < size - 1) {
			legalDirection.add(Direction.RIGHT);
		}
		
		return legalDirection;
		
		/*
		 * if (indexOfZero % size == size - 1)
		 * if (indexOfZero % size == 0)
		 * if (indexOfZero < size) 
		 * if (indexOfZero >= (size - 1) * size  in this scenarios do nothing
		 */
	}
	
	/**
	 * makeMove will always make a move with the empty type (a.k.a Zero)
	 * @param direction
	 */
	public void makeMove(Direction direction) {
		switch (direction) {
		case LEFT: {
			swapOnIndex(indexOfZero, indexOfZero - 1); 
			indexOfZero -= 1;
			break;
		}
		case RIGHT: {
			swapOnIndex(indexOfZero, indexOfZero + 1);
			indexOfZero += 1;
			break;
		}
		case UP: {
			swapOnIndex(indexOfZero, indexOfZero - size);
			indexOfZero -= size;
			break;
		}
		case DOWN: {
			swapOnIndex(indexOfZero, indexOfZero + size);
			indexOfZero += size;
			break;
		}
		default: break;
		}
	}
	
	public Direction getReverseOf(Direction direction) {
		
		switch(direction) {
		case LEFT: return Direction.RIGHT;
		case RIGHT: return Direction.LEFT;
		case UP: return Direction.DOWN;
		case DOWN: return Direction.UP;
		default: return null;
		}
	}
	
	public void undoMove(Direction direction) {
		switch (direction) {
		case LEFT: makeMove(Direction.RIGHT); break;
		case RIGHT: makeMove(Direction.LEFT); break;
		case UP: makeMove(Direction.DOWN); break;
		case DOWN: makeMove(Direction.UP); break;
		default: break;
		}
	}
	
	public boolean isSolved() {
		return Arrays.equals(currentState, goalState);
	}
	
	public Direction getPrevious() {
		return previousDirection;
	}
	
	public void setPrevious(Direction direction) {
		this.previousDirection = direction;
	}
	
	public int getHeuristicCostEstimate() throws Exception {
		int cost = 0;
		for (int index = 0; index < currentState.length; ++index) {
			if (currentState[index] == 0) {
				continue;
			}
			
			int xOfCurrentState = index / size;
			int yOfCurrentState = index % size;
			
			int goalIndex = findIndexOfNumberInGoalState(currentState[index]);
			
			int xOfGoalState = goalIndex / size;
			int yOfGoalState = goalIndex % size;
			
			cost += Math.abs(xOfGoalState - xOfCurrentState) + Math.abs(yOfGoalState - yOfCurrentState);
		}
		
		return cost;
	} 
	
	private void generateGoalTarget() {
		goalState = new int[size * size];
		
		for (int index = 0; index < indexOfTargetZero; ++index) {
			goalState[index] = index + 1;
		}

		goalState[indexOfTargetZero] = 0;

		for (int index = indexOfTargetZero + 1; index < size * size; ++index) {
			goalState[index] = index;
		}
	}
	
	private void swapOnIndex(int lhs, int rhs) {
		int tmp = currentState[lhs];
		currentState[lhs] = currentState[rhs];
		currentState[rhs] = tmp;
	}
	
	private int findIndexOfNumberInCurrentState(int number) throws Exception {
		for (int index = 0; index < size * size; ++index) {
			if (currentState[index] == number) {
				return index;
			}
		}
		throw new Exception("No " + number +" found");
	}
	
	private int findIndexOfNumberInGoalState(int number) throws Exception {
		for (int index = 0; index < size * size; ++index) {
			if (goalState[index] == number) {
				return index;
			}
		}
		throw new Exception("No " + number +" found");
	}
}
