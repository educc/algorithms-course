/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int size;

    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    private static class Node<U> {
        public U item;
        public Node<U> next;
        public Node<U> previous;

        public Node(U item, Node<U> next, Node<U> previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }

    private class DequeIterator implements Iterator<Item> {

        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null) throw new NoSuchElementException();
            Item item = current.item;
            current = current.previous;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove doesn't support");
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("null arg");

        size++;

        if (size == 1) {
            first = new Node<>(item, null, null);
            last = first;
        } else {
            Node<Item> newFirst = new Node<>(item, null, first);
            first.next = newFirst;
            first = newFirst;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("null arg");

        size++;

        if (size == 1) {
            first = new Node<>(item, null, null);
            last = first;
        } else {
            Node<Item> newLast = new Node<>(item, last, null);
            last.previous = newLast;
            last = newLast;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) throw new NoSuchElementException("deque is empty");

        size--;

        Item item = first.item;

        first = first.previous;
        if (first != null) {
            first.next = null;
        } else {
            last = null;
        }

        if (size == 1) {
            last = first;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) throw new NoSuchElementException("deque is empty");

        size--;

        Item item = last.item;
        last = last.next;
        if (last != null) {
            last.previous = null;
        } else {
            first = null;
        }

        if (size == 1) {
            first = last;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();

        // add last and first
        deque.addLast("1");
        deque.addLast("2");
        deque.addLast("3");

        deque.addFirst("-3");
        deque.addFirst("-2");
        deque.addFirst("-1");

        deque.addLast("10");
        deque.addLast("11");
        deque.addLast("12");

        printDeque(deque);

        // remove

        deque.removeLast();
        deque.removeFirst();

        printDeque(deque);

        // remove all
        while (deque.size() > 0) {
            deque.removeFirst();
        }
        printDeque(deque);


        // remove all
        deque.addLast("1");
        deque.addLast("2");
        while (!deque.isEmpty()) {
            deque.removeLast();
        }
        printDeque(deque);
    }

    private static void printDeque(Deque<String> deque) {
        // print all
        for (String item : deque) {
            StdOut.print(item + ";");
        }
        StdOut.print("\n");
    }
}
