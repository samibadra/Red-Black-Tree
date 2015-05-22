/*
 Sami Badra
 
 CS 310, Spring 2014
 Assignment #3, red-black BST
 FILE: RedBlackTree.java
 
 Copyright (c) 2015 Sami Badra. All rights reserved.
 */

public class RedBlackTree<E> {
	int currentSize;
	Node<E> root;
	
	/**
	 * constructor for creating a red-black tree
	 */
	public RedBlackTree() {
		currentSize = 0;
		root = null;
	}
	
	/**
	 * Creates a new node
	 * sets the data to the generic parameter passed in
	 * defaults both left and right children to null
	 *
	 * @param <E> generic type being stored in the tree
	 */
	class Node <E> implements Comparable<Node<E>> {
		E data;
		Node<E> leftChild;
		Node<E> rightChild;
		Node<E> parent;
		boolean isRed;
		public Node (E obj) {
			data = obj;
			leftChild = null;
			rightChild = null;
			isRed = true;
		}
		public int compareTo(Node<E> node) {
			return (((Comparable<E>)data).compareTo((E)node.data));
		}
	}
	
	/**
	 * adds a new node to the tree at the correct null leaf node
	 * re-balances the tree if adding the new node caused a violation
	 * @param obj generic data being added to tree
	 * @return true if object was added and false if not added
	 */
	public boolean add(E obj) {
		if (root == null) {							//tree is empty
			Node<E> newNode = new Node<E>(obj);
			root = newNode;
			currentSize++;
			root.isRed = false;
			return true;
		}
		Node<E> tmp = root;
		while(true) {
			if  (((Comparable<E>)obj).compareTo((E)tmp.data) >= 0) {	//obj is larger than tmp.data
				if (tmp.rightChild == null) {						//reached a null subtree
					Node<E> newNode = new Node<E>(obj);
					newNode.parent = tmp;							//set parent pointer
					tmp.rightChild = newNode;
					currentSize++;
					if (newNode.isRed && newNode.parent.isRed) {	//consecutive red node violation
						balance(newNode, newNode.parent);
					}
					return true;
				}
				else {
					tmp = tmp.rightChild;							//traverse down tree
				}
			}
			else {													//obj is smaller than tmp.data
				if (tmp.leftChild == null) {						//reached a null subtree
					Node<E> newNode = new Node<E>(obj);
					newNode.parent = tmp;							//set parent pointer
					tmp.leftChild = newNode;
					currentSize++;
					if (newNode.isRed && newNode.parent.isRed) {	//consecutive red node violation
						balance(newNode, newNode.parent);
					}
					return true;
				}
				else {
					tmp = tmp.leftChild;						//traverse down tree
				}
			}
		}
	}
	
	/**
	 * recursion method to traverse down tree, starting at the root
	 * checks to see if the tree contains a certain piece of data
	 * @param obj the data that is trying to be found
	 * @return true if the data was found and false if the data was not found
	 */
	public boolean find(E obj) {
		return find(root,obj);								//starts at the root node
	}
	
	/**
	 * recursion method to traverse down tree if the data was not found
	 * @param current the current node being compared to
	 * @param toFind the data that is trying to be found
	 * @return true if the data was found and false if the data was not found
	 */
	public boolean find(Node<E> current, E toFind) {
		if (current == null) {								//data not found
			return false;
		}
		if (((Comparable<E>)current.data).compareTo(toFind) == 0) {	//data was found, return true
			return true;
		}
		if (((Comparable<E>)current.data).compareTo(toFind) < 0) {	//data to find is larger than current node data
			return find(current.rightChild, toFind);				//move to right subtree
		}
		return find(current.leftChild,toFind);	//data to find is smaller than current node data, move to left subtree
	}
	
