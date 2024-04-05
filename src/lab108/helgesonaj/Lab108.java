package lab108.helgesonaj;

import java.io.File;
import java.util.Scanner;

/**
 * A class for Lab108 that contains any code specific to the lab.
 */
public class Lab108 {
    /**
     * Is essentially the main for Lab108
     * 
     * @param filePath Absolute path of file
     */
    public static void startProgram(String filePath) {

        try {
            if (filePath.matches("([a-zA-Z]:(\\\\([\\w\\d\\. _-]{1,})){1,}\\.txt){1,255}")) {// check for correct
                                                                                             // directory format
                File inputFile = new File(filePath);
                Scanner fileScanner = new Scanner(inputFile);
                while (fileScanner.hasNextLine()) {
                    String infix = fileScanner.nextLine();
                    ArrayQueue<String> postFix = toPostFix(infix);
                    ArrayQueue<String> postFixCopy = null;// create copys to prevent destruction of original postfix
                                                          // expression
                    ArrayQueue<String> postFixCopy2 = null;
                    if (postFix != null) {
                        postFixCopy = postFix.copy();
                        postFixCopy2 = postFix.copy();
                    }
                    Double answer = evaluate(postFixCopy);
                    printOutput(infix, postFix, answer);
                    LinkedBinaryTree<String> Tree = postfixToBinaryTree(postFixCopy2);
                    printTraversals(Tree);
                }
                fileScanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("unspecified error in startProgram...");
        }

    }

    /**
     * Converts a mathmatical expression from human readable to
     * something more managable for a computer using the shunting yard algorithm.
     * 
     * @param infix Space delimited human readable expression.
     * @return The postFix expression
     */
    private static ArrayQueue<String> toPostFix(String infix) {
        ArrayQueue<String> newInfix = tokenize(infix);
        ArrayStack<String> opStack = new ArrayStack<>(newInfix.size());
        ArrayQueue<String> postFix = new ArrayQueue<>(newInfix.size());

        String currentToken = "";
        while (!newInfix.isEmpty()) {
            currentToken = newInfix.dequeue();
            if (isLeftBracket(currentToken)) { // case 1
                opStack.push(currentToken);
            } else if (isRightBracket(currentToken)) {// case 2
                while (isOperator(opStack.top())) {
                    postFix.enqueue(opStack.pop()); // moves all operators from opStack to postFix until a bracket is
                                                    // reached
                }
                if (!bracketsMatch(opStack.pop(), currentToken)) {
                    return null; // if the brackets don't match then invalid expression
                }
            } else if (isOperator(currentToken)) {// case 3
                if (opStack.isEmpty() || isLeftBracket(opStack.top())) {
                    opStack.push(currentToken);
                } else {
                    while (precedence(currentToken) < precedence(opStack.top())) {
                        postFix.enqueue(opStack.pop());
                    }
                    opStack.push(currentToken);
                }
            } else if (isNumber(currentToken)) {// case 4
                postFix.enqueue(currentToken);
            }
        }
        while (!opStack.isEmpty()) {
            String temp = opStack.pop();
            if (isLeftBracket(temp)) {
                return null; // there should be no brackets left in stack, if there are then invalid
                             // expression, return null
            }
            postFix.enqueue(temp);
        }
        return postFix;
    }

    /**
     * Evaluates a given postfix expression (ex. 66* = 36)
     * 
     * @param postFix
     * @return The solution of the given expression
     */
    private static Double evaluate(ArrayQueue<String> postFix) {
        if (postFix == null) {
            return 0.0;
        }
        ArrayStack<String> numStack = new ArrayStack<>(postFix.size());
        Double answer = 0.0;
        while (!postFix.isEmpty()) {
            if (isNumber(postFix.first())) {
                numStack.push(postFix.dequeue());
            } else if (isOperator(postFix.first())) {
                Double operandTwo = Double.parseDouble(numStack.pop());// pop two operands off the stack.
                Double operandOne = Double.parseDouble(numStack.pop());
                if (postFix.first().equals("*")) {
                    answer = operandOne * operandTwo;
                } else if (postFix.first().equals("/")) {
                    answer = operandOne / operandTwo;
                } else if (postFix.first().equals("+")) {
                    answer = operandOne + operandTwo;
                } else if (postFix.first().equals("-")) {
                    answer = operandOne - operandTwo;
                }
                numStack.push(answer.toString());// put answer back on stack.
                postFix.dequeue();
            }
        }
        return Double.parseDouble(numStack.pop());
    }

    /**
     * Converts a given postFix expression into a binary tree
     * 
     * @param postFix
     * @return The root of the binary tree
     */
    private static LinkedBinaryTree<String> postfixToBinaryTree(ArrayQueue<String> postFix) {
        if (postFix == null) {
            return null;
        }
        LinkedBinaryTree<String> returnTree = null;
        ArrayQueue<LinkedBinaryTree<String>> nodeQueue = new ArrayQueue<>(postFix.size());
        ArrayStack<LinkedBinaryTree<String>> numStack = new ArrayStack<>(postFix.size());
        while (!postFix.isEmpty()) {// First turn postFix numbers into tree nodes
            LinkedBinaryTree<String> newNode = new LinkedBinaryTree<>();
            newNode.addRoot(postFix.dequeue());
            nodeQueue.enqueue(newNode);
        }
        while (!nodeQueue.isEmpty()) {// Second evaluate the nodes and connect them.
            LinkedBinaryTree<String> current = nodeQueue.dequeue();
            if (isNumber(current.root().getElement())) {
                numStack.push(current);
            } else if (isOperator(current.root().getElement())) {
                LinkedBinaryTree<String> right = numStack.pop();
                LinkedBinaryTree<String> left = numStack.pop();
                current.attach(current.root(), left, right);
                numStack.push(current);
            }
            returnTree = current;
        }
        return returnTree;
    }

    /**
     * Prints various different traversals of the given binary tree.
     * 
     * @param Tree
     */
    private static void printTraversals(LinkedBinaryTree<String> Tree) {
        if (Tree == null) {
            return;
        }
        System.out.printf("Preorder: ");
        for (Position<String> p : Tree.preorder()) {
            System.out.printf(p.getElement());
        }

        System.out.printf("\nPostorder: ");
        for (Position<String> p : Tree.postorder()) {
            System.out.printf(p.getElement());
        }

        System.out.printf("\nInorder: ");
        for (Position<String> p : Tree.inorder()) {
            System.out.printf(p.getElement());
        }

        System.out.printf("\nBreadth First: ");
        for (Position<String> p : Tree.breadthfirst()) {
            System.out.printf(p.getElement());
        }

        System.out.print("\nEuler's Tour: ");
        Euler(Tree, Tree.root());

    }

    /**
     * Euler's tour of a binary tree.
     * 
     * @param t
     * @param p
     */
    public static void Euler(LinkedBinaryTree<String> t, Position<String> p) {
        if (t.isInternal(p) && !t.isRoot(p)) {
            System.out.print("(");
        }
        if (t.left(p) != null) {
            Euler(t, t.left(p));
        }
        System.out.print(p.getElement());
        if (t.right(p) != null) {
            Euler(t, t.right(p));
        }
        if (t.isInternal(p) && !t.isRoot(p)) {
            System.out.print(")");
        }
    }

    /**
     * Helper funtion of for evaluation of expressions
     * Accepts a string of a single character.
     * 
     * @param token
     * @return
     */
    private static boolean isOperator(String token) {
        if (token.matches("[+\\/\\*-]")) {
            return true;
        }
        return false;
    }

    /**
     * Helper funtion of for evaluation of expressions
     * Accepts a string of a single character.
     * 
     * @param token
     * @return
     */
    private static boolean isNumber(String token) {
        if (token.matches("-?(\\d\\.?){1,}")) {
            return true;
        }
        return false;
    }

    /**
     * Helper funtion of for evaluation of expressions
     * Accepts a string of a single character.
     * 
     * @param token
     * @return
     */
    private static boolean isLeftBracket(String token) {
        if (token.matches("[\\[\\{\\(]")) {
            return true;
        }
        return false;
    }

    /**
     * Helper funtion of for evaluation of expressions
     * Accepts a string of a single character.
     * 
     * @param token
     * @return
     */
    private static boolean isRightBracket(String token) {
        if (token.matches("[\\]\\}\\)]")) {
            return true;
        }
        return false;
    }

    /**
     * Helper funtion of for evaluation of expressions
     * Accepts a string of a single character.
     * 
     * @param token
     * @return
     */
    private static int precedence(String Operator) {
        switch (Operator) {
            case "+":
            case "-":
                return 0;
            case "/":
            case "*":
                return 1;
            default:
                throw new IllegalArgumentException("invalid operator");
        }
    }

    /**
     * Used during the analysis of expressions to determine valid nesting.
     * 
     * @param left
     * @param right
     * @return
     */
    private static boolean bracketsMatch(String left, String right) {
        if (left.equals("(") && right.equals(")")) {
            return true;
        } else if (left.equals("{") && right.equals("}")) {
            return true;
        } else if (left.equals("[") && right.equals("]")) {
            return true;
        }
        return false;

    }

    /**
     * To tokenize input expressions from a file.
     * 
     * @param infix
     * @return
     */
    private static ArrayQueue<String> tokenize(String infix) {
        String temp[] = infix.split("[ \\n\\t]+");
        ArrayQueue<String> tokenInfix = new ArrayQueue<>(temp.length);
        for (String string : temp) {
            if (string != null) {
                tokenInfix.enqueue(string);
            }
        }
        return tokenInfix;
    }

    /**
     * Simply used to print expressoins, their postFix, and their solutions.
     * 
     * @param infix
     * @param postFix
     * @param answer
     */
    private static void printOutput(String infix, ArrayQueue<String> postFix, Double answer) {
        if (postFix == null) {
            System.out.printf("\n\nExpression: %-40s %-20s", infix, "Invalid Expression");
        } else {
            String postFixString = "";
            while (!postFix.isEmpty()) {
                postFixString += postFix.dequeue();
            }
            System.out.printf("\n\nExpression: %-40s PostFix: %-20s Solution: %-10f\n", infix, postFixString, answer);
        }
    }

}
