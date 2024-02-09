/* Eric Gutierrez 
 * COSC 3355.001
 * Purpose: 
 * 
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

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
            Scanner input = new Scanner(new File("input1-1.txt"));
            int i = 0;
            
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
              

               if(!line.isEmpty()){
                String paddedLine = String.format("%03d", Integer.parseInt(line));
                memory[i++] = Integer.parseInt(paddedLine);
               }
            //    After loading the instructions into memory
            // System.out.println("Initial memory content:");
            // for (int j = 0; j < i; j++) { // Assuming 'i' is the number of loaded instructions
            //     System.out.println("Memory[" + j + "]: " + memory[j]);
            // }
            
         
            
           
            }
            System.out.println("Initial PC: " + PC);
            System.out.println("Initial runBit: " + runBit);
            PC =0;
            while(runBit && PC < memory.length){
              System.out.println( "memory "+memory[PC]);
              instruction = memory[PC];
             // System.out.println("Current instruction:" +instruction);
              PC = PC +1;
              int opcode = getInstructionType(instruction);
              int operand1 = (instruction % 100)/10;
              int operand2 = instruction % 10;
              System.out.println(opcode + " " + operand1 + " " + operand2);

       
              executeInstruction(opcode, operand1, operand2);
          

             
              instructionCounter++;
            }
            output();
            System.out.println("Final PC: " + PC + ", runBit: " + runBit);

            //Close file scanner 
            input.close();
        } catch (FileNotFoundException e) {
            
            e.printStackTrace();
        }      
      
    }
    private static int getInstructionType(int instruction ){
        int opcode = instruction / 100;
        return opcode;


    }
    private static void  executeInstruction (int opcode, int operand1, int operand2){
      

        switch (opcode) {
            // Halt 
            case 1:
                System.out.println("Halting execution at PC=" + (PC - 1));

                runBit = false;
                break;
            // Set register "operand1" to the value of "operand2"
            case 2: 
                System.out.println("Executing opcode " + opcode + " with operands " + operand1 + ", " + operand2);

                registers[operand1] = operand2;
                break;
            // Multiply register "operand1" by the value "operand2"
            case 3:
                System.out.println("Executing opcode " + opcode + " with operands " + operand1 + ", " + operand2);

                registers[operand1] = (registers[operand1] * operand2) % 1000 ;
                break;
            // Add the value of "operand2" to the value in register "operand1" 
            case 4:
                System.out.println("Executing opcode " + opcode + " with operands " + operand1 + ", " + operand2);

                registers[operand1] = (registers[operand1] + operand2) % 1000 ;
                break;
            // Set register "operand1" to the value in register "operand2"
            case 5:
                System.out.println("Executing opcode " + opcode + " with operands " + operand1 + ", " + operand2);
 
                registers[operand1] = registers[operand2];
                break;
            // Multiply register "operand1" by the value in register "operand2"
            case 6:
                 System.out.println("Executing opcode " + opcode + " with operands " + operand1 + ", " + operand2);

                 registers[operand1] = (registers[operand1] * registers[operand2]) % 1000;
                 break;
            // Add the value in register "operand2" to register "operand1"
            case 7:
                System.out.println("Executing opcode " + opcode + " with operands " + operand1 + ", " + operand2);

                registers[operand1] = (registers[operand1] + registers[operand2])  % 1000;
                break;
            // Set register "operand1" to the value in RAM whose address is in register "operand2"
            case 8:
                System.out.println("Executing opcode " + opcode + " with operands " + operand1 + ", " + operand2);

                registers[operand1] = memory[registers[operand2]];
                break;
            // Set the value in RAM whose address is in register "operand2" to that of register "operand1"
            case 9:
               System.out.println("Executing opcode " + opcode + " with operands " + operand1 + ", " + operand2);
 
                memory[registers[operand2]] = registers[operand1];
                break;
            // Go to the location in register "operand1" unless register "operand2" contains 0 
            case 0:
                System.out.println("Executing opcode " + opcode + " with operands " + operand1 + ", " + operand2);
            
                if(registers[operand2] != 0){
                    PC = registers[operand1];
                  
                  
                }
                break;
            default:
                System.out.println("Opcode not found"+ opcode); 
                break;
        }

    }

    public static void output(){
        for (int i = 0; i < registers.length; i++){
            System.out.println("Register "+ i + ": " + registers[i]);
        }
        System.out.println("Number of instructions executed: " + instructionCounter);
    }
  
}