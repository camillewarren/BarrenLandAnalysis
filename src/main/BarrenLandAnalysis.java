package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/** 
 * The BarrenLandAnalysis program implements an application that determines the area of 
 * the portions of land that is fertile on a 400 x 600 plot given rectangles of barren land.
 * <p>
 * Area is measured in meters squared.
 * @author Camille Warren
 *
 */
public class BarrenLandAnalysis {
	final static int XMAX = 400;
	final static int YMAX = 600;
	
	List<Integer[]> barrenLandRectangles;
	static int land[][]; //entire land
	static List<Integer> fertileLand;
	
	static Queue<Integer[]> queue;
	static boolean[][] visited;
	
	static int fertileLandGroupNumber;
	
	/**
	 * Constructor.
	 */
	public BarrenLandAnalysis()
	{
		barrenLandRectangles= new ArrayList<Integer[]>();
		land = new int[XMAX][YMAX];
		fertileLand = new ArrayList<Integer>();
		queue = new LinkedList<Integer[]>();
		visited = new boolean[XMAX][YMAX];
		fertileLandGroupNumber = 0;
	}
	
	/**
	 * Gets the areas of fertile land
	 * 
	 * @return <code>String</code><P>The area of each portion of fertile land in ascending order
	 * @throws IOException
	 */
	public String getFertileLandAreas() throws IOException {
		String str = readInputs();
        boolean isCleanInput = cleanInput(str);
        if(isCleanInput) {
	        markBarrenLand();
	        getFertileLand();
	        sortFertileLand();
	        return writeOutput();
        }
        
        System.out.println("Invalid input");
        return "Invalid input";

	}
	
