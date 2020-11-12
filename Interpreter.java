//----------------------------------------------------------------------
/*
  file  : Interpreter.java
  date  : 10/15/2020
  author: Joshua RIchard
  description: Simple interpreter program
*/
//----------------------------------------------------------------------
import java.util.Random;

public class Interpreter {
	static Random rand = new Random(); //instance of random class
	
    static int  PC;          //program counter holds address of next instr
    static int  AC;          //the accumulator - a register for doing arithmetic
    static boolean    run_bit = true;  //a bit that can be turned off to halt the machine
    static int  instr;       //a holding register for the current instruction
    static int  instr_type;  //the instruction type (opcode)
    static int  data_loc;    //the address of the data, or -1 if none
    static int  data;        //holds the current operand   
    static boolean debug = false;
    
    /*
     * Initialize all constants to random, unique, integer value
     */
    
    final static int CLR = 1;       //<-- set the value in the AC to 0
    final static int ADDI = 2;   //<-- add the value x to the AC
    final static int ADDM = 3;   //<-- add the value in memory location y to AC
    final static int HALT = 4;      //<-- instruction which halts the processor
    
    final static int LDI = 5;    //<-- load the AC with the value x
    final static int LDM = 6;    //<-- load the AC with the value in memory location y
    final static int ST = 7;    //<-- store the value in the AC  in memory location y
    
	final static int ADDR = 8;	//r1	r3	<-- add value in register 3 to the value in register 1 (with the result in r1)
	final static int DBG = 9;			//<-- turn on debug flag
	final static int NODBG = 10;		//<-- turn off debug flag
	
	final static int r0 = 11;
	final static int r1 = 12;
	final static int r2 = 13;
	final static int r3 = 14;
	final static int r4 = 15;
	
	static int stor_0 = rand.nextInt(100);		//initialize to random value per recommendation by Professor Grevera
	static int stor_1 = rand.nextInt(100);
	static int stor_2 = rand.nextInt(100);
	static int stor_3 = rand.nextInt(100);
	static int stor_4 = rand.nextInt(100);

