/*
//Java program "Triangle"
//@author Joshua Richard
//Checks if Triangle is Valid
*/

import java.util.*;
import java.util.Scanner;

public class Reference {
	
	private double val;
	
	Reference(double val){
		this.val = val;
	}
	
	public static double doubler(double num) {
		return num * 2;
	}
	
	public static double tripler(Reference ref) {
		double temp = ref.val;
		
		ref.val = temp * 3;
				
		return ref.val;
	}
	
	static boolean isPrime(double val){ 
		int num = (int)val;
		
        if (num <= 1) {
            return false;
        }else {
        for (int i = 2; i < num; i++) {
            if (num % i == 0) {
            	return false;
            } 
        }
        return true;
        }
    }
public static void main(String [] args){
Scanner scan = new Scanner(System.in);

Reference ref;

System.out.println("Enter a number :: ");
String num = scan.next();

double i = 0;

boolean flag = true;

try {	
	i = Double.parseDouble(num);
}
catch(Exception e) {
	flag = false;
	
	System.out.println("Invalid Input - Please Enter a number");
}
if(flag) {

	System.out.println("Value of doubler() ::" + doubler(i));
	System.out.println("Value of isPrime() :: " + isPrime(i));
	System.out.println("Value of tripler() :: " + tripler(ref = new Reference(i)));
	System.out.println("Value of num after tripler :: " + ref.val);
}
}
/*
Sample Output:
Enter a number :: 
4
Value of doubler() ::8.0
Value of isPrime() :: false
Value of tripler() :: 12.0
Value of num after tripler :: 12.0
*/
}