package lab108.helgesonaj;

import java.io.File;
import java.util.Scanner;

/**
 * A class for Lab108 that contains any code specific to the lab.
 */
public class Lab108 {

    public static void startProgram(String filePath){

        try{
            if(filePath.matches("([a-zA-Z]:(\\\\([\\w\\d\\. _-]{1,})){1,}\\.txt){1,255}")){
                File inputFile = new File(filePath);
                Scanner fileScanner = new Scanner(inputFile);
                while(fileScanner.hasNextLine()){
                    toPostFix(fileScanner.nextLine());
                }
                fileScanner.close();
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("unspecified error in treebuilder...");
        }

    }

    public static void toPostFix(String infix){
        ArrayQueue<String> newInfix= helperMethods.tokenize(infix);
        ArrayStack<String> opStack = new ArrayStack<>();
        ArrayQueue<String> postFix = new ArrayQueue<>();

        while(!newInfix.isEmpty()){
            System.out.print(newInfix.dequeue());
        }
        System.out.print("\n");

    }

}
