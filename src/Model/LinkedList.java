package CSE210_Assignment.src.Model;
/*************************************************************************
 *
 *  A generic LinkedList, implemented using a singly-linked list.
 *  Each LinkedList element is of type Item.
 *
 *  This version uses a static nested class Node 
 *  
 *
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.AbstractList;

/**
 *  The <tt>LinkedList</tt> class represents a ordered list of generic items.
 *  It supports the usual <em>add</em> and <em>remove</em> operations, along with methods
 *  for peeking at the item at specific index, testing if the list is empty, and iterating through
 *  the items in order.
 *  <p>
 *  This implementation uses a singly-linked list with a static nested class for
 *  linked-list nodes. 
 *  The <em>add</em>, <em>remove</em>, <em>locate</em>, <em>size</em>, and <em>is-empty</em>
 *  operations all take constant time in the worst case.
 *  <p>
 *
 *  @author Yudi An
 */
public class LinkedList<Item> extends AbstractList<Item> implements List<Item>, Iterable<Item> {
    
    private Node head; // head pointer of the list
    private Node tail; // tail pointer of the list
    private int size; // number of the items in the list

    // Node of item and pointers
    private class Node
    {
        Item item;
        Node next;
        Node previous;
    }

    /** 
    * Constructors 
	* Initialize a list to be empty
	*/ 
    public LinkedList() {
        head = null;
        tail = null;
        size=0;
    }

    /**
	* locate the position to insert new item
	* @param newItem Item newItem item to be added
	* @return the position to add new item
	*/
    public Node locate(Item newItem) {
    	if(head == null)
    		return null;
	    Node next = head;
	    while(((Comparable)(next.item)).compareTo(newItem) >= 0){
	    	if(next.next == null)
	    		return next;
	    	next=next.next;
	    }
	    return next.previous;
    }

    /** 
	* to add an Item to a list.
	* @param  newItem item to be added
	* @return whether the item added successfully
	*/
    public boolean add(Item newItem) {
        if (newItem == null) { throw new NullPointerException(); }
        Node insertPoint = locate(newItem);
        // the list is empty or the item added at the head
        if (insertPoint == null){

        	Node tmp = head;
    		head = new Node();
        	head.item = newItem;
        	head.previous = null;
        	head.next = tmp;
        	if(tmp == null)
        		tail = head;
        	else
        		tmp.previous = head;
        }
        // the item added at the tail
        else if(insertPoint.next == null){
        	insertPoint.next = new Node();
        	insertPoint.next.item = newItem;
        	insertPoint.next.previous = insertPoint;
        	insertPoint.next.next = null;
        	tail = insertPoint.next;
        // the item added at the middle
        } else {
        	Node tmp = insertPoint.next;
        	insertPoint.next = new Node();
        	insertPoint.next.item = newItem;
        	insertPoint.next.previous = insertPoint;
        	insertPoint.next.next = tmp;
        	tmp.previous = insertPoint.next;
        }
        size++;
        return true;
    }

    /**
     * Extracts the exact one of a list. The list itself is left unchanged.
     * @param n the sequence of the item to get
     * @return the item exacted
     */
    public Item get(int n) {
        int total = this.size();
        Node current;
        if(n<0 || n>=total) {
            throw new IndexOutOfBoundsException();
        }
        if(n<total/2) {
            current = head;
            for(int i=0; i<n; i++) {
                current=current.next;
            }
        } else {
            current = tail;
            for(int i=0; i<total-n-1; i++) {
                current=current.previous;
            }
        }
        return (Item) current.item;
    }

    /**
     * Remove the exact one of a list.
     * @param n the sequence of the item to remove
     * @return the item removed
     */
    public Item remove(int n) {
    	int total = this.size();
        Node current;
        if(n<0 || n>=total) {
            throw new IndexOutOfBoundsException();
        }
        if(n<total/2) {
            current = head;
            for(int i=0; i<n; i++) {
                current=current.next;
            }
        } else {
            current = tail;
            for(int i=0; i<total-n-1; i++) {
                current=current.previous;
            }
        }

        if(current.previous==null)
        	head = current.next;
        if(current.next==null)
        	tail = current.previous;
        if(current.next!=null)
        	current.next.previous = current.previous;
        if(current.previous!=null)
        	current.previous.next = current.next;
        size--;
        return (Item) current.item;
    }

    /**
	* @return the number of elements in this list.
	*/
    public int size() {
        return size;
    }

    /**
     * @return whether a list is empty or not.
     */
    public boolean isEmpty() {
        return size==0;
    }

    /**
     * @return a string representation of a list. For example
     */
    public String toString() {
        if ( this.isEmpty() ) {
            return "[]";
        }
        else {
            String result = "["+head.item;
            Node next = head.next;
            while(next != null) {
                result=result+","+next.item;
                next=next.next;
            }
         return result+"]";
        }
    }

    /**
     * @return an iterator over items in order from front to end
     */ 
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>
    {
        private Node current = head;
        public boolean hasNext() { return current != null; }
        public void remove() {
            throw new UnsupportedOperationException();
        } 
        public Item next()
        {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }

    // unit test for LinkedList
    // public static void main(String[] args) {

    // }
    
}
