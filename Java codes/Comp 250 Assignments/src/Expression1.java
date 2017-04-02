import java.io.*;
import java.util.*;

public class Expression1 {
	//method to calculate an expression in reverse polish notation
	public static int evalRPN(LinkedList<String> queue) {
		String operators = "+-*%";
		LinkedList<String> outcomeQueue = new LinkedList<String>();
		for (String t : queue) {
			if (!operators.contains(t)) { //push to stack if it is a number
				 outcomeQueue.addLast(t);
			}
			else {//pop numbers from stack if it is an operator
					int a = Integer.valueOf(outcomeQueue.remove());
					int b = Integer.valueOf(outcomeQueue.remove());
					switch (t) {
					case "+":
						outcomeQueue.addFirst(String.valueOf(a+b));
						
						break;
					case "-":
						outcomeQueue.addFirst(String.valueOf(a-b));
						
						break;
					case "*":
						outcomeQueue.addFirst(String.valueOf(a*b));
							
						break;
					case "%":
						outcomeQueue.addFirst(String.valueOf(a/b));
						
						break;
					}
				}
			}
 
		return Integer.valueOf(outcomeQueue.remove());
 
		
	}
	static String delimiters="+-*%()";
	
	
	
	static Integer evaluate(String expr) throws Exception {  
		//using LinkedLists in this case because easier to access elements
		//first list for what goes into method call after everything has been evaluated
		//second list for special case operands, operands that don't have parentheses in them
		//third list is for the operators in the expression, including parentheses
	    LinkedList<String> outputQueue= new LinkedList<String>();
	    LinkedList<String> operandQueue= new LinkedList<String>();
        LinkedList<String> operatorQueue = new LinkedList<String>();
        int numOfElements=0;
        int index=0;
        int numOfLeftParenth=0;
        int numOfRightParenth=0;
        StringTokenizer expression = new StringTokenizer(expr, delimiters, true);
        //putting into list to create data for the expression, easier to make use of than relying on nextToken
        List<String> expressionList= new ArrayList<String>();
        do{
        	expressionList.add(expression.nextToken());
        } while(expression.hasMoreTokens());
        //accessing each element through a for loop
	    for(int j=0; j<expressionList.size();j++){
            //switch cases for operators
        	switch(expressionList.get(j)){
        		case "+":
        			operatorQueue.add("+");

        			break;
        		case "-":
        			operatorQueue.add("-");
        			break;
        		case "*":
        			operatorQueue.add("*");
        			break;
        		case "%":
        			operatorQueue.add("%");
        			break;
        		case "(":
        			operatorQueue.add("(");
        			numOfLeftParenth++;
        			break;
        		case ")":
        			numOfRightParenth++;
        			//throws exception if different number of ( and ) in an expression
        			if (numOfRightParenth>numOfLeftParenth) throw new Exception ("Malformed Expression");
        			while( operatorQueue.getLast()!=("(")){ 
        				//adds the operator after the elements in the list at a certain index depending on where the operator is in the expression
        				while(outputQueue.size()-1!=index){
        					index++;
        					}
        				outputQueue.add(index+1, operatorQueue.removeLast());
        				}
        			//this executes after we reach the case where the last element is "("
        			//we then remove it without adding it anywhere, since we don't care about parentheses in the actual calculations
        			if(!operatorQueue.isEmpty())operatorQueue.removeLast();
        			break;
        		default:
        			//case for a number
        			if(!operatorQueue.contains(")")&&!operatorQueue.contains("(")&&((!outputQueue.contains("+"))&&(!outputQueue.contains("-"))&&(!outputQueue.contains("%"))&&(!outputQueue.contains("*")))){
        				operandQueue.addLast(expressionList.get(j));
        			}
        			//else if statements have to do with where operands go depending on if there are parentheses in the operatorQueue, basically adds higher priority on them
        			else if(!operatorQueue.contains(")")&&operatorQueue.contains("(")&&((!outputQueue.contains("+"))&&(!outputQueue.contains("-"))&&(!outputQueue.contains("%"))&&(!outputQueue.contains("*")))) outputQueue.addLast(expressionList.get(j));
        			else if(operatorQueue.contains("(")&&((outputQueue.contains("+"))||(outputQueue.contains("-"))||(outputQueue.contains("%"))||(outputQueue.contains("*")))){
        				while((outputQueue.size()-1>=numOfElements)&&((outputQueue.get(numOfElements)!="+"||outputQueue.get(numOfElements)!="-"||outputQueue.get(numOfElements)!="%"||outputQueue.get(numOfElements)!="*"))){
        					numOfElements++;
        				}
        				outputQueue.add(numOfElements, expressionList.get(j));
        			}
        			else outputQueue.addLast(expressionList.get(j));
        			break;
        	}
	    }
        if(numOfLeftParenth>numOfRightParenth||numOfRightParenth>numOfLeftParenth) throw new Exception ("Malformed Expression");
        //adding numbers first because of postfix notation
        while (!operandQueue.isEmpty()){
        	outputQueue.addLast(operandQueue.remove());
        }
        //operators are last in RPN
	    while(!operatorQueue.isEmpty()){
	    	outputQueue.addLast(operatorQueue.remove());
	    }
	    //call to method that calculates an RPN expression
	    return evalRPN(outputQueue);
	}	
		
	public static void main(String args[]) throws Exception {
		String line;
		System.out.println("Write your expression here");
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
                                      	                        
		do {
			line=stdin.readLine();
			if (line.length()>0) {
				Integer x=evaluate(line);
				System.out.println(" = " + x);
				/*try {
					Integer x=evaluate(line);
					System.out.println(" = " + x);
				}
				catch (Exception e) {
					System.out.println("Malformed Expression: "+e);
				}*/
			}
		} while (line.length()>0);
	}
}