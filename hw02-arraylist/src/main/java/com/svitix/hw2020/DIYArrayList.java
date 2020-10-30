package com.svitix.hw2020;

import java.util.*;
import java.util.function.Consumer;

public class DIYArrayList<T> implements List<T> {

    private static final int DEFAULT_SIZE = 5;

    private int size = 0;

    private transient Object[] data;

    public DIYArrayList() {
        this.data = new Object[DEFAULT_SIZE];
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Object[] a = c.toArray();
        int sizeNewPart = a.length;

        checkCapacity(size + sizeNewPart);

        System.arraycopy(a, 0, data, size, sizeNewPart);
        return sizeNewPart != 0;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    private void checkCapacity(int quantityOfAdditions) {
        if (quantityOfAdditions > data.length - size) {
            increaseData();
        }
    }

    private void increaseData() {
        data = Arrays.copyOf(data, data.length + (data.length >> 2)+ 1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super T> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);
        ListIterator<T> i = this.listIterator();
        for (Object e : a) {
            i.next();
            i.set((T) e);
        }
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T) data[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T set(int index, T element) {
        T oldValue = (T) data[index];
        data[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListIterator<T> listIterator() {
        return new DIYArrayList.ListItr(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("iterator");
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T t) {
        checkCapacity(1);
        data[size++] = t;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }


    /**
     * Got this from ArrayList
     */
    private class Itr implements Iterator<T> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        Itr() {}

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public T next() {

            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = DIYArrayList.this.data;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (T) elementData[lastRet = i];
        }

        public void remove() {
         throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super T> consumer) {
          throw new UnsupportedOperationException();
        }

    }

    /**
     * Got this from ArrayList
     */
    private class ListItr extends Itr implements ListIterator<T> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        public T previous() {
            throw new UnsupportedOperationException();
        }

        public void set(T t) {
            if (lastRet < 0)
                throw new IllegalStateException();
            try {
                DIYArrayList.this.set(lastRet, t);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(T t) {
          throw new UnsupportedOperationException();
        }
    }

}
