package fmi.ai.georgi.minkov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Chromosome {

	private static List<City> originalChromosome;
	private List<City> tourChromosome;
	
	private double fitness;
	private int distance;
	
	public static void setOriginalChromosome(List<City> src) {
		Chromosome.originalChromosome = src.stream().collect(Collectors.toList());
	}
	
	public Chromosome() {
		tourChromosome = new ArrayList<>();
		for (int index = 0; index < originalChromosome.size(); ++index) {
			tourChromosome.add(null);
		}
	}
	
	public void generateIndividual() {
		for (int index = 0; index < originalChromosome.size(); ++index) {
			setChromosomeGene(index, originalChromosome.get(index));
		}
		
		Collections.shuffle(tourChromosome);
	}
	
	public void setChromosomeGene(int index, City city) {
		this.tourChromosome.set(index, city);
		this.fitness = 0;
		this.distance = 0;
	}
	
	public City getCityGene(int index) {
		return this.tourChromosome.get(index);
	}
	
	public int getDistance() {
		if (distance != 0) {
			return distance;
		}
		
		for (int index = 0; index < tourChromosome.size(); ++index) {
			City from = getCityGene(index);
			City to = (index + 1 == tourChromosome.size()) ? getCityGene(0) : getCityGene(index + 1);
			distance += from.distanceTo(to);
		}
		
		return distance;
	}
	
	public double getFitness() {
		fitness = fitness == 0 ? 1.0 / getDistance() : fitness;
		return fitness;
	}
	
	public int getChromosomeSize() {
		return tourChromosome.size();
	}
	
	public boolean containsGene(City gene) {
		return tourChromosome.contains(gene);
	}
	
	@Override
	public String toString() {
		return Arrays.toString(tourChromosome.toArray());
	}
}
