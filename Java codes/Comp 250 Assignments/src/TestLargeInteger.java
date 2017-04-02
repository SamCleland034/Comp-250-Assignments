import java.lang.Math;
/*********************************************************/
/* NAME: Sam Cleland                                                 */
/* STUDENT ID: 260675996                                          */
/*********************************************************/

/* This class stores and manipulates very large non-negative integer numbers 
   The digits of the number are stored in an array of bytes. */
class LargeInteger {

    /* The digits of the number are stored in an array of bytes. 
       Each element of the array contains a value between 0 and 9. 
       By convention, digits[digits.length-1] correspond to units, 
       digits[digits.length-2] corresponds to tens, digits[digits.length-3] 
       corresponds to hundreds, etc. */

    byte digits[];


    
    /* Constructor that creates a new LargeInteger with n digits */
    public LargeInteger (int n) {
        digits= new byte[n];
    }

        
    /* Constructor that creates a new LargeInteger whose digits are those of the string provided */
    public LargeInteger (String s) {        
        digits = new byte[s.length()]; /* Note on "length" of arrays and strings: Arrays can be seen 
                                          as a class having a member called length. Thus we can access 
                                          the length of digits by writing digits.length
                                          However, in the class String, length is a method, so to access 
                                          it we need to write s.length() */

        for (int i=0;i<s.length();i++) digits[i] = (byte)Character.digit(s.charAt(i),10);
        /* Here, we are using a static method of the Character class, called digit, which 
           translates a character into an integer (in base 10). This integer needs to be 
           cast into a byte. ****/
    }


    /* Constructor that creates a LargeInteger from an array of bytes. Only the bytes  
       between start and up to but not including stop are copied. */
    public LargeInteger (byte[] array, int start, int stop) {
        digits = new byte[stop-start];
        for (int i=0;i<stop-start;i++) digits[i] = array[i+start];
    }


    /* This method returns a LargeInteger where eventual leading zeros are removed. 
       For example, it turns 000123 into 123. Special case: it turns 0000 into 0. */
    public LargeInteger removeLeadingZeros() {
        if (digits[0]!=0) return this;
        int i = 1;
        while (i<digits.length && digits[i]==0) i++;
        if (i==digits.length) return new LargeInteger("0");
        else return new LargeInteger(digits,i,digits.length);
    } // end of removeLeadingZeros
   

    /* This methods multiplies a given LargeInteger by 10^nbDigits, simply by shifting 
       the digits to the left and adding nbDigits zeros at the end */
    public LargeInteger shiftLeft(int nbDigits) {
        LargeInteger ret = new LargeInteger( digits.length + nbDigits );
        for (int i = 0 ; i < digits.length ; i++) ret.digits[ i ] = digits[ i ];
        for (int i = 0; i <  nbDigits; i++) ret.digits[ digits.length + i ] = 0;
        return ret;
    } // end of shiftLeft


      /* Returns true if the value of this is the same as the value of other */
    public boolean equals (LargeInteger other) {
        if ( digits.length != other.digits.length ) return false;
        for (int i = 0 ; i < digits.length ;i++ ) {
            if ( digits[i] != other.digits[i] ) return false;
        }
        return true;
    } // end of equals


      /* Returns true if the value of this is less than the value of other ****/
    public boolean isSmaller (LargeInteger other) {
        if ( digits.length > other.digits.length ) return false;
        if ( digits.length < other.digits.length ) return true;
        for (int i = 0 ; i < digits.length ; i++ ) {
            if ( digits[i] < other.digits[i] ) return true;
            if ( digits[i] > other.digits[i] ) return false;
        }
        return false;
    } // end of isSmaller
    


    /* This method adds two LargeIntegers: the one on which the method is 
       called and the one given as argument. The sum is returned. The algorithms 
       implemented is the normal digit-by-digit addition with carry. */

