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
                    String infix = fileScanner.nextLine();
                    ArrayQueue<String> postFix = toPostFix(infix);
                    ArrayQueue<String> postFixCopy = null;
                    if(postFix != null){
                        postFixCopy = postFix.copy();
                    }
                    Double answer = evaluate(postFixCopy);
                    printOutput(infix, postFix, answer);
                    
                }
                fileScanner.close();
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("unspecified error in startProgram...");
        }

    }

    private static ArrayQueue<String> toPostFix(String infix){
        ArrayQueue<String> newInfix = tokenize(infix);
        ArrayStack<String> opStack = new ArrayStack<>(newInfix.size());
        ArrayQueue<String> postFix = new ArrayQueue<>(newInfix.size());

        String currentToken = "";
        while(!newInfix.isEmpty()){
            currentToken = newInfix.dequeue();
            if(isLeftBracket(currentToken)){ //case 1
                opStack.push(currentToken);
            }
            else if(isRightBracket(currentToken)){//case 2
                while(isOperator(opStack.top())){
                    postFix.enqueue(opStack.pop()); //moves all operators from opStack to postFix until a bracket is reached
                }
                if(!bracketsMatch(opStack.pop(), currentToken)){
                    return null; //if the brackets don't match then invalid expression
                }
            }
            else if(isOperator(currentToken)){//case 3
                if(opStack.isEmpty() || isLeftBracket(opStack.top())){
                    opStack.push(currentToken);
                }
                else{
                    while(precedence(currentToken) < precedence(opStack.top())){
                        postFix.enqueue(opStack.pop());
                    }
                    opStack.push(currentToken);
                }
            }
            else if(isNumber(currentToken)){//case 4
                postFix.enqueue(currentToken);
            }
        }
        while(!opStack.isEmpty()){
            String temp = opStack.pop();
            if(isLeftBracket(temp)){
                return null; // there should be no brackets left in stack, if there are then invalid expression
            }
            postFix.enqueue(temp);
        }
        return postFix;
    }

    private static Double evaluate(ArrayQueue<String> postFix){
        if(postFix == null){
            return 0.0;
        }
        ArrayStack<String> numStack = new ArrayStack<>(postFix.size());
        Double answer = 0.0;
        while(!postFix.isEmpty()){
            if(isNumber(postFix.first())){
                numStack.push(postFix.dequeue());
            }
            else if(isOperator(postFix.first())){
                Double operandTwo = Double.parseDouble(numStack.pop());
                Double operandOne = Double.parseDouble(numStack.pop());
                if(postFix.first().equals("*")){
                    answer = operandOne * operandTwo;
                }
                else if(postFix.first().equals("/")){
                    answer = operandOne / operandTwo;
                }
                else if(postFix.first().equals("+")){
                    answer = operandOne + operandTwo;
                }
                else if(postFix.first().equals("-")){
                    answer = operandOne - operandTwo;
                }
                numStack.push(answer.toString());
                postFix.dequeue();
            }
        }
        return Double.parseDouble(numStack.pop());
    }

    private static void postfixToBinaryTree(ArrayQueue<String> postFix){
        ArrayStack<LinkedBinaryTree<String>> treeNodesStack = new ArrayStack<>(postFix.size()); //to store new node to be linked together later
        ArrayStack<String> numberStack = new ArrayStack<>(postFix.size());
        //First create individual tree nodes
        while(!postFix.isEmpty()){
            if(isNumber(postFix.first())){
                numberStack.push(postFix.dequeue()); //if number add to number stack
            }
            else{
                LinkedBinaryTree<String> newNode = new LinkedBinaryTree<>();
                newNode.addRoot(postFix.dequeue()); //operator is always root
                if(numberStack.top() != null){
                    newNode.addRight(newNode.root, numberStack.pop());
                }
                if(numberStack.top() != null){
                    newNode.addLeft(newNode.root, numberStack.pop());
                }

                treeNodesStack.push(newNode);
            }
        }
        // TODO Now attach all tree nodes. Should be a separate method.
        while(!treeNodesStack.isEmpty()){
            LinkedBinaryTree<String> root = treeNodesStack.pop();
            LinkedBinaryTree<String> right = treeNodesStack.pop();
            LinkedBinaryTree<String> left = treeNodesStack.pop();
            root.attach(root.root, left, right);
        }

    }

    private static boolean isOperator(String token){
        if(token.matches("[+\\/\\*-]")){
            return true;
        }
        return false;
    }

    private static boolean isNumber(String token){
        if(token.matches("-?(\\d\\.?){1,}")){
            return true;
        }
        return false;
    }

    private static boolean isLeftBracket(String token){
        if(token.matches("[\\[\\{\\(]")){
            return true;
        }
        return false;
    }

    private static boolean isRightBracket(String token){
        if(token.matches("[\\]\\}\\)]")){
            return true;
        }
        return false;
    }

    private static int precedence(String Operator){
        switch(Operator){
            case "+" : 
            case "-" : 
                return 0;
            case "/" : 
            case "*" : 
                return 1;
        default : 
            throw new IllegalArgumentException("invalid operator");
        }
    }

    private static boolean bracketsMatch(String left, String right){
        if(left.equals("(") && right.equals(")")){
            return true;
        }
        else if(left.equals("{") && right.equals("}")){
            return true;
        }
        else if(left.equals("[") && right.equals("]")){
            return true;
        }
        return false;

    }

    private static ArrayQueue<String> tokenize(String infix){
        String temp[] = infix.split("[ \\n\\t]+");
        ArrayQueue<String> tokenInfix = new ArrayQueue<>(temp.length);
        for (String string : temp) {
            if(string != null){
                tokenInfix.enqueue(string);
            }
        }
        return tokenInfix;
    }

    private static void printOutput(String infix, ArrayQueue<String> postFix, Double answer){
        if(postFix == null){
            System.out.printf("%-40s %-40s\n", infix, "Invalid Expression");
        }
        else{
            String postFixString = "";
            while(!postFix.isEmpty()){
                postFixString += postFix.dequeue();
            }
            System.out.printf("%-40s %-40s %-10f\n", infix, postFixString, answer);
        }
    }

}
