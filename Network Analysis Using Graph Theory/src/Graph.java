import java.util.ArrayList;

public class Graph{

    public int V; //number of vertices in graph  
    public int E; //number of edges in graph 
    public ArrayList<Edge>[] adjList;  //adjacency list representation of graph
    public boolean[] visited;
    public int tracker; 
    double[] distTo; 
    Edge[] edgeTo;
    IndexMinPQ pq; 
    int counter; 

    public Graph(int numVertices){ //constructor 
        V = numVertices; 
        E =0; 
        adjList = new ArrayList[V]; 
        visited = new boolean[V]; 
        tracker =0; 
        distTo = new double[V]; 
        edgeTo = new Edge[V]; 
        pq = new IndexMinPQ<Double>(V); 
        for(int i =0; i<V; i++){
            adjList[i] = new ArrayList<Edge>(); 
        }

    }

    public int V(){ //return number of vertices
        return V; 
    }

    public int E(){ //return number of edges 
        return E; 
    }

    public Iterable<Edge> adj(int v){
        return adjList[v]; 
    }

    public void addEdge(Edge edge){
        int v = edge.from(); 
        int w = edge.to(); 

        adjList[v].add(edge); 
        adjList[w].add(edge); //this is probably the error  
        E++; 
    }

    public void lowestLatencyPath(int start, int end){
        if(start ==end){
            System.out.println("Starting and ending vertex are the same"); 
            return; 
        }
        distTo = new double[V];
		edgeTo = new Edge[V];
		pq = new IndexMinPQ<Double>(V);
        visited = new boolean[V];
        
        for(int i =0; i<V; i++){
            distTo[i] = Double.POSITIVE_INFINITY; 
        }

        distTo[start] = 0.0; 
        pq.insert(start, distTo[start]); 

        for(int i=0; i<V; i++){//trying all combinations 
            if(!visited[i]){
                while(!pq.isEmpty()){
                    int f = pq.delMin(); 
                    visited[f] = true; 
                    for(int x = 0; x<adjList[f].size(); x++){
                        Edge e = adjList[f].get(x); 
                        int w = e.otherVertex; 
                        if(visited[w]){
                            continue; 
                        }
                        if(e.time+distTo[f]<distTo[w]){
                            distTo[w] = e.time + distTo[f]; 
                            edgeTo[w] = e; 
                            if(pq.contains(w)){
                                pq.decreaseKey(w, distTo[w]);
                            }
                            else{
                                pq.insert(w, distTo[w]); 
                            }
                        }
                    }
                }
            }
        }

        int min = 99999999; 
        boolean gotIt = false; 
        int currentVertex = end; 
        String fullPath = end+ ""; 
        int looper = 0; 
        boolean done = false; 
        while(!gotIt){
            if(looper>V){
                System.out.println("There is not a path!"); 
                done = true; 
                break; 
            }
            if(edgeTo[currentVertex]!=null){
                fullPath = edgeTo[currentVertex].thisVertex+ " ---> "+fullPath; 
                
            }
            if(edgeTo[currentVertex]!=null && edgeTo[currentVertex].thisVertex == start){
                gotIt = true; 
            }
            if(edgeTo[currentVertex]!=null &&edgeTo[currentVertex].bandwith<min){
                min = edgeTo[currentVertex].bandwith; 
            }
            if(edgeTo[currentVertex]!=null){
                currentVertex = edgeTo[currentVertex].thisVertex; 
            }
            looper++; 
        }
        if(!done){
            System.out.println("Path: " + fullPath); 
            System.out.println("Minimum Bandwith of all Edges: "+ min + " megabits per second"); 
        }

    }

    public void primMST()
	{
		distTo = new double[V];
		edgeTo = new Edge[V];
		pq = new IndexMinPQ<Double>(V);
		visited = new boolean[V];
		
		for (int v = 0; v < V; v++)
            distTo[v] = Double.POSITIVE_INFINITY;
		pq.insert(0,0.0);
		for(int i = 0; i < V; i++)
		{
			if(!visited[i]){
				prim(i);			
			}			
		}
		
		System.out.println("The edges that make up this spanning tree are (order is not given): "); 
		for(int i = 0; i < V; i++)
		{
			if(edgeTo[i] != null)
			{
                System.out.println(); 
                System.out.print(edgeTo[i].thisVertex + " ");
                System.out.print(edgeTo[i].otherVertex+ " "); 
                System.out.print(edgeTo[i].cableType+ " ");
                System.out.print(edgeTo[i].bandwith+ " ");
                System.out.print(edgeTo[i].cableLength+ " ");
                
			}
		}
		
	}
	
	private void prim(int i)
	{
		while(!pq.isEmpty())
		{
			int r = pq.delMin();
			scanPrim(r);
		}
	}
	
	private void scanPrim(int j)
	{
		visited[j] = true;
		
		for(int x = 0; x < adjList[j].size(); x++)
		{
	
			Edge e = adjList[j].get(x);
			int w = adjList[j].get(x).otherVertex;
			
			if(visited[w]){
				continue;
			}
	
			if(e.time < distTo[w])
			{				
				distTo[w] = e.time;
				edgeTo[w] = e;
				if(pq.contains(w)) pq.decreaseKey(w,distTo[w]);
				else pq.insert(w, distTo[w]);
			}
		}
	}

    public boolean copperConnected(){
		visited = new boolean[V];
		int counter =0;
		counter = dfs(0); 
		
		
		if(counter == V)
		{
			counter = 0;
			return true;
		}
		counter = 0;
		return false;
	}
	public int dfs(int x){
		counter++; 
		visited[x] = true;  
		for(int i = 0; i < adjList[x].size(); i++)
		{
			if(!visited[adjList[x].get(i).otherVertex] && adjList[x].get(i).cableType.equals("copper"))
			{
				dfs(adjList[x].get(i).otherVertex);
			}
        }
        
        return counter;
    }

    public Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<Edge>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }
                // add only one copy of each self loop (self loops will be consecutive)
                else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }

}