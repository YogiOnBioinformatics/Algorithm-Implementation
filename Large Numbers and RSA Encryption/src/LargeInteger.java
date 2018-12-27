import java.util.Random;
import java.math.BigInteger;

public class LargeInteger {
	
	private final byte[] ONE = {(byte) 1};
	private final byte[] ZERO = {(byte) 0}; 
	 

	private byte[] val;

	/**
	 * Construct the LargeInteger from a given byte array
	 * @param b the byte array that this LargeInteger should represent
	 */
	public LargeInteger(byte[] b) {
		val = b;
	}

	/**
	 * Construct the LargeInteger by generatin a random n-bit number that is
	 * probably prime (2^-100 chance of being composite).
	 * @param n the bitlength of the requested integer
	 * @param rnd instance of java.util.Random to use in prime generation
	 */
	public LargeInteger(int n, Random rnd) {
		val = BigInteger.probablePrime(n, rnd).toByteArray();
	}
	
	/**
	 * Return this LargeInteger's val
	 * @return val
	 */
	public byte[] getVal() {
		return val;
	}

	/**
	 * Return the number of bytes in val
	 * @return length of the val byte array
	 */
	public int length() {
		return val.length;
	}

	/** 
	 * Add a new byte as the most significant in this
	 * @param extension the byte to place as most significant
	 */
	public void extend(byte extension) {
		byte[] newv = new byte[val.length + 1];
		newv[0] = extension;
		for (int i = 0; i < val.length; i++) {
			newv[i + 1] = val[i];
		}
		val = newv;
	}

	/**
	 * If this is negative, most significant bit will be 1 meaning most 
	 * significant byte will be a negative signed number
	 * @return true if this is negative, false if positive
	 */
	public boolean isNegative() {
		return (val[0] < 0);
	}

	/**
	 * Computes the sum of this and other
	 * @param other the other LargeInteger to sum with this
	 */
	public LargeInteger add(LargeInteger other) {
		byte[] a, b;
		// If operands are of different sizes, put larger first ...
		if (val.length < other.length()) {
			a = other.getVal();
			b = val;
		}
		else {
			a = val;
			b = other.getVal();
		}

		// ... and normalize size for convenience
		if (b.length < a.length) {
			int diff = a.length - b.length;

			byte pad = (byte) 0;
			if (b[0] < 0) {
				pad = (byte) 0xFF;
			}

			byte[] newb = new byte[a.length];
			for (int i = 0; i < diff; i++) {
				newb[i] = pad;
			}

			for (int i = 0; i < b.length; i++) {
				newb[i + diff] = b[i];
			}

			b = newb;
		}

		// Actually compute the add
		int carry = 0;
		byte[] res = new byte[a.length];
		for (int i = a.length - 1; i >= 0; i--) {
			// Be sure to bitmask so that cast of negative bytes does not
			//  introduce spurious 1 bits into result of cast
			carry = ((int) a[i] & 0xFF) + ((int) b[i] & 0xFF) + carry;

			// Assign to next byte
			res[i] = (byte) (carry & 0xFF);

			// Carry remainder over to next byte (always want to shift in 0s)
			carry = carry >>> 8;
		}

		LargeInteger res_li = new LargeInteger(res);
	
		// If both operands are positive, magnitude could increase as a result
		//  of addition
		if (!this.isNegative() && !other.isNegative()) {
			// If we have either a leftover carry value or we used the last
			//  bit in the most significant byte, we need to extend the result
			if (res_li.isNegative()) {
				res_li.extend((byte) carry);
			}
		}
		// Magnitude could also increase if both operands are negative
		else if (this.isNegative() && other.isNegative()) {
			if (!res_li.isNegative()) {
				res_li.extend((byte) 0xFF);
			}
		}

		// Note that result will always be the same size as biggest input
		//  (e.g., -127 + 128 will use 2 bytes to store the result value 1)
		return res_li;
	}