    LargeInteger add(LargeInteger other) {
        int size = Math.max( digits.length,other.digits.length );

        /* The sum can have at most one more digit than the two operands */
        LargeInteger sum = new LargeInteger( size + 1 ); 
        byte carry = 0;

        for (int i = 0; i < size + 1 ;i++) {
            // sumColumn will contain the sum of the two digits at position i plus the carry
            byte sumColumn = carry; 
            if ( digits.length - i  - 1 >= 0) sumColumn += digits[ digits.length - i - 1 ];
            if (other.digits.length - i - 1  >= 0) sumColumn += other.digits[ other.digits.length - i - 1 ];
            sum.digits[ sum.digits.length - 1 - i ] = (byte)( sumColumn % 10 ); // The i-th digit in the sum is sumColumn mod 10
            carry = (byte)( sumColumn / 10 );          // The carry for the next iteration is sumColumn/10
        }        
        return sum.removeLeadingZeros();
    } // end of add



    /* This method subtracts the LargeInteger other from that from where the method is called.
       Assumption: the argument other contains a number that is not larger than the current number. 
       The algorithm is quite interesting as it makes use of the addition code.
       Suppose numbers X and Y have six digits each. Then X - Y = X + (999999 - Y) - 1000000 + 1.
       It turns out that computing 999999 - Y is easy as each digit d is simply changed to 9-d. 
       Moreover, subtracting 1000000 is easy too, because we just have to ignore the '1' at the 
       first position of X + (999999 - Y). Finally, adding one can be done with the add code we already have.
       This tricks is the equivalent of the method used by most computers to do subtractions on binary numbers. ***/

    public LargeInteger subtract( LargeInteger other ) {
        // if other is larger than this number, simply return 0;
        if (this.isSmaller( other ) || this.equals( other ) ) return new LargeInteger( "0" );

        LargeInteger complement = new LargeInteger( digits.length ); /* complement will be 99999999 - other.digits */
        for (int i = 0; i < digits.length; i++) complement.digits[ i ] = 9;
        for (int i = 0; i < other.digits.length; i++) 
            complement.digits[ digits.length - i - 1 ] -= other.digits[other.digits.length - i -  1];

        LargeInteger temp = this.add( complement );     // add (999999- other.digits) to this
        temp = temp.add(new LargeInteger( "1" ));       // add one

        // return the value of temp, but skipping the first digit (i.e. subtracting 1000000)
        // also making sure to remove leading zeros that might have appeared.
        return new LargeInteger(temp.digits,1,temp.digits.length).removeLeadingZeros();
    } // end of subtract


    /* Returns a randomly generated LargeInteger of n digits */
    public static LargeInteger getRandom( int n ) {
        LargeInteger ret = new LargeInteger( n );
        for (int i = 0 ; i < n ; i++) {
            // Math.random() return a random number x such that 0<= x <1
            ret.digits[ i ]=(byte)( Math.floor( Math.random() * 10) );
            // if we generated a zero for first digit, regenerate a draw
            if ( i==0 && ret.digits[ i ] == 0 ) i--;
        }
        return ret;
    } // end of getRandom



    /* Returns a string describing a LargeInteger 17*/
    public String toString () {        

        /* We first write the digits to an array of characters ****/
        char[] out = new char[digits.length];
        for (int i = 0 ; i < digits.length; i++) out[ i ]= (char) ('0' + digits[i]);

        /* We then call a String constructor that takes an array of characters to create the string */
        return new String(out);
    } // end of toString




    /* This function returns the product of this and other by iterative addition */
    public LargeInteger iterativeAddition(LargeInteger other) {
    	LargeInteger product= new LargeInteger("0");
    	LargeInteger increment= new LargeInteger("1");
    	LargeInteger count= new LargeInteger("0");
    			//this function keeps on adding a "b" times and stops when boolVal equals other
    	while (!count.equals(other)){
    		
    		product=product.add(this);
    		count= count.add(increment);
    		
    	
    	
    	}
    	return product; 
    	
    	
    	}

    	
    	
        
       
     // end of iterativeAddition



