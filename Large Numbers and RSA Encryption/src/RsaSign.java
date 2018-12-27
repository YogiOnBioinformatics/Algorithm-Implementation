import java.io.*; 
import java.lang.String; 
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.security.MessageDigest;



public class RsaSign{

    public static void main(String[] args){
        byte[] compare = null; 

        if(args.length==0){
            System.out.println("You must input a file for this program to work as well as a mode (sign or verify)!"); 
            System.exit(0);
        }  

        if(args.length==1){
            System.out.println("You must specify a file as your input!"); 
            System.exit(0); 
        }

        if(args[0].equals("s")){
            sign(args[1]); 
        }
        else if(args[0].equals("v")){
            verify(args[1]); 
        }
        
    
        


    }

    public static void sign(String file){

        byte[] digest = {(byte)0}; 
        byte[] ONE = {(byte) 1}; 
        LargeInteger d = new LargeInteger(ONE); 
        LargeInteger n = new LargeInteger(ONE);
        String outputFileName = file+ ".sig"; 
        

        try {
			// read in the file to hash
			Path path = Paths.get(file);
			byte[] data = Files.readAllBytes(path);

			// create class instance to create SHA-256 hash
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			// process the file
			md.update(data);
			// generate a has of the file
			digest = md.digest();

		} catch(Exception e) {
            System.out.println(e.toString());
            System.exit(0); 
        }
        

        try{
            FileInputStream fin = new FileInputStream("privkey.rsa"); 
            ObjectInputStream in = new ObjectInputStream(fin); 

            char thisChar = in.readChar();  

            int length = in.readInt(); 
            
            byte[] data = new byte[length]; 

            for(int i =0; i<length; i++){
                data[i] = in.readByte(); 
            }

            d = new LargeInteger(data); 

            char newChar = in.readChar(); 

            int newLength = in.readInt(); 
            
            byte[] data2 = new byte[newLength]; 

            for(int i =0; i<newLength; i++){
                data2[i] = in.readByte(); 
            }

            n = new LargeInteger(data2); 

            in.close(); 

        }
        catch(FileNotFoundException x){
            System.out.println("privkey.rsa is not found in the current directory"); 
            System.exit(0); 
        }
        catch(IOException y){
            y.printStackTrace();
            System.exit(0); 
        }
        
        LargeInteger hashValue = new LargeInteger(digest); 

        LargeInteger decryption = hashValue.modularExp(d, n); 

        try{
            FileOutputStream fout = new FileOutputStream(outputFileName); 
            ObjectOutputStream out = new ObjectOutputStream(fout); 

            out.writeInt(decryption.getVal().length); 
            
            byte[] transfer = decryption.getVal(); 

            for(int i=0; i<transfer.length; i++){
                out.writeByte((int)transfer[i]);
            }

            out.close(); 
            
        }
        catch(Exception x){
            x.printStackTrace();
            System.exit(0); 
        }

    }


    public static void verify(String file){
        byte[] compare = {(byte) 0}; 
        byte[] digest = {(byte) 0}; 
        byte[] ONE = {(byte) 1}; 
        String inputFileName = file+ ".sig"; 
        LargeInteger theHash = new LargeInteger(ONE);
        LargeInteger e = new LargeInteger(ONE); 
        LargeInteger n = new LargeInteger(ONE); 
        boolean match = true; 
       

        try {
			// read in the file to hash
			Path path = Paths.get(file);
			byte[] data = Files.readAllBytes(path);

			// create class instance to create SHA-256 hash
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			// process the file
			md.update(data);
			// generate a has of the file
			digest = md.digest();

		} catch(Exception y) {
            System.out.println(y.toString());
            System.exit(0); 
        }

        try{
            FileInputStream fin = new FileInputStream(inputFileName); 
            
            ObjectInputStream in = new ObjectInputStream(fin); 

            int length = in.readInt(); 

            byte[] transfer = new byte[length]; 

            for(int i=0; i<length; i++){
                 transfer[i] = in.readByte(); 
            }

            theHash = new LargeInteger(transfer); 


        }
        catch(FileNotFoundException x){
            System.out.println(inputFileName+ " was not found in the current directory"); 
            System.exit(0); 
        }
        catch(IOException y){
            y.printStackTrace();
            System.exit(0); 
        }   

        try{
            FileInputStream fin = new FileInputStream("pubkey.rsa"); 
            ObjectInputStream in = new ObjectInputStream(fin); 

            char c = in.readChar(); 

            int length = in.readInt(); 
            
            byte[] temp = new byte[length]; 

            for(int i =0; i<length; i++){
                temp[i] = in.readByte(); 
            }

            e = new LargeInteger(temp); 

            char newC = in.readChar(); 
             
            int newLength = in.readInt(); 

            byte[] temp2 = new byte[newLength]; 

            for(int i =0; i<newLength; i++){
                temp2[i] = in.readByte(); 
            }

            n = new LargeInteger(temp2); 

        }
        catch(FileNotFoundException x){
            System.out.println("Pubkey.rsa file was not found in current directory"); 
            System.exit(0); 
        }
        catch(IOException j){
            j.printStackTrace();
            System.exit(0); 
        }

        LargeInteger check = theHash.modularExp(e, n); 

        compare = check.getVal(); 

        if(digest.length!=compare.length){
             System.out.println("The hash values generated from "+ file+ " and signature do not match! The signature is not valid!"); 
        }
        else{
            for(int i=0; i<compare.length; i++){
                if(compare[i]!=digest[i]){
                    System.out.println("The hash values generated from "+ file+ " and signature do not match! The signature is not valid!"); 
                    match = false; 
                    break;
                }
            }
            if(match){
                System.out.println("The hash values generated from "+ file+ " and signature match! The signature is valid!"); 
            }
       }


       

    }
}