	/**
	 * re-balances the tree if a violation has occurred
	 * performs proper rotation or color-flip based on the aunt of the node that caused the violation
	 * re-calls it self the the rotation/color-flip causes a new violation
	 * @param node the node that caused the violation
	 * @param parent the parent of the node that caused the violation
	 */
	public void balance(Node<E> node, Node<E> parent) {
		Node<E> newTop = null;
		Node<E> grandpa = parent.parent;
		Node<E> aunt = auntOf(node);
		if (aunt == null || !aunt.isRed) {	//aunt node is black or null... rotate
			if (node == parent.rightChild) {
				if (parent == grandpa.rightChild) {	//error is in the (grandfathers) right child's right child
					newTop = leftRotation(grandpa);
					if (grandpa != root) {
						newTop.parent = grandpa.parent;		//sets the new parent pointer
						if (grandpa.parent.rightChild == grandpa) {
							grandpa.parent.rightChild = newTop;		//sets new child pointer
						}
						else if (grandpa.parent.leftChild == grandpa) {
							grandpa.parent.leftChild = newTop;		//sets new child pointer
						}
					}
					else {					//grandpa was the root node
						root = newTop;			//reset root node
						root.parent = null;
					}
					newTop.leftChild.parent = newTop;		//sets new parent pointer
					newTop.isRed = false;
					newTop.leftChild.isRed = true;
					newTop.rightChild.isRed = true;
					root.isRed = false;
				}
				else if (parent == grandpa.leftChild) {	//error is in the (grandfathers) left child's right child
					leftRotation(parent);
					grandpa.leftChild = node;
					newTop = rightRotation(grandpa);
					if (grandpa != root) {
						newTop.parent = grandpa.parent;			//sets new parent pointer
						if (grandpa.parent.rightChild == grandpa) {
							grandpa.parent.rightChild = newTop;	//sets new child pointer
						}
						else if (grandpa.parent.leftChild == grandpa) {
							grandpa.parent.leftChild = newTop;	//sets new child pointer
						}
					}
					else {							//grandpa was root node
						root = newTop;				//resets root node
						root.parent = null;
					}
					newTop.leftChild.parent = newTop;	//sets new parent pointer
					newTop.rightChild.parent = newTop;	//sets new parent pointer
					newTop.isRed = false;
					newTop.leftChild.isRed = true;
					newTop.rightChild.isRed = true;
					root.isRed = false;
				}
			}
			else if (node == parent.leftChild) {
				if (parent == parent.parent.rightChild) {	//error is in the (grandfathers) right child's left child
					rightRotation(parent);
					grandpa.rightChild = node;
					newTop = leftRotation(grandpa);
					if (grandpa != root) {
						newTop.parent = grandpa.parent;		//sets new parent pointer
						if (grandpa.parent.rightChild == parent.parent) {
							grandpa.parent.rightChild = newTop;		//sets new child pointer
						}
						else if (grandpa.parent.leftChild == parent.parent) {
							grandpa.parent.leftChild = newTop;		//sets new child pointer
						}
					}
					else {						//grandpa was root node
						root = newTop;			//resets root node
						root.parent = null;
					}
					newTop.leftChild.parent = newTop;	//sets new parent pointer
					newTop.rightChild.parent = newTop;	//sets new parent pointer
					newTop.isRed = false;
					newTop.leftChild.isRed = true;
					newTop.rightChild.isRed = true;
					root.isRed = false;
				}
				else if (parent == grandpa.leftChild) {	//error is in the (grandfathers) left child's left child
					newTop = rightRotation(grandpa);
					if (grandpa != root) {
						newTop.parent = grandpa.parent;		//sets new parent pointer
						if (grandpa.parent.rightChild == grandpa) {
							grandpa.parent.rightChild = newTop;	//sets new child pointer
						}
						else if (grandpa.parent.leftChild == grandpa) {
							grandpa.parent.leftChild = newTop;	//sets new child pointer
						}
					}
					else {						//grandpa was root node
						root = newTop;			//resets the root node
						root.parent = null;
					}
					newTop.rightChild.parent = newTop;	//sets new parent pointer
					newTop.isRed = false;
					newTop.leftChild.isRed = true;
					newTop.rightChild.isRed = true;
					root.isRed = false;
				}
			}
		}
		else {								//aunt node is red... color flip
			parent.isRed = false;			//color flip to black
			aunt.isRed = false;				//color flip to black
			grandpa.isRed = true;			//color flip to red
			root.isRed = false;
			newTop = grandpa;
		}
		if (root != newTop) {
			if (newTop.isRed && newTop.parent.isRed) {	//rotate/color flip caused a new violation
				balance(newTop, newTop.parent);
			}
		}
	}
	
