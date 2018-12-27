import java.util.Random; 
import java.math.BigInteger;
import java.io.*; 


public class RsaKeyGen{
    public static void main (String[] args){
       
        //byte[] arr = {(byte) 99}; 
        //byte[] arr2 = {(byte) 78}; 

        Random rand = new Random(); 
        Random rand2 = new Random(); 
        byte[] ONE = {(byte) 1}; 
        byte[] NEGATIVEONE = {(byte) -1}; 
        LargeInteger e; 
        LargeInteger d; 

        LargeInteger one = new LargeInteger(ONE); 
        LargeInteger negativeOne = new LargeInteger(NEGATIVEONE); 

       LargeInteger p = new LargeInteger(255, rand); 
       LargeInteger q = new LargeInteger(255, rand); 

       // LargeInteger p = new LargeInteger(arr); 
       // LargeInteger q = new LargeInteger(arr2); 

        LargeInteger n = p.multiply(q); 
        
        LargeInteger p_minus_one = p.subtract(one); 
        LargeInteger q_minus_one = q.subtract(one); 

        LargeInteger phiOfN = p_minus_one.multiply(q_minus_one); 

        e = new LargeInteger(phiOfN.getVal()); 

        while((e.greaterLargeInteger(e, phiOfN)==1)|| (e.greaterLargeInteger(e, one)==-1) ){
            e = new LargeInteger(512, rand); 
        }

        LargeInteger[] answer = phiOfN.XGCD(e); 

        d = answer[2]; 



        /*while(true){
            duplicatePhiOfN =  duplicatePhiOfN.subtract(one); 
            LargeInteger answer[] = phiOfN.XGCD(duplicatePhiOfN);  

            if(answer[0].greaterLargeInteger(answer[0], one)==0){ 
                e = duplicatePhiOfN;
                d = answer[2];  
                break; 
            }
            
        }*/

        

        try{
            FileOutputStream fout = new FileOutputStream("pubkey.rsa"); 
            ObjectOutputStream out = new ObjectOutputStream(fout); 

            FileOutputStream otherOut = new FileOutputStream("privkey.rsa"); 
            ObjectOutputStream privOut = new ObjectOutputStream(otherOut); 

            out.writeChar(101);
            out.writeInt(e.getVal().length);

            byte[] eArr = e.getVal(); 

            for(int i =0; i<e.getVal().length; i++){
                out.writeByte((int) eArr[i]);
            }

            out.writeChar(110); 
            out.writeInt(n.getVal().length); 

            byte[] nArr = n.getVal(); 

            for(int i =0; i<n.getVal().length; i++){
                out.writeByte((int) nArr[i]);
            }

            out.close(); 

            privOut.writeChar(100); 
            privOut.writeInt(d.getVal().length); 

            byte[] dArr = d.getVal(); 

            for(int i =0; i<d.getVal().length; i++){
                privOut.writeByte((int) dArr[i] );
            }

            privOut.writeChar(110); 
            privOut.writeInt(n.getVal().length); 

            for(int i =0; i<n.getVal().length; i++){
                privOut.writeByte((int) nArr[i]);
            }

            privOut.close();

        }
        catch(IOException x){
            x.printStackTrace(); 
        }


        



    }
}