package fmi.ai.georgi.minkov;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fmi.ai.georgi.minkov.kmeans.KMeans;
import fmi.ai.georgi.minkov.kmeans.Point;

public class Test {
	
	public static void main(String[] args) throws InterruptedException {
		String fileName = "unbalance.txt";
//		String fileName = "normal.txt";
		
		List<Point> points = new ArrayList<>();
		
		File file = new File(fileName);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
			
			while (sc.hasNextDouble()) {
				double x = sc.nextDouble();
				double y = sc.nextDouble();
				Point point = new Point(x, y);
				points.add(point);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			sc.close();
		}
		
		KMeans algo = new KMeans();
		algo.setPoints(points);
		algo.initCentroid();
		algo.calculate();
	}

}