    /* This function returns the product of this and other by using the standard multiplication algorithm */
    public LargeInteger standardMultiplication(LargeInteger other) {
    	
    	
    	//if the numbers are the same length then it will be performed as a*b
    	int biggerNumlength= Math.max(this.digits.length,other.digits.length);
    	int smallerNumlength= Math.min(this.digits.length,other.digits.length);
    	int product = 0;
    	LargeInteger sProduct= new LargeInteger("");
    	LargeInteger actualProduct= new LargeInteger("");
    	//starts the iteration through the ones column of the multiplier to the nth column(reads right to left)
    	for(int i=smallerNumlength-1 ; i>=0; i--){
    		int carry = 0;
    		int[]tmpAdd = new int[biggerNumlength+1];
    		//another for loop for the number being multiplied (reads right to left as well)
    		for (int j = biggerNumlength-1; j>=0;j--){
    			//distinguishes which number is the largest by length
    			//if both are the same length then executes the if statement 
    			if (this.digits.length>=other.digits.length){
    				product= this.digits[j]*other.digits[i]+carry;
    				tmpAdd[j+1]=product%10; //remainder is put into the rightmost part of the array that hasn't been occupied
    				carry= product/10; //calculates carry that will be added to the product through the next iteration of the loop
    			}
    			else{
    				product= this.digits[i]*other.digits[j]+carry;
    				tmpAdd[(int)j+1]=product%10; 
    				carry= product/10;
    			}
    	
    		
    		}
    		tmpAdd[0]= carry;//the left most position gets whatever is left of carry ,since carry has to be put somewhere
    		/*turns integer array into a string to make a new LargeInteger
    		that takes the new string stringProduct */
    		StringBuilder build = new StringBuilder();
    		for(int x = 0; x < tmpAdd.length; x++) {
    		   build.append(tmpAdd[x]);
    		}
    		String stringProduct = build.toString();
    		sProduct= new LargeInteger(stringProduct);
    		sProduct= sProduct.removeLeadingZeros();
    		sProduct= sProduct.shiftLeft(smallerNumlength-1-i);
    		actualProduct= actualProduct.add(sProduct);
    		//shiftLeft used to assign correct power of 10 to the digit
    		//the first digit gets the power (smallerNumlength-1-(smallerNumlength-1) or 10^0
    		
    		} 																		
    	return actualProduct; 
    } // end of standardMultiplication
                


    /* This function returns the product of this and other by using the basic recursive approach described 
       in the homework. Only use the built-in "*" operator to multiply single-digit numbers */
    public LargeInteger recursiveMultiplication( LargeInteger other ) {

        // left and right halves of this and number2                                                                                        
        LargeInteger leftThis, rightThis, leftOther, rightOther;
        LargeInteger term1,  term2,  term3,  term4, sum; // temporary terms                                                                      

        if ( digits.length==1 && other.digits.length==1 ) {
            int product = digits[0] * other.digits[0];
            return new LargeInteger( String.valueOf( product ) );
        }

        int k = digits.length;
        int n = other.digits.length;
        leftThis = new LargeInteger( digits, 0, k - k/2 );
        rightThis = new LargeInteger( digits, k - k/2, k );
        leftOther = new LargeInteger( other.digits, 0, n - n/2 );
        rightOther = new LargeInteger( other.digits, n - n/2, n );

        /* now recursively call recursiveMultiplication to compute the                    
           four products with smaller operands  */

        if ( n > 1 && k > 1 )  term1 = rightThis.recursiveMultiplication(rightOther );
        else term1 = new LargeInteger( "0" );

        if ( k>1 ) term2 = ( rightThis.recursiveMultiplication( leftOther ) ).shiftLeft( n/2 );
        else term2 = new LargeInteger( "0" );

        if ( n>1 ) term3 = ( leftThis.recursiveMultiplication( rightOther ) ).shiftLeft( k/2 );
        else term3 = new LargeInteger( "0" );

        term4 = ( leftThis.recursiveMultiplication( leftOther ) ).shiftLeft( k/2 + n/2 );

        sum = new LargeInteger( "0" );
        sum = sum.add( term1 );
        sum = sum.add( term2 );
        sum = sum.add( term3 );
        sum = sum.add( term4 );

        return sum;
    } // end of recursiveMultiplication             


