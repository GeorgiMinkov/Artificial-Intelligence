package fmi.ai.georgi.minkov;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
	private static final char QUEEN = '*';
	private static final char EMPTY = '-';
	
	private int[] table;
	private int[] rowNumberOfQueens;
	private int[] mainDiagonalNumberOfQueens;
	private int[] secondaryDiagonalNumberOfQueens;
	
	private int size;
	private int diagonalSize;
	
	private int steps;
	
	private Random random;
	
	Board(int size) {
		random = new Random();
		this.size = size;
		this.diagonalSize = 2 * size - 1;
		
		table = new int[size];
		initBoardRandom();

		rowNumberOfQueens = new int[size];
		initRowNumberOfQueens();
		
		mainDiagonalNumberOfQueens = new int[diagonalSize];
		initMainDiagonalNumberOfQueens();
		
		secondaryDiagonalNumberOfQueens = new int [diagonalSize];
		initSecondaryDiagonalNumberOfQueens();
		
	}
	
	private void initBoardDiagonal() {
		for (int row = 0; row < size; ++row) {
			table[row] = row;
		}
	}
	
	private void initBoardRandom() {
		for (int row = 0; row < size; ++row) {
			table[row] = row;
		}
		
		for (int index = 0; index < size; ++index) {
			int rand = random.nextInt(size);
			int swap = table[rand];
			table[rand] = table[index];
			table[index] = swap;
		}
	}
	
	private void initRowNumberOfQueens() {
		for (int index = 0; index < size; ++index) {
			int count = 0;
			for (int col = 0; col < size; ++col) {
				if (index == table[col]) {
					++count;
				}
			}
			rowNumberOfQueens[index] = count;
		}
	}
	
	private void initMainDiagonalNumberOfQueens() {
		for (int diagonalIndex = 0; diagonalIndex < diagonalSize; ++diagonalIndex) {
			int count = 0;
			for (int x = 0; x < size; ++x) {
				if (diagonalIndex == size - 1 + (x - table[x])) {
					++count;
				}
			}
			
			mainDiagonalNumberOfQueens[diagonalIndex] = count;
		}
	}
	
	private void initSecondaryDiagonalNumberOfQueens() {
		for (int diagonalIndex = 0; diagonalIndex < diagonalSize; ++diagonalIndex) {
			int count = 0;
			for (int x = 0; x < size; ++x) {
				if (diagonalIndex == x + table[x]) {
					++count;
				}
			}
			
			secondaryDiagonalNumberOfQueens[diagonalIndex] = count;
		}
	}
	
	private int calculateConflicts(int col) {
		int row = rowNumberOfQueens[table[col]], mainDiagonal = mainDiagonalNumberOfQueens[size - 1 + (col - table[col])],
				secondaryDiagonal = secondaryDiagonalNumberOfQueens[col + table[col]];
		int conflicts = 0;
		if (row > 1) {
			conflicts += row - 1;
		}
		
		if (mainDiagonal > 1) {
			conflicts += mainDiagonal - 1;
		}
		
		if (secondaryDiagonal > 1) {
			conflicts += secondaryDiagonal - 1;
		}
		
		return conflicts;
	}
	
	public boolean solve() {
		int steps = 0;
		
		List<Integer> candidatesForMoving = new ArrayList<>();
		
		while (true) {
			int maxConflicts = calculateMaxConflictNCandidates(candidatesForMoving);
			
			if (maxConflicts == 0) {
				this.steps = steps;
				return true;
			}
			
			int worstQueenColumnIndex = candidatesForMoving.get(random.nextInt(candidatesForMoving.size()));
			
			// move to the place with min conflicts
			// we don't need worst candidates anymore, so will use it for the best ones
			candidatesForMoving.clear();
			fillCandidatesForMinConflicts(worstQueenColumnIndex, candidatesForMoving);
			
			// move worst to best
			if (!candidatesForMoving.isEmpty()) {
				--rowNumberOfQueens[table[worstQueenColumnIndex]];
				--mainDiagonalNumberOfQueens[size - 1 + (worstQueenColumnIndex - table[worstQueenColumnIndex])];
				--secondaryDiagonalNumberOfQueens[worstQueenColumnIndex + table[worstQueenColumnIndex]];
				
				int rowToMove = candidatesForMoving.get(random.nextInt(candidatesForMoving.size()));
				table[worstQueenColumnIndex] = rowToMove;

				++rowNumberOfQueens[rowToMove];
				++mainDiagonalNumberOfQueens[size - 1 + (worstQueenColumnIndex - rowToMove)];
				++secondaryDiagonalNumberOfQueens[worstQueenColumnIndex + rowToMove];
				
			}

			++steps;
			if (steps == size * 3) {
				// restart
				initBoardRandom();

				initMainDiagonalNumberOfQueens();
				initRowNumberOfQueens();
				initSecondaryDiagonalNumberOfQueens();
				
				steps = 0;
				solve();
			}
		}
		
	}
	
	private int calculateMaxConflictNCandidates(List<Integer> candidatesForMoving) {
		int maxConflicts = 0;
		candidatesForMoving.clear();
		
		// find conflicts
		for (int col = 0; col < size; ++col) {
			int conflicts = calculateConflicts(col);
			
			if (maxConflicts < conflicts) {
				candidatesForMoving.clear();
				
				maxConflicts = conflicts;
				candidatesForMoving.add(col); // indexes of moves
			} else if (maxConflicts == conflicts) {
				candidatesForMoving.add(col);
			}
		}
		
		return maxConflicts;
	}
	
	private void fillCandidatesForMinConflicts(int col, List<Integer> candidates) {
		int minConflicts = size;
		
		for (int row = 0; row < size; ++row) {
			if (row == table[col]) {
				continue;
			}
			
			int candidateConflict = rowNumberOfQueens[row]
					+ mainDiagonalNumberOfQueens[size - 1 + (col - row)]
					+ secondaryDiagonalNumberOfQueens[col + row];
					
			if (candidateConflict < minConflicts) {
				minConflicts = candidateConflict;
				
				candidates.clear();
				candidates.add(row);
			} else if (candidateConflict == minConflicts) {
				candidates.add(row);
			}
		}
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < size; ++row) {
			for (int col = 0; col < size; ++col) {
				sb.append(' ');
				if (table[col] == row) {
					sb.append(QUEEN);
				} else {
					sb.append(EMPTY);
				}
			}
			sb.append('\n');
		}

		sb.append("\n" + steps);
		return sb.toString();
	}
	
	public void print(PrintStream stream) {
		for (int row = 0; row < size; ++row) {
			for (int col = 0; col < size; ++col) {
				if (table[col] == row) {
					stream.append(QUEEN);
				} else {
					stream.append(EMPTY);
				}
			}
			stream.append('\n');
		}
	}
	
	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		Board board = new Board(10_000);

		System.out.println(board.solve());
		
		System.out.println((System.currentTimeMillis() - time) / 1000.0);
		
//		 System.out.println(board);
	}
	
}
