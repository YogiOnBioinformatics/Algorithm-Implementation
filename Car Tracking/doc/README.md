# Car Tracking App in Terminal 

**Main Classes**

Car.java
	This class contains self-explanatory set and get methods, mainly, that will be used by the CarTracker application. 

CarTracker.java 
	The main class that has a user interface and does the tracking. 

IndexMinPQ.java 
	Minimal Priority Queue that is used for sorting cars. 

StdOut.java 
	Standard Java class that allows output from java program to terminal screen. 


**Explanation of Creation**

	I first began by designing a Car data structure class called "Car.java" which has basic setters and getters. The notable part of this class is that 
I use a flag called comparingPrice. This flag is set when cars are initialized and before they are put into IndexMinPQ's in the CarTracker.java. The reason is 
because I use two separate cars for each Car that is entered into the program. One of the cars has the flag comparingPrice set to true and the other one to false. 
This way the compareTo() method in Car.java knows which parameter to compare to return the relevant answer. 

	In CarTracker.java I used a mileagePQ and a pricePQ which I gave a max size of 100000 just in case the input file was very big. These PQ's will keep the 
minimum priority of mileage and price, respectively. I used an integer variable, index, which kept track of the index that I would give a new car that I added. 
To allow for quick accesses of cars I kept a HashMap that mapped VIN Strings to their integer indexes so that I could retrieve the car from the PQ with that index, 
quickly. Then I kept two HashMap's related to Make and Model combinations of cars. They mapped the String of the Make and Model together, without spaces, to an 
IndexMinPQ, either comparing price or mileage, which is just for a specific model and make. 

	Here I will prove that retrieving the car with minimum mileage/price is logarithmic or better. I will also prove that all updates and removals are 
logarithmic or better. Before we can do any of the above operations, we must first enter cars into our data structure. This was done with one simple call
to insert(int index) in IndexMinPQ.java. According to the documentation of IndexMinPQ.java, this operation is logarithmic. Now that we have the cars in the data
structure, we can retrieve the car with either minimum mileage or price by calling the minKey() method on the correct IndexMinPQ<Car>. Once again according to 
the documentation in IndexMinPQ.java, the minKey() operation is logarithmic. So this means retrievals for either attribute are logarithmic. For retrieval of 
an attribute on a specific make and model, we first ask the user for the make and model. Then we take the make and model (key) and retrieve the 
IndexMinPQ<Car> (value). This is an O(1) operation. Once we have this IndexMinPQ<Car> that is specific to our make and model, we can call minKey(), which is 
constant. Two constant operations [O(1) + O(1)] is still constant, O(1). To update a car, the user is asked what the VIN number is. Assuming a valid VIN is passed
in, we can find the index in our indirection table that corresponds to that object. On average, this is an O(1) constant operation with a HashMap. Once we have 
the index, we delete the old version of the car which is O(log(n)), according to IndexMinPQ.java, and then re-add the new version which is the insert() method. 
This is also O(log(n)). That means updating a car costs O(1)+O(log(n)) + O(log(n)) which simplifies to O(log(n)). Removing a car is done very similarly with 
an O(1) HashMap look-up given the VIN. Then we call the delete(int index) function in IndexMinPQ.java which according to the documentation is O(log(n)). That 
means removing a car has an average complexity of O(1) + O(log(n)) which simplifies to O(log(n)). Therefore, all the operations involved in this project function
in logarithmic time or better. 

	To make sure that the major operations of CarTracker.java were logarithmic time or better, more memory was used. The memory requirements of this program 
are relatively high. To take into account the major contributors to the memory overhead, we will ignore minor variables such as int index and BufferedReader reader, 
which consume a minimal amount of memory as compared to the other data structures. The mileagePQ and pricePQ which are IndexMinPQ's of type Car used a memory 
overhead of "one million". This is to say that they used up quite a lot of memory but it is hard to say in bytes how much that is. If n equals the size of the input 
data, then in most cases this term is greater than n (1000000>n). There are two of these so we have at MINIMUM Omega(2n) in memory right there. After this, we have
a HashMap known as vinMap which contains the VIN String and int Index that is associated with it. This is O(n). After this, we have two more HashMap's that kept 
the make and model as the key and an IndexMinPQ<Car> as their value that either used mileage or price to compare. This means that in total this is another O(2n). 
Overall, this would mean that at a MINIMUM, as a tight lower bound,  the memory used by this program is Omega(5n). This is quite a lot of memory and it is hard
to give a tight upper bound or even average. However, the work of this paragraph shows that this has to be a minimum. Just to make it clear, I would like to state that 
I have been considering memory used by this program in terms of how much physical space it would take to run. If I was to just look at the memory usage as a function
of input size, then we can say that this program uses Theta(5n) memory. 


**Contact Information** 

![interests](https://avatars1.githubusercontent.com/u/38919947?s=400&u=49ab1365a14fac78a91e425efd583f7a2bcb3e25&v=4)

Yogindra Raghav (YogiOnBioinformatics) 

yraghav97@gmail.com