import java.lang.String; 


public class Car implements Comparable<Car> { //Needs to be comparable so that PQ knows how to keep priority
	
	private String VIN; 
	private String make; 
	private String model;
	private int price;
	private int mileage;
	private String color;
	private boolean comparingPrice; //this flag is used so that we know how to compare two items 
	
	
	public Car(){
		
	}
	
	public boolean set_VIN(String vin){
		VIN = vin; 
		return true;
	}
	
	public String get_VIN(){
		
		return VIN;
	}
	
	public boolean set_make(String Make){
		make = Make;
		return true;
	}
	
	public String get_make(){
		return make;
	}
	
	public boolean set_model(String Model){
		model = Model; 
		return true;
	}
	
	public String get_model(){
		return model; 
	}
	
	public boolean set_price(int Price){
		price = Price;
		return true;
	}
	
	public int get_price(){
		return price;
	}
	
	public boolean set_mileage(int Mileage){
		mileage = Mileage;
		return true;
	}
	
	public int get_mileage(){
		return mileage; 
	}
	
	public boolean set_color(String Color){
		color = Color;
		return true;
	}
	
	public String get_color(){
		return color;
	}
	
	public void set_comparingPrice(boolean b) {
		comparingPrice = b; 
	}
	
	public boolean get_comparingPrice(){
		return comparingPrice; 
	}
	
	public int compareTo(Car other){ //this method checks the flag of comparingPrice before it returns any value.
		if(comparingPrice){
			if(this.price>other.price){
				return 1;
			}
			else if(this.price==other.price){
				return 0; 
			}
			else if(this.price < other.price){
				return -1; 
			}
			else {
				return 0; 
			}
		}
		else {
			if(this.mileage>other.mileage){
				return 1;
			}
			else if(this.mileage==other.mileage){
				return 0; 
			}
			else if(this.mileage < other.mileage){
				return -1; 
			}
			else {
				return 0; 
			}
		}
	}
	
	
	
	
}