	/**
	 * default in-order method which prints out all the
	 * nodes in the tree in order starting at the root
	 */
	public void inorder() {
		inorder(root);
	}
	
	/**
	 * recursive method which prints out all the nodes
	 * in the tree in order starting at a particular node
	 * @param n the current node being looked at
	 */
	public void inorder (Node<E> n) {
		if (n != null) {
			inorder(n.leftChild);
			System.out.println(n.data);
			inorder(n.rightChild);
		}
	}
	
	/**
	 * returns the current number of nodes/data stored in the tree
	 * @return the number of items stored in the tree
	 */
	public int size() {
		return currentSize;
	}
	
	/**
	 * empties all the data from the tree
	 */
	public void clear() {
		root = null;
		currentSize = 0;
	}
	
	/**
	 * recursive method that counts the number of edges in
	 * the longest path in the tree from root to leaf node
	 * @return the height of longest path in the tree
	 */
	public int height() {
		if (root == null) {
			return 0;
		}
		else {
			return height(root);
		}
	}
	
	/**
	 * recursive method that counts the number of edges in
	 * the longest path in the tree from root to leaf node
	 * @param node current node being looked at
	 * @return 1 more than the height to the leaf node
	 */
	public int height(Node<E> node) {
		int leftHeight = -1;
	    int rightHeight = -1;
	    if (node.leftChild != null) {
	        leftHeight = height(node.leftChild);
	    }
	    if (node.rightChild != null) {
	        rightHeight = height(node.rightChild);
	    }
	    if (leftHeight > rightHeight) {
	        return leftHeight+1;
	    }
	    else {
	        return rightHeight+1;
	    }
	}
	
	/**
	 * counts the number of black nodes currently in the tree
	 * starting at the root node
	 * @return the number of black nodes in the tree
	 */
	public int countBlack() {
		return countBlack(root);
	}
	/**
	 * recursively counts the number of
	 * black nodes currently in the tree
	 * @param node
	 * @return the number of black nodes in the tree
	 */
	public int countBlack(Node<E> node) {
		if (node == null) {
			return 0;
		}
		int count = 0;
		if (node.leftChild != null) {
			count += countBlack(node.leftChild);
		}
		if (node.rightChild != null) {
			count += countBlack(node.rightChild);
		}
		if (!node.isRed) {
			count++;
		}
		return count;
	}
	
	/**
	 * retrieves the aunt of the node being passed in
	 * @param node the node of which this method finds the aunt of
	 * @return the aunt of the node that was passed in
	 */
	public Node<E> auntOf(Node<E> node) {
		if (node.parent.parent.rightChild == node.parent) {
			return node.parent.parent.leftChild;
		}
		if (node.parent.parent.leftChild == node.parent) {
			return node.parent.parent.rightChild;
		}
		return null;
	}
	
	/**
	 * rotates the node being passed in
	 * to the left of its right child
	 * @param node the node being rotated
	 * @return returns the new top node after the rotation
	 */
	public Node<E> leftRotation(Node<E> node){
		Node<E> newTop = node.rightChild;
		node.rightChild = newTop.leftChild;
		if (newTop.leftChild != null) {
			newTop.leftChild.parent = node;			//sets new parent pointer
		}
		newTop.leftChild = node;
		return newTop;
	}
	
	/**
	 * rotates the node being passed in
	 * to the right of its left child
	 * @param node the node being rotated
	 * @return returns the new top node after the rotation
	 */
	public Node<E> rightRotation(Node <E> node) {
		Node<E> newTop = node.leftChild;
		node.leftChild = newTop.rightChild;
		if (newTop.rightChild != null) {
			newTop.rightChild.parent = node;		//sets new parent pointer
		}
		newTop.rightChild = node;
		return newTop;
	}
}
