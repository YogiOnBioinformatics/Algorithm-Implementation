/*************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *  WARNING: STARTING WITH ORACLE JAVA 6, UPDATE 7 the SUBSTRING
 *  METHOD TAKES TIME AND SPACE LINEAR IN THE SIZE OF THE EXTRACTED
 *  SUBSTRING (INSTEAD OF CONSTANT SPACE AND TIME AS IN EARLIER
 *  IMPLEMENTATIONS).
 *
 *  See <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this article</a>
 *  for more details.
 *
 *************************************************************************/

public class MyLZW {
    private static final int R = 256;        // number of input chars
    private static int L = 512;       // number of codewords = 2^W
    private static int W = 9;         // codeword width
	private static char mode; 
	

    public static void compress() { 
	
		double unCompressed=0.0; 
		double compressed=0.0; 
		double origCompRatio=0.0;
	
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF
		
		BinaryStdOut.write(mode, W);  

        while (input.length() > 0) {
            String s = st.longestPrefixOf(input); // Find max prefix match s.
			unCompressed+=s.length();
            BinaryStdOut.write(st.get(s), W); // Print s's encoding.
			compressed +=W/8;
            int t = s.length();
            if (t < input.length()) {
				if(code < L){ // Add s to symbol table.
					st.put(input.substring(0, t + 1), code++);
				}
				else if(W<16){
					W++;
					L*=2; 
					st.put(input.substring(0,t+1), code++);
				}
				else if(mode== 'r'){
					st = new TST<Integer>();
					for( int i = 0; i< R; i++){
						st.put(""+ (char) i, i);
					}
					code = R+1;
					L = 512; 
					W = 9; 
					st.put(input.substring(0, t+1), code++);
				}
				else if(mode== 'm'){
					double currRatio = unCompressed/compressed;
					
					if(origCompRatio ==0.0){
						origCompRatio = unCompressed/compressed;
					}
					else if((origCompRatio/currRatio)>1.1){
						origCompRatio = currRatio;
						st = new TST<Integer>();
						for( int i = 0; i< R; i++){
							st.put(""+ (char) i, i);
						}
						code = R+1;
						L = 512; 
						W = 9; 
						st.put(input.substring(0, t+1), code++);
					}
				}
			}	
            input = input.substring(t);            // Scan past s in input.
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    } 


    public static void expand() {
        String[] st = new String[65536];
        int i; // next available codeword value
		double unCompressed=0.0; 
		double compressed=0.0; 
		double origCompRatio=0.0;
		
        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";      		// (unused) lookahead for EOF
		
		mode = BinaryStdIn.readChar(W); 
		int codeword = BinaryStdIn.readInt(W);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];

        while (true) {
			unCompressed +=val.length();
			compressed +=W/8;
			if(i==L){
				if(W<16){
					W++; 
					L*=2;
				}
				else if(mode == 'r'){
					st = new String[65536];
					for (i = 0; i < R; i++){
						st[i] = "" + (char) i;
					}
					st[i++] = ""; 
					L = 512; 
					W = 9; 
					
				}
				else if(mode == 'm'){
					double currRatio = unCompressed/compressed; 
					
					if(origCompRatio == 0.0){
						origCompRatio = unCompressed/compressed;
					}
					else if((origCompRatio/currRatio)>1.1){
						origCompRatio = currRatio; 
						st = new String[65536];
						for (i = 0; i < R; i++){
							st[i] = "" + (char) i;
						}
						st[i++] = ""; 
						L = 512; 
						W = 9; 
					}
				}
			}
			
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W); 
            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L) st[i++] = val + s.charAt(0);
            val = s;
        }
        BinaryStdOut.close();
    }



    public static void main(String[] args) {
		char[] charArr;
		
        if(args[0].equals("-")) {
			
			for(int i =0; i<args[1].toCharArray().length; i++){
				charArr = args[1].toCharArray();
				mode = charArr[0];
			}
			compress();
		}
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}
