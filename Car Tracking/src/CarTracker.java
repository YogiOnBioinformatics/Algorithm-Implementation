import java.io.*;
import java.util.*; 

public class CarTracker{
	
	static BufferedReader reader = null; //to read in cars.txt 
	static IndexMinPQ<Car> mileagePQ = new IndexMinPQ<Car>(1000000); //will keep track of lowest mileage car and also used for color changes
	static IndexMinPQ<Car> pricePQ = new IndexMinPQ<Car>(1000000); //keeps track of lowest priced car
	static int index =0; //keeps track of number of indexes for adding into the PQ
	static HashMap<String, Integer> vinMap = new HashMap<String, Integer>(); //gives the index of the car in PQ when passed in the VIN 
	static HashMap<String, IndexMinPQ<Car>> makeModelMileageMap = new HashMap<String, IndexMinPQ<Car>>(); //maintains mileage IndexMinPQ for all make and model
	static HashMap<String, IndexMinPQ<Car>> makeModelPriceMap = new HashMap<String, IndexMinPQ<Car>>(); //maintains price IndexMinPQ for all make and model
	
	
	public static void main(String[] args){
		
		try{
			loadCarsAndFillHashMap(); //must use try catch since it throws IOException and FileNotFoundException
									  // this method will load cars into the PQ's from file as well as filling hashmaps for indirection table
		}
		catch(FileNotFoundException e){
			System.out.println("No file found");
		}
		catch(IOException e ){
			System.out.println("No file found");
		}
		
		do{
			//The terminal display
			System.out.println();
			System.out.println(); 
			System.out.println("WELCOME TO CAR TRACKER!");
			System.out.println("Here are your options! Please enter the number corresponding to the option you would like... "); 
			System.out.println("1. Add a car");
			System.out.println("2. Update a car");
			System.out.println("3. Remove a specific car");
			System.out.println("4. Retrieve lowest price car");
			System.out.println("5. Retrieve lowest mileage car");
			System.out.println("6. Retrieve lowest price car by make and model");
			System.out.println("7. Retrieve lowest mileage car by make and model");
			System.out.println(); 
			
			Scanner console = new Scanner(System.in);
			String input = console.nextLine().trim();
			
			if(input.trim().contains("1")){ //If the user wants to enter a car. This code is similar to loadCarsAndFillHashMap() method. 
				Car a = new Car(); 
				Car b= new Car();
				
				System.out.print("Please enter the VIN number of the car: "); 
				input = console.nextLine().trim(); 
				
				if(vinMap.containsKey(input)){ //want to make sure that someone does not add car already in hashmap
					System.out.println("You cannot add this VIN number since it already exists! Please re-choose the 'Add Car' option and enter a new VIN"); 
				}
				else{
					a.set_VIN(input); 
					b.set_VIN(input);
				
					System.out.print("Please enter the make of the car: "); 
					input = console.nextLine().trim();
					input = input.substring(0,1).toUpperCase() + input.substring(1, input.length()); //make sure that make is capitalized properly 
					a.set_make(input); 
					b.set_make(input);  
				
					System.out.print("Please enter the model of the car: "); 
					input = console.nextLine().trim(); 
					input = input.substring(0,1).toUpperCase() + input.substring(1, input.length());// make sure model is capitalized properly 
					a.set_model(input); 
					b.set_model(input); 
				
					System.out.print("Please enter the price of the car: "); 
					input = console.nextLine().trim(); 
					if(input.charAt(0)== '$'){ //in case the user decides to put the price with a dollar sign 
						input = input.substring(1, input.length()); 
					}
					
					a.set_price(Integer.parseInt(input)); 
					b.set_price(Integer.parseInt(input)); 
				
					System.out.print("Please enter the mileage of the car: "); 
					input = console.nextLine().trim(); 
					a.set_mileage(Integer.parseInt(input)); 
					b.set_mileage(Integer.parseInt(input)); 
				
					System.out.print("Please enter the color of the car: "); 
					input = console.nextLine().trim(); 
					a.set_color(input); 
					b.set_color(input); 
					
					a.set_comparingPrice(false); 
					b.set_comparingPrice(true); 
				
					mileagePQ.insert(index, a); //Insert the cars into respective PQ's
					pricePQ.insert(index, b); 
				
					vinMap.put(a.get_VIN(), index); //put our VIN and index integer into our VIN map data structure
			
					String makeAndModel = a.get_make() + a.get_model(); 
					
					if(!makeModelPriceMap.containsKey(makeAndModel) &&  !makeModelMileageMap.containsKey(makeAndModel)){ //if the make and model has never been encountered before
						IndexMinPQ<Car> makeModelMileagePQ = new IndexMinPQ<Car>(100000);
						IndexMinPQ<Car> makeModelPricePQ = new IndexMinPQ<Car>(1000000);
						
						makeModelMileagePQ.insert(index, a); 
						makeModelPricePQ.insert(index,b); 
						
						makeModelPriceMap.put(makeAndModel, makeModelPricePQ); //put the PQ's inside hashmap
						makeModelMileageMap.put(makeAndModel, makeModelMileagePQ);
						
					}
					else{
						IndexMinPQ<Car> tempMakeModelMileagePQ = makeModelMileageMap.get(makeAndModel); 
						IndexMinPQ<Car> tempMakeModelPricePQ = makeModelPriceMap.get(makeAndModel);
						
						tempMakeModelMileagePQ.insert(index, a); 
						tempMakeModelPricePQ.insert(index, b); 
						
						makeModelMileageMap.put(makeAndModel, tempMakeModelMileagePQ); 
						makeModelPriceMap.put(makeAndModel, tempMakeModelPricePQ); 
					
					}
					
					index++; //update counter variable
				}
				
				
				
			}
			else if(input.trim().contains("2")){ //User would like to update a current car
				System.out.println("Please enter the VIN number of the car to update! "); 
				input = console.nextLine().trim();
				String currVIN = input;
				
				while(!vinMap.containsKey(input)){	//code here checks to make sure VIN number entered is valid based on the vinMap HashMap
					System.out.println("Invalid VIN Key! Please try again! "); 
					input = console.nextLine().trim();
					currVIN = input;
				}
				
				System.out.println("Would you like to update the 1) Price, 2) Mileage, or 3) Color "); //asks which attribute to update 
				input = console.nextLine().trim();
				
				if(input.contains("1")){ //update price
					System.out.println("What would you like the new price to be?");
					input = console.nextLine().trim();
					if(input.charAt(0)== '$'){ //in case the user decides to put the price with a dollar sign 
						input = input.substring(1, input.length()); 
					}
					int sameIndex = vinMap.get(currVIN); 
					Car a = (Car) pricePQ.keyOf(sameIndex); 
					pricePQ.delete(sameIndex);
					a.set_price(Integer.parseInt(input)); 
					pricePQ.insert(sameIndex, a); 
					
					String makeAndModel = a.get_make() + a.get_model();
					
					IndexMinPQ<Car> tempMakeModelPricePQ = makeModelPriceMap.get(makeAndModel);
						 
					Car temp =  (Car) tempMakeModelPricePQ.keyOf(sameIndex);
					tempMakeModelPricePQ.delete(sameIndex);
					temp.set_price(Integer.parseInt(input));
					tempMakeModelPricePQ.insert(sameIndex,temp); 
					
					makeModelPriceMap.put(makeAndModel, tempMakeModelPricePQ); 
					
				}
				else if(input.contains("2")){ //update mileage
					System.out.println("What would you like the new mileage to be?");
					input = console.nextLine().trim();
					int sameIndex = vinMap.get(currVIN); 
					Car a = (Car) mileagePQ.keyOf(sameIndex); 
					mileagePQ.delete(sameIndex);
					a.set_mileage(Integer.parseInt(input)); 
					mileagePQ.insert(sameIndex, a); 
					
					String makeAndModel = a.get_make() + a.get_model();
					
					IndexMinPQ<Car> tempMakeModelMileagePQ = makeModelMileageMap.get(makeAndModel);
						 
					Car temp =  (Car) tempMakeModelMileagePQ.keyOf(sameIndex);
					tempMakeModelMileagePQ.delete(sameIndex);
					temp.set_mileage(Integer.parseInt(input));
					tempMakeModelMileagePQ.insert(sameIndex,temp); 
					
					makeModelMileageMap.put(makeAndModel, tempMakeModelMileagePQ); 
					
					
				}
				else if(input.contains("3")){ //update color
					System.out.println("What would you like the new color to be?");
					input = console.nextLine().trim();
					int sameIndex = vinMap.get(currVIN); 
					Car a = (Car) mileagePQ.keyOf(sameIndex); 
					mileagePQ.delete(sameIndex);
					a.set_color(input); 
					mileagePQ.insert(sameIndex, a); 
				}
				
				
			}
			else if(input.trim().contains("3")){ //user wants to remove car. the car will be removed from all indirection tables and PQ's
				System.out.println("Please enter the VIN number of the car to remove! "); 
				input = console.nextLine().trim();
				String currVIN = input;
				
				while(!vinMap.containsKey(input)){	//code here checks to make sure VIN number entered is valid based on the vinMap HashMap
					System.out.println("Invalid VIN Key! Please try again! "); 
					input = console.nextLine().trim();
					currVIN = input;
				}
				
				int tempIndex = vinMap.get(currVIN);
				Car temp = (Car) mileagePQ.keyOf(tempIndex); //retrieve car so we can find out more info about make and model
				 
				mileagePQ.delete(tempIndex); //deleting from the main PQ's
				pricePQ.delete(tempIndex); 
				
				String makeAndModel = temp.get_make() + temp.get_model();
				
				IndexMinPQ<Car> tempMakeModelMileagePQ = makeModelMileageMap.get(makeAndModel);
				IndexMinPQ<Car> tempMakeModelPricePQ = makeModelPriceMap.get(makeAndModel); 
				
				tempMakeModelMileagePQ.delete(tempIndex); //delete from hashmap value PQ's for specific make and model 
				tempMakeModelPricePQ.delete(tempIndex); 
				
				makeModelMileageMap.put(makeAndModel, tempMakeModelMileagePQ); //Put the updated PQ's back into the hashmap.
				makeModelPriceMap.put(makeAndModel, tempMakeModelPricePQ); 
				
				
			}
			else if(input.trim().contains("4")){ //user wants lowest priced car 
				System.out.println("The lowest price car is: "); 
				Car temp = (Car) pricePQ.minKey(); 
				System.out.println("VIN: "+ temp.get_VIN() + " " + "Price: "+ temp.get_price()); 
			}
			else if(input.trim().contains("5")){ //user wants lowest mileage car 
				System.out.println("The car with the lowest mileage is: "); 
				Car temp = (Car) mileagePQ.minKey(); 
				System.out.println("VIN: " + temp.get_VIN()+ " " + "Mileage: " + temp.get_mileage()); 
			}
			else if(input.trim().contains("6")){ //user wants lowest price car based on specific make and model 
				System.out.println("Please enter the make of the car: "); 
				String make = console.nextLine().trim(); 
				System.out.println("Please enter the model of the car: "); 
				String model = console.nextLine().trim(); 
				String makeAndModel= make + model;
				
				while(!makeModelPriceMap.containsKey(makeAndModel)){//makes sure that a passed in make and model actually exists 
					System.out.println("Either the make or the model of the car is invalid. Please RENTER the make!"); 
					make = console.nextLine().trim(); 
					System.out.println("Please RENTER the model of the car: "); 
					model = console.nextLine().trim(); 
					makeAndModel= make + model;
				}
				
				System.out.println("The lowest priced " + make + " " + model + " is: ");
				 
				
				IndexMinPQ<Car> tempMakeModelPricePQ = makeModelPriceMap.get(makeAndModel); 
				Car temp = tempMakeModelPricePQ.minKey(); 
				System.out.println("VIN: " + temp.get_VIN()+ " " + "Price: " + temp.get_price()); 
				
			}
			else if(input.trim().contains("7")){ //user wants lowest mileage car based on specific make and model 
			
				System.out.println("Please enter the make of the car: "); 
				String make = console.nextLine().trim(); 
				System.out.println("Please enter the model of the car: "); 
				String model = console.nextLine().trim(); 
				
				
				String makeAndModel= make + model; 
				
				while(!makeModelMileageMap.containsKey(makeAndModel)){//makes sure that a passed in make and model actually exists 
					System.out.println("Either the make or the model of the car is invalid. Please RENTER the make!"); 
					make = console.nextLine().trim(); 
					System.out.println("Please RENTER the model of the car: "); 
					model = console.nextLine().trim(); 
					makeAndModel= make + model;
				}
				
				System.out.println("The lowest mileage " + make + " " + model + " is: ");
				
				IndexMinPQ<Car> tempMakeModelMileagePQ = makeModelMileageMap.get(makeAndModel); 
				Car temp = tempMakeModelMileagePQ.minKey(); 
				System.out.println("VIN: " + temp.get_VIN()+ " " + "Mileage: " + temp.get_mileage()); 	
			}
			
			
			
		}while(true);
	}
	
