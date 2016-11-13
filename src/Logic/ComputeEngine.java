package Logic;

import java.math.BigInteger;
import java.util.*;
import java.util.HashMap;

/**
 * Created by BradWilliams on 10/8/16.
 * Class to perform conversion from infix to post fix. Evaluates converted expression
 */
public abstract class ComputeEngine {


    private static HashMap<String, Integer> ops = new HashMap<String, Integer>() {{
        put("+", 1);
        put("-", 1);
        put("*", 2);
        put("/", 2);
        put("%", 2);
    }};

    // ------- SHUNTING YARD
    public static String infixToPostfix(String infix){
        String output = "";
        List stack = new ArrayList();

        for (String token : infix.split("\\s")) {
            // operator
            if (ops.containsKey(token)) {

                while (!stack.isEmpty() && ops.get(token) <= ops.get(stack.get(0))) { // if operator has same precedence or less than element 0 in stack

                    output += stack.get(0) + " "; //push operator to output
                    stack.remove(0); //remove from stack
                }

                stack.add(0, token);// otherwise add operator to stack
            }

            // left parenthesis
            else if (token.equals("(")) {
                stack.add(0, token); // add to stack
            }

            // right parenthesis
            else if (token.equals(")")) {
                while (!stack.get(0).equals("(")) { //while stack isn't left parenthesis

                    output += stack.get(0) + " ";//push operator to output
                    stack.remove(0); //remove from stack
                }

                stack.remove(0); //discard right parenthesis
            }

            // digit
            else {
                output += token + " "; // add all digits to output
            }

        }

        while (!stack.isEmpty()) {

            output += stack.get(0) + " "; //add rest of operators to output
            stack.remove(0); //remove from stack
        }

        return output;
    }

    public static String compute(String expression){

        String postfix = ComputeEngine.infixToPostfix(expression);//allows outside classes to only call compute()
        int operand1; //first operand
        int operand2; //second operand
        //Note: operands are computed operand2 -> operator -> operand1
        int result; // hold temporary result of each algorithm iteration
        int[] stackTemp; //holds return values from stackPop subroutine

        Stack<Integer> stack = new Stack(); // Digits are pushed and pulled from stack to be computed


        for(String token : postfix.split("\\s")){
            boolean isInt = true; //flag for integer validation
            int integerToken = 0; //holds parsed int

            try{
                //integerToken = Integer.parseInt(token); //parse String to Int
                integerToken = parseIntWithOverflow(token);
            }
            catch (NumberFormatException e){
                isInt = false; //is parse failed, token is a string
            }
            catch (OutOfBoundsException oe){
                return "Input too big to calculate!";
            }

            if(isInt){
                stack.push(integerToken); // if token was integer, add it to the stack
            }

            // if token is string (+,-,/,*,%)
            switch (token){
                case "+":
                    stackTemp = stackPop(stack);

                    operand1 = stackTemp[0];
                    operand2 = stackTemp[1];

                    if(operand1 == -1 || operand2 == -1){
                        return "Invalid Expression";
                    }

                    //result = operand2 + operand1;
                    try {
                        result = Math.addExact(operand2, operand1);
                    }
                    catch (ArithmeticException e){
                        return "Integer Overflow!";
                    }

                    stack.push(result);
                    break;
                case "-":
                    stackTemp = stackPop(stack);

                    operand1 = stackTemp[0];
                    operand2 = stackTemp[1];

                    if(operand1 == -1 || operand2 == -1){
                        return "Invalid Expression";
                    }

                    //result = operand2 - operand1;
                    try {
                        result = Math.subtractExact(operand2,operand1);
                    }
                    catch (ArithmeticException e){
                        return "Integer Overflow!";
                    }

                    stack.push(result);
                    break;
                case "/":
                    stackTemp = stackPop(stack);

                    operand1 = stackTemp[0];
                    operand2 = stackTemp[1];

                    if(operand1 == -1 || operand2 == -1){
                        return "Invalid Expression";
                    }

                    // handle divide b zero
                    if(operand1 == 0){
                        return new String("Undefined");
                    }

                    result = operand2 / operand1;
                    stack.push(result);
                    break;
                case "*":
                    stackTemp = stackPop(stack);

                    operand1 = stackTemp[0];
                    operand2 = stackTemp[1];

                    if(operand1 == -1 || operand2 == -1){
                        return "Invalid Expression";
                    }

                    //result = operand2 * operand1;

                    try {
                        result = Math.multiplyExact(operand2,operand1);
                    }
                    catch (ArithmeticException e){
                        return "Integer Overflow!";
                    }
                    stack.push(result);
                    break;
                case "%":
                    stackTemp = stackPop(stack);

                    operand1 = stackTemp[0];
                    operand2 = stackTemp[1];

                    if(operand1 == -1 || operand2 == -1){
                        return "Invalid Expression";
                    }

                    result = operand2 % operand1;
                    stack.push(result);
                    break;
            }
        }


        try {
            return Integer.toString(stack.pop()); //convert final result back to string
        }
        catch (EmptyStackException e){
            return "Invalid Expression";
        }
    }

    private static int[] stackPop(Stack stack){
        int[] results = new int[2];

        try{
            results[0] = (int) stack.pop();
        }
        catch(EmptyStackException e){
            results[0] = -1;
            results[1] = -1;
        }

        try{
            results[1] = (int) stack.pop();
        }
        catch(EmptyStackException e){
            results[0] = -1;
            results[1] = -1;
        }

        return results;
    }

    private static class OutOfBoundsException extends Exception {
        //Parameterless Constructor
        public OutOfBoundsException(){}

        //Constructor that accepts a message
        public OutOfBoundsException(String message)
        {
            super(message);
        }
    }

    public static int parseIntWithOverflow(String s) throws NumberFormatException, OutOfBoundsException {

        int result = 0;
        try {
            result = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            try {
                new BigInteger(s);
            } catch (NumberFormatException e1) {
                throw e; // re-throw
            }
            // If s is a valid integer that's outside
            // of integer range
            throw new OutOfBoundsException("Input is outside of Integer range!");
        }
        // the input parsed no problem
        return result;
    }

    //  ------------------     TEST

    public static void main(String args[]) {

        String infix = "5 + 3 - 10 / 4 * 10 % 9";
        int result = 5 + 3 - 10 / 4 * 10 % 9;

        System.out.println(infix + " = " + result);
        System.out.println();
        System.out.println("Converted: " + ComputeEngine.infixToPostfix(infix) + " = " + ComputeEngine.compute(infix));

    }
}
