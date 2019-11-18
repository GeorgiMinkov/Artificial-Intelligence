package fmi.ai.georgi.minkov;

import java.util.Random;

public class City {
	
	private static int id = -1;
	private int name;
	private int x;
	private int y;
	private Random random = new Random();
	
	public City() {
		City.id++;
		this.name = id;
		this.x = random.nextInt(300);
		this.y = random.nextInt(300);
	}
	
	public City(int x, int y) {
		City.id++;
		this.name = id;
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public double distanceTo(City city) {
		int pitagorianX = Math.abs(x - city.getX());
		int pitagorianY = Math.abs(y - city.getY());
		
		return Math.sqrt(pitagorianX * pitagorianX + pitagorianY * pitagorianY);
	}
	
	@Override
		public String toString() {
			return "[" + name + "](" + x + ", " + y + ")";
		}
}