	/**
	 * Reads the input from the standard input stream
	 * 
	 * @return <code>String</code><P>The characters from the standard input stream.
	 * @throws IOException
	 */
	String readInputs() throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String str = bufferedReader.readLine();
		return str;
	}
	
	/**
	 * sets this BarrenLandAnlysis' list of barren land coordinates.
	 * 
	 * @param input <code>String</code><P>The set of rectangles that contains the barren land.
	 * @return <code>true</code> if <code>input<code> is formatted like {"x<sub>(0)(1)</sub> y<sub>(0)(1)</sub> x<sub>(0)(2)</sub> y<sub>(0)(2)</sub>", "x<sub>(1)(1)</sub> y<sub>(1)(1)</sub> x<sub>(1)(2)</sub> y<sub>(1)(2)</sub>", ...},
	 * where x<sub>(rectangleNumber)(coordinatePairNumber)</sub> and y<sub>(rectangleNumber)(coordinatePairNumber)</sub> are integers; otherwise, <code>false</code>.
	 */
	boolean cleanInput(String input){
		
		//if there is no barren land, return true
		if(input.split("}")[0].trim().equals("{")) {
			return true;
		}
		
		String[] allBarrenRectanglesStr = input.split(",");
		for(String barrenRectangleStr:allBarrenRectanglesStr) {
			//get rid of brackets
			barrenRectangleStr = barrenRectangleStr.replaceAll("\\{|\\}", "");
			//get rid of quote marks
			barrenRectangleStr = barrenRectangleStr.replace("\"", "");
			barrenRectangleStr = barrenRectangleStr.replaceAll("\"|\"", "");
			//get rid of leading spaces " "
			barrenRectangleStr = barrenRectangleStr.replaceAll("^ ", "");
			
			
			String[] barrenRectangle = barrenRectangleStr.split(" ");
			if(isValidRectangle(barrenRectangle)) {
				Integer[] barrenRectangleInt = {Integer.parseInt(barrenRectangle[0]),
											Integer.parseInt(barrenRectangle[1]),
											Integer.parseInt(barrenRectangle[2]),
											Integer.parseInt(barrenRectangle[3])};
					
				barrenLandRectangles.add(barrenRectangleInt);
			} else {
				return false;
			}
		}
		
		return true;
			
	}
	
	/**
	 * Validates barren land rectangle.
	 * 
	 * @param rectangle <code>String[]</code><P>An array of strings containing numbers, representing a single barren land rectangle.
	 * @return <code>true</code> if <code>rectangle</code> is of length 4 and the value at each index can be parsed as an integer,
	 *  the values at even indices are between 0 and 399 and do not equal each other, and the values at odd indices
	 *  are between 0 and 599 and do not equal each other; otherwise, <code>false</code>
	 */
	boolean isValidRectangle(String[] rectangle) {
		//must have 4 valid coordinates
		if(rectangle.length == 4) {
			int i = 0;
			while(i < rectangle.length ) {
				//coordinate must be an integer
				if(canParseInt(rectangle[i])) {
					//if index is even, then referring to x coordinate
					if(i % 2 == 0) {
						//x coordinate must be between 0 and 399
						if(Integer.parseInt(rectangle[i]) < 0 || Integer.parseInt(rectangle[i]) >= XMAX) {
							return false;
						}
					}
					else { //else referring to y coordinate
						//y coordinate must be between 0 and 599
						if(Integer.parseInt(rectangle[i]) < 0 || Integer.parseInt(rectangle[i]) >= YMAX) {
							return false;
						}
					}
				}
				else {
					return false;
				}
				i++;
			}
		} else {
			return false;
		}

		return true;
	}
	/**
	 * Determines if a string can be converted to an Integer. 
	 * 
	 * @param str <code>String</code><P>A string containing a number to parse.
	 * @return <code>true</code> if <code>str</code> contains a number that
	 * can be parsed to its Integer equivalent; otherwise, <code>false</code>
	 */
	boolean canParseInt(String str){
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Flags the indices of this BarrenLandAnylysis' land that are barren.
	 */
	void markBarrenLand(){
		for(Integer[] barrenLandRectangle:barrenLandRectangles){
			for(int i = barrenLandRectangle[0]; i <= barrenLandRectangle[2]; i++) {
				for(int j = barrenLandRectangle[1]; j <= barrenLandRectangle[3]; j++) {
					land[i][j] = 1;
				}
			}
		}
	}
	
	/**
	 *   Determines all areas of fertile land and sets this BarrenLand Analysis's fertile land. 
	 */
	void getFertileLand() {
		for(int j = 0; j < YMAX; j++) {
			for(int i = 0; i < XMAX; i++) {
				//if a node is fertile and hasn't been visited, 
				//you have a new group of connected nodes in the disconnected graph 
				//aka a new portion of fertile land 
				if(land[i][j] == 0 && !visited[i][j]) {
					//traverse this new portion of fertile land and find its area
					BFS(i,j);
					fertileLandGroupNumber++; /*increment group number so that the next time BFS is called, 
												its referencing a new portion of fertile land */
				}
			}
		}
	}
	
	/**
	 * Determines an area of fertile land and sets an index in this BarrenLandAnalysis;
	 * fertileLand. 
	 * 
	 * @param ni <code>Integer</code><P> The x coordinate of a new area of fertile land.
	 * @param nj <code>Integer</code><P> The y coordinate of a new area of fertile land.
	 */
	static void BFS(int ni, int nj)
	{
		Integer[] node = {ni,nj};
		visited[ni][nj] = true;
		fertileLand.add(1); //create a new index (portion of fertile land) and count that node towards its area
		queue.add(node); //add a node to queue so that you can check its child nodes (the nodes surrounding it)
		
		//used to get neighboring cells
		int neighbor_i[] = { -1, 0, 0, 1 };//left, bottom, top, right   
		int neighbor_j[] = { 0, -1, 1, 0 };
		
		while(!queue.isEmpty()) {
			//"pop" the current node
			int i = queue.peek()[0];
			int j = queue.peek()[1];
			queue.remove();
			
			//(don't need to check the current node because it is only added to the
			//queue if it has already been checked)
			
			
			//look at current node's neighbors
			for (int k = 0; k < 4; k++) {
				//if neighbor is fertile (and hasn't been visited)
				if(isFertile(i + neighbor_i[k], j + neighbor_j[k])) {
					//mark the neighbor as visited
					visited[i + neighbor_i[k]][j + neighbor_j[k]] = true;
					//count the neighbor node toward the current fertile land portion's area 
					fertileLand.set(fertileLandGroupNumber, fertileLand.get(fertileLandGroupNumber) + 1);
					//add current node to queue so that its neighbors can be checked 
					Integer[] neighborNode = {i + neighbor_i[k], j + neighbor_j[k]};
					//add node to queue so that its children can be checked
					queue.add(neighborNode);
				}
			}
			
		}
	}
	
	/**
	 * Determines if a coordinate is in fertile land
	 * 
	 * @param i <code>Integer</code> The x coordinate of the point being checked.
	 * @param j <code>Integer</code> The y coordinate of the point being checked.
	 * @return <code>true</code> if the coordinate is with in range, fertile, and not visited; otherwise, <code>false</code>
	 */
	static boolean isFertile(int i, int j) {
		return i >= 0 && i < XMAX &&
				j >= 0 && j < YMAX &&
				land[i][j] == 0 && !visited[i][j];
	}
	
	/**
	 * Sorts this BarrenLandAnalysis's fertileLand attribute in ascending order
	 */
	public void sortFertileLand() {
		Collections.sort(fertileLand);
	}
	
	/**
	 * writes this BarrenLandAnalysis's fertileLand as string out to the standard output stream
	 * @return <code>String</code> the areas of the portions of land that are fertile
	 */
	String writeOutput() {
		String fertileLandAreas = fertileLand.isEmpty() ? "0" : fertileLand.toString().replaceAll("\\[|\\]", "").replace(",", ""); 
		System.out.println(fertileLandAreas);
		return fertileLandAreas;
		
	}
	
	
	public static void main(String[] args) throws IOException {
        BarrenLandAnalysis b = new BarrenLandAnalysis();
        b.getFertileLandAreas();
        

    }
	
}
