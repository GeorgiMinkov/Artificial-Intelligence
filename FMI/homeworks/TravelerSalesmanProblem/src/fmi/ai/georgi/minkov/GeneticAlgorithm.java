package fmi.ai.georgi.minkov;

import java.util.Random;

public class GeneticAlgorithm {
	private static final double mutationRate = 0.025;
	private static final int tournamentSize = 5; // TODO : change it to bigger
	private static final boolean elitism = true;
	
	private SelectionType selector;
	
	private Random random;
	private Population population;
	private int numberOfIterations;
	
	public GeneticAlgorithm() {
		this.random = new Random();
		this.population = null;
		this.numberOfIterations = 0;
	}
	
	public GeneticAlgorithm(Population population, int numberOfIterations, SelectionType selector) {
		this.random = new Random();
		this.population = population;
		this.numberOfIterations = numberOfIterations;
		this.selector = selector;
	}
	
	public enum SelectionType {
		TOURNAMENT; // get n element n < population size . From that n elements get the fittest
	}
	
	public void runAlgorithm() {
		int step = 5;
		
		for (int iteration = 0; iteration < numberOfIterations; ++iteration) {
			if ((iteration + 1) % 10 == 0 && (step--) != 1) {
				System.out.println("Iteration " + (iteration + 1) + " -> distance -> " + population.getFittest().getDistance());
			}
			
			evolution();
		}
		
		System.out.println("Final Iteration -> distance -> " + population.getFittest().getDistance());
	}
	
	private void evolution() {
		Population evolvedPopulation = new Population(population.getPopulationSize(), false);
		
		int elitismOffset = elitism ? 1 : 0;
		if (elitism) {
			evolvedPopulation.saveChromosome(0, population.getFittest());
		}
		
		for (int index = elitismOffset; index < population.getPopulationSize(); ++index) {
			Chromosome lhs = selection(selector, population);
			Chromosome rhs = selection(selector, population);
			
			Chromosome result = crossover(lhs, rhs);
			evolvedPopulation.saveChromosome(index, result);
		}
		
		for (int index = elitismOffset; index < population.getPopulationSize(); ++index) {
			mutateChromosome(evolvedPopulation.getChromosome(index));
		}
		
		population = evolvedPopulation;
	}
	
	private Chromosome crossover(Chromosome lhs, Chromosome rhs) {
		Chromosome child = new Chromosome();
		
		int size = child.getChromosomeSize();
		int startPosition = random.nextInt(size);
		int endPosition = random.nextInt(size);
		
		if (startPosition > endPosition) {
			int tmp = startPosition;
			startPosition = endPosition;
			endPosition = tmp;
		}
		
		for (int index = startPosition; index <= endPosition; ++index) {
			child.setChromosomeGene(index, lhs.getCityGene(index));
		}
		
		for (int rhsIndex = 0; rhsIndex < size; ++rhsIndex) {
			if (!child.containsGene(rhs.getCityGene(rhsIndex))) {
				for (int childIndex = 0; childIndex < size; ++childIndex) {
					if (child.getCityGene(childIndex) == null) {
						child.setChromosomeGene(childIndex, rhs.getCityGene(rhsIndex));
						break;
					}
				}
			}
		}
 		
		return child;
	}
	
	private void mutateChromosome(Chromosome tour) {
		for (int positionToSwap = 0; positionToSwap < tour.getChromosomeSize(); ++positionToSwap) {
			if (random.nextDouble() < mutationRate) {
				int swapIndex = random.nextInt(tour.getChromosomeSize());
				
				City tmp = tour.getCityGene(positionToSwap);
				tour.setChromosomeGene(positionToSwap, tour.getCityGene(swapIndex));
				tour.setChromosomeGene(swapIndex, tmp);
			}
		}
	}
	
	private Chromosome selection(SelectionType type, Population population) {
		if (SelectionType.TOURNAMENT.equals(type)) {
			return tournametSelection(population);
		}
		
		return null;
	}
	
	private Chromosome tournametSelection(Population population) {
		// Create new population based on current. This population is only used for mmaking of mixed population and get the fittest (a.k.a the winner)
		Population tournament = new Population(tournamentSize, false);
		for (int index = 0; index < tournamentSize; ++index) {
			tournament.saveChromosome(index, population.getChromosome(random.nextInt(population.getPopulationSize())));
		}
		
		Chromosome fittestFromTournament = tournament.getFittest();
		return fittestFromTournament;
	}
}
