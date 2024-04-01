package lab108.helgesonaj;

import java.io.File;
import java.util.Scanner;

/**
 * A class for Lab108 that contains any code specific to the lab.
 */
public class Lab108 {

    public static void treeBuilder(String filePath){

        try{
            if(filePath.matches("([a-zA-Z]:(\\\\([\\w\\d\\. _-]{1,})){1,}\\.txt){1,255}")){
                File inputFile = new File(filePath);
                Scanner fileScanner = new Scanner(inputFile);
                while(fileScanner.hasNextLine()){
                    System.out.println(fileScanner.nextLine());
                }
                fileScanner.close();
            }
            else{
                System.out.println("invalid input, genius...");
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("unspecified error in treebuilder...");
        }

    }

    public static void toPostFix(String infix){
        
    }

}
