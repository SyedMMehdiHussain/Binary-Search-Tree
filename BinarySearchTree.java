public class BinarySearchTree<E extends Comparable<E>> {

	protected Node<E> root;
	private int size;
   
	public E deleteReturn = null;

public BinarySearchTree() {	
		 root =null;
		 size=0;
}

protected BinarySearchTree(Node<E> root){
	this.root= root;
}


@SuppressWarnings("unchecked")
public  BinarySearchTree(E data, BinarySearchTree<Pair> leftTree,
								BinarySearchTree<Pair> rightTree){
	
	root = new Node<E>(data);
	
	if(leftTree !=null){
		root.left=(Node<E>) leftTree.root;
	} else{
		root.left = null;
	}
	
	if(rightTree!=null){
		root.right=(Node<E>) rightTree.root;
	}else{
		root.right=null;
	}
}


@SuppressWarnings({ "unchecked", "rawtypes" })
public BinarySearchTree<Pair> getLeftSubTree() {
	if (root!=null && root.left!=null){
		return new BinarySearchTree(root.left);
	}
	else
		return null;
}

@SuppressWarnings({ "unchecked", "rawtypes" })
public BinarySearchTree<Pair> getRightSubTree() {
	if (root!=null && root.right!=null){
		return new BinarySearchTree(root.right);
	}
	else
		return null;
}


public static class Node<E> {
        private E data;
        private Node<E> left;
        private Node<E> right;
        
        public Node(E item) {
            data = item;
            left=null;
            right=null;
        }

        public E getData() {
            return data;
        }

        public void setData(E item) {
            data = item;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> left) {
            this.left = left;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> right) {
            this.right = right;
        }
} 	

/*
 * this method is a node
 */
public E find(E target) {
	return findW(root, target);
}
/** Recursive find method.
@param localRoot The local subtree's root
@param target The object being sought
@return The object, if found, otherwise null
 */
private E findW(Node<E> localRoot, E target) {
	if (localRoot == null)
		return null;
	// Compare the target with the data field at the root.
	int compResult = target.compareTo(localRoot.data);
	if (compResult == 0)
		return localRoot.data;
	else if (compResult < 0)
		return findW(localRoot.left, target);
	else
		return findW(localRoot.right, target);
}


public E get(int i) {
		Node<E> temp = search(i,root);
	return temp.data;
	}
	
	public Node<E> search(int id) {
		return search(id, root);
	}
    
	private Node<E> search(int id, Node<E> node){
      
    	if(node != null){
            if(node.data.equals(id)){
               return node;
            } else {
                Node<E> foundNode = search(id, node.left);
                if(foundNode == null) {
                    foundNode = search(id, node.right);
                }
                return foundNode;
             }
        } else {
            return null;
        }
    } 

	/**
	 *this is a method for printing the subtree from any particular node(which is found
	 *by the find method below) 
	 * @param rootItem
	 */
	public void BFSTraversal(E rootItem){
	//set root to item
	
		Node<E> root = null;
		
		root = this.find(this.getRoot(), rootItem);
	
	//if item was not found, root will be null
		
		ArrayQueue<Node<E>> q = new ArrayQueue<Node<E>>();
		int levelNodes = 0; 
		if(root == null)return;
		q.add(root);
 		while(!q.isEmpty()){
 			levelNodes = q.size();
 			while(levelNodes>0){
				Node<E> n = (Node<E>)q.remove();
				System.out.print(" "+n.data);
				if(n.right!=null){ q.add(n.right);}
				if(n.left!=null) {q.add(n.left);}
				levelNodes--;
			}
 			System.out.println("\n ");
		}
	}
	
/*
 * this is a helper method for find a certain node with a target 
 *  
 */
	private Node<E> find(Node<E> localRoot, E target) {
		if (localRoot == null)
			return null;
		// Compare the target with the data field at the root.
		int compResult = target.compareTo(localRoot.data);
		if (compResult == 0)
			return localRoot;
		else if (compResult < 0)
			return find(localRoot.right, target);
		else
			return find(localRoot.left, target);
	}   

	
/* this method is for deleting a node   
 * 
 */
    public E delete(E target) {
 
	 root = delete(root, target);
	 return deleteReturn;
}
 
 private Node<E> delete(Node<E> localRoot, E item) {
	 if (localRoot == null) {
		 // item is not in the tree.
		 deleteReturn = null;
		 return localRoot;
	 }
	 // Search for item to delete.
	 int compResult = item.compareTo(localRoot.data);
	 if (compResult < 0) {
		 // item is smaller than localRoot.data.
		 localRoot.left = delete(localRoot.left, item);
		 return localRoot;
	 } else if (compResult > 0) {
		 // item is larger than localRoot.data.
		 localRoot.right = delete(localRoot.right, item);
		 return localRoot;
	 } else {
		 // item is at local root.
		 deleteReturn = localRoot.data;
		 if (localRoot.left == null) {
			 // If there is no left child, return right child
			 // which can also be null.
			 return localRoot.right;

		 } else if (localRoot.right == null) {
			 // If there is no right child, return left child.
			 return localRoot.left;
		 } else {
			 // Node being deleted has 2 children, replace the data
			 // with inorder predecessor.
			 if (localRoot.left.right == null) {
				 // The left child has no right child.
				 // Replace the data with the data in the
				 // left child.
				 localRoot.data = localRoot.left.data;
				 // Replace the left child with its left child.
				 localRoot.left = localRoot.left.left;
				 return localRoot;
			 } else {
				 // Search for the inorder predecessor (ip) and
				 // replace deleted node's data with ip.
				 localRoot.data = findLargestChild(localRoot.left);
				 return localRoot;
			 }
		 }
	 }
 }

 /*
  * this method is a helper method for finding the largest child in the sub tree
  * preceding the deleted node in an inorder traversing way
  */
 private E findLargestChild(Node<E> parent) {
	 // If the right child has no right child, it is
	 // the inorder predecessor.
	 if (parent.right.right == null) {
		 E returnValue = parent.right.data;
		 parent.right = parent.right.left;
		 return returnValue;
	 } else {
		 return findLargestChild(parent.right);
	 }
 } 
     
    public int size(){
		return size;
	}
    
    public boolean isEmpty(){
    	return null == root; 
    }

    public Node<E> getRoot() {
        return root;
    }
    
/**
 * adding a new node into the binary tree     
 * @param item
 */
public boolean add(E item){
    	Node<E> newNode  = new Node<>(item);
		
		if(root==null){
			root = newNode;
			size++;
			return true;
		}
		Node<E> current = root;
		Node<E> parent = null;
		while(true){
			parent = current;
			if(item.compareTo(current.data)>0){				
				current = current.left;
				if(current==null){
					parent.left = newNode;
					size++;
					return true;
				}
			}else{
				current = current.right;
				if(current==null){
					parent.right = newNode;
					size++;
					return true;
				}
			}
		}
	}

/*
 * reset the bst 
 */
    public void reset(){ root = null;}

    /**
     * this is the method for breadth first traversal from the root of the tree
     */
	public void breadthFirstTraversal(){
		
		Node<E> temp = root;
 		
 		ArrayQueue<Node<E>> q = null;
 		q = new ArrayQueue<Node<E>>();
 			
 		int levelNodes =0; 
		if(temp==null) return;
 		q.add(temp);
 		while(!q.isEmpty()){
 			levelNodes = q.size();
 			while(levelNodes>0){
				Node<E> n = (Node<E>)q.remove();
				System.out.print(" " + n.data);
				if(n.right!=null) q.add(n.right);
				if(n.left!=null) q.add(n.left);
				levelNodes--;
			}
		}
 		System.out.println(" \n");
	}



} 

