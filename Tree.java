/*
 * 
 * 
 *  Copyright 2017 WJClub
 *    wjclub.tk
 *    github.com/wjclub
 * 
 * 
 */
//region package
package tk.wjclub.avl;
//endregion
//region imports
import java.util.Vector;

import tk.wjclub.avl.Tree.Node;
//endregion
//region Tree-Class
public class Tree<T extends Comparable<? super T>> {
		//region inner tree node class
	  	protected static class Node<T> {
		  //region node properties
		  protected T  element;
		  protected Node<T>    left;
		  protected Node<T>    right;
		  protected int      height;
		  //endregion
		  //region constructors
		  public Node (T x){
			  this (x, null, null);
		  }
		  
		  public Node (T theElement, Node<T> lt, Node<T> rt){
			  element = theElement;
			  left = lt;
			  right = rt;
		  }
		  //endregion
		  //region public methods
		  public int getHeight() {
			  return height;
		  }
		  //endregion
	  }
	  	//endregion
	  	//region inner helper classes/structs
	  	enum outputType {
			preOrder, inOrder, postOrder, withNullPreOrder
		}
	   
	   enum rotationDirection {
		   left, right
	   }

	   class depthNode {
		   public depthNode(int depth, Node<T> node) {
			   this.depth = depth;
			   this.node = node;
		   }

		   public int depth;
		   public Node<T> node;
	   }
	   //endregion
	   //region main class properties
	   public Node<T> root;
	   public int countInsertions;
	   public int countSingleRotations;
	   public int countDoubleRotations;
	   //endregion
	   //region constructors
	   public Tree () {
		   root = null;
		   countInsertions = 0;
		   countSingleRotations = 0;
		   countDoubleRotations = 0;    
	   }
	   //endregion
	   //region methods
	   public int height() {
		   return height(this.root);
	   }
	  
	   public int height (Node<T> t){
		   return t == null ? -1 : t.height;
	   }
	  
	   public int realHeight() {
		   return height(this.root);
	   }
	  
	   public int realHeight (Node<T> t){
		   return t == null ? -1 : (t.height + 1);
	   }
	  
	   public int max (int a, int b){
		   if (a > b)
			   return a;
		   return b;
	   }
	   
	  public boolean insert (T x){
	    try {
	      root = insert (x, root);
	      countInsertions++;
	      return true;
	    } catch(DuplicateValueException e) {
	      return false;
	    }
	  }
	  
	  protected Node<T> insert (T x, Node<T> node) throws DuplicateValueException {
	    if (node == null) {
	    	node = new Node<T> (x);
	    } else if (x.compareTo (node.element) < 0) {
	    	System.out.println("peanuts");
	      node.left = insert(x, node.left);
	    } else if (x.compareTo (node.element) > 0) {
	    	System.out.println("hello");
	      node.right = insert (x, node.right);
	    } else if (x.compareTo(node.element) == 0) {
	      throw new DuplicateValueException();
	    }
	    node = rotate(node);
	    node.height = max (height (node.left), height (node.right)) + 1;
	    return node;
	  }
	  
	  protected Node<T> rotate(Node<T> node) {
		  int deltaHeight = height(node.right) - height(node.left);	
		  if (Math.abs(deltaHeight) > 1) {
			  if (deltaHeight <= -2) {
				  //region left
				  System.out.println("test");
				  if (deltaHeight == -2) {
					  Node<T> tempNode = node.left;
					  node.left = tempNode.right;
					  tempNode.right = node;
					  node.height = max(height(node.left), height(node.right)) + 1;
					  tempNode.height = max (height (tempNode.right), node.height) + 1;
					  return tempNode;
				  } else {
					  node.left = rotate(node.left);
					  return node;
				  }
				  //endregion
			  } else if (deltaHeight >= 2) {
				  System.out.println("ing");
				  //region right
				  if (deltaHeight == 2) {
					  /*if () {*/
						  Node<T> tempNode = node.right;
						  node.right = tempNode.left;
						  tempNode.left = node;
						  node.height = max(height(tempNode.left), height(tempNode.right)) + 1;
						  tempNode.height = max (height (tempNode.left), node.height) + 1;
						  return tempNode; 
					  /*}*/
				  } else {
					  node.right = rotate(node.right);
					  return node;
				  }
				  //endregion
			  }
		  } return node;
	  }
	  
	  /*protected Node<T> rotateWithLeftChild (Node<T> k2){
	    Node<T> k1 = k2.left;
	    
	    k2.left = k1.right;
	    k1.right = k2;
	    
	    k2.height = max (height (k2.left), height (k2.right)) + 1;
	    k1.height = max (height (k1.left), k2.height) + 1;
	    
	    return (k1);
	  }
	  
	  protected Node<T> doubleWithLeftChild (Node<T> k3){
	    k3.left = rotateWithRightChild (k3.left);
	    return rotateWithLeftChild (k3);
	  }*/
	  
