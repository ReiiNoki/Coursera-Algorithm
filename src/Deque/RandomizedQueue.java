package Deque;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        queue = (Item[]) new Object[1];
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

        queue[size++] = item;

        if (size >= queue.length) {
            doubleSize(size);
        }
    }

    private void doubleSize(int size) {
        Item[] resizedQueue = (Item[]) new Object[size * 2];
        for (int i = 0; i < queue.length; i++) {
            resizedQueue[i] = queue[i];
        }
        queue = resizedQueue;
    }
    // remove and return a random item
    public Item dequeue() {
        int removeIndex = StdRandom.uniform(size);
        Item item = queue[removeIndex];
        queue[removeIndex] = null;

        if (removeIndex == size) {
            size--;
        }

        if (size < queue.length / 4) {
            shrinkSize(size);
        }

        return item;
    }

    private void shrinkSize(int size) {
        Item[] resizedQueue = (Item[]) new Object[size / 2];
        for (int i = 0; i < queue.length; i++) {
            resizedQueue[i] = queue[i];
        }
        queue = resizedQueue;
    }



    // return a random item (but do not remove it)
    public Item sample() {
        int removeIndex = StdRandom.uniform(size);
        Item item = queue[removeIndex];
        return item;
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item>{
        private Item[] iteratorItems;
        private int index;

        public RandomIterator() {
            iteratorItems = copyRandomQueueItems();
            StdRandom.shuffle(iteratorItems);
        }

        private Item[] copyRandomQueueItems() {
            Item[] copiedItems = (Item[]) new Object[size];
            for (int i = 0; i < size; i ++) {
                copiedItems[i] = queue[i];
            }
            return copiedItems;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return index < iteratorItems.length;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                return iteratorItems[index++];
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}