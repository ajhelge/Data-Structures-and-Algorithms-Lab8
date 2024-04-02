package lab108.helgesonaj;

import java.util.Scanner;

/**
 *
 * @author ajhel
 * This class simply accepts input from the user used to start other programs.
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = "";

        while(!input.equals("quit")){
            System.out.printf("Please input file directory:   ");
            input = sc.nextLine();
            Lab108.startProgram(input);
        }

        sc.close();

    }
    
}