	/**
	 * Negate val using two's complement representation
	 * @return negation of this
	 */
	public LargeInteger negate() {
		byte[] neg = new byte[val.length];
		int offset = 0;

		// Check to ensure we can represent negation in same length
		//  (e.g., -128 can be represented in 8 bits using two's 
		//  complement, +128 requires 9)
		if (val[0] == (byte) 0x80) { // 0x80 is 10000000
			boolean needs_ex = true;
			for (int i = 1; i < val.length; i++) {
				if (val[i] != (byte) 0) {
					needs_ex = false;
					break;
				}
			}
			// if first byte is 0x80 and all others are 0, must extend
			if (needs_ex) {
				neg = new byte[val.length + 1];
				neg[0] = (byte) 0;
				offset = 1;
			}
		}

		// flip all bits
		for (int i  = 0; i < val.length; i++) {
			neg[i + offset] = (byte) ~val[i];
		}

		LargeInteger neg_li = new LargeInteger(neg);
	
		// add 1 to complete two's complement negation
		return neg_li.add(new LargeInteger(ONE));
	}

	/**
	 * Implement subtraction as simply negation and addition
	 * @param other LargeInteger to subtract from this
	 * @return difference of this and other
	 */
	public LargeInteger subtract(LargeInteger other) {
		return this.add(other.negate());
	}

	/**
	 * Compute the product of this and other
	 * @param other LargeInteger to multiply by this
	 * @return product of this and other
	 */
	public LargeInteger multiply(LargeInteger other) {
		byte[] thisArr; 
		byte[] otherArr; 
		byte[] partialArr; 
		LargeInteger total = new LargeInteger(new byte[1]); 
		
		if(this.isNegative()){
			thisArr = this.negate().getVal(); 
		}
		else{
			thisArr = this.getVal(); 
		}
		if(other.isNegative()){
			otherArr = other.negate().getVal(); 
		}
		else{
			otherArr = other.getVal(); 
		}


		for(int i = thisArr.length-1; i>=0; i--){
			for(int j = otherArr.length-1; j>=0; j--){
				int product = ((int) thisArr[i] & 0xFF) * ((int) otherArr[j] & 0xFF); 
				int shifter = (thisArr.length-1)-i + (otherArr.length-1)-j; 
				byte lowerByte = (byte)(product & 0xFF); 
				product = product >>>8; 
				byte upperByte = (byte)(product & 0xFF); 
				if(upperByte<0){
					partialArr = new byte[3+shifter]; 
				}
				else{
					partialArr = new byte[2+shifter]; 
				}
				partialArr[partialArr.length-1-shifter] = lowerByte; 
				partialArr[partialArr.length-2-shifter] = upperByte; 
				LargeInteger partialInt = new LargeInteger(partialArr); 
				total = total.add(partialInt); 
			}
		}
		
		if(this.isNegative() && !other.isNegative()){
			total = total.negate(); 
		}
		else if(!this.isNegative() && other.isNegative()){
			total = total.negate(); 
		}

		return total; 
		

	}
	
	/**
	 * Run the extended Euclidean algorithm on this and other
	 * @param other another LargeInteger
	 * @return an array structured as follows:
	 *   0:  the GCD of this and other
	 *   1:  a valid x value
	 *   2:  a valid y value
	 * such that this * x + other * y == GCD in index 0
	 */
	 public LargeInteger[] XGCD(LargeInteger other) {

		LargeInteger[] answer = new LargeInteger[3];  
		
		 
		
		int compare = greaterLargeInteger(this, other); 
	
		// find which large integer is bigger 
		if(compare ==1){ //if first number is bigger than second 
		
			answer = extendedEuclidean(this, other); 
			
		
		}
		else if(compare ==0){
			
			byte[] zero = {(byte) 0}; 
			answer[0] = other; //numbers are the exact same 
			answer[1] = new LargeInteger(zero); 
			answer[2] = new LargeInteger(ONE); 
			
		}
		else if(compare ==-1){//second number bigger than first 
			
			answer = extendedEuclidean(other, this); 
			
        }
     

		return answer; 
	}

