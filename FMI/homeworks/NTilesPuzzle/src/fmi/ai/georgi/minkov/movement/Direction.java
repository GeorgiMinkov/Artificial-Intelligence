package fmi.ai.georgi.minkov.movement;

public enum Direction {
	LEFT("LEFT"),
	RIGHT("RIGHT"),
	UP("UP"),
	DOWN("DOWN");
	
	private String direction;
	
	private Direction(String direction) {
		this.direction = direction;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public Direction reverse() {
		switch (this) {
		case LEFT:
			return RIGHT; 
		case RIGHT:
			return LEFT; 
		case UP:
			return DOWN; 
		case DOWN:
			return UP; 
		default: return null;
		}
	}
}
