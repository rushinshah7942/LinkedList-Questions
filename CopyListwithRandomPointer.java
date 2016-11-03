/*

A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.
Return a deep copy of the list.

*/

// Pro: Does not modify the input linkedlist
// Con: uses extra data strcture HashMap
/**
 * Definition for singly-linked list with a random pointer.
 * class RandomListNode {
 *     int label;
 *     RandomListNode next, random;
 *     RandomListNode(int x) { this.label = x; }
 * };
 */
public class Solution {
    public RandomListNode copyRandomList(RandomListNode head) {
        if(head == null)
            return head;
        
        HashMap<RandomListNode, RandomListNode> map = new HashMap<>();
        RandomListNode newHead = new RandomListNode(head.label);
        RandomListNode p = head;
        RandomListNode q = newHead;
        
        map.put(head,newHead);
        
        p = p.next;
        // store node and it's copy into hashamp
        while(p!=null)
        {
            RandomListNode temp = new RandomListNode(p.label);
            map.put(p,temp);
            q.next = temp;
            q = temp;
            p = p.next;
        }
        // now start again and assign random pointers
        p = head;
        q = newHead;
        
        while(p!=null)
        {
            if(p.random != null){
                q.random  = map.get(p.random);
            }
            else{
                q.random = null;
            }
            p = p.next;
            q = q.next;
        }       
        return newHead;
    }
}


// Pro: Below solution does not use any data structrue like HashMap 
// con: Modifies given linkedlist and then restore it, which is not a good and practica approach
/**
 * Definition for singly-linked list with a random pointer.
 * class RandomListNode {
 *     int label;
 *     RandomListNode next, random;
 *     RandomListNode(int x) { this.label = x; };
 * };
 */
public RandomListNode copyRandomList(RandomListNode head) {
	RandomListNode iter = head, next;

	// First round: make copy of each node,
	// and link them together side-by-side in a single list.
	while (iter != null) {
		next = iter.next;

		RandomListNode copy = new RandomListNode(iter.label);
		iter.next = copy;
		copy.next = next;

		iter = next;
	}

	// Second round: assign random pointers for the copy nodes.
	iter = head;
	while (iter != null) {
		if (iter.random != null) {
			iter.next.random = iter.random.next;
		}
		iter = iter.next.next;
	}

	// Third round: restore the original list, and extract the copy list.
	iter = head;
	RandomListNode pseudoHead = new RandomListNode(0);
	RandomListNode copy, copyIter = pseudoHead;

	while (iter != null) {
		next = iter.next.next;

		// extract the copy
		copy = iter.next;
		copyIter.next = copy;
		copyIter = copy;

		// restore the original list
		iter.next = next;

		iter = next;
	}

	return pseudoHead.next;
}