    /* This method returns the product of this and other by using the faster recursive approach 
       described in the homework. It only uses the built-in "*" operator to multiply single-digit numbers */
    public LargeInteger recursiveFastMultiplication(LargeInteger other) {
    	 LargeInteger leftThis, rightThis, leftOther, rightOther;
         LargeInteger term1,  term2,  term3,termTmp1,termTmp2,termTmp3, sum; // temporary terms 
         int k = digits.length;
         int n = other.digits.length;
         //if ( n<k ) then return recursiveFastMultiplication( b, a );
         //if ( k=1 ) then return standardMultiplication( a, b );
         //follows the fast recursive algorithm, using 3 multiplications instead of 4
         if(n<k)return other.recursiveFastMultiplication(this);
         if(k==1)return this.standardMultiplication(other);
         leftThis = new LargeInteger( digits, 0, k - k/2 );
         rightThis = new LargeInteger( digits, k - k/2, k );
         leftOther = new LargeInteger( other.digits, 0, n - n/2 );
         rightOther = new LargeInteger( other.digits, n - n/2, n );
         term1= rightThis.recursiveFastMultiplication (rightOther);
         term2= leftThis.recursiveFastMultiplication(leftOther);
         termTmp1=leftThis.add(rightThis);
         termTmp2= leftOther.shiftLeft(n/2-k/2);
         termTmp2= termTmp2.add(rightOther);
         term3= termTmp1.recursiveFastMultiplication(termTmp2);
         termTmp3= term2.shiftLeft(n/2-k/2);
         termTmp3= termTmp3.add(term1);
         term3= term3.subtract(termTmp3);
         term2= term2.shiftLeft(k/2+n/2);
         term3= term3.shiftLeft(k/2);
         sum=term1.add(term3);
         sum=sum.add(term2);
         return sum;
         
       
     
     
    }


}
// end of the LargeInteger class




// This class contains code to test the methods of the LargeInteger class. 
// Modify it as you wish to thorougly test each of the multiplication methods
// and to measure their running time.
// THE CODE IN THIS CLASS WILL NOT BE EVALUATED OR TESTED BY THE TAS.

public class TestLargeInteger {
    public static void main( String args[] ) {
        /* TEST YOUR METHODS BY ADDING CODE HERE */
        /* THIS CODE IS NOT GOING TO BE GRADED. IT'S JUST FOR YOU TO TEST YOUR PROGRAM */
    	
        /* For example */
    	long startTime = System.nanoTime();
		for(int x= 0 ; x<1000; x++){
		LargeInteger num1 = LargeInteger.getRandom(8);
		LargeInteger num2 = LargeInteger.getRandom(8);
		num1.iterativeAddition(num2);
		//num1.standardMultiplication(num2);
		//num1.recursiveMultiplication(num2);
		//num1.recursiveFastMultiplication(num2);
		
}
		long endTime = System.nanoTime();
		long duration = endTime-startTime;
		duration= duration/1000;
		//LargeInteger a= new LargeInteger("0");
		//LargeInteger b= new LargeInteger ("0");
	
    	
        //System.out.println(a + " + " + b + " = " + a.add( b ) );
        //System.out.println(b + " - " + a + " = " + b.subtract( a ) );
        //System.out.println(b + " * " + a + " = " + b.recursiveMultiplication( a ) );
        //System.out.println(b + " * " + a + " = " + b.recursiveFastMultiplication(a));
        //System.out.println(b + " * " + a  + " (iteratively) = "  + b.iterativeAddition(a));
        //System.out.println(b + " * " + a + " (Standard Multiplication) = "+ b.standardMultiplication(a));
        
       
        
        System.out.println(duration + " Nanoseconds");
        
    }
}


                
        
