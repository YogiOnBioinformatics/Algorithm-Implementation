import java.util.ArrayList;
import java.util.HashMap;

public class Vertex{ //keeps all information about necessaary vertices 

    private int root; //number of the vertex for this object 
    private ArrayList<Vertex> neighbors = new ArrayList<Vertex>(); //all vertices that it is connected to 
    private Hashmap<Integer, String> cables; //type of cable used
    private int bandwith; //bandwith in megabits per second
    private int length; //length of the cable 

    public Vertex(){ //empty constructor 
    
    }

    public void setRoot(int x){
        root = x; 
    }

    public int getRoot(){
        return root; 
    }

    public void setNeighbor(Vertex j){
        neighbors.add(j); 
    }

    public ArrayList<Vertex> getNeighbors(){
        return neighbors;
    }

    public void setCableType(String type){
        cable = type;
    }

    public String getCableType(){
        return cable;
    }

    public void setBandwith(int x){
        bandwith = x; 
    }

    public int getBandWith(){
        return bandwith; 
    }

    public void setLength(int l){
        length = l; 
    }

    public int getLength(){
        return length; 
    }


}