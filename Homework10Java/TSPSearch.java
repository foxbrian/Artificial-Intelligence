import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class TSPSearch{
	
	private static Random rng = new Random();

	public static void main(String[] args){
		Scanner tsp= new Scanner(System.in);
		ArrayList<String[]> cityList = new ArrayList<>();

		try{
			File tspFile = new File(args[0]);
			tsp = new Scanner(tspFile);
		}catch(IndexOutOfBoundsException e){
			e.printStackTrace();
			System.out.println("you did not enter a tsp file");
			System.exit(0);
		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.out.print("That file doesn't seem to exist");
			System.exit(0);
		}
		
		while(!tsp.nextLine().equals("NODE_COORD_SECTION") ){}
		while(tsp.hasNextLine()){
			String line = tsp.nextLine();
			if(line=="EOF"){
				break;
			}
			String[] temp = line.split(" ");
			cityList.add(temp);
		}
		
		ArrayList<String[]> tour = getRandomStart(cityList);
		
		//System.out.println(getTourDistance(start));
		
		int attempts = 0;
		int distance = getTourDistance(tour);
		while(attempts < 500 ){
			attempts++;
			ArrayList<String[]> neighbor = getNeighbor(tour);
			int thisDistance = getTourDistance(neighbor);
			if(thisDistance < distance){
				distance=thisDistance;
			      	tour=neighbor;
				attempts=0;
			} 

		}

		System.out.println(distance);
		for(String[] city : tour){
			System.out.print(city[0] + " ");
		}
		System.out.println("");

	}
	

	public static int euclideanDistance(float x1,float y1,float x2,float y2){
		return (int)Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	public static int getTourDistance(ArrayList<String[]> cities){
		String[] previous = cities.get(cities.size()-1);
		int sum =0;
		for(String[] city : cities){
			sum += euclideanDistance(Float.parseFloat(city[1]),Float.parseFloat(city[2]),Float.parseFloat(previous[1]),Float.parseFloat(previous[2]));
			previous = city;
		}

		return sum;
	}

	public static ArrayList<String[]> getRandomStart(ArrayList<String[]> cities){
		ArrayList<String[]> random = new ArrayList<>(cities);
		for(int i = 0;i<random.size()-2;i++){
			int swap = rng.nextInt(random.size()-i-1)+i;
			String[] temp = random.get(i);
			random.set(i,random.get(swap));
			random.set(swap,temp);
		}

		return random;

	}

	public static ArrayList<String[]> getNeighbor(ArrayList<String[]> cities){
		int first = rng.nextInt(cities.size());
		int second = rng.nextInt(cities.size());
		while(first == second){
			second = rng.nextInt(cities.size());
		}
		
		ArrayList<String[]> neighbor = new ArrayList<>(cities);

		String[] temp = neighbor.get(first);
		neighbor.set(first,neighbor.get(second));
		neighbor.set(second,temp);

		return neighbor;
	}
}
