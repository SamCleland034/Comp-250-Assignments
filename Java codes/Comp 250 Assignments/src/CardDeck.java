import java.util.*;

class CardDeck {
    LinkedList<Integer> deck;

    // constructor, creates a deck with n cards, placed in increasing order
    CardDeck(int n) {
	deck = new LinkedList<Integer> ();
	for (int i=1;i<=n;i++) deck.addLast(new Integer(i));
    }

    // executes the card trick
    public void runTrick() {

	while (!deck.isEmpty()) {
	    // remove the first card and remove it
	    Integer topCard = this.deck.removeFirst();
	    System.out.println("Showing card "+topCard);

	    // if there's nothing left, we are done
	    if (deck.isEmpty()) break;
	    
	    // otherwise, remove the top card and place it at the back.
	    Integer secondCard = deck.removeFirst();
	    deck.addLast(secondCard);

	    System.out.println("Remaining deck: "+deck);

	}
    }



    public void setupDeck(int n) {
    	//new list for to manipulate order of cards
    	LinkedList<Integer> magicDeck= new LinkedList<Integer>();
    	for(int tmp=n-1; n>=0;tmp--){
    		if(!deck.isEmpty()){
    		//adds the first element of the deck list that is sorted and puts it into the first slot of the magic deck
    		magicDeck.addFirst(deck.removeLast());
    		//technically base case (not recursion though) since we always want 1 to be at the front
    		//assigns the magic deck to the current deck of cards of and breaks immediately 
    		if(magicDeck.getFirst()==1){
    			deck= magicDeck;
    			break;
    		}
    		//performs the operation that magic trick requires, to get it into perfect order for the trick
    		magicDeck.addFirst(magicDeck.removeLast());
    		}	
    	}
    }



    public static void main(String args[]) {
	// this is just creating a deck with cards in increasing order, and running the trick. 
    CardDeck d = new CardDeck(10);
	d.runTrick();

	// this is calling the method you are supposed to write, and executing the trick.
	CardDeck e = new CardDeck(5);
	e.setupDeck(5);
	e.runTrick();
    }
}

    