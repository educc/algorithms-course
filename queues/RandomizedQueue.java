/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        items = (Item[]) new Object[1];
    }

    private static class IteratorRandomizedQueue<Item> implements Iterator<Item> {

        private int size;
        private int position;
        private Item[] items;

        public IteratorRandomizedQueue(Item[] list, int size) {
            this.size = size;
            this.position = 0;
            this.items = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                this.items[i] = list[i];
            }
        }

        public boolean hasNext() {
            return position < size;
        }

        public Item next() {
            if (position >= size) {
                throw new NoSuchElementException();
            }
            int idx = StdRandom.uniform(position, size);
            changePosition(items, position, idx);
            Item it = items[position];
            items[position] = null;
            position++;
            return it;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void changeArraySize(int someSize) {
        Item[] copy = (Item[]) new Object[someSize];
        for (int i = 0; i < size; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    private static void changePosition(Object[] items, int i, int j) {
        Object aux = items[i];
        items[i] = items[j];
        items[j] = aux;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        items[size++] = item;

        if (size == items.length) {
            changeArraySize(size * 2);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        int idxRandom = StdRandom.uniform(0, size);
        changePosition(items, idxRandom, size - 1);

        size--;
        Item item = items[size];
        items[size] = null;
        if (items.length / 2 > size) {
            changeArraySize(items.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int idx = StdRandom.uniform(0, size);
        return items[idx];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new IteratorRandomizedQueue<Item>(items, size());
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();

        // enqueue
        StdOut.print("Enqueue:\n");
        for (int i = 0; i < 5; i++) {
            queue.enqueue(i);
        }

        printQueue(queue);


        StdOut.print("\nPrint sample:\n");
        // print sample
        for (int i = 0; i < 10; i++) {
            StdOut.print(queue.sample());
            StdOut.print(" ");
        }

        // remove all
        StdOut.print("\nRemove all:\n");
        while (!queue.isEmpty()) {
            Integer dequeue = queue.dequeue();
            StdOut.print(dequeue);
            StdOut.print(",");
        }
        printQueue(queue);


        // enqueue
        StdOut.print("\nEnqueue:\n");
        for (int i = 0; i < 4; i++) {
            queue.enqueue(i);
        }

        printQueue(queue);

        // double iterator
        StdOut.print("\nDouble iterator:\n");
        Iterator<Integer> it1 = queue.iterator();
        Iterator<Integer> it2 = queue.iterator();
        while (it1.hasNext()) {
            Integer n1 = it1.next();
            Integer n2 = it2.next();
            StdOut.printf("%d-%d ", n1, n2);
        }

    }

    private static void printQueue(RandomizedQueue<Integer> queue) {
        for (Integer t : queue) {
            StdOut.print(t.toString());
            StdOut.print(" ");
        }
        StdOut.print("\n");
    }

}