	  /*protected Node<T> rotateWithRightChild (Node<T> k1){
	    Node<T> k2 = k1.right;
	    
	    k1.right = k2.left;
	    k2.left = k1;
	    
	    k1.height = max (height (k1.left), height (k1.right)) + 1;
	    k2.height = max (height (k2.right), k1.height) + 1;
	    
	    return (k2);
	  }
	  
	  protected Node<T> doubleWithRightChild (Node<T> k1){
	    k1.right = rotateWithLeftChild (k1.right);
	    return rotateWithRightChild (k1);
	  }*/
	 
	  public void makeEmpty(){
	    root = null;
	  }
	  
	  public boolean isEmpty(){
	    return (root == null);
	  }
	  
	  public T findMin() {
	    if(isEmpty()) 
	    	return null;
	    return findMin(root).element;
	  }
	  
	  public T findMax() {
	    if(isEmpty()) 
	    	return null;
	    return findMax(root).element;
	  }
	  
	  private Node<T> findMin(Node<T> t) {
	    if(t == null)
	    	return t;
	    while(t.left != null)
	    	t = t.left;
	    return t;
	  }
	  
	  private Node<T> findMax(Node<T> t) {
	    if(t == null)
	    	return t;
	    while(t.right != null)
	    	t = t.right;
	    return t;
	  }
	  
	  /*public void remove(T x) {
	    root = remove(x, root);
	  }*/
	  
	  /*public Node<T> remove(T x, Node<T> t) {
	    if (t==null)    {
	      return null;
	    }
	    if (x.compareTo(t.element) < 0 ) {
	      t.left = remove(x,t.left);
	      int l = t.left != null ? t.left.height : 0;
	      
	      if((t.right != null) && (t.right.height - l >= 2)) {
	        int rightHeight = t.right.right != null ? t.right.right.height : 0;
	        int leftHeight = t.right.left != null ? t.right.left.height : 0;
	        
	        if(rightHeight >= leftHeight)
	        t = rotateWithLeftChild(t);            
	        else
	        t = doubleWithRightChild(t);
	      }
	    }
	    else if (x.compareTo(t.element) > 0) {
	      t.right = remove(x,t.right);
	      int r = t.right != null ? t.right.height : 0;
	      if((t.left != null) && (t.left.height - r >= 2)) {
	        int leftHeight = t.left.left != null ? t.left.left.height : 0;
	        int rightHeight = t.left.right != null ? t.left.right.height : 0;
	        if(leftHeight >= rightHeight)
	        t = rotateWithRightChild(t);               
	        else
	        t = doubleWithLeftChild(t);
	      }
	    }
	    else if(t.left != null) {
	      t.element = findMax(t.left).element;
	      remove(t.element, t.left);
	      
	      if((t.right != null) && (t.right.height - t.left.height >= 2)) {
	        int rightHeight = t.right.right != null ? t.right.right.height : 0;
	        int leftHeight = t.right.left != null ? t.right.left.height : 0;
	        
	        if(rightHeight >= leftHeight)
	        t = rotateWithLeftChild(t);            
	        else
	        t = doubleWithRightChild(t);
	      }
	    }
	    else
	    t = (t.left != null) ? t.left : t.right;
	    if(t != null) {
	      int leftHeight = t.left != null ? t.left.height : 0;
	      int rightHeight = t.right!= null ? t.right.height : 0;
	      t.height = Math.max(leftHeight,rightHeight) + 1;
	    }
	    return t;
	  } */
	  
	  public boolean contains(T x){
	    return contains(x, root); 
	  }
	  
	  protected boolean contains(T x, Node<T> t) {
	    if (t == null){
	      return false;
	    } else if (x.compareTo(t.element) < 0){
	      return contains(x, t.left);
	    } else if (x.compareTo(t.element) > 0){
	      return contains(x, t.right);
	    }
	    return true;
	  }
	  
	  public boolean checkBalanceOfTree(Tree.Node<Integer> current) {
	    boolean balancedRight = true, balancedLeft = true;
	    int leftHeight = 0, rightHeight = 0;
	    if (current.right != null) {
	      balancedRight = checkBalanceOfTree(current.right);
	      rightHeight = getDepth(current.right);
	    }
	    if (current.left != null) {
	      balancedLeft = checkBalanceOfTree(current.left);
	      leftHeight = getDepth(current.left);
	    }
	    return balancedLeft && balancedRight && Math.abs(leftHeight - rightHeight) < 2;
	  }
	  
