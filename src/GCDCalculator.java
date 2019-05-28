import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class GCDCalculator
{
	static int GCD(int a, int b) 
    { 
      if (b == 0) 
        return a; 
      return GCD(b, a % b);  
    }
	public static void main(String[] args)  
    { 
		int choice=-2;
		Scanner scan = new Scanner(System.in);
		while(choice!=-1)
		{
			int a,b;
			System.out.println("Enter a:");
			a=scan.nextInt();
			System.out.println("Enter b:");
			b=scan.nextInt();
			System.out.printf("gcd(%d,%d)=%d\n\n",a,b,GCD(a,b));
			System.out.println("Enter -1 to stop or any other key to keep going!");
			choice=scan.nextInt();
		}
    }
}