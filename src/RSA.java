import java.io.*;
import java.math.*;
import java.math.BigInteger.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RSA {
	static ArrayList<Line> lines = new ArrayList<>();
	static BigInteger n,p,q,phi,e,d;
	public static void main(String[] argv) 
	{
		/*if(argv.length>=2)
		{
			System.out.println("No file Specified!");
			System.out.println("Run Like this!");
			System.out.println("java RSA <fileName>");
			System.out.println("eg.  java RSA testfile-SDES.txt");
			System.exit(0);
			
		}*/
		
		// 1. Find large primes p and q
		Random r = new Random();
		p = BigInteger.probablePrime(r.nextInt(13-11) + 11,r);
		q = BigInteger.probablePrime(r.nextInt(13-11) + 11,r);

		/* 		2. Compute n from p and q
				n is mod for private & public keys, n bit length is key length
		*/
		 n = n(p, q);

		/* 		3. Compute Phi(n) (Euler's totient function)
				Phi(n) = (p-1)(q-1)
				BigIntegers are objects and must use methods for algebraic operations
		*/
		 phi = generatePhiValue(p, q);

		// 4. Find an int e such that 1 < e < Phi(n) 	and gcd(e,Phi) = 1
		 e = generateE(phi);
		// 5. Calculate d where  d ≡ e^(-1) (mod Phi(n))
		 d = new BigInteger(""+1);
		//calculate d until  it is greater than  1
		 repeat:
			 while(true)
			 {
				d = extendedEuclid(e, phi)[1];
				if(d.intValue()==1)
				{
					d = d.add(phi);
				}
				else
				{
					break repeat;
				}
			 }
		
		// Print generateErated values for reference
		System.out.println("p: " + p);
		System.out.println("q: " + q);
		System.out.println("n: " + n);
		System.out.println("Phi: " + phi);
		System.out.println("e: " + e);
		System.out.println("d: " + d);
		 File f = new File("testfile-SDES.txt");
		byte[] bArray = new byte[(int) file.length()];
		FileInputStream fis = null;
		  try{
           			 fis = new FileInputStream(file);
            			fis.read(bArray);
            			fis.close();        
            
        		}		
		catch(IOException ioExp){
            			ioExp.printStackTrace();
        		}
		BigInteger iq = new BigInteger(bArray);
		BigInteger enc = encrypt(iq,e,n);
		BigInteger dec = decrypt(enc,d,n);
		byte[] array = dec.toByteArray();
        Files.write(Paths.get("test1"),array);
	}
	/** 3. Compute Phi(n) (Euler's totient function)
	 *  Phi(n) = (p-1)(q-1)
	 *	BigIntegers are objects and must use methods for algebraic operations
	 */
	public static BigInteger generatePhiValue(BigInteger p, BigInteger q) {
		BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		return phi;
	}
	//open the file and read it line by line. Then those lines are read character by character.
	//The characters are then converted into numeric value , which then are encrypted!
	public static void getFileLinesEnc()
	{
		try {
            File f = new File("testfile-SDES.txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                lines.add(new Line(readLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public static void getLineCharsEnc(String line)
	{
	}
	/**
	 * Recursive implementation of Euclidian algorithm to find greatest common denominator
	 * Note: Uses BigInteger operations
	 */
	public static BigInteger gcd(BigInteger a, BigInteger b) {
		if (b.equals(BigInteger.ZERO)) {
			return a;
		} else {
			return gcd(b, a.mod(b));
		}
	}


	/** Recursive EXTENDED Euclidean algorithm, solves Bezout's identity (ax + by = gcd(a,b))
	 * and finds the multiplicative inverse which is the solution to ax ≡ 1 (mod m)
	 * returns [d, p, q] where d = gcd(a,b) and ap + bq = d
	 * Note: Uses BigInteger operations
	 */
	public static BigInteger[] extendedEuclid(BigInteger a, BigInteger b) {
		if (b.equals(BigInteger.ZERO)) return new BigInteger[] {
			a, BigInteger.ONE, BigInteger.ZERO
		};
		BigInteger[] vals = extendedEuclid(b, a.mod(b));
		BigInteger d = vals[0];
		BigInteger q = vals[1].subtract(a.divide(b).multiply(vals[2]));
		BigInteger p = vals[2];
		return new BigInteger[] {
			d, p, q
		};
	}


	/**
	 * generateErate e by finding a Phi such that they are coprimes (gcd = 1)
	 *
	 */
	public static BigInteger generateE(BigInteger phi) {
		Random rand = new Random();
		BigInteger e = new BigInteger(""+23);
		return e;
	}

	public static BigInteger encrypt(BigInteger message, BigInteger e, BigInteger n) {
		return message.modPow(e, n);
	}

	public static BigInteger decrypt(BigInteger message, BigInteger d, BigInteger n) {
		return message.modPow(d, n);
	}
	public static BigInteger n(BigInteger p, BigInteger q) {
		return p.multiply(q);

	}
	private static class Line
	{
		String line;
		ArrayList<BigInteger> bigIntegerEnc = new ArrayList<>();
		public Line(String line)
		{
			this.line=line;
			doLineEncryption();
		}
		//This function will do line by line encryption
		public void doLineEncryption()
		{
			for(int i=0;i<line.length();i++)
			{
				int value = line.charAt(i);
				BigInteger enc = encrypt(new BigInteger(""+value), e, n);
				bigIntegerEnc.add(enc);
			}
		}
		//this function will do line by line decryption
		public String doLineDecryption()
		{
			String s = "";
			for(int i=0;i<bigIntegerEnc.size();i++)
			{
				BigInteger decrypt = decrypt(bigIntegerEnc.get(i),d,n);
				s = s + (char)decrypt.intValue();
			}
			return s;
		}
		//This is toString to show the EncryptedVlaues
		public String toString()
		{
			String s ="";
			for(int i=0;i<bigIntegerEnc.size();i++)
			{
				s=s+bigIntegerEnc.get(i);
			}
			return s;
		}
	}
}