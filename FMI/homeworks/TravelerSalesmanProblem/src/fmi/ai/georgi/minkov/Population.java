package fmi.ai.georgi.minkov;

public class Population {

	Chromosome[] population;
	
	public Population(int populationSize, boolean initialise) {
		population = new Chromosome[populationSize];
		if (initialise) {
			for (int index = 0; index < populationSize; ++index) {
				Chromosome tour = new Chromosome();
				tour.generateIndividual();
				saveChromosome(index, tour);
			}
		}
	}
	
	public void saveChromosome(int index, Chromosome chromosome) {
		this.population[index] = chromosome;
	}
	
	public Chromosome getChromosome(int index) {
		return this.population[index];
	}
	
	public Chromosome getFittest() {
		Chromosome fittest = this.population[0];
		for (Chromosome tour: population) {
			if (tour.getFitness() >= fittest.getFitness()) {
				fittest = tour;
			}
		}
		
		return fittest;
	}
	
	public int getPopulationSize() {
		return population.length;
	}
}
