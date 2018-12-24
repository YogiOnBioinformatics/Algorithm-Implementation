

public class Edge implements Comparable<Edge> {

    public int thisVertex; //this vertex 
    public int otherVertex; //The other vertex 
    public int cableLength; //length of the cable 
    public int bandwith; //amount of bandwith available 
    public double time; //time it takes
    public double flow; // amount of flow that can be sent 
    public String cableType; //Copper or optical fiber

    public Edge(){
        thisVertex =0; 
        otherVertex = 0; 
        cableType = ""; 
        bandwith =0; 
        cableLength = 0; 
        time = cableLength/20; 
        flow = 0.0; 
    }

    public Edge(int v, int w, String type, int bwidth, int length){
        thisVertex = v; 
        otherVertex =w; 
        cableType = type; 
        bandwith = bwidth; 
        cableLength = length; 
        flow = 0.0; 
        if(cableType.equals("copper")){
            time = cableLength/230000000.0; 
        }
        else{
            time = cableLength/200000000.0; 
        }
    }

    public int from(){
        return thisVertex; 
    }

    public int to(){
        return otherVertex; 
    }
    public int either() {
        return thisVertex;
    }

    public int weight(){
        return cableLength; 
    }

    public int other(int vertex){
        if(vertex ==thisVertex){
            return otherVertex; 
        }
        else{
            return thisVertex; 
        }
    }

    public int compareTo (Edge other){
        return this.bandwith - other.bandwith; 
    }

    



}