	 /**
	  * Compute the result of raising this to the power of y mod n
	  * @param y exponent to raise this to
	  * @param n modulus value to use
	  * @return this^y mod n
	  */
	 public LargeInteger modularExp(LargeInteger y, LargeInteger n) {
		/*LargeInteger thisCopy = new LargeInteger(this.getVal()); 
		int[] bitsOfY = byteToBitArr(y.getVal()); 
		int iter =0; 												//ALL OF THIS CODE WAS NOT WORKING. IT ONLY GOT UP TO 7 ITERATIONS EVERYTIME
		LargeInteger modExpAnswer = new LargeInteger(ONE); 		// AND THEN STOPPED UNEXPECTEDLY. IT WOULD NOT MOVE ON AND I COULD NOT FIGURE
																	//OUT THE SPECIFIC REASON FOR WHY THIS HAPPENED. THIS IS WHY I HAD TO USE
		for(int i =0; i<bitsOfY.length; i++){						//BIGINTEGER
			System.out.print(bitsOfY[i]+ " "); 
		}

		while(iter<bitsOfY.length){
			modExpAnswer = modExpAnswer.multiply(modExpAnswer).modulus(n); 
			System.out.println("YO"); 
			if(bitsOfY[iter] == 1){

				System.out.println("I SHOULD BE HERE");
				modExpAnswer = modExpAnswer.multiply(thisCopy).modulus(n); 
			}


			System.out.println("HERE"); 
			iter++;
		}

		// YOUR CODE HERE (replace the return, too...)
		return modExpAnswer; */

		BigInteger thisOne = new BigInteger(this.getVal()); 

		BigInteger exp= new BigInteger(y.getVal()); 
		BigInteger modulus = new BigInteger(n.getVal()); 

		BigInteger answer = thisOne.modPow(exp, modulus); 

		LargeInteger returnThis = new LargeInteger(answer.toByteArray()); 

		return returnThis; 
		

	}

	public int greaterLargeInteger(LargeInteger first, LargeInteger second){
		byte[] firstArr = first.getVal(); 
		byte[] secondArr = second.getVal(); 
		

		if(firstArr.length>secondArr.length){
			int difference = firstArr.length-secondArr.length;
			byte[] inputArr=new byte[difference]; //stores the higher-order bytes for situations where differences in length exist between the LargeInteger objects

			for(int i =0; i<difference; i++){//load the higher-order bytes of longer byte array into a temporary byte array 
				inputArr[i] = firstArr[i];
			}
			
			int[] bitArr = byteToBitArr(inputArr); 
			for(int i =0; i<bitArr.length; i++){//check if higher-order byte contains a 1 in which case we know that we're done.
				if(bitArr[i]==1){
					return 1; 
				}
			}

			for(int i =0; i<secondArr.length; i++){//look at all other bytes of both LargeIntegers 
				byte[] inputArr2 = new byte[1]; 
				byte[] inputArr3 = new byte[1]; 
				inputArr2[0] = firstArr[difference]; 
				inputArr3[0] = secondArr[i]; 
				int[] bits = byteToBitArr(inputArr2);
				int[] bits2 = byteToBitArr(inputArr3);
				assert bits.length == bits2.length; 

				for(int j =0; j<bits.length;j++){
					if(bits[j]==1 && bits2[j]==0){
						return 1; 
					}
					else if(bits[j]==0 && bits2[j]==1){
						return -1; 
					}
				}

				difference++;
			}
		}
		else if(secondArr.length>firstArr.length){

			int difference = secondArr.length-firstArr.length;
			byte[] inputArr=new byte[difference]; //stores the higher-order bytes for situations where differences in length exist between the LargeInteger objects

			for(int i =0; i<difference; i++){//load the higher-order bytes of longer byte array into a temporary byte array 
				inputArr[i] = secondArr[i];
			}
			
			int[] bitArr = byteToBitArr(inputArr); 
			for(int i =0; i<bitArr.length; i++){//check if higher-order byte contains a 1 in which case we know that we're done.
				if(bitArr[i]==1){
					return -1; 
				}
			}

			for(int i =0; i<firstArr.length; i++){//look at all other bytes of both LargeIntegers 
				byte[] inputArr2 = new byte[1]; 
				byte[] inputArr3 = new byte[1]; 
				inputArr2[0] = secondArr[difference]; 
				inputArr3[0] = firstArr[i]; 
				int[] bits = byteToBitArr(inputArr2);
				int[] bits2 = byteToBitArr(inputArr3);
				assert bits.length == bits2.length; 

				for(int j =0; j<bits.length;j++){
					if(bits[j]==1 && bits2[j]==0){
						return -1; 
					}
					else if(bits[j]==0 && bits2[j]==1){
						return 1; 
					}
				}

				difference++;
			}
		}
		else if(firstArr.length == secondArr.length){

			for(int i =0; i<secondArr.length; i++){//look at all other bytes of both LargeIntegers 
				byte[] inputArr2 = new byte[1]; 
				byte[] inputArr3 = new byte[1]; 
				inputArr2[0] = firstArr[i]; 
				inputArr3[0] = secondArr[i]; 
				int[] bits = byteToBitArr(inputArr2);
				int[] bits2 = byteToBitArr(inputArr3);
				assert bits.length == bits2.length; 

				for(int j =0; j<bits.length;j++){
					if(bits[j]==1 && bits2[j]==0){
						return 1; 
					}
					else if(bits[j]==0 && bits2[j]==1){
						return -1; 
					}
				}

				
			}

		}

		return 0; 

	}