    //------------------------------------------------------------------
    //This procedure interprets programs for a simple machine.  The machine
    //has a register AC (accumulator), used for arithmetic.  The interpreter
    //keeps running until the run bit is turned off by the HALT instruction.
    //The state of a process running on this machine consists of the memory,
    //the program counter, the run bit, and the AC.  The input parameters
    //consist of the memory image and the starting address.
    public static void interpret ( int memory[], int starting_address ) {
        PC = starting_address;
        run_bit = true;
        while (run_bit) {	
            instr = memory[PC];  //fetch next instruction into instr
            PC++;  //increment program counter
            instr_type = get_instr_type( instr );  //determine instruction type
            //System.out.println("Istruction: " + instr + " Memory:  " + memory[PC] + " PC: " + PC);
            data_loc = find_data( instr, instr_type, memory );  //locate data (-1 if none)
            if (data_loc >= 0)  {  //if data_loc is -1, there is no operands
                data = memory[data_loc];  //fetch the data
            }
            execute( instr_type, data, memory );  //execute instruction
            if(debug == true){
               //if(PC == starting_address+1) System.out.println("\n------BEGIN DEBUG MODE-----\n");
               System.out.println("PC value: " + PC + " | Register 0: " + stor_0 + " | Register 1: " + stor_1 + " | Register 2: " + stor_2 + " | Register 3: " + stor_3 + " | Register 4: " + stor_4);
            }
        }
        System.out.println("\n				------END DEBUG MODE-----\n");
    }
    //------------------------------------------------------------------
    //since our instruction set is so simple, we'll let the opcode and 
    // instruction type be the same.
    private static int get_instr_type ( int opcode ) { return opcode; }
    //------------------------------------------------------------------
    /*
     * function: find_data
     * @param opcode(instruction type), type, program in memory
     * Finds program data in memory and returns it for execution
     * @todo v3
     * @author Joshua Richard
     */
    private static int find_data ( int opcode, int type, int memory[] ) { 
    	switch(opcode){
	    	case ADDI:						
	    		PC+=2;						//increment PC
	    		return PC-1;				//decrement PC accordingly, to point to required info
			case ADDM:						
				PC+=2;
				return memory[PC-1];		//decrement PC in memory for same reason
	    	case ADDR:						
	    		PC+=2;						
	    		return PC-1;
			case LDI:						//Load Register with value of data
	    		PC+=2;
	    		return PC - 1;
			case LDM:
	    		PC+=2;
	    		return memory[PC-1];
			case ST:
	    		PC+=2;
	    		return PC-2;				//ST points to different location, as is required to fulfill task
			case HALT:
				PC+=2;
				return PC-1;
			case CLR:
				PC+=2;
				return PC-1;
			case DBG:
				PC+=2;
				return PC-1;
			case NODBG:
				PC+=2;
				return PC-1;			
	    	default:
	    		return -1;					//else return -1
    	}
    }
    //------------------------------------------------------------------
    /*
     * function: execute
     * @param type, data - to be executed, program in memory
     * Executes variosu operations on data in memory
     * @todo v3
     * @author Joshua Richard
     */
    private static void execute ( int type, int data, int memory[] ) {
    	//System.out.println(PC);
    	//System.out.println(" - --- Istruction: " + type + " Memory:  " + memory[PC-1] + " PC: " + PC + " data: " + data);
    	switch(type) {
	    	case HALT:						//stop program execution
	    		run_bit = false;
	    		
	    		//For Debugging, View Memory
	    		/*
	    		for(int i = 0 ; i < memory.length; i ++) {
	    			System.out.println("Memory Location : " + i + " :: " + memory[i]);
	    		}*/
	    		//
	    		
	    		break;
	    	case CLR:						//Clear the value of the specified register
	    		switch(memory[PC-2]) {		//track Program counter - 2 to point to the specified register
		    		case r0:
		    			stor_0 = 0;			//Set registers equal to zero
		    			break;
		    		case r1:
		    			stor_1 = 0;
		    			break;
		    		case r2:
		    			stor_2 = 0;
		    			break;
		    		case r3:
		    			stor_3 = 0;
		    			break;
		    		case r4:
		    			stor_4 = 0;
		    			break;
	    		}
	    		break;
	    	case ADDI:						//ADD specified value to specified register                                                                                           
	    		switch(memory[PC-2]) {
		    		case r0:
		    			stor_0 += data;		//add data
		    			break;
		    		case r1:
		    			stor_1 += data;
		    			break;
		    		case r2:
		    			stor_2 += data;
		    			break;
		    		case r3:
		    			stor_3 += data;
		    			break;
		    		case r4:
		    			stor_4 += data;
		    			break;
	    		}
	    		break;
	    	case ADDM:						//ADD specified memory location value to specified register
	    		switch(memory[PC-2]) {
		    		case r0:
		    			stor_0 += data;
		    			break;
		    		case r1:
		    			System.out.println(data + " " + memory[data]);
		    			stor_1 += data;
		    			break;
		    		case r2:
		    			stor_2 += data;
		    			break;
		    		case r3:
		    			stor_3 += data;
		    			break;
		    		case r4:
		    			stor_4 += data;
		    			break;
	    		}
	    		break;
	    	case ADDR:						//Set value of specified register to other specified register
	    		int temp = 0;				//temporary value to hold inner storage variable
	    		
	    		switch(data) {
		    		case r0:
		    			temp = stor_0;		//set temp to hold register value
		    			break;
		    		case r1:
		    			temp = stor_1;
		    			break;
		    		case r2:
		    			temp = stor_2;
		    			break;
		    		case r3:
		    			temp = stor_3;
		    			break;
		    		case r4:
		    			temp = stor_4;
		    			break;
	    		}
	    		switch(memory[PC-2]) {
		    		case r0:
		    			stor_0 += temp;		//set value equal of register to value of other register
		    			break;
		    		case r1:
		    			stor_1 += temp;
		    			break;
		    		case r2:
		    			stor_2 += temp;
		    			break;
		    		case r3:
		    			stor_3 += temp;
		    			break;
		    		case r4:
		    			stor_4 += temp;
		    			break;
	    		}
	    		break;
	    	case LDI:						//Replace value of register with specified value                                                                                      
	    		switch(memory[PC-2]) {
		    		case r0:
		    			stor_0 = 0;			//First clear register
		    			stor_0 += data;		//Then set to specified value
		    			break;
		    		case r1:
		    			stor_1 = 0;
		    			stor_1 += data;
		    			break;
		    		case r2:
		    			stor_2 = 0;
		    			stor_2 += data;
		    			break;
		    		case r3:
		    			stor_3 = 0;
		    			stor_3 += data;
		    			break;
		    		case r4:
		    			stor_4 = 0;
		    			stor_4 += data;
		    			break;
	    		}
	    		break;
	    	case LDM:						//Replace register value with specified memory location value
	    		switch(memory[PC-2]) {
		    		case r0:
		    			stor_0 = 0;			//clear
		    			stor_0 += data;		//set	
		    			break;
		    		case r1:
		    			stor_1 = 0;
		    			stor_1 += data;
		    			break;
		    		case r2:
		    			stor_2 = 0;
		    			stor_2 += data;
		    			break;
		    		case r3:
		    			stor_3 = 0;
		    			stor_3 += data;
		    			break;
		    		case r4:
		    			stor_4 = 0;
		    			stor_4 += data;
		    			break;
	    		}
	    		break;
	    	case ST:							//Store specified register value in a memory location
	    		switch(memory[PC-1]) {
		    		case r0:
		    			memory[data] = stor_0;	//set memory specified location equal to register value
		    			break;
		    		case r1:
		    			memory[data] = stor_1;
		    			break;
		    		case r2:
		    			memory[data] = stor_2;
		    			break;
		    		case r3:
		    			memory[data] = stor_3;
		    			break;
		    		case r4:
		    			memory[data] = stor_4;
		    			break;
	    		}
	    		break;
	    	case DBG:							//Print out runtime values, start debug
	    		debug = true;
	    		break;
	    	case NODBG:							//End print, end debug
	    		debug = false;
	    		break;
	    	default:
	    		break;
    	}
       
    }
public static void main(String [] args){
	 int  m2[] = { 9,
             -5,
              CLR,    r0,  0,
              DBG,    0,   0,
              CLR,    r1,  0,
              ADDI,   r0,  17,
              ADDI,   r3,  2,
              ADDM,   r0,  0,
              NODBG,  0,   0,
              ADDM,   r3,  1,
              ST,     1,   r3,
              LDM,    r2,  1,
              CLR,    r4,  0,
              HALT,   0,   0
	 };
	 int m3[] = {
			 9, -5,
			 DBG, 0, 0,
			 CLR, r2, 0,
			 ADDI, r0, 3,
			 ADDI, r2, 9,
			 ADDR, r0, r2,
			 CLR, r2, 0,
			 ST, 1, r2,
			 ADDI, r2, 4,
			 CLR, r4, 0,
			 HALT, 0, 0
	 };
	 int m4[] = {
			 9, -5,
			 DBG, 0, 0,
			 ADDI, r3, 4,
			 LDI, r3, 0,
			 NODBG, 0, 0,
			 ADDM, r3, 9,
			 DBG, 0, 0,
			 LDM, r3, 0,
			 ADDI, r3, 9,
			 ADDR, r2, r3,
			 CLR, r3, 0,
			 HALT, 0, 0
	 };

	 /*
	  * Test Populations
	  * 
		 int m3[] = { 9, -5,
				 CLR, 0, 0,
				 DBG, 0, 0 ,
				 ADDI,   r0,  16,
	             ADDI,   r3, 17 ,
	             ADDM,   r0,  18,
	             ADDM,   r3,  20,
	             CLR,    r3, 0,
	             HALT,   0,   0
	             
		 };
		 int m4[] = {
				 9, -5, 
				 DBG, 0 , 0, 
				 ADDI, r0, 16,  
				 ADDI, r0, 16, 
				 ADDM, r0, 0, 
				 ADDM, r0, 0, 
				 HALT, 0 , 0 
		 };
		 int m5[] = {		//test for ADDI and ADDM
				 0, 9,
				 DBG, 0, 0 , 
				 ADDI, r1, 2,
				 ADDI, r3, 4, 
				 ADDM, r1, 1,
				 ADDM, r3, 4, 
				 CLR, 0 , 0,
				 HALT, 0 , 0
		 };
		 int m6[]= {		//test for ADDM and CLR
				 0, 9,
				 DBG, 0, 0,
				 ADDI, r3, 8,
				 ADDM, r1, 5,
				 CLR, r3, 0 ,
				 HALT, 0, 0,
		 };
		 int m7[] = {		//test for ST
				 0, 9,
				 DBG, 0, 0,
				 ADDI, r1, 2,
				 ADDI, r1 , 8,
				 ST, 7, r1,
				 ADDM, r2, 7,
				 HALT, 0, 0,
		 };
		 
		 int m8[] = {		//test for LDI and LDM
				 0, 9,
				 DBG, 0, 0,
				 ADDI, r0, 1,
				 LDI, r0, 9,
				 LDM, r0, 6,
				 HALT, 0, 0,
		 };
		 	 int[] m9 = {		//test for ADDR
			 	9, 5,
			 	DBG, 0, 0,
			 	ADDI, r1, 1,
			 	ADDI, r2, 1,
			 	ADDR, r1, r2,
			 	HALT, 0, 0,
	 	};	
	 	 	int[] m10 = {
			 	9, 5, 
			 	DBG, 0, 0, 
			 	ADDI, r3, 8, 
			 	ST, 1, r3, 
			 	LDI, r3, 8,
			 	ADDM, r3, 1,
			 	CLR, r4, 0,
			 	HALT, 0, 0
	 	};
	 	 	int m11[] = {		//test for ST
			 	0, 9,
			 	DBG, 0, 0,
			 	ADDI, r1, 2,
			 	ADDI, r1 , 8,
			 	ST, 7, r1,
			 	ADDM, r2, 7,
			 	HALT, 0, 0,
	 	};
	 */

System.out.println("*************************************************************************************************");
System.out.println("\nFirst Program: ");
interpret( m2, 2 );								//program 2
System.out.println("*************************************************************************************************");

System.out.println("\nSecond Program: ");
interpret( m3, 2 );								//program 2
System.out.println("*************************************************************************************************");

System.out.println("\nThird Program: ");
interpret( m4, 2 );								//program 2
System.out.println("*************************************************************************************************");
       }
}
//----------------------------------------------------------------------

