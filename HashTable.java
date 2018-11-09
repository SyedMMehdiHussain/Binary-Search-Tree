public class HashTable<E extends Comparable<E> & Square<E>> {

	public Object[] hT = new Object[37];
	private int tableSize;

	public HashTable() {
		
		tableSize = 37 ;
		for(int i = 0; i < tableSize; i++){
			hT[i] = new BinarySearchTree<E>();
		}
	}

//helper function for add and search methods	
int myHash (E item){
	return (item.square() >> 10) % 37;
}

/*
 * this is the add method, which first calculates the index of the key and then places the 
 * data in a binary search tree at that index
 * 
 */
@SuppressWarnings("unchecked")
public void add(E item){
	int index = myHash(item);

	System.out.println("added to tree " + index);

	//this adds the item to the index and in a binary search tree at that particular index
	((BinarySearchTree<E>)hT[index]).add(item);
}	

/*
 *this is the delete function, first this calculates the index of the key to be deleted, then goes to 
 *to that index and in the binary search  tree at that index, it deletes the data to be deleted 
 */
@SuppressWarnings("unchecked")
public void delete(E item){
	int index = myHash(item);

	//this deletes the item from the binary search tree at that particular index

	((BinarySearchTree<E>)hT[index]).delete(item);
}

/*
 * a helper fucntion for the get method, which calcuates the index in which an item 
 * will be present
 */
private int myHashInt(int id) {
	return ((id*id) >> 10)% 37;
}

/*
 * this is the get method which gets the index of the item and goes to that index and
 * looks for the item in that binary search tree
 */
@SuppressWarnings("unchecked")
public E get(int item){
	int index = myHashInt(item);
	 E 	record	=		(E) ((BinarySearchTree<E>)hT[index]).get(item);
	 return record;
}
/**
 * prints the indices which have a non-empty binary search tree 
 * @param start
 * @param end
 */
@SuppressWarnings("unchecked")
public void printNonEmpty(int start,int end){
	
		for(int i=start;i<end;i++){
		if (((BinarySearchTree<E>)hT[i]).isEmpty()){
			
		}
		else{
			System.out.println("\nPrinting tree " + i+":");
		((BinarySearchTree<E>)hT[i]).breadthFirstTraversal();
		}
	}
}

/**
 * this prints all the trees, be it empty or non-empty both
 */
@SuppressWarnings("unchecked")
public void print(){
	for(int i=0;i<hT.length;i++){
		System.out.println("\nprinting tree " +i+":");
		
		((BinarySearchTree<E>)hT[i]).breadthFirstTraversal();
	}
}


}