	public LargeInteger[] extendedEuclidean(LargeInteger bigger, LargeInteger smaller){

		

		if(greaterLargeInteger(smaller, new LargeInteger(ZERO))==0){
			LargeInteger returnX = new LargeInteger(ONE); 
			LargeInteger returnY = new LargeInteger(ZERO); 
			LargeInteger[] answer = new LargeInteger[3]; 
			answer[0] = bigger; 
			answer[1] = returnX; 
			answer[2] = returnY; 
			return answer; 
			
		}

		LargeInteger quotient = bigger.divide(smaller); 
		LargeInteger modulo = bigger.modulus(smaller); 


		LargeInteger[] answer = extendedEuclidean(smaller, modulo); 

		LargeInteger gcd = answer[0]; 
		LargeInteger prevX = answer[1]; 
		LargeInteger prevY = answer[2]; 
		
		bigger = prevY; 
		smaller = prevX.subtract(quotient.multiply(prevY)); 
		
		return new LargeInteger[]{gcd.fixByteArray(),bigger.fixByteArray(), smaller.fixByteArray()}; 


	}


	public static int[] byteToBitArr(byte[] byteArr){
		
        int[] bitArr = new int[byteArr.length*8];
        
        for (int n =0; n<byteArr.length; n++)
        {
          //Use ints to avoid any possible confusion due to signed byte values
          int sourceByte = 0xFF &(int)byteArr[n];//convert byte to unsigned int
          int mask = 0x80;
          for (int i=0; i<8; i++)
          {
            int maskResult = sourceByte & mask;  // Extract the single bit
            if (maskResult>0) {
                 bitArr[8*n + i] = 1;
            }
            else {
                 bitArr[8*n + i] = 0;  // Unnecessary since array is initiated to zero but good documentation
            }
            mask = mask >> 1;
          }
        }

        return bitArr; 

        
    }

    private boolean isZero(){
        byte[] one = new byte[1]; 
        one[0] = (byte) 1; 
        if(this.subtract(new LargeInteger(one)).isNegative() && !this.isNegative()){
            return true; 
        }
        return false; 
	}
	
	public LargeInteger divide (LargeInteger other){
		
		LargeInteger thisCopy = new LargeInteger(this.getVal()); 
		LargeInteger otherCopy = new LargeInteger(other.getVal()); 
		LargeInteger quotient = new LargeInteger(ZERO); 
		LargeInteger one = new LargeInteger(ONE); 

		while(true){
			if(greaterLargeInteger(thisCopy, otherCopy)!=-1){
				quotient = quotient.add(one); 
			}
			else{
				break;
			}

			thisCopy = thisCopy.subtract(otherCopy); 
		}

		return quotient; 

	}

	public LargeInteger modulus(LargeInteger other){
		LargeInteger thisCopy = new LargeInteger(this.getVal()); 
		LargeInteger otherCopy = new LargeInteger(other.getVal()); 
		LargeInteger modulo = new LargeInteger(thisCopy.getVal()); 

		while(greaterLargeInteger(thisCopy, otherCopy)!=-1){
			thisCopy = thisCopy.subtract(otherCopy); 
			modulo = new LargeInteger(thisCopy.getVal()); 
		
		}

		return modulo; 

	}
	
	public LargeInteger fixByteArray(){
		LargeInteger fixThis = new LargeInteger(this.getVal()); 
		boolean isNegative = false; 

		if(this.isNegative()){
			fixThis = fixThis.negate(); 
			isNegative = true; 
		}

		byte[] fixThisArr = fixThis.getVal(); 

		while(fixThisArr.length>2){
			if(fixThisArr[0] ==0 && ((fixThisArr[1]&128) ==0)){
				byte[] smaller = new byte[fixThisArr.length-1]; 
				for(int i =0; i<smaller.length; i++){
					smaller[i] = fixThisArr[i+1]; 
				}
				fixThisArr = smaller; 
			}
			else{
				break;
			}

		}

		fixThis= new LargeInteger(fixThisArr); 

		if(isNegative){
			return fixThis.negate(); 
		}

		return fixThis; 
	}
	
	
}
