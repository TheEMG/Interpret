import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Interpret{
    //Program counter holds address of next instr 
    static int PC = 000;
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
               String tempProcessData = input.nextLine().trim();
               if(!tempProcessData.isEmpty()){
                memory[i++] = Integer.parseInt(tempProcessData);
               }
              while(runBit){
                instruction = memory[PC];
                PC = PC +1;
                int opcode = getInstructionType(instruction);
                int operand1 = (instruction % 100)/10;
                int operand2 = instruction % 10;

                executeInstruction(opcode, operand1, operand2);


              }
            

               




                
            }
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
    private static void  int executeInstruction (int opcode, int operand1, int operand2){
        switch (opcode) {
            case 1:
            
                break;
        
            default:
                break;
        }

    }
  
}