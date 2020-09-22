package Deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int size = 0;

    private static class Node<Item> {
        private Item item;
        private Node<Item> lastItem;
        private Node<Item> nextItem;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
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
            throw new IllegalArgumentException("You can't add null in the deque");
        }

        if (first == null) {
            Node<Item> newItem = new Node<Item>();
            newItem.item = item;
            first = newItem;
            last = newItem;
        } else {
            Node<Item> newItem = new Node<Item>();
            newItem.item = item;
            newItem.nextItem = first;
            first = newItem;
        }

        size++;
    }



    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("You can't add null in the deque");
        }

        if (last == null) {
            Node<Item> newItem = new Node<Item>();
            newItem.item = item;
            first = newItem;
            last = newItem;
        } else {
            Node<Item> newItem = new Node<Item>();
            newItem.item = item;
            newItem.lastItem = last;
            last = newItem;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.nextItem;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.lastItem;
        size--;
        return item;
    }
    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There is no more item in the deque");
            }
            Item item = current.item;
            current = current.nextItem;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("This method is no longer supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args){

    }

}