package fmi.ai.georgi.minkov;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Run {
	public static void main(String[] args) {
		Scanner scanner = new Scanner( System. in);
		int size = scanner.nextInt(); 
		scanner.close();
		
		List<City> initChromosome = new ArrayList<>(size);
		for (int index = 0; index < size; ++index) {
			City city = new City();
			initChromosome.add(city);
			System.out.println(city.getX() + ", " + city.getY());
		}
		
		Chromosome.setOriginalChromosome(initChromosome);
		Population population = new Population(size, true);
		
		GeneticAlgorithm ga = new GeneticAlgorithm(population, 1_000, GeneticAlgorithm.SelectionType.TOURNAMENT);
		ga.runAlgorithm();
	}
}
