/**
 * ****************************************************************************
 * Name:
 * Date:
 * Description:
 * ***************************************************************************
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node sentinel;
    private int size = 0;

    private class Node {
        public Item val;
        public Node prev;
        public Node next;

        public Node(Item val, Node p, Node n) {
            this.val = val;
            this.prev = p;
            this.next = n;
        }
    }

    // construct an empty deque
    public Deque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node node = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = node;
        sentinel.next = node;

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node node = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = node;
        sentinel.prev = node;

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Item item = sentinel.next.val;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Item item = sentinel.prev.val;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return item;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        System.out.println(deque.size());
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addLast(5);
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());
        Iterator<Integer> iterator = deque.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        deque.removeFirst();
        deque.removeLast();
        System.out.println(deque.size());
        Iterator<Integer> iterator1 = deque.iterator();
        while (iterator1.hasNext()) {
            System.out.println(iterator1.next());
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node p;
        private int pos;

        public DequeIterator() {
            p = sentinel;
            pos = 0;
        }

        public boolean hasNext() {
            return pos < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = p.next.val;
            p = p.next;
            pos++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
