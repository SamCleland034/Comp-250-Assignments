
class ExpressionTree {
    private String value;
    private ExpressionTree leftChild, rightChild, parent;
    
    ExpressionTree() {
        value = null; 
        leftChild = rightChild = parent = null;
    }
    
    // Constructor
    /* Arguments: String s: Value to be stored in the node
                  ExpressionTree l, r, p: the left child, right child, and parent of the node to created      
       Returns: the newly created ExpressionTree               
    */
    ExpressionTree(String s, ExpressionTree l, ExpressionTree r, ExpressionTree p) {
        value = s; 
        leftChild = l; 
        rightChild = r;
        parent = p;
    }
    
    /* Basic access methods */
    String getValue() { return value; }

    ExpressionTree getLeftChild() { return leftChild; }

    ExpressionTree getRightChild() { return rightChild; }

    ExpressionTree getParent() { return parent; }


    /* Basic setting methods */ 
    void setValue(String o) { value = o; }
    
    // sets the left child of this node to n
    void setLeftChild(ExpressionTree n) { 
        leftChild = n; 
        n.parent = this; 
    }
    
    // sets the right child of this node to n
    void setRightChild(ExpressionTree n) { 
        rightChild = n; 
        n.parent=this; 
    }
    

    // Returns the root of the tree describing the expression s
    // Watch out: it makes no validity checks whatsoever!
    ExpressionTree(String s) {
        // check if s contains parentheses. If it doesn't, then it's a leaf
        if (s.indexOf("(")==-1) setValue(s);
        else {  // it's not a leaf

            /* break the string into three parts: the operator, the left operand,
               and the right operand. ***/
            setValue( s.substring( 0 , s.indexOf( "(" ) ) );
            // delimit the left operand 2008
            int left = s.indexOf("(")+1;
            int i = left;
            int parCount = 0;
            // find the comma separating the two operands
            while (parCount>=0 && !(s.charAt(i)==',' && parCount==0)) {
                if ( s.charAt(i) == '(' ) parCount++;
                if ( s.charAt(i) == ')' ) parCount--;
                i++;
            }
            int mid=i;
            if (parCount<0) mid--;

        // recursively build the left subtree
            setLeftChild(new ExpressionTree(s.substring(left,mid)));
    
            if (parCount==0) {
                // it is a binary operator
                // find the end of the second operand.F13
                while ( ! (s.charAt(i) == ')' && parCount == 0 ) )  {
                    if ( s.charAt(i) == '(' ) parCount++;
                    if ( s.charAt(i) == ')' ) parCount--;
                    i++;
                }
                int right=i;
                setRightChild( new ExpressionTree( s.substring( mid + 1, right)));
        }
    }
    }


    // Returns a copy of the subtree rooted at this node... 2014
    ExpressionTree deepCopy() {
        ExpressionTree n = new ExpressionTree();
        n.setValue( getValue() );
        if ( getLeftChild()!=null ) n.setLeftChild( getLeftChild().deepCopy() );
        if ( getRightChild()!=null ) n.setRightChild( getRightChild().deepCopy() );
        return n;
    }
    
    // Returns a String describing the subtree rooted at a certain node.
    public String toString() {
        String ret = value;
        if ( getLeftChild() == null ) return ret;
        else ret = ret + "(" + getLeftChild().toString();
        if ( getRightChild() == null ) return ret + ")";
        else ret = ret + "," + getRightChild().toString();
        ret = ret + ")";
        return ret;
    } 
    //called to perform an operation depending on what the expression is (left most child evaluated first)
    public static double operate(String expression, double x, double y) throws Exception{
    	switch(expression){
    		case "add":
    			return x+y;	
    		case "minus":
    			return x-y;
    		case "mult":
    			return x*y;
    		case "cos":
    			return Math.cos(x);
    		case "sin":
    			return Math.sin(x);
    		case "exp":
    			return Math.exp(x);
    		default:
    			throw new Exception("Malformed Expression");	                   
    	}
    }
    // Returns the value of the expression rooted at a given node
    // when x has a certain value
    double evaluate(double x) throws Exception {
    	if (getLeftChild()==null) {
    	// base case, no children nodes, returns x or whatever number the node is 
    		if(getValue().equals("x")) return x;
    		else return Double.parseDouble(getValue());
    	}
    	else{
    		if(getRightChild()!=null){
    			//case for mult add minus, since they need 2 childs for operations
    			//operate method on line 111
    			return operate(getValue(),getLeftChild().evaluate(x),getRightChild().evaluate(x));
    		}
    		else{
    			//case for cos sin and exp, since they only have 1 argument has a child
    			//or the case when one of the operands is 0
    			return operate(getValue(), getLeftChild().evaluate(x),0);
    		}
    	}
	// AND CHANGE THIS RETURN STATEMENT
    }                                                 

    /* returns the root of a new expression tree representing the derivative of the
       original expression */
    ExpressionTree differentiate() {
	// WRITE YOUR CODE HERE
    if(getLeftChild()==null){
    	if(getValue().equals("x")) return new ExpressionTree("1");
    	else return new ExpressionTree("0");
    }
    	if(getValue().equals("add")||getValue().equals("minus")){
    		return new ExpressionTree(getValue(),getLeftChild().differentiate(), getRightChild().differentiate(),getParent());
    	}
    	if(getValue().equals("mult")){
    		ExpressionTree left,right;
    		//product rule, works when there is  only one multiplication as well since the derivative of constant = 0
    		left = new ExpressionTree("mult",getLeftChild().differentiate(),getRightChild().deepCopy(),getParent());
    		right = new ExpressionTree("mult",getLeftChild().deepCopy(),getRightChild().differentiate(),getParent());
    		return new ExpressionTree("add",left, right, getParent());
    	}
    	if(getValue().equals("sin")){
    		ExpressionTree inner,outer;
    		//inner is the derivative of the inside expression (chain rule)
    		inner= getLeftChild().differentiate();
    		//outer is the derivative of the cosine with the expression inside unchanged 
    		outer= new ExpressionTree("cos",getLeftChild(),null ,getParent());
    		//multiply them together to get result w/ chain rule
    		return new ExpressionTree("mult",inner,outer,getParent());
    	}
    	if(getValue().equals("cos")){
    		//similar to sin except need to account for negative
    		ExpressionTree inner,outer,product;
    		inner= getLeftChild().differentiate();
    		outer = new ExpressionTree("sin",getLeftChild(),null,getParent());
    		//put into another expression tree to then account for the -sin
    		product = new ExpressionTree("mult",inner,outer,getParent());
    		//returns the same expression except multipled by -1 since deriv of cos= -sin
    		return new ExpressionTree("mult",product,new ExpressionTree("-1"),getParent());
    	}
    	if(getValue().equals("exp")){
    		//same case as cos except derivative of exp is exp
    		ExpressionTree inner,outer;
    		inner = getLeftChild().differentiate();
    		
    		outer = new ExpressionTree("exp",getLeftChild(),null, getParent());
    		return new ExpressionTree("mult",inner,outer,getParent());
    	}
	// AND CHANGE THIS RETURN STATEMENT  
    	return null;
    }
        
    
    public static void main(String args[]) throws Exception {
        ExpressionTree e = new ExpressionTree("mult(x,add(add(2,x),cos(minus(x,4))))");
        System.out.println(e);
        System.out.println(e.evaluate(1));
        System.out.println(e.differentiate());
        ExpressionTree f= e.differentiate();
        System.out.println(f.evaluate(1));
   
 }
}
