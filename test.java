/*
//Java program "Triangle"
//@author Joshua Richard
//Checks if Triangle is Valid
*/

import java.util.*;
import java.util.Scanner;

public class test {
public static void main(String [] args){
Scanner scan = new Scanner(System.in);

System.out.println("Enter a number :: ");
String num = scan.next();

double i = 0;

boolean flag = true;

try {
	flag = false;
	
	i = Double.parseDouble(num);
}
catch(Exception e) {
	System.out.println("Invalid Input - Please Enter a number");
}
if(!flag) {
	System.out.println(i);
}
}
/*
Sample Output:

Enter value for a: 
7
Enter value for b: 
10
Enter value for c: 
12

Method 1:
Valid Triangle

Method 2:
Valid Triangle

Method 3:
Valid Triangle

Enter value for a: 
1
Enter value for b: 
10
Enter value for c: 
12

Method 1:
Invalid Triangle

Method 2:
Invalid Triangle

Method 3:
Invalid Triangle
*/
}