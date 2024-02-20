/* Eric Gutierrez 
 * COSC 3355.001
 * 2/20/2024
 * Assignment 1 
 * Purpose: To simulate how a computer might interpret and execute Instructions (in this case 3-digit integers),and
 * perform operations that a computer might do such as arithmetric operations, memory management between the register (1-9).
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The Interpret Class simulates a computer system execution of instructions, using an input file.
 * The final output shows the final state of the register along with the counter of the executed instructions 
 */
class Interpret{
    //Program counter holds address of next instr 
    static int PC = 0;
    //10 registers 
    static int[] registers = new int[10];
    //A holding register for the current instruction 
    static int instruction;
    //Instruction type (opcode)
    static int instructionType;
    //Memory for storing operations 
    static int [] memory= new int [1000];
    //A bit that can be turned off to halt the machine 
    static boolean runBit = true;
    //Counter for instructions executed 
    static int instructionCounter;

    public static void main(String[] args) {
       
        try {
            //Create a scanner object to read from a file 
            // Scanner input = new Scanner(new File("input1-1.txt"));
            Scanner input = new Scanner(new File("input2-1.txt"));
            int i = 0;
            
            //Read from file 
            while (input.hasNextLine()) {
                //Remove leading spaces 
                String line = input.nextLine().trim();
              
                //Read in values of the input file into memory array , if 3-digits are not read in
                //then it would "pad" 0's until it meets the condition (3-digit)
               if(!line.isEmpty()){
                String paddedLine = String.format("%03d", Integer.parseInt(line));
                memory[i++] = Integer.parseInt(paddedLine);
               }
        
            }

            while(runBit){

              instruction = memory[PC];
              PC = PC +1;
              //Extracts the opcode
              int opcode = getInstructionType(instruction);
              //Extracts the first operand 
              int operand1 = (instruction % 100)/10;
              //Extracts the second operand 
              int operand2 = instruction % 10;

              executeInstruction(opcode, operand1, operand2);
          
              instructionCounter++;
            }

            output();

            //Close file scanner 
            input.close();
        } catch (FileNotFoundException e) {
            
            e.printStackTrace();
        }      
      
    }

    /**
     * Determines the opcode of the given instruction. 
     * 
     * @param instruction The instruction , which to extract the opcode. 
     * return The opcode of the instruction 
     */
    private static int getInstructionType(int instruction ){
        int opcode = instruction / 100;
        return opcode;
    }

    /**
     * Executes a single instruction based on opcode and the operands 
     * 
     * @param opcode The operation code indicating instruction type 
     * @param operand1 The first operand 
     * @param operand2 The second operand 
     */
    private static void  executeInstruction (int opcode, int operand1, int operand2){
    
        switch (opcode) {
            // Halt 
            case 1:
                runBit = false;
                break;
            // Set register "operand1" to the value of "operand2"
            case 2: 
                registers[operand1] = operand2;
                break;
            // Multiply register "operand1" by the value "operand2"
            case 3:
                registers[operand1] = (registers[operand1] * operand2) % 1000 ;
                break;
            // Add the value of "operand2" to the value in register "operand1" 
            case 4:
                registers[operand1] = (registers[operand1] + operand2) % 1000 ;
                break;
            // Set register "operand1" to the value in register "operand2"
            case 5:
                registers[operand1] = registers[operand2];
                break;
            // Multiply register "operand1" by the value in register "operand2"
            case 6:
                 registers[operand1] = (registers[operand1] * registers[operand2]) % 1000;
                 break;
            // Add the value in register "operand2" to register "operand1"
            case 7:
                registers[operand1] = (registers[operand1] + registers[operand2])  % 1000;
                break;
            // Set register "operand1" to the value in RAM whose address is in register "operand2"
            case 8:
                registers[operand1] = memory[registers[operand2]];
                break;
            // Set the value in RAM whose address is in register "operand2" to that of register "operand1"
            case 9:
                memory[registers[operand2]] = registers[operand1];
                break;
            // Go to the location in register "operand1" unless register "operand2" contains 0 
            case 0:
                if(registers[operand2] != 0){
                    PC = registers[operand1];
                }
                break;
            default:
                System.out.println("Opcode not found"+ opcode); 
                break;
        }

    }

    /**
     * Outputs the final state of the registers and the total number of instruction executed 
     */
    public static void output(){
        for (int i = 0; i < registers.length; i++){
            System.out.println("Register "+ i + ": " + registers[i]);
        }
        System.out.println("Number of instructions executed: " + instructionCounter);
    }
  
}