	private static void loadCarsAndFillHashMap() throws FileNotFoundException, IOException { //fill up all hashmaps and PQ's that are global variables above from file
		
		reader = new BufferedReader(new FileReader("cars.txt"));
		
		while(reader.ready()){
			
			String line = reader.readLine(); 
			if(line.charAt(0) == '#'){ //check that the first line, which explains the formatting of file, is not included in program
				
			}
			else{
				
				String[] splitter = line.split(":"); //parse out each line for useful info
				
				if(vinMap.containsKey(splitter[0])){ //checks to see for duplicates in the given file
					System.out.println("A previous car in the list provided has the same VIN number! Please get rid of duplicates in cars.txt! EITHER WAY DUPLICATES HAVE BEEN DISCARDED IN THIS PROGRAM!"); 
				}
				else{
					Car a = new Car(); 
					Car b= new Car(); //two cars are used; one car will be given flag for comparePrice as true. Car a is !comparingPrice
				
					a.set_VIN(splitter[0]); 
					b.set_VIN(splitter[0]);
				
					a.set_make(splitter[1]); 
					b.set_make(splitter[1]); 
				
					a.set_model(splitter[2]); 
					b.set_model(splitter[2]); 
				
					a.set_price(Integer.parseInt(splitter[3])); //parseInt allows us to change from String to int
					b.set_price(Integer.parseInt(splitter[3]));
					
					a.set_mileage(Integer.parseInt(splitter[4]));
					b.set_mileage(Integer.parseInt(splitter[4]));
				
					a.set_color(splitter[5]); 
					b.set_color(splitter[5]); 
				
					a.set_comparingPrice(false); 
					b.set_comparingPrice(true); 
		
					mileagePQ.insert(index, a); //Insert the cars into respective PQ's
					pricePQ.insert(index, b); 
					
					vinMap.put(a.get_VIN(), index); //put our VIN and index integer into our VIN map data structure
			
					String makeAndModel = a.get_make() + a.get_model(); 
					
					if(!makeModelPriceMap.containsKey(makeAndModel) &&  !makeModelMileageMap.containsKey(makeAndModel)){ //if the make and model has never been encountered before
						IndexMinPQ<Car> makeModelMileagePQ = new IndexMinPQ<Car>(100000);
						IndexMinPQ<Car> makeModelPricePQ = new IndexMinPQ<Car>(1000000);
						
						makeModelMileagePQ.insert(index, a); 
						makeModelPricePQ.insert(index,b); 
						
						makeModelPriceMap.put(makeAndModel, makeModelPricePQ); //put the PQ's inside hashmap
						makeModelMileageMap.put(makeAndModel, makeModelMileagePQ);
						
					}
					else{
						IndexMinPQ<Car> tempMakeModelMileagePQ = makeModelMileageMap.get(makeAndModel); 
						IndexMinPQ<Car> tempMakeModelPricePQ = makeModelPriceMap.get(makeAndModel);
						
						tempMakeModelMileagePQ.insert(index, a); 
						tempMakeModelPricePQ.insert(index, b); 
						
						makeModelMileageMap.put(makeAndModel, tempMakeModelMileagePQ); 
						makeModelPriceMap.put(makeAndModel, tempMakeModelPricePQ); 
					
					}
					
					
					index++; //update counter variable
				}
				

			}
			
		}
		
	}
	
	
	
	
}