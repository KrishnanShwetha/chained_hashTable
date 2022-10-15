/*
 /*
 * ChainedHashTable.java
 *
 * Computer Science 112, Boston University
 * 
 * Modifications and additions by:
 *     name:
 *     email:
 */

import java.util.*;     // to allow for the use of Arrays.toString() in testing

/*
 * A class that implements a hash table using separate chaining.
 */
public class ChainedHashTable implements HashTable {
    /* 
     * Private inner class for a node in a linked list
     * for a given position of the hash table
     */
    private class Node {
        private Object key;
        private LLQueue<Object> values;
        private Node next;
        
        private Node(Object key, Object value) {
            this.key = key;
            values = new LLQueue<Object>();
            values.insert(value);
            next = null;
        }
    }
    
    private Node[] table;      // the hash table itself
    private int numKeys;       // the total number of keys in the table
        
    /* hash function */
    public int h1(Object key) {
        int h1 = key.hashCode() % table.length;
        if (h1 < 0) {
            h1 += table.length;
        }
        return h1;
    }
    
    /*** Add your constructor here ***/
    public ChainedHashTable(int size) {
    	if (size <= 0) {
    		throw new IllegalArgumentException();
    	}
    	table = new Node[size];
    }
    /*** Add the other required methods here ***/
    /*
     * insert - insert the specified (key, value) pair in the hash table.
     * Returns true if the pair can be added and false if there is overflow.
     */
    
    
   
         
        public boolean insert(Object key, Object value) {
    	if (key == null) {
            throw new IllegalArgumentException("key must be non-null");
        } else {
        	int i = h1(key);
        	Node trav = table[i];
        	while (trav != null) {
        		if (trav.key.equals(key)) {
        			break;
        		}
        		trav = trav.next;
        	}
        	if (trav != null) {
        		trav.values.insert(value);
        	} else {
        		Node newNode= new Node(key, value);
        		newNode.next = table[i];
        		table[i] = newNode;
        		numKeys ++;
        	}
        }
        return true;
    }

      
    
    /*
     * search - search for the specified key and return the
     * associated collection of values, or null if the key 
     * is not in the table
     */
    public Queue<Object> search(Object key) {
        if (key == null) {
        	throw new IllegalArgumentException();
        }
        int i = h1(key);
        Node trav = table[i];
    	while (trav!= null && !trav.key.equals(key)) {
    		trav = trav.next;
    	}
    	if (trav == null) {
    		return null;
    	}
    	else if (trav.key.equals(key)) {
    		return trav.values;
    	}
        return trav.values;
    }
        
        
    
    /* 
     * remove - remove from the table the entry for the specified key
     * and return the associated collection of values, or null if the key 
     * is not in the table
     */
    public Queue<Object> remove(Object key) {
    	if (key == null) {
        	throw new IllegalArgumentException();
        }
    	int i =  h1(key);
    	if (table[i] == null) {
    		return null;
    	}
    	LLQueue<Object> removedVal;
    	if (table[i].key.equals(key)) {
    		removedVal = table[i].values;
    		table[i] = table[i].next;
    		numKeys--;
    		return removedVal;
    	}
    	
    	Node trail = table[i];
    	Node trav = trail.next;
    	while (trav != null && !trav.key.equals(key)) {
    		trail = trav;
    		trav = trav.next;
    		
    	}
    	if (trav == null) {
    		return null;
    	}
    
    		removedVal = trav.values;
    		trail.next = trav.next;
    		numKeys--;
    		return removedVal;
    		
	
    	
    }
    	
    	
    	
    	
       
    
    public int getNumKeys() {
    	return numKeys;
    }
    
    public double load() {
    	int NumKeys = getNumKeys();
    	double size = table.length;
    	double load = NumKeys/size;
    	return load;
    }
    
   
   
    
    
    public void resize(int newSize) {
    	Node[] enlargedTable = new Node[newSize];
    	for (int i = 0; i < table.length; i++) {
    		Node trav = table[i];
    		while (trav != null) {
    			Node next = trav.next;
    			int p = trav.key.hashCode() % enlargedTable.length;
    	        if (p < 0) {
    	            p += enlargedTable.length;
    	        }
    	        trav.next = enlargedTable[p];
    	        enlargedTable[p] = trav;
    	        trav = next;
    		}
    	}
    	table = enlargedTable;
    }

   
 
    /*
     * toString - returns a string representation of this ChainedHashTable
     * object. *** You should NOT change this method. ***
     */
    public String toString() {
        String s = "[";
        
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                s += "null";
            } else {
                String keys = "{";
                Node trav = table[i];
                while (trav != null) {
                    keys += trav.key;
                    if (trav.next != null) {
                        keys += "; ";
                    }
                    trav = trav.next;
                }
                keys += "}";
                s += keys;
            }
        
            if (i < table.length - 1) {
                s += ", ";
            }
        }       
        
        s += "]";
        return s;
    }

    public static void main(String[] args) {
    	System.out.println("--- Testing method insert ---");
        System.out.println();
        ChainedHashTable table = new ChainedHashTable(7);

        System.out.println("inserting part 1");
        try {
        	
        	boolean result = table.insert("ant", 10);
        	System.out.println("actual results:");
            System.out.println(result);
            System.out.println("expected results:");
            System.out.println("true");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(result == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();    // include a blank line between tests
        
        
        System.out.println("testing insert part 2");
        try {
        	
        	boolean result1 = table.insert("howdy", 15);
        	boolean result2 = table.insert("goodbye", 10);
        	boolean result3 = table.insert("howdy", 25);
        	System.out.println("actual results:");
            System.out.println(result1);
            System.out.println(result2);
            System.out.println(result3);
            System.out.println("expected results:");
            System.out.println("true");
            System.out.println("true");
            System.out.println("false");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(result1 == true);
            System.out.println(result1 == true);
            System.out.println(result1 == false);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();  
        
        System.out.println("removing part 1");
        try {
        	
        	Queue<Object> result = table.remove("howdy");
        	System.out.println("actual results:");
            System.out.println(result);
            System.out.println("expected results:");
            System.out.println("true");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(result != null);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();    // include a blank line between tests
        
        
        
        System.out.println("removing part 2 ");
        try {
        	
        	Queue<Object> result = table.remove("ant");
        	System.out.println("actual results:");
            System.out.println(result);
            System.out.println("expected results:");
            System.out.println("true");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(result != null);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();    // include a blank line between tests
        
        
        
        
       
        
        
       
    
    
    
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }
    
    }