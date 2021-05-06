/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[10];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (q.length == size) {
            Item[] newq = (Item[]) new Object[2 * q.length];
            for (int i = 0; i < size; i++) {
                newq[i] = q[i];
            }
            q = newq;
        }
        q[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int random = StdRandom.uniform(0, size);
        Item item = q[random];
        q[random] = q[size - 1];
        q[size - 1] = null;
        size--;
        // check if there are too much empty space in array
        if (q.length > 4 && size * 4 < q.length) {
            Item[] newq = (Item[]) new Object[q.length / 2];
            for (int i = 0; i < size; i++) {
                newq[i] = q[i];
            }
            q = newq;
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int random = StdRandom.uniform(0, size);
        Item item = q[random];
        return item;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
        System.out.println(queue.size());
        for (Integer i : queue) {
            System.out.println(i);
        }
        System.out.println("sample:" + queue.sample());
        System.out.println("dequeue");
        while (!queue.isEmpty()) System.out.println(queue.dequeue());
        System.out.println(queue.size());

    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator {
        private Item[] iteratorqueue;
        private int pos;

        public RandomizedQueueIterator() {
            iteratorqueue = (Item[]) new Object[size];
            for (int i = 0; i < iteratorqueue.length; i++) {
                iteratorqueue[i] = q[i];
            }
            StdRandom.shuffle(iteratorqueue);
            pos = 0;
        }

        public boolean hasNext() {
            return pos < iteratorqueue.length;
        }

        public Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return iteratorqueue[pos++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
