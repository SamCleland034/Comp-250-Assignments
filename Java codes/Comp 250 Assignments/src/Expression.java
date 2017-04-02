import java.io.*;
import java.util.*;
import java.lang.String;
import java.lang.Integer;


public class Expression {
	static String delimiters="+-*%()";
	
	
	
	// Returns the value of the arithmetic Expression described by expr
	// Throws an exception if the Expression is malformed
	static Integer evaluate(String expr) throws Exception {  
        Stack<String> operatorStack = new Stack<String>();
        Stack<Integer> operandStack = new Stack<Integer>();
        int numOfLeftParenth=0;
        int numOfRightParenth=0;
        StringTokenizer expression = new StringTokenizer(expr, delimiters, true);
        //putting into list to create data for the expression, easier to make use of than relying on nextToken
        List<String> expressionList= new ArrayList<String>();
        do{
        	expressionList.add(expression.nextToken());
        } while(expression.hasMoreTokens());
       
        for(int j=0; j<expressionList.size();j++){
        	List<Integer> storageOfOperands= new ArrayList<Integer>();
            List<String> storageOfOperators= new ArrayList<String>();
            //switch cases for operators
        	switch(expressionList.get(j)){
        		case "+":
        			operatorStack.push("+");
        			break;
        		case "-":
        			operatorStack.push("-");
        			break;
        		case "*":
        			operatorStack.push("*");
        			break;
        		case "%":
        			operatorStack.push("%");
        			break;
        		case "(":
        			operatorStack.push("(");
        			numOfRightParenth=numOfRightParenth+1;
        			break;
        		case ")":
        			numOfLeftParenth=numOfLeftParenth+1;
        			//can never have more ) than ( in an expression, same vice versa
        			//also tracks if ) happens before or after (, if it happens before prints error
        			if(numOfLeftParenth>numOfRightParenth) throw new Exception ("not a valid expression");
        			if(operatorStack.contains("(")&&(numOfLeftParenth==numOfRightParenth)){
        				Integer result= 0;
        				do{
        					if(operatorStack.peek()==")") {
        						operatorStack.pop();
        						
        						//If given a statement ((4-2)+2), this if block calculates 4-2 first 
        						if(!operatorStack.contains(")")){
        							Integer parenthOutcome = 0;
        							List<String> operators= new ArrayList<String>();
    								List<Integer> operands= new ArrayList<Integer>();
    								for(int y=0; y<operandStack.size()-1;y++){
    									operands.add(operandStack.pop());
    								}
    								for (int x=0; x<operands.size()-1;x++){
    									
    								}
        							while(operatorStack.peek()!="("){
        								
        								if(parenthOutcome==0){
        									Integer num1= operandStack.pop();
        									Integer num2= operandStack.pop();
        									switch(operatorStack.pop()){
        										case "+":
        											parenthOutcome = num1+num2;
        											break;
        										case "-":
        											parenthOutcome = num2-num1;
        											break;
        										case "*":
        											parenthOutcome = num1*num2;
        											break;
        										case "%":
        											parenthOutcome = num2/num1;
        											break;
        										}
        									
        									}
        								else{
        									Integer num3 = operandStack.pop();
        									switch(operatorStack.pop()){
    											case "+":
    												parenthOutcome += num3;
    												break;
    											case "-":
    												parenthOutcome = num3-parenthOutcome;
    												break;
    											case "*":
    												parenthOutcome *= num3;
    												break;
    											case "%":
    												parenthOutcome = num3/parenthOutcome;
    												break;
    										}
    									
                						}
        								
        							}
        							operandStack.push(parenthOutcome);
        						}
        						else if(operatorStack.peek()!= ")"){
        							storageOfOperators.add(operatorStack.pop());
        						}
        						else if (operatorStack.peek()!="("){
        							Integer parenthResult = 0;
        							//List<Integer> storageOperands = new ArrayList <Integer>();
        							//List<String> storageOperators = new ArrayList <String>();
        							/*while(operatorStack.contains("(")){      								
        								storageOperands.add(operandStack.pop());
        								if(!operatorStack.isEmpty()) if(operatorStack.peek()== ")"){
    										while(operatorStack.peek()== ")"){
    											operatorStack.pop();
    										}
        								}
        								storageOperators.add(operatorStack.pop());
        							}
        							if(storageOperands.size()==storageOperators.size()) storageOperands.add(operandStack.pop());*/
        							while(operatorStack.peek()!="("){
        							
        								if(parenthResult==0){
        									Integer num1 = operandStack.pop();
        									Integer num2 = operandStack.pop();
        									if(!operatorStack.isEmpty()) if(operatorStack.peek()== ")"){
        										while(operatorStack.peek()== ")"){
        											operatorStack.pop();
        										}
        									}
        									switch(operatorStack.pop()){
    										case "+":
    											parenthResult = num1+num2;
    											break;
    										case "-":
    											parenthResult= num2-num1;
    											break;
    										case "*":
    											parenthResult = num1*num2;
    											break;
    										case "%":
    											parenthResult = num2/num1;
    											break;
    										}
        									
        								}
        								else{
        									if(operatorStack.peek()== ")"){
        										while(operatorStack.peek()== ")"){
        											operatorStack.pop();
        										}
        									}
        									Integer num3 = operandStack.pop();
        									switch(operatorStack.pop()){
    										case "+":
    											parenthResult +=num3;
    											break;
    										case "-":
    											parenthResult =num3-parenthResult;
    											break;
    										case "*":
    											parenthResult *=num3;
    											break;
    										case "%":
    											parenthResult =num3/parenthResult;
    											break;
        									}
        								}
        							}//while(operatorStack.contains("("));
        							operandStack.push(parenthResult);
        						}
        						
        					}
        					
        					else {
        						if(operatorStack.peek()=="(") {
        							operatorStack.pop();
        							//always need one more operand than operator, (2+4) partheneses don't count in this case since they aren't pushed onto the stack;
        							if(storageOfOperators.size()==storageOfOperands.size()&&!operatorStack.contains("(")) storageOfOperands.add(operandStack.pop());
        						}
        						else{
        							//case when the peek is an operator
        							storageOfOperators.add(operatorStack.pop());
        							if (!operandStack.isEmpty())storageOfOperands.add(operandStack.pop());
        							
        						}
        					}
        				}while(operatorStack.contains("("));
        				//calculates everything that was inside the partheneses from right to left
        				//if there was a case such as ((4-2)-2), (4-2) shows up as 2 here 
        				for(int i=0;i<=storageOfOperators.size()-1; i++){
        					if(result == 0){
        						//using switch chases for operators, if it is a + then it adds the 2 elements 
        						switch(storageOfOperators.get(i)){
        						case "+":
        							result = storageOfOperands.get(i+1)+storageOfOperands.get(i);
        							break;
        						case "-":
        							result = storageOfOperands.get(i+1)-storageOfOperands.get(i);
        							break;
        						case "*":
        							result = storageOfOperands.get(i+1)*storageOfOperands.get(i);
        							break;
        						case "%":
        							result = storageOfOperands.get(i+1)/storageOfOperands.get(i);
        							break;
        						}
        					}
        					else{
        						//case where there is more than one operator, such as 5-4-3 inside the parentheses
        						//keeps working from right to left
        						switch(storageOfOperators.get(i)){
        						case "+":
        							result += storageOfOperands.get(i+1);
        							break;
        						case "-":
        							result = storageOfOperands.get(i+1)-result;
        							break;
        						case "*":
        							result *= storageOfOperands.get(i+1);
        							break;
        						case "%":
        							result = storageOfOperands.get(i+1)/result;
        							break;
        							}
        					}	
        				}
        				//pushes result onto the stack, since there may be other operations that need to be calculated later
        				operandStack.push(result);
        			}
        			else{
        				//happens when still adding parentheses to the stack (hasn't finished going through List) mainly
        				operatorStack.push(")");
        			}
        			break;
        		default:
        			//when adding a number to the stack 
        			//this happens when there we are at a number and there are no parentheses and there are some operations that can be done
        			//to make the stacks smaller, ex: if we have 5+4-3, once we get to 4, it will see that 5 and + are in the stacks, so it will carry out the operation
        			if(!operatorStack.isEmpty()&&(!operandStack.isEmpty())&&(!operatorStack.contains("("))){
        				Integer operand = operandStack.pop();
        				String operator = operatorStack.pop();
        				Integer currentNum= 0;
        				Integer result1= 0;
        				currentNum= Integer.parseInt(expressionList.get(j),10);
        				switch(operator){
                			case "+":
                			result1 = operand+currentNum;
                				break;
                			case "-":
                			result1 = operand-currentNum;
                				break;
                			case "*":
                			result1 = operand*currentNum;
                				break;
                			case "%":
                			result1 = operand/currentNum;
                				break;
        				}
        				operandStack.push(result1);
        			}
        			//case when nothing is in the stacks
        			else if (operatorStack.isEmpty() && operandStack.isEmpty()){
        				Integer currentNum= 0;
        				currentNum= Integer.parseInt(expressionList.get(j), 10);
        				operandStack.push(currentNum);
        			}
        			//case when only ( are in the operation stack
        			else if(!operatorStack.isEmpty()&&operatorStack.contains("(")){
        				Integer currentNum= 0;
        				currentNum= Integer.parseInt(expressionList.get(j), 10);
        				operandStack.push(currentNum);
        			}
        			//everything else considered an invalid expression
        			else{
        				throw new Exception ("Malformed expression");
        			}
        		}
        }
        //executes after everything has been evaluated in the expressionList
        if(operatorStack.isEmpty()&&operandStack.size()==1){
        	return operandStack.pop();
        }
        //executes if there is left over stuff in the stack
        //ex: 6+(4-2) will have to go through this operation
        else if(!operatorStack.isEmpty()&&operandStack.size()>1){
        	Integer outcome=0;
        	for(int index=0; index<operatorStack.size();index++){
        		if(outcome==0){
        			Integer num1= operandStack.pop();
        			Integer num2 = operandStack.pop();
        			String operator = operatorStack.pop();
        			switch(operator){
    					case "+":
    						outcome = num1+num2;
    						break;
    					case "-":
    						outcome = num2-num1;
    						break;
    					case "*":
    						outcome = num1*num2;
    						break;
    					case "%":
    						outcome = num2/num1;
    						break;
        				}
        			}
        		else{
        			Integer num= operandStack.pop();
        			String operator= operatorStack.pop();
        			switch(operator){
						case "+":
							outcome += num;
							break;
						case "-":
							outcome = num-outcome;
							break;
						case "*":
							outcome *= num;
							break;
						case "%":
							outcome = num/outcome;
							break;
        			}
        		}
        	}
        	return outcome;
}
        //everything else considered invalid expression
        else{
        	throw new Exception ("Malformed Expression");
        } 
	}	
		
	public static void main(String args[]) throws Exception {
		String line;
		System.out.println("Type your expression here");
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
                                      	                        
		do {
			line=stdin.readLine();
			if (line.length()>0) {
				/*try {
					Integer x=evaluate(line);
					System.out.println(" = " + x);
				}
				catch (Exception e) {
					System.out.println("Malformed Expression: "+e);
				}*/
			Integer x=evaluate(line);
			System.out.println(" = " + x);
				
			}
		} while (line.length()>0);
	}
}