package fmi.ai.georgi.minkov.kmeans;
/* 
 * KMeans.java ; Cluster.java ; Point.java
 *
 * Solution implemented by DataOnFocus
 * www.dataonfocus.com
 * 2015
 *
*/

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import graph.SimpleGraph;

public class KMeans {

	//Number of Clusters. This metric should be related to the number of points
    private int NUMBER_OF_CLUSTERS = 10;    
    //Number of Points
//    private int NUM_POINTS = 15;
    //Min and Max X and Y
    private static double MIN_COORDINATE = 0;
    private static double MAX_COORDINATE = 10;
    
    private SimpleGraph graph = new SimpleGraph();
    
    private List<Point> points;
    private List<Cluster> clusters;
    
    public KMeans() {
    	this.points = new ArrayList<>();
    	this.clusters = new ArrayList<>();    	
    }
    
    public static void main(String[] args) {
    	
    	
    }
    
    public void setPoints(List<Point> points) {
    	this.points = points;
    }
    
    //Initializes the process
    public void initCentroid() {
    	MIN_COORDINATE = minValue();
    	MAX_COORDINATE = maxValue();
    	
    	//Create Clusters
    	//Set Random Centroids
    	for (int i = 0; i < NUMBER_OF_CLUSTERS; i++) {
    		Cluster cluster = new Cluster(i);
    		Point centroid = Point.createRandomPoint(MIN_COORDINATE, MAX_COORDINATE);
    		cluster.setCentroid(centroid);
    		clusters.add(cluster);
    	}
    	
    	//Print Initial state
    	plotClusters();
    }

	private void plotClusters() {
    	for (int i = 0; i < NUMBER_OF_CLUSTERS; i++) {
    		Cluster c = clusters.get(i);
    		c.plotCluster();
    	}
    }
	
	private double minValue() {
		double min = Double.MAX_VALUE;
		for (Point point : points) {
			if (min > point.getX()) {
				min = point.getX();
			}
			
			if (min > point.getY()) {
				min = point.getY();
			}
		}
		
		return min;
	}
	
	private double maxValue() {
		double max = Double.MIN_VALUE;
		for (Point point : points) {
			if (max < point.getX()) {
				max = point.getX();
			}
			
			if (max < point.getY()) {
				max = point.getY();
			}
		}
		
		return max;
	}
    
	//The process to calculate the K Means, with iterating method.
    public void calculate() throws InterruptedException {
        boolean finish = false;
        int iteration = 0;
        
        // Add in new data, one at a time, recalculating centroids with each new one. 
        while(!finish) {
        	//Clear cluster state
        	clearClusters();
        	
        	List<Point> lastCentroids = getCentroids();
        	
        	//Assign points to the closer cluster
        	assignCluster();
            
            //Calculate new centroids.
        	calculateCentroids();
        	
        	iteration++;
        	
        	List<Point> currentCentroids = getCentroids();
        	
        	//Calculates total distance between new and old Centroids
        	double distance = 0;
        	for(int i = 0; i < lastCentroids.size(); i++) {
        		distance += Point.distance(lastCentroids.get(i),currentCentroids.get(i));
        	}
        	System.out.println("#################");
        	System.out.println("Iteration: " + iteration);
        	System.out.println("Centroid distances: " + distance);
        	plotClusters();
        	        	
        	if(distance == 0) {
        		finish = true;
        	}
        	
//        	visualize(iteration);
        }
    }
    
    private void visualize(int iteration) throws InterruptedException {
    	
    	if (iteration == 1) {
    		graph.display();
        		
    	}
    	graph.clearPoints();
    	Thread.sleep(450);
    	for (Cluster cluster: clusters) {
    		Random rand = new Random();
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			
			Color randomColor = new Color(r, g, b);
			graph.addPoint(cluster.getCentroid().getX(), cluster.getCentroid().getY(), new Color(r / 256, g, b));
    		graph.repaint();
    		Thread.sleep(500);
    		
    		for (Point point: cluster.getPoints()) {
    			graph.addPoint(point.getX(), point.getY(), randomColor);
    		}
    		
    		graph.repaint();
    		Thread.sleep(450);
    		
    	}
    }
    
    private void clearClusters() {
    	for(Cluster cluster : clusters) {
    		cluster.clear();
    	}
    }
    
    private List<Point> getCentroids() {
    	List<Point> centroids = new ArrayList<>(NUMBER_OF_CLUSTERS);
    	for(Cluster cluster : clusters) {
    		Point aux = cluster.getCentroid();
    		Point point = new Point(aux.getX(), aux.getY());
    		centroids.add(point);
    	}
    	return centroids;
    }
    
    private void assignCluster() {
        int clusterIndex = 0;                 
        double distance = 0.0; 
        
        for(Point point : points) {
        	double min = Double.MAX_VALUE;
            for(int index = 0; index < NUMBER_OF_CLUSTERS; ++index) {
            	Cluster cluster = clusters.get(index);
                distance = Point.distance(point, cluster.getCentroid());
                if(distance < min){
                    min = distance;
                    clusterIndex = index;
                }
            }
            point.setCluster(clusterIndex);
            clusters.get(clusterIndex).addPoint(point);
        }
    }
    
    private void calculateCentroids() {
        for(Cluster cluster : clusters) {
            double sumX = 0;
            double sumY = 0;
            List<Point> list = cluster.getPoints();
            int n_points = list.size();
            
            for(Point point : list) {
            	sumX += point.getX();
                sumY += point.getY();
            }
            
            Point centroid = cluster.getCentroid();
            if(n_points > 0) {
            	double newX = sumX / n_points;
            	double newY = sumY / n_points;
                centroid.setX(newX);
                centroid.setY(newY);
            }
        }
    }
}