/*
Begin Output

*************************************************************************************************

First Program: 
PC value: 8 | Register 0: 0 | Register 1: 90 | Register 2: 58 | Register 3: 92 | Register 4: 81
PC value: 11 | Register 0: 0 | Register 1: 0 | Register 2: 58 | Register 3: 92 | Register 4: 81
PC value: 14 | Register 0: 17 | Register 1: 0 | Register 2: 58 | Register 3: 92 | Register 4: 81
PC value: 17 | Register 0: 17 | Register 1: 0 | Register 2: 58 | Register 3: 94 | Register 4: 81
PC value: 20 | Register 0: 26 | Register 1: 0 | Register 2: 58 | Register 3: 94 | Register 4: 81

				------END DEBUG MODE-----

*************************************************************************************************

Second Program: 
PC value: 5 | Register 0: 26 | Register 1: 0 | Register 2: 89 | Register 3: 89 | Register 4: 0
PC value: 8 | Register 0: 26 | Register 1: 0 | Register 2: 0 | Register 3: 89 | Register 4: 0
PC value: 11 | Register 0: 29 | Register 1: 0 | Register 2: 0 | Register 3: 89 | Register 4: 0
PC value: 14 | Register 0: 29 | Register 1: 0 | Register 2: 9 | Register 3: 89 | Register 4: 0
PC value: 17 | Register 0: 38 | Register 1: 0 | Register 2: 9 | Register 3: 89 | Register 4: 0
PC value: 20 | Register 0: 38 | Register 1: 0 | Register 2: 0 | Register 3: 89 | Register 4: 0
PC value: 23 | Register 0: 38 | Register 1: 0 | Register 2: 0 | Register 3: 89 | Register 4: 0
PC value: 26 | Register 0: 38 | Register 1: 0 | Register 2: 4 | Register 3: 89 | Register 4: 0
PC value: 29 | Register 0: 38 | Register 1: 0 | Register 2: 4 | Register 3: 89 | Register 4: 0
PC value: 32 | Register 0: 38 | Register 1: 0 | Register 2: 4 | Register 3: 89 | Register 4: 0

				------END DEBUG MODE-----

*************************************************************************************************

Third Program: 
PC value: 5 | Register 0: 38 | Register 1: 0 | Register 2: 4 | Register 3: 89 | Register 4: 0
PC value: 8 | Register 0: 38 | Register 1: 0 | Register 2: 4 | Register 3: 93 | Register 4: 0
PC value: 11 | Register 0: 38 | Register 1: 0 | Register 2: 4 | Register 3: 0 | Register 4: 0
PC value: 20 | Register 0: 38 | Register 1: 0 | Register 2: 4 | Register 3: 14 | Register 4: 0
PC value: 23 | Register 0: 38 | Register 1: 0 | Register 2: 4 | Register 3: 9 | Register 4: 0
PC value: 26 | Register 0: 38 | Register 1: 0 | Register 2: 4 | Register 3: 18 | Register 4: 0
PC value: 29 | Register 0: 38 | Register 1: 0 | Register 2: 22 | Register 3: 18 | Register 4: 0
PC value: 32 | Register 0: 38 | Register 1: 0 | Register 2: 22 | Register 3: 0 | Register 4: 0
PC value: 35 | Register 0: 38 | Register 1: 0 | Register 2: 22 | Register 3: 0 | Register 4: 0

				------END DEBUG MODE-----

************************************************************************************************

End Output

 */