	  public int getDepth(Tree.Node<Integer> n) {
	    int leftHeight = 0, rightHeight = 0;
	    if (n.right != null)
	    	rightHeight = getDepth(n.right);
	    if (n.left != null)
	    	leftHeight = getDepth(n.left);
	    return Math.max(rightHeight, leftHeight)+1;
	  }
	  
	  public boolean checkOrderingOfTree(Tree.Node<Integer> current) {
	    if(current.left != null) {
	      if(current.left.element.compareTo(current.element) > 0)
	    	  return false;
	      else
	    	  return checkOrderingOfTree(current.left);
	    } else if (current.right != null) {
	      if(current.right.element.compareTo(current.element) < 0)
	    	  return false;
	      else
	    	  return checkOrderingOfTree(current.right);
	    } else if(current.left == null && current.right == null)
	    	return true;
	    return true;
	  }
	  
	  private Vector<Node<T>> vecOut(outputType type) {
		  return vecOut(type, root);
	  }
	  
	  private Vector<Node<T>> vecOut(outputType type, Node<T> n) {
		  return vecOut(type, n, new Vector<Node<T>>());
	  }
	  
	  private Vector<Node<T>> vecOut(outputType type, Node<T> n, Vector<Node<T>> vector) {
		  if (n != null) {
			  if (type == outputType.preOrder)
				  addNodeToVectorNotNull(vector, n);
			  if (type == outputType.withNullPreOrder)
				  vector.add(n);
			  vecOut(type, n.left, vector);
			  if (type == outputType.inOrder)
				  addNodeToVectorNotNull(vector, n);
			  vecOut(type, n.right, vector);
			  if (type == outputType.postOrder)
				  addNodeToVectorNotNull(vector, n);
		  }
		  return vector;
	  }
	  
	  private Vector<depthNode> withNullVecOut() {
		  return withNullVecOut(root);
	  }
	  
	  private Vector<depthNode> withNullVecOut(Node<T> n) {
		  return withNullVecOut(n, new Vector<depthNode>());
	  }
	  
	  private Vector<depthNode> withNullVecOut(Node<T> n, Vector<depthNode> vector) {
		  return withNullVecOut(n, vector, 0);
	  }
	  
	  private Vector<depthNode> withNullVecOut(Node<T> n, Vector<depthNode> vector, int currentDepth) {
		  vector.add(new depthNode(currentDepth, n));
		  if (n != null) {
			  withNullVecOut(n.left, vector, currentDepth + 1);
			  withNullVecOut(n.right, vector, currentDepth + 1);
			   
		  }
		  return vector;
	  }
	  
	  private void addNodeToVectorNotNull(Vector<Node<T>> vector, Node<T> n) {
		  if (n.element != null)
   		   vector.add(n);
	  }   
	  
	  private String strOut(outputType type) {
	    	Vector<Node<T>> vec = vecOut(type);
	    	char[] typeChars = type.toString().toCharArray();
	    	typeChars[0] = String.valueOf(typeChars[0]).toUpperCase().charAt(0);
	    	String output = String.valueOf(typeChars) + ": ";
	    	for (Node<T> node : vec) {
	    		output += node.element.toString() + " | ";
	    	}
	    	output = output.substring(0, output.length() - 3);
	    	return output;
	   }
	  
	  public String echoPreOrder() {
		  return strOut(outputType.preOrder);
	  }
	  
	  public String echoInOrder() {
		  return strOut(outputType.inOrder);
	  }
	  
	  public String echoPostOrder() {
		  return strOut(outputType.postOrder);
	  }

	   public Integer[][] getAsArray() {
		   T test = null;
		   if (test instanceof Integer)
			   return null;
		   Vector<depthNode> vec = withNullVecOut();
		   Vector<Integer>[] result = new Vector[height() + 2];
		   for (depthNode n : vec) {
			   if (result[n.depth] == null)
				   result[n.depth] = new Vector<Integer>();
			   if (!(n == null || n.node == null || n.node.element == null))
				   result[n.depth].add((Integer)n.node.element);
			   else 
				   result[n.depth].add(null);
		   }
		   Integer[][] output = new Integer[result.length][(int) Math.pow(2, realHeight())];
		   for (int i = 0; i < result.length; i++) {
			   if (result[i] != null)
				   result[i].toArray(output[i]);
		   }
		   return output;
	   }
	   //endregion		
}
//endregion
//region Custom Exception
class DuplicateValueException extends Throwable {
	/**
	 * Empty unique Throwable for duplicate values on insert
	 */
	private static final long serialVersionUID = -1370833320065830235L;
}
//endregion