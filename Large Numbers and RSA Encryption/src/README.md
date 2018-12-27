# RSA Encryption Code  

The code below must be run in a sequence of steps 

**1. RSA Key Generation**

    javac RsaKeyGen.java 
    java RsaKeyGen

This creates two files known as `pubkey.rsa` and `privkey.rsa` which will be used by the next program. This means that *you must RUN* this code before using the rest of the software. 

**2. Sign File**

    javac RsaSign.java
    java RsaSign s *.txt

Next you must create an encrypted file by giving the `RsaSign` program the `s` flag which stands for *sign*. You must provide a `.txt` file which the program will take and rename to tack on a `.sig` file extension (eg. `*.txt` -> `*.txt.sig`). 

**3. Verify File** 

    java RsaSign v *.txt

This time we need to verify the file. For this reason we use the `v` flag. *Note: DO NOT* input the `.sig`-ending file. Just the use the name of the original text file that was signed. The program will then verify the hash and see if the signature of the verified decrypted file matches that of the original `.txt` file that was used. 

**Contact Information** 

![interests](https://avatars1.githubusercontent.com/u/38919947?s=400&u=49ab1365a14fac78a91e425efd583f7a2bcb3e25&v=4)

Yogindra Raghav (YogiOnBioinformatics) 

yraghav97@gmail.com