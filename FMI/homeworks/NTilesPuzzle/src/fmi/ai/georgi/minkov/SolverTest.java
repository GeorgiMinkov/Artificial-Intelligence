package fmi.ai.georgi.minkov;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import fmi.ai.georgi.minkov.movement.Direction;

/**
 * Resources used: https://algorithmsinsight.wordpress.com/graph-theory-2/ida-star-algorithm-in-general/
 * @author georgi minkov
 *
 */

public class SolverTest {
	
	public static void executeFromFile(String fileName) {
		int size = -1;
		int indexOfZero = -1;
		List<Integer> integers = null;
		
		File file = new File(fileName);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
			
			size = sc.nextInt() + 1; // not used inside, only for valid input
			indexOfZero = sc.nextInt();
			integers = new ArrayList<>();
			
			while(sc.hasNextInt()) {
				integers.add(sc.nextInt());
			}

			if (size != integers.size()) {
				throw new Exception("Check input values");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			sc.close();
		}
		
		Solver solver = null;
		try {
			int[] initialState = new int[integers.size()];
			for (int index = 0; index < integers.size(); ++ index) {
				initialState[index] = integers.get(index);
			}
			
			solver = new Solver(initialState, indexOfZero);
			solver.solve();
			Stack<String> pathOfZero = solver.getPathOfZero();
			System.out.println(pathOfZero.size());
			
			System.out.println("Moves of the zero:");
			pathOfZero.stream().forEach(System.out::println);


			System.out.println("Moves of the game:");
			Stack<Direction> gamePath = solver.getGamePath();
			gamePath.stream().forEach(System.out::println);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
//		int[] currentState = {1, 3, 2, 5, 0, 7, 8, 4, 6};
//		Solver solver;
//		try {
//			solver = new Solver(currentState, -1);
//			solver.solve();
//			Stack<String> path = solver.getPath();
//			System.out.println(path.size());
//			
//			path.stream().forEach(System.out::println);
//			
//			System.out.println();
//			System.out.println(path);
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		executeFromFile("test.txt");
		
		
	}

}
