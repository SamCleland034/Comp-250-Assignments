/* 
 * Created by Numa Karolinski
 * 
 * This class creates the two pairs of trees from the assignment that are isomorphic/non-isomorphic tree 
 * pair examples.
 *
 * This mostly just sets up the tree for you, if you don't have a boolean return method then I would
 * suggest editing everything you see in the main method
 * 
 */

public class tree {
	private int value;
	private tree leftChild, rightChild, parent;
	tree(int s, tree l, tree r, tree p) {
		value = s; 
		leftChild = l; 
		rightChild = r;
		parent = p;
	}
	tree(int s, tree p){
		value = s;
		parent = p;
		leftChild = rightChild = null;
	}
	tree(int s){
		value = s;
		leftChild=rightChild=parent=null;
	}
	int getValue(){ 
		return value; 
	}
    tree getLeftChild(){
    	return leftChild;
    }
    tree getRightChild(){
    	return rightChild;
    }
    tree getParent(){
    	return parent;
    }
    void setLeftChild(tree n) {
        leftChild = n; 
        n.parent = this; 
    }
    void setRightChild(tree n) { 
        rightChild = n; 
        n.parent=this; 
    }
    void setValue(int treeValue){
    	value = treeValue;
    }
    static boolean isomorphismCheck(tree A, tree B){
    	
    	/*
    	 * Write your method here, and click run, hopefully your method works :)
    	 *
    	 *
    	 * This class only tests the two trees that he put as examples on the assignment, 
    	 * so it doesn't check a lot of trees. If you want to test more trees I suggest 
    	 * editing the trees I created in the main
    	 *
    	 * I would advise looking through your code in debug mode just in case you're suspicious
    	 *
    	 */
    	
    	return false;
    }
    public static void main(String args[]){
    	tree A = new tree(5, null);
    	tree AL = new tree(8);
    	tree AR = new tree(4);
    	A.setLeftChild(AL);
    	A.setRightChild(AR);
    	tree ALL = new tree(2);
    	tree ALR = new tree(6);
    	AL.setLeftChild(ALL);
    	AL.setRightChild(ALR);
    	tree ALRL = new tree (3);
    	tree ALRR = new tree(2);
    	ALR.setLeftChild(ALRL);
    	ALR.setRightChild(ALRR);
    	tree B = new tree(5, null);
    	tree BL = new tree(4);
    	tree BR  = new tree(8);
    	B.setLeftChild(BL);
    	B.setRightChild(BR);
    	tree BRL = new tree(6);
    	tree BRR = new tree(2);
    	BR.setLeftChild(BRL);
    	BR.setRightChild(BRR);
    	tree BRLL = new tree(2);
    	tree BRLR = new tree(3);
    	BRL.setLeftChild(BRLL);
    	BRL.setRightChild(BRLR);
    	boolean weAreIsomorphic = isomorphismCheck(A,B);
    	BL.setValue(8);
    	BR.setValue(4);
    	boolean weAreNotIsomorphic = isomorphismCheck(A,B);
    	if(weAreIsomorphic == true){
    		System.out.println("A and B are Isomorphic, they should be :)");
    	}
    	else{
    		System.out.println("A and B are not Isomorphic, they should be :(");
    	}
    	if(weAreNotIsomorphic == true){
    		System.out.println("A and B are Isomorphic, they should not be :(");
    	}
    	else{
    		System.out.println("A and B are not Isomorphic, they should not be :)");
    	}
    	if((weAreIsomorphic == true)&&(weAreNotIsomorphic == false)){
    		System.out.println("Looks like your method works :)");
    	}
    }
}