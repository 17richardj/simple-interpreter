import java.util.Scanner;

public class Recursive {
public static int josephus(int n, int k) {
	
	if(n == 1) return 1;
	
    else return (josephus(n - 1, k) + k - 1) % n + 1;
}
public static int choose(int n, int r) {
	if(r == 0) return 1;
	if(r == n) return 1;	
	if(r > n) return 0;
	
	return (choose(n-1, r-1) + choose(n - 1, r));
}
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.println("----------Josephus-----------");
		System.out.println("How many people are there : ");
		int n = scan.nextInt();
		System.out.println("Eliminate every how many people : ");
		int k = scan.nextInt();
		
		System.out.println("-----------Choose------------");
		System.out.println("Enter value for n : ");
		int nc = scan.nextInt();
		System.out.println("Enter value for r : ");
		int r = scan.nextInt();
		System.out.println("Josephus position is :: " + josephus(n, k));
		System.out.println("Choose is :: " + choose(nc, r));
		
	}
/*
 * Sample Output
 * 
 * ----------Josephus-----------
How many people are there? 6
Eliminate every how often? 2
-----------Choose------------
Enter value for n :
7
Enter value for r : 
4
Josephus position is ::  5
Choose is ::  15


----------Josephus-----------
How many people are there : 
6
Eliminate every how many people : 
2
-----------Choose------------
Enter value for n :
 10
Enter value for r : 
3
Josephus position is :: 5
Choose is :: 120

 */
}
