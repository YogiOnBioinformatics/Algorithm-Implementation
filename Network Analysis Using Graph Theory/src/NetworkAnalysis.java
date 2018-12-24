import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;




public class NetworkAnalysis{
	   
	static boolean firstTime = true; //flag for whether we are in the first line of a file 
	static BufferedReader reader; //reader for reading in file 
	static Graph graph; //graph that keeps adjacency list and all important info from file
	static Scanner sc; //scanner for input 
	
	public static void main(String[] args) throws FileNotFoundException, IOException{ //need try catch because filereaders could throw possible exceptions
		
		try{
			loadGraph(args[0]); 
		}
		catch(FileNotFoundException e){
			throw new FileNotFoundException("The file that you have specified does not exist!"); 
		}
		catch(IOException e){
			throw new IOException("The file may have issues! Please use a different file!"); 
		}

		do{
			// this code is related to creating the menu user interface
			System.out.println("Hello there! Welcome to your favorite Network Analysis tool! Please choose from the following options: "); 
			System.out.println("1. Find the lowest latency path between any two points"); 
			System.out.println("2. Is the graph Copper-only connected?"); 
			System.out.println("3. Find maximum amount of data that can be transmitted from one vertex to another");
			System.out.println("4. Find minimum average latency spanning tree for the graph");
			System.out.println("5. Determine if graph would remain connected even if any two vertices fail"); 
			System.out.println("6. Quit the program"); 

			sc = new Scanner(System.in); 
			String line = sc.next(); 
			
			if(line.contains("1")){ //find lowest latency path with Dijkstra algorithm 
				System.out.println("What would you like the starting vertex to be?"); 
				int startingVertex = sc.nextInt(); 
				System.out.println("What would you like the ending vertex to be?"); 
				int endingVertex = sc.nextInt(); 

				graph.lowestLatencyPath(startingVertex, endingVertex); 
			}
			else if(line.contains("2")){// is the graph copper only connected?
				if(graph.copperConnected()){
					System.out.println("The graph IS copper-connected"); 
				}
				else{
					System.out.println("The graph is NOT copper-connected"); 
				}
			}
			else if(line.contains("3")){ //check for the maximum amount of data that can be transmitted 
				System.out.println("What would you like the starting vertex to be?"); 
				int startingVertex = sc.nextInt(); 
				System.out.println("What would you like the ending vertex to be?"); 
				int endingVertex = sc.nextInt(); 
				
				reader = new BufferedReader(new FileReader(args[0])); //reset the reader to load in vertices to FlowNetwork 

				FlowNetwork fnet = new FlowNetwork(Integer.parseInt(reader.readLine())); //make FlowNetwork which we add FlowEdges to. 

				while(reader.ready()){
					String[] splitter = reader.readLine().split(" ");
					FlowEdge e = new FlowEdge(Integer.parseInt(splitter[0]), Integer.parseInt(splitter[1]), Integer.parseInt(splitter[3])); 
					FlowEdge e2 = new FlowEdge(Integer.parseInt(splitter[1]), Integer.parseInt(splitter[0]), Integer.parseInt(splitter[3])); 
					fnet.addEdge(e);
					fnet.addEdge(e2);
				}

				FordFulkerson ff = new FordFulkerson(fnet, startingVertex, endingVertex); 
				System.out.println(ff.value()); 


			}
			else if(line.contains("4")){ //use Prim MST
				graph.primMST(); 
			}
			else if(line.contains("5"))
			{
				boolean found = false;
				
				for(int i = 0; i < graph.V(); i++)
				{
					for(int j=i+1;j<graph.V();j++)
					{
						Graph tester = new Graph(graph.V());
						
						 
						for(Edge e : graph.edges())
						{
							if(e.either() == i || e.either() == j) continue;
							if(e.other(e.either()) == i || e.other(e.either()) == j) continue;
							tester.addEdge(e);
						}
						
						CC connected  = new CC(tester);
						if(connected.count() > 1)
						{
							found = true;
							break;
						}
					}
					if(found)
					{
						break;
					}					
				}
				if(found)
				{
					System.out.println("The graph wouldn't be connected if any two vertices failed");
				}
				else
				{
					System.out.println("\nThe graph would stay connected if any two vertices failed\n");
				}
			}
			else if(line.contains("6")){
				System.out.println("Thanks for using NetworkAnalysis!"); 
				System.exit(0); 
			}
			


			System.out.println(); 
		}while(true);
		
	}

	public static void loadGraph(String arg) throws FileNotFoundException, IOException{
			 
			reader = new BufferedReader(new FileReader(arg));
		
			
			while(reader.ready()){// makes graph and adds edges 
			
				if(firstTime){ //get number of vertices total in file
					
					firstTime = false; //reset firstTime
					graph = new Graph(Integer.parseInt(reader.readLine().trim())); //initialize size of graph to be number of vertices 
					 
				}
				
				
				String[] splitter = reader.readLine().split(" "); // split the different components based on the space between them 

				if(Integer.parseInt(splitter[0])< 0){// starting vertex is impossible 
					System.out.println("One of the vertices you've listed is negative. Please fix the file and re-run the program!"); 
					System.exit(0); 
				}

				
				if(Integer.parseInt(splitter[1])< 0){// ending vertex is impossible 
					System.out.println("One of the vertices you've listed is negative. Please fix the file and re-run the program!"); 
					System.exit(0); 
				}

				if(Integer.parseInt(splitter[3])< 0){// bandwith is impossible 
					System.out.println("One of the lengths you've listed is negative. Please fix the file and re-run the program!"); 
					System.exit(0); 
				}

				
				if(Integer.parseInt(splitter[4])< 0){// cable length is impossible 
					System.out.println("One of the lengths you've listed is negative. Please fix the file and re-run the program!"); 
					System.exit(0); 
				}

				Edge e = new Edge(Integer.parseInt(splitter[0]), Integer.parseInt(splitter[1]), splitter[2], Integer.parseInt(splitter[3]), Integer.parseInt(splitter[4])); //make the edge in one direction 
				Edge f = new Edge(Integer.parseInt(splitter[1]), Integer.parseInt(splitter[0]), splitter[2], Integer.parseInt(splitter[3]), Integer.parseInt(splitter[4])); //make edge in other direction

				graph.addEdge(e); //add edges 
				graph.addEdge(f); //add edges
				
				
				
				
			}

	}

	
	
}